# 应用层读写分离的改进


## 背景

数据库读写分离是构建高性能 Web 架构不可缺少的一环，其主要提升在于：

1. 主从职责单一，主写从读，可以极大程度地缓解 X 锁和 S 锁的竞争，并且可以进行针对性调优
2. 请求分流，减少主库压力
3. 当读成为 DB 瓶颈时，很容易进行水平拓展
4. 增加冗余，实现高可用，出现故障后可快速恢复，仅丢失少量数据或不丢失数据


## 实现方式

读写分离首先需要 DB 实例的支持，配置主库、从库以及主从同步策略，此步骤一般交给 OP 即可。实例搭建完毕后，我们就可以开发相应模块，以实现真正的读写分离。

业界的实现方式一般分为两种：**DB 中间件** 和 **应用层读写分离**，二者均有各自的优缺点，详情见下表：

### DB 中间件

> 优点：对于应用透明；不限语言<br>
> 缺点：专人部署 + 维护；保证 HA、LB；一般只支持 MySQL

### 应用层读写分离

> 优点：开发简单，团队内部可以自行消化；基于 JDBC 驱动或框架，理论支持任意类型的 DB<br>
> 缺点：通用性差，各应用需要自己实现；手动指定数据源

用不用 DB 中间件需要考虑实际情况，如数据体量和有没有人维护等等，本文讲的是应用层读写分离。

## 当前方案

通过自定义注解 `@DataSourceRoute`，手动声明当前方法操作的数据源，再通过切面拦截该切入点，路由到目标数据源。

因为实际中还要与事务结合，所以又写了一套基于事务路由主从数据源的切面，使用起来较为繁琐。

```java
//annotation
public @interface DataSourceRoute {
    AccessType type() default AccessType.MASTER;
}

//aspect
public class DataSourceRouteAspect {
    @Before("@annotation(DataSourceRoute)")
    public void before(JoinPoint point) {
        Method targetMethod = ((MethodSignature) point.getSignature()).getMethod();
        DataSourceRoute annotation = targetMethod.getAnnotation(DataSourceRoute.class);
        DynamicDataSourceHolder.route(annotation.type());
    }
}

//dao
@DataSourceRoute(type=AccessType.SLAVE)
public Housedel findByPK(Long housedelCode) {
    return mapper.findByPK(housedelCode);
}
```


## 改进方案

其实总结一下我们使用读写分离的场景会发现，主库一般负责写入（偶尔用来读），从库则全部用来读取。而为了保障数据的正确性，我们在写入操作时一般会加上事务（这也是我推荐的最佳实践），也就是说，大部分事务操作是在写入，大部分非事务操作则是在读取，由此可见读写分离和事务之间是有一定关联的。

既然思路是可行的，那我们不妨思考一下，实际使用中具体有哪些场景呢？

| 序号 | 事务 | 数据源 | 操作
| :-- | :-- | :-- | :-- |
| 1 | 无 | 从库 | 读
| 2 | 无 | 主库 | 读
| 3 | 有 | 从库 | 读
| 4 | 有 | 主库 | 写

第 1 种，无事务从库读取。典型的只读场景，我们的业务场景一般是读多写少，为了方便，可以作为默认选项。

第 2 种，无事务主库读取。主库中读取数据的情况还是比较少见的，一般是因为对数据的实时性要求较高，而 MySQL 的主从复制是异步的，中间会有短暂的时间差，为了保证数据的一致性，会直接从主库读取。

第 3 种，有事务从库读取。前面我们说道，事务一般加在写入操作上，但也有个别情况只读时也需要加入事务，比如在当前只读事务内，不希望其它事务更改数据，从而保证数据前后的一致性。

第 4 种，有事务主库写入。典型的写入场景，数据写入主库后，异步复制到从库。


## 落地

那么如何实现呢？阅读 Spring 的源码会发现，`DataSourceTransactionManager`[^3] 是 Spring 用来管理事务的类，我们只需要自定义一个事务管理器，在开启事务之前指定数据源即可。

有了之前的分析，我们可以得到以下规则：默认无事务时路由到从库，**有事务且非只读**时路由到主库。

1. 定义动态数据源
```java
public class DynamicDataSource extends AbstractRoutingDataSource {
    public DynamicDataSource(Object defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
    }
    
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.get();
    }
}

public final class DynamicDataSourceHolder {
    public static final String MASTER_DATA_SOURCE = "Master";
    public static final String SLAVE_DATA_SOURCE = "Slave";

    private static final ThreadLocal<String> CONTAINER = ThreadLocal.withInitial(
            () -> DynamicDataSourceHolder.SLAVE_DATA_SOURCE
    );

    public static void routeMaster() {
        CONTAINER.set(MASTER_DATA_SOURCE);
    }
    public static void routeSlave() {
        CONTAINER.set(SLAVE_DATA_SOURCE);
    }
    public static String get() {
        return CONTAINER.get();
    }
    public static void clear() {
        CONTAINER.remove();
    }
}
```

2. 声明动态数据源
```java
@Configuration
public class SpringDataSourceConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource masterDataSource() {
        return new DruidDataSource();
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource slaveDataSource() {
        return new DruidDataSource();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DruidDataSource masterDataSource, DruidDataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = ImmutableMap.builder()
                .put(MASTER_DATA_SOURCE, masterDataSource)
                .put(SLAVE_DATA_SOURCE, slaveDataSource)
                .build();
        return new DynamicDataSource(slaveDataSource, targetDataSources);
    }
}
```

3. 重写 Spring 默认的事务管理器
```java
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {

    public DynamicDataSourceTransactionManager(DataSource dataSource) {
        super(dataSource);
    }
    
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        if (!definition.isReadOnly()) {
            DynamicDataSourceHolder.routeMaster();
        }
        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        DynamicDataSourceHolder.clear();
        super.doCleanupAfterCompletion(transaction);
    }
}
```

4. 声明自定义的事务管理器
```java
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class SpringDaoConfig implements TransactionManagementConfigurer {
    @Autowired
    private DynamicDataSource dataSource;

    @Override
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DynamicDataSourceTransactionManager(dataSource);
    }
}
```

代码贴完了，让我们来看看能不能满足之前的 4 种场景呢？

其中 1、4 的区别仅仅是加不加事务，比较简单，那么待解决的还有 2 和 3。第 2 种因为没有事务，需要我们手动指定数据源，第 3 种则使用 Spring 提供的只读事务[^1]即可实现。

| 序号 | 事务 | 数据源 | 操作 | 实现方式 |
| :-- | :-- | :-- | :-- |
| 1 | 无 | 从库 | 读 | 默认 |
| 2 | 无 | 主库 | 读 | 手动指定 `DynamicDataSourceHolder.routeMaster()` |
| 3 | 有 | 从库 | 读 | `@Transactional(readOnly = true)` |
| 4 | 有 | 主库 | 写 | `@Transactional` |

如此一来，之前的问题都已经解决。我们仅仅通过 Spring 自带的 `@Transactional` 注解即可指定数据源，对比之前简化不少。


## 硬广

由于篇幅原因，文章中没有展示具体的执行结果。完整代码已打包成 `dynamic-data-source-demo`项目，并上传到 [Github](https://github.com/drtrang/dynamic-data-source-demo)，项目中提供完整的单元测试，详情大家可以 Clone 到本地自己执行一遍。

`dynamic-data-source-demo` 项目基于 Spring Boot，集成了 MyBatis、通用 Mapper、PageHelper、Druid、Copiers，可以作为简单的脚手架使用，欢迎大家 Star 或者 Fork 到自己的仓库。

如果有问题，可以在 Github 上提 Issue，或者微信交流，以下是联系方式：<br>
我的 Github 地址：https://github.com/drtrang<br>
项目 Github 地址：https://github.com/drtrang/dynamic-data-source-demo<br>
BeanCopier 工具：https://github.com/drtrang/Copiers<br>
微信：dong349096849


[^1]: 只读事务：http://www.blogjava.net/terry-zj/archive/2005/12/06/22792.html

[^2]: Spring的动态数据源：http://www.cnblogs.com/surge/p/3582248.html

[^3]: Spring事务源码分析：http://www.cnblogs.com/lcxdever/p/4570090.html
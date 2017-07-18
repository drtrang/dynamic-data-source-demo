# Changelog

## 1.0.1 2017-07-18
1. 使用 [`druid-spring-boot-starter`](https://github.com/drtrang/druid-spring-boot) 替换官方 Druid 

## 1.0.0 2017-06-30
1. 常规依赖升级 `druid`、`page-helper`、`mybatis-generator-extension`
2. 基于 0.0.2 版本的 `mybatis-generator-extension` 重新生成相应文件
3. 提供基于 `com.mysql.jdbc.ReplicationDriver` 的读写分离方式，请 checkout 到 *mysql* 分支
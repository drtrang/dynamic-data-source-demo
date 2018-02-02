package com.github.trang.dynamic.util;

import com.github.pagehelper.PageInfo;
import com.github.trang.dynamic.base.response.Message;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;

/**
 * #{@link Message} 辅助工具类
 *
 * @author trang
 */
@UtilityClass
public class MessageHelper {

    /**
     * 创建一个属性为空的 PageInfo 对象
     */
    private static final PageInfo<?> EMPTY_PAGE = new PageInfo<>();

    /**
     * 返回一个属性为空的 PageInfo 对象
     */
    public static PageInfo<?> emptyPage() {
        return EMPTY_PAGE;
    }

    //// 第一种方式（正经模式），执行 provider 返回 MessageWrapper，由 MessageWrapper 进行转换 ////

    /** */
    public static <T> MessageWrapper<T> execute(Provider<T> provider) {
        return new MessageWrapper<>(provider.execute());
    }

    //// 第二种方式（兼容模式），先执行接口得到 message 后再转换 ////

    /**  */
    public static <T> MessageTransformer<T> isOk(Message<T> message) {
        return new MessageWrapper<>(message).isOk();
    }

    /**  */
    public static <T> T okAndGet(Message<T> message) {
        return new MessageWrapper<>(message).okAndGet();
    }

    //// 第三种方式（偷懒模式），执行 provider 直接转换 ////

    /**  */
    public static <T> MessageTransformer<T> isOk(Provider<T> provider) {
        return new MessageWrapper<>(provider.execute()).isOk();
    }

    /**  */
    public static <T> T okAndGet(Provider<T> provider) {
        return new MessageWrapper<>(provider.execute()).okAndGet();
    }

    /**
     * 外部接口提供者
     *
     * @param <T>
     * @author trang
     */
    public interface Provider<T> {

        /**
         * 执行接口
         *
         * @return message
         */
        Message<T> execute();

    }

    /**
     * #{@link Message} 包装类，通过 #{@link MessageTransformer} 提供丰富的转换功能
     *
     * @param <T>
     * @author trang
     */
    @RequiredArgsConstructor
    public static class MessageWrapper<T> {

        private final Message<T> message;

        public MessageTransformer<T> isOk() {
            return new MessageTransformer<>(message);
        }

        public MessageTransformer<T> isOkOrElseThrow(RuntimeException ex) {
            return new MessageTransformer<>(message, ex);
        }

        public T okAndGet() {
            return new MessageTransformer<>(message).get();
        }

    }

    /**
     * #{@link Message} 转换工具类类，提供丰富的转换功能
     *
     * @param <T>
     * @author trang
     */
    @EqualsAndHashCode
    @ToString
    public static class MessageTransformer<T> {

        private static final MessageTransformer<?> EMPTY = new MessageTransformer<>();

        private final T value;

        private MessageTransformer() {
            this.value = null;
        }

        private MessageTransformer(T value) {
            this.value = Objects.requireNonNull(value);
        }

        private MessageTransformer(Message<T> message) {
            Objects.requireNonNull(message);
            this.value = Optional.of(message)
                    .filter(m -> m.getCode() == 1)
                    .orElseThrow(() -> new RuntimeException(message.getMsg()))
                    .getData();
        }

        private MessageTransformer(Message<T> message, RuntimeException ex) {
            Objects.requireNonNull(message);
            this.value = Optional.of(message)
                    .filter(m -> m.getCode() == 1)
                    .orElseThrow(() -> ex)
                    .getData();
        }

        @SuppressWarnings("unchecked")
        public static <T> MessageTransformer<T> empty() {
            return (MessageTransformer<T>) EMPTY;
        }

        public static <T> MessageTransformer<T> of(T value) {
            return new MessageTransformer<>(value);
        }

        public static <T> MessageTransformer<T> ofNullable(T value) {
            return value != null ? of(value) : empty();
        }

        public boolean isPresent() {
            return value != null;
        }

        public T get() {
            if (value == null) {
                throw new NoSuchElementException("不能获取 null 值！");
            }
            return value;
        }

        public T getOrElse(T other) {
            return isPresent() ? value : other;
        }

        public T getOrNull() {
            return getOrElse(null);
        }

        @SuppressWarnings("unchecked")
        public T getOrEmptyList() {
            return getOrElse((T) emptyList());
        }

        @SuppressWarnings("unchecked")
        public T getOrEmptyPage() {
            return getOrElse((T) emptyPage());
        }

        public T getOrElseGet(Supplier<? extends T> other) {
            return getOrElse(other.get());
        }

        public <X extends Throwable> T getOrElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            if (isPresent()) {
                return value;
            } else {
                throw exceptionSupplier.get();
            }
        }

        public void ifPresent(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            if (isPresent()) {
                consumer.accept(value);
            }
        }

        public MessageTransformer<T> filter(Predicate<? super T> predicate) {
            Objects.requireNonNull(predicate);
            return isPresent() ? (predicate.test(value) ? this : empty()) : empty();
        }

        public <U> MessageTransformer<U> map(Function<? super T, ? extends U> mapper) {
            Objects.requireNonNull(mapper);
            return isPresent() ? MessageTransformer.ofNullable(mapper.apply(value)) : empty();
        }

        public <U> U mapAndGet(Function<? super T, ? extends U> mapper) {
            return map(mapper).get();
        }

        public <U> U mapOrElse(Function<? super T, ? extends U> mapper, U other) {
            MessageTransformer<? extends U> nullable = map(mapper);
            return nullable.isPresent() ? nullable.get() : other;
        }

        public <U> U mapOrNull(Function<? super T, ? extends U> mapper) {
            return mapOrElse(mapper, null);
        }

        public <U> Message<U> mapOrOk(Function<? super T, ? extends Message<U>> mapper) {
            return mapOrElse(mapper, Message.ok());
        }

        public <U> List<U> mapOrEmptyList(Function<? super T, ? extends List<U>> mapper) {
            return mapOrElse(mapper, emptyList());
        }

        @SuppressWarnings("unchecked")
        public <F, U> MessageTransformer<PageInfo<U>> mapPage(Function<? super List<F>, ? extends List<U>> mapper) {
            Objects.requireNonNull(mapper);
            boolean isPage = value instanceof PageInfo;
            if (!isPage) {
                throw new IllegalArgumentException("'value' can't cast to PageInfo!");
            } else {
                if (!isPresent()) {
                    return empty();
                }
                PageInfo<F> page = (PageInfo<F>) this.value;
                List<U> list = mapper.apply(page.getList());
                return MessageTransformer.of(new PageInfo<>(list));
            }
        }

        public <F, U> PageInfo<U> mapPageAndGet(Function<? super List<F>, ? extends List<U>> mapper) {
            return mapPage(mapper).get();
        }

        @SuppressWarnings("unchecked")
        public <F, U> PageInfo<U> mapPageOrEmpty(Function<? super List<F>, ? extends List<U>> mapper) {
            MessageTransformer<PageInfo<U>> nullable = mapPage(mapper);
            return nullable.isPresent() ? nullable.get() : (PageInfo<U>) emptyPage();
        }

        public <U> MessageTransformer<U> flatMap(Function<? super T, MessageTransformer<U>> mapper) {
            Objects.requireNonNull(mapper);
            return isPresent() ? Objects.requireNonNull(mapper.apply(value)) : empty();
        }

        public <U> U flatMapAndGet(Function<? super T, MessageTransformer<U>> mapper) {
            return flatMap(mapper).get();
        }

        public <U> U flatMapOrElse(Function<? super T, MessageTransformer<U>> mapper, U other) {
            MessageTransformer<U> nullable = flatMap(mapper);
            return nullable.isPresent() ? nullable.get() : other;
        }

        public <U> U flatMapOrNull(Function<? super T, MessageTransformer<U>> mapper) {
            return flatMapOrElse(mapper, null);
        }

        public <U> Message<U> flatMapOrOk(Function<? super T, MessageTransformer<Message<U>>> mapper) {
            return flatMapOrElse(mapper, Message.ok());
        }

        public <U> List<U> flatMapOrEmptyList(Function<? super T, MessageTransformer<List<U>>> mapper) {
            return flatMapOrElse(mapper, emptyList());
        }

    }

}
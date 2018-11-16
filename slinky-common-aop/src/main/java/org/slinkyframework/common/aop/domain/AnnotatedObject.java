package org.slinkyframework.common.aop.domain;

public class AnnotatedObject<T, A> {

    private final T object;
    private final A annotation;

    public AnnotatedObject(T object, A annotation) {
        this.object = object;
        this.annotation = annotation;
    }

    public T getObject() {
        return object;
    }

    public A getAnnotation() {
        return annotation;
    }
}

package org.slinkyframework.common.aop.domain;

import java.lang.annotation.Annotation;

public class AnnotatedObject {

    private Object object;
    private Annotation annotation;

    public AnnotatedObject(Object object, Annotation annotation) {
        this.object = object;
        this.annotation = annotation;
    }

    public Object getObject() {
        return object;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}

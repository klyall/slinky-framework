package org.slinkyframework.common.logging;

import org.slinkyframework.common.logging.mask.Mask;
import org.slinkyframework.common.logging.mask.NoMaskMasker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD })
public @interface Loggable {
    String value() default "";
    String name() default "";

    String fields() default "";

    Class<? extends Mask> mask() default NoMaskMasker.class;
}

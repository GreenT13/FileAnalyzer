package com.apon.impl.processors;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DataMartProcessor {
    int order() default Integer.MAX_VALUE;
}

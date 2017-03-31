package com.harreke.easyapp.injection.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 由huoqisheng于2016/4/25创建
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface InjectToolbar {
    String value() default "";
}
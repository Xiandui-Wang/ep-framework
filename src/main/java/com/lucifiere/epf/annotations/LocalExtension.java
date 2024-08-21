package com.lucifiere.epf.annotations;

import com.lucifiere.epf.enums.BizLine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface LocalExtension {

    String id() default "";

    String desc() default "";

    String expression() default "";

    int priority() default 0;

    BizLine[] biz();

}

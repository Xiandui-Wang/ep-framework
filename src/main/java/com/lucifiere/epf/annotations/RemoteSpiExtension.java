package com.lucifiere.epf.annotations;

import com.lucifiere.epf.enums.BizLine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RemoteSpiExtension {

    String id() default "";

    String desc() default "";

    String expression() default "";

    int priority() default 0;

    String appKey() default "";

    String service() default "";

    String port() default "";

    long timeout() default 3000L;

    BizLine[] biz();

}

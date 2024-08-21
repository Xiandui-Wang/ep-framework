package com.lucifiere.epf.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExtensionSpec {

    String id() default "";

    String desc() default "";

}

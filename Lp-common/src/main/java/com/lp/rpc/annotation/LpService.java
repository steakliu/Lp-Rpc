package com.lp.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface LpService {
    String version() default "";
    String path() default "";
    String group() default "";
}

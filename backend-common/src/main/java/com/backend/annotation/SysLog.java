package com.backend.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author Skynet
 * @date 2017年04月22日 01:13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}

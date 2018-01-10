package com.cal.base.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义的系统Action日志注解
 * @author andyc
 * 2018-1-1
 */
@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemActionLog {
	String desc() default "";
}

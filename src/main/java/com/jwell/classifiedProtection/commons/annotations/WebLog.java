package com.jwell.classifiedProtection.commons.annotations;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)//这个值最低-2147483648，优先级最高
public @interface WebLog {

    /**
     * 日志描述信息
     * @return
     */
    String description() default "";
}

package com.heyqing.cvarst_back.common.aop;

import java.lang.annotation.*;

/**
 * ClassName:LogAnnotation
 * Package:com.heyqing.cvarst_back.common.aop
 * Description:
 *
 * @Date:2023/11/12
 * @Author:Heyqing
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";
    String operator() default "";
}

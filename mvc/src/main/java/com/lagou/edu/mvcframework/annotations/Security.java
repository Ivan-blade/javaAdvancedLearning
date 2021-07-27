package com.lagou.edu.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * @author hylu.Ivan
 * @date 2021/7/27 下午10:18
 * @description
 */

@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Security {

    String[] value() default "";
}

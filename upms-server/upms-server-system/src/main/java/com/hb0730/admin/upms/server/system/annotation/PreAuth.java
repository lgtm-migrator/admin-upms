package com.hb0730.admin.upms.server.system.annotation;

import java.lang.annotation.*;

/**
 * 权限注解,用于拼接
 *
 * @author bing_huang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuth {
    String value() default "";
}

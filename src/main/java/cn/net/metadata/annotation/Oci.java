package cn.net.metadata.annotation;

import java.lang.annotation.*;

/**
 * Created by wjp on 2017/5/26.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Oci {
    String value() default "";
}

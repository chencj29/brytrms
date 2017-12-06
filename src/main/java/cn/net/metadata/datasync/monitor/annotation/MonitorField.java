package cn.net.metadata.datasync.monitor.annotation;

import java.lang.annotation.*;

/**
 * Created by xiaowu on 16/1/7.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MonitorField {
    String desc();
}

package cn.net.metadata.datasync.monitor.annotation;

import java.lang.annotation.*;

/**
 * Created by xiaowu on 16/1/23.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionService {
    ActionServiceType type();

    String desc();
}

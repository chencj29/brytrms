package cn.net.metadata.datasync.monitor.annotation;

import com.thinkgem.jeesite.common.service.CrudService;

import java.lang.annotation.*;

/**
 * 用于标识供前台可配置的实体类
 * Created by xiaowu on 16/1/7.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Monitor {
    String desc();

    String tableName();

    Class<? extends CrudService> service();

    String socketNS();
}

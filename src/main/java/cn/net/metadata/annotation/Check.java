package cn.net.metadata.annotation;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.lang.annotation.*;

/**
 * Created by wjp on 2017/3/20.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Check {
    /**
     * 要执行的具体操作比如：添加航班、到达，延误等
     **/
    String operationName() default "";

    boolean isOci() default false;

    Class<? extends DataEntity> oci() default DataEntity.class;
}

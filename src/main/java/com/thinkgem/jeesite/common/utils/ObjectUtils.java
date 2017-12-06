/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import cn.net.metadata.datasync.monitor.wrapper.MonitorTipWrapper;
import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;

/**
 * 对象操作工具类, 继承org.apache.commons.lang3.ObjectUtils类
 *
 * @author ThinkGem
 * @version 2014-6-29
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

    /**
     * 注解到对象复制，只复制能匹配上的方法。
     *
     * @param annotation
     * @param object
     */
    public static void annotationToObject(Object annotation, Object object) {
        if (annotation != null) {
            Class<?> annotationClass = annotation.getClass();
            Class<?> objectClass = object.getClass();
            for (Method m : objectClass.getMethods()) {
                if (StringUtils.startsWith(m.getName(), "set")) {
                    try {
                        String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
                        Object obj = annotationClass.getMethod(s).invoke(annotation);
                        if (obj != null && !"".equals(obj.toString())) {
                            if (object == null) {
                                object = objectClass.newInstance();
                            }
                            m.invoke(object, obj);
                        }
                    } catch (Exception e) {
                        // 忽略所有设置失败方法
                    }
                }
            }
        }
    }

    /**
     * 序列化对象
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            if (object != null) {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化对象
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            if (bytes != null && bytes.length > 0) {
                bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean toComp(Object newData, Object oldData, List<String> changeList) {
        boolean isSuccess = false;
        if (!com.thinkgem.jeesite.common.utils.StringUtils.equals(newData.getClass().getName(), oldData.getClass().getName()))
            return isSuccess;
        for (Field field : ReflectionUtils.getFields(oldData.getClass())) {
            if (isChanged(newData, oldData, field.getName())) {
                if (!field.isAnnotationPresent(MonitorField.class)) continue;
                String desc = field.getAnnotation(MonitorField.class).desc();
                String oldValue = toStr(Reflections.getFieldValue(oldData, field.getName()));
                String newValue = toStr(Reflections.getFieldValue(newData, field.getName()));

                if (field.getType().getName().equals("java.util.Date")) {  // 日期字段特殊处理
                    if (com.thinkgem.jeesite.common.utils.StringUtils.isNoneEmpty(oldValue) && !com.thinkgem.jeesite.common.utils.StringUtils.equals(oldValue, "null"))
                        oldValue = DateUtils.formatDate((Date) Reflections.getFieldValue(oldData, field.getName()), "yyyy-MM-dd HH:mm:ss");
                    if (com.thinkgem.jeesite.common.utils.StringUtils.isNoneEmpty(newValue) && !com.thinkgem.jeesite.common.utils.StringUtils.equals(newValue, "null"))
                        newValue = DateUtils.formatDate((Date) Reflections.getFieldValue(newData, field.getName()), "yyyy-MM-dd HH:mm:ss");
                }
                changeList.add(new MonitorTipWrapper(field.getName(), desc, oldValue, newValue).toString());
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    /**
     * 获取基类日志记录
     *
     * @param obj
     * @param notField 过滤不显示的字段
     * @return
     */
    public static String toEnitiyLog(Object obj, String... filterFields) {
        if (obj == null) return "";
        StringBuffer sb = new StringBuffer();
        List<String> filterField = null;
        if (filterFields != null && filterFields.length > 0) filterField = Arrays.asList(filterFields);
        if (obj != null && obj.getClass().isAnnotationPresent(Monitor.class)) {
            sb.append(obj.getClass().getAnnotation(Monitor.class).desc());
        }
        sb.append("[");
        for (Field field : ReflectionUtils.getFields(obj.getClass())) {
            //不计录空值
            if (!Collections3.isEmpty(filterField) && filterField.contains(field.getName())) continue;
            if (field.isAnnotationPresent(MonitorField.class)) {
                Object objValue = Reflections.invokeGetter(obj, field.getName());
                if (objValue == null) continue;
                sb.append(field.getAnnotation(MonitorField.class).desc());
                sb.append(":");
                if (field.getType().getName().equals("java.util.Date")) {
                    if (StringUtils.equals(field.getName(), "planDate"))
                        sb.append(DateUtils.formatDate((Date) objValue));
                    else sb.append(DateUtils.formatDate((Date) objValue, "yyyy-MM-dd HH:mm"));
                } else {
                    sb.append(String.valueOf(Reflections.invokeGetter(obj, field.getName())));
                }
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static boolean isChanged(Object newData, Object oldData, String fieldName) {
        return !toStr(Reflections.getFieldValue(newData, fieldName)).equals(toStr(Reflections.getFieldValue(oldData, fieldName)));
    }

    private static String toStr(Object obj) {
        return obj == null ? "" : obj.toString();
    }

}

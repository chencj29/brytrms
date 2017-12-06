/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.service;

import cn.net.metadata.datasync.monitor.annotation.Monitor;
import cn.net.metadata.datasync.monitor.annotation.MonitorField;
import com.alibaba.fastjson.JSONArray;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SysConstant;
import com.thinkgem.jeesite.modules.ams.dao.SysDictDao;
import com.thinkgem.jeesite.modules.ams.entity.SysDict;
import com.thinkgem.jeesite.modules.ams.entity.common.DataTablesPage;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.reflections.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service基类
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    @Autowired
    private SysDictDao sysDictDao;

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 查询分页数据
     *
     * @param page   分页对象
     * @param entity
     * @return
     */
    public Page<T> findPage(Page<T> page, T entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void save(T entity) {
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete(T entity) {
        dao.delete(entity);
    }

    public DataTablesPage findDataTablesPage(DataTablesPage<T> dataTablesPage, T entity) {
        Criteria criteria = hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(entity.getClass());

        if (entity != null) {
            criteria.add(Example.create(entity).enableLike());
        }

        // 获取根据条件分页查询的总行数
        int rowCount = Integer.valueOf(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
        criteria.setProjection(null);

        criteria.setFirstResult((dataTablesPage.getDraw() - 1) * dataTablesPage.getLength());
        criteria.setMaxResults(dataTablesPage.getLength());

        List result = criteria.list();

        dataTablesPage.setRecordsTotal(rowCount);
        dataTablesPage.setRecordsFiltered(rowCount);
        dataTablesPage.setData(result);

        return dataTablesPage;
    }

    public DataTablesPage<T> findDataTablesPage(Page<T> page,DataTablesPage<T> dataTablesPage, T entity) {
        page.setPageNo(dataTablesPage.getPageNo());
        page.setPageSize(dataTablesPage.getLength());
        Page<T> oldPage = findPage(page, entity);
        //
        dataTablesPage.setRecordsTotal((int)oldPage.getCount());
        dataTablesPage.setRecordsFiltered((int)oldPage.getCount());
        dataTablesPage.setData(oldPage.getList());
        return dataTablesPage;
    }


    public String getBaseSiteCode(){
        SysDict baseSite = new SysDict();
        baseSite.setType(SysConstant.BASE_SITE);
        baseSite.setTableName("sys_settings");
        baseSite = sysDictDao.getByTableNameAndType(baseSite);
        return  baseSite.getValue();
    }

    /**
     * 检查字段值是否重复(多字段默认为并且关系) wjp_2017年3月10日16时03分
     */

    public void checkRedo(Object obj,String[] fieldNames,Message msg, boolean ... is_Or){
        boolean resultFlag = false;
        if(obj !=null && obj.getClass().isAnnotationPresent(Monitor.class) && fieldNames !=null) {
            String sql = "SELECT COUNT(*) from ${tableName} WHERE ${fieldName} = ? AND DEL_FLAG = 0";
            String tableName = obj.getClass().getAnnotation(Monitor.class).tableName();
            if (StringUtils.isNotBlank(tableName)) {
                if(is_Or.length == 0) {
                    //多字段唯一且是且的关系的（eg：机位号唯一，机坪号也唯一）
                    String id = (String) Reflections.getFieldValue(obj, "id");
                    List<String> tmpList = new ArrayList<>();
                    List<Object> tmpValueList = new ArrayList();
                    for (String name : fieldNames) {
                        tmpList.add(StringUtils.toUnderScoreCase(name));
                        tmpValueList.add(Reflections.getFieldValue(obj, name));
                    }
                    String sqlStr;
                    if(StringUtils.isBlank(id)) {
                        sqlStr = sql.replace("${tableName}", tableName).replace("${fieldName}", StringUtils.join(tmpList,"=? AND "));
                    }else{
                        sqlStr = sql.replace("${tableName}", tableName).replace("${fieldName}", "id<>'"+id+"' AND "+StringUtils.join(tmpList,"=? AND "));
                    }
                    resultFlag = jdbcTemplate.queryForObject(sqlStr, tmpValueList.toArray(), Integer.class) > 0 ? true : false;
                }else{
                    //多字段唯一且是且的关系的（eg：机位号唯一，机坪号也唯一）
                    String id = (String) Reflections.getFieldValue(obj, "id");
                    if(StringUtils.isBlank(id)) {
                        for (String name : fieldNames) {
                            String sqlStr = sql.replace("${tableName}", tableName).replace("${fieldName}", StringUtils.toUnderScoreCase(name));
                            resultFlag = jdbcTemplate.queryForObject(sqlStr, new Object[]{Reflections.getFieldValue(obj, name)}, Integer.class) > 0 ? true : false;
                            if(resultFlag) break;
                        }
                    }else{
                        for (String name : fieldNames) {
                            String sqlStr = sql.replace("${tableName}", tableName).replace("${fieldName}", "id <>'"+id+"' AND "+StringUtils.toUnderScoreCase(name));
                            resultFlag = jdbcTemplate.queryForObject(sqlStr, new Object[]{Reflections.getFieldValue(obj, name)}, Integer.class) > 0 ? true : false;
                            if(resultFlag) break;
                        }
                    }
                }

                if(resultFlag) {
//                    List<String> chNameList = ReflectionUtils.getFields(obj.getClass(), field -> field.isAnnotationPresent(MonitorField.class) && Arrays.asList(fieldNames).contains(field.getName()))
//                            .stream().map(field -> {
//                                return field.getAnnotation(MonitorField.class).desc();
//                            }).collect(Collectors.toList());
                    List<String> chNameList = new ArrayList<>();
                    for (Field field : ReflectionUtils.getFields(obj.getClass())) {
                        if(Arrays.asList(fieldNames).contains(field.getName())) chNameList.add(field.getAnnotation(MonitorField.class).desc());
                    }
                    msg.setCode(1);
                    msg.setMessage(StringUtils.join(chNameList, "、") + "不能重复");
                }
            }
        }
    }

    //将json对象转为导出数据的表和取值对象的属性名
    public static String[] jsonTofiled(JSONArray jsonArray, List<String> fields, List<String>... filterFields) {
        List<String> rowsNameList = new ArrayList<>();
        jsonArray.forEach(a -> {
            if (a instanceof Map) {
                for (Map.Entry<String, String> entry : ((Map<String, String>) a).entrySet()) {
                    if (filterFields != null && filterFields.length == 1) { //过滤不导出字段
                        String key = entry.getKey();
                        boolean flag = false;
                        for (String s : filterFields[0]) {
                            if (key.toUpperCase().indexOf(s.toUpperCase()) >= 0) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            fields.add(key);
                            rowsNameList.add(entry.getValue());
                        }
                    } else {
                        fields.add(entry.getKey());
                        rowsNameList.add(entry.getValue());
                    }
                }
            }
        });
        String[] rowsName = new String[rowsNameList.size()];
        for (int i = 0; i < rowsNameList.size(); i++) {
            rowsName[i] = rowsNameList.get(i);
        }
        return rowsName;
    }
}

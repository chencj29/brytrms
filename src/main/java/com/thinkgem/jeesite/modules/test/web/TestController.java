/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.net.httpserver.Headers;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.service.FlightDynamicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.test.entity.Test;
import com.thinkgem.jeesite.modules.test.service.TestService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 测试Controller
 *
 * @author ThinkGem
 * @version 2013-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/test/test")
public class TestController extends BaseController {

    @Autowired
    private TestService testService;

    @Autowired
    private FlightDynamicService flightDynamicService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static Map<String, String> ptext = new LinkedHashMap<>();
    private static Map<String, String> ptext2 = new LinkedHashMap<>();
    private static Map<String, String> psql = new HashMap<>();
    private static Map<String, String> sqlTable = new HashMap<>();
    private static String findOciDataSql;

    static {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("	(select count(1) FROM {{fd_to}} WHERE INOUT_TYPE_CODE = 'J') 进港,");
        sql.append("	(select count(1) FROM {{fd_to}} WHERE INOUT_TYPE_CODE = 'C') 出港,");
        sql.append("	(select count(1) FROM {{pair_to}}) 连班,");
        sql.append("	(SELECT count(1) FROM {{poci_to}}) 机位p,");        //-- 机位占用数据
        sql.append("	(SELECT count(1) FROM {{checking_to}}) 值机柜台c,");   //-- 值机柜台占用数据
        sql.append("	(SELECT count(1) FROM {{boarding_to}}) 登机口c,");        //-- 登机口占用数据
        sql.append("	(SELECT count(1) FROM {{arrival_to}}) 到港门j,");         //-- 到港门占用数据
        sql.append("	(SELECT count(1) FROM {{carousel_to}}) 行李转盘j,");           //-- 行李转盘占用数据
        sql.append("	(SELECT count(1) FROM {{slide_to}}) 滑槽c,");           //-- 滑槽占用数据
        sql.append("	(SELECT count(1) FROM {{departure_to}}) 候机厅c,");       //-- 候机厅占用数据
        sql.append("	(SELECT count(1) FROM {{security_to}}) 安检口c ");      //-- 安检口占用数据
        sql.append("FROM dual");
        findOciDataSql = sql.toString();

        ptext.put("fd_to", "ams->rms(动态表)");
        ptext.put("fd_re", "rms->ams(动态表)");
        sqlTable.put("fd_to", "AMS_FLIGHT_DYNAMIC");
        sqlTable.put("fd_re", "AMS_FLIGHT_DYNAMIC@AMS");
        psql.put("fd_to", "INSERT INTO {{fd_to}} SELECT * from {{fd_re}} WHERE id NOT IN (SELECT id from {{fd_to}})");
        psql.put("fd_re", "INSERT INTO {{fd_re}} SELECT * from {{fd_to}} WHERE id NOT IN (SELECT id from {{fd_re}})");

        ptext.put("pair_to", "ams->rms(配对表)");
        ptext.put("pair_re", "rms->ams(配对表)");
        sqlTable.put("pair_to", "AMS_FLIGHT_DYNAMIC_PAIR");
        sqlTable.put("pair_re", "AMS_FLIGHT_DYNAMIC_PAIR@AMS");
        psql.put("pair_to", "INSERT INTO {{pair_to}} SELECT * from {{pair_re}} WHERE id NOT IN (SELECT id from {{pair_to}})");
        psql.put("pair_re", "INSERT INTO {{pair_re}} SELECT * from {{pair_to}} WHERE id NOT IN (SELECT id from {{pair_re}})");

        ptext.put("poci_to", "ams->rms(机位P表)");
        ptext.put("poci_re", "rms->ams(机位P表)");
        sqlTable.put("poci_to", "RMS_GATE_OCCUPYING_INFO");
        sqlTable.put("poci_re", "RMS_GATE_OCCUPYING_INFO@AMS");
        psql.put("poci_to", "INSERT INTO {{poci_to}} SELECT * from {{poci_re}} WHERE id NOT IN (SELECT id from {{poci_to}})");
        psql.put("poci_re", "INSERT INTO {{poci_re}} SELECT * from {{poci_to}} WHERE id NOT IN (SELECT id from {{poci_re}})");

        ptext.put("checking_to", "ams->rms(值机柜台C表)");
        ptext.put("checking_re", "rms->ams(值机柜台C表)");
        sqlTable.put("checking_to", "RMS_CHECKING_COUNTER_OCI");
        sqlTable.put("checking_re", "RMS_CHECKING_COUNTER_OCI@AMS");
        psql.put("checking_to", "INSERT INTO {{checking_to}} SELECT * from {{checking_re}} WHERE id NOT IN (SELECT id from {{checking_to}})");
        psql.put("checking_re", "INSERT INTO {{checking_re}} SELECT * from {{checking_to}} WHERE id NOT IN (SELECT id from {{checking_re}})");

        ptext.put("boarding_to", "ams->rms(登机口C表)");
        ptext.put("boarding_re", "rms->ams(登机口C表)");
        sqlTable.put("boarding_to", "RMS_BOARDING_GATE_OCI");
        sqlTable.put("boarding_re", "RMS_BOARDING_GATE_OCI@AMS");
        psql.put("boarding_to", "INSERT INTO {{boarding_to}} SELECT * from {{boarding_re}} WHERE id NOT IN (SELECT id from {{boarding_to}})");
        psql.put("boarding_re", "INSERT INTO {{boarding_re}} SELECT * from {{boarding_to}} WHERE id NOT IN (SELECT id from {{boarding_re}})");

        ptext.put("arrival_to", "ams->rms(到港门J表)");
        ptext.put("arrival_re", "rms->ams(到港门J表)");
        sqlTable.put("arrival_to", "RMS_ARRIVAL_GATE_OCI");
        sqlTable.put("arrival_re", "RMS_ARRIVAL_GATE_OCI@AMS");
        psql.put("arrival_to", "INSERT INTO {{arrival_to}} SELECT * from {{arrival_re}} WHERE id NOT IN (SELECT id from {{arrival_to}})");
        psql.put("arrival_re", "INSERT INTO {{arrival_re}} SELECT * from {{arrival_to}} WHERE id NOT IN (SELECT id from {{arrival_re}})");

        ptext.put("carousel_to", "ams->rms(行李转盘J表)");
        ptext.put("carousel_re", "rms->ams(行李转盘J表)");
        sqlTable.put("carousel_to", "RMS_CAROUSEL_OCI");
        sqlTable.put("carousel_re", "RMS_CAROUSEL_OCI@AMS");
        psql.put("carousel_to", "INSERT INTO {{carousel_to}} SELECT * from {{carousel_re}} WHERE id NOT IN (SELECT id from {{carousel_to}})");
        psql.put("carousel_re", "INSERT INTO {{carousel_re}} SELECT * from {{carousel_to}} WHERE id NOT IN (SELECT id from {{carousel_re}})");

        ptext.put("slide_to", "ams->rms(滑槽C表)");
        ptext.put("slide_re", "rms->ams(滑槽C表)");
        sqlTable.put("slide_to", "RMS_SLIDE_COAST_OCI");
        sqlTable.put("slide_re", "RMS_SLIDE_COAST_OCI@AMS");
        psql.put("slide_to", "INSERT INTO {{slide_to}} SELECT * from {{slide_re}} WHERE id NOT IN (SELECT id from {{slide_re}})");
        psql.put("slide_re", "INSERT INTO {{slide_re}} SELECT * from {{slide_to}} WHERE id NOT IN (SELECT id from {{slide_to}})");

        ptext.put("departure_to", "ams->rms(候机厅C表)");
        ptext.put("departure_re", "rms->ams(候机厅C表)");
        sqlTable.put("departure_to", "RMS_DEPARTURE_HALL_OCI");
        sqlTable.put("departure_re", "RMS_DEPARTURE_HALL_OCI@AMS");
        psql.put("departure_to", "INSERT INTO {{departure_to}} SELECT * from {{departure_re}} WHERE id NOT IN (SELECT id from {{departure_to}})");
        psql.put("departure_re", "INSERT INTO {{departure_re}} SELECT * from {{departure_to}} WHERE id NOT IN (SELECT id from {{departure_re}})");

        ptext.put("security_to", "ams->rms(安检口C表)");
        ptext.put("security_re", "rms->ams(安检口C表)");
        sqlTable.put("security_to", "RMS_SECURITY_CHECKIN_OCI");
        sqlTable.put("security_re", "RMS_SECURITY_CHECKIN_OCI@AMS");
        psql.put("security_to", "INSERT INTO {{security_to}} SELECT * from {{security_re}} WHERE id NOT IN (SELECT id from {{security_to}})");
        psql.put("security_re", "INSERT INTO {{security_re}} SELECT * from {{security_to}} WHERE id NOT IN (SELECT id from {{security_re}})");

        ptext2.put("all_to", "ams->rms(同步所有动态、配对表)");
        ptext2.put("all_re", "rms->ams(同步所有占用表)");
        sqlTable.put("all_to", "call");
        sqlTable.put("all_re", "call");
        psql.put("all_to", "call p_dynamic_ams_to_rms()");
        psql.put("all_re", "call p_oci_rms_to_ams()");

        ptext2.put("clear_oci_ams", "ams(清除所有不匹配的占用数据)");
        ptext2.put("clear_oci_rms", "rms(清除所有不匹配的占用数据)");
        sqlTable.put("clear_oci_ams", "call");
        sqlTable.put("clear_oci_rms", "call");
        psql.put("clear_oci_ams", "call p_clear_err_oci('ams')");
        psql.put("clear_oci_rms", "call p_clear_err_oci('rms')");

        ptext2.put("clear_dist_oci_ams", "ams(删除所有重复的占用数据)");
        ptext2.put("clear_dist_oci_rms", "rms(删除所有重复的占用数据)");
        sqlTable.put("clear_dist_oci_ams", "call");
        sqlTable.put("clear_dist_oci_rms", "call");
        psql.put("clear_dist_oci_ams", "call p_clear_dist_err_oci('ams')");
        psql.put("clear_dist_oci_rms", "call p_clear_dist_err_oci('rms')");
    }

    @ModelAttribute
    public Test get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return testService.get(id);
        } else {
            return new Test();
        }
    }

    /**
     * 显示列表页
     *
     * @param test
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("test:test:view")
    @RequestMapping(value = {"list", ""})
    public String list(Test test, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "modules/test/testList";
    }

    /**
     * 获取硕正列表数据（JSON）
     *
     * @param test
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("test:test:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<Test> listData(Test test, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();
        if (!user.isAdmin()) {
            test.setCreateBy(user);
        }
        Page<Test> page = testService.findPage(new Page<Test>(request, response), test);
        return page;
    }

    /**
     * 新建或修改表单
     *
     * @param test
     * @param model
     * @return
     */
    @RequiresPermissions("test:test:view")
    @RequestMapping(value = "form")
    public String form(Test test, Model model) {
        model.addAttribute("test", test);
        return "modules/test/testForm";
    }

    /**
     * 表单保存方法
     *
     * @param test
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("test:test:edit")
    @RequestMapping(value = "save")
    public String save(Test test, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, test)) {
            return form(test, model);
        }
//		testService.save(test);
        addMessage(redirectAttributes, "保存测试'" + test.getName() + "'成功");
        return "redirect:" + adminPath + "/test/test/?repage";
    }

    /**
     * 删除数据方法
     *
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("test:test:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(Test test, RedirectAttributes redirectAttributes) {
//		testService.delete(test);
//		addMessage(redirectAttributes, "删除测试成功");
//		return "redirect:" + adminPath + "/test/test/?repage";
        return "true";
    }

    @RequiresPermissions("test:test:view")
    @RequestMapping(value = "inout")
    @ResponseBody
    public String flightDynamicInoutType() {
        FlightDynamic flightDynamic = flightDynamicService.get("1de081b5-a614-4a89-b2ca-5e07ed80c1c6");
        Map<String, Object> result = flightDynamicService.queryInOutTypeByDynamic(flightDynamic);
        return "true";
    }


    //------------------------------------- begin
    @RequiresPermissions("sys:ociSync:view")
    @RequestMapping(value = "findOciData")
    @ResponseBody
    public Map findOciData() {
        Map<String, Object> result = new HashMap();
        result.put("rms", jdbcTemplate.queryForMap(StringUtils.tplMap(findOciDataSql, sqlTable)));
        result.put("ams", jdbcTemplate.queryForMap(StringUtils.tplMap(findOciDataSql.replace("_to", "_re"), sqlTable)));
        return result;
    }

    @RequiresPermissions("sys:ociSync:edit")
    @RequestMapping("updateSql")
    @ResponseBody
    public Object updateSql(String params) {
        if (StringUtils.isNotBlank(params)) {
            if (StringUtils.equals("call", sqlTable.get(params))) { //存储过程调用
                jdbcTemplate.execute(psql.get(params));
                return 1;
            } else {
                String sql = StringUtils.tplMap(psql.get(params), sqlTable);
                if (StringUtils.isNotBlank(sql))
                    return jdbcTemplate.update(sql);
            }
        }
        return -1;
    }

    @RequiresPermissions("sys:ociSync:view")
    @RequestMapping("ociSync")
    public String ociSync(Model model) {
        model.addAttribute("attrMap", ptext);
        model.addAttribute("attrMap2", ptext2);
        return "modules/test/ociSync";
    }
}

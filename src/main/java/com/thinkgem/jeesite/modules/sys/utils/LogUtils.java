/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.ams.entity.FlightDynamic;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.rms.entity.FlightPlanPair;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.method.HandlerMethod;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.dao.LogDao;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2014-11-7
 */
public class LogUtils {
	
	public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";
	
	private static LogDao logDao = SpringContextHolder.getBean(LogDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, String title){
		if(title!=null && title.length()>2000){
			StringBuffer msg = new StringBuffer(title);
			for (int i = 0; i < Math.ceil(msg.length()/2000) ; i++) {
				saveLog(request,msg.substring(2000*i,2000*(i+1)));
			}
		}else {
			saveLog(request, null, null, title);
		}
	}

	/**
	 * 保存日志 后清除 日志内容返回给前台
	 */
	public static void saveLogToMsg(HttpServletRequest request, Message message){
		if(message.isSuccess()) {
			saveLog(request,message.getMessage());
			message.setMessage("操作成功！");
		}
	}

	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
		User user = UserUtils.getUser();
		if (user != null && user.getId() != null){
			Log log = new Log();
			log.setTitle(title);
			log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
			log.setRemoteAddr(StringUtils.getRemoteAddr(request));
			log.setUserAgent(request.getHeader("user-agent"));
			log.setRequestUri(request.getRequestURI());
			log.setParams(request.getParameterMap());
			log.setMethod(request.getMethod());
			// 异步保存日志
			new SaveLogThread(log, handler, ex).start();
		}
	}

	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{
		
		private Log log;
		private Object handler;
		private Exception ex;
		
		public SaveLogThread(Log log, Object handler, Exception ex){
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.ex = ex;
		}
		
		@Override
		public void run() {
			// 获取日志标题
			if (StringUtils.isBlank(log.getTitle())){
				String permission = "";
				if (handler instanceof HandlerMethod){
					Method m = ((HandlerMethod)handler).getMethod();
					RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
					permission = (rp != null ? StringUtils.join(rp.value(), ",") : "");
				}
				log.setTitle(getMenuNamePath(log.getRequestUri(), permission));
			}
			// 如果有异常，设置异常信息
			log.setException(Exceptions.getStackTraceAsString(ex));
			// 如果无标题并无异常日志，则不保存信息
			if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getException())){
				return;
			}
			// 保存日志信息
			log.preInsert();
			logDao.insert(log);
		}
	}

	/**
	 * 添加日志  类型设置为3 用于aop
	 */
	public static Log toLog(HttpServletRequest request,String title){
		User user = UserUtils.getUser();
		if (user != null && user.getId() != null){
			Log log = new Log();
			log.setTitle(title);
			log.setType(Log.TYPE_CHECKASP);
			log.setRemoteAddr(StringUtils.getRemoteAddr(request));
			log.setUserAgent(request.getHeader("user-agent"));
			log.setRequestUri(request.getRequestURI());
			log.setParams(request.getParameterMap());
			log.setMethod(request.getMethod());
			return log;
		}
		return null;
	}

	/**
	 * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
	 */
	public static String getMenuNamePath(String requestUri, String permission){
		String href = StringUtils.substringAfter(requestUri, Global.getAdminPath());
		@SuppressWarnings("unchecked")
		Map<String, String> menuMap = (Map<String, String>)CacheUtils.get(CACHE_MENU_NAME_PATH_MAP);
		if (menuMap == null){
			menuMap = Maps.newHashMap();
			List<Menu> menuList = menuDao.findAllList(new Menu());
			for (Menu menu : menuList){
				// 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
				String namePath = "";
				if (menu.getParentIds() != null){
					List<String> namePathList = Lists.newArrayList();
					for (String id : StringUtils.split(menu.getParentIds(), ",")){
						if (Menu.getRootId().equals(id)){
							continue; // 过滤跟节点
						}
						for (Menu m : menuList){
							if (m.getId().equals(id)){
								namePathList.add(m.getName());
								break;
							}
						}
					}
					namePathList.add(menu.getName());
					namePath = StringUtils.join(namePathList, "-");
				}
				// 设置菜单名称路径
				if (StringUtils.isNotBlank(menu.getHref())){
					menuMap.put(menu.getHref(), namePath);
				}else if (StringUtils.isNotBlank(menu.getPermission())){
					for (String p : StringUtils.split(menu.getPermission())){
						menuMap.put(p, namePath);
					}
				}
				
			}
			CacheUtils.put(CACHE_MENU_NAME_PATH_MAP, menuMap);
		}
		String menuNamePath = menuMap.get(href);
		if (menuNamePath == null){
			for (String p : StringUtils.split(permission)){
				menuNamePath = menuMap.get(p);
				if (StringUtils.isNotBlank(menuNamePath)){
					break;
				}
			}
			if (menuNamePath == null){
				return "";
			}
		}
		return menuNamePath;
	}

	/**
	 *  日志类型转换(将CheckAspect记录的日志转为常规日志)
	 * @param logId
	 * @param msg
	 */
	public static void type2Save(String logId,String msg){
		new Thread(()->{
			Log log = logDao.get(logId);
			if (log != null) {
				log.setTitle(msg);
				log.setType("1");
				logDao.update(log);
			}
		}).start();
	}

	/**
	 * 定制化航班共用消息
	 *
	 * @param flightDynamic
	 * @return
	 */
	public static String msgFlightDynamic(FlightDynamic flightDynamic) {
		StringBuffer msg = new StringBuffer();
		if (flightDynamic != null) {
			msg.append("计划日期：").append(DateUtils.formatDate(flightDynamic.getPlanDate()));
			msg.append(",进出港：").append("J".equals(flightDynamic.getInoutTypeCode()) ? "进港" : "出港");
			msg.append(",航班号：").append(flightDynamic.getFlightCompanyCode() + flightDynamic.getFlightNum());
			return msg.toString();
		}
		return "";
	}

	public static String msgPair(FlightPlanPair flightPlanPair) {
		StringBuffer msg = new StringBuffer();
		if (flightPlanPair != null) {
			if (flightPlanPair.getFlightDynimicId() != null && flightPlanPair.getFlightDynimicId2() != null) {
				msg.append("计划日期：").append(DateUtils.formatDate(flightPlanPair.getPlanDate()));
				msg.append(",进港航班号：").append(flightPlanPair.getFlightCompanyCode() + flightPlanPair.getFlightNum());
				msg.append(",出港航班号：").append(flightPlanPair.getFlightCompanyCode2() + flightPlanPair.getFlightNum2());
				return msg.toString();
			}
			if (flightPlanPair.getFlightDynimicId() != null) {
				msg.append("计划日期：").append(DateUtils.formatDate(flightPlanPair.getPlanDate()));
				msg.append(",进港航班号：").append(flightPlanPair.getFlightCompanyCode() + flightPlanPair.getFlightNum());
				return msg.toString();
			}
			if (flightPlanPair.getFlightDynimicId2() != null) {
				msg.append("计划日期：").append(DateUtils.formatDate(flightPlanPair.getPlanDate()));
				msg.append(",出港航班号：").append(flightPlanPair.getFlightCompanyCode2() + flightPlanPair.getFlightNum2());
				return msg.toString();
			}
		}
		return "";
	}
	
}

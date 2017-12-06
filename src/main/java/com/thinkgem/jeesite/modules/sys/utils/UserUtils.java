/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.MessageWrapper;
import com.thinkgem.jeesite.modules.ams.entity.common.MenuTreeWrapper;
import com.thinkgem.jeesite.modules.ams.service.MessageService;
import com.thinkgem.jeesite.modules.sys.dao.*;
import com.thinkgem.jeesite.modules.sys.entity.*;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 用户工具类
 *
 * @author ThinkGem
 * @version 2013-12-05
 */
public class UserUtils {

    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_LOGIN_NAME_ = "ln";
    public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_AREA_LIST = "areaList";
    public static final String CACHE_OFFICE_LIST = "officeList";
    public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
    public static final String CACHE_MENU_TREE_LIST = "menuTreeWrapperList";
    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
    private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
    private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
    private static MessageService messageService = SpringContextHolder.getBean(MessageService.class);

    private static SessionDAO sessionDAO = SpringContextHolder.getBean(SessionDAO.class);

    /**
     * 根据ID获取用户
     *
     * @param id
     * @return 取不到返回null
     */
    public static User get(String id) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user == null) {
            user = userDao.get(id);
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return 取不到返回null
     */
    public static User getByLoginName(String loginName) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
        if (user == null) {
            user = userDao.getByLoginName(new User(null, loginName));
            if (user == null) {
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache() {
        removeCache(CACHE_ROLE_LIST);
        removeCache(CACHE_MENU_LIST);
        removeCache(CACHE_AREA_LIST);
        removeCache(CACHE_OFFICE_LIST);
        removeCache(CACHE_OFFICE_ALL_LIST);
        UserUtils.clearCache(getUser());
    }

    /**
     * 清除指定用户缓存
     *
     * @param user
     */
    public static void clearCache(User user) {
        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
        if (user.getOffice() != null && user.getOffice().getId() != null) {
            CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
        }
    }

    /**
     * 获取当前用户
     *
     * @return 取不到返回 new User()
     */
    public static User getUser() {
        Principal principal = getPrincipal();
        if (principal != null) {
            User user = get(principal.getId());
            if (user != null) {
                return user;
            }
            return new User();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new User();
    }

    /**
     * 获取当前用户被授权的航班公司列表
     */
    public static List<String> getCompanyIds() {
        List<String> companyIds = Lists.newArrayList();
        User user = UserUtils.getUser();
        if (user.getId().isEmpty() || user.getRoleIdList().isEmpty()) return companyIds;

        for (Role role : user.getRoleList()) {
            if (role.getCompanyIdList().isEmpty()) continue;
            role.getCompanyIdList().forEach(loop_entity -> companyIds.add(loop_entity));
        }

        return companyIds;
    }

    /**
     * 获取当前用户被授权的服务提供者列表
     */
    public static List<String> getServiceProviderIds() {
        List<String> serviceProviderIds = Lists.newArrayList();
        User user = UserUtils.getUser();
        if (user.getId().isEmpty() || user.getRoleIdList().isEmpty()) return serviceProviderIds;

        for (Role role : user.getRoleList()) {
            if (role.getServiceIdList().isEmpty()) continue;
            role.getServiceIdList().forEach(loop_entity -> serviceProviderIds.add(loop_entity));
        }

        return serviceProviderIds;
    }

    /**
     * 获取当前用户角色列表
     *
     * @return
     */
    public static List<Role> getRoleList() {
        @SuppressWarnings("unchecked")
        List<Role> roleList = (List<Role>) getCache(CACHE_ROLE_LIST);
        if (roleList == null) {
            User user = getUser();
            if (user.isAdmin()) {
                roleList = roleDao.findAllList(new Role());
            } else {
                Role role = new Role();
                role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
                roleList = roleDao.findList(role);
            }
            putCache(CACHE_ROLE_LIST, roleList);
        }
        return roleList;
    }

    /**
     * 获取当前用户授权菜单
     *
     * @return
     */
    public static List<Menu> getMenuList() {
        removeCache(CACHE_MENU_LIST);
        List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
        if (menuList == null) {
            User user = getUser();
            if (user.isAdmin()) {
                menuList = menuDao.findAllList(new Menu());
            } else {
                Menu m = new Menu();
                m.setUserId(user.getId());
                menuList = menuDao.findByUserId(m);
            }
            putCache(CACHE_MENU_LIST, menuList);
        }
        return menuList;
    }

    /**
     * 获取菜单树结构
     *
     * @return
     */
    public static MenuTreeWrapper getMenuTree(String menuId) {
        MenuTreeWrapper root = new MenuTreeWrapper();
        Menu manMenu = menuDao.get((StringUtils.isNotBlank(menuId) ? menuId : "1"));
        root.setMenu(manMenu);
        root.setChindren(getSubMenu(root));
        return root;
    }

    private static List<MenuTreeWrapper> getSubMenu(MenuTreeWrapper menuTreeWrapper) {
        //cache
        try {
            Object object = getCache(CACHE_MENU_TREE_LIST + menuTreeWrapper.getMenu().getId());
            if (object != null)
                return (List<MenuTreeWrapper>) object;
        } catch (Exception e) {
            clearCache();
        }


        List<MenuTreeWrapper> menuTreeWrappers = new ArrayList<>();
        List<Menu> menus = menuDao.findList(menuTreeWrapper.getMenu());
        for (Menu tempMenu : menus) {
            MenuTreeWrapper temp = new MenuTreeWrapper();
            temp.setMenu(tempMenu);
            temp.setChindren(getSubMenu(temp));
            menuTreeWrappers.add(temp);
        }
        menuTreeWrapper.setChindren(menuTreeWrappers);

        try {
            //add cache
            putCache(CACHE_MENU_TREE_LIST + menuTreeWrapper.getMenu().getId(), menuTreeWrappers);
        } catch (Exception e) {
            clearCache();
        }
        return menuTreeWrappers;
    }

    /**
     * 获取当前用户授权的区域
     *
     * @return
     */
    public static List<Area> getAreaList() {
        @SuppressWarnings("unchecked")
        List<Area> areaList = (List<Area>) getCache(CACHE_AREA_LIST);
        if (areaList == null) {
            areaList = areaDao.findAllList(new Area());
            putCache(CACHE_AREA_LIST, areaList);
        }
        return areaList;
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<Office> getOfficeList() {
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_LIST);
        if (officeList == null) {
            User user = getUser();
            if (user.isAdmin()) {
                officeList = officeDao.findAllList(new Office());
            } else {
                Office office = new Office();
                office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
                officeList = officeDao.findList(office);
            }
            putCache(CACHE_OFFICE_LIST, officeList);
        }
        return officeList;
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<Office> getOfficeAllList() {
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_ALL_LIST);
        if (officeList == null) {
            officeList = officeDao.findAllList(new Office());
        }
        return officeList;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
//			subject.logout();
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
//			subject.logout();
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    // ============== User Cache ==============

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
        Object obj = getSession().getAttribute(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
//		getCacheMap().remove(key);
        getSession().removeAttribute(key);
    }

//	public static Map<String, Object> getCacheMap(){
//		Principal principal = getPrincipal();
//		if(principal!=null){
//			return principal.getCacheMap();
//		}
//		return new HashMap<String, Object>();
//	}

    public static List<User> getAllUsers() {
        List<User> cacheList = (List<User>) CacheUtils.get(UserUtils.class.getName() + "allUsers");
        if (cacheList == null || cacheList.size() == 0) {
            List<User> result = userDao.findAllList(new User());
            CacheUtils.put(UserUtils.class.getName() + "allUsers", result);
            return result;
        } else {
            return cacheList;
        }

    }

    public static List<MessageWrapper> getUnreadMessages() {
        return messageService.getUnreadMessages(getUser());
    }

    // 删除登录用户的角色缓存（通过shiro 获取当前登录的用户id） wjp_2017年1月13日18时14分
    public static void removeCacheAllUser(String key, String... keys) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        Iterator it = sessions.iterator();
        while (it.hasNext()) {
            Session session = (Session) it.next();
            Object o = session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
            if (o != null) {
                session.removeAttribute(key + o.toString());
                if (keys != null && keys.length > 0) {
                    for (String s : keys) {
                        session.removeAttribute(s + o.toString());
                    }
                }
            }
        }
    }

    // 删除登录用户的菜单缓存
    public static void removeCacheAll(String key) {
//		removeCacheAllUser(key,CACHE_MENU_LIST,CACHE_COMPANY_LIST,CACHE_MENU_TREE_LIST);
        removeCacheAllUser(key, CACHE_MENU_LIST);
    }

    /**
     * 获取当前用户有权限访问的部门
     *
     * @return
     */
    public static List<Office> getAllOffices() {
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_LIST + "allOffices");
        if (officeList == null) {
            officeList = officeDao.findAllList(new Office());
            putCache(CACHE_OFFICE_LIST + "allOffices", officeList);
        }
        return officeList;
    }

    //判断是否有权查看审核数据
    public static Boolean dataScopeFilterCheckAsp(User createBy) {

        // 如果是超级管理员，则不过滤数据
        if (UserUtils.getUser().isAdmin()) {
            return true;
        }

//        createBy = userDao.get(createBy.getId());
        Boolean flag = false;
        for (Menu menu : UserUtils.getMenuList()) {
            if (StringUtils.isNotBlank(menu.getPermission()) && menu.getPermission().indexOf("ams:resource:check") >= 0) {
                flag = true;
                break;
            }
        }

        if (flag) {
            List<String> companyList = new ArrayList<>();
            List<String> serviceList = new ArrayList<>();
            UserUtils.getCompanyIdAndserviceId(UserUtils.getRoleList(), companyList, serviceList);

            List<String> companyList2 = new ArrayList<>();
            List<String> serviceList2 = new ArrayList<>();
            UserUtils.getCompanyIdAndserviceId(getRoleList(createBy), companyList2, serviceList2);

            companyList.retainAll(companyList2);
            serviceList.retainAll(serviceList2);

            if (companyList.size() > 0 || serviceList.size() > 0) {
                return true;
            }
        }

        return false;
    }

    //获取角色数据中的航空公司及代理人
    public static void getCompanyIdAndserviceId(List<Role> roleList, List<String> companyInfoIdList, List<String> serviceIdList) {
        for (Role role : roleList) {
            if (!Collections3.isEmpty(role.getCompanyIdList())) {
                companyInfoIdList.removeAll(role.getCompanyIdList());
                companyInfoIdList.addAll(role.getCompanyIdList());
            }
            if (!Collections3.isEmpty(role.getServiceIdList())) {
                serviceIdList.removeAll(role.getServiceIdList());
                serviceIdList.addAll(role.getServiceIdList());
            }
        }
    }

    public static List<Role> getRoleList(User user) {
        Role role = new Role();
//        role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "o", "u"));
        role.setUser(user);
        return roleDao.findList(role);
    }

    //判断是否有权查看审核数据
    public static Boolean dataScopeFilterAocThirdParty(String flightCompanyCode) {

        // 如果是超级管理员，则不过滤数据
        if (UserUtils.getUser().isAdmin()) {
            return true;
        }

        //航空公司为空，直接过滤
        if (StringUtils.isBlank(flightCompanyCode)) return false;

        Boolean flag = false;
        for (Menu menu : UserUtils.getMenuList()) {
            if (StringUtils.isNotBlank(menu.getPermission()) && menu.getPermission().indexOf("ams:flightDynamicManager:aocThirdParty") >= 0) {
                flag = true;
                break;
            }
        }

        if (flag) {
            List<String> companyList = new ArrayList<>();
            UserUtils.getCompanyCode(UserUtils.getRoleList(), companyList);

            if (companyList.contains(flightCompanyCode)) {
                return true;
            }
        }

        return false;
    }

    public static void getCompanyCode(List<Role> roleList, List<String> companyInfoIdList) {
        for (Role role : roleList) {
            if (!Collections3.isEmpty(role.getCompanyIdList())) {
                companyInfoIdList.removeAll(role.getCompanyIdList());
                companyInfoIdList.addAll(role.getCompanyIdList());
            }
        }
    }
}

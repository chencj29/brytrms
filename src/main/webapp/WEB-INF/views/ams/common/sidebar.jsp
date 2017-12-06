<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="nav navbar-nav side-nav" id="sidebar">
    <li class="navigation" id="navigation">
        <ul class="menu" id="left_menu">
            <%-- left menu --%>
            <c:set var="menuWrapper" value="${fns:getMenuTree('1')}"/>
            <c:set var="menuList" value="${menuWrapper.chindren}"/>
            <c:forEach items="${menuList}" var="menuWrapperTemp" varStatus="idxStatus">
                <c:set var="menu" value="${menuWrapperTemp.menu}"/>
                <c:set var="children" value="${menuWrapperTemp.chindren}"/>
                <c:if test="${menu.parent.id eq '1'&& menu.isShow eq '1'}">
                    <li class="dropdown">
                        <c:if test="${empty menu.href}">
                            <a class="dropdown-toggle" href="javascript:void(0);"
                               data-id="${ctx}${menu.href}" data-toggle="dropdown">
                                <i class="fa fa-calendar"></i>&nbsp;${menu.name}<b class="fa fa-plus dropdown-plus"></b>
                            </a>
                        </c:if>
                        <c:if test="${not empty menu.href}">
                            <a class="dropdown-toggle" href="javascript:void(0);"
                               data-id="${ctx}${menu.href}" data-toggle="dropdown">
                                <i class="fa fa-calendar"></i>&nbsp;${menu.name}<b class="fa fa-plus dropdown-plus"></b>
                            </a>
                        </c:if>

                        <!-- submenu -->
                        <c:if test="${not empty children}">
                            <ul class="dropdown-menu">
                                <c:forEach items="${children}" var="temp">
                                    <c:if test="${temp.menu.isShow eq '1'}">
                                        <li>
                                            <a href="${ctx}${temp.menu.href}">
                                                <i class="fa fa-caret-right"></i>&nbsp;${temp.menu.name}
                                            </a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </li>
                </c:if>
            </c:forEach>
            <%-- left menu end --%>
            <%--<li class="dropdown open">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-calendar"></i>&nbsp;航班计划管理<b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="main.jsp" class="item-current">
                            <i class="fa fa-caret-right"></i>&nbsp;航班计划
                        </a>
                    </li>
                    <li>
                        <a href="./longTermPlan.html">
                            <i class="fa fa-caret-right"></i>&nbsp;长期计划
                        </a>
                    </li>
                    <li>
                        <a href="./shareFlight.html">
                            <i class="fa fa-caret-right"></i>&nbsp;共享航班
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-paste"></i>&nbsp;航班动态管理<b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="./flightDynamic.html">
                            <i class="fa fa-caret-right"></i>&nbsp;航班动态初始
                        </a>
                    </li>
                    <li>
                        <a href="./flightDynamicPublish.html">
                            <i class="fa fa-caret-right"></i>&nbsp;航班动态发布
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-th"></i>&nbsp;基础数据
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="./airlineInfo.html">
                            <i class="fa fa-caret-right"></i>&nbsp;航班公司信息管理
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-th"></i>&nbsp;统计查询
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="./flightStatistics.html">
                            <i class="fa fa-caret-right"></i>&nbsp;航班动态统计查询
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-cog"></i>&nbsp;系统管理<b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="./messageCenter.html">
                            <i class="fa fa-caret-right"></i>&nbsp;消息中心
                        </a>
                    </li>
                </ul>
            </li>--%>
        </ul>
    </li>
</ul>
<script>
    $(function () {
        selectDropdownMenu();
    });


    // 菜单选中测试
    function selectDropdownMenu() {
        try {
            var href = location.href;
            href = "/a" + href.substring(href.indexOf("/rms"));
            //父级选中
            //$("a[data-id='" + href + "']").dropdown("toggle");
            //子菜单选中
            $("ul.dropdown-menu a[href='" + href + "']").addClass("item-current")
                    .parents("ul.dropdown-menu").siblings("a.dropdown-toggle").dropdown("toggle");
        } catch (e) {

        }
    }
</script>
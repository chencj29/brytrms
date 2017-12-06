<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>${fns:getConfig('productName')}</title>
    <script src="${ctxAssets}/tools/js/browser.js"></script>
    <script src="${ctxAssets}/tools/js/left_shortcut_menu.js"></script>
    <script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
    <script src="${ctxAssets}/scripts/layer/src/layer.js"></script>
    <style type="text/css">
        #rtMessageWindow {
            width: 500px;
            height: 300px;
            padding: 10px;
            line-height: 15px;
        }

        #rtMessageWindow p span {
            font-weight: bold !important;
            color: green !important;
            margin-right: 10px !important;
        }

        .messager-body {
            padding: 0px 10px;
        }

        a.cyan {
            background-color: rgba(34, 190, 239, 0.1);
        }

        a.message-info {
            vertical-align: top;
            display: inline-block;
            font-size: 11px;
            margin-left: 5px;
            width: 265px;
        }

        a .message-info .message-content {
            white-space: normal;
            margin-top: 5px;
            color: #a4a4a4;
        }

        .message-content {
            white-space: normal;
            margin-top: 5px;
            color: #a4a4a4;
        }

        .sender {
            font-weight: 700;
        }
    </style>
</head>
<body>
<!-- 顶部-->

<div region="north" border="false" title="" style="BACKGROUND: #A8D7E9; height: 100px; padding: 1px; overflow: hidden;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left"
                style="vertical-align: middle;font-size: 18px;font-family:'微软雅黑';color: #4F8CD0;font-weight: 900;padding: 10px 0px 17px 10px;white-space:nowrap;">
                机场资源管理系统
            </td>
            <td align="right" nowrap>
                <table border="0" cellpadding="0" cellspacing="0">
                    <tr style="height: 25px;" align="right">
                        <td style="" colspan="2">
                            <div style="background: url(${ctxAssets}/login/images/top_bg.jpg) no-repeat right center; float: right;">
                                <div style="float: left; line-height: 20px; margin-left: 2px;">
                                    <a href="#" onclick="showMsgPanel(event)">
                                        <marquee direction="left" align="center" id="msgText"
                                                 style="width: 200px;color: yellow;"></marquee>
                                    </a>
                                </div>
                                <div style="float: left; line-height: 25px; margin-left: 70px;">
                                    <%--<!--同步管理菜单-->--%>
                                    <%--<span style="color: #FFFFFF">--%>
                                    <%--<a href="javascript:void(0)" onclick="oicSync()" style="color:#fafafa">同步管理</a>--%>
                                    <%--</span>--%>
                                    <shiro:hasPermission name="ams:flightDynamicManager:aocThirdParty">
                                        <!--审批菜单-->
                                        <span style="color: #FFFFFF">
                                            <a href="javascript:void(0)" onclick="openFlightAocTP()"
                                               style="color:#fafafa">保障进程审核</a>
                                        </span>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="ams:resource:check">
                                        <span style="color: #FFFFFF">
                                                <a href="javascript:void(0)" onclick="openFlightCheck()"
                                                   style="color:#1555ec">资源分配审核</a>
                                        </span>
                                    </shiro:hasPermission>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <span style="color: #386780" id="userInfo" class="dropdown">
                                            <span style="color: #386780">
                                                用户	：
                                            </span>
                                            <span style="color: #FFFFFF">${fns:getUser().name}</span>&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
                                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">岗位：
                                                <span style="color: #FFFFFF">${fns:getUser().office.name}</span>&nbsp;
                                            </a>
                                        </span>
                                </div>
                                <div style="float: left; margin-left: 18px;">
                                    <div style="right: 0px; bottom: 0px;">
                                        <a href="javascript:void(0);" class="easyui-menubutton"
                                           menu="#layout_north_kzmbMenu" iconCls="icon-comturn" style="color: #FFFFFF">
                                            控制面版
                                        </a>
                                        &nbsp;&nbsp;
                                        <c:set var="unreadMessages" value="${fns:getUnreadMessages()}"></c:set>
                                        <a href="javascript:void(0);" class="easyui-menubutton" id="msgMenu"
                                           menu="#layout_north_msgMenu" iconCls="icon-email" style="color: #FFFFFF">
                                            您有<strong><span id="msgLength">${fn:length(unreadMessages)}</span></strong>条新消息
                                        </a>
                                        &nbsp;&nbsp;
                                        <a href="javascript:void(0);" class="easyui-button"
                                           onclick="exit('${ctx}/logout','退出登录',1);" style="color: #FFFFFF">
                                            退出
                                        </a>
                                        &nbsp;&nbsp;
                                    </div>
                                    <div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
                                        <div onclick="showRtMessageDialog()">航班实时消息</div>
                                        <div onclick="addTab('个人信息','${ctx}/sys/user/info','default')">个人信息</div>
                                        <div onclick="addTab('修改密码','${ctx}/sys/user/modifyPwd','default')">修改密码</div>
                                        <div onclick="showOrHidesDialog()"><span id="showOrHidesId">屏蔽</span>"弹出消息"
                                        </div>
                                    </div>
                                    <div id="layout_north_zxMenu" style="width: 100px; display: none;">
                                        <div onclick="exit('${ctx}/logout','退出登录',1);">退出</div>
                                    </div>
                                    <div id="layout_north_msgMenu" style="width: 500px; display: none;">
                                        <div data-options="iconCls:'icon-redo'" onclick="send_message()">发送消息</div>
                                        <c:forEach items="${unreadMessages}" var="unread">
                                            <div onclick="get_message('${unread.id}')">
                                                <a class="cyan" href="#">
                                                    <div class="message-info">
                                                        <span class="sender">来自:${unread.send_name}</span>
                                                        <span class="message-content">${unread.message.replace("emergencyPrepare","「应急救援」")}</span>
                                                    </div>
                                                </a>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr style="height: 80px;">
                        <td colspan="2">
                            <ul id="menu" class="shortcut">
                                <c:set var="firstMenu" value="true"/>
                                <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
                                    <c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
                                        <c:if test="${ not empty main_icos[menu.name]}">
                                            <li style='position: relative;' data-id="${menu.id}"><img class='imag1'
                                                                                                      src='${ctxAssets}/login/images/main_icos/${main_icos[menu.name]}.png'/>
                                                <img class='imag2'
                                                     src='${ctxAssets}/login/images/main_icos/${main_icos[menu.name]}_up.png'
                                                     style='display: none;'/>
                                                <div style='
                                                        *margin-left: -70px;
                                                        width: 67px;
                                                        position: absolute;
                                                        top: 40px;
                                                        text-align: center;
                                                        color: #909090;
                                                        font-size: 12px;'>${menu.name}</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${empty main_icos[menu.name]}">
                                            <li style='position: relative;' data-id="${menu.id}"><img class='imag1'
                                                                                                      src='${ctxAssets}/login/images/main_icos/${main_icos['default']}.png'/>
                                                <img class='imag2'
                                                     src='${ctxAssets}/login/images/main_icos/${main_icos['default']}_up.png'
                                                     style='display: none;'/>
                                                <div style='
                                                        *margin-left: -70px;
                                                        width: 67px;
                                                        position: absolute;
                                                        top: 40px;
                                                        text-align: center;
                                                        color: #909090;
                                                        font-size:12px;'>${menu.name}</div>
                                            </li>
                                        </c:if>
                                        <c:if test="${firstMenu}">
                                            <c:set var="firstMenuId" value="${menu.id}"/>
                                        </c:if>
                                        <c:set var="firstMenu" value="false"/>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<!-- 左侧-->
<div data-options="region:'west',title:'导航菜单',split:false, collapsible: false" style="width: 200px; padding: 1px;">
    <ul id="menu_left" class="easyui-tree"></ul>
</div>

<!-- 中间-->
<div id="mainPanle" region="center" style="overflow: hidden;">
    <div id="maintabs" class="easyui-tabs" fit="true" border="false">
    </div>
</div>

<div id="mm" class="easyui-menu" style="width: 150px; display: none;">
    <div id="mm-tabupdate">刷新</div>
    <div id="mm-tabclose">关闭</div>
    <div id="mm-tabcloseall">关闭所有</div>
    <div id="mm-tabcloseother">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div id="mm-tabcloseright">关闭右侧标签</div>
    <div id="mm-tabcloseleft">关闭左侧标签</div>
</div>

<div id="rtMessageWindow" class="easyui-window" title="消息列表" data-options="
    collapsible: false, minimizable: false, maximizable: false, closed: true, draggable: false, resizable: false, modal: true">

</div>

<script type="text/html" id="unreadMessagesTemplate">
    <div>
        <span class="sender">{{send_name}}({{post_date}}):</span>
        <div style="vertical-align: top;display: inline-block;font-size: 21px;margin-left: 5px;" _id={{send_id}}>
            {{message}}
        </div>
    </div>
</script>

<script type="text/html" id="layout_north_msgMenu_tpl">
    <div data-options="iconCls:'icon-redo'" onclick="send_message()">发送消息</div>
    {{each unreadMessages as unread i}}
    <div onclick="get_message('{{unread.id}}')" class="menu-item" style="height: 20px;">
        <div class="menu-text" style="height: 20px; line-height: 20px;">
            <a class="cyan" href="#">
                <div class="message-info">
                    <span class="sender">来自:{{unread.send_name}}</span>
                    <span class="message-content">{{unread.message.replace("emergencyPrepare","「应急救援」")}}</span>
                </div>
            </a>
        </div>
    </div>
    {{/each}}
</script>

<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>

<script type="text/javascript">
    $(function () {

        $('#layout_east_calendar').calendar({
            fit: true, current: new Date(), border: false, onSelect: function (date) {
                $(this).calendar('moveTo', new Date());
            }
        });

        $(".layout-expand").click(function () {
            $('#layout_east_calendar').css("width", "auto");
            $('#layout_east_calendar').parent().css("width", "auto");
            $("#layout_jeecg_onlinePanel").find(".datagrid-view").css("max-height", "200px");
            $("#layout_jeecg_onlinePanel .datagrid-view .datagrid-view2 .datagrid-body").css("max-height", "180px").css("overflow-y", "auto");
        });

        var item = localStorage.getItem("showOrHidesDialog");
        if (!item) {
            $("#showOrHidesId").html("屏蔽")
        } else {
            var itemObj = JSON.parse(item);
            if (!itemObj["flag"]) $("#showOrHidesId").html("屏蔽");
            else $("#showOrHidesId").html("显示");
        }

        //定时清除
        cleartRtMessage();
    });

    var onlineInterval;

    function easyPanelCollapase() {
        window.clearTimeout(onlineInterval);
    }

    function easyPanelExpand() {
        onlineInterval = window.setInterval(function () {
            $('#layout_jeecg_onlineDatagrid').datagrid('load', {});
        }, 1000 * 20);
    }

    function panelCollapase() {
        $(".easyui-layout").layout('collapse', 'north');
    }


    var msgCount = 0;

    function exit(url, content) {
        $.messager.confirm("提示", content, function (r) {
            if (r) {
                window.location = url;
            }
        });
    }

    // 接收航班实时消息　
    let REALTIME_MESSAGE = io.connect(message_socket_server_url, {query: "ns=/dynamic_realtime_message"});

    REALTIME_MESSAGE.on('dynamic_message_dispatcher', (data) => {
        var entity = JSON.parse(data);
        rtMessageStorage.total++;
        rtMessageStorage.items.push(entity);
        sessionStorage.setItem("rtMessage", JSON.stringify(rtMessageStorage));

        var message = "<p class='rtp' style='line-height:20px;'>" + entity.message + " &nbsp;&nbsp;<a onclick='showRtMessageDialog()' style='color:#0e78c9; cursor: pointer;'>点击查看消息列表</a></p>";
        // 在生成之前，将已经存在的面板向上移动

        var currentLooperIndex = 0, totalPanelLength = $(".rtp").length;

        $(".rtp").each(function () {
            var $parent = $(this).parents("div.panel.window");
            $parent.css('bottom', (totalPanelLength - currentLooperIndex) * ($parent.height() + 15));
            currentLooperIndex++;
        });

        var item = localStorage.getItem("showOrHidesDialog");
        if (!item) {
            $.messager.show({title: '消息提示', msg: message, timeout: 8000, showType: 'null', timeout: 6000});
        } else {
            var itemObj = JSON.parse(item);
            if (!itemObj["flag"]) $.messager.show({
                title: '消息提示',
                msg: message,
                timeout: 8000,
                showType: 'null',
                timeout: 6000
            });
        }

        var fmtOccur = moment(entity.occur).format('DD HH:mm:ss'), message = entity.message;
        var hmtlcode = "<p><span>[" + fmtOccur + "]</span>" + message + "</p>";
        $("#rtMessageWindow").prepend(hmtlcode);
    });

    function showRtMessageDialog() {
        var $win = $("#rtMessageWindow").window({title: '航班时实消息', modal: false, collapsible: true, resizable: true});
        $win.window('open');
    }

    //控制航班实时消息弹出
    function showOrHidesDialog() {
        var item = localStorage.getItem("showOrHidesDialog");
        if (item) {
            var itemObj = {};
            try {
                itemObj = JSON.parse(item);
            } catch (e) {
            }
            if (!itemObj["flag"]) {
                $("#showOrHidesId").html("显示");
                itemObj["flag"] = true;//屏蔽
                localStorage.setItem("showOrHidesDialog", JSON.stringify(itemObj));
                showRtMessageDialog();
            } else {
                $("#showOrHidesId").html("屏蔽");
                itemObj["flag"] = false;
                localStorage.setItem("showOrHidesDialog", JSON.stringify(itemObj))
            }
        } else {
            var itemObj = {};
            $("#showOrHidesId").html("显示");
            itemObj["flag"] = true;//屏蔽
            localStorage.setItem("showOrHidesDialog", JSON.stringify(itemObj));
            showRtMessageDialog();
        }
    }

    let rtMessageStorage = {total: 0, items: []}; // 消息体
    if (sessionStorage.getItem("rtMessage")) {
        rtMessageStorage = JSON.parse(sessionStorage.getItem("rtMessage"));

        for (var i = rtMessageStorage.total - 1, len = 0; i >= len; i--) {
            const item = rtMessageStorage.items[i];
            var fmtOccur = moment(item.occur).format('DD HH:mm:ss'), message = item.message;
            var hmtlcode = "<p><span>[" + fmtOccur + "]</span>" + message + "</p>";
            $("#rtMessageWindow").append(hmtlcode);
        }
    }

    let DYNAMIC_INIT_SOCKET = io.connect(message_socket_server_url, {query: 'ns=/global/dynamics'});
    // 接收航班初始化的消息
    DYNAMIC_INIT_SOCKET.on('flightDynamicInit', function (resp) {
        var data = JSON.parse(resp);

        var currentSelectedTab = "", rsTabNames = [];
        // 得到当前用户打开的资源分配标签
        _.each($("ul.tabs .tabs-title"), function (tab) {
            if ($(tab).parents('li').hasClass('tabs-selected')) currentSelectedTab = $(tab).text().trim();
            if ($(tab).text().trim().endsWith('分配')) rsTabNames.push($(tab).text().trim());
        });

        // 根据用户打开的标签发送相应的消息，如果有选中的资源标签，优先处理
        if (_.isEmpty(rsTabNames)) return;

        // 如果当前选中的标签在资源分配标签组中，优先处理
        if (!_.isEmpty(currentSelectedTab) && _.contains(rsTabNames, currentSelectedTab) && rsTabNames.length > 1) rsTabNames = _.without(rsTabNames, currentSelectedTab).reverse();
        else {
            currentSelectedTab = rsTabNames.pop();
            if (!_.isEmpty(rsTabNames)) rsTabNames.reverse(); // 如何当前没有选中的，则按照顺序进行处理
        }

        var currentIframe = $("#maintabs").find('iframe').filter(function () {
            return $(this)[0].contentDocument.title === currentSelectedTab
        })[0];

        if (currentIframe && currentIframe.contentWindow.fnAfterInited) currentIframe.contentWindow.fnAfterInited({
            data: data,
            type: currentSelectedTab.replace('分配', ''),
            restResourceArray: rsTabNames
        })
    });

    /**
     * 资源分配审核消息接收处理
     * // 首先判断当前用户是否具有审批权限
     */
        <shiro:hasPermission name="ams:resource:check">

    var CHECK_SOCKET_IO_CONNECT = io.connect(message_socket_server_url, {
            query: "ns=/resourceCheck&uuid=" + $uuid()
        });
    CHECK_SOCKET_IO_CONNECT.on("resourceCheck", function () {
        openFlightCheck();
    })

    var checkIframeWin = "";

    function openFlightCheck() {
        if (checkIframeWin) {
            checkIframeWin.location.reload();
        } else {
            layer.open({
                title: "资源分配操作审核(注：两天前提交记录的审核后将不显示在此表中)",
                type: 2,
                area: ['1000px', '500px'],
                content: '${ctx}/rms/rs/checkView',
                cancel: function (index, layero) {
                    try {
                    } catch (e) {
                    }
                },
                zIndex: 1030,
                end: function () {
                    checkIframeWin = "";
                },
                success: function (layero, index) {
                    checkIframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
                }
            })
        }
    }

    </shiro:hasPermission>

    /**
     * 空管消息审核处理
     */
        <shiro:hasPermission name="ams:flightDynamicManager:aocThirdParty">

    var AOCTP_SOCKET_IO_CONNECT = io.connect(message_socket_server_url, {
            query: "ns=/flightDynamicManageCheck&uuid=" + $uuid()
        });
    AOCTP_SOCKET_IO_CONNECT.on("flightDynamicManageCheck", function (type) {
        if (type == "aocTPRMS") openFlightAocTP();
    })

    var aocIframeWin = "";

    function openFlightAocTP() {
        if (aocIframeWin) {
            aocIframeWin.location.reload();
        } else {
            layer.open({
                title: "保障进程审核审核(注：审核过的记录超期两天将不显示在此表中)",
                type: 2,
                area: ['1000px', '500px'],
                content: '${ctx}/ams/flightDynamicManager/aocThirdParty',
                zIndex: 1030,
                end: function () {
                    aocIframeWin = "";
                },
                success: function (layero, index) {
                    aocIframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象
                }
            })
        }
    }

    </shiro:hasPermission>

    function get_message(id) {
        $utils.ajaxLoadFragments("${ctx}/ams/message/view/" + id, function (msg) {
            if (!!msg) {
                var mg = msg.message;
                if (!!mg) msg.message = mg.replace("emergencyPrepare", "「应急救援」");
                var unreadMessagesHtml = template('unreadMessagesTemplate', msg);

                layer_ = layer.open({
                    type: 1,
                    closeBtn: 1,
                    shift: 2,
                    area: ["600px", "392px"],
                    shadeClose: false,
                    content: unreadMessagesHtml,

                    title: "查看消息",
                    cancel: function (index) {
                        try {
                            parent.getNewMessage();
                        } catch (a) {
                        }
                    }
                });
            }
        });
    }

    function showMsgPanel(e) {
        var msgTest = $("#msgText").text();
        if (msgTest.indexOf("「应急救援」") >= 0)
            addTab('救援消息', ctx + '/rms/emergencyPrepare/handle', 'default');
        else getNewMessage();
        $("#msgText").text("");//清除滚动
        e.stopPropagation();
    }

    //显示消息列表
    function list_message() {
        getNewMessage();
    }

    //发送消息
    function send_message() {
        addTab('发送新消息', ctx + '/ams/message/sendmessage', 'default');

//        $utils.ajaxLoadFragments( ctx +"/ams/message/sendmessage", function (data) {
//            var area = ["900px", "592px"];
//            layer_audit = layer.open({
//                type: 1,
//                closeBtn: 1,
//                shift: 2,
//                area: area,
//                shadeClose: false,
//                content: data,
//                title: "消息发送"
//            });
//        });
    }

    function getNewMessage() {
        //判断该消息是否属于当前用户
        $.get(ctx + '/ams/message/validateNewMessage', {}, function (resp) {
            if (resp) {
                var unreadMessagesHtml = template('layout_north_msgMenu_tpl', resp);
                $('#layout_north_msgMenu').html(unreadMessagesHtml);
                $("#msgLength").html(resp.unreadNum).click();
            }
        }, 'json');
    }

    function oicSync() {
        layer.open({
            title: "同步管理",
            type: 2,
            area: ['1000px', '500px'],
            content: ctx + '/test/test/ociSync',
            cancel: function (index, layero) {
                try {
                } catch (e) {
                }
            },
            zIndex: 1030,
            end: function () {
            },
            success: function (layero, index) {
            }
        })
    }

    var clearTime = new Date();

    //定时清除航实时消息
    function cleartRtMessage() {
        var diffTime = moment(clearTime).add(1, 'day').set('hour', 0).set('minute', 0).set('second', 20).diff();
        setTimeout(function () {
            $("#rtMessageWindow").html("")
            clearTime = new Date();
            cleartRtMessage();
        }, diffTime);
    }
</script>

</body>

</html>
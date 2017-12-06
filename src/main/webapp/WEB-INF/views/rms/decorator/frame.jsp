<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en" xmlns:decorator="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<head>
    <title><decorator:title/></title>
    <meta charset="UTF-8">
    <!-- 优先使用最新版的IE和Chrome浏览器 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <!-- 360浏览器使用webkit内核 -->
    <meta name="renderer" content="webkit"/>
    <!-- 禁止百度转码（适用于移动端浏览器） -->
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!-- 移动优先,如果网站非响应式设计,请勿使用本Meta -->
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <!-- 禁止Google浏览器提示翻译 -->
    <meta name="google" content="notranslate"/>

    <!-- 移动端设备开始 -->
    <!-- 移除邮箱、电话号码检测 -->
    <meta name="format-detection" content="telephone=no, email=no"/>
    <!-- 删除苹果默认的工具栏和菜单栏 -->
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <!--UC强制竖屏-->
    <meta name="screen-orientation" content="portrait">
    <!--QQ强制竖屏-->
    <meta name="x5-orientation" content="portrait">
    <!--UC强制全屏-->
    <meta name="full-screen" content="yes">
    <!--QQ强制全屏-->
    <meta name="x5-fullscreen" content="true">
    <!--UC应用模式-->
    <meta name="browsermode" content="application">
    <!--QQ应用模式-->
    <meta name="x5-page-mode" content="app">
    <!-- 移动端设备结束 -->

    <!-- 样式引用开始 -->
    <link rel="stylesheet" type="text/css" href="${ctxAssets}/scripts/easyui144/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxAssets}/scripts/easyui144/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxAssets}/scripts/easyui144/IconExtension.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/font-awesome-4.4.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css"
          href="${ctxAssets}/scripts/jquery/jquery-autocomplete/jquery.autocomplete.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css"/>
    <!-- 样式引用结束 -->

    <!-- 添加favicon -->
    <link rel="shortcut icon" type="image/ico" href="${ctxAssets}/images/shortcuticon.ico"/>
    <!-- 指定base路径 -->
    <script>
        document.write('<base href="' + document.location + '" />');
    </script>

    <style type="text/css">
        .formtable {
            border-collapse: collapse;
        }

        .formtable td:nth-child(odd) {
            font-weight: bold;
            text-align: right;
            background-color: #F2F7FE;
            color: #5E7595;
        }

        .formtable td, .formtable th {
            border: 1px solid #cad9ea;
            padding: 6px;
        }

        #main {
            padding: 0;
            margin: 0;
        }

        #main .container-fluid {
            padding: 0 4px 0 6px;
        }

        #header {
            margin: 0 0 8px;
            position: static;
        }

        #header li {
            font-size: 14px;
            _font-size: 12px;
        }

        #header .brand {
            font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
            font-size: 26px;
            padding-left: 33px;
        }

        #footer {
            margin: 8px 0 0 0;
            padding: 3px 0 0 0;
            font-size: 11px;
            text-align: center;
            border-top: 2px solid #0663A2;
        }

        #footer, #footer a {
            color: #999;
        }

        #left {
            overflow-x: hidden;
            overflow-y: auto;
        }

        #left .collapse {
            position: static;
        }

        #userControl > li > a {
            text-shadow: none;
        }

        #userControl > li > a:hover, #user #userControl > li.open > a {
            background: transparent;
        }

        a {
            color: Black;
            text-decoration: none;
        }

        a:hover {
            color: black;
            text-decoration: none;
        }

        .tree-node-selected {
            background: #eaf2ff;
        }

        .shortcut {
            margin-left: 5px;
            margin-right: 15px;
            margin-top: 8px;
            height: 62px;
            float: right;
        }

        .shortcut li {
            float: left;
            list-style: none;
            margin-right: 10px;
            cursor: pointer;
        }

        .textbox-disabled {
            opacity: 0.6;
            filter: alpha(opacity=60);
        }

        .textbox-readonly {
            opacity: 0.6;
            filter: alpha(opacity=60);
        }

        .drag-item {
            list-style-type: none;
            display: block;
            padding: 5px;
            border: 1px solid #ccc;
            margin: 2px;
            width: 95%;
            background: #fafafa;
            color: #444;
        }

        .indicator {
            position: absolute;
            font-size: 9px;
            width: 10px;
            height: 10px;
            display: none;
            color: red;
        }

        .ct-selected {
            background-color: #FFE48D;
        }
    </style>
    <!-- 脚本引用开始 -->
    <script src="${ctxAssets}/scripts/underscore/underscore-min.js"></script>
    <script src="${ctxAssets}/scripts/easyui144/jquery.min.js"></script>
    <script src="${ctxAssets}/scripts/easyui144/jquery.easyui.min.js"></script>
    <script src="${ctxAssets}/scripts/easyui144/locale/easyui-lang-zh_CN.js"></script>
    <script src="${ctxAssets}/scripts/easyui144/extensions/datagrid-filter/datagrid-filter.js"></script>
    <script src="${ctxAssets}/scripts/easyui144/extensions/datagrid-cell-tooltip/datagrid-cell-tooltip-extension.js"></script>
    <script src="${ctxAssets}/scripts/easyui144/datagridview/datagrid-detailview.js"></script>
    <script src="${ctxAssets}/scripts/metadata-dev/garims.jQuery.utilities.js"></script>
    <script src="${ctxAssets}/scripts/metadata-dev/garims.SysDictUtils.js"></script>
    <script src="${ctxAssets}/scripts/My97DatePicker/WdatePicker.js"></script>
    <script src="${ctxAssets}/scripts/socket.io-client/socket.io.js"></script>
    <script src="${ctxAssets}/scripts/metadata-dev/garims.CookieUtils.js"></script>
    <script src="${ctxAssets}/scripts/js-cookie/js.cookie.js"></script>
    <script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.js"></script>
    <script src="${ctxAssets}/scripts/layer/src/layer.js"></script>


    <script>
        var ctx = '${ctx}', ctxAssets = '${ctxAssets}', currentUserId = '${fns:getUser().id}',
            currentOfficeId = '${fns:getUser().office.id}', colorsObj;

        if (!localStorage.getItem("colors")) {
            colorsObj = {};
            localStorage.setItem("colors", JSON.stringify(colorsObj));
        } else colorsObj = JSON.parse(localStorage.getItem("colors"));

        (function ($) {
            // 扩展easyui messager 的show
            $.extend($.messager, {
                slide: function (msg, timeout) {
                    timeout = (typeof(timeout) != 'undefined' && !isNaN(timeout)) ? parseInt(timeout, 10) : 5000;
                    this.show({title: '提示', msg: msg, timeout: timeout, showType: 'slide'});
                }
            });

            $.fn.extend({
                serializeObject: function () {
                    var a = this.serializeArray(), o = {}, h = o.hasOwnProperty, i, e;
                    for (i = 0; i < a.length; i++) {
                        e = a[i];
                        if (!h.call(o, e.name)) o[e.name] = e.value;
                    }
                    return o;
                }
            });
        })(jQuery);

        /****************消息提醒*****************/
        var message_socket_server_url = "${fns:getConstValue('socket_io_setting', 'url')}";

        //应急救援开关,数据
        var flag_msgEP, msgEPData = [];
        var MESSAGE_SOCKET_IO_CONNECT = io.connect(message_socket_server_url, {query: "ns=/message/notify&uuid=" + $uuid()});

        MESSAGE_SOCKET_IO_CONNECT.on('messagenotify', function (data) {
            if (data && data.length > 0) {
//                var postData = {};

                data = JSON.parse(data);
//                var message_group = data['message_group'];
//                var message_log = data['message_log'];

//                postData.message_group = message_group;
//                for (var i = 0; i < message_log.length; i++) {
//                    if (!!message_log[i] && message_log[i].indexOf("|") > 0) {
//                        var temp = message_log[i].split('|');
//                        if (temp[0] == currentUserId) {
//                            postData.rec_id = temp[0];
//                            postData.log_id = temp[1];
//                            postData.id = temp[2];
//                            break;
//                        }
//                    }
//                }

                // 判断该消息是否属于当前用户
//                $.get(ctx + '/ams/message/validateNewMessage', postData, function (resp) {
//                    if (resp && resp.newMessage) {
//                        var flag = false, flag_ep = false;
//                        _.each(resp.unreadMessages, function (msg) {
//                            if (!!msg && !!msg.message && (msg.message.indexOf("emergencyPrepare") == 0)) {
//                                msgEPData.push(msg);
//                                flag_ep = true;
//                            } else flag = true;
//                        })
//
//                        if (flag_ep)
//                            $.messager.slide('<small>您有「应急救援」新消息,请查收 <a style="color:red;" href="javascript:addTab(\'救援消息\',' + ctx + '\'/rms/emergencyPrepare/handle\',\'default\');">查看</a></small>');
//                        else if (flag && !flag_msgEP)
//                            $.messager.slide('<small>您有一条新消息,请注意查收 <a style="color:red;" href="javascript:addTab(\'消息管理\',' + ctx + '\'/ams/message/list\',\'default\');">查看</a></small>');
//
//                    }
//                }, 'json');

                if ((data['rec_ids'] && data['rec_ids'].indexOf(currentUserId) >= 0 ) || (data['message_group'] && data['message_group'].indexOf(currentOfficeId) >= 0)) {
                    var msg = data['msg'];

                    var msgHtml = '<small>您有一条新消息,请注意<a style="color:red;" href="#" onclick="list_message()">查收</a>!</small>';
                    if (msg.indexOf("emergencyPrepare") >= 0)
                        msgHtml = '<small>您有「应急救援」新消息,请查收 <a style="color:red;" href="javascript:addTab(\'救援消息\',\'' + ctx + '/rms/emergencyPrepare/handle\',\'default\');">查看</a></small>';

                    layer.open({
                        title: '新消息', offset: 'rb', btn: [], time: 5000,
                        content: msgHtml
                    });

                    $("#msgLength").text(parseInt($("#msgLength").text()) + 1);

                    $("#msgText").text(msg.replace("emergencyPrepare", "「应急救援」"));
                }
            }
        });

    </script>
    <decorator:head/>
</head>

<body class="easyui-layout" style="overflow-y: hidden" scroll="no">

<decorator:body/>

</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: xiaopo
  Date: 15/12/18
  Time: 上午9:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- 优先使用最新版的IE和Chrome浏览器 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>系统登录-资源管理系统</title>
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

    <!-- SEO开始 -->
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="robots" content="">
    <!-- SEO结束 -->

    <!-- 样式引用开始 -->
    <!-- bootstrap -->
    <link href="${ctxAssets}/scripts/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- font-awesome 字体-->
    <link href="${ctxAssets}/scripts/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- 动画 -->
    <link rel="stylesheet" href="${ctxAssets}/styles/vendor/animate/animate.css">
    <!-- mmenu 菜单-->
    <link rel="stylesheet" media="all" href="${ctxAssets}/scripts/jQuery.mmenu/dist/core/css/jquery.mmenu.all.css"/>

    <link rel="stylesheet" href="${ctxAssets}/scripts/datatables.net-bs/css/dataTables.bootstrap.min.modify.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/datatables.net-buttons-bs/css/buttons.bootstrap.min.css">
    <!-- datatables extensions styling 4 bootstrap -->
    <link rel="stylesheet" href="${ctxAssets}/scripts/datatables-autofill-bootstrap/css/autoFill.bootstrap.min.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/datatables.net-colreorder-bs/css/colReorder.bootstrap.min.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/datatables.net-keytable-bs/css/keyTable.bootstrap.min.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/datatables.net-responsive-bs/css/responsive.bootstrap.min.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/datatables.net-rowreorder-bs/css/rowReorder.bootstrap.min.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/datatables.net-scroller-bs/css/scroller.bootstrap.min.css">
    <link rel="stylesheet" href="${ctxAssets}/scripts/datatables.net-select-bs/css/select.bootstrap.min.css">
    <!-- bootstrap datepicker -->
    <link rel="stylesheet" href="${ctxAssets}/scripts/bootstrap-datepicker/dist/css/bootstrap-datepicker3.min.css">
    <!--form verification表单验证-->
    <link href="${ctxAssets}/scripts/jQuery-Validation-Engine-2.6.2-ciaoca/css/validationEngine.jquery.css" rel="stylesheet">

    <!--Minimal Framework CSS-->
    <%--<link href="${ctxAssets}/styles/minimal.css" rel="stylesheet">--%>
    <!--base-->
    <link href="${ctxAssets}/styles/base.css" rel="stylesheet">
    <link href="${ctxAssets}/styles/vendor/garims.metadata.dev.css" rel="stylesheet">
    <!--login-->
    <link href="${ctxAssets}/styles/login.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="${ctxAssets}/scripts/html5shiv/dist/html5shiv.min.js"></script>
    <script src="${ctxAssets}/scripts/respond/dest/respond.min.js"></script>
    <![endif]-->
    <!-- 样式引用结束 -->

    <!-- 添加favicon -->
    <link rel="shortcut icon" type="image/ico" href="/favicon.ico"/>
</head>

<body class="login-bg" style="background: url(${ctxAssets}/images/login-bg.jpg) no-repeat scroll center center;background-attachment: fixed;">
<div class="flightControlLogin-wrap">
    <h1 class="flightControlLogin-title">欢迎使用资源管理系统</h1>

    <div class="flightControlLogin-main">
        <h5 class="text-center">用户登录</h5>

        <div class="flightControlLogin-form">
            <form id="form_id" action="${ctx}/login" method="post" class="form-horizontal">
                <div class="form-group">
                    <label class="flightControlLogin-form-label"><i class="fa fa-user"></i></label>
                    <input type="text" id="username" name="username" class="form-control flightControlLogin-form-text" placeholder="用户名"
                           data-validation-engine="validate[required,minSize[2],maxSize[20]]"/>
                </div>
                <div class="form-group">
                    <label class="flightControlLogin-form-label"><i class="fa fa-lock"></i></label>
                    <input type="password" id="password" name="password" class="form-control flightControlLogin-form-text" placeholder="密码"
                           data-validation-engine="validate[required,minSize[2],maxSize[20]"/>
                </div>
                <c:if test="${isValidateCodeLogin}">
                    <div class="form-group">
                        <label class="flightControlLogin-captcha-label">
                            <img src="${pageContext.request.contextPath}/servlet/validateCodeServlet"
                                 onclick="$(this).attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid"/>
                        </label>
                        <input id="validateCode" name="validateCode" type="text" class="form-control flightControlLogin-form-captchaText" placeholder="请输入验证码"
                               data-validation-engine="validate[required,custom[onlyLetterNumber]]"/>
                    </div>
                </c:if>
                <div class="form-group text-center">
                    <input type="submit" class="btn btn-info" value="&nbsp;&nbsp;&nbsp;&nbsp;登&nbsp;&nbsp;录&nbsp;&nbsp;&nbsp;&nbsp;">
                </div>
                <c:if test="${not empty message }">
                    <div class="form-group" style="text-align: center;">
                        <span class="label label-warning" style="font-size: 18px;">${message}</span>
                    </div>
                </c:if>
            </form>
        </div>
    </div>
</div>

<!-- jQuery Library V2.1.4 -->
<script src="${ctxAssets}/scripts/jquery/dist/jquery.min.js"></script>
<!-- layer -->
<script src="${ctxAssets}/scripts/layer/layer.js"></script>
<!--form verification表单验证开始-->
<script src="${ctxAssets}/scripts/jQuery-Validation-Engine-2.6.2-ciaoca/js/jquery.validationEngine_modify.js"></script>
<script src="${ctxAssets}/scripts/jQuery-Validation-Engine-2.6.2-ciaoca/js/jquery.validationEngine-zh_CN.js"></script>
<script>
    //直接调用
    $('#form_id').validationEngine({
        showOneMessage: true
    });

</script>
<!--form verification表单验证结束-->

</body>
</html>

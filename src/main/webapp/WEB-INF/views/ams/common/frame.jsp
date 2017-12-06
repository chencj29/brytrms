
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en" xmlns:decorator="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<head>

	<title><decorator:title/>--航班管理系统</title>
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

	<!--Minimal Framework CSS-->
	<link href="${ctxAssets}/styles/minimal.css" rel="stylesheet">

	<link href="${ctxAssets}/styles/vendor/garims.metadata.dev.css" rel="stylesheet">

	<!-- validation -->
	<link href="${ctxAssets}/scripts/jQuery-Validation-Engine-2.6.2-ciaoca/css/validationEngine.jquery.css" rel="stylesheet">

	<%--<link rel="stylesheet" href="${ctxAssets}/scripts/ams/ams.css">--%>

	<!--base-->
	<link href="${ctxAssets}/styles/base.css" rel="stylesheet">

	<!-- select2 -->
	<link rel="stylesheet" href="${ctxAssets}/scripts/select2/dist/css/select2.min.css">

	<!-- Gantt CSS -->
	<link rel="stylesheet" href="${ctxAssets}/scripts/gantt/gantt.css">

	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="${ctxAssets}/scripts/html5shiv/dist/html5shiv.min.js"></script>
	<script src="${ctxAssets}/scripts/respond/dest/respond.min.js"></script>
	<![endif]-->
	<!-- 样式引用结束 -->

	<!-- 添加favicon -->
	<link rel="shortcut icon" type="image/ico" href="${ctxAssets}/images/shortcuticon.ico"/>
	<!-- 指定base路径 -->
	<script>
		document.write('<base href="' + document.location + '" />');
		var ctx = '${ctx}';
	</script>

	<!-- jQuery Library V2.1.4 -->
	<script src="${ctxAssets}/scripts/jquery/dist/jquery.min.js"></script>
	<!-- sysutils -->
	<script src="${ctxAssets}/scripts/metadata-dev/garims.SysDictUtils.js"></script>
	<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>

	<style type="text/css">
		.add-detail-content {
			width: 100%;
			height: 300px;
			background-color: #D5F7FF;
			border-radius: 4px;
		}

		body #content input[type="search"] {
			background-color: #ffffff;
		}

		body #content .tile-body.transparent-white input[type="search"]:focus {
			background-color: #ffffff;
		}
	</style>

	<decorator:head/>
</head>
<body class="bg-1">
<div id="wrap">
	<div class="row">
		<div class="navbar navbar-default navbar-fixed-top navbar-transparent-black mm-fixed-top" role="navigation"
		     id="navbar">
			<!-- logo开始 -->
			<div class="navbar-header col-md-2">
				<a class="navbar-brand" href="javascript:void(0);">
					航班管理系统
				</a>

				<div class="sidebar-collapse">
					<a href="javascript:void(0);">
						<i class="fa fa-bars"></i>
					</a>
				</div>
			</div>
			<!-- logo结束-->
			<!-- fixed header right开始 -->
			<div class="navbar-collapse">
				<!-- fixed header right list开始 -->
				<ul class="nav navbar-nav quick-actions">
					<!--用户-->
					<li class="dropdown divided">
						<a href="javascript:void(0);">用户：${fns:getUser().loginName}</a>
						<a href="${ctx}/logout">退出</a>
					</li>
					<!--岗位-->
					<li class="dropdown divided"><a href="#">岗位：${fns:getUser().name}</a></li>
					<!--信息开始-->
					<li class="dropdown divided">
						<a class="dropdown-toggle button" data-toggle="dropdown" href="#">
							<i class="fa fa-envelope"></i>
							<span class="label label-transparent-black">1</span>
						</a>
						<ul class="dropdown-menu wider arrow nopadding messages">
							<li><h1>您有<strong>1</strong>条新消息</h1></li>
							<li>
								<a class="cyan" href="#">
									<div class="profile-photo">
										<img src="${ctxAssets}/images/ici-avatar.jpg" alt/>
									</div>
									<div class="message-info">
										<span class="sender">消息标题</span>
										<span class="time">12分钟</span>

										<div class="message-content">消息内容
										</div>
									</div>
								</a>
							</li>
							<li>
								<a class="green" href="#">
									<div class="profile-photo">
										<img src="${ctxAssets}/images/arnold-avatar.jpg" alt/>
									</div>
									<div class="message-info">
										<span class="sender">消息标题</span>
										<span class="time">1小时</span>

										<div class="message-content">消息内容
										</div>
									</div>
								</a>
							</li>
							<li>
								<a href="#">
									<div class="profile-photo">
										<img src="${ctxAssets}/images/profile-photo.jpg" alt/>
									</div>
									<div class="message-info">
										<span class="sender">消息标题</span>
										<span class="time">3小时</span>

										<div class="message-content">消息内容
										</div>
									</div>
								</a>
							</li>
							<li>
								<a class="red" href="#">
									<div class="profile-photo">
										<img src="${ctxAssets}/images/peter-avatar.jpg" alt/>
									</div>
									<div class="message-info">
										<span class="sender">消息标题</span>
										<span class="time">5小时</span>

										<div class="message-content">消息内容
										</div>
									</div>
								</a>
							</li>
							<li>
								<a class="orange" href="#">
									<div class="profile-photo">
										<img src="${ctxAssets}/images/george-avatar.jpg" alt/>
									</div>
									<div class="message-info">
										<span class="sender">消息标题</span>
										<span class="time">6小时</span>

										<div class="message-content">消息内容
										</div>
									</div>
								</a>
							</li>
							<li class="topborder"><a href="#">查看所有的信息<i class="fa fa-angle-right"></i></a>
							</li>
						</ul>
					</li>
					<!--信息结束-->
				</ul>
				<!--fixed header right list结束-->
				<!-- Sidebar左侧菜单开始 -->
				<%@include file="sidebar.jsp" %>
				<!-- Sidebar左侧菜单结束-->
			</div>
			<!--fixed header right结束 -->
		</div>
		<!-- Fixed header结束 -->
		<!-- 正文开始 -->
		<decorator:body/>
		<!-- 正文结束 -->
	</div>
</div>


<!-- 脚本引用开始 -->
<!-- Bootstrap v3.3.6 -->
<script src="${ctxAssets}/scripts/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- block ui -->
<%--<script src="${ctxAssets}/scripts/jQuery.blockUI/jquery.blockUI.min.js"></script>--%>
<!-- jQuery MMenu -->
<script src="${ctxAssets}/scripts/jQuery.mmenu/dist/core/js/jquery.mmenu.min.all.js"></script>
<!-- jQuery NiceScroll -->
<script src="${ctxAssets}/scripts/jquery.nicescroll/dist/jquery.nicescroll.min.js"></script>

<!-- datatables -->
<script src="${ctxAssets}/scripts/datatables.net/js/jquery.dataTables.min.js"></script>
<!-- datatables by bootstrap styling -->
<script src="${ctxAssets}/scripts/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<!-- datatables extensions -->

<!-- autofill -->
<script src="${ctxAssets}/scripts/datatables-autofill-bootstrap/js/dataTables.autoFill.min.js"></script>
<script src="${ctxAssets}/scripts/datatables-autofill-bootstrap/js/autoFill.bootstrap.min.js"></script>
<!-- buttons -->
<script src="${ctxAssets}/scripts/datatables.net-buttons/js/dataTables.buttons.js" charset="utf-8"></script>
<script src="${ctxAssets}/scripts/datatables.net-buttons-bs/js/buttons.bootstrap.js" charset="utf-8"></script>
<script src="${ctxAssets}/scripts/datatables.net-buttons/js/buttons.colVis.min.js"></script>
<script src="${ctxAssets}/scripts/datatables.net-buttons/js/buttons.html5.min.js"></script>
<script src="${ctxAssets}/scripts/datatables.net-buttons/js/buttons.print.min.js"></script>
<script src="${ctxAssets}/scripts/jszip/dist/jszip.min.js"></script>
<script src="${ctxAssets}/scripts/pdfmake/build/pdfmake.min.js"></script>
<script src="${ctxAssets}/scripts/pdfmake/build/vfs_fonts.js"></script>

<script src="${ctxAssets}/scripts/datatables.net-colreorder/js/dataTables.colReorder.min.js"></script>
<script src="${ctxAssets}/scripts/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
<script src="${ctxAssets}/scripts/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
<script src="${ctxAssets}/scripts/datatables.net-rowreorder/js/dataTables.rowReorder.min.js"></script>
<script src="${ctxAssets}/scripts/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
<script src="${ctxAssets}/scripts/datatables.net-select/js/dataTables.select.min.js"></script>

<!-- layer -->
<script src="${ctxAssets}/scripts/layer/src/layer.js"></script>
<!-- Framework Scripts -->
<script src="${ctxAssets}/scripts/minimal/minimal.js"></script>
<!-- Metadata Garims Dev Javascript -->
<script src="${ctxAssets}/scripts/metadata-dev/garims.DataTables.Defaults.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.jQuery.utilities.js"></script>
<!-- validation -->
<script src="${ctxAssets}/scripts/jQuery-Validation-Engine-2.6.2-ciaoca/js/jquery.validationEngine_modify.js"></script>
<script src="${ctxAssets}/scripts/jQuery-Validation-Engine-2.6.2-ciaoca/js/jquery.validationEngine-zh_CN.js"></script>

<!-- form -->
<script src="${ctxAssets}/scripts/JQuery-from/jquery.form.js"></script>

<script src="${ctxAssets}/scripts/jQuery.serializeObject/dist/jquery.serializeObject.min.js"></script>

<!-- select2 -->
<script src="${ctxAssets}/scripts/select2/dist/js/select2.full.js"></script>
<script src="${ctxAssets}/scripts/select2/dist/js/i18n/zh-CN.js"></script>
<!-- datetimepicker -->
<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/moment/min/locales.min.js"></script>

<!-- myDatePick97 -->
<script src="${ctxAssets}/scripts/My97DatePicker/WdatePicker.js"></script>

<!-- Gantt JS -->
<script src="${ctxAssets}/scripts/jquery-ui/jquery-ui.min.js"></script>
<script src="${ctxAssets}/scripts/gantt/collision.detection.plugin.js"></script>
<script src="${ctxAssets}/scripts/gantt/gantt.js"></script>


<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet"/>

<script src="${ctxStatic}/jquery-plugs/storage/jquery.storageapi.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-plugs/storage/jquery.storageapi.min.js" type="text/javascript"></script>

<!-- 脚本引用结束 -->
<script>
	var ctx = '${ctx}';

	$(function () {
		// ajax loading
		$(document).ajaxStart(function () {
			layer.load(1);
		}).ajaxStop(function () {
			layer.closeAll('loading');
		});


		layer.config({zIndex: 1000});
		//$.fn.select2.defaults.set("language", "zh-CN");
	});
</script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>预警提醒设置</title>
    <c:set var="controllerUrl" value="monitor"/>
</head>
<body>
<div id="content" class="col-md-12">
    <!--正文头部开始-->
    <%--<div class="pageheader">
        <h2>系统管理</h2>

        <div class="breadcrumbs">
            <ol class="breadcrumb">
                <li>
                    <a href="###">航班管理系统</a>
                </li>
                <li>
                    <a href="###">系统管理</a>
                </li>
                <li class="active">预警提醒设置</li>
            </ol>
        </div>
    </div>--%>
    <!--正文头部结束-->

    <!--正文内容开始-->
    <div class="main">
        <div class="row">
            <div class="col-md-12">
                <section class="tile transparent">
                    <div class="tile-header transparent">
                        <h1>预警提醒设置</h1>

                        <div class="controls">
                            <a href="javascript:void(0);" id="refresh_config_datatbles" class="refresh">
                                <i class="fa fa-refresh"></i>
                            </a>
                        </div>
                    </div>
                    <div class="tile-body color transparent-white rounded-corners">
                        <div class="table-responsive">
                            <table class="table display nowrap  table-custom table-datatable table-hover" width="100%" id="configTable">
                                <thead>
                                <tr>
                                    <th>监听名称</th>
                                    <th>监听类型</th>
                                    <th>监听实体</th>
                                    <th>执行动作编码</th>
                                    <th>提示信息</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <section class="tile transparent">
                    <div class="tile-header transparent">
                        <h1>预警提醒规则</h1>

                        <div class="controls">
                            <a href="javascript:void(0);" id="refresh_item_datatbles" class="refresh">
                                <i class="fa fa-refresh"></i>
                            </a>
                        </div>
                    </div>
                    <div class="tile-body color transparent-white rounded-corners">
                        <div class="table-responsive">
                            <table class="table display nowrap  table-custom table-datatable table-hover" width="100%" id="itemTable">
                                <thead>
                                <tr>
                                    <th>字段名称</th>
                                    <th>字段编码</th>
                                    <th>条件名称</th>
                                    <th>条件编码</th>
                                    <th>条件阀值</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
    <!--正文内容结束-->
</div>
<script src='${ctxAssets}/scripts/metadata-dev/garims.WarnRemind.js'></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>数据字典管理</title>
    <c:set var="controllerUrl" value="sysdict"/>
</head>
<body>
<div id="content" class="col-md-12">
    <!--正文头部开始-->
    <div class="pageheader">
        <h2>基础数据</h2>

        <div class="breadcrumbs">
            <ol class="breadcrumb">
                <li><a href="###">资源管理系统</a></li>
                <li><a href="###">系统管理</a></li>
                <li class="active">数据字典管理</li>
            </ol>
        </div>
    </div>
    <!--正文头部结束-->

    <!--正文内容开始-->
    <div class="main">
        <div class="row">
            <div class="col-md-12">
                <section class="tile transparent">
                    <div class="tile-header transparent">
                        <h1>数据字典</h1>

                        <div class="controls">
                            <a href="javascript:void(0);" id="refresh_datatbles" class="refresh"><i class="fa fa-refresh"></i></a>
                        </div>
                    </div>
                    <div class="tile-body color transparent-white rounded-corners">
                        <div class="table-responsive">
                            <table class="table display  table-custom table-datatable table-hover"
                                   id="modifyDataTable" width="100%">
                                <thead>
                                <tr>
                                    <th>表名</th>
                                    <th>类型</th>
                                    <th>字段说明</th>
                                    <th>字段值</th>
                                    <th>描述</th>
                                    <th>排序</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
    <!--正文内容结束-->
</div>
<!-- 添加航空公司弹出框 -->
<div class="airlineInfoForm" id="modifyForm" style="display:none;">
    <div class="row" style="padding-top: 20px; margin: 0px;">
        <div class="col-md-12">
            <form class="form-horizontal" role="form" name="saveModifyForm" method="post"
                  action="${ctx}/ams/${controllerUrl}/save">
                <div class="form-group">
                    <label for="tableName" class="col-sm-3 control-label">表名</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="tableName" name="tableName" placeholder="请输入表名">
                    </div>
                </div>
                <div class="form-group">
                    <label for="type" class="col-sm-3 control-label">字段名</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[englishNormal],minSize[2],maxSize[20]]" id="type"
                               name="type" placeholder="请输入字段名">
                    </div>
                </div>
                <div class="form-group">
                    <label for="label" class="col-sm-3 control-label">字段说明</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[50]]"
                               id="label" name="label" placeholder="请输入字段说明">
                    </div>
                </div>
                <div class="form-group">
                    <label for="value" class="col-sm-3 control-label">字段值</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[englishNormal],minSize[1],maxSize[20]]"
                               id="value" name="value" placeholder="请输入字段值">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-3 control-label">描述</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[chinese],minSize[2],maxSize[100]]"
                               id="description" name="description" placeholder="请输入描述">
                    </div>
                </div>
                <div class="form-group">
                    <label for="sort" class="col-sm-3 control-label">排序</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[2],maxSize[20]]"
                               id="sort" name="sort" value="20" placeholder="请输入排序值">
                    </div>
                </div>
                <input type="hidden" name="id" value="">
            </form>
        </div>
    </div>
</div>

<script>
    var modelName = "数据字典";
    var columns = [
        {"data": "tableName"},
        {"data": "type"},
        {"data": "label"},
        {"data": "value"},
        {"data": "description"},
        {"data": "sort"}
    ];
</script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.FlightBasic.Common.js"></script>
</body>
</html>

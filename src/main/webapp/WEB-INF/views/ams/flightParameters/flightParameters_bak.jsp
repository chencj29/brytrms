<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>航班参数管理</title>
    <c:set var="controllerUrl" value="flightparams"/>
</head>
<body>
<div id="content" class="col-md-12">
    <!--正文头部开始-->
    <div class="pageheader">
        <h2>基础数据</h2>

        <div class="breadcrumbs">
            <ol class="breadcrumb">
                <li><a href="###">资源管理系统</a></li>
                <li><a href="###">基础数据</a></li>
                <li class="active">航班参数管理</li>
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
                        <h1>航班参数</h1>

                        <div class="controls">
                            <a href="javascript:void(0);" id="refresh_datatbles" class="refresh"><i class="fa fa-refresh"></i></a>
                        </div>
                    </div>
                    <div class="tile-body color transparent-white rounded-corners">
                        <div class="table-responsive">
                            <table class="table display  table-custom table-datatable table-hover"
                                   id="modifyDataTable"  width="100%">
                                <thead>
                                <tr>
                                    <th>状态编号</th>
                                    <th>状态类型</th>
                                    <th>状态简称</th>
                                    <th>性质描述</th>
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
            <!--航空公司添加&修改表单 ~ see ./fragments/bacisData/airlineInfo/airlineInfoForm.html-->
            <form class="form-horizontal" role="form" name="saveModifyForm" method="post"
                  action="${ctx}/ams/${controllerUrl}/save">
                <div class="form-group">
                    <label for="statusNum" class="col-sm-3 control-label">状态编号</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[onlyLetterNumber],minSize[2],maxSize[10]]" id="statusNum"
                               name="statusNum" placeholder="请输入状态编码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="statusName" class="col-sm-3 control-label">状态类型</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[chinese],minSize[2],maxSize[20]]" id="statusName"
                               name="statusName" placeholder="请输入状态类型">
                    </div>
                </div>
                <div class="form-group">
                    <label for="statusShort" class="col-sm-3 control-label">状态简称</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[chinese],minSize[2],maxSize[20]]" id="statusShort"
                               name="statusShort" placeholder="请输入状态简称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="propertyDescription" class="col-sm-3 control-label">性质描述</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[50]]" id="propertyDescription"
                               name="propertyDescription" placeholder="请输入性质描述">
                    </div>
                </div>
                <input type="hidden" name="id" value="">
            </form>
        </div>
    </div>
</div>

<script>
    var modelName = "航班参数";
    var columns = [
        {"data": "statusNum"},
        {"data": "statusName"},
        {"data": "propertyDescription"},
        {"data": "statusShort"}
    ];
</script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.FlightBasic.Common.js"></script>
</body>
</html>

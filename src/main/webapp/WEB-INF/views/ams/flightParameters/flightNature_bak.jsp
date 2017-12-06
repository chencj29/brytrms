<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>航班性质管理</title>
    <c:set var="controllerUrl" value="flightnature"/>
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
                <li class="active">航班性质管理</li>
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
                        <h1>航班性质</h1>

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
                                    <th>性质编号</th>
                                    <th>性质名称</th>
                                    <th>英文名称</th>
                                    <th>SITA编码</th>
                                    <th>AFTN编码</th>
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
                    <label for="natureNum" class="col-sm-3 control-label">性质编号</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[onlyLetterNumber],minSize[2],maxSize[10]]"
                               id="natureNum" name="natureNum" placeholder="请输入性质编号">
                    </div>
                </div>
                <div class="form-group">
                    <label for="natureName" class="col-sm-3 control-label">性质名称</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[20]]"
                               id="natureName" name="natureName" placeholder="请输入性质名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="englishName" class="col-sm-3 control-label">英文名称</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[onlyLetterNumber],minSize[2],maxSize[20]]" id="englishName"
                               name="englishName" placeholder="请输入英文名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="sita" class="col-sm-3 control-label">SITA编码</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[50]]" id="sita"
                               name="sita" placeholder="请输入SITA编码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="sita" class="col-sm-3 control-label">AFTN编码</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[50]]" id="aftn"
                               name="aftn" placeholder="请输入AFTN编码">
                    </div>
                </div>
                <input type="hidden" name="id" value="">
            </form>
        </div>
    </div>
</div>

<script>
    var modelName = "航班性质";
    var columns = [
        {"data": "natureNum"},
        {"data": "natureName"},
        {"data": "englishName"},
        {"data": "sita"},
        {"data": "aftn"}
    ];
</script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.FlightBasic.Common.js"></script>
</body>
</html>

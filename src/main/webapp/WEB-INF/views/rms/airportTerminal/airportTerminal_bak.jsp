
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title> 航站楼信息</title>
    <c:set var="controllerUrl" value="airportTerminal"/>
    <style>
        div.dataTables_wrapper {
            margin: 0 auto;
        }

        table, tbody, th, td {
            white-space: nowrap;
        }
    </style>
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
                <li class="active"> 航站楼信息</li>
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
                        <h1> 航站楼信息</h1>

                        <div class="controls">
                            <a href="javascript:void(0);" id="refresh_datatbles" class="refresh"><i class="fa fa-refresh"></i></a>
                        </div>
                    </div>
                    <div class="tile-body color transparent-white rounded-corners">
                        <div class="table-responsive">
                            <table class="table display nowrap table-custom table-datatable table-hover"
                                   id="modifyDataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>航站楼名称</th>
                                    <th>航站楼编码</th>
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
                  action="${ctx}/rms/${controllerUrl}/save">
                <div class="form-group">
                    <label for="terminalName" class="col-sm-2 control-label">航站楼名称</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[36]]"
                         id="terminalName" name="terminalName" placeholder="请输入航站楼名称">
                    </div>
                </div>
                
                <div class="form-group">    
                   <label for="terminalCode" class="col-sm-2 control-label">航站楼编码</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[200]]" 
                        id="terminalCode" name="terminalCode" placeholder="请输入航站楼编码">
                    </div>
                </div>
                
                <input type="hidden" name="id" value="">
            </form>
        </div>
    </div>
</div>

<script>
     var modelName = "航站楼", width = "800px";
    var columns = [
        {"data": "terminalName"},
        {"data": "terminalCode"}
    ];

</script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.FlightBasic.Common.js"></script>
</body>
</html>

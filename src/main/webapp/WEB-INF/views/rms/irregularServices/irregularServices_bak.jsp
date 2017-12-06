<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>不正常服务记录</title>
    <c:set var="controllerUrl" value="irregularServices"/>
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
                <li class="active">不正常服务记录</li>
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
                        <h1>不正常服务记录</h1>

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
                                    <th>航空公司代码</th>
                                    <th>航空公司名称</th>
                                    <th>航班号</th>
                                    <th>地点</th>
                                    <th>不正常服务编号</th>
                                    <th>不正常服务类型</th>
                                    <th>产生原因</th>
                                    <th>补救措施</th>
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
<div class="airlineInfoForm" id="modifyForm" style="display:none;">
    <div class="row" style="padding-top: 20px; margin: 0px;">
        <div class="col-md-12">
            <form class="form-horizontal" role="form" name="saveModifyForm" method="post"
                  action="${ctx}/rms/${controllerUrl}/save">
				<div class="form-group">
					<label for="flightCompanyCode" class="col-sm-2 control-label">航空公司代码</label>
					<div class="col-sm-3">
						<input id="flightCompanyCode" name="flightCompanyCode" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入航空公司代码">
					</div>
				</div>
				<div class="form-group">
					<label for="flightCompanyName" class="col-sm-2 control-label">航空公司名称</label>
					<div class="col-sm-3">
						<input id="flightCompanyName" name="flightCompanyName" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入航空公司名称">
					</div>
				</div>
				<div class="form-group">
					<label for="flightNum" class="col-sm-2 control-label">航班号</label>
					<div class="col-sm-3">
						<input id="flightNum" name="flightNum" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入航班号">
					</div>
				</div>
				<div class="form-group">
					<label for="place" class="col-sm-2 control-label">地点</label>
					<div class="col-sm-3">
						<input id="place" name="place" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入地点">
					</div>
				</div>
				<div class="form-group">
					<label for="irregularServiceCode" class="col-sm-2 control-label">不正常服务编号</label>
					<div class="col-sm-3">
						<input id="irregularServiceCode" name="irregularServiceCode" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入不正常服务编号">
					</div>
				</div>
				<div class="form-group">
					<label for="irregularServiceName" class="col-sm-2 control-label">不正常服务类型</label>
					<div class="col-sm-3">
						<input id="irregularServiceName" name="irregularServiceName" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入不正常服务类型">
					</div>
				</div>
				<div class="form-group">
					<label for="cause" class="col-sm-2 control-label">产生原因</label>
					<div class="col-sm-3">
						<input id="cause" name="cause" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入产生原因">
					</div>
				</div>
				<div class="form-group">
					<label for="remedialMeasure" class="col-sm-2 control-label">补救措施</label>
					<div class="col-sm-3">
						<input id="remedialMeasure" name="remedialMeasure" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入补救措施">
					</div>
				</div>
				<input type="hidden" name="id" value="">
            </form>
        </div>
    </div>
</div>
<script>
     var modelName = "不正常服务记录", width = "800px";
    var columns = [
        		{"data": "flightCompanyCode"},
        		{"data": "flightCompanyName"},
        		{"data": "flightNum"},
        		{"data": "place"},
        		{"data": "irregularServiceCode"},
        		{"data": "irregularServiceName"},
        		{"data": "cause"},
        		{"data": "remedialMeasure"}
    ];

</script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.FlightBasic.Common.js"></script>
</body>
</html>
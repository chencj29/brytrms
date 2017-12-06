<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>损坏行李表</title>
    <c:set var="controllerUrl" value="rmsDamagedLuggage"/>
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
                <li class="active">损坏行李表</li>
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
                        <h1>损坏行李表</h1>

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
                                    <th>航班号</th>
                                    <th>姓名</th>
                                    <th>行李名称</th>
                                    <th>破损程度编号</th>
                                    <th>破损程度名称</th>
                                    <th>破损部位描述</th>
                                    <th>购买行李价格</th>
                                    <th>购买地点</th>
                                    <th>是否有发票</th>
                                    <th>处理方式</th>
                                    <th>赔偿金额</th>
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
					<label for="flightNum" class="col-sm-2 control-label">航班号</label>
					<div class="col-sm-3">
						<input id="flightNum" name="flightNum" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入航班号">
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-3">
						<input id="name" name="name" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入姓名">
					</div>
				</div>
				<div class="form-group">
					<label for="luggageName" class="col-sm-2 control-label">行李名称</label>
					<div class="col-sm-3">
						<input id="luggageName" name="luggageName" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入行李名称">
					</div>
				</div>
				<div class="form-group">
					<label for="damagedConditionCode" class="col-sm-2 control-label">破损程度编号</label>
					<div class="col-sm-3">
						<input id="damagedConditionCode" name="damagedConditionCode" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入破损程度编号">
					</div>
				</div>
				<div class="form-group">
					<label for="damagedConditionName" class="col-sm-2 control-label">破损程度名称</label>
					<div class="col-sm-3">
						<input id="damagedConditionName" name="damagedConditionName" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入破损程度名称">
					</div>
				</div>
				<div class="form-group">
					<label for="damagedPart" class="col-sm-2 control-label">破损部位描述</label>
					<div class="col-sm-3">
						<input id="damagedPart" name="damagedPart" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入破损部位描述">
					</div>
				</div>
				<div class="form-group">
					<label for="luggagePrice" class="col-sm-2 control-label">购买行李价格</label>
					<div class="col-sm-3">
						<input id="luggagePrice" name="luggagePrice" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入购买行李价格">
					</div>
				</div>
				<div class="form-group">
					<label for="purchasePrice" class="col-sm-2 control-label">购买地点</label>
					<div class="col-sm-3">
						<input id="purchasePrice" name="purchasePrice" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入购买地点">
					</div>
				</div>
				<div class="form-group">
					<label for="isreceipt" class="col-sm-2 control-label">是否有发票</label>
					<div class="col-sm-3">
						<input id="isreceipt" name="isreceipt" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入是否有发票">
					</div>
				</div>
				<div class="form-group">
					<label for="processingMode" class="col-sm-2 control-label">处理方式</label>
					<div class="col-sm-3">
						<input id="processingMode" name="processingMode" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入处理方式">
					</div>
				</div>
				<div class="form-group">
					<label for="indemnify" class="col-sm-2 control-label">赔偿金额</label>
					<div class="col-sm-3">
						<input id="indemnify" name="indemnify" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入赔偿金额">
					</div>
				</div>
				<input type="hidden" name="id" value="">
            </form>
        </div>
    </div>
</div>
<script>
     var modelName = "损坏行李表", width = "800px";
    var columns = [
        		{"data": "flightNum"},
        		{"data": "name"},
        		{"data": "luggageName"},
        		{"data": "damagedConditionCode"},
        		{"data": "damagedConditionName"},
        		{"data": "damagedPart"},
        		{"data": "luggagePrice"},
        		{"data": "purchasePrice"},
        		{"data": "isreceipt"},
        		{"data": "processingMode"},
        		{"data": "indemnify"}
    ];

</script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.FlightBasic.Common.js"></script>
</body>
</html>
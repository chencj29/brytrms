<%@ page contentType="text/html;charset=UTF-8"  language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>值机柜台基础信息表</title>
    <c:set var="controllerUrl" value="rmsCheckinCounter"/>
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
                <li class="active">值机柜台基础信息表</li>
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
                        <h1>值机柜台基础信息表</h1>

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
                                    <th>航站楼编号</th>
                                    <th>航站楼名称</th>
                                    <th>值机柜台编号</th>
                                    <th>值机柜台性质</th>
                                    <th>值机柜台状态</th>
                                    <th>值机柜台状态名称</th>
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
<script type="text/html" id="modifyForm">
    <div class="row" style="padding-top: 20px; margin: 0px;">
        <div class="col-md-12">
            <form class="form-horizontal" role="form" name="saveModifyForm" method="post"
                  action="${ctx}/rms/${controllerUrl}/save">
				<div class="form-group">
					<label for="airportTerminalCode" class="col-sm-2 control-label">航站楼编号</label>
					<div class="col-sm-3">
						<input id="airportTerminalCode" name="airportTerminalCode"
							data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入航站楼编号"
						 	value="{{data.airportTerminalCode}}">
					</div>
				</div>
				<div class="form-group">
					<label for="airportTerminalName" class="col-sm-2 control-label">航站楼名称</label>
					<div class="col-sm-3">
						<input id="airportTerminalName" name="airportTerminalName"
							data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入航站楼名称"
						 	value="{{data.airportTerminalName}}">
					</div>
				</div>
				<div class="form-group">
					<label for="checkinCounterNum" class="col-sm-2 control-label">值机柜台编号</label>
					<div class="col-sm-3">
						<input id="checkinCounterNum" name="checkinCounterNum"
							data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入值机柜台编号"
						 	value="{{data.checkinCounterNum}}">
					</div>
				</div>
				<div class="form-group">
					<label for="checkinCounterNature" class="col-sm-2 control-label">值机柜台性质</label>
					<div class="col-sm-3">
						<input id="checkinCounterNature" name="checkinCounterNature"
							data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入值机柜台性质"
						 	value="{{data.checkinCounterNature}}">
					</div>
				</div>
				<div class="form-group">
					<label for="checkinCounterTypeCode" class="col-sm-2 control-label">值机柜台状态</label>
					<div class="col-sm-3">
						<input id="checkinCounterTypeCode" name="checkinCounterTypeCode"
							data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入值机柜台状态"
						 	value="{{data.checkinCounterTypeCode}}">
					</div>
				</div>
				<div class="form-group">
					<label for="checkinCounterTypeName" class="col-sm-2 control-label">值机柜台状态名称</label>
					<div class="col-sm-3">
						<input id="checkinCounterTypeName" name="checkinCounterTypeName"
							data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" placeholder="请输入值机柜台状态名称"
						 	value="{{data.checkinCounterTypeName}}">
					</div>
				</div>
				<input type="hidden" name="id" value="{{data.id}}">
            </form>
        </div>
    </div>
</script>

<script>
    var modelName = "值机柜台基础信息表", width = "400px";
    var columns = [
        		{"data": "airportTerminalCode"},
        		{"data": "airportTerminalName"},
        		{"data": "checkinCounterNum"},
        		{"data": "checkinCounterNature"},
        		{"data": "checkinCounterTypeCode"},
    			{"data": "checkinCounterTypeName"}
    ];

</script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.FlightBasic.Common.js"></script>
</body>
</html>
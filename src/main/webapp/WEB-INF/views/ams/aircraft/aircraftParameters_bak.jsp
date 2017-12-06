<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>机型参数管理</title>
    <c:set var="controllerUrl" value="aircraftparameters"/>
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
                <li class="active">机型参数管理</li>
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
                        <h1>机型参数</h1>

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
                                    <th>机型</th>
                                    <th>主机型</th>
                                    <th>子机型</th>
                                    <th>备用梯</th>
                                    <th>是否用桥</th>
                                    <th>供电方式</th>
                                    <th>燃料补充方式</th>
                                    <th>最大座位数</th>
                                    <th>最大载货量</th>
                                    <th>最大加油量</th>
                                    <th>翼展</th>
                                    <th>机身长</th>
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
                    <label for="aircraftModel" class="col-sm-2 control-label">机型</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" id="aircraftModel"
                               name="aircraftModel" placeholder="请输入机型">
                    </div>
                    <label for="aircraftMainModel" class="col-sm-2 control-label">主机型</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[20]]" id="aircraftMainModel"
                               name="aircraftMainModel" placeholder="请输入主机型">
                    </div>
                    <label for="aircraftSubModel" class="col-sm-2 control-label">子机型</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[20]]"
                               id="aircraftSubModel" name="aircraftSubModel" placeholder="请输入子机型">
                    </div>
                </div>
                <div class="form-group">
                    <label for="useLift" class="col-sm-2 control-label">备用梯</label>

                    <div class="controls col-sm-4">
                        <label class="radio inline col-sm-2">
                            <input type="radio" value="1" checked="checked" name="useLift">
                            是
                        </label>
                        <label class="radio inline  col-sm-2">
                            <input type="radio" value="0" name="useLift">
                            否
                        </label>
                    </div>

                    <label for="useBridge" class="col-sm-2 control-label">是否用桥</label>

                    <div class="controls col-sm-4">
                        <label class="radio inline col-sm-2">
                            <input type="radio" value="1" checked="checked" name="useBridge">
                            是
                        </label>
                        <label class="radio inline  col-sm-2">
                            <input type="radio" value="0" name="useBridge">
                            否
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="powerTypeCode" class="col-sm-2 control-label">供电方式</label>

                    <div class="col-sm-2">
                        <select class="form-control dict" id="powerTypeCode" name="powerTypeCode">
                            <c:forEach items="${fns:getDictListByTableNameType('ams_aircraft_parameters','power_type')}" var="item">
                                <option value="${item.value}">${item.label}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" id="powerTypeName" name="powerTypeName" value="">
                    </div>
                    <label for="fuelTypeCode" class="col-sm-2 control-label">燃料补充方式</label>

                    <div class="col-sm-2">
                        <select class="form-control dict" id="fuelTypeCode" name="fuelTypeCode">
                            <c:forEach items="${fns:getDictListByTableNameType('ams_aircraft_parameters','fuel_type')}" var="item">
                                <option value="${item.value}">${item.label}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" id="fuelTypeName" name="fuelTypeName" value="">
                    </div>

                    <label for="maxnumSeat" class="col-sm-2 control-label">最大座位数</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[integer],minSize[1],maxSize[10]]"
                               id="maxnumSeat" name="maxnumSeat" placeholder="请输入最大座位数">
                    </div>
                </div>
                <div class="form-group">
                    <label for="maxnumCargoes" class="col-sm-2 control-label">最大载货量</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[20]]"
                               id="maxnumCargoes" name="maxnumCargoes" placeholder="请输入最大载货量">
                    </div>
                    <label for="maxnumAmount" class="col-sm-2 control-label">最大加油量</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               id="maxnumAmount" name="maxnumAmount" placeholder="请输入最大加油量">
                    </div>
                    <label for="wingspan" class="col-sm-2 control-label">翼展</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[20]]"
                               id="wingspan" name="wingspan" placeholder="请输入翼展">
                    </div>
                </div>
                <div class="form-group">
                    <label for="fuselageLength" class="col-sm-2 control-label">机身长</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               id="fuselageLength" name="fuselageLength" placeholder="请输入机身长">
                    </div>
                    <label for="frontWheelLength" class="col-sm-2 control-label">鼻轮前长</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[20]]"
                               id="frontWheelLength" name="frontWheelLength" placeholder="请输入鼻轮前长">
                    </div>

                    <label for="fuselageHeight" class="col-sm-2 control-label">高度</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               id="fuselageHeight" name="fuselageHeight" placeholder="请输入高度">
                    </div>
                </div>
                <div class="form-group">
                    <label for="aircraftWeight" class="col-sm-2 control-label">空重</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[20]]"
                               id="aircraftWeight" name="aircraftWeight" placeholder="请输入空重">
                    </div>

                    <label for="zeroFuelWeight" class="col-sm-2 control-label">无油重量</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               id="zeroFuelWeight" name="zeroFuelWeight" placeholder="请输入无油重量">
                    </div>

                    <label for="wheelsWeight" class="col-sm-2 control-label">着陆重量</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[20]]"
                               id="wheelsWeight" name="wheelsWeight" placeholder="请输入着陆重量">
                    </div>
                </div>
                <div class="form-group">
                    <label for="maxnumPlayload" class="col-sm-2 control-label">最大业载</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               id="maxnumPlayload" name="maxnumPlayload" placeholder="请输入最大业载">
                    </div>
                    <label for="maxnumTakeoffWeight" class="col-sm-2 control-label">最大起飞重量</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[20]]"
                               id="maxnumTakeoffWeight" name="maxnumTakeoffWeight" placeholder="请输入最大起飞重量">
                    </div>

                    <label for="cruisingAltitude" class="col-sm-2 control-label">巡航高度</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               id="cruisingAltitude" name="cruisingAltitude" placeholder="请输入巡航高度">
                    </div>
                </div>
                <div class="form-group">
                    <label for="maxnumRange" class="col-sm-2 control-label">最大航程</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[20]]"
                               id="maxnumRange" name="maxnumRange" placeholder="请输入最大航程">
                    </div>

                    <label for="runwayLength" class="col-sm-2 control-label">跑道长度</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               id="runwayLength" name="runwayLength" placeholder="请输入跑道长度">
                    </div>
                    <label for="acnRigid" class="col-sm-2 control-label">ACN刚性</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[20]]"
                               id="acnRigid" name="acnRigid" placeholder="请输入ACN刚性">
                    </div>
                </div>
                <div class="form-group">
                    <label for="acnFlexible" class="col-sm-2 control-label">ACN柔性</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               id="acnFlexible" name="acnFlexible" placeholder="请输入ACN柔性">
                    </div>

                    <label for="engineNum" class="col-sm-2 control-label">发动机数量</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[20]]"
                               id="engineNum" name="engineNum" placeholder="请输入发动机数量">
                    </div>
                    <label for="engineType" class="col-sm-2 control-label">发动机型号</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               id="engineType" name="engineType" placeholder="请输入发动机型号">
                    </div>
                </div>
                <div class="form-group">
                    <label for="engineManufacturer" class="col-sm-2 control-label">生产商</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[20]]"
                               id="engineManufacturer" name="engineManufacturer" placeholder="请输入生产商">
                    </div>
                    <label for="lostofStopTime" class="col-sm-2 control-label">最少停留时间</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               id="lostofStopTime" name="lostofStopTime" placeholder="请输入最少停留时间">
                    </div>
                    <label for="articraftTypeName" class="col-sm-2 control-label">机型分类</label>

                    <div class="col-sm-2">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[20]]"
                               id="articraftTypeName" name="articraftTypeName" placeholder="请输入生产商">
                    </div>
                </div>
                <input type="hidden" name="id" value="">
            </form>
        </div>
    </div>
</div>

<script>
    var modelName = "机型参数", width = "800px";
    var columns = [
        {"data": "aircraftModel"},
        {"data": "aircraftMainModel"},
        {"data": "aircraftSubModel"},
        {"data": "useLift"},
        {"data": "useBridge"},
        {"data": "powerTypeName"},
        {"data": "fuelTypeName"},
        {"data": "maxnumSeat"},
        {"data": "maxnumCargoes"},
        {"data": "maxnumAmount"},
        {"data": "wingspan"},
        {"data": "fuselageLength"}
    ];

</script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.FlightBasic.Common.js"></script>
</body>
</html>

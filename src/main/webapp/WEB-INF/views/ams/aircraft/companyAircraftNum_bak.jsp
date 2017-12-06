<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>公司机号管理</title>
    <c:set var="controllerUrl" value="companyaircraftnum"/>
    <link rel="stylesheet" href="${ctxAssets}/scripts/select2/dist/css/select2.min.css">
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
                <li class="active">公司机号管理</li>
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
                        <h1>公司机号管理</h1>

                        <div class="controls">
                            <a href="javascript:void(0);" id="refresh_datatbles" class="refresh"><i class="fa fa-refresh"></i></a>
                        </div>
                    </div>
                    <div class="tile-body color transparent-white rounded-corners">
                        <div class="table-responsive">
                            <table class="table display nowrap  table-custom table-datatable table-hover"
                                   id="modifyDataTable" width="100%">
                                <thead>
                                <tr>
                                    <th>航空公司</th>
                                    <th>子公司名称</th>
                                    <th>飞机型号</th>
                                    <th>飞机主型号</th>
                                    <th>飞机子型号</th>
                                    <th>起飞重量</th>
                                    <th>业务载重</th>
                                    <th>客座数</th>
                                    <th>飞机类型</th>
                                    <th>飞机编码</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </div>
        <div class="row">
            <!-- 添加航空公司弹出框 -->
            <div class="airlineInfoForm" id="modifyForm" style="display:none;">
                <div class="row" style="padding-top: 20px; margin: 0px;">
                    <div class="col-md-12">
                        <!--航空公司添加&修改表单 ~ see ./fragments/bacisData/airlineInfo/airlineInfoForm.html-->
                        <form class="form-horizontal" role="form" name="saveModifyForm" method="post"
                              action="${ctx}/ams/${controllerUrl}/save">
                            <div class="form-group">
                                <label for="flightCompanyName" class="col-sm-3 control-label">航空公司名称</label>

                                <div class="col-sm-9">
                                    <select class="form-control" id="flightCompanyCode" name="flightCompanyCode"></select>
                                    <input type="hidden" id="flightCompanyName" name="flightCompanyName" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="flightCompanySubCode" class="col-sm-3 control-label">子公司名称</label>

                                <div class="col-sm-9">
                                    <select class="form-control" class="form-control" id="flightCompanySubCode" name="flightCompanySubCode"></select>
                                    <input type="hidden" id="flightCompanySubName" name="flightCompanySubName" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="aircraftModel" class="col-sm-3 control-label">飞机型号</label>

                                <div class="col-sm-9">
                                    <select class="form-control" class="form-control" id="aircraftParametersId" name="aircraftParametersId"></select>
                                    <input type="hidden" name="aircraftModel" id="aircraftModel" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="aircraftMainModel" class="col-sm-3 control-label">飞机主型号</label>

                                <div class="col-sm-9">
                                    <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[50]]" id="aircraftMainModel"
                                           name="aircraftMainModel" placeholder="请输入飞机主型号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="aircraftSubModel" class="col-sm-3 control-label">飞机子型号</label>

                                <div class="col-sm-9">
                                    <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[50]]" id="aircraftSubModel"
                                           name="aircraftSubModel" placeholder="请输入飞机子型号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="aircraftTypeName" class="col-sm-3 control-label">飞机类型</label>

                                <div class="col-sm-9">
                                    <select class="form-control dict" id="aircraftTypeCode" name="aircraftTypeCode">
                                        <c:forEach items="${fns:getDictListByTableNameType('company_aircraft_num','aircraft_type')}" var="item">
                                            <option value="${item.value}">${item.label}</option>
                                        </c:forEach>
                                    </select>
                                    <%--<input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[50]]" id="aircraftTypeName"
                                           name="aircraftTypeName" placeholder="请输入飞机子型号">--%>
                                    <input type="hidden" id="aircraftTypeName" name="aircraftTypeName" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="aircraftNum" class="col-sm-3 control-label">飞机号码</label>

                                <div class="col-sm-9">
                                    <input type="text" class="form-control" data-validation-engine="validate[required,custom[number],minSize[1],maxSize[50]]" id="aircraftNum"
                                           name="aircraftNum" placeholder="请输入飞机号码">
                                </div>
                            </div>
                            <input type="hidden" name="id" value="">
                            <input type="hidden" name="maxnumTakeoffWeight" value="">
                            <input type="hidden" name="maxnumPlayload" value="">
                            <input type="hidden" name="maxnumSeat" value="">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--正文内容结束-->
</div>

<script src="${ctxAssets}/scripts/select2/dist/js/select2.full.min.js"></script>
<script src="${ctxAssets}/scripts/select2/dist/js/i18n/zh-CN.js"></script>
<script>
    //init select2 layer callback
    var flightCompanySubSelect2Data = new Array();
    function layerShowSuccessCallBack(backData) {
        // 选择航空公司
        var flightCompanySelect2 = $("select[name='flightCompanyCode']").select2({
            dropdownParent: $("select[name='flightCompanyCode']").parent(),
            language: "zh-CN",
            ajax: {
                url: "${ctx}/ams/flightcompany/list",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        searchText: params.term,
                        pageNo: params.page
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;
                    return {
                        results: data.data,
                        pagination: {
                            more: (params.page * 30) < data.recordsTotal
                        }
                    };
                },
                cache: true
            },
            escapeMarkup: function (markup) {
                return markup;
            },
            minimumInputLength: 1,
            templateResult: function (repo) {
                if (repo.loading) return repo.text;
                return repo.chineseName;
            },
            templateSelection: function (repo) {
                return repo.chineseName;
            }
        });

        //event
        flightCompanySelect2.on("select2:select", function (e) {
            var data = e.params.data;
            $("#flightCompanyName").val(data.chineseName);

            // init sub
            $.getJSON("${ctx}/ams/flightcompanysub/list", {"flight_company_info_id": data.id}, function (resp) {
                var options = "";
                $(resp.data).each(function (index, rec) {
                    options += "<option value='" + rec.id + "'>" + rec.chineseName + "</option>";
                });
                // init sub
                $("select[name='flightCompanySubCode']").html(options).change();
            });
        });

        // 设置子公司名称
        $("#flightCompanySubCode").change(function () {
            $("#flightCompanySubName").val($(this).find("option:selected").text());
        });


        //飞机型号
        // 选择航空公司
        var aircraftModelSelect2 = $("select[name='aircraftParametersId']").select2({
            dropdownParent: $("select[name='aircraftParametersId']").parent(),
            language: "zh-CN",
            ajax: {
                url: "${ctx}/ams/aircraftparameters/list",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        searchText: params.term,
                        pageNo: params.page
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;
                    return {
                        results: data.data,
                        pagination: {
                            more: (params.page * 30) < data.recordsTotal
                        }
                    };
                },
                cache: true
            },
            escapeMarkup: function (markup) {
                return markup;
            },
            minimumInputLength: 1,
            templateResult: function (repo) {
                if (repo.loading) return repo.text;
                return repo.aircraftModel;
            },
            templateSelection: function (repo) {
                return repo.aircraftModel;
            }
        });

        //
        aircraftModelSelect2.on("select2:select", function (e) {
            var data = e.params.data;
            data.id = null;
            $utils.initFormHTML("form[name='saveModifyForm']", data);
        });


        // back data
        if (typeof(backData) != 'undefined' && $(backData).length > 0) {
            flightCompanySelect2.val(backData.flightCompanyName).change();
        }

    }
    // 航空公司子公司


    // init datatables
    var modelName = "公司机号";
    var columns = [
        {"data": "flightCompanyName"},
        {"data": "flightCompanySubName"},
        {"data": "aircraftModel"},
        {"data": "aircraftMainModel"},
        {"data": "aircraftSubModel"},
        {"data": "maxnumTakeoffWeight"},
        {"data": "maxnumPlayload"},
        {"data": "maxnumSeat"},
        {"data": "aircraftTypeName"},
        {"data": "aircraftNum"}
    ];
</script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.FlightBasic.Common.js"></script>
</body>
</html>

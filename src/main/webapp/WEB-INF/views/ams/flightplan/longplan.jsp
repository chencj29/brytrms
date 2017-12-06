<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>机型参数管理</title>
    <c:set var="controllerUrl" value="shareflight"/>
    <link rel="stylesheet" href="${ctxAssets}/scripts/select2/dist/css/select2.min.css">
</head>
<body>
<div id="content" class="col-md-12">
    <!--正文头部开始-->
    <div class="pageheader">
        <h2>航班计划</h2>

        <div class="breadcrumbs">
            <ol class="breadcrumb">
                <li><a href="###">资源管理系统</a></li>
                <li><a href="###">航班计划管理</a></li>
                <li class="active">共享航班管理</li>
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
                        <h1>共享航班</h1>

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
                                    <th>航空公司(被共享)</th>
                                    <th>航班号(被共享)</th>
                                    <th>航空公司(共享)</th>
                                    <th>航班号(共享)</th>
                                    <th>目的地(共享)</th>
                                    <th>班期</th>
                                    <th>进出港</th>
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
                    <label for="masterAirlineCode" class="col-sm-4 control-label">航空公司(被公司)</label>

                    <div class="col-sm-8">
                        <%--<input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]" id="masterAirline"
                               name="masterAirline" placeholder="请输入航空公司(被公司)">--%>
                        <select class="form-control" id="masterAirlineCode" name="masterAirlineCode"></select>
                        <input type="hidden" id="masterAirlineName" name="masterAirlineName" value="">
                    </div>
                </div>
                <div class="form-group">
                    <label for="masterFlightNo" class="col-sm-4 control-label">航班号(被共享)</label>

                    <div class="col-sm-8">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[20]]" id="masterFlightNo"
                               name="masterFlightNo" placeholder="请输入航班号(被共享)">
                    </div>
                </div>
                <div class="form-group">
                    <label for="slaveAirlineCode" class="col-sm-4 control-label">航空公司(共享)</label>

                    <div class="col-sm-8">
                        <%-- <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[20]]"
                                id="slaveAirline" name="slaveAirline" placeholder="请输入航空公司(共享)">--%>
                        <select class="form-control" id="slaveAirlineCode" name="slaveAirlineCode"></select>
                        <input type="hidden" id="slaveAirlineName" name="slaveAirlineName" value="">
                    </div>
                </div>
                <div class="form-group">
                    <label for="slaveFlightNo" class="col-sm-4 control-label">航班号(共享)</label>

                    <div class="col-sm-8">
                         <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[20]]"
                                id="slaveFlightNo" name="slaveFlightNo" placeholder="航班号(共享)">
                    </div>
                </div>
                <div class="form-group">
                    <label for="daysOpt" class="col-sm-4 control-label">班期</label>

                    <div class="controls col-sm-8">
                        <label class="checkbox inline"></label>
                        <input name="daysOpt" type="checkbox" value="1">周一
                        <label class="checkbox inline"></label>
                        <input name="daysOpt" type="checkbox" value="2">周二
                        <label class="checkbox inline"></label>
                        <input name="daysOpt" type="checkbox" value="3">周三
                        <label class="checkbox inline"></label>
                        <input type="checkbox" value="4">周四
                        <label class="checkbox inline"></label>
                        <input name="daysOpt" type="checkbox" value="5">周五
                        <label class="checkbox inline"></label>
                        <input name="daysOpt" type="checkbox" value="6">周六
                        <label class="checkbox inline"></label>
                        <input name="daysOpt" type="checkbox" value="7">周日
                    </div>
                </div>
                <div class="form-group">
                    <label for="destination" class="col-sm-4 control-label">目的地</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[20]]"
                               id="destination" name="destination" placeholder="请输入航班号(被共享)">
                    </div>
                </div>
                <div class="form-group">
                    <label for="ioOptCode" class="col-sm-4 control-label">进出港</label>
                    <div class="col-sm-8">
                        <select class="form-control dict" id="ioOptCode" name="ioOptCode">
                            <c:forEach items="${fns:getDictListByTableNameType('sys','inout')}" var="item">
                                <option value="${item.value}">${item.label}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="ioOptName" id="ioOptName" value="">
                    </div>
                </div>
                <input type="hidden" name="id" value="">
            </form>
        </div>
    </div>
</div>


<script src="${ctxAssets}/scripts/select2/dist/js/select2.full.min.js"></script>
<script src="${ctxAssets}/scripts/select2/dist/js/i18n/zh-CN.js"></script>
<script>
    // layer show succ callback
    function layerShowSuccessCallBack(backData) {
        // 选择航空公司
        var masterAirlineSelect2 = $("select[name='masterAirlineCode']").select2({
            dropdownParent: $("select[name='masterAirlineCode']").parent(),
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
        // select2 on selected
        masterAirlineSelect2.on("select2:select", function (e) {
            var data = e.params.data;
            $("input[name='masterAirlineName']").val(data.chineseName);
        });


        // 选择航空公司
        var slaveAirlineSelect2 = $("select[name='slaveAirlineCode']").select2({
            dropdownParent: $("select[name='slaveAirlineCode']").parent(),
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
        // select2 on selected
        slaveAirlineSelect2.on("select2:select", function (e) {
            var data = e.params.data;
            $("input[name='slaveAirlineName']").val(data.chineseName);
        });

    }


    var modelName = "共享航班", width = "550px";
    var columns = [
        {"data": "masterAirlineName"},
        {"data": "masterFlightNo"},
        {"data": "slaveAirlineName"},
        {"data": "slaveFlightNo"},
        {"data": "destination"},
        {"data": "daysOpt"},
        {"data": "ioOptName"}
    ];

</script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.FlightBasic.Common.js"></script>
</body>
</html>

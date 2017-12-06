<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>航空公司信息管理</title>
</head>
<body>
<div id="content" class="col-md-12">
    <!--正文头部开始-->
<%--    <div class="pageheader">
        <h2>基础数据</h2>

        <div class="breadcrumbs">
            <ol class="breadcrumb">
                <li><a href="###">航班管理系统</a></li>
                <li><a href="###">基础数据</a></li>
                <li class="active">航空公司信息管理</li>
            </ol>
        </div>
    </div>--%>
    <!--正文头部结束-->

    <!--正文内容开始-->
    <div class="main">
        <div class="row">
            <div class="col-md-12">
                <section class="tile transparent">
                    <div class="tile-header transparent">
                        <h1>航空公司</h1>

                        <div class="controls">
                            <a href="#" class="refresh"><i class="fa fa-refresh"></i></a>
                        </div>
                    </div>
                    <div class="tile-body color transparent-white rounded-corners">
                        <div class="table-responsive">
                            <table class="table display  table-custom table-datatable table-hover"
                                   id="airlineInfoDataTable">
                                <thead>
                                <tr>
                                    <th>二字码</th>
                                    <th>中文简称</th>
                                    <th>中文名称</th>
                                    <th>英文名称</th>
                                    <th>是否外航</th>
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
            <div class="col-md-12">
                <section class="tile transparent">
                    <div class="tile-header transparent">
                        <h1>航空公司子公司</h1>

                        <div class="controls">
                            <a href="#" class="refresh"><i class="fa fa-refresh"></i></a>
                        </div>
                    </div>
                    <div class="tile-body color transparent-white rounded-corners">
                        <div class="table-responsive">
                            <table class="table display  table-custom table-datatable table-hover"
                                   id="subAirlineInfoDataTable">
                                <thead>
                                <tr>
                                    <th>子公司代码</th>
                                    <th>中文名称</th>
                                    <th>英文名称</th>
                                    <th>是否基地</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
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
<div class="airlineInfoForm" id="flightCompanyInfoForm" style="display:none;">
    <div class="row" style="padding-top: 20px; margin: 0px;">
        <div class="col-md-12">
            <!--航空公司添加&修改表单 ~ see ./fragments/bacisData/airlineInfo/airlineInfoForm.html-->
            <form class="form-horizontal" role="form" name="saveFlightCompanyForm" method="post"
                  action="${ctx}/ams/flightcompany/save">
                <div class="form-group">
                    <label for="chineseName" class="col-sm-3 control-label">中文名称</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[chinese],minSize[2],maxSize[100]]" id="chineseName"
                               name="chineseName" placeholder="请输入中文名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="chineseShort" class="col-sm-3 control-label">中文简称</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[chinese],minSize[2],maxSize[20]]" id="chineseShort"
                               name="chineseShort" placeholder="请输入中文简称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="twoCode" class="col-sm-3 control-label">二字码</label>

                    <div class="col-sm-3">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[onlyLetterSp],minSize[2],maxSize[2]]" id="twoCode" name="twoCode"
                               placeholder="请输入二字码">
                    </div>
                    <label for="threeCode" class="col-sm-3 control-label">三字码</label>

                    <div class="col-sm-3">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[onlyLetterSp],minSize[3],maxSize[3]]" id="threeCode"
                               name="threeCode" placeholder="请输入三字码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="englishName" class="col-sm-3 control-label">英文名称</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[onlyLetterSp],minSize[1],maxSize[30]]" id="englishName"
                               name="englishName" placeholder="请输入英文名称">
                    </div>
                </div>
                <input type="hidden" name="id" value="">
            </form>
        </div>
    </div>
</div>
<div class="airlineInfoForm" id="flightCompanySubForm" style="display:none;">
    <div class="row" style="padding-top: 20px; margin: 0px;">
        <div class="col-md-12">
            <!--航空公司添加&修改表单 ~ see ./fragments/bacisData/airlineInfo/airlineInfoForm.html-->
            <form class="form-horizontal" role="form" name="saveFlightCompanySubForm" method="post"
                  action="${ctx}/ams/flightcompanysub/save">
                <div class="form-group">
                    <label for="chineseName" class="col-sm-3 control-label">中文名称</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[chinese],minSize[2],maxSize[100]]"
                               name="chineseName" placeholder="请输入中文名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="subCode" class="col-sm-3 control-label">编码</label>

                    <div class="col-sm-3">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[onlyLetterNumber],minSize[1],maxSize[10]]" name="subCode"
                               placeholder="请输入编码">
                    </div>
                    <label for="threeCode" class="col-sm-3 control-label">三字码</label>

                    <div class="col-sm-3">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[onlyLetterSp],minSize[3],maxSize[3]]"
                               name="threeCode" placeholder="请输入三字码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="englishName" class="col-sm-3 control-label">英文名称</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" data-validation-engine="validate[required,custom[onlyLetterSp],minSize[1],maxSize[30]]"
                               name="englishName" placeholder="请输入英文名称">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">是否基地</label>

                    <div class="controls col-sm-9">
                        <label class="radio inline col-sm-4">
                            <input type="radio" value="1" checked="checked" name="isbase">
                            是
                        </label>
                        <label class="radio inline  col-sm-4">
                            <input type="radio" value="0" name="isbase">
                            否
                        </label>
                    </div>
                </div>
                <input type="hidden" name="id" value="">
            </form>
        </div>
    </div>
</div>
<script>
    var flightCompanyInfoHtml, flightCompanySubinfo;
    $(function () {
        // validation
        flightCompanyInfoHtml = $("#flightCompanyInfoForm").clone().html();
        $("#flightCompanyInfoForm").remove();
        //
        flightCompanySubinfo = $("#flightCompanySubForm").clone().html();
        $("#flightCompanySubForm").remove();

        airlineInfoDataTable = $("#airlineInfoDataTable").DataTable({
            select: 'single',
            buttons: [{
                text: '添加航空公司',
                action: function (evt, dt, node, config) {
                    saveOrUpdateForMain();
                }
            }],
            columnDefs: [{
                targets: 5,
                render: function (data, type, row, dt) {
                    return "<a href='javascript:void(0);' data-row-index='" + dt.row
                            + "' name='airlineInfoModifyBtn'>编辑</a> &nbsp;&nbsp;"
                            + "<a href='javascript:void(0);' data-row-index='" + dt.row
                            + "' name='airlineInfoDeleteBtn'>删除</a>";
                }
            }],
            "processing": true,
            "serverSide": true,
            "ajax": "${ctx}/ams/flightcompany/list",
            "columns": [
                {"data": "twoCode"},
                {"data": "chineseShort"},
                {"data": "chineseName"},
                {"data": "englishName"},
                {"data": "otherFlight"}
            ]
        }).on('select', function (evt, dt, type, indexes) {
            var data = airlineInfoDataTable.row(indexes).data(), detailDataFilePath = "${ctx}/ams/flightcompanysub/list";
            subAirlineInfoDataTable.settings({serverSide: true}).ajax.url(detailDataFilePath + "?flight_company_info_id=" + data.id).load().draw();
        });

        subAirlineInfoDataTable = $("#subAirlineInfoDataTable").DataTable({
            select: 'single', buttons: [{
                text: '添加航空子公司',
                action: function (evt, dt, node, config) {
                    flightCompanySelected = airlineInfoDataTable.row({selected: true});
                    if (flightCompanySelected.count()) {
                        saveOrUpdateSub();
                    } else {
                        layer.msg("请先选择一条航空公司数据再进行添加航空子公司操作", {icon: 2});
                    }
                }
            }],
            columnDefs: [{
                targets: 4,
                render: function (data, type, row, dt) {
                    return "<a href='javascript:void(0);' data-row-index='" + dt.row + "' name='subAirlineInfoModifyBtn'>编辑</a> &nbsp;&nbsp;"
                            + "<a href='javascript:void(0);' data-row-index='" + dt.row + "' name='subAirlineInfoDeleteBtn'>删除</a>";
                }
            }],
            "processing": true,
            "retrieve": true,
            //"serverSide": true,
            //"ajax":"${ctx}/ams/flightcompanysub/list?flight_company_info_id=-1",
            "columns": [
                {"data": "subCode"},
                {"data": "threeCode"},
                {"data": "chineseName"},
                {"data": "englishName"}
            ]
        });


        //
        $('body').on('click', 'a[name=airlineInfoModifyBtn]', function (e) {
            var data = airlineInfoDataTable.data()[$(this).data()['rowIndex']];
            saveOrUpdateForMain(data);
        }).on('click', 'a[name=airlineInfoDeleteBtn]', function (e) {
            var data = airlineInfoDataTable.data()[$(this).data()['rowIndex']];
            layer.confirm("确定删除该条数据?(下属子公司将被一并删除)", {
                btn: ["确定", "取消"]
            }, function () {
                $.post("${ctx}/ams/flightcompany/delete", {"id": data.id}, function (resp) {
                    if (resp && resp.code == 1) {
                        layer.msg("删除成功");
                        airlineInfoDataTable.ajax.reload();
                        subAirlineInfoDataTable.ajax.reload();
                    } else {
                        layer.alert(resp.message);
                    }
                })
            });
        }).on('click', 'a[name=subAirlineInfoModifyBtn]', function (e) {
            var data = subAirlineInfoDataTable.data()[$(this).data()['rowIndex']];
            saveOrUpdateSub(data);
        }).on('click', 'a[name=subAirlineInfoDeleteBtn]', function (e) {
            var data = subAirlineInfoDataTable.data()[$(this).data()['rowIndex']];
            layer.confirm("确定删除该条数据?", {
                btn: ["确定", "取消"]
            }, function () {
                $.post("${ctx}/ams/flightcompanysub/delete", {"id": data.id}, function (resp) {
                    if (resp && resp.code == 1) {
                        layer.msg("删除成功");
                        subAirlineInfoDataTable.ajax.reload();
                    } else {
                        layer.alert(resp.message);
                    }
                })
            });
        });
    });

    /**
     * 新增或修改航空公司信息
     * @param data
     */
    function saveOrUpdateForMain(data) {
        var title = "新增航空公司信息";
        if (data && $(data).length > 0) {
            title = "修改航空公司信息";
        }
        var index = layer.open({
            title: title,
            type: 1, closeBtn: 1, shift: 2,
            area: '500px',
            shadeClose: false,
            content: flightCompanyInfoHtml,
            success: function () {
                $("form[name='saveFlightCompanyForm']").validationEngine({
                    showOneMessage: true,
                    promptPosition: 'bottomLeft'
                })
                //修改
                if (data && $(data).length > 0) {
                    $("form[name='saveFlightCompanyForm']").children("input[name='id']").val(data.id);
                    //
                    $utils.initFormHTML("form[name='saveFlightCompanyForm']", data);
                }
            },
            btn: ['保存', '取消'],
            yes: function () {
                var valid = $("form[name='saveFlightCompanyForm']").validationEngine("validate");
                if (valid) {
                    var form = $("form[name='saveFlightCompanyForm']");
                    $.post(form.attr("action"), form.serialize(), function (resp) {
                        if (resp && resp.code == 1) {
                            layer.close(index);
                            airlineInfoDataTable.ajax.reload();
                        } else {
                            alert(resp.message);
                            return false;
                        }
                    });
                }
            }
        });
    }


    /**
     * 子公司的增删改查
     * @param data
     */
    function saveOrUpdateSub(data) {
        var title = "新增航空子公司信息";
        if (data && $(data).length > 0) {
            title = "修改航空子公司信息";
        }
        var index2 = layer.open({
            title: title,
            type: 1, closeBtn: 1, shift: 2,
            area: "500px",
            shadeClose: false,
            content: flightCompanySubinfo,
            btn: ['保存', '取消'],
            success: function () {
                $("form[name='saveFlightCompanySubForm']").validationEngine({
                    showOneMessage: true,
                    promptPosition: 'bottomLeft'
                });
                //修改
                if (data && $(data).length > 0) {
                    $("form[name='saveFlightCompanySubForm']").children("input[name='id']").val(data.id);
                    //
                    $utils.initFormHTML("form[name='saveFlightCompanySubForm']", data);
                }
            },
            yes: function () {
                var valid = $("form[name='saveFlightCompanySubForm']").validationEngine("validate");
                if (valid) {
                    flightCompanySelected = airlineInfoDataTable.row({selected: true});
                    var form = $("form[name='saveFlightCompanySubForm']");
                    var datas = form.serialize() + "&flightCompanyInfo.id=" + flightCompanySelected.data().id;
                    $.post(form.attr("action"), datas, function (resp) {
                        if (resp && resp.code == 1) {
                            layer.close(index2);
                            subAirlineInfoDataTable.ajax.reload();
                        } else {
                            alert(resp.message);
                            return false;
                        }
                    });
                }
            }
        });
    }
</script>
</body>
</html>

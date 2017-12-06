<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>机位信息管理</title>
    <c:set var="controllerUrl" value="aircraftStand"/>
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
                <li class="active">机位信息管理</li>
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
                        <h1>机位信息</h1>

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
                                    <th>机位号</th>
                                    <th>机坪号</th>
                                    <th>机坪调度者</th>
                                    <th>是否有廊桥</th>
                                    <th>廊桥状态</th>
                                    <th>是否有油管</th>
                                    <th>油管状态</th>
                                    <th>左机位号</th>
                                    <th>右机位号</th>
                                    <th>翼展</th>
                                    <th>是否可用</th>
                                    <th>所属航站楼</th>
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
<script type="text/html" id="modifyForm">
    <div class="row" style="padding-top: 20px; margin: 0px;">
        <div class="col-md-12">
            <form class="form-horizontal" role="form" name="saveModifyForm" method="post" action="${ctx}/rms/${controllerUrl}/save">
                <div class="form-group">
                    <label for="aircraftStandNum" class="col-sm-2 control-label">机位号</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="aircraftStandNum" name="aircraftStandNum" placeholder="请输入机位号"
                               data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[36]]"
                               value="{{data.aircraftStandNum}}">
                    </div>

                    <label for="aircraftParkNum" class="col-sm-2 control-label">机坪号</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="aircraftParkNum" name="aircraftParkNum" placeholder="请输入机坪号"
                               data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[10]]"
                               value="{{data.aircraftParkNum}}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="aircraftParkDispatcher" class="col-sm-2 control-label">机坪调度者</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="aircraftParkDispatcher" name="aircraftParkDispatcher"
                               placeholder="请输入机坪调度者" value="{{data.aircraftParkDispatcher}}"
                               data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[50]]">
                    </div>

                    <label for="airportTerminalCode" class="col-sm-2 control-label">所属航站楼</label>
                    <div class="col-sm-3">
                        <select id="airportTerminalCode" name="airportTerminalCode" class="form-control"
                                data-validation-engine="validate[required]" data-errormessage-value-missing="请选择航站楼">
                            <option value=""></option>
                            {{ each terminals as value }}
                            <option value="{{value}}" {{ if value=== data.airportTerminalCode}} selected {{ /if }}>{{value}}</option>
                            {{ /each }}
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="hasSalon" class="col-sm-2 control-label">是否有廊桥</label>
                    <div class="controls col-sm-3">
                        <label class="checkbox-inline" style=" padding-left: 0;">
                            <input type="radio" value="1" checked="checked" name="hasSalon"
                                   {{ if data.hasSalon == 1 }} checked {{ /if }}>
                            有
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" value="0" name="hasSalon"
                                   {{ if data.hasSalon == 0 }} checked {{ /if }}>
                            没有
                        </label>

                    </div>

                    <label for="salonStatus" class="col-sm-2 control-label">廊桥状态</label>
                    <div class="controls col-sm-3">

                        <label class="checkbox-inline" style=" padding-left: 0;">
                            <input type="radio" value="1" checked="checked" name="salonStatus"
                                   {{ if data.salonStatus == 1 }} checked {{ /if }}>
                            可用
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" value="0" name="salonStatus"
                                   {{ if data.salonStatus == 0 }} checked {{ /if }}>
                            不可用
                        </label>

                    </div>
                </div>

                <div class="form-group">
                    <label for="hasOilpipeline" class="col-sm-2 control-label">是否有油管</label>
                    <div class="controls col-sm-3">
                        <label class="checkbox-inline" style=" padding-left: 0;">
                            <input type="radio" value="1" checked="checked" name="hasOilpipeline"
                                   {{ if data.hasOilpipeline == 1 }} checked {{ /if }}>
                            有
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" value="0" name="hasOilpipeline"
                                   {{ if data.hasOilpipeline == 0 }} checked {{ /if }}>
                            没有
                        </label>

                    </div>

                    <label for="oilpipelineStatus" class="col-sm-2 control-label">油管状态</label>
                    <div class="controls col-sm-3">
                        <label class="checkbox-inline" style=" padding-left: 0;">
                            <input type="radio" value="1" checked="checked" name="oilpipelineStatus"
                                   {{ if data.oilpipelineStatus == 1 }} checked {{ /if }}>
                            可用
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" value="0" name="oilpipelineStatus"
                                   {{ if data.oilpipelineStatus == 0 }} checked {{ /if }}>
                            不可用
                        </label>

                    </div>
                </div>

                <div class="form-group">
                    <label for="leftAsNum" class="col-sm-2 control-label">左机位号</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="leftAsNum" name="leftAsNum" placeholder="请输入左机位号"
                               data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[10]]"
                               value="{{data.leftAsNum}}">
                    </div>

                    <label for="rightAsNum" class="col-sm-2 control-label">右机位号</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="rightAsNum" name="rightAsNum" placeholder="请输入右机位号"
                               data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[10]]"
                               value="{{data.rightAsNum}}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="frontCoast" class="col-sm-2 control-label">前滑行道</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="frontCoast" name="frontCoast" placeholder="请输入前滑行道"
                               data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[100]]"
                               value="{{data.frontCoast}}">
                    </div>

                    <label for="backCoast" class="col-sm-2 control-label">后滑行道</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="backCoast" name="backCoast" placeholder="请输入后滑行道"
                               data-validation-engine="validate[required,custom[normal],minSize[1],maxSize[100]]"
                               value="{{data.backCoast}}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="leftWheelLine" class="col-sm-2 control-label">左鼻轮线距</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="leftWheelLine" name="leftWheelLine" placeholder="请输入左鼻轮线距"
                               data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               value="{{data.leftWheelLine}}">
                    </div>

                    <label for="rightWheelLine" class="col-sm-2 control-label">右鼻轮线距</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="rightWheelLine" name="rightWheelLine" placeholder="请输入右鼻轮线距"
                               data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               value="{{data.rightWheelLine}}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="wingLength" class="col-sm-2 control-label">翼展</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="wingLength" name="wingLength"  placeholder="请输入翼展"
                               data-validation-engine="validate[required,custom[number],minSize[1],maxSize[10]]"
                               value="{{data.wingLength}}">
                    </div>

                    <label for="available" class="col-sm-2 control-label">是否可用</label>
                    <div class="controls col-sm-3">
                        <label class="checkbox-inline" style=" padding-left: 0;">
                            <input type="radio" value="1" checked="checked" name="available"
                                   {{ if data.available == 1 }} checked {{ /if }}>
                            可用
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" value="0" name="available"
                                   {{ if data.available == 0 }} checked {{ /if }}>
                            不可用
                        </label>

                    </div>
                </div>
                <input type="hidden" name="id" value="{{data.id}}">
            </form>
        </div>
    </div>
</script>

<!-- sysutils -->
<script src="${ctxAssets}/scripts/metadata-dev/garims.SysDictUtils.js"></script>
<script>
    var dictHasSalon = new sysDictUtils("", "yes_no"), dictSalonStatus = new sysDictUtils("", "ed_status"),
            dictHasOilpipeline = new sysDictUtils("", "yes_no"),
            dictOilpipelineStatus = new sysDictUtils("", "ed_status");

    var modifyFormHtml, modelName = "机位信息", width = "800px", columns = [
        {"data": "aircraftStandNum"},
        {"data": "aircraftParkNum"},
        {"data": "aircraftParkDispatcher"},
        {"data": "hasSalon"},
        {"data": "salonStatus"},
        {"data": "hasOilpipeline"},
        {"data": "oilpipelineStatus"},
        {"data": "leftAsNum"},
        {"data": "rightAsNum"},
        {"data": "wingLength"},
        {"data": "available"},
        {"data": "airportTerminalCode"}
    ];
    $(function () {
        // init datatables
        modifyDataTable = $("#modifyDataTable").DataTable({
            select: 'single',
            scrollX: true,
            buttons: [{
                text: '添加' + modelName,
                action: function (evt, dt, node, config) {
                    saveOrUpdateForMain();
                }
            }],
            columnDefs: [{
                targets: columns.length,
                render: function (data, type, row, dt) {
                    return "<a href='javascript:void(0);' data-row-index='" + dt.row + "' name='modifyBtn'>编辑</a> &nbsp;&nbsp;"
                            + "<a href='javascript:void(0);' data-row-index='" + dt.row + "' name='deleteBtn'>删除</a>";
                }
            }, {
                "targets": 2,
                "render": function (data, type, row) {
                    return dictHasSalon.getLabel(data);
                }
            }, {
                "targets": 3,
                "render": function (data, type, row) {
                    return dictSalonStatus.getLabel(data);
                }
            }, {
                "targets": 4,
                "render": function (data, type, row) {
                    return dictHasOilpipeline.getLabel(data);
                }
            }, {
                "targets": 5,
                "render": function (data, type, row) {
                    return dictOilpipelineStatus.getLabel(data);
                }
            }
            ],
            "processing": true,
            "serverSide": true,
            "ajax": "list",
            "columns": columns
        });

        // modify & delete
        $('body').on('click', 'a[name=modifyBtn]', function (e) {
            var data = modifyDataTable.data()[$(this).data()['rowIndex']];
            saveOrUpdateForMain(data);
        }).on('click', 'a[name=deleteBtn]', function (e) {
            var data = modifyDataTable.data()[$(this).data()['rowIndex']];
            layer.confirm("确定删除该条数据?", {
                btn: ["确定", "取消"]
            }, function () {
                $.post("delete", {"id": data.id}, function (resp) {
                    if (resp && resp.code == 1) {
                        layer.msg("删除成功");
                        modifyDataTable.ajax.reload();
                    } else {
                        layer.alert(resp.message);
                    }
                })
            });
        });

        //refresh
        $("#refresh_datatbles").click(function () {
            modifyDataTable.ajax.reload();
        });
    });

    function saveOrUpdateForMain(data) {

        var templateArgs = {
            data: data && data.id != '' ? $utils.ajaxLoadFragments('${ctx}/rms/aircraftStand/get?id=' + data.id) : {},
            terminals: _.pluck($utils.ajaxLoadFragments('${ctx}/rms/airportTerminal/jsonData'), 'terminalName')
        };


        modifyFormHtml = template('modifyForm')(templateArgs);

        var index = layer.open({
            title: ((data && $(data).length > 0) ? "修改" : "新增") + modelName,
            type: 1, closeBtn: 1, shift: 2,
            area: width, shadeClose: false, content: modifyFormHtml,
            success: function () {
                $("form[name='saveModifyForm']").validationEngine({
                    showOneMessage: true,
                    promptPosition: 'bottomLeft'
                })
                $("#airportTerminalCode").select2({
                    language: "zh-CN", allowClear: true, placeholder: "请选择航站楼"
                });

                try { layerShowSuccessCallBack(data); } catch (e) { }
            },
            btn: ['保存', '取消'],
            yes: function () {
                var valid = $("form[name='saveModifyForm']").validationEngine("validate");
                if (valid) {
                    var form = $("form[name='saveModifyForm']");
                    $.post(form.attr("action"), form.serialize(), function (resp) {
                        if (resp && resp.code == 1) {
                            layer.close(index);
                            modifyDataTable.ajax.reload();
                        } else {
                            layer.alert(resp.message);
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

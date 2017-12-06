<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>航班计划</title>
</head>
<body class="bg-1">
<!-- 正文开始 -->
<div id="content" class="col-md-12">
    <!--正文头部开始-->
    <div class="pageheader">
        <h2>航班计划</h2>

        <div class="breadcrumbs">
            <ol class="breadcrumb">
                <li><a href="#">资源管理系统</a></li>
                <li><a href="#">航班计划管理</a></li>
                <li class="active">航班计划</li>
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
                        <h1>航班计划初始</h1>

                        <div class="controls">
                            <a href="#" class="refresh"><i class="fa fa-refresh"></i></a>
                        </div>
                    </div>
                    <div class="tile-body color transparent-white rounded-corners">
                        <div class="table-responsive">
                            <table class="table  table-custom table-datatable" id="flightPlanDataTable">
                                <thead>
                                <tr>
                                    <th>计划日期</th>
                                    <th>状态</th>
                                    <th>初始时间</th>
                                    <th>发布时间</th>
                                    <th>变更时间</th>
                                    <th>是否变更</th>
                                    <th>类目</th>
                                    <th class="no-sort">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>2015-11-10</td>
                                    <td>待审核</td>
                                    <td>11:10</td>
                                    <td>12:10</td>
                                    <td>12:10</td>
                                    <td>是</td>
                                    <td>内容</td>
                                    <td class="actions text-center">
                                        <a class="deleteBtn" href="javascript:void(0);">删除</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>2015-11-11</td>
                                    <td>待审核</td>
                                    <td>11:10</td>
                                    <td>12:10</td>
                                    <td>12:10</td>
                                    <td>是</td>
                                    <td>内容</td>
                                    <td class="actions text-center">
                                        <a class="deleteBtn" href="javascript:void(0);">删除</a>
                                    </td>
                                </tr>
                                </tbody>
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
                        <h1>航班计划详情</h1>

                        <div class="controls">
                            <a href="#" class="refresh"><i class="fa fa-refresh"></i></a>
                        </div>
                    </div>
                    <div class="tile-body color transparent-white rounded-corners">
                        <div class="table-responsive">
                            <table class="table  table-custom table-hover table-datatable" id="flightDetailDataTable">
                                <thead>
                                <tr>
                                    <th>进出港</th>
                                    <th>航站楼</th>
                                    <th>航班号</th>
                                    <th>飞机号</th>
                                    <th>机型</th>
                                    <th>性质</th>
                                    <th>属性</th>
                                    <th>起飞地</th>
                                    <th>经停地一</th>
                                    <th>经停地二</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tfoot>
                                <tr>
                                    <th>进出港</th>
                                    <th>航站楼</th>
                                    <th>航班号</th>
                                    <th>飞机号</th>
                                    <th>机型</th>
                                    <th>性质</th>
                                    <th>属性</th>
                                    <th>起飞地</th>
                                    <th>经停地一</th>
                                    <th>经停地二</th>
                                    <th>操作</th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
    <!--正文内容结束-->
</div>
<!-- 正文结束 -->
<script>


    $(function () {
        var flightPlanDataTable = $('#flightPlanDataTable').DataTable({
            buttons: [{
                text: '<i class="fa fa-calendar-plus-o" ></i>&nbsp;添加计划初始',
                action: function (evt, dt, node, config) {
                    //event handler code here
                    $utils.ajaxLoadFragments('fragments/flightPlan/addPlan.html', function (data) {
                        var _data = $(data).data();
                        layer.open({
                            type: 1, closeBtn: 1, shift: 2,
                            area: [_data["width"], _data["height"]],
                            shadeClose: false, //开启遮罩关闭
                            content: data, title: "航班计划初始化"
                        });
                    });
                }
            }, {
                text: '<i class="fa fa-calendar-check-o" ></i>&nbsp;航班计划审核',
                action: function (evt, dt, node, config) {
                    $.ajax({
                        url: 'fragments/flightPlan/checkWizard/step4.html', method: "GET", dataType: "html",
                        data: {}, success: function (data) {
                            layer.open({
                                type: 1, closeBtn: 1, shift: 2,
                                area: ['940px', '500px'],
                                shadeClose: false, //开启遮罩关闭
                                content: data, title: "航班计划审核"
                            });
                        }
                    });
                }
            }, {
                text: '<i class="fa fa-check" ></i>&nbsp;航班计划发布',
                action: function (evt, dt, node, config) {
                    //event handler code here
                    var selectedRowData = dt.row(".selected").data();

                    if (selectedRowData) {
                        layer.confirm('你确认要发布2015-12-2的航班计划吗?', {
                            btn: ['确定', '取消'] //按钮
                        }, function () {
                            layer.msg('发布成功', {icon: 1});
                        }, function () {

                        });
                    } else {
                        layer.alert("请选择一条数据再进行发布操作", {icon: 2});
                    }
                }
            }, extraButtons.collections, extraButtons.colvis]
        }), flightDetailDataTable = $("#flightDetailDataTable").DataTable({
            buttons: [{
                text: '<i class="fa fa-plus-circle"></i>&nbsp;添加详情',
                action: function (evt, dt, node, config) {
                    var selector = "#planDetailForm";
                    if (flightPlanDataTable.row({selected: true}).count()) {
                        if (!$(selector).length) {
                            $utils.ajaxLoadFragments('fragments/flightPlan/addPlanDetailForm.html', function (data) {
                                var _data = $(data).data();
                                $(_data["selector"]).after(data);
                            });
                        }
                    } else {
                        layer.alert("请先选择一条航班计划数据再进行添加详情操作", {icon: 2});
                    }
                }
            }, {
                text: '<i class="fa fa-save"></i>&nbsp;保存计划详情',
                action: function (evt, dt, node, config) {

                }
            }, extraButtons.collections, extraButtons.colvis]
        });

        flightPlanDataTable.on('select', function (evt, dt, type, indexes) {
            var data = flightPlanDataTable.row(indexes).data(), detailDataFilePath = "datas/T-FlightDetailData-",
                    fileName = data[0].replace("-", "").replace("-", "");
            flightDetailDataTable.ajax.url(detailDataFilePath + fileName + ".json").load();
        });

        var searchElIndex = 0;
        $('#flightDetailDataTable tfoot th').each(function () {
            var title = $(this).text();
            $(this).html('<input type="text" _index="' + searchElIndex++ + '" style="width: 66px; margin-left:-7px;"/>');
        });

        $("#flightDetailDataTable tfoot input[type=text]").on("keyup change", function () {
            flightDetailDataTable.columns($(this).attr("_index")).search($(this).val()).draw();
        });

        $('body').on('click', '#btnHistory', function () {
            if (addPlanValidation()) {
                $utils.ajaxLoadFragments('fragments/flightPlan/historyPlan.html', function (data) {
                    var _data = $(data).data();
                    layer.open({
                        type: 1, closeBtn: 1, shift: 2,
                        area: [_data["width"], _data["height"]],
                        shadeClose: false, //开启遮罩关闭
                        content: data, title: "航班计划初始化"
                    });
                });
            }
        }).on('click', '#btnManual', function () {
            if (addPlanValidation()) {

            }
        }).on('click', '#btnLongTermPlan', function () {
            if (addPlanValidation()) {

            }
        }).on('click', '#btnInterface', function () {
            if (addPlanValidation()) {

            }
        });

    });


    function addPlanValidation() {
        if ($("#ipPlanDate").val() == "") {
            layer.tips('请先选择计划日期', '#ipPlanDate', { tips: [1, '#3595CC'], time: 3000 });
            return false;
        }
        return true;
    }
</script>
</body>
</html>
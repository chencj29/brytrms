<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>消息更新审批</title>

    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="author" content="http://www.metadata.net.cn"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10"/>
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-store">
    <script src="/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <link href="/static/bootstrap/2.3.1/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script src="/static/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="/static/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css" rel="stylesheet"/>
    <script src="${ctxAssets}/scripts/underscore/underscore-min.js"></script>
    <script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
    <style type="text/css">
        .tdrow {
            word-wrap: break-word;
            word-break: break-all;
        }

        .table th {
            vertical-align: middle !important;
            text-align: center;
        }

        .table td {
            vertical-align: middle !important;
            text-align: center;
        }
    </style>
</head>
<body class="bg-1">
<ul id="myTab" class="nav nav-tabs">
    <li class="active">
        <a href="#adviceNull" data-toggle="tab">未审批</a>
    </li>
    <li>
        <a href="#adviceYes" data-toggle="tab">已审批</a>
    </li>
</ul>

<!-- 正文开始 -->

<div id="myTabContent" class="tab-content">
    <div class="tab-pane fade in" id="adviceYes">
        <table class="table table-striped table-bordered table-condensed">
            <thead>
            <tr>
                <th style="width: 60px" rowspan="2">审核状态</th>
                <th colspan="3" align="center">消息</th>
                <th rowspan="2" style="width: 220px;">被选航班动态信息</th>
                <th style="width: 60px;" rowspan="2">审核人</th>
            </tr>
            <tr class="success">
                <th style="width: 114px;">消息时间</th>
                <th style="width: 60px;">航班号</th>
                <th style="width: 220px;">消息描述</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${advice}" var="y">
                <tr>
                    <c:choose>
                        <c:when test="${y.adviceFlag== '1'}">
                            <td>同意</td>
                        </c:when>
                        <c:otherwise>
                            <td>否</td>
                        </c:otherwise>
                    </c:choose>
                    <td><fmt:formatDate value="${ y.sendTime }" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>${ y.flightCompanyCode }${ y.flightNum }</td>
                    <td>${ y.description }</td>
                    <td>${ y.ext2 }</td>
                    <td>${ y.daviceBy}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="tab-pane fade in active" id="adviceNull">
        <div class="panel-body">
            <div class="list-op" id="adviceNull_btn">
                <span>&nbsp;&nbsp;&nbsp;批量审批:</span>
                <button type="button" class="btn btn-success btn-sm">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>同意
                </button>
                <button type="button" class="btn btn-danger btn-sm">
                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>不同意
                </button>
            </div>
        </div>
        <table class="table table-striped table-bordered table-condensed">
            <thead>
            <tr class="success">
                <th style="width: 114px;">消息时间</th>
                <th style="width: 60px;">航班号</th>
                <th style="width: 220px;">消息描述</th>
                <th style="width: 220px;">匹配航班动态</th>
                <%--<th style="width: 60px;">审核人</th>--%>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${adviceNull}" var="y">
                <tr _id='${ y.id}'>
                    <td style="display:none;" name="id">${ y.id}</td>
                    <td><fmt:formatDate value="${ y.sendTime }" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>${ y.flightCompanyCode }${ y.flightNum }</td>
                    <td>${ y.description }</td>
                    <td style="display:none;" name="flightJson">${ y.ext1}</td>
                    <td name="flightDynamic"></td>
                        <%--<td>${ y.daviceBy}</td>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    //未审核空管消息
    var adviceNullFlight = {<c:forEach items="${adviceNull}" var="y">'${ y.id}':${ y.ext1}, </c:forEach>};

    $(function () {
        initTableCheckbox("adviceNull");
        btnClick("adviceNull");

        //解析匹配的动态
        _.each($("#adviceNull table tbody tr"), function (tr) {
            var _id = $(tr).attr("_id"), _flight = $(tr).find('[name=flightDynamic]');
            _select = $('<select class="form-control input-lg" style="width: 100%;"/>');
            _select.append('<option value=""/>');
            _.each(adviceNullFlight[_id], (o) => {
                var _option = $('<option value="' + o.id + '"/>')
                _option.text(o.planDate + ' ' + (o.inoutTypeCode == "J" ? "进港" : "出港") + ' ' + o.flightNum + ' ' + o.desc);
                _select.append(_option);
            });
            _flight.append($('<div class="form-group"/>').append(_select));
            $(tr).attr("_flightDynamicId", "");

            _flight.find('select').change(function () {
                var flightdynamicId = $(this).children('option:selected').val().trim();
                $(tr).attr("_flightDynamicId", flightdynamicId);
                $(tr).attr("_desc", $(this).children('option:selected').text().trim());
                _flight.attr("_id", flightdynamicId);
            });
        });
    });

    function btnClick(name) {
        $('#' + name + '_btn button').click(function () {
            var ids = [], flightDynamicIds = [], descs = [];
            _.each($('[name=checkItem]:checked'), (td) => {
                var _tr = $(td).parents("tr"), _id = _tr.attr("_id"), _flightDynamicId = _tr.attr("_flightDynamicId"),
                    _desc = _tr.attr("_desc");
                if (_id) ids.push(_id);
                if (_flightDynamicId) {
                    flightDynamicIds.push(_flightDynamicId);
                    descs.push(!_desc ? "" : _desc);
                }
            });


            if (ids.length > 0) {
                if ($(this).index() == 1) {
                    if (ids.length == flightDynamicIds.length) {
                        // 执行同意
                        doAdvice(ids, flightDynamicIds, descs, 1);
                    } else {
                        parent.layer.msg("请选择【匹配航班动态】后再操作!", {zIndex: 1110});
                    }
                } else if ($(this).index() == 2) {
                    doAdvice(ids, flightDynamicIds, descs, 0);
                }

            } else {
                parent.layer.msg("请选择后再操作", {zIndex: 1100});
            }
        });
    }
    function doAdvice(ids, flightDynamicIds, descs, flag) {
        <shiro:hasPermission name="ams:flightDynamicManager:aocThirdParty">
        //同意
        $.post("${ctx}/ams/flightDynamicManager/doAocThirdParty", {
            "ids": ids,
            "flightDynamicIds": flightDynamicIds,
            "descs": descs,
            "adviceFlag": flag
        }, function (msg) {
            location.reload();
            if (msg.code != 1) {
                parent.layer.msg(msg.message);
            }
        });
        </shiro:hasPermission>
    }

    function initTableCheckbox(name) {
        var $thr = $('#' + name + ' thead tr');
        var $checkAllTh = $('<th style="width: 20px;"><input type="checkbox" id="checkAll" name="checkAll" /></th>');
        /*将全选/反选复选框添加到表头最前，即增加一列*/
        $thr.prepend($checkAllTh);
        /*“全选/反选”复选框*/
        var $checkAll = $thr.find('input');
        $checkAll.click(function (event) {
            /*将所有行的选中状态设成全选框的选中状态*/
            $tbr.find('input').prop('checked', $(this).prop('checked'));
            /*并调整所有选中行的CSS样式*/
            if ($(this).prop('checked')) {
                $tbr.find('input').parent().parent().addClass('warning');
            } else {
                $tbr.find('input').parent().parent().removeClass('warning');
            }
            /*阻止向上冒泡，以防再次触发点击操作*/
            event.stopPropagation();
        });
        /*点击全选框所在单元格时也触发全选框的点击操作*/
        $checkAllTh.click(function () {
            $(this).find('input').click();
        });
        var $tbr = $('#' + name + ' tbody tr');
        var $checkItemTd = $('<td><input type="checkbox" name="checkItem" /></td>');
        /*每一行都在最前面插入一个选中复选框的单元格*/
        $tbr.prepend($checkItemTd);
        /*点击每一行的选中复选框时*/
        $tbr.find('input').click(function (event) {
            /*调整选中行的CSS样式*/
            $(this).parent().parent().toggleClass('warning');
            /*如果已经被选中行的行数等于表格的数据行数，将全选框设为选中状态，否则设为未选中状态*/
            $checkAll.prop('checked', $tbr.find('input:checked').length == $tbr.length ? true : false);
            /*阻止向上冒泡，以防再次触发点击操作*/
            event.stopPropagation();
        });
        /*点击每一行时也触发该行的选中操作*/
        $tbr.click(function () {
            $(this).find('input').click();
        });
    }
</script>
</body>
</html>
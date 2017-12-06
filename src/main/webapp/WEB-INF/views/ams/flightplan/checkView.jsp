<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>航班动态操作审批</title>

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
        }
    </style>
</head>
<body class="bg-1">
<ul id="myTab" class="nav nav-tabs">
    <li class="active">
        <a href="#adviceNull" data-toggle="tab">未审批</a>
    </li>
    <li>
        <a href="#adviceYes" data-toggle="tab">审批通过</a>
    </li>
    <li>
        <a href="#adviceNo" data-toggle="tab">审批未通过</a>
    </li>
</ul>

<!-- 正文开始 -->

<div id="myTabContent" class="tab-content">
    <div class="tab-pane fade in" id="adviceYes">
        <table class="table table-striped table-bordered table-condensed">
            <thead>
            <tr class="success">
                <th style="width: 114px;">申请时间</th>
                <th style="width: 113px;">申请动作</th>
                <th>动作描述</th>
                <th style="width: 75px;">航班日期</th>
                <th style="width: 30px;">航班类型</th>
                <th style="width: 60px;">航班号</th>
                <th style="width: 60px;">申请人</th>
                <th style="width: 60px;">审核人</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${adviceYes}" var="y">
                <tr>
                    <td style="display:none;" name="id">${ y.id}</td>
                        <%--<td style="display:none;" name="arguments">${y.arguments}</td>--%>
                    <td><fmt:formatDate value="${ y.createDate }" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td name="description">${ y.description }</td>
                    <td name="desc">${ y.ext1 }</td>
                    <td name="planDate"><fmt:formatDate value="${ y.planDate }" pattern="yyyy-MM-dd"/></td>
                    <td name="inoutTypeCode">${ y.inoutTypeName }</td>
                    <td name="flightNum" class="tdrow">${ y.flightNum }</td>
                    <td>${ y.createBy.name }</td>
                    <td>${ y.adviceBy}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="tab-pane fade in" id="adviceNo">
        <table class="table table-striped table-bordered table-condensed">
            <thead>
            <tr class="success">
                <th style="width: 114px;">申请时间</th>
                <th style="width: 113px;">申请动作</th>
                <th>动作描述</th>
                <th style="width: 75px;">航班日期</th>
                <th style="width: 30px;">航班类型</th>
                <th style="width: 60px;">航班号</th>
                <th style="width: 60px;">申请人</th>
                <th style="width: 60px;">审核人</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${adviceNo}" var="y">
                <tr>
                    <td style="display:none;" name="id">${ y.id}</td>
                        <%--<td style="display:none;" name="arguments">${y.arguments}</td>--%>
                    <td><fmt:formatDate value="${ y.createDate }" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td name="description">${ y.description }</td>
                    <td name="desc">${ y.ext1 }</td>
                    <td name="planDate"><fmt:formatDate value="${ y.planDate }" pattern="yyyy-MM-dd"/></td>
                    <td name="inoutTypeCode">${ y.inoutTypeName }</td>
                    <td name="flightNum" class="tdrow">${ y.flightNum }</td>
                    <td>${ y.createBy.name }</td>
                    <td>${ y.adviceBy}</td>
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
                <th style="width: 114px;">申请时间</th>
                <th style="width: 113px;">申请动作</th>
                <th>动作描述</th>
                <th style="width: 75px;">航班日期</th>
                <th style="width: 30px;">航班类型</th>
                <th style="width: 60px;">航班号</th>
                <th style="width: 60px;">申请人</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${adviceNull}" var="y">
                <tr _id='${ y.id}'>
                    <td style="display:none;" name="id">${ y.id}</td>
                        <%--<td style="display:none;" name="arguments">${y.arguments}</td>--%>
                    <td><fmt:formatDate value="${ y.createDate }" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td name="description">${ y.description }</td>
                    <td name="desc">${ y.ext1 }</td>
                    <td name="planDate"><fmt:formatDate value="${ y.planDate }" pattern="yyyy-MM-dd"/></td>
                    <td name="inoutTypeCode">${ y.inoutTypeName }</td>
                    <td name="flightNum" class="tdrow">${ y.flightNum }</td>
                    <td>${ y.createBy.name }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>


<script>
    /*_.templateSettings = {
     interpolate: /\{\{(.+?)\}\}/gim,
     evaluate: /\{\#(.+?)\}\}/gim,
     escape: /\{\{\-(.+?)\}\}/gim
     };*/

    var typeArr = ["adviceNull", "adviceNo", "adviceYes"];
    $(function () {
        $('body').css({overflow: "auto"});
        initTableCheckbox("adviceNull");
        btnClick("adviceNull");
    });

    function btnClick(name) {
        $('#' + name + '_btn button').click(function () {
            var result = [];
            _.each($(':checked'), (td) => {
                var _id = $(td).parents("tr").attr("_id");
                if (_id) result.push(_id);
            });
            if (result.length > 0) {
                if ($(this).index() == 1) {
                    // 执行同意
                    doAdvice(result, 1);
                } else if ($(this).index() == 2) {
                    doAdvice(result, 0);
                }
            } else {
                parent.layer.msg("请选择后再操作");
            }
        });
    }
    function doAdvice(ids, flag) {
        <shiro:hasPermission name="ams:resource:check">
        //同意1，不同意0
        $.post("${ctx}/rms/rs/doCheck", {"ids": ids, "adviceFlag": flag}, function (msg) {
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
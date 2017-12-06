<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>处理两系统同步</title>

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
    <%--<script src="${ctxAssets}/scripts/underscore/underscore-min.js"></script>--%>
    <%--<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>--%>
    <script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>
    <script src="${ctxAssets}/scripts/layer/src/layer.js"></script>
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
<body>
<p class="bg-danger" style="margin: 10px;">
    <span>说明：要使用此功能,请先dblink数据库; &nbsp;&nbsp;<span id="showtip" style="color:#0080c0;cursor:pointer;">示例</span></span>
    <span style="float: right;margin: 0 40px;color: #0080c0;cursor: pointer;" id="refreshBtn">刷新</span>
</p>
<div id="dbTable"></div>
<div class="btn-toolbar breadcrumb" style="width:45%;float: left">
    <label>选择更新方式("->":为前库数据更新到后库)：此为添加差异</label>
    <div class="form-group">
        <select id="attrUpdate" name="attrUpdate" class="form-control input-lg">
            <option value="">--请选择--</option>
            <c:forEach items="${attrMap}" var="mymap">
                <option value="${mymap.key}">${mymap.value}</option>
            </c:forEach>
        </select>
    </div>
    <button type="button" class="btn btn-primary" id="saveBtn"
            data-toggle="button"> 确定
    </button>
</div>
<div class="btn-toolbar breadcrumb" style="width:49%;float: left">
    <label>选择更新方式("->":为前库数据更新到后库)：此为删除后添加</label>
    <div class="form-group">
        <select id="attrUpdate2" name="attrUpdate2" class="form-control input-lg">
            <option value="">--请选择--</option>
            <c:forEach items="${attrMap2}" var="mymap">
                <option value="${mymap.key}">${mymap.value}</option>
            </c:forEach>
        </select>
    </div>
    <button type="button" class="btn btn-primary" id="saveBtn2"
            data-toggle="button"> 确定
    </button>
</div>
<script id="oldData" type="text/html">
    <table class="table table-striped table-hover table-bordered">
        <thead>
        <tr>
            <th>表名/字段名</th>
            <th>进港</th>
            <th>出港</th>
            <th>连班</th>
            <th>机位P</th>
            <th>值机柜台C</th>
            <th>登机口C</th>
            <th>到港门J</th>
            <th>行李转盘J</th>
            <th>滑槽C</th>
            <th>候机厅C</th>
            <th>安检口C</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>AMS</td>
            <td>{{o.ams.进港}}</td>
            <td>{{o.ams.出港}}</td>
            <td>{{o.ams.连班}}</td>
            <td>{{o.ams.机位P}}</td>
            <td>{{o.ams.值机柜台C}}</td>
            <td>{{o.ams.登机口C}}</td>
            <td>{{o.ams.到港门J}}</td>
            <td>{{o.ams.行李转盘J}}</td>
            <td>{{o.ams.滑槽C}}</td>
            <td>{{o.ams.候机厅C}}</td>
            <td>{{o.ams.安检口C}}</td>
        </tr>
        <tr>
            <td>RMS</td>
            <td>{{o.rms.进港}}</td>
            <td>{{o.rms.出港}}</td>
            <td>{{o.rms.连班}}</td>
            <td>{{o.rms.机位P}}</td>
            <td>{{o.rms.值机柜台C}}</td>
            <td>{{o.rms.登机口C}}</td>
            <td>{{o.rms.到港门J}}</td>
            <td>{{o.rms.行李转盘J}}</td>
            <td>{{o.rms.滑槽C}}</td>
            <td>{{o.rms.候机厅C}}</td>
            <td>{{o.rms.安检口C}}</td>
        </tr>
        </tbody>
    </table>
</script>

<script type="text/javascript">
    $(function () {
        $.get("${ctx}/test/test/findOciData", function (result) {
            $("#dbTable").html(template("oldData", {o: result}));
            //console.log(result);
        });

        $("#saveBtn").click(function () {
            var params = $("#attrUpdate").val();
            var text = $("#attrUpdate").find("option:selected").text();
            actionSave(params, text);
        })

        $("#saveBtn2").click(function () {
            var params = $("#attrUpdate2").val();
            var text = $("#attrUpdate2").find("option:selected").text();
            actionSave(params, text);
        })

        $("#showtip").click(function () {
            layer.tips("<pre>##创建DBLINK\n" +
                "CREATE DATABASE LINK AMS_W\n" +
                "CONNECT TO ams IDENTIFIED BY metadata\n" +
                "USING '(DESCRIPTION =\n" +
                "    (ADDRESS_LIST =\n" +
                "      (ADDRESS = (PROTOCOL = TCP)(HOST = 10.24.1.11)(PORT = 2521))\n" +
                "    )\n" +
                "    (CONNECT_DATA =\n" +
                "      (SERVICE_NAME = TESTMSGS)\n" +
                "    )\n" +
                "  )'</pre>", this, {area: '530px', tips: 3, time: 4000});
        })

        $("#refreshBtn").click(function () {
            location.reload();
        });
    })

    function actionSave(params, title) {
        if (!params) {
            parent.layer.msg("请选择后，再操作！", {zIndex: 1100});
            return;
        }

        parent.layer.confirm('你确定要"' + title + '"操作吗?', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.get("${ctx}/test/test/updateSql?params=" + params, function (resp) {
                if (resp > -1) {
                    parent.layer.msg("列更新成功!" + resp, {zIndex: 1100});
                    setTimeout(function () {
                        window.location.reload();
                    }, 2000);
                } else {
                    parent.layer.msg("更新失败!", {zIndex: 1100});
                }
            });
        })
    }
</script>
</body>
</html>

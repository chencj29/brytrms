<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>本站仓单数据管理</title>
    <style>

        .formtable_entend td:nth-child(even) input,
        .formtable_entend td:nth-child(even) input {
            width: 90px;
            height: 22px;
            margin: -2px;
        }

        .formtable_new td, .formtable_new th {
            color: #5E7595;
            font-size: 12px;
            font-weight: bold;
            text-align: center;
            padding: 2px;
        }

        .formtable_new tr:nth-child(1) td, .formtable_new tr:nth-child(1) td,
        .formtable_new tr:nth-child(2) td, .formtable_new tr:nth-child(2) td {
            padding: 3px;
            background-color: #F2F7FE;
        }

        .formtable_new td:nth-child(odd) {
            text-align: center;
        }

        .formtable_new td input, .formtable_new th input {
            width: 99%;
            height: 22px;
            margin: -2px;
        }

        tr {
            height: 10px;
        }

        .formtable td, .formtable th {
            border: 1px solid #cad9ea;
            padding: 3px;
        }
    </style>


</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'" id="dgContainer" style="overflow-y:hidden;"></div>
    <div data-options="region:'south'" style="height:180px;">

        <form action="${ctx}/rms/rmsFlightHomeManifest/saveSce" id="sce_modifyForm">
            <input type="hidden" name="flightDynamicId">
            <table width="100%" class="formtable formtable_new" style="margin-bottom: 15px;">
                <tr>
                    <td rowspan="2" style="width: 120px;">
                        日&nbsp;期:&nbsp;<label name="planDate"></label>
                        <br/>
                        航班号：&nbsp;<label name="flightNum"></label>
                    </td>
                    <td rowspan="2">地名</td>
                    <td colspan="3">出港旅客</td>
                    <td colspan="2">货物</td>
                    <td colspan="2">邮件</td>
                    <td colspan="2">行李</td>
                </tr>
                <tr>
                    <td>儿童</td>
                    <td>成人</td>
                    <td>婴儿</td>
                    <td>件数</td>
                    <td>重量</td>
                    <td>件数</td>
                    <td>重量</td>
                    <td>件数</td>
                    <td>重量</td>
                </tr>
                <tr t="ARRI">
                    <td>目的站</td>
                    <td>
                        <input type="text" name="addr" readonly style="border:0px;"/>
                        <input type="hidden" name="addrCode" value=""/>
                        <input type="hidden" name="addrNature" value="ARRI"/>
                        <input type="hidden" name="id" value=""/>
                    </td>
                    <td><input type="text" name="childCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="personCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="goodsCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="goodsWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="mailCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="mailWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="luggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="luggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                </tr>
                <tr t="PASS1">
                    <td>经停地1</td>
                    <td>
                        <input type="text" name="addr" readonly style="border:0px;"/>
                        <input type="hidden" name="addrCode" value=""/>
                        <input type="hidden" name="addrNature" value="PASS1"/>
                        <input type="hidden" name="id" value=""/>
                    </td>
                    <td><input type="text" name="childCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="personCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="goodsCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="goodsWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="mailCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="mailWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="luggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="luggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                </tr>
                <tr t="PASS2">
                    <td>经停地2</td>
                    <td>
                        <input type="text" name="addr" readonly style="border:0px;"/>
                        <input type="hidden" name="addrCode" value=""/>
                        <input type="hidden" name="addrNature" value="PASS2"/>
                        <input type="hidden" name="id" value=""/>
                    </td>
                    <td><input type="text" name="childCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="personCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="goodsCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="goodsWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="mailCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="mailWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="luggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="luggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                </tr>
            </table>
        </form>

        <form action="${ctx}/rms/rmsFlightHomeManifest/save" id="modifyForm">
            <input type="hidden" name="flightDynamicId">
            <input type="hidden" name="id">
            <table width="100%" class="formtable formtable_entend">
                <tr>
                    <td>本站出港成人：</td>
                    <td>
                        <input type="text" name="personCount"/>
                    </td>
                    <td>出港邮件件数：</td>
                    <td>
                        <input type="text" name="mailCount"/>
                    </td>
                    <td>出港行李件数：</td>
                    <td>
                        <input type="text" name="luggageCount"/>
                    </td>
                    <td>出港VIP人数：</td>
                    <td>
                        <input type="text" name="vipCount" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/>
                    </td>
                    <td>实际过站成人数：</td>
                    <td>
                        <input type="text" name="personCountP" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/>
                    </td>
                </tr>
                <tr>
                    <td>本站出港儿童：</td>
                    <td>
                        <input type="text" name="childCount"/>
                    </td>
                    <td>出港邮件重量：</td>
                    <td>
                        <input type="text" name="mailWeight"/>
                    </td>
                    <td>出港行李重量：</td>
                    <td>
                        <input type="text" name="luggageWeight"/>
                    </td>
                    <td>头等舱旅客人数：</td>
                    <td>
                        <input type="text" name="firstCabinCount" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/>
                    </td>
                    <td>实际过站儿童数：</td>
                    <td>
                        <input type="text" name="childCoutP" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/>
                    </td>
                </tr>
                <tr>
                    <td>本站出港婴儿：</td>
                    <td>
                        <input type="text" name="babyCount"/>
                    </td>
                    <td>出港货物数量：</td>
                    <td>
                        <input type="text" name="goodsCount"/>
                    </td>
                    <td>机组人数：</td>
                    <td>
                        <input type="text" name="flightCrewCount" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/>
                    </td>
                    <td>公务舱旅客人数：</td>
                    <td>
                        <input type="text" name="businessCabinCount" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/>
                    </td>
                    <td>实际过站婴儿数：</td>
                    <td>
                        <input type="text" name="babyCountP" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/>
                    </td>
                </tr>
                <tr>
                    <td>本站出港总计：</td>
                    <td>
                        <input type="text" name="travellerAllCount" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/>
                    </td>
                    <td>出港货物重量：</td>
                    <td>
                        <input type="text" name="goodsWeight" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/>
                    </td>
                    <td>油料重量：</td>
                    <td>
                        <input type="text" name="oilWeight" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/>
                    </td>
                    <td>经济舱旅客人数：</td>
                    <td>
                        <input type="text" name="touristCabinCount" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/>
                    </td>
                    <td>实际过站旅客总数：</td>
                    <td>
                        <input type="text" name="personActualCountP" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/>
                    </td>
                </tr>
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>

    </div>
</div>
<div id="view-setting-dialog" title="显示设置" class="easyui-dialog" data-options="closed:true, modal:true"
     style="width: 265px; height:500px;"></div>

<script type="text/html" id="datagridTpl">
    <table class="easyui-datagrid" id="datagrid" data-options="remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="100" hidden="true">id</th>
            <th field="flightDynamicId" width="100" hidden="true">动态id</th>
            <th field="lastAllCount" width="60" align="center" hidden="true" sortable="true">载结人数</th>
            <th field="planDate" width="80" align="center" sortable="true" formatter="dateFmt">航班日期</th>
            <th field="flightCompanyCode" width="40" align="center" sortable="true">航空公司</th>
            <th field="flightNum" width="40" align="center" sortable="true">航班号码</th>
            <th field="aircraftNum" width="40" align="center" sortable="true">飞机号</th>
            <th field="flightCompanySubInfoCode" width="40" align="center" sortable="true">子公司<br/>代码</th>
            <th field="flightCompanySubInfoName" width="60" align="center" sortable="true">子公司<br/>公司名称</th>
            <th field="travellerAllCount" width="60" align="center" sortable="true">本站出港<br/>旅客总数</th>
            <th field="personCount" width="100" align="center" sortable="true">本站出港<br/>实际过站成人数</th>
            <th field="childCount" width="100" align="center" sortable="true">本站出港<br/>实际过站儿童数</th>
            <th field="babyCount" width="100" align="center" sortable="true">本站出港<br/>实际过站婴儿数</th>
            <th field="flightCrewCount" width="80" align="center" sortable="true">机组人数</th>
            <th field="goodsCount" width="80" align="center" sortable="true">出港货物<br/>数量</th>
            <th field="goodsWeight" width="80" align="center" sortable="true">出港货物<br/>重量</th>
            <th field="mailCount" width="80" align="center" sortable="true">出港邮件<br/>件数</th>
            <th field="mailWeight" width="80" align="center" sortable="true">出港邮件<br/>重量</th>
            <th field="luggageCount" width="100" align="center" sortable="true">出港行李数量<br/>件数</th>
            <th field="luggageWeight" width="100" align="center" sortable="true">出港行李数量<br/>重量</th>
            <th field="vipCount" width="80" align="center" sortable="true">出港旅客<br/>出港VIP人数</th>
            <th field="firstCabinCount" width="100" align="center" sortable="true">出港旅客<br/>头等舱旅客人数</th>
            <th field="businessCabinCount" width="100" align="center" sortable="true">出港旅客<br/>公务舱旅客人数</th>
            <th field="touristCabinCount" width="100" align="center" sortable="true">出港旅客<br/>经济舱旅客人数</th>
            <th field="oilWeight" width="80" align="center" sortable="true">油料重量</th>
            <th field="personActualCountP" width="80" align="center" sortable="true">实际过站<br/>旅客总数</th>
            <th field="personCountP" width="80" align="center" sortable="true">实际过站<br/>成人数</th>
            <th field="childCoutP" width="80" align="center" sortable="true">实际过站<br/>儿童数</th>
            <th field="babyCountP" width="80" align="center" sortable="true">实际过站<br/>婴儿数</th>
        </tr>
        </thead>
    </table>
</script>

<script>
    var moduleContext = "rmsFlightHomeManifest";

    var olnyInput = [
        "personCount",
        "childCount",
        "babyCount",
        "mailCount",
        "mailWeight",
        "goodsCount",
        "goodsWeight",
        "luggageCount",
        "luggageWeight"
    ], trArr = [], selectIndex;
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:rmsFlightHomeManifest:edit">flagDgEdit = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsFlightHomeManifest:insert">flagDgInsert = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsFlightHomeManifest:del">flagDgDel = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsFlightHomeManifest:expExcel">flagDgExp = true;
    </shiro:hasPermission>

    flagValidate = true; //取消所有必填验证
    $(function () {
        //禁止编辑，并自动计算
        $("#modifyForm input[name='travellerAllCount']").attr('readonly', 'readonly').css("background", "#c7c7c7");
        _.each(olnyInput, function (name) {
            $("#modifyForm input[name='" + name + "']").attr('readonly', 'readonly').css("background", "#c7c7c7");
            $("#sce_modifyForm input[name='" + name + "']").change(function () {
                var total = 0;
                _.each(trArr, function ($tr) {
                    total += parseFloat($tr.find("input[name='" + name + "']").val() || 0);
                })
                $("#modifyForm input[name='" + name + "']").val(total);
                calculateCounts();
            })
        })
    });

    //获取本站仓单的航段数据并家里填充
    function getManifestSecListAndfilld(flightDynamicId) {
        if (flightDynamicId) {
            $.ajax({
                type: "post",
                async: false,
                url: "${ctx}/rms/" + moduleContext + "/getManifestSecs",
                data: {flightDynamicId: flightDynamicId},
                success: function (msg) {
                    if (msg.code == 1) {
                        //填充每个航段的值机数据
                        setValueToManifestSecsForm(msg.result.manifestSecs, flightDynamicId);
                    } else {
                        alert(msg.message);
                    }
                }
            });
        }
        fillDynamicMsg();
    }


    function fillDynamicMsg() {
        var row = datagrid.datagrid("getSelected");
        var _planDate = row.planDate;
        _planDate = !_planDate ? "--" : moment(_planDate).format("YYYY-MM-DD");
        $("#sce_modifyForm table label[name='planDate']").html(_planDate);
        $("#sce_modifyForm table label[name='flightNum']").html(row.flightNum);
    }


    function setValueToManifestSecsForm(datas, flightDynamicId) {
        $("#sce_modifyForm input[name='flightDynamicId']").val(flightDynamicId);
        for (var i = 0; i < datas.length; i++) {
            var addrNature = datas[i]['addrNature'];
            var _$tr = $("#sce_modifyForm table tr[t=" + addrNature + "]");
//            if (addrNature == 'PASS1') {
//                _$tr = $("#sce_modifyForm table tr:nth-child(3)")
//            } else if (addrNature == 'PASS2') {
//                _$tr = $("#sce_modifyForm table tr:nth-child(4)")
//            } else if (addrNature == 'ARRI') {
//                _$tr = $("#sce_modifyForm table tr:nth-child(5)")
//            }
            _$tr.find("input[name='id']").val(datas[i]['id']);
            _$tr.find("input[name='addr']").val(datas[i]['addr']);
            _$tr.find("input[name='addrCode']").val(datas[i]['addrCode']);
            _$tr.find("input[name='personCount']").val(datas[i]['personCount']);
            _$tr.find("input[name='childCount']").val(datas[i]['childCount']);
            _$tr.find("input[name='babyCount']").val(datas[i]['babyCount']);
            _$tr.find("input[name='goodsCount']").val(datas[i]['goodsCount']);
            _$tr.find("input[name='goodsWeight']").val(datas[i]['goodsWeight']);
            _$tr.find("input[name='mailCount']").val(datas[i]['mailCount']);
            _$tr.find("input[name='mailWeight']").val(datas[i]['mailWeight']);
            _$tr.find("input[name='luggageCount']").val(datas[i]['luggageCount']);
            _$tr.find("input[name='luggageWeight']").val(datas[i]['luggageWeight']);
        }
    }


    function calculateCounts() {
        $("#sce_modifyForm table tr td>input[namd='id']")

        var personCount = $("#modifyForm input[name='personCount']").val();
        var childCount = $("#modifyForm input[name='childCount']").val();
        var babyCount = $("#modifyForm input[name='babyCount']").val();
        personCount = ("undefined" == typeof personCount) ? 0 : Number(personCount);
        childCount = ("undefined" == typeof childCount) ? 0 : Number(childCount);
        babyCount = ("undefined" == typeof babyCount) ? 0 : Number(babyCount);
        $("#modifyForm input[name='travellerAllCount']").val(personCount + childCount + babyCount);
        var personCountP = $("#modifyForm input[name='personCountP']").val();
        var childCoutP = $("#modifyForm input[name='childCoutP']").val();
        var babyCountP = $("#modifyForm input[name='babyCountP']").val();
        personCountP = ("undefined" == typeof personCountP) ? 0 : Number(personCountP);
        childCoutP = ("undefined" == typeof childCoutP) ? 0 : Number(childCoutP);
        babyCountP = ("undefined" == typeof babyCountP) ? 0 : Number(babyCountP);
        $("#modifyForm input[name='personActualCountP']").val(personCountP + childCoutP + babyCountP);
    }


    function dateFmt(v, r, i, f) {
        return !v ? "" : moment(v).format("YYYY-MM-DD");
    }
</script>

<script src="${ctxAssets}/scripts/metadata-dev/jquery.validatebox.extend.js"></script>
<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.Ext_duty.js"></script>
<script>
    /**
     * form: crud表单对象
     * datagrid: 数据表格对象
     * dictColumns: 字典字段
     * comboboxFilterDefinetion: 字典类型字段过滤定义 （需传入字段名及JSON枚举值）
     *      例如：[{value: '', text: '全部'}, {value: '0', text: '不可用'}, {value: '1', text: '可用'}]
     */
    var form, datagrid, dictColumns, comboboxFilterDefinetion = function (fieldCode, enumValues) {
        return {
            field: fieldCode, type: 'combobox', options: {
                panelHeight: 'auto', data: enumValues, onChange: function (value) {
                    if (value == '') datagrid.datagrid('removeFilterRule', fieldCode);
                    else datagrid.datagrid('addFilterRule', {field: fieldCode, op: ['equal'], value: value});
                    datagrid.datagrid('doFilter');
                }
            }
        }
    }, toolbarDate = [];//按钮数据

    var flagAutoHight, flagValidate;//编辑框自动适应高度开关，所有非空验证开关
    var flagDgEdit, flagDgDel, flagDgInsert, flagDgImp, flagDgExp;  //修改、删除、新增、导入、导出按钮开关
    var dataGridMeta;

    $(function () {
        loadDataGridContent();

        form = $("#modifyForm");

        //增加非空验证 wjp 2016-4-5
        if (!flagValidate) {
            $("#modifyForm input:visible").validatebox({
                required: true
            });
        }

        //编辑框自动适应高度
        if (!flagAutoHight) {
            //var c = $('body>div').eq(0);
            var c = $('#datagrid').parents('.easyui-layout').eq(0);
            //c.layout('panel', 'south').outerHeight();
            var h = (c.layout('panel', 'south').outerHeight() - ($(window).height() - $('#btnSave').offset().top) + 35);
            c.layout('panel', 'south').panel('resize', {height: h});
            c.layout('resize', {height: 'auto'});
        }

        if (flagDgExp) {
            toolbarDate.push('-');
            toolbarDate.push({
                text: "EXCEL导出",
                iconCls: "icon-tip",
                handler: function () {
                    downloadExcel();
                }
            });

            toolbarDate.push({
                text: '显示设置', iconCls: 'icon-2012080404391', handler: function () {
                    $("#view-setting-dialog").dialog('open');
                }
            });
        }

        $("#btnSave").linkbutton('disable');
        //添加点击编辑事件；
        if (flagDgEdit) {
            $("#datagrid").datagrid({
                onSelect: function (index, row) {
                    try {
                        if(preSelectFn) preSelectFn(index, row);
                    } catch (e) {
                    }

                    if (!row) return;

                    form.form("load", $utils.ajaxLoadFragments(ctx + "/rms/" + moduleContext + "/get?id=" + row.id));
                    //当从数据库获取不到数据时候，也要填充  flightDynamicId
                    form.find("input[name='flightDynamicId']").val(row.flightDynamicId);
                    //获取航段数据并填充到页面上
                    getManifestSecListAndfilld(row.flightDynamicId);

                    //装载统计行对象
                    trArr = [];
                    _.each($("#sce_modifyForm input[name=addrCode]"), function (input) {
                        var $input = $(input);
                        if ($input.val()) trArr.push($input.parents("tr"));
                    })

                    //获取选中id
                    selectIndex = index;
                },
                onLoadSuccess: function (data) {
                    if (selectIndex) datagrid.datagrid("selectRow", selectIndex);
                    //datagrid.datagrid("reload").datagrid("clearSelections");
                }
            });
            $("#btnSave").linkbutton('enable');
        }

        toolbarDate.push({
            text: '刷新数据', iconCls: 'icon-arrow_refresh_small', handler: function () {
                var opt = datagrid.datagrid('options');
                if(opt && opt.sortName) opt.sortName = "";
                datagrid.datagrid('reload');
            }
        })

        dictColumns = $("#datagrid th[formatter=globalFormat]").map(function () {
            return $(this).attr("field");
        });

        datagrid = $("#datagrid").datagrid({
            rownumbers: true, url: "./list", method: 'get',
            fit: ((typeof(isFit) != 'undefined' && isFit == false) ? false : true),
            fitColumns: false,
            idField: "id", singleSelect: true,
            showFooter: true, remoteSort: false, multiSort: false,
            toolbar: toolbarDate
        });


        //拿到specialFilteringColumnDefinition进行合并操作
        _.each(dictColumns, function (field) {

            var enumValues = [{value: '', text: '全部'}], dictValue = getAllDictValue(field);
            // 遍历可选值，构造datagrid-filter能够识别的JSON对象
            _.each(_.allKeys(dictValue), function (key) {
                enumValues.push({value: _.propertyOf(dictValue)(key), text: _.propertyOf(dictValue)(key)});
            });

            specialFilteringColumnDefinition.push(comboboxFilterDefinetion(field, enumValues));
        });
        datagrid.datagrid('enableFilter', specialFilteringColumnDefinition);


        $("#btnSave").click(function () {
            if ($(this).linkbutton('options').disabled == false && $('#modifyForm').form('validate')) {
                try {
                    preSaveFn()
                } catch (e) {
                }

                $.post(ctx + "/rms/" + moduleContext + "/save", $("#modifyForm").serialize(), function (data) {
                    if (data.code === 1) datagrid.datagrid("reload");
                    $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
                });

                calculateCounts();

                $.post(ctx + "/rms/" + moduleContext + "/saveSec", $("#sce_modifyForm").serialize(), function (data) {
                    $.messager.show({title: "提示", msg: data.message, timeout: 3000, showType: 'slide'});
                });


            }
        });
    });


    /***
     * 通用的Easyui字典值转换方法
     * @param value 值
     * @param row 行
     * @param index 索引
     * @param field 字段名
     */
    function globalFormat(value, row, index, field) {
        return _.propertyOf(getAllDictValue(field))(value);
    }

    /**
     * 拿到可用的字典值
     * @param fieldCode 字段名
     */
    function getAllDictValue(field) {
        return _.last(_.values(_.pick(dict, _.last(_.keys(_.pick(mapping, function (value, key, object) {
            return _.contains(value, field);
        }))))));
    }

    function downloadExcel() {
        var expMap = [];
        if (!dataGridMeta) dataGridMeta = getDataGridDraggableAndVisibleMetaInfo($("#datagrid"));
        _.each(dataGridMeta.columns, function (o) {
            if (o.visible) {
                var obj = {};
                obj[o.field] = o.text;
                expMap.push(obj);
            }
        });

        //location.href = ctx + '/rms/' + moduleContext + '/expExcel';
        downLoadFile({
            url:ctx + '/rms/' + moduleContext + '/expExcel',
            data:{fields:JSON.stringify(expMap)}
        });
    }

    var downLoadFile = function (options) {
        var config = $.extend(true, { method: 'post' }, options);
        var $iframe = $('<iframe id="down-file-iframe" />');
        var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
        $form.attr('action', config.url);
        for (var key in config.data) {
            $form.append('<input type="hidden" name="' + key + '" value="' + config.data[key].replace(/\"/g,"'") + '" />');
        }
        $iframe.append($form);
        $(document.body).append($iframe);
        $form[0].submit();
        $iframe.remove();
    }
</script>

</body>
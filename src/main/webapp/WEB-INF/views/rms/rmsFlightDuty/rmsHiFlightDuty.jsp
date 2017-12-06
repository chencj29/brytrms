<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <style>
        .formtable_new td, .formtable_new th {
            color: #5E7595;
            font-size: 12px;
            font-weight: bold;
            text-align: center;
            padding: 2px;
        }

        .formtable_new tr:nth-child(1) td, .formtable_new tr:nth-child(1) td,
        .formtable_new tr:nth-child(2) td, .formtable_new tr:nth-child(2) td,
        .formtable_new tr:nth-child(3) td, .formtable_new tr:nth-child(3) td {
            padding: 8px;
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
    </style>
    <title>值机数据</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'" id="dgContainer" style="overflow-y:hidden;"></div>
    <div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/rmsFlightDuty/hiSave" id="modifyForm">
            <input type="hidden" name="flightDynamicId" value=""/>
            <table width="100%" class="formtable formtable_new">
                <tr>
                    <td rowspan="3"></td>
                    <td rowspan="3">地名</td>
                    <td colspan="3">航班号:&nbsp;<label name="flightNum"></label></td>
                    <td colspan="4">行李</td>
                    <td colspan="4">机号:&nbsp;<label name="aircraftNum"></label></td>
                    <td colspan="4">日期:&nbsp;<label name="planDate"></label></td>
                </tr>
                <tr>
                    <td colspan="3">旅客</td>
                    <td colspan="2">直达行李</td>
                    <td colspan="2">转运行李</td>
                    <td colspan="4">VIP</td>
                    <td colspan="2">头等舱</td>
                    <td colspan="2">公务舱</td>
                </tr>
                <tr>
                    <td>成人</td>
                    <td>儿童</td>
                    <td>婴儿</td>
                    <td>件数</td>
                    <td>重量</td>
                    <td>件数</td>
                    <td>重量</td>
                    <td>人数</td>
                    <td>座位</td>
                    <td>行李件</td>
                    <td>行李重</td>
                    <td>人数</td>
                    <td>座位</td>
                    <td>人数</td>
                    <td>座位</td>
                </tr>
                <tr>
                    <td style="width:60px;">经停地1</td>
                    <td>
                        <input type="text" name="addr" readonly style="border:0px;"/>
                        <input type="hidden" name="addrTwoCode" value=""/>
                        <input type="hidden" name="addrTwoNature" value="PASS1"/>
                        <input type="hidden" name="id" value=""/>
                    </td>
                    <td><input type="text" name="personCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="childCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="nonstopLuggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="nonstopLuggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="transferLuggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="transferLuggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="vipPersonCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="vipSeat" value="" class="easyui-validatebox"
                               data-options="validType:'length[0,20]'" invalidMessage="最大长度不能超过20"/></td>
                    <td><input type="text" name="vipLuggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="vipLuggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="firstCabinPersonCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="firstCabinSeat" value="" class="easyui-validatebox"
                               data-options="validType:'length[0,20]'" invalidMessage="最大长度不能超过20"/></td>
                    <td><input type="text" name="businessCabinPersonCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="businessCabinSeat" value="" class="easyui-validatebox"
                               data-options="validType:'length[0,20]'" invalidMessage="最大长度不能超过20"/></td>
                </tr>
                <tr>
                    <td>经停地2</td>
                    <td>
                        <input type="text" name="addr" readonly style="border:0px;"/>
                        <input type="hidden" name="addrTwoCode" value=""/>
                        <input type="hidden" name="addrTwoNature" value="PASS2"/>
                        <input type="hidden" name="id" value=""/>
                    </td>
                    <td><input type="text" name="personCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="childCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="nonstopLuggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="nonstopLuggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="transferLuggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="transferLuggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="vipPersonCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="vipSeat" value="" class="easyui-validatebox"
                               data-options="validType:'length[0,20]'" invalidMessage="最大长度不能超过20"/></td>
                    <td><input type="text" name="vipLuggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="vipLuggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="firstCabinPersonCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="firstCabinSeat" value="" class="easyui-validatebox"
                               data-options="validType:'length[0,20]'" invalidMessage="最大长度不能超过20"/></td>
                    <td><input type="text" name="businessCabinPersonCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="businessCabinSeat" value="" class="easyui-validatebox"
                               data-options="validType:'length[0,20]'" invalidMessage="最大长度不能超过20"/></td>
                </tr>
                <tr>
                    <td>目的站</td>
                    <td>
                        <input type="text" name="addr" readonly style="border:0px;"/>
                        <input type="hidden" name="addrTwoCode" value=""/>
                        <input type="hidden" name="addrTwoNature" value="ARRI"/>
                        <input type="hidden" name="id" value=""/>
                    </td>
                    <td><input type="text" name="personCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="childCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="nonstopLuggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="nonstopLuggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="transferLuggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="transferLuggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="vipPersonCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="vipSeat" value="" class="easyui-validatebox"
                               data-options="validType:'length[0,20]'" invalidMessage="最大长度不能超过20"/></td>
                    <td><input type="text" name="vipLuggageCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="vipLuggageWeight" value="0" class="easyui-validatebox"
                               data-options="validType:'Digit'" invalidMessage="只能输入数字"/></td>
                    <td><input type="text" name="firstCabinPersonCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="firstCabinSeat" value="" class="easyui-validatebox"
                               data-options="validType:'length[0,20]'" invalidMessage="最大长度不能超过20"/></td>
                    <td><input type="text" name="businessCabinPersonCount" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="businessCabinSeat" value="" class="easyui-validatebox"
                               data-options="validType:'length[0,20]'" invalidMessage="最大长度不能超过20"/></td>
                </tr>
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
                <%--<a class="easyui-linkbutton" id="btnLoadSave" data-options="iconCls:'icon-ok'"--%>
                   <%--style="width:80px">值机结载</a>--%>
            </div>
        </form>

    </div>
</div>
<div id="view-setting-dialog" title="显示设置" class="easyui-dialog" data-options="closed:true, modal:true"
     style="width: 265px; height:500px;"></div>

<script type="text/html" id="datagridTpl">
    <table id="datagrid" class="easyui-datagrid" data-options="remoteSort:false">
        <thead>
        <tr>
            <th field="id" width="100" hidden="true" sortable="false" order="false">id</th>
            <th field="planDate" width="100" align="center" sortable="true" formatter="dateFmt">航班日期</th>
            <th field="flightCompanyCode" width="50" align="center" sortable="true">航空公司</th>
            <th field="flightCompanySubInfoCode" width="50" align="center" sortable="true">子公司<br>代码</th>
            <th field="flightCompanySubInfoName" width="60" align="center" sortable="true">子公司<br>公司名称</th>
            <th field="flightNum" width="50" align="center" sortable="true">航班号</th>
            <th field="aircraftNum" width="50" align="center" sortable="true">飞机号</th>
            <th field="travellersAllCount" width="100" align="center" sortable="true">本站出港<br>旅客总数</th>
            <th field="personCount" width="100" align="center" sortable="true">本站出港<br>成人数</th>
            <th field="childCount" width="100" align="center" sortable="true">本站出港<br>儿童数</th>
            <th field="babyCount" width="100" align="center" sortable="true">本站出港<br>婴儿数</th>
            <th field="vipPersonCount" width="100" align="center" sortable="true">本站出港<br>vip人数</th>
            <th field="babyAloneCount" width="100" align="center" sortable="true">本站出港<br>无陪伴儿童人数</th>
            <th field="nonstopLuggageCount" width="100" align="center" sortable="true">本站出港<br>直达行李数量</th>
            <th field="nonstopLuggageWeight" width="100" align="center" sortable="true">本站出港<br>直达行李重量</th>
            <th field="transferLuggageCount" width="100" align="center" sortable="true">本站出港<br>转运行李数量</th>
            <th field="transferLuggageWeight" width="100" align="center" sortable="true">本站出港<br>转运行李重量</th>
        </tr>
        </thead>
    </table>
</script>

<script>
    var moduleContext = "rmsHiFlightDuty",moduleContext_old = "rmsFlightDuty";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:rmsFlightDuty:hiEdit">flagDgEdit = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsFlightDuty:insert">flagDgInsert = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsFlightDuty:del">flagDgDel = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsFlightDuty:hiExpExcel">flagDgExp = true;
    </shiro:hasPermission>

    flagValidate = true; //取消所有必填验证

    $(function () {

    });

    /*
     * 显示航班虽对应的每个航段值机数据
     */
    function getFlightDutys() {
        var row = datagrid.datagrid('getSelected');
        if (row) {
            $.ajax({
                type: "post",
                async: false,
                url: "${ctx}/rms/rmsFlightDuty/getHiFlightDutys",
                data: {flightDynamicId: row.id},
                success: function (msg) {
                    if (msg.code == 1) {
                        //填充每个航段的值机数据
                        setValueToFlightDutyForm(msg.result.flightDutys, row.id);
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
        if (!row) return;
        var _planDate = row.planDate;
        _planDate = !_planDate ? "--" : moment(_planDate).format("YYYY-MM-DD");
        $("#modifyForm table label[name='planDate']").html(_planDate);
        $("#modifyForm table label[name='flightNum']").html(row.flightNum);
        $("#modifyForm table label[name='aircraftNum']").html(row.aircraftNum);
    }


    //该方法用于填充值机数据
    function setValueToFlightDutyForm(flightDutys, flightDynamicId) {
        $("#modifyForm input[name='flightDynamicId']").val(flightDynamicId);
        for (var i = 0; i < flightDutys.length; i++) {
            var addrTwoNature = flightDutys[i]['addrTwoNature'];
            var _$tr;
            if (addrTwoNature == 'PASS1') {
                _$tr = $("#modifyForm table tr:nth-child(4)")
            } else if (addrTwoNature == 'PASS2') {
                _$tr = $("#modifyForm table tr:nth-child(5)")
            } else if (addrTwoNature == 'ARRI') {
                _$tr = $("#modifyForm table tr:nth-child(6)")
            }
            _$tr.find("input[name='id']").val(flightDutys[i]['id']);
            _$tr.find("input[name='addr']").val(flightDutys[i]['addr']);
            _$tr.find("input[name='addrTwoCode']").val(flightDutys[i]['addrTwoCode']);
            _$tr.find("input[name='personCount']").val(flightDutys[i]['personCount']);
            _$tr.find("input[name='childCount']").val(flightDutys[i]['childCount']);
            _$tr.find("input[name='babyCount']").val(flightDutys[i]['babyCount']);
            _$tr.find("input[name='nonstopLuggageCount']").val(flightDutys[i]['nonstopLuggageCount']);
            _$tr.find("input[name='nonstopLuggageWeight']").val(flightDutys[i]['nonstopLuggageWeight']);
            _$tr.find("input[name='transferLuggageCount']").val(flightDutys[i]['transferLuggageCount']);
            _$tr.find("input[name='transferLuggageWeight']").val(flightDutys[i]['transferLuggageWeight']);
            _$tr.find("input[name='vipPersonCount']").val(flightDutys[i]['vipPersonCount']);
            _$tr.find("input[name='vipSeat']").val(flightDutys[i]['vipSeat']);
            _$tr.find("input[name='vipLuggageCount']").val(flightDutys[i]['vipLuggageCount']);
            _$tr.find("input[name='vipLuggageWeight']").val(flightDutys[i]['vipLuggageWeight']);
            _$tr.find("input[name='firstCabinPersonCount']").val(flightDutys[i]['firstCabinPersonCount']);
            _$tr.find("input[name='firstCabinSeat']").val(flightDutys[i]['firstCabinSeat']);
            _$tr.find("input[name='businessCabinPersonCount']").val(flightDutys[i]['businessCabinPersonCount']);
            _$tr.find("input[name='businessCabinSeat']").val(flightDutys[i]['businessCabinSeat']);
        }
    }

    function dateFmt(v, r, i, f) {
        return !v ? "" : moment(v).format("YYYY-MM-DD");
    }

</script>
<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/jquery.validatebox.extend.js"></script>
<script src="${ctxAssets}/scripts/aui-artTemplate/dist/template.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.Ext_duty.js"></script>
<script>

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
    var flagDgEdit, flagDgDel, flagDgInsert, flagDgImp, flagDgExp;  //修改、删除、新增、导入按钮开关
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
            c.layout('panel', 'south').outerHeight();
            var h = (c.layout('panel', 'south').outerHeight() - ($(window).height() - $('#btnSave').offset().top) + 38);
            c.layout('panel', 'south').panel('resize', {height: h});
            c.layout('resize', {height: 'auto'});
        }

        //增加选择历史日期
        toolbarDate.push({
            text: '请选择日期：<input type="text" id="queryDate"/>',
        });

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
            //处理行选中事件
            $('#datagrid').datagrid({
                onClickRow: function (rowIndex) {
                    getFlightDutys()
                },
                onLoadSuccess: function (data) {
                    //datagrid.datagrid("selectRow", 0);
                    getFlightDutys();
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
            rownumbers: true, url: "./hiList", method: 'get',
            fit: ((typeof(isFit) != 'undefined' && isFit == false) ? false : true),
            fitColumns: ((typeof(isFitColumns) != 'undefined' && isFitColumns == false ) ? false : true),
            idField: "id", singleSelect: true,
            showFooter: true, remoteSort: false, multiSort: false,
            toolbar: toolbarDate, onLoadSuccess: function () {
                $("#queryDate").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({
                        dateFmt: 'yyyy-MM-dd',
                        onpicking: queryDateFun
                    });
                });
            }
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
                    preSaveFn();
                } catch (e) {
                }

                $.post(ctx + "/rms/" + moduleContext_old + "/hiSave", $("#modifyForm").serialize(), function (data) {
                    if (data.code === 1) datagrid.datagrid("reload");
                    $.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
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

    function queryDateFun(dp) {
        var getDateStr = dp.cal.getNewDateStr();
        if (getDateStr) {
            var queryParams = datagrid.datagrid('options').queryParams;
            queryParams.dateStr = getDateStr;
            datagrid.datagrid('options').queryParams = queryParams;
            datagrid.datagrid('reload');
        }
    }

    function downloadExcel() {
        if (!$("#queryDate").val()) {
            $.messager.show({title: "提示", msg: "请选择日期", timeout: 3000, showType: 'slide'});
            return
        }

        var expMap = [];
        if (!dataGridMeta) dataGridMeta = getDataGridDraggableAndVisibleMetaInfo($("#datagrid"));
        _.each(dataGridMeta.columns, function (o) {
            if (o.visible) {
                var obj = {};
                obj[o.field] = o.text;
                expMap.push(obj);
            }
        });

        //location.href = ctx + '/rms/' + moduleContext_old + '/expExcel?dateStr=' + $("#queryDate").val();
        downLoadFile({
            url: ctx + '/rms/' + moduleContext_old + '/expExcel',
            data: {
                fields: JSON.stringify(expMap),
                dateStr: $("#queryDate").val()
            }
        });
    }

    var downLoadFile = function (options) {
        var config = $.extend(true, {method: 'post'}, options);
        var $iframe = $('<iframe id="down-file-iframe" />');
        var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
        $form.attr('action', config.url);
        for (var key in config.data) {
            $form.append('<input type="hidden" name="' + key + '" value="' + config.data[key].replace(/\"/g, "'") + '" />');
        }
        $iframe.append($form);
        $(document.body).append($iframe);
        $form[0].submit();
        $iframe.remove();
    }
</script>
</body>
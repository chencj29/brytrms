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
        .formtable_new tr:nth-child(2) td, .formtable_new tr:nth-child(2) td {
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
    <title>上站舱单数据管理</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'" id="dgContainer" style="overflow-y:hidden;"></div>
    <div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/rmsFlightPreManifest/save" id="modifyForm">
            <input type="hidden" name="flightDynamicId">
            <table width="100%" class="formtable formtable_new">
                <tr>
                    <td rowspan="2" style="width: 120px;">
                        日&nbsp;期:&nbsp;<label name="planDate"></label>
                        <br/>
                        航班号：&nbsp;<label name="flightNum"></label>
                    </td>
                    <td rowspan="2">地名</td>
                    <td colspan="3">进港旅客</td>
                    <td colspan="3">过站旅客</td>
                    <td colspan="3">货物</td>
                    <td colspan="2">邮件</td>
                    <td colspan="2">行李</td>
                </tr>
                <tr>
                    <td>成人</td>
                    <td>儿童</td>
                    <td>婴儿</td>
                    <td>成人</td>
                    <td>儿童</td>
                    <td>婴儿</td>
                    <td>件数</td>
                    <td>重量</td>
                    <td>件数</td>
                    <td>重量</td>
                    <td>件数</td>
                    <td>重量</td>
                </tr>
                <tr>
                    <td>经停地1</td>
                    <td>
                        <input type="text" name="addr" readonly style="border:0px;"/>
                        <input type="hidden" name="addrCode" value=""/>
                        <input type="hidden" name="addrNature" value="PASS1"/>
                        <input type="hidden" name="id" value=""/>
                    </td>
                    <td><input type="text" name="personCountJ" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="childCountJ" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCountJ" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="personCountP" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="childCountP" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCountP" value="0" class="easyui-validatebox"
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
                <tr>
                    <td>经停地2</td>
                    <td>
                        <input type="text" name="addr" readonly style="border:0px;"/>
                        <input type="hidden" name="addrCode" value=""/>
                        <input type="hidden" name="addrNature" value="PASS2"/>
                        <input type="hidden" name="id" value=""/>
                    </td>
                    <td><input type="text" name="personCountJ" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="childCountJ" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCountJ" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="personCountP" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="childCountP" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCountP" value="0" class="easyui-validatebox"
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
                <tr>
                    <td>起飞地</td>
                    <td>
                        <input type="text" name="addr" readonly style="border:0px;"/>
                        <input type="hidden" name="addrCode" value=""/>
                        <input type="hidden" name="addrNature" value="DEPA"/>
                        <input type="hidden" name="id" value=""/>
                    </td>
                    <td><input type="text" name="personCountJ" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="childCountJ" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCountJ" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="personCountP" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="childCountP" value="0" class="easyui-validatebox"
                               data-options="validType:'Number'" invalidMessage="只能输入整数"/></td>
                    <td><input type="text" name="babyCountP" value="0" class="easyui-validatebox"
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
            <th field="planDate" width="80" align="center" sortable="true" formatter="dateFmt">计划日期</th>
            <th field="flightCompanyCode" width="40" align="center" sortable="true">航空公司</th>
            <th field="flightCompanySubInfoCode" width="40" align="center" sortable="true">子公司<br>代码</th>
            <th field="flightCompanySubInfoName" width="60" align="center" sortable="true">子公司<br>公司名称</th>
            <th field="flightNum" width="40" align="center" sortable="true">航班号码</th>
            <th field="aircraftNum" width="40" align="center" sortable="true">飞机号</th>
            <th field="departureAirportName" width="80" align="center" sortable="true">起飞地</th>
            <th field="passAirport1Name" width="80" align="center" sortable="true">经停地1</th>
            <th field="passAirport2Name" width="80" align="center" sortable="true">经停地2</th>
            <th field="touristCountJ" width="60" align="center" sortable="true">进港旅客<br>总数</th>
            <th field="babyCountJ" width="60" align="center" sortable="true">进港旅客<br>婴儿数</th>
            <th field="personCountJ" width="60" align="center" sortable="true">进港旅客<br>成人数</th>
            <th field="childCountJ" width="60" align="center" sortable="true">进港旅客<br>儿童数</th>
            <th field="touristCountP" width="60" align="center" sortable="true">过站旅客<br>总数</th>
            <th field="babyCountP" width="60" align="center" sortable="true">过站旅客<br>婴儿数</th>
            <th field="personCountP" width="60" align="center" sortable="true">过站旅客<br>成人数</th>
            <th field="childCountP" width="60" align="center" sortable="true">过站旅客<br>儿童数</th>
            <th field="goodsCount" width="60" align="center" sortable="true">过站货物<br>数量</th>
            <th field="goodsWeight" width="60" align="center" sortable="true">过站货物<br>重量</th>
            <th field="mailCount" width="60" align="center" sortable="true">过站邮件<br>件数</th>
            <th field="mailWeight" width="60" align="center" sortable="true">过站邮件<br>重量</th>
            <th field="luggageCount" width="60" align="center" sortable="true">过站行李<br>件数</th>
            <th field="luggageWeight" width="60" align="center" sortable="true">过站行李<br>重量</th>
        </tr>
        </thead>
    </table>
</script>

<script>
    var moduleContext = "rmsHiFlightPreManifest",moduleContext_old = "rmsFlightPreManifest";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:rmsFlightPreManifest:hiEdit">flagDgEdit = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsFlightPreManifest:insert">flagDgInsert = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsFlightPreManifest:del">flagDgDel = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsFlightPreManifest:hiExpExcel">flagDgExp = true;
    </shiro:hasPermission>


    flagValidate = true; //关闭非空校验

    $(function () {
    });

    /*
     //自定义验证规则。例如，定义一个 minLength 验证类 验证长度
     $.extend($.fn.validatebox.defaults.rules, {
     minLength: {
     validator: function(value, param){
     return value.length >= param[0];
     },
     message: 'Please enter at least {0} characters.'
     }
     });*/

    /*
     * 显示航班虽对应的每个航段值机数据
     */
    function getListAndFill (flightDynamicId) {
        var row = datagrid.datagrid('getSelected');
        if (row) {
            $.ajax({
                type: "post",
                async: false,
                url: "${ctx}/rms/" + moduleContext_old + "/getHiFlightPreManifests",
                data: { flightDynamicId: flightDynamicId },
                success: function (msg) {
                    if (msg.code == 1) {
                        setValueToForm(msg.result.flightPreManifests, flightDynamicId);
                    } else {
                        alert(msg.message);
                    }
                }
            });
        }
        fillDynamicMsg();
    }


    function fillDynamicMsg () {
        var row = datagrid.datagrid("getSelected");
        var _planDate = row.planDate;
        _planDate = !_planDate ? "--" : moment(_planDate).format("YYYY-MM-DD");
        $("#modifyForm table label[name='planDate']").html(_planDate);
        $("#modifyForm table label[name='flightNum']").html(row.flightNum);
    }


    function setValueToForm (datas, flightDynamicId) {
        $("#modifyForm input[name='flightDynamicId']").val(flightDynamicId);
        for (var i = 0; i < datas.length; i++) {
            var addrNature = datas[i]['addrNature'];
            var _$tr;
            if (addrNature == 'PASS1') {
                _$tr = $("#modifyForm table tr:nth-child(3)")
            } else if (addrNature == 'PASS2') {
                _$tr = $("#modifyForm table tr:nth-child(4)")
            } else if (addrNature == 'DEPA') {
                _$tr = $("#modifyForm table tr:nth-child(5)")
            }
            _$tr.find("input[name='id']").val(datas[i]['id']);
            _$tr.find("input[name='addr']").val(datas[i]['addr']);
            _$tr.find("input[name='addrCode']").val(datas[i]['addrCode']);
            _$tr.find("input[name='personCountJ']").val(datas[i]['personCountJ']);
            _$tr.find("input[name='childCountJ']").val(datas[i]['childCountJ']);
            _$tr.find("input[name='babyCountJ']").val(datas[i]['babyCountJ']);
            _$tr.find("input[name='personCountP']").val(datas[i]['personCountP']);
            _$tr.find("input[name='childCountP']").val(datas[i]['childCountP']);
            _$tr.find("input[name='babyCountP']").val(datas[i]['babyCountP']);
            _$tr.find("input[name='goodsCount']").val(datas[i]['goodsCount']);
            _$tr.find("input[name='goodsWeight']").val(datas[i]['goodsWeight']);
            _$tr.find("input[name='mailCount']").val(datas[i]['mailCount']);
            _$tr.find("input[name='mailWeight']").val(datas[i]['mailWeight']);
            _$tr.find("input[name='luggageCount']").val(datas[i]['luggageCount']);
            _$tr.find("input[name='luggageWeight']").val(datas[i]['luggageWeight']);
        }
    }


    function dateFmt (v, r, i, f) {
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
                    else datagrid.datagrid('addFilterRule', { field: fieldCode, op: ['equal'], value: value });
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
            c.layout('panel', 'south').panel('resize', { height: h });
            c.layout('resize', { height: 'auto' });
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
            $("#datagrid").datagrid({
                onSelect: function (index, row) {
                    getListAndFill(row.flightDynamicId);
                },
                onLoadSuccess: function (data) {
                    //datagrid.datagrid("selectRow", 0);
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
            rownumbers: true, url: "./hiList", method: 'get',
            fit: ((typeof(isFit) != 'undefined' && isFit == false) ? false : true),
            fitColumns: false,
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

            var enumValues = [{ value: '', text: '全部' }], dictValue = getAllDictValue(field);
            // 遍历可选值，构造datagrid-filter能够识别的JSON对象
            _.each(_.allKeys(dictValue), function (key) {
                enumValues.push({ value: _.propertyOf(dictValue)(key), text: _.propertyOf(dictValue)(key) });
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

                $.post(ctx + "/rms/" + moduleContext_old + "/hiSave", $("#modifyForm").serialize(), function (data) {
                    if (data.code === 1) datagrid.datagrid("reload");
                    $.messager.show({ title: "提示", msg: data.message, timeout: 5000, showType: 'slide' });
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
    function globalFormat (value, row, index, field) {
        return _.propertyOf(getAllDictValue(field))(value);
    }

    /**
     * 拿到可用的字典值
     * @param fieldCode 字段名
     */
    function getAllDictValue (field) {
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
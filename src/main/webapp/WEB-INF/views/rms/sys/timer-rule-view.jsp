<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>规则列表</title>
</head>
<body>
<input id="configId" type="hidden" value="${configId }"/>
<div class="easyui-layout" id="ruleMainLayout" data-options="fit:true">
    <div data-options="region:'center'">
        <div class="easyui-layout" data-options="fit:true" id="ruleCenterLayout">
            <div data-options="region:'center'">
                <table class="easyui-datagrid" title="规则列表" id="ruleDataGrid">
                    <thead>
                    <tr>
                        <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                        <th field="fieldName" width="150" align="center" sortable="true">字段名称</th>
                        <th field="fieldCode" width="150" align="center" sortable="true">字段编码</th>
                        <th field="conditionName" width="150" align="center" sortable="true">条件名称</th>
                        <th field="conditionCode" width="150" align="center" sortable="true">条件编码</th>
                        <th field="thresholdValue" width="150" align="center" sortable="true">预警阀值</th>
                    </tr>
                    </thead>
                </table>
            </div>

            <div data-options="region:'south'" style="height:320px;" id="timerSouthForm">
                <form action="${ctx}/rms/monitor/item-save" id="ruleModifyForm">
                    <input type="hidden" name="id">
                    <table width="100%" class="formtable" id="timerFormTable">
                        <tr>
                            <td>监控字段：</td>
                            <td>
                                <select class="easyui-combobox" name="fields" data-options="editable:false, prompt: '请选择监控字段'">
                                    <option value="">请选择</option>
                                    <c:forEach items="${fields}" var="field">
                                        <option value="${field.value}">${field.text}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>比较方式：</td>
                            <td>
                                <select class="easyui-combobox" name="condition" data-options="editable:false">
                                    <option value="@=">前置时间</option>
                                    <option value="=@">后置时间</option>
                                    <option value=">=">大于等于</option>
                                    <option value="&lt;=">小于等于</option>
                                    <option value="=">等于</option>
                                    <option value="!=">不等于</option>
                                </select>
                            </td>
                            <td>预警阀值：</td>
                            <td>
                                <input type="text" class="easyui-textbox" style="width: 40px;" name="thresholdValue"/>
                            </td>
                        </tr>
                    </table>
                    <div style="text-align: center; margin-top:15px; margin-right:15px;">
                        <a class="easyui-linkbutton" id="ruleBtnAdd" data-options="iconCls:'icon-add'" style="width:80px">添加条件</a>
                        &nbsp;&nbsp;
                        <a class="easyui-linkbutton" id="ruleBtnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    var _entityId = $("#configId").val(), _entity = $utils._loadFragments({url: './get?id=' + _entityId, method: 'get', dataType: 'json'}),
            $form = $("#ruleModifyForm"), $datagrid, conditionArray = {
                "@=": "前置时间", "=@": "后置时间", ">=": "大于等于", "<=": "小于等于", "=": "等于", "!=": "不等于"
            }

    $(function () {


        $datagrid = $("#ruleDataGrid").datagrid({
            rownumbers: true, url: "./item-list?configId=" + _entityId, method: 'get',
            fit: ((typeof(isFit) != 'undefined' && isFit == false) ? false : true),
            fitColumns: ((typeof(isFitColumns) != 'undefined' && isFitColumns == false ) ? false : true),
            idField: "id", singleSelect: true, showFooter: true, remoteSort: false, multiSort: false, toolbar: [],
            onLoadSuccess: function (data) {
                //数据回填
                //$("#selField").combobox("setValues", _.pluck(data.rows, "fieldCode"));

                // step 1 清空form下的元素
                $("#timerFormTable").html("");

                var rowIndex = 0;
                // step 2 根据数据重绘表单
                data.rows.forEach(function (item) {
//                    var _htmlCode = $("#conditionTpl").html();

//                    $(_htmlCode).find("option[value='" + item.fieldCode + "']:first").attr("selected", "selected");
//                    $(_htmlCode).find("option[value='" + item.conditionCode + "']:first").attr("selected", "selected");
//                    $(_htmlCode).find("input[name=thresholdValue]:first").val(item.thresholdValue);

                    $("#timerFormTable").append($("#conditionTpl").html());
                    if (!rowIndex) $("#timerFormTable > tr:last a[name=ruleBtnRemove]").remove()
                    $("#timerFormTable > tr:last select[name=fields] option[value='" + item.fieldCode + "']").attr("selected", "selected");
                    $("#timerFormTable > tr:last select[name=condition] option[value='" + item.conditionCode + "']").attr("selected", "selected");
                    $("#timerFormTable > tr:last input[name=thresholdValue]").val(item.thresholdValue);

                    $("select[name=condition], select[name=fields]").combobox();
                    $("input[name=thresholdValue]").textbox();

                    rowIndex++;


//
//                    $("select[name=condition]").combobox("setValue", item.conditionCode);
//                    $("select[name=fields]").combobox("setValue", item.fieldCode);
//                    $("input[name=thresholdValue]").textbox("setValue", item.thresholdValue);
//                    $("input[name=thresholdValue]").textbox("setText", item.thresholdValue);
                });

            }
        });

        $("#ruleBtnAdd").click(function () {
            $("#timerFormTable").append($("#conditionTpl").html());
            $("select[name=condition], select[name=fields]").combobox();
            $("input[name=thresholdValue]").textbox();
            $("#timerSouthForm").scrollTop(10000); //保持在最底部
        });


        $("#ruleBtnSave").click(function (e) {
            e.preventDefault();

            var fieldList = $("select[comboname=fields]:first").children().filter(function () {
                return $(this).val() != '';
            }).map(function () {
                return {value: $(this).val(), text: $(this).text()};
            });

            var codes = $("input[name=fields]:hidden").map(function () {
                return $(this).val();
            });

            // 查找是否存在空值
            if (_.contains(codes, "")) {
                $.messager.show({title: "提示", msg: '请选择监控字段！', timeout: 5000, showType: 'slide'});
                return false;
            }

            var thresholdValues = $("input[name=thresholdValue]:hidden").map(function () {
                return $(this).val();
            });

            // 查找是否存在空值
            if (_.contains(thresholdValues, "")) {
                $.messager.show({title: "提示", msg: '请填写预警阀值！', timeout: 5000, showType: 'slide'});
                return false;
            }

            var conditions = $("input[name=condition]:hidden").map(function () {
                return $(this).val();
            });

            //检测codes中是否存在相同的值
            var invalidData = _.omit(_.countBy(codes), function (value, key, object) {
                return value === 1;
            });

            var buffer = [], params = [];
            _.each(_.keys(invalidData), function (key) {
                buffer.push("字段名称：" + _.filter(fieldList, function (item) {
                            return item.value === key
                        })[0].text + "，重复次数：" + _.propertyOf(invalidData)(key) + "<br/>");
            });


            if (!_.isEmpty(buffer)) {
                $.messager.alert("监控字段设置重复提示", buffer.join("") + "</br>请调整。");
                buffer.$clear();
                return false;
            }

            for (var i = 0, len = codes.length; i < len; i++) {
                params.$push({
                    fieldCode: codes[i], fieldName: _.filter(fieldList, function (item) {
                        return item.value === codes[i]
                    })[0].text,
                    conditionCode: conditions[i], conditionName: conditionArray[conditions[i]], thresholdValue: thresholdValues[i]
                });
            }

            if (!_.isEmpty(params)) {
                $.post('./item-save-timer', {configId: _entityId, class: _entity.monitorClass, configType: _entity.configType, data: JSON.stringify(params)}, function (data) {
                    if (data.code === 1) {
                        $.messager.show({title: "提示", msg: '保存成功！', timeout: 5000, showType: 'slide'});
                        $datagrid.datagrid("reload");
                    } else {
                        $.messager.show({title: "提示", msg: '保存失败：' + data.message, timeout: 5000, showType: 'slide'});
                    }
                });
            }
        });

        $("body").on("click", "a[name=ruleBtnRemove]", {}, function () {
            $(this).parent().parent().remove();
        });
    });
</script>
<script type="text/html" id="conditionTpl">
    <tr>
        <td>监控字段：</td>
        <td>
            <select class="easyui-combobox" name="fields" data-options="editable:false">
                <option value="">请选择</option>
                <c:forEach items="${fields}" var="field">
                    <option value="${field.value}">${field.text}</option>
                </c:forEach>
            </select>
        </td>
        <td>比较方式：</td>
        <td>
            <select class="easyui-combobox" name="condition" data-options="editable:false">
                <option value="@=">前置时间</option>
                <option value="=@">后置时间</option>
                <option value=">=">大于等于</option>
                <option value="&lt;=">小于等于</option>
                <option value="=">等于</option>
                <option value="!=">不等于</option>
            </select>
        </td>
        <td>预警阀值：</td>
        <td>
            <input type="text" class="easyui-textbox" style="width: 40px;" name="thresholdValue"/>
            <a href="javascript:void(0)" name="ruleBtnRemove">[删除]</a>
        </td>
    </tr>
</script>
</body>
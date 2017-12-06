<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>机位基础信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="机位基础信息" id="datagrid" sortName="aircraftStandNum" sortOrder="asc">
            <thead>
            <tr>
                <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                <th field="aircraftStandNum" width="100" align="center" sortable="true">机位号</th>
                <th field="aircraftParkNum" width="100" align="center" sortable="true">机坪号</th>
                <th field="aircraftParkDispatcher" width="100" align="center" sortable="true">机坪调度者</th>
                <th field="hasSalon" width="100" formatter="globalFormat" align="center" sortable="true">是否有廊桥</th>
                <th field="salonStatus" width="100" formatter="globalFormat" align="center" sortable="true">廊桥状态</th>
                <th field="hasOilpipeline" width="100" formatter="globalFormat" align="center" sortable="true">是否有油管</th>
                <th field="oilpipelineStatus" width="100" formatter="globalFormat" align="center" sortable="true">油管状态</th>
                <th field="leftAsNum" width="100" align="center" sortable="true">左机位号</th>
                <th field="rightAsNum" width="100" align="center" sortable="true">右机位号</th>
                <th field="frontCoast" width="100" align="center" sortable="true">前滑行道</th>
                <th field="backCoast" width="100" align="center" sortable="true">后滑行道</th>
                <th field="leftWheelLine" width="100" align="center" sortable="true">左鼻轮线距</th>
                <th field="rightWheelLine" width="100" align="center" sortable="true">右鼻轮线距</th>
                <th field="wingLength" width="100" align="center" sortable="true">翼展</th>
                <th field="available" width="100" formatter="globalFormat" align="center" sortable="true">是否可用</th>
                <th field="airportTerminalCode" width="100" align="center" sortable="true">所属航站楼</th>
                <th field="orderIndex" width="100" align="center" sortable="true">排序字段</th>
            </tr>
            </thead>
        </table>
    </div>

    <div data-options="region:'south'" style="height:270px;">
        <form action="${ctx}/rms/aircraftStand/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>机位号：</td>
                    <td><input type="text" name="aircraftStandNum" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[0,36]'"  invalidMessage="最大长度不能超过36"></td>
                    <td>机坪号：</td>
                    <td><input type="text" name="aircraftParkNum" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[0,10]'" invalidMessage="最大长度不能超过10"
                    ></td>
                    <td>机坪调度者：</td>
                    <td><input type="text" name="aircraftParkDispatcher" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[0,50]'" invalidMessage="最大长度不能超过50"
                    ></td>
                    <td>是否有廊桥：</td>
                    <td>
                        <input type="radio" name="hasSalon" value="1" checked>&nbsp;是
                        <input type="radio" name="hasSalon" value="0">&nbsp;否
                    </td>
                </tr>
                <tr>
                    <td>廊桥状态：</td>
                    <td>
                        <input type="radio" name="salonStatus" value="1" checked>&nbsp;可用
                        <input type="radio" name="salonStatus" value="0">&nbsp;不可用
                    </td>
                    <td>是否有油管：</td>
                    <td>
                        <input type="radio" name="hasOilpipeline" value="1" checked>&nbsp;是
                        <input type="radio" name="hasOilpipeline" value="0">&nbsp;否
                    </td>
                    <td>油管状态：</td>
                    <td>
                        <input type="radio" name="oilpipelineStatus" value="1" checked>&nbsp;可用
                        <input type="radio" name="oilpipelineStatus" value="0">&nbsp;不可用
                    </td>
                    <td>左机位号：</td>
                    <td><input type="text" name="leftAsNum" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[0,5]',novalidate:true" invalidMessage="最大长度不能超过5"
                    ></td>
                </tr>
                <tr>
                    <td>右机位号：</td>
                    <td><input type="text" name="rightAsNum" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[0,5]',novalidate:true" invalidMessage="最大长度不能超过5"
                    ></td>
                    <td>前滑行道：</td>
                    <td><input type="text" name="frontCoast" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[0,5]',novalidate:true" invalidMessage="最大长度不能超过5"
                    ></td>
                    <td>后滑行道：</td>
                    <td><input type="text" name="backCoast" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[0,5]',novalidate:true" invalidMessage="最大长度不能超过5"
                    ></td>
                    <td>左鼻轮线距：</td>
                    <td><input type="text" id="leftWheelLine" name="leftWheelLine" class="easyui-textbox easyui-numberbox"
                               data-options="max:99.99,precision:'2',novalidate:true" invalidMessage="必须填写0~99.99之间的数字"
                    ></td>
                </tr>
                <tr>
                    <td>右鼻轮线距：</td>
                    <td><input type="text" id="rightWheelLine" name="rightWheelLine" class="easyui-textbox easyui-numberbox"
                               data-options="max:99.99,precision:'2',novalidate:true" invalidMessage="必须填写0~99.99之间的数字"
                    ></td>
                    <td>翼展：</td>
                    <td><input type="text" id="wingLength" name="wingLength" class="easyui-textbox easyui-numberbox"
                               data-options="max:99.99,precision:'2',novalidate:true" invalidMessage="必须填写0~99.99之间的数字"
                    ></td>
                    <td>是否可用：</td>
                    <td>
                        <input type="radio" name="available" value="1" checked>&nbsp;是
                        <input type="radio" name="available" value="0">&nbsp;否
                    </td>
                    <td>所属航站楼：</td>
                    <td colspan="3">
                        <select class="easyui-validatebox" style="width:130px;" data-options=""
                                id="airportTerminalCode" name="airportTerminalCode">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>排序字段：</td>
                    <td>
                        <input class="easyui-textbox easyui-validatebox" data-options="novalidate:true" type="text" name="orderIndex"/>
                    </td>
                </tr>
            </table>
            <div style="text-align: center; margin-top:15px; margin-right:15px;">
                <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'" style="width:80px">保存</a>
            </div>
        </form>

    </div>
</div>

<script>
    var moduleContext = "aircraftStand",
    // 拿到字典Map, key = tableName, value = columnName
            dict = dictMap([{key: "", value: "yes_no"}, {key: "", value: "ed_status"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
                "@yes_no": ["hasSalon", "hasOilpipeline", "available"],
                "@ed_status": ["salonStatus", "oilpipelineStatus"]
            };

    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:aircraftStand:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:aircraftStand:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:aircraftStand:del">flagDgDel=true;</shiro:hasPermission>

    $(function () {
        //拿到航站楼数据
        $("#airportTerminalCode").combobox({panelHeight: 'auto',panelMaxHeight: '200',
            url: ctx + '/rms/airportTerminal/jsonData',
            valueField: 'terminalName',
            textField: 'terminalName'
        });

        //
        $("#modifyForm [name=hasOilpipeline]").change(function(){
            oilpipelineIs();
        })

        $('#datagrid').datagrid({onClickRow:function(index,data){
            oilpipelineIs();
        }})
    });

    function oilpipelineIs (){
        if($("#modifyForm [name=hasOilpipeline]:checked").val()==0){
            //$("#modifyForm [name=oilpipelineStatus]").removeAttr("disabled").removeAttr("checked");
            $("#modifyForm [name='oilpipelineStatus'][value='0']").prop("checked",true);
            $("#modifyForm [name=oilpipelineStatus][value='1']").attr("disabled",true);
        }else{
            $("#modifyForm [name=oilpipelineStatus]").prop("disabled",false);
        }
    }

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
</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>
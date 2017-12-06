<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <style>

        .formtable_new td input, .formtable_new th input {
            width: 10%;

        }

        .formtable_new td select, .formtable_new th select {
            width: 10%;

        }

        .formtable_new td:nth-child(odd) {
            width: 8%;
        }

    </style>

    <title>安检旅客信息</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" id="datagrid" data-options="remoteSort:false">
            <thead>
            <tr>
                <th field="id" width="100" hidden="true" rowspan="2">ID</th>
                <th field="checkDate" width="80" align="center" sortable="true" formatter="dateFmt" rowspan="2">航班日期
                </th>
                <th field="flightCompanyCode" width="40" align="center" sortable="true" rowspan="2">航空公司</th>
                <th field="flightNum" width="30" align="center" sortable="true" rowspan="2">航班号</th>
                <th field="arrival" width="100" colspan="2">目的地</th>
                <th field="arrival" width="100" colspan="4">旅客</th>
                <th field="arrival" width="100" colspan="2">不能通过原因</th>
                <th field="arrival" width="100" colspan="2">不能登机原因</th>
            </tr>
            <tr>
                <th field="arrivalAirportCode" width="50" align="center" sortable="true">代码</th>
                <th field="arrivalAirportName" width="60" align="center" sortable="true">地名</th>
                <th field="name" width="50" align="center" sortable="true">姓名</th>
                <th field="sex" width="40" align="center" sortable="true" formatter="sexFmt">性别</th>
                <th field="idCode" width="80" align="center" sortable="true">身份证号</th>
                <th field="nationality" width="60" align="center" sortable="true">国籍</th>
                <th field="noPassReason" width="100" align="center" sortable="true">不通过原因</th>
                <th field="screeners" width="60" align="center" sortable="true">安检员</th>
                <th field="noBoardReason" width="100" align="center" sortable="true">不能登机原因</th>
                <th field="publicSecurityOfficers" width="60" align="center" sortable="true">公安人员</th>
            </tr>
            </thead>
        </table>
    </div>

    <div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/rmsFlightSecurity/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable formtable_new">
                <tr>
                    <td>安检日期</td>
                    <td><input name="checkDate" readonly="readonly" maxlength="20" class="input-medium Wdate "
                               style="width: 100%"
                               value="<fmt:formatDate value="${rmsFlightSecurity.checkDate}" pattern="yyyy-MM-dd"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/></td>
                    <td>达到地</td>
                    <td>
                        <select name="arrivalAirportCode" id="arrivalAirportCode"></select>
                        <input name="arrivalAirportName" type="hidden" id="arrivalAirportName"/>
                    <td>旅客性别</td>
                    <td>
                        <input type="radio" name="sex" id="sex1" value="1"
                               <c:if test="${rmsFlightSecurity.sex eq 1 or rmsFlightSecurity.sex eq ''}">checked</c:if>/>&nbsp;
                        <label>男</label>
                        <input type="radio" name="sex" id="sex2" value="2"
                               <c:if test="${rmsFlightSecurity.sex eq 2}">checked</c:if>/>&nbsp;
                        <label>女</label>
                    </td>
                    <td rowspan="2">未通过<br/>原&nbsp;因</td>
                    <td rowspan="2">
                        <input type="text" name="noPassReason" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[1,1000]'" invalidMessage="最多1000个字符"/>
                    </td>
                    <td rowspan="2">不能登机<br/>原&nbsp;&nbsp;因</td>
                    <td rowspan="2">
                        <input type="text" name="noBoardReason" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[1,1000]'" invalidMessage="最多1000个字符"/>
                    </td>
                </tr>
                <tr>
                    <td>航空公司</td>
                    <td>
                        <select name="flightCompanyCode" id="flightCompanyCode"></select>
                    </td>
                    <td>旅客姓名</td>
                    <td>
                        <input type="text" name="name" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[1,10]'" invalidMessage="最多10个字符"/>
                    </td>
                    <td>旅客国籍</td>
                    <td>
                        <input type="text" name="nationality" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[1,50]'" invalidMessage="50"/>
                    </td>
                </tr>

                <tr>
                    <td>航班号</td>
                    <td>
                        <input type="text" name="flightNum" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[1,4]'" invalidMessage="最多4个字符"/>
                    </td>
                    <td>身份证号</td>
                    <td colspan="3">
                        <input type="text" name="idCode" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'IDCode'" invalidMessage="身份证格式不正确"/>
                    </td>
                    <td>安检人员</td>
                    <td>
                        <input type="text" name="screeners" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[1,100]'" invalidMessage="最多100个字符"/>
                    </td>
                    <td>公安人员</td>
                    <td>
                        <input type="text" name="publicSecurityOfficers" class="easyui-textbox easyui-validatebox"
                               data-options="validType:'length[1,100]'" invalidMessage="最多100个字符"/>
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
    var moduleContext = "rmsFlightSecurity";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:rmsFlightSecurity:edit">flagDgEdit = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsFlightSecurity:edit">flagDgInsert = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsFlightSecurity:edit">flagDgDel = true;
    </shiro:hasPermission>
    flagValidate = true, flagRefresh = true;

    var arrivalAirportCodeDates = "",flightCompanyCodeDates;
    $(function () {

//        $("#flightCompanyCode").combobox({
//            panelHeight: 'auto', panelMaxHeight: '200',
//            url: ctx + '/ams/flightCompanyInfo/jsonData',
//            valueField: 'twoCode',
//            textField: 'chineseShort'
//        });

        if(!flightCompanyCodeDates) {
            $.ajax({
                url:ctx + '/ams/flightCompanyInfo/jsonData',
                dataType: "json",
                async: false,
                success: function (data) {
                    flightCompanyCodeDates = _.map(data,function(o){
                        var result = {};
                        result.twoCode = o.twoCode;
                        result.chineseName = o.chineseName;
                        result.tCodechName = o.twoCode+o.chineseName;
                        return result;
                    });
                }
            });
        }
        //拿到航空公司数据
        $("#flightCompanyCode").combobox({
            panelHeight: 'auto',panelMaxHeight: '200',
            data: flightCompanyCodeDates,
            valueField: 'twoCode',
            textField: 'tCodechName'
        });

        if(!arrivalAirportCodeDates) {
            $.ajax({
                url: ctx + '/ams/amsAddress/jsonData',
                dataType: "json",
                async: false,
                success: function (data) {
                    arrivalAirportCodeDates = _.map(data,function(o){
                        var result = {};
                        result.threeCode = o.threeCode;
                        result.chName = o.chName;
                        result.tCodechName = o.threeCode+o.chName;
                        return result;
                    });
                }
            });
        }

       $("#arrivalAirportCode").combobox({
            panelHeight: 'auto', panelMaxHeight: '200',
            data:arrivalAirportCodeDates,
            valueField: 'threeCode',
            textField: 'tCodechName',
            onSelect: function (node) {
                $("#arrivalAirportName").val(node.chName);
        }
    })
    });


    function dateFmt(v, r, i, f) {
        return !v ? "" : moment(v).format("YYYY-MM-DD");
    }

    function sexFmt(v, r, i, f) {
        return (v == 1) ? '男' : '女';
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

<script src="${ctxAssets}/scripts/metadata-dev/jquery.validatebox.extend.js"></script>
<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>
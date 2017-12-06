<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>应急救援方案</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="应急救援方案" id="datagrid" sortName="emPlanCode" sortOrder="asc">
            <thead>
            <tr>
                <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                <th field="emPlanCode" width="100" align="center" sortable="true">方案编号</th>
                <th field="emPlanName" width="100" align="center" sortable="true">方案名称</th>
                <th field="emPlanDesc" width="100" align="center" sortable="true">救援流程说明</th>
                <th field="emPlanFilePath" width="100" align="center" sortable="true">应急救援文件名</th>
            </tr>
            </thead>
        </table>
    </div>

    <div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/emergencyPlan/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>方案编号：</td>
                    <td>
                        <input type="text" name="emPlanCode" class="easyui-textbox easyui-validatebox"
                               data-options=""
                        >
                    </td>
                    <td>方案名称：</td>
                    <td>
                        <input type="text" name="emPlanName" class="easyui-textbox easyui-validatebox"
                               data-options=""
                        >
                    </td>
                    <td>救援流程说明：</td>
                    <td>
                        <input type="text" name="emPlanDesc" class="easyui-textbox easyui-validatebox"
                               data-options=""
                        >
                    </td>
                </tr>
                <tr>
                    <td>应急救援文件：</td>
                    <td>
                        <div class="uploadFile" id="fileDiv">
                            <%--<span id="doc"><input type="text" class="easyui-textbox" id="filepath"/></span>--%>
                            <%--<input type="hidden" name="emPlanFilePath"/>--%>
                            <input class="easyui-filebox" labelPosition="top" id="fileImport" name="fileImport"
                                   data-options="prompt:'请选择word文件...',accept:'.doc,.docx',buttonText:'选择文件'"
                                   style="width:300px">
                            <%--<input type="button"  class="easyui-button" id="btnUploadFile" value="上传" />
                            <input type="button"  class="easyui-button" id="btnDeleteFile" value="删除"  />--%>
                        </div>
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
    var moduleContext = "emergencyPlan";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:emergencyPlan:edit">flagDgEdit = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:emergencyPlan:insert">flagDgInsert = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:emergencyPlan:del">flagDgDel = true;
    </shiro:hasPermission>

    $(function () {
        $("#fileDiv input:file").on('change', function () {
            if (this.files[0].size > 100 * 1024 * 1000) {
                $.messager.show({title: "提示", msg: '文件最大100M！', timeout: 5000, showType: 'slide'});
                $('#fileImport').filebox('clear');
            } else if (this.files[0].name.indexOf(".doc") < 0) {
                $.messager.show({title: "提示", msg: '请选择word文件！', timeout: 5000, showType: 'slide'});
                $('#fileImport').filebox('clear');
            }
            ;
        });
    });

    function preSaveFn() {
        var id = $('#modifyForm [name=id]').val();
        $.ajax({
            url: "${ctx}/rms/emergencyPlan/impDoc?id=" + id,
            type: "post",
            data: new FormData($("#fileDiv input:file").parents("form").get(0)),
            cache: false,
            processData: false,
            contentType: false,
            sync: false,
            success: function (data) {
                if(!id && data){
                    $('#modifyForm [name=id]').val(data);
                }
                //$.messager.show({title: "提示", msg: data.message, timeout: 5000, showType: 'slide'});
            }
        });
    }
</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
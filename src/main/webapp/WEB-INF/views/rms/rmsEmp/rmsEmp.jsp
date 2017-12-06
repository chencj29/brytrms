<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>工作人员基础信息</title>
    <style type="text/css">
        .tree-node {
            height: 18px;
            white-space: nowrap;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">

    <div data-options="region:'west',split:true" title="部门岗位" style="width:150px;">
        <ul class="easyui-tree" id="tree"></ul>
    </div>

    <div data-options="region:'center', border:false">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center' ,split:true ,border:false">
                <table class="easyui-datagrid" title="工作人员基础信息" id="datagrid" sortName="dept" sortOrder="asc">
                    <thead>
                    <tr>
                        <th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                        <th field="dept" width="100" align="center" sortable="true">部门</th>
                        <th field="post" width="100" align="center" sortable="true">岗位</th>
                        <th field="jobNum" width="100" align="center" sortable="true">工号</th>
                        <th field="empName" width="100" align="center" sortable="true">姓名</th>
                        <th field="sex" width="100" align="center" formatter="globalFormat" sortable="true">性别</th>
                        <th field="duty" width="100" align="center" sortable="true">职务</th>
                        <th field="skill" width="100" align="center" sortable="true">业务技能</th>
                        <th field="aircraftTerminalCode" width="100" align="center" sortable="true">航站楼</th>
                        <th field="dutyGroup" width="100" align="center" sortable="true">班组</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div data-options="region:'south',split:true,border:true" style="height:180px;">
                <form action="${ctx}/rms/rmsEmp/save" id="modifyForm">
                    <input type="hidden" name="id">

                    <input type="hidden" id="postId" name="postId">
                    <input type="hidden" id="deptId" name="deptId">

                    <table width="100%" class="formtable">
                        <tr>
                            <td>姓名：</td>
                            <td>
                                <input type="text" name="empName" class="easyui-textbox easyui-validatebox"
                                       data-options=""
                                >
                            </td>
                            <td>部门：</td>
                            <td>
                                <select class="easyui-combotree easyui-validatebox" id="dept" name="dept"
                                        style="width:170px;"
                                        data-options="">
                                </select>
                            </td>
                            <td>岗位：</td>
                            <td>
                                <select class="easyui-combotree easyui-validatebox" id="post" name="post"
                                        style="width:170px;"
                                        data-options="">
                                </select>
                            </td>
                            <td>航站楼：</td>
                            <td>
                                <select class="easyui-validatebox" id="aircraftTerminalCode" name="aircraftTerminalCode"
                                        style="width:170px;"
                                        data-options="">
                                </select>
                            </td>

                        </tr>
                        <tr>
                            <td>性别：</td>
                            <td>
                                <input type="radio" name="sex" value="1" checked>&nbsp;男
                                <input type="radio" name="sex" value="0">&nbsp;女
                            </td>
                            <td>职务：</td>
                            <td>
                                <input type="text" name="duty" class="easyui-textbox easyui-validatebox"
                                       data-options=""
                                >
                            </td>
                            <td>业务技能：</td>
                            <td>
                                <input type="text" name="skill" class="easyui-textbox easyui-validatebox"
                                       data-options=""
                                >
                            </td>
                            <td>班组：</td>
                            <td>
                                <input type="text" name="dutyGroup" class="easyui-textbox easyui-validatebox"
                                       data-options=""
                                >
                            </td>
                        </tr>
                        <tr>
                            <td>工号：</td>
                            <td colspan="7">
                                <input type="text" name="jobNum" class="easyui-textbox easyui-validatebox"
                                       data-options=""
                                >
                            </td>
                        </tr>
                    </table>
                    <div style="text-align: center; margin-top:15px; margin-right:15px;">
                        <a class="easyui-linkbutton" id="btnSave" data-options="iconCls:'icon-ok'"
                           style="width:80px">保存</a>
                    </div>
                </form>

            </div>
        </div>
    </div>

</div>

<script>
    var moduleContext = "rmsEmp";
    // 拿到字典Map, key = tableName, value = columnName
    var dict = dictMap([{key: "", value: "sex_0_1"}]),
    // 构建字典与列字段的映射关系
    // key = tableName + columnName | value = 当前表格中需要转换的fieldCode
            mapping = {
                "@sex_0_1": ["sex"]
            };
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:rmsEmp:edit">flagDgEdit = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsEmp:insert">flagDgInsert = true;
    </shiro:hasPermission>
    <shiro:hasPermission name="rms:rmsEmp:del">flagDgDel = true;
    </shiro:hasPermission>


    $(function () {

        $('#tree').tree({
            url: ctx + '/sys/office/treeNode?type=2',
            method: 'get',
            onClick: function (node) {
                if (node.attributes.office.type == '4') {
                    // 类型为4的都是岗位,岗位点击后出现岗位下的所有员工
                    // 员工只能在岗位下添加
                    selectedId = node.id;
                    datagrid.datagrid("reload", {"postId": selectedId});
                }
            }
        });


        //拿到部门数据
        $("#dept").combotree({
            panelHeight: 'auto', panelMaxHeight: '200',
            url: ctx + '/sys/office/treeNode?type=2',
            required: true,
            onSelect: function (n) {
                $("#deptId").val(n.id);
                $("#modifyForm input[name=dept]").val(n.text);

                //拿到岗位数据
                $("#post").combotree({
                    panelHeight: 'auto',
                    panelMaxHeight: '200',
                    url: ctx + '/sys/office/treeNode?type=2&topId='+ n.id,
                    required: true,
                    //选择树节点触发事件
                    onSelect: function (node) {
                        $("#postId").val(node.id);
                        $("#modifyForm input[name=post]").val(node.text);
                        //返回树对象
                        var tree = $(this).tree;
                        //选中的节点是否为叶子节点,如果不是叶子节点,清除选中
                        var isLeaf = tree('isLeaf', node.target);
                        if (!isLeaf) {
                            //清除选中
                            $('#postId').combotree('clear');
                        }
                    }
                });
            }
        });
        //拿到岗位数据
        $("#post").combotree({
            panelHeight: 'auto', panelMaxHeight: '200',
            url: ctx + '/sys/office/treeNode?type=2',
            required: true,
            //选择树节点触发事件
            onSelect: function (node) {
                $("#postId").val(node.id);
                $("#modifyForm input[name=post]").val(node.text);
                //返回树对象
                var tree = $(this).tree;
                //选中的节点是否为叶子节点,如果不是叶子节点,清除选中
                var isLeaf = tree('isLeaf', node.target);
                if (!isLeaf) {
                    //清除选中
                    $('#post').combotree('clear');
                }

                $("#dept").combotree('setValues',node.pid);
            }
        });

        //拿到航站楼数据
        $("#aircraftTerminalCode").combobox({
            panelHeight: 'auto', panelMaxHeight: '200',
            url: ctx + '/rms/airportTerminal/jsonData',
            valueField: 'terminalName',
            textField: 'terminalName'
        });
    });

</script>

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>

</body>
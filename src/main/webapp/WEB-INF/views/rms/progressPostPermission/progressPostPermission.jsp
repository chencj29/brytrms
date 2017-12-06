<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>保障过程权限</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table id="datagrid" class="easyui-datagrid" title="保障过程列表">
            <thead>
            <tr>
                <th field="id" hidden="true">主键</th>
                <th field="progressId" hidden="true">过程ID</th>
                <th field="privilege" formatter="yesNoFmt" width="100" align="center" data-options="editor: { type: 'checkbox', options: {on: '1', off: '0'}}">是否可编辑</th>
                <th field="progressCode" width="200" align="center">保障过程编码</th>
                <th field="progressName" width="250" align="center">保障过程名称</th>
                <th field="progressAttr" width="200" align="center">保障过程属性</th>
            </tr>
            </thead>
        </table>
    </div>

    <div data-options="region:'west'" style="width:200px;">
        <ul id="tree" class="easyui-tree"></ul>
    </div>
</div>
<script src="${ctxAssets}/scripts/metadata-dev/garims.resource.dist.js"></script>
<script src="${ctxAssets}/scripts/easyui144/extensions/datagrid-cell-editing/datagrid-cellediting.js"></script>
<script>
    var moduleContext = "progressPostPermission", datagrid, tree, editingIndex;

    $(function () {
        datagrid = $("#datagrid").datagrid({
            rownumbers: true, method: 'get', fitColumns: false, idField: "id", singleSelect: true, showFooter: true, remoteSort: false, multiSort: false,
            rowStyler: function (index, row) {
                return row.privilege == '1' ? 'color:green' : 'color: red';
            }, onBeginEdit: function(index, row) {
                editingIndex = index;
            }, toolbar: [{
                text: '全部选中', iconCls: 'icon-ok', handler: function () {
                    if (tree.tree('getSelected').attributes.type !== '4' || datagrid.datagrid('getData').total === 0) return;
                    var total = datagrid.datagrid('getData').total;
                    for (var i = 0; i < total; i++) datagrid.datagrid('updateRow', {index: i, row: {privilege: '1'}});
                }
            }, {
                text: '全部取消', iconCls: 'icon-remove', handler: function () {
                    if (tree.tree('getSelected').attributes.type !== '4' || datagrid.datagrid('getData').total === 0) return;
                    var total = datagrid.datagrid('getData').total;
                    for (var i = 0; i < total; i++) datagrid.datagrid('updateRow', {index: i, row: {privilege: '0'}});
                }
            }, {
                text: '保存', iconCls: 'icon-save', handler: function () {
                    if (tree.tree('getSelected').attributes.type !== '4' || datagrid.datagrid('getData').total === 0) return;
                    if(editingIndex) datagrid.datagrid('endEdit', editingIndex);

                    $.ajax({
                        url: ctx + '/rms/' + moduleContext + '/savePrivileges', type: 'post',
                        data: JSON.stringify({
                            privileges: datagrid.datagrid('getData').rows.filter(entity => entity.privilege == '1'),
                            postId: tree.tree('getSelected').id
                        }), contentType: 'application/json', dataType: 'json', success: function(data) {
                            if(data.code) {
                                $.messager.slide('保存成功!')
                                datagrid.datagrid('loadData', $utils.ajaxLoadFragments(ctx + '/rms/' + moduleContext + '/queryPrivileges?postId=' + tree.tree('getSelected').id));
                            } else $.messager.slide('保存失败: ' + data.message);
                        }
                    });
                }
            }]
        }).datagrid('enableFilter').datagrid('enableCellEditing');


        $.post(ctx + '/sys/office/treeData', {}, function (data) {
            tree = $("#tree").tree({
                animate: true, data: [convertZTree2EasyUITree(data)],
                onSelect: function (node) {
                    if (node.attributes.type !== '4') {
                        datagrid.datagrid('loadData', []); return;
                    }

                    $.post(ctx + '/rms/' + moduleContext + '/queryPrivileges', {postId: node.id}, function (data) {
                        if (data) datagrid.datagrid('loadData', data);
                    });
                }
            });
        });
    });

    function yesNoFmt(v, r, i, f) {
        return v == '1' ? '是' : '否';
    }
</script>
</body>
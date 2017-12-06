<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>配载工作人员值班记录</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="配载工作人员值班记录" id="datagrid" sortName="dutyDate" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
				<th field="dutyDate" width="100" align="center" formatter="dateFmt" sortable="true">值班日期</th>
				<th field="dutyDept" width="100" align="center" sortable="true">所在部门</th>
				<th field="postName" width="100" align="center" sortable="true">岗位</th>
				<%--<th field="postId" width="100" align="center" sortable="true">岗位ID</th>--%>
				<th field="dutyPersonal" width="100" align="center" formatter="toName" sortable="true">值班人员</th>
				<th field="dutyStartTime" width="100" align="center" sortable="true">值班开始时间</th>
				<th field="dutyEndTime" width="100" align="center" sortable="true">值班结束时间</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/rms/dutyHistory/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>值班日期：</td>
                    <td>
						<input name="dutyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
							value="<fmt:formatDate value="${dutyHistory.dutyDate}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</td>
                    <td>所在部门：</td>
                    <td>
						<select class="easyui-validatebox" id="dutyDept" name="dutyDept" style="width:171px;"
								data-options="">
						</select>
					</td>
                    <td>岗位：</td>
                    <td>
                        <input type="hidden" name="postName">
                        <select class="easyui-combotree easyui-validatebox" id="postId" name="postId"
                                style="width:171px;"
                                data-options="">
                        </select>
						<%--<input type="text" name="postName" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
					</td>
                    <%--<td>岗位ID：</td>
                    <td>
						<input type="text" name="postId" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>--%>
                </tr>
                <tr>
                    <td>值班人员：</td>
                    <td>
						<%--<input type="text" name="dutyPersonal" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>--%>
                        <select class="easyui-combotree easyui-validatebox" id="dutyPersonal" name="dutyPersonal"
                                style="width:171px;"
                                data-options="">
                        </select>
					</td>
                    <td>值班开始时间：</td>
                    <td>
						<input name="dutyStartTime" id="dutyStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${dutyHistory.dutyStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,maxDate:'#F{$dp.$D(\'dutyEndTime\')}'});"/>
					</td>
                    <td>值班结束时间：</td>
                    <td>
						<input name="dutyEndTime" id="dutyEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${dutyHistory.dutyEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'dutyStartTime\')}'});"/>
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
    var moduleContext = "dutyHistory";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="rms:dutyHistory:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:dutyHistory:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="rms:dutyHistory:del">flagDgDel=true;</shiro:hasPermission>

    var rmsEmpDatas;
    $(function () {
        if(!rmsEmpDatas){
            $.ajax({
                url : ctx + '/rms/rmsEmp/jsonData',
                async : false,
                success : function(d){
                    rmsEmpDatas= d;
                }
            });
        }

         //拿到部门数据
         $("#dutyDept").combotree({
             panelHeight: 'auto',panelMaxHeight: '200',
             url: ctx + '/sys/office/treeNode?type=2',
             required: true,
             onChange:function(){
                 $("#modifyForm input[name=dutyDept]").val($(this).combotree('getText'));
             },
             onSelect:function(n){
                 //拿到岗位数据
                 $("#postId").combotree({
                     panelHeight: 'auto',
                     panelMaxHeight: '200',
                     url: ctx + '/sys/office/treeNode?type=2&topId='+ n.id,
                     required: true,
                     //选择树节点触发事件
                     onSelect: function (node) {
                         $("#modifyForm input[name=postName]").val(node.text);
                         //返回树对象
                         var tree = $(this).tree;
                         //选中的节点是否为叶子节点,如果不是叶子节点,清除选中
                         var isLeaf = tree('isLeaf', node.target);
                         if (!isLeaf) {
                             //清除选中
                             $('#postId').combotree('clear');
                         }
                         //$("#dutyPersonal").combobox('reload', ctx + '/rms/rmsEmp/jsonData?postId=' + node.id);
                         $("#dutyPersonal").combobox({
                             panelHeight: 'auto',
                             panelMaxHeight: '200',
                             data:_.filter(rmsEmpDatas, function(d) {return d.postId == node.id}),
                             /*url: ctx + '/rms/rmsEmp/jsonData?postId='+node.id,*/
                             valueField: 'id',
                             textField: 'empName'
                         });
                     }
                 });
             }

         });

        loadPost();

         $("#dutyPersonal").combobox({
             panelHeight: 'auto',
             panelMaxHeight: '200',
             url: ctx + '/rms/rmsEmp/jsonData',
             valueField: 'id',
             textField: 'empName'
         });

        clearCallFn = function () {
            $("#dutyDept").combotree('clear');
            loadPost();
        }
    });

    function toName(value, row, index, field){
        if(!rmsEmpDatas){
            $.ajax({
                url : ctx + '/rms/rmsEmp/jsonData',
                async : false,
                success : function(d){
                    rmsEmpDatas= d;
                }
            });
        }
        var o = _.find(rmsEmpDatas,function(t){
            if(t.id==value) return t;
        });

        if(o!=null) return o.empName;
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
    function dateFmt(v, r, i, f) {
        return !v ? "" : moment(v).format("YYYY-MM-DD");
    }

    function loadPost() {
        //拿到岗位数据
        $("#postId").combotree({
            panelHeight: 'auto',
            panelMaxHeight: '200',
            url: ctx + '/sys/office/treeNode?type=2',
            required: true,
            //选择树节点触发事件
            onSelect: function (node) {
                $("#modifyForm input[name=postName]").val(node.text);
                //返回树对象
                var tree = $(this).tree;
                //选中的节点是否为叶子节点,如果不是叶子节点,清除选中
                var isLeaf = tree('isLeaf', node.target);
                if (!isLeaf) {
                    //清除选中
                    $('#postId').combotree('clear');
                }
                $("#dutyDept").combotree('setValues',node.pid);
                //$('#dutyPersonal').combobox('clear');
                //("#dutyPersonal").combobox('reload', ctx + '/rms/rmsEmp/jsonData?postId=' + node.id);
                $("#dutyPersonal").combobox({
                    panelHeight: 'auto',
                    panelMaxHeight: '200',
                    data:_.filter(rmsEmpDatas, function(d) {return d.postId == node.id}),
                    /*url: ctx + '/rms/rmsEmp/jsonData?postId='+node.id,*/
                    valueField: 'id',
                    textField: 'empName'
                });
            }
        });
    }
</script>
<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>
</body>
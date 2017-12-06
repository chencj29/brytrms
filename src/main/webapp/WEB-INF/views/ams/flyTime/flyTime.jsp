<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>飞越时间</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table class="easyui-datagrid" title="飞越时间" id="datagrid" sortName="startSite" sortOrder="asc">
            <thead>
            <tr>
			   	<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>
                <th field="startSiteCode" width="100" align="center" sortable="true">起飞地3字码</th>
                <th field="startSite" width="100" align="center" sortable="true">起飞地</th>
                <th field="endSiteCode" width="100" align="center" sortable="true">到达地3字码</th>
                <th field="endSite" width="100" align="center" sortable="true">到达地</th>
				<th field="flyTime" width="100" align="center" sortable="true">飞越时间</th>
            </tr>
            </thead>
        </table>
    </div>

	<div data-options="region:'south'" style="height:180px;">
        <form action="${ctx}/ams/flyTime/save" id="modifyForm">
            <input type="hidden" name="id">
            <table width="100%" class="formtable">
                <tr>
                    <td>起飞地3字码：</td>
                    <td>
                        <%--<input type="text" name="startSiteCode" class="easyui-textbox easyui-validatebox"
                               data-options=""
                        >--%>
                        <select class="easyui-validatebox" id="startSiteCode" name="startSiteCode" style="width:170px;"
                                data-options="">
                        </select>
                    </td>
                    <td>起飞地：</td>
                    <td>
						<input type="text" name="startSite" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>

					</td>
                    <td>到达地3字码：</td>
                    <td>
                        <%--<input type="text" name="endSiteCode" class="easyui-textbox easyui-validatebox"
                               data-options=""
                        >--%>
                        <select class="easyui-validatebox" id="endSiteCode" name="endSiteCode" style="width:170px;"
                                data-options="">
                        </select>
                    </td>
                    <td>到达地：</td>
                    <td>
						<input type="text" name="endSite" class="easyui-textbox easyui-validatebox"
							 data-options=""
						>
					</td>
                </tr>
                <tr>
                    <td>飞越时间：</td>
                    <td>
						<input type="text" name="flyTime" class="easyui-textbox easyui-numberspinner"
							 data-options="min:0,max:1000,value:0,editable:true"
						>分钟
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
    var moduleContext = "flyTime";
    /**
     * 过滤列声明接口(必须，可空)
     */
    var specialFilteringColumnDefinition = [];
    <shiro:hasPermission name="ams:flyTime:edit">flagDgEdit=true;</shiro:hasPermission>
    <shiro:hasPermission name="ams:flyTime:insert">flagDgInsert=true;</shiro:hasPermission>
    <shiro:hasPermission name="ams:flyTime:del">flagDgDel=true;</shiro:hasPermission>

    var addressDatas;
     $(function () {
         if(!addressDatas){
             $.ajax({
                 url:ctx + '/ams/amsAddress/jsonData',
                 dataType: "json",
                 async: false,
                 success: function (data) {
                     addressDatas = _.map(data,function(o){
                         var result = {};
                         result.threeCode = o.threeCode;
                         result.chName = o.chName;
                         result.tCodechName = o.threeCode+o.chName;
                         return result;
                     });
                 }
             });
         }
         $("#startSiteCode").combobox({
             panelHeight: 'auto',panelMaxHeight: '200',
             data: addressDatas,
             valueField: 'threeCode',
             textField: 'tCodechName',
             onSelect : function(rec){ //改变单选值时，通过选中值后回填内容
                 form.form('load',{startSite:rec.chName});
             },
             filter: function (q, row) {
                 //定义当'mode'设置为'local'时如何过滤本地数据，函数有2个参数：
                 //q：用户输入的文本。
                 //row：列表行数据。
                 //返回true的时候允许行显示。
                 //统一转换成小写进行比较；==0：匹配首位，>=0：匹配任意位置；
                 if(!q) return;
                 return row[$(this).combobox('options').textField].toLowerCase().indexOf(q.toLowerCase()) >= 0;
             },
             onHidePanel: onComboboxHidePanel
         });

         $("#endSiteCode").combobox({
             panelHeight: 'auto',panelMaxHeight: '200',
             data: addressDatas,
             valueField: 'threeCode',
             textField: 'tCodechName',
             onSelect : function(rec){ //改变单选值时，通过选中值后回填内容
                 form.form('load',{endSite:rec.chName});
             }
         });

    });

    /**
     * easyui combobox 开启搜索功能,自动装载选中的项目处理函数
     */
    function onComboboxHidePanel() {
        var el = $(this);
        el.combobox('textbox').focus();
        // 检查录入内容是否在数据里
        var opts = el.combobox("options");
        var data = el.combobox("getData");
        var value = el.combobox("getValue");
        // 有高亮选中的项目, 则不进一步处理
        var panel = el.combobox("panel");
        var items = panel.find(".combobox-item-selected");
        if (items.length > 0) {
            var values = el.combobox("getValues");
            el.combobox("setValues", values);
            return;
        }
        var allowInput = opts.allowInput;
        if (allowInput) {
            var idx = data.length;

            data[idx] = [];
            data[idx][opts.textField] = value;
            data[idx][opts.valueField] = value;
            el.combobox("loadData", data);
        } else {
            // 不允许录入任意项, 则清空
            el.combobox("clear");
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

<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGridAms.js"></script>
</body>
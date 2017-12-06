<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <title>配载人员工作量统计</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div region="center" style="padding:1px;">
        <table width="100%" id="${statisticsName}List" toolbar="#${statisticsName}Listtb" data-options="fitColumns:true">
            <thead>
            <tr>
                <%--<th field="id" width="100" hidden="true" sortable="false" order="false">主键</th>--%>
                <th field="dutyPersonal" formatter="toName" width="100"  sortable="true">值班人员</th>
                <%--<th field="dutyDate" width="100"  sortable="true">值班日期</th>--%>
                <th field="dutyDept" width="100"  sortable="true">所在部门</th>
                <th field="postName" width="100"  sortable="true">岗位</th>
                <%--<th field="postId" width="100"  sortable="true">岗位ID</th>--%>
                <th field="dutyStartTime" width="100"  sortable="true">值班开始时间</th>
                <th field="dutyEndTime" width="100"  sortable="true">值班结束时间</th>
            </tr>
            </thead>
        </table>
        <div id="${statisticsName}Listtb" style="padding:3px; height: auto">
            <div name="searchColums" style="display:inline">
                <input id="_sqlbuilder" name="sqlbuilder" type="hidden"/>
                 <span style="display:-moz-inline-box;display:inline-block;">
                    <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " title="岗位">岗位：</span>
                    <%--<input class="easyui-combobox" type="text" id="dutyDept" name="dutyDept">--%>
                    <%--<span style="text-align:right;" title="值班人员">岗位：</span>--%>
                     <input class="easyui-combobox" type="text" id="postId" name="post" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                    <span style="text-align:right;" title="值班人员">值班人员：</span>
                    <input class="easyui-combobox" type="text" id="empId" name="emp" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                    <span style="text-align:right;" title="开始日期">开始日期：</span>
                     <input class="inuptxt" id="dutyStartTime" name="dutyStartTime" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                     <span style="text-align:right;" title="结束日期">结束日期：</span>
                    <input class="inuptxt" id="dutyEndTime" name="dutyEndTime" onkeypress="EnterPress(event)" onkeydown="EnterPress()">
                 </span>
            </div>
            <div style="height:30px;display:inline" class="datagrid-toolbar">
                <span style="float:right">
                    <a href="#" class="easyui-linkbutton" iconcls="icon-search" id="search" onclick="listsearch()">查询</a>
                    <a href="#" class="easyui-linkbutton" iconcls="icon-reload" onclick="searchReset('${statisticsName}List')">重置</a>
                    <%--<a href="#" class="easyui-linkbutton" iconcls="icon-20130406125519344_easyicon_net_16" onclick="downloadExcel()">下载</a>--%>
                </span>
            </div>
        </div>
    </div>
</div>

<script src="${ctxAssets}/scripts/moment/min/moment.min.js"></script>
<script>
    var rmsEmpDatas;
    $(function() {
        //给时间控件加上样式
        $("#${statisticsName}Listtb").find("input[name='dutyStartTime']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {WdatePicker({ dateFmt: 'yyyy-MM-dd' });});
        $("#${statisticsName}Listtb").find("input[name='dutyEndTime']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {WdatePicker({ dateFmt: 'yyyy-MM-dd' });});

        if(!rmsEmpDatas){
            $.ajax({
                url : ctx + '/rms/rmsEmp/jsonData',
                async : false,
                success : function(d){
                    rmsEmpDatas= d;
                }
            });
        }

        /*$("#dutyDept").combotree({
            url: ctx + '/sys/office/treeNode?type=2',
            required: true,
            //选择树节点触发事件
            onSelect: function(node) {
                $("#postId").combotree('clear');
                $("#postId").combotree('reload', ctx + '/rms/rmsEmp/jsonData?postId=' + node.id);
            }
        });*/
        //拿到岗位数据
        $("#postId").combotree({
            url: ctx + '/sys/office/treeNode?type=2',
            required: true,
            //选择树节点触发事件
            onSelect: function(node) {
                //返回树对象
                var tree = $(this).tree;
                //选中的节点是否为叶子节点,如果不是叶子节点,清除选中
                var isLeaf = tree('isLeaf', node.target);
                if(!isLeaf) {
                    //清除选中
                    $('#postId').combotree('clear');
                }

                $("#empId").combobox('clear');
                //$("#empId").combobox('reload', ctx + '/rms/rmsEmp/jsonData?postId=' + node.id);
                $("#empId").combobox({
                    panelHeight: 'auto',
                    panelMaxHeight: '200',
                    data:_.filter(rmsEmpDatas, function(d) {return d.postId == node.id}),
                    /*url: ctx + '/rms/rmsEmp/jsonData?postId='+node.id,*/
                    valueField: 'id',
                    textField: 'empName'
                });
            }
        });

        //
        $("#empId").combobox({
            panelHeight: 'auto',
            panelMaxHeight: '200',
            /*url: ctx + '/rms/rmsEmp/jsonData',*/
            data:rmsEmpDatas,
            valueField: 'id',
            textField: 'empName'
        });

        //
        $('#${statisticsName}List').datagrid({
            url: ctx + '/rms/dutyHistory/listStatistics',
            showFooter : true,
            onLoadSuccess : function(){
                <%--$("#${statisticsName}List").datagrid('appendRow', {dutyDept:"<b>总计：</b>",dutyStartTime:"<b>工作时间：${dockTotal}小时</b>"});--%>

                <%--
                var rows = $("#${statisticsName}List").datagrid('getRows')//获取当前页的数据行
                var ptotal = 0//计算列的总和
                var dgrowset = {};//添加统计行的数据
                dgrowset[dgtotal]='<b>总计：</b>';

                if (numargs == 3){
                    for (var i = 0; i < rows.length; i++) {
                        ptotal += parseFloat(rows[i][sumcolumn]);
                    }
                    dgrowset[sumcolumn]=ptotal;
                }else{
                    for (var i = 0; i < rows.length; i++) {
                        for(var j=0;j<numargs-2;j++){
                            totals[j] += parseFloat(rows[i][arg[j]]);
                        }
                    }
                    for(var j=0;j<numargs-2;j++){
                        dgrowset[arg[j]]=totals[j]
                    }
                }
                $("#${statisticsName}List").datagrid('appendRow', dgrowset);
                --%>
            }
        })

    });

    function listsearch() {
        var postId = $('#postId').combobox('getValue');
        //var dutyPersonal = $('#empId').combobox('getText');
        var dutyPersonal = $('#empId').combobox('getValue');
//        var dutyStartTime = $('#dutyStartTime').datebox('getValue');
//        var dutyEndTime = $('#dutyEndTime').datebox('getValue');
        var dutyStartTime = $('#dutyStartTime').val();
        var dutyEndTime = $('#dutyEndTime').val();

        /*var o = _.find(rmsEmpDatas,function(t){
            if(t.empName==dutyPersonal) return t;
        });*/
       // debugger
        $('#${statisticsName}List').datagrid('load', {
            postId: postId,
            dutyPersonal:dutyPersonal,
            dutyStartTime: dutyStartTime,
            dutyEndTime: dutyEndTime
        });
    }

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

        if(value=="<b>总计:</b>") return "<b>总计:</b>";
        if(o!=null) return o.empName;
    }

    function searchReset(name) {
        $("#" + name + "tb").find(":input").val("");
        listsearch()
    }

    function downloadExcel(){
        location.href= ctx + '/rms/statistics/${statisticsName}ExpExcel';
    }

    function EnterPress (e) {
        var e = e || window.event;
        if (e.keyCode == 13) {
            listsearch();
        }
    }

</script>
<%--<script src="${ctxAssets}/scripts/metadata-dev/garims.EasyUI.DataGrid.js"></script>--%>
</body>
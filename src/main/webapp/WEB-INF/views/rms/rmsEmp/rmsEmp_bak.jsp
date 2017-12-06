<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>工作人员基础信息</title>
    <c:set var="controllerUrl" value="rmsEmp"/>
    <style>
        table, tbody, th, td {
            white-space: nowrap;
        }
    </style>
</head>
<body>
<div id="content" class="col-md-12">
    <!--正文头部开始-->
    <div class="pageheader">
        <h2>基础数据</h2>

        <div class="breadcrumbs">
            <ol class="breadcrumb">
                <li><a href="###">资源管理系统</a></li>
                <li><a href="###">基础数据</a></li>
                <li class="active">工作人员基础信息</li>
            </ol>
        </div>
    </div>
    <!--正文头部结束-->

    <!--正文内容开始-->
    <div class="main">
        <div class="row">
            <div class="col-md-12">
                <section class="tile transparent">
                    <div class="tile-header transparent">
                        <h1>工作人员基础信息</h1>
                        <div class="controls">
                            <a href="javascript:void(0);" id="refresh_datatbles" class="refresh"><i class="fa fa-refresh"></i></a>
                        </div>
                    </div>
                    <div class="tile-body color transparent-white rounded-corners">
                        <div class="table-responsive">
                            <table class="table display nowrap table-custom table-datatable table-hover" id="modifyDataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>部门</th>
                                    <th>岗位</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>职务</th>
                                    <th>业务技能</th>
                                    <th>航站楼</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
    <!--正文内容结束-->
</div>


<script type="text/html" id="formTpl">
    <div class="row" style="padding-top: 20px; margin: 0px;">
        <div class="col-md-12">
            <form class="form-horizontal" role="form" name="saveModifyForm" method="post" action="${ctx}/rms/${controllerUrl}/save">
                <input type="hidden" name="id" value="{{employee.id}}">
                <input type="hidden" name="dept" value="{{employee.dept}}"><input type="hidden" name="post" value="{{employee.post}}">
                <input type="hidden" name="deptId" value="{{employee.deptId}}"><input type="hidden" name="postId" value="{{employee.postId}}">
                <div class="form-group">
                    <label for="dept" class="col-sm-3 control-label">部门</label>
                    <div class="col-sm-9">
                        <select id="dept" class="form-control" data-validation-engine="validate[required]" data-errormessage-value-missing="请选择部门">
                            <option value=""></option>
                            {{ each companyAndDepts as dept }}
                            <optgroup label="{{dept.NAME}}">
                                {{ each dept.children as child }}
                                <option value="{{child.ID}}" {{ if employee.deptId=== child.ID }} selected {{ /if }}>{{child.NAME}}</option>
                                {{ /each }}
                            </optgroup>
                            {{ /each }}
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="post" class="col-sm-3 control-label">岗位</label>
                    <div class="col-sm-9">
                        <select id="post" class="form-control" data-validation-engine="validate[required]" data-errormessage-value-missing="请选择岗位">
                            <option value=""></option>
                            {{ each postData as post }}
                            <option value="{{post.ID}}" {{ if employee.postId=== post.ID }} selected {{ /if }} >{{post.NAME}}</option>
                            {{ /each }}
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="empName" class="col-sm-3 control-label">姓名</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="empName" name="empName" placeholder="请输入姓名" value="{{employee.empName}}"
                               data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]">
                    </div>
                </div>
                <div class="form-group">
                    <label for="sex" class="col-sm-3 control-label">性别</label>
                    <div class="controls col-sm-9">
                        <label class="checkbox-inline">
                            <input type="radio" value="0" checked="checked" id="sex" name="sex" {{ if employee.sex== 0 }} checked {{ /if }}>
                            男
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" value="1" name="sex" {{ if employee.sex== 1 }} checked {{ /if }}>
                            女
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="duty" class="col-sm-3 control-label">职务</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="duty" name="duty" placeholder="请输入职务" value="{{employee.duty}}"
                               data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]">
                    </div>
                </div>
                <div class="form-group">
                    <label for="skill" class="col-sm-3 control-label">业务技能</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="skill" name="skill" placeholder="请输入业务技能" value="{{employee.skill}}"
                               data-validation-engine="validate[required,custom[normal],minSize[2],maxSize[10]]">
                    </div>
                </div>
                <div class="form-group">
                    <label for="aircraftTerminalCode" class="col-sm-3 control-label">航站楼</label>
                    <div class="col-sm-9">
                        <select id="aircraftTerminalCode" name="aircraftTerminalCode" class="form-control"
                                data-validation-engine="validate[required]" data-errormessage-value-missing="请选择航站楼">
                            <option value=""></option>
                            {{ each terminals as value }}
                            <option value="{{value}}" {{ if value=== employee.aircraftTerminalCode}} selected {{ /if }}>{{value}}</option>
                            {{ /each }}
                        </select>
                    </div>
                </div>
            </form>
        </div>
    </div>
</script>

<script>
    var dictSex = new sysDictUtils("rms_emp", "sex"), employeeDataTable, modifyFormHtml,
            modelName = "工作人员信息", width = "400px", columns = [
                {"data": "dept"}, {"data": "post"}, {"data": "empName"}, {"data": "sex"},
                {"data": "duty"}, {"data": "skill"}, {"data": "aircraftTerminalCode"}
            ];

    $(function () {
        // init datatables
        employeeDataTable = $("#modifyDataTable").DataTable({
            select: 'single', scrollX: true,
            buttons: [{
                text: '添加' + modelName,
                action: function (evt, dt, node, config) {
                    saveOrUpdateForMain();
                }
            }],
            columnDefs: [{
                targets: columns.length,
                render: function (data, type, row, dt) {
                    return "<a href='javascript:void(0);' data-row-index='" + dt.row + "' name='modifyBtn'>编辑</a> &nbsp;&nbsp;"
                            + "<a href='javascript:void(0);' data-row-index='" + dt.row + "' name='deleteBtn'>删除</a>";
                }
            }, {
                "targets": 3,
                "render": function (data, type, row) {
                    return dictSex.getLabel(data);
                }
            }],
            "processing": true,
            "serverSide": true,
            "ajax": "list",
            "columns": columns
        });


        // modify & delete
        $('body').on('click', 'a[name=modifyBtn]', function (e) {
            saveOrUpdateForMain(employeeDataTable.data()[$(this).data()['rowIndex']]);
        }).on('click', 'a[name=deleteBtn]', function (e) {
            var data = employeeDataTable.data()[$(this).data()['rowIndex']];
            layer.confirm("确定删除该条数据?", {
                btn: ["确定", "取消"]
            }, function () {
                $.post("delete", {"id": data.id}, function (resp) {
                    if (resp && resp.code == 1) {
                        layer.msg("删除成功");
                        employeeDataTable.ajax.reload();
                    } else {
                        layer.alert(resp.message);
                    }
                })
            });
        });

        //refresh
        $("#refresh_datatbles").click(function () {
            employeeDataTable.ajax.reload();
        });
    })
    ;

    function saveOrUpdateForMain(data) {
        // 找到公司及部门数据
        var companyAndDeptArray = $utils.ajaxLoadFragments('${ctx}/sys/office/companyAndDeptData'),
                comp = _.filter(companyAndDeptArray, function (item) { // 公司数据
                    return item["PARENT_ID"] === "0";
                }), dept = _.filter(companyAndDeptArray, function (item) { // 部门数据
                    return item["PARENT_ID"] !== "0";
                }), employeeData = {}, postData = [];

        if (data) {
            employeeData = $utils.ajaxLoadFragments('${ctx}/rms/rmsEmp/get?id=' + data.id);
            postData = $utils.ajaxLoadFragments('${ctx}/sys/office/postData?deptId=' + employeeData.deptId);
        }

        var templateHtml = template('formTpl')({
            // 航站楼数据
            terminals: _.pluck($utils.ajaxLoadFragments('${ctx}/rms/airportTerminal/jsonData'), 'terminalName'),
            // 部门数据（带公司）
            companyAndDepts: _.each(comp, function (opt) {
                opt.children = _.filter(dept, function (item) {
                    return item["PARENT_ID"] === opt["ID"];
                });
            }), employee: employeeData, postData: postData
        });


        var title = "新增";
        if (data && $(data).length > 0) title = "修改";
        title = title + modelName;
        var index = layer.open({
            title: title, type: 1, closeBtn: 1, shift: 2, area: width,
            shadeClose: false, content: templateHtml,
            btn: ['保存', '取消'], success: function () {
                $("form[name=saveModifyForm]").validationEngine('attach');
                $("#aircraftTerminalCode").select2({
                    language: "zh-CN", allowClear: true, placeholder: "请选择航站楼"
                });

                $("#post").select2({
                    language: "zh-CN", allowClear: true, placeholder: "请选择岗位",
                }).on("select2:select", function (e) {
                    $("input[name=postId]").val(e.params.data.id);
                    $("input[name=post]").val(e.params.data.text);
                });

                $("#dept").select2({
                    language: "zh-CN", allowClear: true, placeholder: "请选择部门"
                }).on("select2:select", function (e) {
                    $("input[name=deptId]").val(e.params.data.id);
                    $("input[name=dept]").val(e.params.data.text);
                    postSelectCascade(e.params.data.id);
                });
            },
            yes: function () {
                var form = $("form[name=saveModifyForm]");
                if (form.validationEngine("validate")) {
                    $.post(form.attr("action"), form.serialize(), function (resp) {
                        if (resp && resp.code == 1) {
                            layer.close(index);
                            employeeDataTable.ajax.reload();
                        } else {
                            layer.alert(resp.message);
                            return false;
                        }
                    });
                }
            }
        });
    }

    function postSelectCascade(deptId) {
        $("#post").select2({
            ajax: {
                url: '${ctx}/sys/office/postData?deptId=' + deptId,
                dataType: 'json', processResults: function (data, params) {
                    var results = [];

                    _.each(data, function (item) {
                        results.push({
                            id: item.ID, text: item.NAME
                        });
                    });

                    return {
                        results: results
                    }
                }
            }
        });
    }


    // 适用于select 冗余例如:powerTypeCode powerTypeName select
    $(function () {
        $("body").on("change", "select.dict", function () {
            setSelectName();
        });
        setSelectName();
    });

    //
    function setSelectName() {
        $("select.dict").each(function () {
            try {
                var text = $(this).find("option:selected").text();
                var code = $(this).attr("name");
                var name = code.substring(0, code.lastIndexOf("Code")) + "Name";
                $("input[name='" + name + "']").val(text);
            } catch (e) {
            }
        });
    }
</script>
</body>
</html>

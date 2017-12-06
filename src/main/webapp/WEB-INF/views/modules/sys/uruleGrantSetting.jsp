<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理 - 规则引擎权限设置</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        html, body {
            height: 100%;
            width: auto;
        }

        .box {
            height: 100%;
            display: flex;
            display: -webkit-flex;
            flex-direction: row;
            -webkit-flex-direction: row;
            -webkit-align-content: stretch;
            align-content: stretch;
        }

        .box .left {
            width: 30%;
            margin-left: 15px;
        }

        .box .right {
            -webkit-flex: 1;
            flex: 1;
            position: relative;
            margin-left: 10px;
        }

        .content-tr {
            height: 26px;
        }

        .content-tr td {
            padding: 1px 5px;
        }

        .content-tr td:hover {
            background-color: #F5F5F5;
            cursor: pointer;
        }

        .active {
            background-color: #F5F5F5;
            font-weight: bold;
        }

        .content-tr td div {
            margin-top: 5px;
            min-height: 26px;
            word-break: break-all;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/sys/user/list">用户列表</a></li>
    <li class="active"><a href="#">权限设置</a></li>
</ul>

<p style="margin-left:15px;">
    <button class="btn btn-primary" type="button" id="btnSave">保存</button>

    <button class="btn btn-default" type="button" id="allSelect">全选</button>
    <button class="btn btn-default" type="button" id="notAllSelect">全否</button>
</p>

<div class="box">
    <div class="left"></div>
    <div class="right">
    </div>
</div>
<form>
    <input type="hidden" id="loginName" value="${user}"/>
</form>
<sys:message content="${message}"/>
<script type="text/html" id="projectList">
    <table class="table table-bordered" id="projects" style="margin: 0px; width: 100%;">
        <thead>
        <tr class="well">
            <td style="width: 200px;"><label>项目名称</label></td>
        </tr>
        </thead>
        <tbody>
        {{ each list as project }}
        <tr class="content-tr">
            <td>
                <div>{{ project }}</div>
            </td>
        </tr>
        {{ /each}}
        </tbody>
    </table>
</script>
<script type="text/html" id="permissionInfo">
    <table class="table table-bordered" id="permissions" style="margin: 0px; width: 60%;">
        <tbody>
        <tr>
            <td width="50%" style="text-align: right;">
                是否允许查看当前项目:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readProject" value="true" {{ if data.readProject }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readProject" value="false" {{ if !data.readProject }} checked {{ /if}}>否
            </label>
            </td>
        </tr>

        {{ if data.readProject }}
        <tr>
            <td width="50%" style="text-align: right;">
                是否允许查看知识包:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readPackage" value="true" {{ if data.readPackage }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readPackage" value="false" {{ if !data.readPackage }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ if data.readPackage }}
        <tr style="background: #f5f5f5;">
            <td width="50%" style="text-align: right;">
                是否允许修改知识包:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writePackage" value="true" {{ if data.writePackage }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writePackage" value="false" {{ if !data.writePackage }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ /if }}
        <tr>
            <td width="50%" style="text-align: right;">
                是否允许查看变量库文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readVariableFile" value="true" {{ if data.readVariableFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readVariableFile" value="false" {{ if !data.readVariableFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ if data.readVariableFile }}
        <tr style="background: #f5f5f5;">
            <td width="50%" style="text-align: right;">
                是否允许修改变量库文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeVariableFile" value="true" {{ if data.writeVariableFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeVariableFile" value="false" {{ if !data.writeVariableFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ /if }}
        <tr>
            <td width="50%" style="text-align: right;">
                是否允许查看参数库文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readParameterFile" value="true" {{ if data.readParameterFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readParameterFile" value="false" {{ if !data.readParameterFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ if data.readParameterFile }}
        <tr style="background: #f5f5f5;">
            <td width="50%" style="text-align: right;">
                是否允许修改参数库文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeParameterFile" value="true" {{ if data.writeParameterFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeParameterFile" value="false" {{ if !data.writeParameterFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ /if }}
        <tr>
            <td width="50%" style="text-align: right;">
                是否允许查看常量库文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readConstantFile" value="true" {{ if data.readConstantFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readConstantFile" value="false" {{ if !data.readConstantFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ if data.readConstantFile }}
        <tr style="background: #f5f5f5;">
            <td width="50%" style="text-align: right;">
                是否允许修改常量库文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeConstantFile" value="true" {{ if data.writeConstantFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeConstantFile" value="false" {{ if !data.writeConstantFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ /if }}
        <tr>
            <td width="50%" style="text-align: right;">
                是否允许查看动作库文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readActionFile" value="true" {{ if data.readActionFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readActionFile" value="false" {{ if !data.readActionFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ if data.readActionFile }}
        <tr style="background: #f5f5f5;">
            <td width="50%" style="text-align: right;">
                是否允许修改动作库文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeActionFile" value="true" {{ if data.writeActionFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeActionFile" value="false" {{ if !data.writeActionFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ /if }}
        <tr>
            <td width="50%" style="text-align: right;">
                是否允许查看规则集文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readRuleFile" value="true" {{ if data.readRuleFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readRuleFile" value="false" {{ if !data.readRuleFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ if data.readRuleFile }}
        <tr style="background: #f5f5f5;">
            <td width="50%" style="text-align: right;">
                是否允许修改规则集文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeRuleFile" value="true" {{ if data.writeRuleFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeRuleFile" value="false" {{ if !data.writeRuleFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ /if }}
        <tr>
            <td width="50%" style="text-align: right;">
                是否允许查看决策表文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readDecisionTableFile" value="true" {{ if data.readDecisionTableFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readDecisionTableFile" value="false" {{ if !data.readDecisionTableFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ if data.readDecisionTableFile }}
        <tr style="background: #f5f5f5;">
            <td width="50%" style="text-align: right;">
                是否允许修改决策表文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeDecisionTableFile" value="true" {{ if data.writeDecisionTableFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeDecisionTableFile" value="false" {{ if !data.writeDecisionTableFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ /if }}
        <tr>
            <td width="50%" style="text-align: right;">
                是否允许查看决策树文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readDecisionTreeFile" value="true" {{ if data.readDecisionTreeFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readDecisionTreeFile" value="false" {{ if !data.readDecisionTreeFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ if data.readDecisionTreeFile }}
        <tr style="background: #f5f5f5;">
            <td width="50%" style="text-align: right;">
                是否允许修改决策树文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeDecisionTreeFile" value="true" {{ if data.writeDecisionTreeFile }} checked {{ /if }}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeDecisionTreeFile" value="false" {{ if !data.writeDecisionTreeFile }} checked {{ /if }}>否
            </label>
            </td>
        </tr>
        {{ /if }}
        <tr>
            <td width="50%" style="text-align: right;">
                是否允许查看评分卡文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readScorecardFile" value="true" {{ if data.readScorecardFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readScorecardFile" value="false" {{ if !data.readScorecardFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ if data.readScorecardFile }}
        <tr style="background: #f5f5f5;">
            <td width="50%" style="text-align: right;">
                是否允许修改评分卡文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeScorecardFile" value="true" {{ if data.writeScorecardFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeScorecardFile" value="false" {{ if !data.writeScorecardFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ /if }}
        <tr>
            <td width="50%" style="text-align: right;">
                是否允许查看决策流文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readFlowFile" value="true" {{ if data.readFlowFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="readFlowFile" value="false" {{ if !data.readFlowFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ if data.readFlowFile }}
        <tr style="background: #f5f5f5;">
            <td width="50%" style="text-align: right;">
                是否允许修改决策流文件:
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeFlowFile" value="true" {{ if data.writeFlowFile }} checked {{ /if}}>是
            </label>
                &nbsp;&nbsp;&nbsp;<label class="checkbox-inline">
                <input type="radio" name="writeFlowFile" value="false" {{ if !data.writeFlowFile }} checked {{ /if}}>否
            </label>
            </td>
        </tr>
        {{ /if }}
        {{ /if }}
        </tbody>
    </table>
</script>
<script src="/assets/scripts/underscore/underscore-min.js"></script>
<script src="/assets/scripts/aui-artTemplate/dist/template.js"></script>
<script src="/assets/scripts/layer/layer.js"></script>
<script>
    var currentUserPermission, currentProject, currentProjectIndex = -1, projectList = [];
    $(function () {
        $(document).on('click', '#projects tbody tr td', {}, function () {
            if ($(this).hasClass('active')) return;
            $("#projects td").removeClass('active');
            $(this).addClass('active');

            currentProject = _.find(currentUserPermission.projectConfigs, (item) => item.project === $(this).text().trim());
            currentProjectIndex = _.findIndex(currentUserPermission.projectConfigs, (item) => item.project === $(this).text().trim());

            $(".right").html(template('permissionInfo')({ data: currentProject }))
        }).on('change', 'input[type=radio]', {}, function () {
            // 赋值到currentUserPermission中
            var key = $(this).attr('name'), value = $(this).val() === "true" ? true : false;

            currentProject[key] = value;

            if (!value) {
                //所有key设置为false
                if (key === 'readProject') {
                    currentProject = _.mapObject(currentProject, (v, k) => (k === 'project') ? v : false)
                    currentUserPermission.projectConfigs[currentProjectIndex] = currentProject
                }
                else currentProject[key.replace('read', 'write')] = value
            }

            reloadPermissionInfo();
        });


        var loginName = $("#loginName").val();
        // 得到规则引擎项目列表
        $.get('/urule/permission/loadResourceSecurityConfigs').then(function (data) {
            currentUserPermission = _.find(data, (item) => item.username === loginName);
            // 得到项目列表
            $(".left").html(template('projectList')({ list: _.pluck(currentUserPermission.projectConfigs, 'project') }));
        });


        $("#btnSave").click(generatePermissionXml);

        $("#allSelect").click(onSelect);

        $("#notAllSelect").click(()=>{
            onSelect(false);
        })
    })

    function onSelect(flag){
        var radioItem = !flag?"false":"true";
        var length = $("#permissions input[value="+radioItem+"]").length;
        _.each($("#permissions input[value="+radioItem+"]"),(k,i)=>$("#permissions input[value="+radioItem+"]").eq(i).click())
        if($("#permissions input[value="+radioItem+"]").length != length) onSelect(flag);
    }

    function reloadPermissionInfo () {
        $(".right").html(template('permissionInfo')({ data: currentProject }))
    }

    function generatePermissionXml () {
        var waitingIndex = layer.open({ title: false, closeBtn: 0, scrollbar: false, move: false, btn: false, content: "操作进行中，请稍候……", icon: 16 })
        // 获取urule权限xml串
        $.get('/urule/permission/loadResourceSecurityConfigs').then(function (data) {

            var xmlArray = [];
            xmlArray.push("<?xml version=\"1.0\" encoding=\"utf-8\"?>"); // 头
            xmlArray.push("<user-permission>"); // 权限根开始

            for (var item of data) {
                if (item.username === $("#loginName").val().trim()) continue;

                xmlArray.push("<user-permission username=\"")
                xmlArray.push(item.username)
                xmlArray.push("\">");

                var permissionDetailArray = [];

                _.each(item.projectConfigs, (project) => {
                    if (project.readProject) {
                        permissionDetailArray.push("<project-config ");
                        _.mapObject(project, (v, k) => {
                            permissionDetailArray.push(k.convertPro2LowerConcatStr())
                            permissionDetailArray.push("=\"")
                            permissionDetailArray.push(v)
                            permissionDetailArray.push("\" ")
                        })

                        permissionDetailArray.push(" />")
                    }
                })

                if (!_.isEmpty(permissionDetailArray)) xmlArray.push(permissionDetailArray.join(""))
                xmlArray.push("</user-permission>")
            }

            xmlArray.push("<user-permission username=\"") // 当前设置的权限开始
            xmlArray.push($("#loginName").val().trim())
            xmlArray.push("\">");

            var cpArray = [];

            for (var cp of currentUserPermission.projectConfigs) {
                if (cp.readProject) {
                    cpArray.push("<project-config ");
                    _.mapObject(cp, (v, k) => {
                        cpArray.push(k.convertPro2LowerConcatStr())
                        cpArray.push("=\"")
                        cpArray.push(v)
                        cpArray.push("\" ")
                    })

                    cpArray.push(" />")
                }
            }

            if (!_.isEmpty(cp)) xmlArray.push(cpArray.join(""))

            xmlArray.push("</user-permission>"); // 当前设置的权限结束

            xmlArray.push("</user-permission>"); // 权限根结束

            $.post('/urule/permission/saveResourceSecurityConfigs', { content: xmlArray.join("") }, function (data, response) {
                if (response === 'success') {
                    layer.close(waitingIndex)
                    layer.alert('权限保存成功！')
                }
            })
        })
    }

    String.prototype.convertPro2LowerConcatStr = function () {
        return this.replace(/([A-Z])/g, "-$1").toLowerCase()
    }
</script>
</body>
</html>
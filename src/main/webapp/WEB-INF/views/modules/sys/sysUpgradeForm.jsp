<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>app升级配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/sysUpgrade/">app升级配置列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysUpgrade/form?id=${sysUpgrade.id}">app升级配置<shiro:hasPermission name="sys:sysUpgrade:edit">${not empty sysUpgrade.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysUpgrade:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysUpgrade" action="${ctx}/sys/sysUpgrade/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">升级标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本号：</label>
			<div class="controls">
				<form:input path="version" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下载地址：</label>
			<div class="controls">
				<form:hidden id="url" path="url" htmlEscape="false" maxlength="500" class="input-xlarge"/>
				<sys:ckfinder input="url" type="files" uploadPath="/app包" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统：</label>
			<div class="controls">
				<form:radiobutton path="os" value="android" label="android" checked="checked" />
				<form:radiobutton path="os" value="ios" label="ios" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">升级方式：</label>
			<div class="controls">
				<form:radiobutton path="isForce" value="1"  label="强制" checked="checked"  />
				<form:radiobutton path="isForce" value="2"  label="不强制" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysUpgrade:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
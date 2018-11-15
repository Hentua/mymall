<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>佣金提现管理</title>
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
		<li><a href="${ctx}/commission/commissionTakeOut/">佣金提现列表</a></li>
		<li class="active"><a href="${ctx}/commission/commissionTakeOut/form?id=${commissionTakeOut.id}">佣金提现<shiro:hasPermission name="commission:commissionTakeOut:edit">${not empty commissionTakeOut.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="commission:commissionTakeOut:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="commissionTakeOut" action="${ctx}/commission/commissionTakeOut/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户id：</label>
			<div class="controls">
				<form:input path="userId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现金额：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行账户：</label>
			<div class="controls">
				<form:input path="bankAccount" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户人名称：</label>
			<div class="controls">
				<form:input path="bankAccountName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户行：</label>
			<div class="controls">
				<form:input path="bankName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现审核状态：1未审核 2已审核：</label>
			<div class="controls">
				<form:input path="checkStatus" htmlEscape="false" maxlength="3" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="commission:commissionTakeOut:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
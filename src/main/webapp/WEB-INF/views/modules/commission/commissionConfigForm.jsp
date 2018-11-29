<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>佣金比例配置信息管理</title>
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
		<li><a href="${ctx}/commission/commissionConfig/">佣金比例配置信息列表</a></li>
		<li class="active"><a href="${ctx}/commission/commissionConfig/form?id=${commissionConfig.id}">佣金比例配置信息<shiro:hasPermission name="commission:commissionConfig:edit">${not empty commissionConfig.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="commission:commissionConfig:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="commissionConfig" action="${ctx}/commission/commissionConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="type"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">佣金类型：</label>
			<div class="controls">
				<c:if test="${commissionConfig.type == '1'}">
					推荐用户消费返佣
				</c:if>
				<c:if test="${commissionConfig.type == '2'}">
					推荐商家销售返佣
				</c:if>
				<c:if test="${commissionConfig.type == '3'}">
					推荐商家入驻返佣
				</c:if>
				<c:if test="${commissionConfig.type == '4'}">
					推荐商家送出礼包返佣
				</c:if>
				<c:if test="${commissionConfig.type == '5'}">
					商家送出礼包返佣
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">佣金计算方式：</label>
			<div class="controls">
				<form:radiobutton title="固定金额" htmlEscape="false" class="required" path="mode" value="1" label="固定金额"/>
				<%--<c:if test="${commissionConfig.type != '3'}">--%>
				<form:radiobutton title="百分比" htmlEscape="false" class="required" path="mode" value="2" label="百分比"/>
				<%--</c:if>--%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数值：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<form:textarea path="remarkes" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="commission:commissionConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
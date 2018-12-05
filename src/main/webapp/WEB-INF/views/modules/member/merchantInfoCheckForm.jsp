<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>店铺信息审核管理</title>
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
		<li><a href="${ctx}/member/merchantInfoCheck/memberList">店铺信息审核列表</a></li>
        <li class="active"><a href="${ctx}/member/merchantInfoCheck/form?id=${merchantInfoCheck.id}">店铺信息审核申请</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantInfoCheck" action="${ctx}/member/merchantInfoCheck/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">店铺名称：</label>
			<div class="controls">
				<form:input path="merchantName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">头像地址：</label>
				<div class="controls">
					<form:hidden id="avatar" path="avatar" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:ckfinder input="avatar" type="images" uploadPath="/头像" selectMultiple="false" maxWidth="100" maxHeight="100"/>
				</div>
		</div>
		<div class="control-group">
			<label class="control-label">头图：</label>
			<div class="controls">
				<form:hidden id="merchantHeadImg" path="merchantHeadImg" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="merchantHeadImg" type="images" uploadPath="/店铺头图" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客服电话：</label>
			<div class="controls">
				<form:input path="merchantServicePhone" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
            <c:if test="${empty org}">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
            </c:if>
            <c:if test="${!empty org}">
                <span style="color: red">平台正在审核您的申请，请耐心等待</span><br/>
            </c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
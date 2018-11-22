<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员反馈信息</title>
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
		<li><a href="${ctx}/member/memberFeedback/">会员反馈信息</a></li>
		<li class="active"><a href="${ctx}/member/memberFeedback/form?id=${memberFeedback.id}">会员反馈详情</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="memberFeedback" action="${ctx}/member/memberFeedback/reply" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会员：</label>
			<div class="controls">
				${memberFeedback.customerName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员账号：</label>
			<div class="controls">
				${memberFeedback.mobile}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${memberFeedback.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反馈信息：</label>
			<div class="controls">
				<form:textarea path="feedbackDetail" htmlEscape="false" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回复：</label>
			<div class="controls">
				<form:textarea path="reply" htmlEscape="false" maxlength="500" class="input-xlarge required" readonly="${not empty memberFeedback.reply}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${empty memberFeedback.reply}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
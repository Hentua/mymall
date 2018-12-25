<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统参数配置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
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
		<li class="active"><a href="${ctx}/sys/dict/sysConfig">系统参数配置</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysConfig" action="${ctx}/sys/dict/saveSysConfig" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">提现手续费:</label>
			<div class="controls">
				<form:input path="serviceCharge" htmlEscape="false" maxlength="50" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算周期:</label>
			<div class="controls">
				<form:input path="settlementCycle" htmlEscape="false" maxlength="50" class="input-xlarge  number"/>
				<span class="help-inline"><font color="red">（注：单位ms(毫秒),一天(1000*60*60*24=86400000ms)）</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">充值赠送折扣券比例:</label>
			<div class="controls">
				<form:input path="rechargeDiscount" htmlEscape="false" maxlength="50" class="input-xlarge  number"/>
				<span class="help-inline"><font color="red">（注：0则不赠送)）</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">佣金转余额赠送折扣券比例:</label>
			<div class="controls">
				<form:input path="toAccountDiscount" htmlEscape="false" maxlength="50" class="input-xlarge  number"/>
				<span class="help-inline"><font color="red">（注：0则不赠送)）</font> </span>
			</div>
		</div>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
</body>
</html>
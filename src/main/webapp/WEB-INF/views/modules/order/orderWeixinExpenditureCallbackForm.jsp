<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信提现返回结果管理</title>
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
		<li><a href="${ctx}/order/orderWeixinExpenditureCallback/">微信提现返回结果列表</a></li>
		<li class="active"><a href="${ctx}/order/orderWeixinExpenditureCallback/form?id=${orderWeixinExpenditureCallback.id}">微信提现返回结果<shiro:hasPermission name="order:orderWeixinExpenditureCallback:edit">${not empty orderWeixinExpenditureCallback.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:orderWeixinExpenditureCallback:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderWeixinExpenditureCallback" action="${ctx}/order/orderWeixinExpenditureCallback/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">return_code：</label>
			<div class="controls">
				<form:input path="returnCode" htmlEscape="false" maxlength="16" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">return_msg：</label>
			<div class="controls">
				<form:input path="returnMsg" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">mch_appid：</label>
			<div class="controls">
				<form:input path="mchAppid" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">mchid：</label>
			<div class="controls">
				<form:input path="mchid" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">device_info：</label>
			<div class="controls">
				<form:input path="deviceInfo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">nonce_str：</label>
			<div class="controls">
				<form:input path="nonceStr" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">result_code：</label>
			<div class="controls">
				<form:input path="resultCode" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">err_code：</label>
			<div class="controls">
				<form:input path="errCode" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">err_code_des：</label>
			<div class="controls">
				<form:input path="errCodeDes" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">partner_trade_no：</label>
			<div class="controls">
				<form:input path="partnerTradeNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">payment_no：</label>
			<div class="controls">
				<form:input path="paymentNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">payment_time：</label>
			<div class="controls">
				<form:input path="paymentTime" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:orderWeixinExpenditureCallback:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
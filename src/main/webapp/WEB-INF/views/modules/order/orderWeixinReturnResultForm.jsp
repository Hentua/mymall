<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信支付退款结果管理</title>
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
		<li><a href="${ctx}/order/orderWeixinReturnResult/">微信支付退款结果列表</a></li>
		<li class="active"><a href="${ctx}/order/orderWeixinReturnResult/form?id=${orderWeixinReturnResult.id}">微信支付退款结果<shiro:hasPermission name="order:orderWeixinReturnResult:edit">${not empty orderWeixinReturnResult.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:orderWeixinReturnResult:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderWeixinReturnResult" action="${ctx}/order/orderWeixinReturnResult/save" method="post" class="form-horizontal">
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
			<label class="control-label">result_code：</label>
			<div class="controls">
				<form:input path="resultCode" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">err_code：</label>
			<div class="controls">
				<form:input path="errCode" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">err_code_des：</label>
			<div class="controls">
				<form:input path="errCodeDes" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">appid：</label>
			<div class="controls">
				<form:input path="appid" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">mch_id：</label>
			<div class="controls">
				<form:input path="mchId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">nonce_str：</label>
			<div class="controls">
				<form:input path="nonceStr" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">sign：</label>
			<div class="controls">
				<form:input path="sign" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">transaction_id：</label>
			<div class="controls">
				<form:input path="transactionId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">out_trade_no：</label>
			<div class="controls">
				<form:input path="outTradeNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">out_refund_no：</label>
			<div class="controls">
				<form:input path="outRefundNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">refund_id：</label>
			<div class="controls">
				<form:input path="refundId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">refund_fee：</label>
			<div class="controls">
				<form:input path="refundFee" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">settlement_refund_fee：</label>
			<div class="controls">
				<form:input path="settlementRefundFee" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">total_fee：</label>
			<div class="controls">
				<form:input path="totalFee" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">fee_type：</label>
			<div class="controls">
				<form:input path="feeType" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">settlement_total_fee：</label>
			<div class="controls">
				<form:input path="settlementTotalFee" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cash_fee：</label>
			<div class="controls">
				<form:input path="cashFee" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cash_fee_type：</label>
			<div class="controls">
				<form:input path="cashFeeType" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">cash_refund_fee：</label>
			<div class="controls">
				<form:input path="cashRefundFee" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">coupon_refund_fee：</label>
			<div class="controls">
				<form:input path="couponRefundFee" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">coupon_refund_count：</label>
			<div class="controls">
				<form:input path="couponRefundCount" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:orderWeixinReturnResult:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
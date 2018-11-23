<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信支付回调结果管理</title>
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
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/orderPaymentWeixinCallback/">微信支付回调结果列表</a></li>
		<li class="active"><a href="${ctx}/order/orderPaymentWeixinCallback/form?id=${orderPaymentWeixinCallback.id}">微信支付回调结果<shiro:hasPermission name="order:orderPaymentWeixinCallback:edit">${not empty orderPaymentWeixinCallback.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:orderPaymentWeixinCallback:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderPaymentWeixinCallback" action="${ctx}/order/orderPaymentWeixinCallback/save" method="post" class="form-horizontal">
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
				<form:input path="returnMsg" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
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
			<label class="control-label">sign：</label>
			<div class="controls">
				<form:input path="sign" htmlEscape="false" maxlength="32" class="input-xlarge "/>
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
			<label class="control-label">openid：</label>
			<div class="controls">
				<form:input path="openid" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">is_subscribe：</label>
			<div class="controls">
				<form:input path="isSubscribe" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">trade_type：</label>
			<div class="controls">
				<form:input path="tradeType" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bank_type：</label>
			<div class="controls">
				<form:input path="bankType" htmlEscape="false" maxlength="16" class="input-xlarge "/>
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
			<label class="control-label">coupon_fee：</label>
			<div class="controls">
				<form:input path="couponFee" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">coupon_count：</label>
			<div class="controls">
				<form:input path="couponCount" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
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
			<label class="control-label">attach：</label>
			<div class="controls">
				<form:input path="attach" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">time_end：</label>
			<div class="controls">
				<form:input path="timeEnd" htmlEscape="false" maxlength="14" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">promotion_detail：</label>
			<div class="controls">
				<form:input path="promotionDetail" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">version：</label>
			<div class="controls">
				<form:input path="version" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">settlement_total_fee：</label>
			<div class="controls">
				<form:input path="settlementTotalFee" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">微信支付回调使用优惠券数据：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>coupon_fee</th>
								<th>coupon_id</th>
								<th>coupon_type</th>
								<shiro:hasPermission name="order:orderPaymentWeixinCallback:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="orderPaymentWeixinCallbackCouponList">
						</tbody>
						<shiro:hasPermission name="order:orderPaymentWeixinCallback:edit"><tfoot>
							<tr><td colspan="5"><a href="javascript:" onclick="addRow('#orderPaymentWeixinCallbackCouponList', orderPaymentWeixinCallbackCouponRowIdx, orderPaymentWeixinCallbackCouponTpl);orderPaymentWeixinCallbackCouponRowIdx = orderPaymentWeixinCallbackCouponRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="orderPaymentWeixinCallbackCouponTpl">//<!--
						<tr id="orderPaymentWeixinCallbackCouponList{{idx}}">
							<td class="hide">
								<input id="orderPaymentWeixinCallbackCouponList{{idx}}_id" name="orderPaymentWeixinCallbackCouponList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="orderPaymentWeixinCallbackCouponList{{idx}}_delFlag" name="orderPaymentWeixinCallbackCouponList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="orderPaymentWeixinCallbackCouponList{{idx}}_couponFee" name="orderPaymentWeixinCallbackCouponList[{{idx}}].couponFee" type="text" value="{{row.couponFee}}" maxlength="11" class="input-small  digits"/>
							</td>
							<td>
								<input id="orderPaymentWeixinCallbackCouponList{{idx}}_couponId" name="orderPaymentWeixinCallbackCouponList[{{idx}}].couponId" type="text" value="{{row.couponId}}" maxlength="20" class="input-small "/>
							</td>
							<td>
								<input id="orderPaymentWeixinCallbackCouponList{{idx}}_couponType" name="orderPaymentWeixinCallbackCouponList[{{idx}}].couponType" type="text" value="{{row.couponType}}" maxlength="10" class="input-small "/>
							</td>
							<shiro:hasPermission name="order:orderPaymentWeixinCallback:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#orderPaymentWeixinCallbackCouponList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var orderPaymentWeixinCallbackCouponRowIdx = 0, orderPaymentWeixinCallbackCouponTpl = $("#orderPaymentWeixinCallbackCouponTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(orderPaymentWeixinCallback.orderPaymentWeixinCallbackCouponList)};
							for (var i=0; i<data.length; i++){
								addRow('#orderPaymentWeixinCallbackCouponList', orderPaymentWeixinCallbackCouponRowIdx, orderPaymentWeixinCallbackCouponTpl, data[i]);
								orderPaymentWeixinCallbackCouponRowIdx = orderPaymentWeixinCallbackCouponRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:orderPaymentWeixinCallback:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
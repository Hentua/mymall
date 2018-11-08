<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包类别管理</title>
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
		<li class="active"><a href="${ctx}/gift/giftConfigCategory/giftConfigCategoryBuyDetail?id=${giftConfigCategory.id}">礼包购买</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="giftConfigCategory" action="${ctx}/gift/giftConfigCategory/giftConfigCategoryBuy" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">礼包分类名称：</label>
			<div class="controls">
				${giftConfigCategory.categoryName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">礼包价格：</label>
			<div class="controls">
				${giftConfigCategory.giftPrice}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">定制礼包商家：</label>
			<div class="controls">
				${giftConfigCategory.merchantName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否赠送商家资格：</label>
			<div class="controls">
				${giftConfigCategory.merchantQualification == '1' ? '是' : '否'}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否上架销售：</label>
			<div class="controls">
				${giftConfigCategory.status == '1' ? '是' : '否'}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买数量：</label>
			<div class="controls">
				<form:input path="buyCount" class="input-xxlarge digits required" value="1" onchange="calculateAmountTotal('${giftConfigCategory.giftPrice}', this)"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总金额：</label>
			<div class="controls">
				<span class="help-inline" id="amountTotal">${giftConfigCategory.giftPrice}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付渠道：</label>
			<div class="controls">
				<c:if test="${giftConfigCategory.giftPrice <= 10000}"><span id="WeChatPay"><form:radiobutton path="payChannel" label="微信支付" value="0" /></span></c:if>
				<form:radiobutton path="payChannel" label="余额支付" value="3" checked="true"/>
				<form:radiobutton path="payChannel" label="打款到财务" value="2"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="购 买"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">
		function calculateAmountTotal(giftPrice, buyCountObj) {
		    var buyCount = $(buyCountObj).val();
		    var amountTotal = (giftPrice * buyCount).toFixed(2);
		    if(amountTotal > 10000) {
		        $('#WeChatPay').hide();
			}else {
                $('#WeChatPay').show();
			}
		    $("#amountTotal").text(amountTotal);
		}
	</script>
</body>
</html>
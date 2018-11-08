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
		<li class="active"><a href="${ctx}/gift/giftMerchant/giftTransferForm?id=${giftMerchant.id}">礼包赠送</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="giftMerchant" action="" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">礼包名称：</label>
			<div class="controls">
				${giftMerchant.giftConfigCategory.categoryName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">礼包价格：</label>
			<div class="controls">
				${giftMerchant.giftConfigCategory.giftPrice}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">定制礼包商家：</label>
			<div class="controls">
				${giftMerchant.giftConfigCategory.merchantName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否赠送商家资格：</label>
			<div class="controls">
				${giftMerchant.giftConfigCategory.merchantQualification == '1' ? '是' : '否'}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">礼包数量：</label>
			<div class="controls">
				${giftMerchant.giftCount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已赠送数量：</label>
			<div class="controls">
				${giftMerchant.givenCount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">可赠送数量：</label>
			<div class="controls">
				${giftMerchant.stock}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">获取时间：</label>
			<div class="controls">
				<fmt:formatDate value="${giftMerchant.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赠送给：</label>
			<div class="controls">
				<form:input path="customerMobile" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">* 请填写要赠送会员的手机号</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">礼包备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge " readonly="true"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="赠 送"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
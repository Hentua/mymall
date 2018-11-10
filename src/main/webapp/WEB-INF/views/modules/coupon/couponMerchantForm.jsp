<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商家优惠券管理</title>
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
		<li><a href="${ctx}/coupon/couponMerchant/">优惠券列表</a></li>
		<li class="active"><a href="${ctx}/coupon/couponMerchant/form?id=${couponMerchant.id}">优惠券赠送</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="couponMerchant" action="${ctx}/coupon/couponMerchant/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">优惠券类型：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${couponMerchant.couponType == '0'}">五折券</c:when>
					<c:when test="${couponMerchant.couponType == '1'}">七折券</c:when>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠券名称：</label>
			<div class="controls">
				${couponMerchant.couponName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠券金额：</label>
			<div class="controls">
				${couponMerchant.limitAmount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">获得时间：</label>
			<div class="controls">
				<fmt:formatDate value="${couponMerchant.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过期时间：</label>
			<div class="controls">
				<fmt:formatDate value="${couponMerchant.endDate}" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="赠 送"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
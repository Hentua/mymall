<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>优惠券规则管理</title>
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
		<li><a href="${ctx}/coupon/couponConfig/">优惠券规则列表</a></li>
		<li class="active"><a href="${ctx}/coupon/couponConfig/form?id=${couponConfig.id}">优惠券规则<shiro:hasPermission name="coupon:couponConfig:edit">${not empty couponConfig.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="coupon:couponConfig:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="couponConfig" action="${ctx}/coupon/couponConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">优惠券类型：</label>
			<div class="controls">
				<form:select path="couponType" class="input-medium">
					<form:option value="0" label="折扣减免"/>
					<form:option value="1" label="金额减免"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠券名称：</label>
			<div class="controls">
				<form:input path="couponName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过期时间：</label>
			<div class="controls">
				<form:input path="eapiryTime" htmlEscape="false" maxlength="11" class="input-xlarge required number"/> 天
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扣减比例：</label>
			<div class="controls">
				<form:input path="discountRate" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扣减金额：</label>
			<div class="controls">
				<form:input path="discountAmount" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">满减金额限制：</label>
			<div class="controls">
				<form:input path="limitAmount" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可使用：</label>
			<div class="controls">
				<form:select path="status" class="input-medium">
					<form:option value="0" label="可使用"/>
					<form:option value="1" label="不可使用"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="coupon:couponConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
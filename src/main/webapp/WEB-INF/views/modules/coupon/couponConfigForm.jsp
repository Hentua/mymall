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
			<label class="control-label">优惠券名称：</label>
			<div class="controls">
				<form:input path="couponName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠券类型：</label>
			<div class="controls">
				<form:select path="couponType">
					<form:option value="0" label="五折券"/>
					<form:option value="1" label="七折券"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过期时间：</label>
			<div class="controls">
				<form:input path="expiryTime" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*  从发放时间开始计算，单位为天。0为不过期。</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转赠过期时间：</label>
			<div class="controls">
				<form:input path="transferExpiryTime" htmlEscape="false" maxlength="11" class="input-xlarge required digits" value="0"/>
				<span class="help-inline"><font color="red">* 从获得日开始计算，单位为天。0为不过期。</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:radiobutton path="status" value="1" label="启用"/>
				<form:radiobutton path="status" value="0" label="停用" checked="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠券金额：</label>
			<div class="controls">
				<form:input path="limitAmount" htmlEscape="false" class="input-xlarge  number required"/>
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
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
		<li><a href="${ctx}/gift/giftConfigCategory/">礼包类别列表</a></li>
		<li class="active"><a href="${ctx}/gift/giftConfigCategory/form?id=${giftConfigCategory.id}">礼包类别<shiro:hasPermission name="gift:giftConfigCategory:edit">${not empty giftConfigCategory.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="gift:giftConfigCategory:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="giftConfigCategory" action="${ctx}/gift/giftConfigCategory/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">礼包分类名称：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${empty giftConfigCategory.id }">
						<form:input path="categoryName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</c:when>
					<c:otherwise>
						${giftConfigCategory.categoryName}
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">礼包价格：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${empty giftConfigCategory.id }">
						<form:input path="giftPrice" htmlEscape="false" maxlength="20" class="input-xlarge required number"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</c:when>
					<c:otherwise>
						${giftConfigCategory.giftPrice}
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">礼包赠送返佣：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${empty giftConfigCategory.id }">
						<form:input path="commission" htmlEscape="false" maxlength="20" class="input-xlarge required number"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</c:when>
					<c:otherwise>
						${giftConfigCategory.commission}
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">定制礼包商家：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${empty giftConfigCategory.id }">
						<form:input path="merchantMobile" htmlEscape="false" maxlength="20" class="input-xlarge"/>
						<span class="help-inline"><font color="red">留空则不是定制礼包类别，填写商家手机号码</font> </span>
					</c:when>
					<c:otherwise>
						${giftConfigCategory.merchantName}
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否赠送商家资格：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${empty giftConfigCategory.id }">
						<form:radiobutton path="merchantQualification" label="是" value="1"/>
						<form:radiobutton path="merchantQualification" label="否" value="0" checked="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</c:when>
					<c:otherwise>
						${giftConfigCategory.merchantQualification == '1' ? '是' : '否'}
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否上架销售：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${empty giftConfigCategory.id }">
						<form:radiobutton path="status" label="是" value="1"/>
						<form:radiobutton path="status" label="否" value="0" checked="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</c:when>
					<c:otherwise>
						${giftConfigCategory.status == '1' ? '是' : '否'}
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="gift:giftConfigCategory:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
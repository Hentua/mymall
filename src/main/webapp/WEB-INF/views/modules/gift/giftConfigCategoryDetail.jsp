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
		<li class="active"><a href="${ctx}/gift/giftConfigCategory/giftConfigCategoryDetail?id=${giftConfigCategory.id}">礼包详情</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="giftConfigCategory" action="" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">礼包名称：</label>
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
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge " readonly="true"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
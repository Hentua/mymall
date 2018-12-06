<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>店铺信息审核管理</title>
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

        function go_a(status) {
            $("#checkStatus").val(status);
            $("#inputForm").submit();
            <%--location.href = "${ctx}/goods/goodsInfo/updateStatusCheck?id=${goodsInfo.id}&status="+status;--%>
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/member/merchantInfoCheck/list">店铺信息审核列表</a></li>
		<li class="active"><a href="${ctx}/member/merchantInfoCheck/checkform?id=${merchantInfoCheck.id}">店铺信息审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="merchantInfoCheck" action="${ctx}/member/merchantInfoCheck/checkStatus" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="checkStatus" name="checkStatus" >
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">店铺名称：</label>
			<div class="controls">
				${merchantInfoCheck.merchantName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">头像地址：</label>
			<div class="controls">
				<img src="${merchantInfoCheck.avatar}" width="100">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">头图：</label>
			<div class="controls">
				<img src="${merchantInfoCheck.merchantHeadImg}" width="200" height="100">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客服电话：</label>
			<div class="controls">
				${merchantInfoCheck.merchantServicePhone}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核备注：</label>
			<div class="controls">
				<form:textarea path="checkRemark" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				<input id="btnSubmit2" class="btn btn-primary" type="button" onclick="go_a('2')" value="上 架"/>
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="go_a('3')" value="驳 回"/>
		</div>
	</form:form>
</body>
</html>
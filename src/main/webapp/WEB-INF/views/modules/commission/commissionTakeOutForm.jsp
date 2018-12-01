<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>佣金提现管理</title>
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
		<li class="active"><a href="${ctx}/commission/commissionTakeOut/form?id=${commissionInfo.id}">打款审核</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="commissionInfo" action="${ctx}/commission/commissionTakeOut/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="checkStatus" name="checkStatus" >
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户账号：</label>
			<div class="controls">
				${commissionInfo.userMobile}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户名称：</label>
			<div class="controls">
				${commissionInfo.userName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行账户：</label>
			<div class="controls">
				${commissionInfo.bankAccount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户人名称：</label>
			<div class="controls">
				${commissionInfo.bankAccountName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户行：</label>
			<div class="controls">
				${commissionInfo.bankName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现金额：</label>
			<div class="controls">
					${commissionInfo.amount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核备注：</label>
			<div class="controls">
				<form:textarea path="checkRemark" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input class="btn btn-primary" type="button"  onclick="go_a('2')" value="审核通过"/>
			<input  class="btn btn-primary" type="button"  onclick="go_a('3')" value="驳 回"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
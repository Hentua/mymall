<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现结算信息管理</title>
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
		<li><a href="${ctx}/settlement/settlementInfo/">提现结算信息列表</a></li>
		<li class="active"><a href="${ctx}/settlement/settlementInfo/form?id=${settlementInfo.id}">提现结算信息<shiro:hasPermission name="settlement:settlementInfo:edit">${not empty settlementInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="settlement:settlementInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="settlementInfo" action="${ctx}/settlement/settlementInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">提现结算类型  ：</label>
			<div class="controls">
				<c:if test="${settlementInfo.type == 1}">
					佣金提现
				</c:if>
				<c:if test="${settlementInfo.type == 2}">
					订单交易结算
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算金额：</label>
			<div class="controls">
				${settlementInfo.amount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算审核状态 ：</label>
			<div class="controls">
				<c:if test="${settlementInfo.status == 1}">
					待提交
				</c:if>
				<c:if test="${settlementInfo.status == 2}">
					待结算
				</c:if>
				<c:if test="${settlementInfo.status == 3}">
					已结算
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提交人：</label>
			<div class="controls">
				<c:if test="${empty settlementInfo.subUserId}">
					系统生成
				</c:if>
					${settlementInfo.subUserName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提交时间：</label>
			<div class="controls">
				<fmt:formatDate value="${settlementInfo.subDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结算备注：</label>
			<div class="controls">
				<form:input path="settlementRemarks" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="settlement:settlementInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
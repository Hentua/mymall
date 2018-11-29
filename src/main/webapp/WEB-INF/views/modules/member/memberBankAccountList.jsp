<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户银行卡管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/member/memberBankAccount/">用户银行卡列表</a></li>
		<shiro:hasPermission name="member:memberBankAccount:edit"><li><a href="${ctx}/member/memberBankAccount/form">用户银行卡添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="memberBankAccount" action="${ctx}/member/memberBankAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属用户：</label>
				<form:input path="userId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>银行卡号：</label>
				<form:input path="bankAccount" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>开户人名称：</label>
				<form:input path="bankAccountName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>开户行地址：</label>
				<form:input path="bankAddress" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${memberBankAccount.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>所属用户</th>
				<th>银行卡号</th>
				<th>开户人名称</th>
				<th>开户行地址</th>
				<th>创建时间</th>
				<shiro:hasPermission name="member:memberBankAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memberBankAccount">
			<tr>
				<td><a href="${ctx}/member/memberBankAccount/form?id=${memberBankAccount.id}">
					${memberBankAccount.userId}
				</a></td>
				<td>
					${memberBankAccount.bankAccount}
				</td>
				<td>
					${memberBankAccount.bankAccountName}
				</td>
				<td>
					${memberBankAccount.bankAddress}
				</td>
				<td>
					<fmt:formatDate value="${memberBankAccount.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="member:memberBankAccount:edit"><td>
    				<a href="${ctx}/member/memberBankAccount/form?id=${memberBankAccount.id}">修改</a>
					<a href="${ctx}/member/memberBankAccount/delete?id=${memberBankAccount.id}" onclick="return confirmx('确认要删除该用户银行卡吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
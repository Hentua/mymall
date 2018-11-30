<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台银行账户管理管理</title>
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
		<li class="active"><a href="${ctx}/sys/platBankAccount/">平台银行账户管理列表</a></li>
		<shiro:hasPermission name="sys:platBankAccount:edit"><li><a href="${ctx}/sys/platBankAccount/form">平台银行账户管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="platBankAccount" action="${ctx}/sys/platBankAccount/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>账户：</label>
				<form:input path="bankAccount" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>开户人：</label>
				<form:input path="bankAccountName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>开户行：</label>
				<form:input path="bankName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>平台银行账户</th>
				<th>平台银行账户开户人</th>
				<th>平台银行账户开户行</th>
				<th>title</th>
				<shiro:hasPermission name="sys:platBankAccount:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="platBankAccount">
			<tr>
				<td><a href="${ctx}/sys/platBankAccount/form?id=${platBankAccount.id}">
					${platBankAccount.bankAccount}
				</a></td>
				<td>
					${platBankAccount.bankAccountName}
				</td>
				<td>
					${platBankAccount.bankName}
				</td>
				<td>
					${platBankAccount.title}
				</td>
				<shiro:hasPermission name="sys:platBankAccount:edit"><td>
    				<a href="${ctx}/sys/platBankAccount/form?id=${platBankAccount.id}">修改</a>
					<a href="${ctx}/sys/platBankAccount/delete?id=${platBankAccount.id}" onclick="return confirmx('确认要删除该平台银行账户管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
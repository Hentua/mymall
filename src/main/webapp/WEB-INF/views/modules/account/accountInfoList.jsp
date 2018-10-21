<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户明细管理</title>
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
		<li class="active"><a href="${ctx}/account/accountInfo/">账户明细列表</a></li>
		<shiro:hasPermission name="account:accountInfo:edit"><li><a href="${ctx}/account/accountInfo/form">账户明细添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="accountInfo" action="${ctx}/account/accountInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>收支类型 1：收入 2：支出：</label>
				<form:input path="type" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>收入（1：佣金收益 2：销售收益） 支出（3：提现 4：结算）：</label>
				<form:input path="way" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>关联ID（佣金明细ID 销售订单ID 提现记录ID 消费订单ID）：</label>
				<form:input path="unionId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>金额：</label>
				<form:input path="amount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>状态 （1：已到账 2：未到账 3：未提现结算 4：已提现结算 ）【订单交易成功后可退货期内（7天）不可以提现结算】【推荐商家入驻除外， 入驻成功则佣金到账可提现】：</label>
				<form:input path="status" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<shiro:hasPermission name="account:accountInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accountInfo">
			<tr>
				<shiro:hasPermission name="account:accountInfo:edit"><td>
    				<a href="${ctx}/account/accountInfo/form?id=${accountInfo.id}">修改</a>
					<a href="${ctx}/account/accountInfo/delete?id=${accountInfo.id}" onclick="return confirmx('确认要删除该账户明细吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
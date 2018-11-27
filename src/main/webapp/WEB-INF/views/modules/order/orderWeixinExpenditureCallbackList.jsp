<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信提现返回结果管理</title>
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
		<li class="active"><a href="${ctx}/order/orderWeixinExpenditureCallback/">微信提现返回结果列表</a></li>
		<shiro:hasPermission name="order:orderWeixinExpenditureCallback:edit"><li><a href="${ctx}/order/orderWeixinExpenditureCallback/form">微信提现返回结果添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderWeixinExpenditureCallback" action="${ctx}/order/orderWeixinExpenditureCallback/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>update_date</th>
				<shiro:hasPermission name="order:orderWeixinExpenditureCallback:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderWeixinExpenditureCallback">
			<tr>
				<td><a href="${ctx}/order/orderWeixinExpenditureCallback/form?id=${orderWeixinExpenditureCallback.id}">
					<fmt:formatDate value="${orderWeixinExpenditureCallback.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="order:orderWeixinExpenditureCallback:edit"><td>
    				<a href="${ctx}/order/orderWeixinExpenditureCallback/form?id=${orderWeixinExpenditureCallback.id}">修改</a>
					<a href="${ctx}/order/orderWeixinExpenditureCallback/delete?id=${orderWeixinExpenditureCallback.id}" onclick="return confirmx('确认要删除该微信提现返回结果吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
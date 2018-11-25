<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信支付退款结果管理</title>
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
		<li class="active"><a href="${ctx}/order/orderWeixinReturnResult/">微信支付退款结果列表</a></li>
		<shiro:hasPermission name="order:orderWeixinReturnResult:edit"><li><a href="${ctx}/order/orderWeixinReturnResult/form">微信支付退款结果添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderWeixinReturnResult" action="${ctx}/order/orderWeixinReturnResult/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="order:orderWeixinReturnResult:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderWeixinReturnResult">
			<tr>
				<td><a href="${ctx}/order/orderWeixinReturnResult/form?id=${orderWeixinReturnResult.id}">
					<fmt:formatDate value="${orderWeixinReturnResult.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="order:orderWeixinReturnResult:edit"><td>
    				<a href="${ctx}/order/orderWeixinReturnResult/form?id=${orderWeixinReturnResult.id}">修改</a>
					<a href="${ctx}/order/orderWeixinReturnResult/delete?id=${orderWeixinReturnResult.id}" onclick="return confirmx('确认要删除该微信支付退款结果吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
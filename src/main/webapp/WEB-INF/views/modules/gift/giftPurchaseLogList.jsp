<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包购买记录管理</title>
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
		<li class="active"><a href="${ctx}/gift/giftPurchaseLog/">礼包购买记录列表</a></li>
		<shiro:hasPermission name="gift:giftPurchaseLog:edit"><li><a href="${ctx}/gift/giftPurchaseLog/form">礼包购买记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="giftPurchaseLog" action="${ctx}/gift/giftPurchaseLog/" method="post" class="breadcrumb form-search">
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
				<th>remarks</th>
				<shiro:hasPermission name="gift:giftPurchaseLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftPurchaseLog">
			<tr>
				<td><a href="${ctx}/gift/giftPurchaseLog/form?id=${giftPurchaseLog.id}">
					<fmt:formatDate value="${giftPurchaseLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${giftPurchaseLog.remarks}
				</td>
				<shiro:hasPermission name="gift:giftPurchaseLog:edit"><td>
    				<a href="${ctx}/gift/giftPurchaseLog/form?id=${giftPurchaseLog.id}">修改</a>
					<a href="${ctx}/gift/giftPurchaseLog/delete?id=${giftPurchaseLog.id}" onclick="return confirmx('确认要删除该礼包购买记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
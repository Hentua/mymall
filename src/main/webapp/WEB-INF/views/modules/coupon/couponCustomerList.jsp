<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户优惠券管理</title>
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
		<li class="active"><a href="${ctx}/coupon/couponCustomer/">用户优惠券列表</a></li>
		<shiro:hasPermission name="coupon:couponCustomer:edit"><li><a href="${ctx}/coupon/couponCustomer/form">用户优惠券添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="couponCustomer" action="${ctx}/coupon/couponCustomer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>优惠券类型：</label>
				<form:input path="couponType" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>用户：</label>
				<form:input path="customerCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>update_date</th>
				<shiro:hasPermission name="coupon:couponCustomer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="couponCustomer">
			<tr>
				<td><a href="${ctx}/coupon/couponCustomer/form?id=${couponCustomer.id}">
					<fmt:formatDate value="${couponCustomer.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="coupon:couponCustomer:edit"><td>
    				<a href="${ctx}/coupon/couponCustomer/form?id=${couponCustomer.id}">修改</a>
					<a href="${ctx}/coupon/couponCustomer/delete?id=${couponCustomer.id}" onclick="return confirmx('确认要删除该用户优惠券吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
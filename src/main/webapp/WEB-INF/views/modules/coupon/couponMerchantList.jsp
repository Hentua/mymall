<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商家优惠券管理</title>
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
		<li class="active"><a href="${ctx}/coupon/couponMerchant/">商家优惠券列表</a></li>
		<shiro:hasPermission name="coupon:couponMerchant:edit"><li><a href="${ctx}/coupon/couponMerchant/form">商家优惠券添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="couponMerchant" action="${ctx}/coupon/couponMerchant/" method="post" class="breadcrumb form-search">
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
				<th>优惠券类型</th>
				<th>商户ID</th>
				<th>可用余额</th>
				<th>update_date</th>
				<shiro:hasPermission name="coupon:couponMerchant:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="couponMerchant">
			<tr>
				<td><a href="${ctx}/coupon/couponMerchant/form?id=${couponMerchant.id}">
					${couponMerchant.couponType}
				</a></td>
				<td>
					${couponMerchant.merchantCode}
				</td>
				<td>
					${couponMerchant.balance}
				</td>
				<td>
					<fmt:formatDate value="${couponMerchant.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="coupon:couponMerchant:edit"><td>
    				<a href="${ctx}/coupon/couponMerchant/form?id=${couponMerchant.id}">修改</a>
					<a href="${ctx}/coupon/couponMerchant/delete?id=${couponMerchant.id}" onclick="return confirmx('确认要删除该商家优惠券吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
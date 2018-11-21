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
		<li class="active"><a href="${ctx}/coupon/couponMerchant/">商家优惠券</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>优惠券类型</th>
				<th>可用余额</th>
				<shiro:hasPermission name="coupon:couponMerchant:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="couponMerchant">
			<tr>
				<td>
					<c:choose>
						<c:when test="${couponMerchant.couponType == '0'}">
							五折券
						</c:when>
						<c:when test="${couponMerchant.couponType == '1'}">
							七折券
						</c:when>
					</c:choose>
				</td>
				<td>
					${couponMerchant.balance}
				</td>
				<shiro:hasPermission name="coupon:couponMerchant:edit"><td>
    				<a href="${ctx}/coupon/couponMerchant/form?id=${couponMerchant.id}" onclick="return confirmx('确认要赠送优惠券吗？', this.href)">赠送</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
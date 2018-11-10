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
		<li class="active"><a href="${ctx}/coupon/couponMerchant/">优惠券列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="couponMerchant" action="${ctx}/coupon/couponMerchant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>优惠券类型：</label>
				<form:select path="couponType">
					<form:option value="" label="全部"/>
					<form:option value="0" label="五折券"/>
					<form:option value="1" label="七折券"/>
				</form:select>
			</li>
			<li><label>优惠券名称：</label>
				<form:input path="couponName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>优惠券类型</th>
				<th>优惠券名称</th>
				<th>优惠券金额</th>
				<th>获得时间</th>
				<th>过期时间</th>
				<th>备注</th>
				<th>赠送</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="couponMerchant">
			<tr>
				<td>
					<c:choose>
						<c:when test="${couponMerchant.couponType == '0'}">五折券</c:when>
						<c:when test="${couponMerchant.couponType == '1'}">七折券</c:when>
					</c:choose>
				</td>
				<td>
						${couponMerchant.couponName}
				</td>
				<td>
						${couponMerchant.limitAmount}
				</td>
				<td>
					<fmt:formatDate value="${couponMerchant.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${couponMerchant.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${couponMerchant.remarks}
				</td>
				<td>
					<a href="${ctx}/coupon/couponMerchant/form?id=${couponMerchant.id}" onclick="return confirmx('确认要赠送该优惠券吗？', this.href)">赠送</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
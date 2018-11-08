<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>优惠券规则管理</title>
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
	<form:form id="searchForm" modelAttribute="couponConfig" action="${ctx}/coupon/couponConfig/" method="post" class="breadcrumb form-search">
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
	已选择：<span id="selectedCoupon"></span>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>优惠券类型</th>
				<th>优惠券名称</th>
				<th>优惠券金额</th>
				<th>创建时间</th>
				<th>最后修改时间</th>
				<th>状态</th>
				<th>备注</th>
				<th>选择</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="couponConfig">
			<tr>
				<td>
					<c:choose>
						<c:when test="${couponConfig.couponType == '0'}">五折券</c:when>
						<c:when test="${couponConfig.couponType == '1'}">七折券</c:when>
					</c:choose>
				</td>
				<td>
					${couponConfig.couponName}
				</td>
				<td>
					${couponConfig.limitAmount}
				</td>
				<td>
					<fmt:formatDate value="${couponConfig.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${couponConfig.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:choose>
						<c:when test="${couponConfig.status == '0'}">停用</c:when>
						<c:when test="${couponConfig.status == '1'}">启用</c:when>
					</c:choose>
				</td>
				<td>
					${couponConfig.remarks}
				</td>
				<td>
					<a href="javascript:void(0)" onclick="selectCoupon('${couponConfig.id}', '${couponConfig.couponType}', '${couponConfig.limitAmount}', '${couponConfig.couponName}')">选择</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<input id="couponId" type="hidden"/>
	<input id="couponType" type="hidden"/>
	<input id="limitAmount" type="hidden"/>
	<input id="couponShowName" type="hidden"/>
	<script type="text/javascript">
        function selectCoupon(id, couponType, limitAmount, couponName) {
            $('#couponId').val(id);
            $('#couponType').val(couponType);
            $('#limitAmount').val(limitAmount);
            $('#couponShowName').val(limitAmount);
            $('#selectedCoupon').text(couponName);
        }
	</script>
	<div class="pagination">${page}</div>
</body>
</html>
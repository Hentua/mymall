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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/coupon/couponConfig/">优惠券规则列表</a></li>
		<shiro:hasPermission name="coupon:couponConfig:edit"><li><a href="${ctx}/coupon/couponConfig/form">优惠券规则添加</a></li></shiro:hasPermission>
	</ul>
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
			<li><label>是否可使用：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="1" label="是"/>
					<form:option value="0" label="否"/>
				</form:select>
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
				<th>创建时间</th>
				<th>最后修改时间</th>
				<th>状态</th>
				<th>备注</th>
				<shiro:hasPermission name="coupon:couponConfig:edit"><th>操作</th></shiro:hasPermission>
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
				<shiro:hasPermission name="coupon:couponConfig:edit"><td>
					<c:choose>
						<c:when test="${couponConfig.status == '0'}"><a href="${ctx}/coupon/couponConfig/form?id=${couponConfig.id}">启用</a></c:when>
						<c:when test="${couponConfig.status == '1'}"><a href="${ctx}/coupon/couponConfig/form?id=${couponConfig.id}">停用</a></c:when>
					</c:choose>
					<a href="${ctx}/coupon/couponConfig/delete?id=${couponConfig.id}" onclick="return confirmx('确认要删除该优惠券规则吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
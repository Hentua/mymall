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
				<form:select path="couponType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="折扣减免"/>
					<form:option value="1" label="金额减免"/>
					<form:option value="2" label="满减"/>
				</form:select>
			</li>
			<li><label>优惠券名称：</label>
				<form:input path="couponName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>是否可使用：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="可使用"/>
					<form:option value="1" label="不可使用"/>
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
				<th>过期时间</th>
				<th>扣减比例</th>
				<th>扣减金额</th>
				<th>满减金额限制</th>
				<th>是否可使用</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>更新人</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="coupon:couponConfig:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="couponConfig">
			<tr>
				<td>
					<c:if test="${couponConfig.couponType == '0'}">
						折扣减免
					</c:if>
					<c:if test="${couponConfig.couponType == '1'}">
						金额减免
					</c:if>
					<c:if test="${couponConfig.couponType == '2'}">
						满减
					</c:if>
				</td>
				<td>
					${couponConfig.couponName}
				</td>
				<td>
					${couponConfig.eapiryTime} 天
				</td>
				<td>
					${couponConfig.discountRate}
				</td>
				<td>
					${couponConfig.discountAmount}
				</td>
				<td>
					${couponConfig.limitAmount}
				</td>
				<td>
					${couponConfig.status eq '0' ? '可使用' : '不可使用'}
				</td>
				<td>
					${couponConfig.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${couponConfig.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${couponConfig.updateBy.name}
				</td>
				<td>
					<fmt:formatDate value="${couponConfig.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${couponConfig.remarks}
				</td>
				<shiro:hasPermission name="coupon:couponConfig:edit"><td>
    				<a href="${ctx}/coupon/couponConfig/form?id=${couponConfig.id}">修改</a>
					<a href="${ctx}/coupon/couponConfig/delete?id=${couponConfig.id}" onclick="return confirmx('确认要删除该优惠券规则吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
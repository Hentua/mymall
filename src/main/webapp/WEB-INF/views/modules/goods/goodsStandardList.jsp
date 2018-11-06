<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品规格管理</title>
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
		<li class="active"><a href="${ctx}/goods/goodsStandard/">商品规格列表</a></li>
		<shiro:hasPermission name="goods:goodsStandard:edit"><li><a href="${ctx}/goods/goodsStandard/form">商品规格添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsStandard" action="${ctx}/goods/goodsStandard/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>name：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>price：</label>
				<form:input path="price" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>goods_id：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>category_id：</label>
				<form:input path="categoryId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>name</th>
				<th>price</th>
				<th>goods_id</th>
				<th>category_id</th>
				<shiro:hasPermission name="goods:goodsStandard:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsStandard">
			<tr>
				<td><a href="${ctx}/goods/goodsStandard/form?id=${goodsStandard.id}">
					${goodsStandard.name}
				</a></td>
				<td>
					${goodsStandard.price}
				</td>
				<td>
					${goodsStandard.goodsId}
				</td>
				<td>
					${goodsStandard.categoryId}
				</td>
				<shiro:hasPermission name="goods:goodsStandard:edit"><td>
    				<a href="${ctx}/goods/goodsStandard/form?id=${goodsStandard.id}">修改</a>
					<a href="${ctx}/goods/goodsStandard/delete?id=${goodsStandard.id}" onclick="return confirmx('确认要删除该商品规格吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
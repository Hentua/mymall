<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品分类管理</title>
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
		<li class="active"><a href="${ctx}/goods/goodsCategory/list">商品分类列表</a></li>
		<shiro:hasPermission name="goods:goodsCategory:edit"><li><a href="${ctx}/goods/goodsCategory/form">商品分类添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsCategory" action="${ctx}/goods/goodsCategory/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>分类名称：</label>
				<form:input path="categoryName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>分类级别：</label>
				<form:select path="depth" cssStyle="width: 170px">
					<form:option value="">全部</form:option>
					<form:option value="0">一级分类</form:option>
					<form:option value="1">二级分类</form:option>
				</form:select>
			</li>
			<%--<li><label>分类状态：</label>--%>
				<%--<form:select path="status"  cssStyle="width: 170px">--%>
					<%--<form:option value="">全部</form:option>--%>
					<%--<form:option value="1">有效</form:option>--%>
					<%--<form:option value="0">无效</form:option>--%>
				<%--</form:select>--%>
			<%--</li>--%>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${goodsCategory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>分类图片</th>
				<th>父分类名称</th>
				<th>分类名称</th>
				<th>层级</th>
				<th>排序</th>
				<%--<th>状态</th>--%>
				<th>创建者</th>
				<th>创建时间</th>
				<th>更新者</th>
				<th>更新时间</th>
				<shiro:hasPermission name="goods:goodsCategory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsCategory">
			<tr>
				<td>
						<img style="border: 1px solid #3aa9e7;" src="${goodsCategory.image}" width="50px" height="50px">
				</td>
				<td>
						${goodsCategory.parentCategoryName == null ? "顶级分类" : goodsCategory.parentCategoryName}
				</td>
				<td>
					${goodsCategory.categoryName}
				</td>
				<td>
					<c:if test="${goodsCategory.depth == 0}">
						一级分类
					</c:if>
					<c:if test="${goodsCategory.depth == 1}">
						二级分类
					</c:if>
				</td>
				<td>${goodsCategory.sort}</td>
				<%--<td>--%>
					<%--<c:if test="${goodsCategory.status == 1}">--%>
						<%--有效--%>
					<%--</c:if>--%>
					<%--<c:if test="${goodsCategory.status == 0}">--%>
						<%--无效--%>
					<%--</c:if>--%>
				<%--</td>--%>
				<td>
					${goodsCategory.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${goodsCategory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsCategory.updateBy.name}
				</td>
				<td>
					<fmt:formatDate value="${goodsCategory.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="goods:goodsCategory:edit"><td>
    				<a href="${ctx}/goods/goodsCategory/form?id=${goodsCategory.id}">修改</a>
					<a href="${ctx}/goods/goodsCategory/delete?id=${goodsCategory.id}" onclick="return confirmx('确认要删除该商品分类吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
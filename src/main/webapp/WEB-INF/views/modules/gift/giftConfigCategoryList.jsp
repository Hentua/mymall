<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包类别管理</title>
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
		<li class="active"><a href="${ctx}/gift/giftConfigCategory/">礼包类别列表</a></li>
		<shiro:hasPermission name="gift:giftConfigCategory:edit"><li><a href="${ctx}/gift/giftConfigCategory/form">礼包类别添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="giftConfigCategory" action="${ctx}/gift/giftConfigCategory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>分类名称：</label>
				<form:input path="categoryName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>礼包分类名称</th>
				<th>礼包价格</th>
				<th>是否赠送商家资格</th>
				<th>定制礼包商家</th>
				<th>创建时间</th>
				<th>备注</th>
				<th>是否上架销售</th>
				<shiro:hasPermission name="gift:giftConfigCategory:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftConfigCategory">
			<tr>
				<td><a href="${ctx}/gift/giftConfigCategory/form?id=${giftConfigCategory.id}">
					${giftConfigCategory.categoryName}
				</a></td>
				<td>
					${giftConfigCategory.giftPrice}
				</td>
				<td>
					<c:choose>
						<c:when test="${giftConfigCategory.merchantQualification == '0'}">否</c:when>
						<c:when test="${giftConfigCategory.merchantQualification == '1'}">是</c:when>
					</c:choose>
				</td>
				<td>
                    ${giftConfigCategory.merchantName}
				</td>
				<td>
					<fmt:formatDate value="${giftConfigCategory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${giftConfigCategory.remarks}
				</td>
				<td>
					<c:choose>
						<c:when test="${giftConfigCategory.status == '0'}">否</c:when>
						<c:when test="${giftConfigCategory.status == '1'}">是</c:when>
					</c:choose>
				</td>
				<shiro:hasPermission name="gift:giftConfigCategory:edit"><td>
					<c:choose>
						<c:when test="${giftConfigCategory.status == '0'}"><a href="${ctx}/gift/giftConfigCategory/form?id=${giftConfigCategory.id}">上架</a></c:when>
						<c:when test="${giftConfigCategory.status == '1'}"><a href="${ctx}/gift/giftConfigCategory/form?id=${giftConfigCategory.id}">下架</a></c:when>
					</c:choose>
					<a href="${ctx}/gift/giftConfigCategory/delete?id=${giftConfigCategory.id}" onclick="return confirmx('确认要删除该礼包类别吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>首页广告位管理</title>
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
		<li class="active"><a href="${ctx}/billboard/indexBillboard/">首页广告位列表</a></li>
		<shiro:hasPermission name="billboard:indexBillboard:edit"><li><a href="${ctx}/billboard/indexBillboard/form">首页广告位添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="indexBillboard" action="${ctx}/billboard/indexBillboard/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>轮播图片</th>
				<th>排序</th>
				<th>类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="billboard:indexBillboard:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="indexBillboard">
			<tr>
				<td><a href="${ctx}/billboard/indexBillboard/form?id=${indexBillboard.id}">
					${indexBillboard.title}
				</a></td>
				<td>
					<img src="${indexBillboard.image}" width="200px" height="80px">
				</td>
				<td>
					${indexBillboard.sort}
				</td>
				<td>
					<c:if test="${indexBillboard.type == 1}">
						轮播图广告
					</c:if>
					<c:if test="${indexBillboard.type == 2}">
						固定广告
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${indexBillboard.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${indexBillboard.remarks}
				</td>
				<shiro:hasPermission name="billboard:indexBillboard:edit"><td>
    				<a href="${ctx}/billboard/indexBillboard/form?id=${indexBillboard.id}">修改</a>
					<a href="${ctx}/billboard/indexBillboard/delete?id=${indexBillboard.id}" onclick="return confirmx('确认要删除该首页广告位吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
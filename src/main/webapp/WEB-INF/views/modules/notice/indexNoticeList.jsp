<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>首页公告管理</title>
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
		<li class="active"><a href="${ctx}/notice/indexNotice/">首页公告列表</a></li>
		<shiro:hasPermission name="notice:indexNotice:edit"><li><a href="${ctx}/notice/indexNotice/form">首页公告添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="indexNotice" action="${ctx}/notice/indexNotice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
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
				<th>创建人</th>
				<th>创建时间</th>
				<shiro:hasPermission name="notice:indexNotice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="indexNotice">
			<tr>
				<td><a href="${ctx}/notice/indexNotice/form?id=${indexNotice.id}">
					${indexNotice.title}
				</a></td>
				<td>
					${indexNotice.createUser}
				</td>
				<td>
					<fmt:formatDate value="${indexNotice.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="notice:indexNotice:edit"><td>
    				<a href="${ctx}/notice/indexNotice/form?id=${indexNotice.id}">修改</a>
					<a href="${ctx}/notice/indexNotice/delete?id=${indexNotice.id}" onclick="return confirmx('确认要删除该首页公告吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
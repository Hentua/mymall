<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户信息管理</title>
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
		<li class="active"><a href="${ctx}/member/memberInfo/">用户信息列表</a></li>
		<shiro:hasPermission name="member:memberInfo:edit"><li><a href="${ctx}/member/memberInfo/form">用户信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="memberInfo" action="${ctx}/member/memberInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>主键，与sys_user主键保持一致</th>
				<th>自己的推荐码</th>
				<th>推荐人ID</th>
				<th>账户余额</th>
				<th>头像地址</th>
				<th>create_date</th>
				<th>update_date</th>
				<th>注册途径（0-注册页面自主注册，1-商户管理后台直接添加）</th>
				<th>update_by</th>
				<th>create_by</th>
				<th>del_flag</th>
				<shiro:hasPermission name="member:memberInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memberInfo">
			<tr>
				<td><a href="${ctx}/member/memberInfo/form?id=${memberInfo.id}">
					${memberInfo.id}
				</a></td>
				<td>
					${memberInfo.referee}
				</td>
				<td>
					${memberInfo.refereeId}
				</td>
				<td>
					${memberInfo.balance}
				</td>
				<td>
					${memberInfo.avatar}
				</td>
				<td>
					<fmt:formatDate value="${memberInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${memberInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${memberInfo.registerWay}
				</td>
				<td>
					${memberInfo.updateBy.id}
				</td>
				<td>
					${memberInfo.createBy.id}
				</td>
				<td>
					${fns:getDictLabel(memberInfo.delFlag, 'del_flag', '')}
				</td>
				<shiro:hasPermission name="member:memberInfo:edit"><td>
    				<a href="${ctx}/member/memberInfo/form?id=${memberInfo.id}">修改</a>
					<a href="${ctx}/member/memberInfo/delete?id=${memberInfo.id}" onclick="return confirmx('确认要删除该用户信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
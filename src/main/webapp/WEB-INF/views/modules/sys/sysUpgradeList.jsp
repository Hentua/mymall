<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>app升级配置管理</title>
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
		<li class="active"><a href="${ctx}/sys/sysUpgrade/">app升级配置列表</a></li>
		<shiro:hasPermission name="sys:sysUpgrade:edit"><li><a href="${ctx}/sys/sysUpgrade/form">app升级配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysUpgrade" action="${ctx}/sys/sysUpgrade/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>升级标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>版本号：</label>
				<form:input path="version" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>升级标题</th>
				<th>版本号</th>
				<th>下载地址</th>
				<th>内容</th>
				<th>create_date</th>
				<th>系统</th>
				<th>升级方式</th>
				<shiro:hasPermission name="sys:sysUpgrade:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysUpgrade">
			<tr>
				<td><a href="${ctx}/sys/sysUpgrade/form?id=${sysUpgrade.id}">
					${sysUpgrade.title}
				</a></td>
				<td>
					${sysUpgrade.version}
				</td>
				<td>
					<%--<c:if test="${!empty sysUpgrade.url}">--%>
						<%--<a href="${ctx}/${sysUpgrade.url}" >下载安装包</a>--%>
					<%--</c:if>--%>
						${ctx}/${sysUpgrade.url}
				</td>
				<td>
					${sysUpgrade.content}
				</td>
				<td>
					<fmt:formatDate value="${sysUpgrade.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysUpgrade.os}
				</td>
				<td>
					<c:if test="${sysUpgrade.isForce == '1'}">
						强制
					</c:if>
					<c:if test="${sysUpgrade.isForce == '2'}">
						不强制
					</c:if>
				</td>
				<shiro:hasPermission name="sys:sysUpgrade:edit"><td>
    				<a href="${ctx}/sys/sysUpgrade/form?id=${sysUpgrade.id}">修改</a>
					<a href="${ctx}/sys/sysUpgrade/delete?id=${sysUpgrade.id}" onclick="return confirmx('确认要删除该app升级配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
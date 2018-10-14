<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>短信发送记录管理</title>
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
		<li class="active"><a href="${ctx}/sys/sysSmsLog/">短信发送记录列表</a></li>
		<shiro:hasPermission name="sys:sysSmsLog:edit"><li><a href="${ctx}/sys/sysSmsLog/form">短信发送记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysSmsLog" action="${ctx}/sys/sysSmsLog/" method="post" class="breadcrumb form-search">
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
				<th>接收手机号</th>
				<th>短信签名</th>
				<th>短信模板ID</th>
				<th>模板参数</th>
				<th>请求阿里云短信ID</th>
				<th>短信发送返回码</th>
				<th>返回状态码描述</th>
				<th>发送回执ID,可根据该ID查询具体的发送状态</th>
				<th>create_by</th>
				<th>create_date</th>
				<th>update_by</th>
				<th>update_date</th>
				<shiro:hasPermission name="sys:sysSmsLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysSmsLog">
			<tr>
				<td><a href="${ctx}/sys/sysSmsLog/form?id=${sysSmsLog.id}">
					${sysSmsLog.phone}
				</a></td>
				<td>
					${sysSmsLog.signName}
				</td>
				<td>
					${sysSmsLog.templateCode}
				</td>
				<td>
					${sysSmsLog.templateParam}
				</td>
				<td>
					${sysSmsLog.requestId}
				</td>
				<td>
					${sysSmsLog.code}
				</td>
				<td>
					${sysSmsLog.message}
				</td>
				<td>
					${sysSmsLog.bizId}
				</td>
				<td>
					${sysSmsLog.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${sysSmsLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysSmsLog.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${sysSmsLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sys:sysSmsLog:edit"><td>
    				<a href="${ctx}/sys/sysSmsLog/form?id=${sysSmsLog.id}">修改</a>
					<a href="${ctx}/sys/sysSmsLog/delete?id=${sysSmsLog.id}" onclick="return confirmx('确认要删除该短信发送记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
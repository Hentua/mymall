<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>运费定义管理</title>
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
		<li class="active"><a href="${ctx}/member/memberLogisticsFee/">运费定义列表</a></li>
		<shiro:hasPermission name="member:memberLogisticsFee:edit"><li><a href="${ctx}/member/memberLogisticsFee/form">运费定义添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="memberLogisticsFee" action="${ctx}/member/memberLogisticsFee/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>id：</label>
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商铺ID：</label>
				<form:input path="merchantCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>省份ID：</label>
				<sys:treeselect id="province" name="province" value="${memberLogisticsFee.province}" labelName="" labelValue="${memberLogisticsFee.}"
					title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>update_date</th>
				<shiro:hasPermission name="member:memberLogisticsFee:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memberLogisticsFee">
			<tr>
				<td><a href="${ctx}/member/memberLogisticsFee/form?id=${memberLogisticsFee.id}">
					<fmt:formatDate value="${memberLogisticsFee.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="member:memberLogisticsFee:edit"><td>
    				<a href="${ctx}/member/memberLogisticsFee/form?id=${memberLogisticsFee.id}">修改</a>
					<a href="${ctx}/member/memberLogisticsFee/delete?id=${memberLogisticsFee.id}" onclick="return confirmx('确认要删除该运费定义吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
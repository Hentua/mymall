<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>佣金比例配置信息管理</title>
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
		<li class="active"><a href="${ctx}/commission/commissionConfig/">佣金比例配置</a></li>
		<shiro:hasPermission name="commission:commissionConfig:edit"></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="commissionConfig" action="${ctx}/commission/commissionConfig/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-medium"/>
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
				<th>佣金类型 </th>
				<th>佣金计算方式</th>
				<th>数值</th>
				<th>描述</th>
				<shiro:hasPermission name="commission:commissionConfig:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="commissionConfig">
			<tr>
				<td><a href="${ctx}/commission/commissionConfig/form?id=${commissionConfig.id}">
					${commissionConfig.title}
				</a></td>
				<td>
					<c:if test="${commissionConfig.type == '1'}">
						推荐用户消费返佣
					</c:if>
					<c:if test="${commissionConfig.type == '2'}">
						推荐商家销售返佣
					</c:if>
					<c:if test="${commissionConfig.type == '3'}">
						推荐商家入驻返佣
					</c:if>
					<c:if test="${commissionConfig.type == '4'}">
						推荐商家送出礼包返佣
					</c:if>
					<c:if test="${commissionConfig.type == '5'}">
						商家送出礼包返佣
					</c:if>
				</td>
				<td>
					<c:if test="${commissionConfig.mode == '1'}">
						固定金额
					</c:if>
					<c:if test="${commissionConfig.mode == '2'}">
						总额百分比
					</c:if>
				</td>
				<td>
					${commissionConfig.number}
				</td>
				<td>
					${commissionConfig.remarkes}
				</td>
				<shiro:hasPermission name="commission:commissionConfig:edit"><td>
    				<a href="${ctx}/commission/commissionConfig/form?id=${commissionConfig.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>店铺信息审核管理</title>
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
		<li class="active"><a href="${ctx}/member/merchantInfoCheck/">店铺信息审核列表</a></li>
		<shiro:hasPermission name="member:merchantInfoCheck:edit"><li><a href="${ctx}/member/merchantInfoCheck/form">店铺信息审核添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantInfoCheck" action="${ctx}/member/merchantInfoCheck/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>店铺名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>客服电话：</label>
				<form:input path="merchantServicePhone" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>审核状态1：待审核 2：已审核 3:已驳回：</label>
				<form:radiobuttons path="checkStatus" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li><label>审核备注：</label>
				<form:input path="checkRemark" htmlEscape="false" maxlength="500" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>id</th>
				<th>商家id</th>
				<th>店铺名称</th>
				<th>头像地址</th>
				<th>头图</th>
				<th>客服电话</th>
				<th>审核状态1：待审核 2：已审核 3:已驳回</th>
				<th>审核备注</th>
				<shiro:hasPermission name="member:merchantInfoCheck:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantInfoCheck">
			<tr>
				<td><a href="${ctx}/member/merchantInfoCheck/form?id=${merchantInfoCheck.id}">
					${merchantInfoCheck.id}
				</a></td>
				<td>
					${merchantInfoCheck.merchantId}
				</td>
				<td>
					${merchantInfoCheck.merchantName}
				</td>
				<td>
					${merchantInfoCheck.avatar}
				</td>
				<td>
					${merchantInfoCheck.merchantHeadImg}
				</td>
				<td>
					${merchantInfoCheck.merchantServicePhone}
				</td>
				<td>
					${fns:getDictLabel(merchantInfoCheck.checkStatus, '', '')}
				</td>
				<td>
					${merchantInfoCheck.checkRemark}
				</td>
				<shiro:hasPermission name="member:merchantInfoCheck:edit"><td>
    				<a href="${ctx}/member/merchantInfoCheck/form?id=${merchantInfoCheck.id}">修改</a>
					<a href="${ctx}/member/merchantInfoCheck/delete?id=${merchantInfoCheck.id}" onclick="return confirmx('确认要删除该店铺信息审核吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
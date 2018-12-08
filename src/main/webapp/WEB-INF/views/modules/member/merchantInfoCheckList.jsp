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
		<li class="active"><a href="${ctx}/member/merchantInfoCheck/list">店铺信息审核列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="merchantInfoCheck" action="${ctx}/member/merchantInfoCheck/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>店铺名称：</label>
				<form:input path="merchantName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>客服电话：</label>
				<form:input path="merchantServicePhone" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>用户账号</th>
			<th>用户名称</th>
			<th>店铺名称</th>
			<th>头像地址</th>
			<th>头图</th>
			<th>客服电话</th>
			<th>审核状态</th>
			<th>审核备注</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="merchantInfoCheck">
			<tr>
				<td>
						${merchantInfoCheck.userMobile}
				</td>
				<td>
						${merchantInfoCheck.userName}
				</td>
				<td>
						${merchantInfoCheck.merchantName}
				</td>
				<td>
					<a href="${merchantInfoCheck.avatar}"  target="_blank">
						<img src="${merchantInfoCheck.avatar}" width="50">
					</a>

				</td>
				<td>
					<a href="${merchantInfoCheck.merchantHeadImg}"  target="_blank">
						<img src="${merchantInfoCheck.merchantHeadImg}" width="50">
					</a>
				</td>
				<td>
						${merchantInfoCheck.merchantServicePhone}
				</td>
				<td>
					<c:if test="${merchantInfoCheck.checkStatus == '1'}">待审核</c:if>
					<c:if test="${merchantInfoCheck.checkStatus == '2'}">已审核</c:if>
					<c:if test="${merchantInfoCheck.checkStatus == '3'}">已驳回</c:if>
				</td>
				<td>
						${merchantInfoCheck.checkRemark}
				</td>
				<td>
					<c:if test="${merchantInfoCheck.checkStatus == '1'}">
						<a href="${ctx}/member/merchantInfoCheck/checkform?id=${merchantInfoCheck.id}">审核</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
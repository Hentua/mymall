<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员审核</title>
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
		<li class="active"><a href="${ctx}/member/memberInfo/memberInfoCheckList/">会员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="memberInfo" action="${ctx}/member/memberInfo/memberInfoCheckList/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>昵称：</label>
				<form:input path="nickname" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>注册途径：</label>
				<form:select path="registerWay" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="自主注册"/>
					<form:option value="1" label="后台添加"/>
				</form:select>
			</li>
			<li><label>会员状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="审核中"/>
					<form:option value="1" label="已生效"/>
					<form:option value="2" label="审核未通过"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>昵称</th>
				<th>注册途径</th>
				<th>注册时间</th>
				<th>会员状态</th>
				<th>推荐人</th>
				<th>备注</th>
				<shiro:hasPermission name="member:memberInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memberInfo">
			<tr>
				<td>
					${memberInfo.nickname}
				</td>
				<td>
					${memberInfo.registerWay eq '0' ? '自主注册' : '后台添加'}
				</td>
				<td>
					<fmt:formatDate value="${memberInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:choose>
						<c:when test="${memberInfo.status == '0'}">
							审核中
						</c:when>
						<c:when test="${memberInfo.status == '1'}">
							已生效
						</c:when>
						<c:when test="${memberInfo.status == '2'}">
							审核未通过
						</c:when>
					</c:choose>
				</td>
				<td>
					${memberInfo.refereeName}
				</td>
				<td>
					${memberInfo.remarks}
				</td>
				<shiro:hasPermission name="member:memberInfo:edit"><td>
    				<c:if test="${memberInfo.status == '0'}">
						<a href="${ctx}/member/memberInfo/checkPass?id=${memberInfo.id}">审核通过</a>
						<a href="${ctx}/member/memberInfo/checkReject?id=${memberInfo.id}">审核不通过</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
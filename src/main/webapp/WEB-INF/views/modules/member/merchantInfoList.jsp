<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
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
		<li class="active"><a href="${ctx}/member/memberInfo/merchantList">商户列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="memberInfo" action="${ctx}/member/memberInfo/merchantList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>昵称：</label>
				<form:input path="nickname" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>手机号码：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>注册途径：</label>
				<form:select path="registerWay" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="自主注册"/>
					<form:option value="1" label="后台添加"/>
				</form:select>
			</li>
			<li><label>审核状态：</label>
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
				<th>商户昵称</th>
				<th>商户账号</th>
				<th>商户类型</th>
				<th>注册途径</th>
				<th>注册时间</th>
				<th>商户审核状态</th>
				<th>登录状态</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memberInfo">
			<tr>
				<td>
					${memberInfo.nickname}
				</td>
				<td>
					${memberInfo.mobile}
				</td>
				<td>
					<c:choose>
						<c:when test="${memberInfo.merchantType == '0'}">
							推广者
						</c:when>
						<c:when test="${memberInfo.merchantType == '1'}">
							商户
						</c:when>
					</c:choose>
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
							未提交
						</c:when>
						<c:when test="${memberInfo.status == '1'}">
							已生效
						</c:when>
						<c:when test="${memberInfo.status == '2'}">
							审核未通过
						</c:when>
						<c:when test="${memberInfo.status == '3'}">
							审核中
						</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${memberInfo.loginFlag == '0'}">
							禁止登录
						</c:when>
						<c:when test="${memberInfo.loginFlag == '1'}">
							允许登录
						</c:when>
					</c:choose>
				</td>
				<td>
					${memberInfo.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
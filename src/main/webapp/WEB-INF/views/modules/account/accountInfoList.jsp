<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户明细</title>
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
	<style>
		.sts_tab{text-align: center;font-weight: bold;font-size: 15px}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/account/accountInfo/">账户明细列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="accountInfo" action="${ctx}/account/accountInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>收支类型：</label>
				<form:select path="type" cssStyle="width: 170px">
					<form:option value="">全部</form:option>
					<form:option value="1">收入</form:option>
					<form:option value="2">支出</form:option>
				</form:select>
			</li>
			<li><label>收支方式：</label>
				<form:select path="way" cssStyle="width: 170px">
					<form:option value="">全部</form:option>
					<form:option value="1">佣金收益</form:option>
					<form:option value="2">销售收益</form:option>
					<form:option value="3">提现</form:option>
					<form:option value="4">结算</form:option>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" cssStyle="width: 170px">
					<form:option value="">全部</form:option>
					<form:option value="1">已到账</form:option>
					<form:option value="2">未到账</form:option>
					<form:option value="3">未提现结算</form:option>
					<form:option value="4">已提现结算</form:option>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<%--//收入 income 支出 expenditure 未到账 outAccount--%>
	<table width="100%" class="sts_tab">
		<tr>
			<td colspan="3">余额：${stsInfo.income - stsInfo.expenditure - stsInfo.outAccount}</td>
		</tr>
		<tr>
			<td width="33.33%">收入：${stsInfo.income}</td><td width="33.33%">支出：${stsInfo.expenditure}</td><td width="33.33%">未到账：${stsInfo.outAccount}</td>
		</tr>
	</table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>收支类型</th>
				<th>收支方式</th>
				<th>金额</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>到账时间</th>
				<shiro:hasPermission name="account:accountInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accountInfo">
			<tr>
				<td>
					<c:if test="${accountInfo.type == 1}">
						收入
					</c:if>
					<c:if test="${accountInfo.type == 2}">
						支出
					</c:if>
				</td>
				<td>
					<c:if test="${accountInfo.way == 1}">
						佣金收益【${accountInfo.commissionInfo.typeText}】
					</c:if>
					<c:if test="${accountInfo.way == 2}">
						销售收益
					</c:if>
					<c:if test="${accountInfo.way == 3}">
						提现
					</c:if>
					<c:if test="${accountInfo.way == 4}">
						结算
					</c:if>
				</td>
				<td>${accountInfo.amount}</td>
				<td>
					<c:if test="${accountInfo.status == 1}">
						已到账
					</c:if>
					<c:if test="${accountInfo.status == 2}">
						未到账
					</c:if>
					<c:if test="${accountInfo.status == 3}">
						未提现结算
					</c:if>
					<c:if test="${accountInfo.status == 4}">
						已提现结算
					</c:if>
					<c:if test="${accountInfo.status == 5}">
						已结算
					</c:if>
				</td>
				<td><fmt:formatDate value="${accountInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${accountInfo.toAccountDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="account:accountInfo:edit"><td>
					<c:if test="${accountInfo.way == 1 && accountInfo.status == 1 && accountInfo.isSub == 0}">
						<a href="${ctx}/account/accountInfo/updateStatus?id=${accountInfo.id}&status=11" onclick="return confirmx('确认要提现该笔收益吗？', this.href)">提现</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
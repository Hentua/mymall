<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>充值提现审核</title>
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
		<li  ><a href="${ctx}/sys/user/merchantInfo">首页</a></li>
		<li class="active"><a href="${ctx}/account/accountFlow/merchantList">余额明细</a></li>
		<li ><a href="${ctx}/account/accountFlow/recharge">打款充值</a></li>
		<li  ><a href="${ctx}/account/accountFlow/merchantRechargeFlow">打款充值记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="accountFlow" action="${ctx}/account/accountFlow/merchantList" method="post" class="breadcrumb form-search">
		<input id="userId" name="userId" type="hidden" value="${accountFlow.userId}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<input id="org" name="org" type="hidden" value="13"/>--%>
		<ul class="ul-form">
			<li><label>类型：</label>
				<form:select path="type" cssStyle="width: 170px">
					<form:option value="">全部</form:option>
					<form:option value="1">收入</form:option>
					<form:option value="2">支出</form:option>
				</form:select>
			</li>
			<li><label>用户名称：</label>
				<form:input path="nickname" htmlEscape="false" maxlength="100" class="input-medium" />
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流水单号</th>
				<th>用户名称</th>
				<th>金额</th>
				<th>类型</th>
				<th>操作时间</th>
				<%--<th>转账时间</th>--%>
				<%--<th>平台收款账户</th>--%>
				<%--<th>转账银行账户</th>--%>
				<%--<th>开户人名称</th>--%>
				<%--<th>开户行地址</th>--%>
				<%--<th>审核状态</th>--%>
				<%--<shiro:hasPermission name="account:accountFlow:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accountFlow">
			<tr>
				<td>1
					${accountFlow.flowNo}
				 </td>
				<td>
					${accountFlow.nickname}
				</td>
				<td>
					${accountFlow.amount}
				</td>
				<td>
					<c:if test="${accountFlow.type == '1'}">
						<span style="color:#4cab0b">收入</span>-<c:if test="${accountFlow.mode == '1'}">
						<c:if test="${accountFlow.incomeExpenditureMode == '1'}">微信充值</c:if>
						<c:if test="${accountFlow.incomeExpenditureMode == '2'}">银行转账充值</c:if>
					</c:if>
					</c:if>
					<c:if test="${accountFlow.mode == '2'}">佣金转余额</c:if>
					<c:if test="${accountFlow.type == '2'}">
						<span style="color:#ff0f1e">支出</span>
						-<c:if test="${accountFlow.mode == '3'}">提现</c:if>
						<c:if test="${accountFlow.mode == '4'}">
							<c:if test="${accountFlow.paymentType == '0'}">
								订单消费
							</c:if>
							<c:if test="${accountFlow.paymentType == '1'}">
								礼包消费
							</c:if>
							${accountFlow.paymentType}
						</c:if>
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${accountFlow.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td><fmt:formatDate value="${accountFlow.transferDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>--%>
				<%--<td>${accountFlow.platBankAccount}</td>--%>
				<%--<td>转账银行账户</td>--%>
				<%--<td>开户人名称</td>--%>
				<%--<td>开户行地址</td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单结算管理</title>
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
		<li class="active"><a href="${ctx}/order/orderSettlement/list">订单结算列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="orderSettlement" action="${ctx}/order/orderSettlement/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>结算用户：</label>
				<form:input path="userId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>结算状态 ：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="1" label="未清算" />
					<form:option value="2" label="已清算" />
					<form:option value="3" label="已结算" />
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orderSettlement.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>结算时间：</label>
				<input name="settlementDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orderSettlement.settlementDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>结算用户</th>
				<th>结算金额</th>
				<th>结算状态 </th>
				<th>创建时间</th>
				<th>结算时间</th>
				<th>结算人</th>
				<shiro:hasPermission name="order:orderSettlement:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderSettlement">
			<tr>
				<td>
					${orderSettlement.userId}
				</td>
				<td>
					${orderSettlement.settlementAmount}
				</td>
				<td>
					<c:if test="${orderSettlement.status == '1'}">
						未清算1
					</c:if>
					<c:if test="${orderSettlement.status == '2'}">
						已清算2
					</c:if>
					<c:if test="${orderSettlement.status == '3'}">
						已结算
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${orderSettlement.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${orderSettlement.settlementDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${orderSettlement.settlementUserId}
				</td>
				<shiro:hasPermission name="order:orderSettlement:edit"><td>
					<c:if test="${orderSettlement.status == '2'}">
						<a href="${ctx}/order/orderSettlement/updateStatus?id=${orderSettlement.id}" onclick="return confirmx('确认要结算该笔信息吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
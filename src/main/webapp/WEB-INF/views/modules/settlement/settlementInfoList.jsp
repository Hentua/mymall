<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>提现结算信息管理</title>
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
		<li class="active"><a href="${ctx}/settlement/settlementInfo/">提现结算信息列表</a></li>
		<shiro:hasPermission name="settlement:settlementInfo:edit"><li><a href="${ctx}/settlement/settlementInfo/form">提现结算信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="settlementInfo" action="${ctx}/settlement/settlementInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>提现结算类型 （1：佣金提现 2：订单交易结算）：</label>
				<form:input path="type" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>关联单号（佣金明细ID 订单ID）：</label>
				<form:input path="unionId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>结算金额：</label>
				<form:input path="amount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>结算审核状态 1：待提交 2:已审核 3：已结算：</label>
				<form:input path="status" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>提交人：</label>
				<form:input path="subUserId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>提交时间：</label>
				<input name="subDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${settlementInfo.subDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>审核人：</label>
				<form:input path="auditUserId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>审核时间：</label>
				<input name="auditDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${settlementInfo.auditDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>结算人：</label>
				<form:input path="settlementUserId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>结算时间：</label>
				<input name="settlementDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${settlementInfo.settlementDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>提现结算类型 （1：佣金提现 2：订单交易结算）</th>
				<th>关联单号（佣金明细ID 订单ID）</th>
				<th>结算金额</th>
				<th>结算审核状态 1：待提交 2:已审核 3：已结算</th>
				<th>提交人</th>
				<th>提交时间</th>
				<th>审核人</th>
				<th>审核时间</th>
				<th>审核备注</th>
				<th>结算人</th>
				<th>结算时间</th>
				<th>结束备注</th>
				<shiro:hasPermission name="settlement:settlementInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settlementInfo">
			<tr>
				<td><a href="${ctx}/settlement/settlementInfo/form?id=${settlementInfo.id}">
					${settlementInfo.type}
				</a></td>
				<td>
					${settlementInfo.unionId}
				</td>
				<td>
					${settlementInfo.amount}
				</td>
				<td>
					${settlementInfo.status}
				</td>
				<td>
					${settlementInfo.subUserId}
				</td>
				<td>
					<fmt:formatDate value="${settlementInfo.subDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${settlementInfo.auditUserId}
				</td>
				<td>
					<fmt:formatDate value="${settlementInfo.auditDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${settlementInfo.auditRemarks}
				</td>
				<td>
					${settlementInfo.settlementUserId}
				</td>
				<td>
					<fmt:formatDate value="${settlementInfo.settlementDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${settlementInfo.settlementRemarks}
				</td>
				<shiro:hasPermission name="settlement:settlementInfo:edit"><td>
    				<a href="${ctx}/settlement/settlementInfo/form?id=${settlementInfo.id}">修改</a>
					<a href="${ctx}/settlement/settlementInfo/delete?id=${settlementInfo.id}" onclick="return confirmx('确认要删除该提现结算信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
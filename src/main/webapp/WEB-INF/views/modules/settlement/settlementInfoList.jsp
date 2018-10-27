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
	</ul>
	<form:form id="searchForm" modelAttribute="settlementInfo" action="${ctx}/settlement/settlementInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类型：</label>
				<form:select path="type" cssStyle="width: 170px">
					<form:option value="">全部</form:option>
					<form:option value="1">佣金提现</form:option>
					<form:option value="2">订单交易结算</form:option>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" cssStyle="width: 170px">
					<form:option value="">全部</form:option>
					<form:option value="1">待提交</form:option>
					<form:option value="2">待结算</form:option>
					<form:option value="3">已结算</form:option>
				</form:select>
			</li>
			<li><label>提交人账号：</label>
				<form:input path="subUserId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>提交时间：</label>
				<input name="subDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${settlementInfo.subDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>-
				<input name="subDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${settlementInfo.subDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>结算时间：</label>
				<input name="settlementDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${settlementInfo.settlementDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>-
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
				<th>提现结算类型</th>
				<th>结算审核状态</th>
				<th>结算金额</th>
				<th>提交人</th>
				<th>提交时间</th>
				<th>结算人</th>
				<th>结算时间</th>
				<th>结束备注</th>
				<shiro:hasPermission name="settlement:settlementInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="settlementInfo">
			<tr>
				<td>
					<c:if test="${settlementInfo.type == 1}">
						佣金提现
					</c:if>
					<c:if test="${settlementInfo.type == 2}">
						订单交易结算
					</c:if>
				</td>
				<td>
					<c:if test="${settlementInfo.status == 1}">
						待提交
					</c:if>
					<c:if test="${settlementInfo.status == 2}">
						待结算
					</c:if>
					<c:if test="${settlementInfo.status == 3}">
						已结算
					</c:if>
				</td>
				<td>
					${settlementInfo.amount}
				</td>
				<td>
					<c:if test="${empty settlementInfo.subUserId}">
						系统生成
					</c:if>
				</td>
				<td>
					<c:if test="${empty settlementInfo.subDate}">
						系统生成
					</c:if>
					<fmt:formatDate value="${settlementInfo.subDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
					<c:if test="${settlementInfo.status == 1}">
						<a href="${ctx}/settlement/settlementInfo/form?id=${settlementInfo.id}" onclick="return confirmx('确认要提交该笔信息吗？', this.href)">提交</a>
					</c:if>
					<c:if test="${settlementInfo.status == 2}">
						<a href="${ctx}/settlement/settlementInfo/form?id=${settlementInfo.id}" onclick="return confirmx('确认要结算该笔信息吗？', this.href)">结算</a>
					</c:if>
					<a href="${ctx}/settlement/settlementInfo/form?id=${settlementInfo.id}"  >查看详情</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
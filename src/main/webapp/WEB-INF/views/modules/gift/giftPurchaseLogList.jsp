<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包购买记录管理</title>
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
		function exportData() {
			window.open('${ctx}/gift/giftPurchaseLog/exportData?' + $('#searchForm').serialize());
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/gift/giftPurchaseLog/">礼包购买记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="giftPurchaseLog" action="${ctx}/gift/giftPurchaseLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>礼包名称：</label>
				<form:input path="giftConfigCategoryName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>订单号：</label>
				<form:input path="paymentNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>购买时间：</label>
				<input name="startDate" id="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${giftPurchaseLog.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endDate" id="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${giftPurchaseLog.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>支付时间：</label>
				<input name="startDate" id="startPayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${giftPurchaseLog.startPayDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endDate" id="endPayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${giftPurchaseLog.endPayDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单号</th>
				<th>礼包名称</th>
				<th>购买数量</th>
				<th>单价</th>
				<th>总价</th>
				<th>支付渠道</th>
				<th>购买时间</th>
				<th>支付时间</th>
				<th>购买状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftPurchaseLog">
			<tr>
				<td>
					${giftPurchaseLog.paymentNo}
				</td>
				<td>
					<a href="${ctx}/gift/giftConfigCategory/giftConfigCategoryDetail?id=${giftPurchaseLog.giftCategory}">${giftPurchaseLog.giftConfigCategoryName}</a>
				</td>
				<td>
					${giftPurchaseLog.giftCount}
				</td>
				<td>
					${giftPurchaseLog.giftPrice}
				</td>
				<td>
					${giftPurchaseLog.giftAmountTotal}
				</td>
				<td>
					${giftPurchaseLog.payChannelZh}
				</td>
				<td>
					<fmt:formatDate value="${giftPurchaseLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${giftPurchaseLog.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${giftPurchaseLog.statusZh}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
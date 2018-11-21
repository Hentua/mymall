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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/gift/giftPurchaseLog/checkList">礼包购买审核</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="giftPurchaseLog" action="${ctx}/gift/giftPurchaseLog/checkList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>礼包名称：</label>
				<form:input path="giftConfigCategoryName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>订单号：</label>
				<form:input path="paymentNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单号</th>
				<th>礼包名称</th>
				<th>购买商户</th>
				<th>购买数量</th>
				<th>单价</th>
				<th>总价</th>
				<th>支付渠道</th>
				<th>购买时间</th>
				<th>支付时间</th>
				<th>购买状态</th>
				<%--<shiro:hasPermission name="gift:giftPurchaseLog:edit"><th>操作</th></shiro:hasPermission>--%>
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
					${giftPurchaseLog.merchantName}
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
				<%--<shiro:hasPermission name="gift:giftPurchaseLog:edit"><td>--%>
					<%--<c:if test="${giftPurchaseLog.status == '0' && giftPurchaseLog.payChannel == '2'}">--%>
						<%--<a href="${ctx}/gift/giftPurchaseLog/checkPass?id=${giftPurchaseLog.id}">审核通过</a>--%>
						<%--<a href="${ctx}/gift/giftPurchaseLog/checkNotPass?id=${giftPurchaseLog.id}">审核不通过</a>--%>
					<%--</c:if>--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
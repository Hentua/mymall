<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单信息管理</title>
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
		<li class="active"><a href="${ctx}/order/orderInfo/merchantList">订单信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="orderInfo" action="${ctx}/order/orderInfo/merchantList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单号：</label>
				<form:input path="orderNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>订单状态：</label>
				<form:select path="orderStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="待付款"/>
					<form:option value="1" label="待发货"/>
					<form:option value="2" label="待收货"/>
					<form:option value="3" label="交易完成"/>
					<form:option value="4" label="交易关闭"/>
					<form:option value="5" label="退款申请"/>
					<form:option value="6" label="已退款"/>
				</form:select>
			</li>
			<li><label>订单类型：</label>
				<form:select path="orderType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="自主下单"/>
					<form:option value="1" label="礼包兑换"/>
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
				<th>订单号</th>
				<th>订单状态</th>
				<th>订单类型</th>
				<th>商品总数量</th>
				<th>商品总金额</th>
				<th>运费</th>
				<th>应付金额</th>
				<th>优惠金额</th>
				<th>下单人</th>
				<th>下单时间</th>
				<th>备注</th>
				<th>支付时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderInfo">
			<tr>
				<td>
					${orderInfo.orderNo}
				</td>
				<td>
					${orderInfo.orderStatusZh}
				</td>
				<td>
					<c:choose>
						<c:when test="${orderInfo.orderType eq '0'}">
							自主下单
						</c:when>
						<c:when test="${orderInfo.orderType eq '1'}">
							礼包兑换
						</c:when>
					</c:choose>
				</td>
				<td>
					${orderInfo.goodsCount}
				</td>
				<td>
					${orderInfo.goodsAmountTotal}
				</td>
				<td>
					${orderInfo.logisticsFee}
				</td>
				<td>
					${orderInfo.orderAmountTotal}
				</td>
				<td>
					${orderInfo.discountAmountTotal}
				</td>
				<td>
					${orderInfo.customerName}
				</td>
				<td>
					<fmt:formatDate value="${orderInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${orderInfo.remarks}
				</td>
				<td>
					<fmt:formatDate value="${orderInfo.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="order:orderInfo:edit"><td>
					<a href="${ctx}/order/orderInfo/orderDelivery?id=${orderInfo.id}">详情</a>
    				<c:if test="${orderInfo.orderStatus eq '1'}">
						<a href="${ctx}/order/orderInfo/orderDelivery?id=${orderInfo.id}">发货</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
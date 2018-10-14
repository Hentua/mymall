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
		<li class="active"><a href="${ctx}/order/orderInfo/">订单信息列表</a></li>
		<shiro:hasPermission name="order:orderInfo:edit"><li><a href="${ctx}/order/orderInfo/form">订单信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderInfo" action="${ctx}/order/orderInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单号</th>
				<th>卖家店铺ID</th>
				<th>订单状态（0-待付款，1-已付款，2-已取消，3-退款申请，4-已退款）</th>
				<th>订单类型（0-平台自主下单，1-礼包兑换）</th>
				<th>支付渠道（0-微信支付，1-礼包）</th>
				<th>商品总数量</th>
				<th>订单商品总金额</th>
				<th>运费</th>
				<th>订单应付总金额</th>
				<th>订单优惠扣减金额</th>
				<th>支付渠道返回的订单号</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th>买家ID</th>
				<th>备注</th>
				<th>平台记录的支付时间</th>
				<th>支付渠道记录的支付时间</th>
				<shiro:hasPermission name="order:orderInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderInfo">
			<tr>
				<td><a href="${ctx}/order/orderInfo/form?id=${orderInfo.id}">
					${orderInfo.orderNo}
				</a></td>
				<td>
					${orderInfo.merchantCode}
				</td>
				<td>
					${orderInfo.orderStatus}
				</td>
				<td>
					${orderInfo.orderType}
				</td>
				<td>
					${orderInfo.payChannel}
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
					${orderInfo.payChannelNo}
				</td>
				<td>
					<fmt:formatDate value="${orderInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${orderInfo.createBy.id}
				</td>
				<td>
					${orderInfo.customerCode}
				</td>
				<td>
					${orderInfo.remarks}
				</td>
				<td>
					<fmt:formatDate value="${orderInfo.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${orderInfo.payChannelTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="order:orderInfo:edit"><td>
    				<a href="${ctx}/order/orderInfo/form?id=${orderInfo.id}">修改</a>
					<a href="${ctx}/order/orderInfo/delete?id=${orderInfo.id}" onclick="return confirmx('确认要删除该订单信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
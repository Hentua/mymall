<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包购买记录</title>
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
		<li class="active"><a href="${ctx}/gift/giftSaleLog/buyList">礼包购买记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="giftSaleLog" action="${ctx}/gift/giftSaleLog/buyList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>购买订单号：</label>
				<form:input path="orderNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>订单状态：</label>
				<form:select path="flag" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="待审核"/>
					<form:option value="1" label="购买成功"/>
					<form:option value="2" label="已取消"/>
				</form:select>
			</li>
			<li><label>礼包名称：</label>
				<form:input path="giftName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>购买订单号</th>
				<th>礼包名称</th>
				<th>订单状态</th>
				<th>礼包价格</th>
				<th>单个礼包商品数量</th>
				<th>购买数量</th>
				<th>总金额</th>
				<th>最后更新时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftSaleLog">
			<tr>
				<td>
					${giftSaleLog.orderNo}
				</td>
				<td>
					<a href="${ctx}/gift/giftConfig/giftDetail?id=${giftSaleLog.giftConfigId}">${giftSaleLog.giftName}</a>
				</td>
				<td>
					<c:if test="${giftSaleLog.flag == '0'}">
						待审核
					</c:if>
					<c:if test="${giftSaleLog.flag == '1'}">
						购买成功
					</c:if>
					<c:if test="${giftSaleLog.flag == '2'}">
						已作废
					</c:if>
				</td>
				<td>
					${giftSaleLog.giftPrice}
				</td>
				<td>
					${giftSaleLog.giftGoodsCount}
				</td>
				<td>
					${giftSaleLog.giftCount}
				</td>
				<td>
					${giftSaleLog.giftAmountTotal}
				</td>
				<td>
					<fmt:formatDate value="${giftSaleLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
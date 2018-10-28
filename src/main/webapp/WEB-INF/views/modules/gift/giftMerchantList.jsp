<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包列表管理</title>
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
		<li class="active"><a href="${ctx}/gift/giftMerchant/">礼包列表列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="giftMerchant" action="${ctx}/gift/giftMerchant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>礼包名称：</label>
				<form:input path="giftName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>购买时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${giftMerchant.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${giftMerchant.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>礼包名称</th>
				<th>现价</th>
				<th>单个礼包商品数量</th>
				<th>礼包数量</th>
				<th>购买时间</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftMerchant">
			<tr>
				<td><a href="${ctx}/gift/giftConfig/giftDetail?id=${giftMerchant.giftConfigId}">
					${giftMerchant.giftName}
				</a></td>
				<td>
					${giftMerchant.giftPrice}
				</td>
				<td>
					${giftMerchant.goodsCount}
				</td>
				<td>
					${giftMerchant.giftCount}
				</td>
				<td>
					<fmt:formatDate value="${giftMerchant.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${giftMerchant.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
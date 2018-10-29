<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包赠送记录</title>
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
		<li class="active"><a href="${ctx}/gift/giftGiveLog/">礼包赠送记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="giftGiveLog" action="${ctx}/gift/giftGiveLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
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
				<th>礼包名称</th>
				<th>赠送会员</th>
				<th>单个礼包商品数量</th>
				<th>赠送数量</th>
				<th>赠送时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftGiveLog">
			<tr>
				<td><a href="${ctx}/gift/giftConfig/giftDetail?id=${giftGiveLog.giftConfigId}">
					${giftGiveLog.giftName}
				</a></td>
				<td>
					${giftGiveLog.customerName}
				</td>
				<td>
					${giftGiveLog.giftGoodsCount}
				</td>
				<td>
					${giftGiveLog.giftCount}
				</td>
				<td>
					<fmt:formatDate value="${giftGiveLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
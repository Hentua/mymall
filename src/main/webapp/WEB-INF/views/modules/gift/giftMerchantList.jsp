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
		<li class="active"><a href="${ctx}/gift/giftMerchant/">礼包列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="giftMerchant" action="${ctx}/gift/giftMerchant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>礼包名称：</label>
				<form:input path="giftConfigCategoryName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>购买时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${giftMerchant.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> -
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${giftMerchant.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
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
				<th>礼包数量</th>
				<th>已赠送数量</th>
				<th>可赠送数量</th>
				<th>获取时间</th>
				<th>赠送</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftMerchant">
			<tr>
				<td>
					<a href="${ctx}/gift/giftConfigCategory/giftConfigCategoryDetail?id=${giftMerchant.giftCategory}">${giftMerchant.giftConfigCategoryName}</a>
				</td>
				<td>
					${giftMerchant.giftCount}
				</td>
				<td>
					${giftMerchant.givenCount}
				</td>
				<td>
					${giftMerchant.stock}
				</td>
				<td>
					<fmt:formatDate value="${giftMerchant.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${giftMerchant.stock > 0}"><a href="${ctx}/member/memberInfo/checkPayPasswordForm?id=${fns:getUser().id}&failedCallbackUrl=${ctx}/gift/giftMerchant/&successCallbackUrl=${ctx}/gift/giftMerchant/giftTransferForm?id=${giftMerchant.id}" onclick="return confirmx('确认要赠送该礼包吗？', this.href)">赠送</a></c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包类别管理</title>
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
		<li class="active"><a href="${ctx}/gift/giftConfigCategory/buyList">礼包购买</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="giftConfigCategory" action="${ctx}/gift/giftConfigCategory/buyList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>礼包名称：</label>
				<form:input path="categoryName" htmlEscape="false" maxlength="20" class="input-medium"/>
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
				<th>礼包价格</th>
				<th>是否赠送商家资格</th>
				<th>定制礼包商家</th>
				<th>创建时间</th>
				<th>备注</th>
				<th>购买</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftConfigCategory">
			<tr>
				<td><a href="${ctx}/gift/giftConfigCategory/giftConfigCategoryDetail?id=${giftConfigCategory.id}">
					${giftConfigCategory.categoryName}
				</a></td>
				<td>
					${giftConfigCategory.giftPrice}
				</td>
				<td>
					<c:choose>
						<c:when test="${giftConfigCategory.merchantQualification == '0'}">否</c:when>
						<c:when test="${giftConfigCategory.merchantQualification == '1'}">是</c:when>
					</c:choose>
				</td>
				<td>
                    ${giftConfigCategory.merchantName}
				</td>
				<td>
					<fmt:formatDate value="${giftConfigCategory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${giftConfigCategory.remarks}
				</td>
				<td>
					<a href="${ctx}/gift/giftConfigCategory/giftConfigCategoryBuyDetail?id=${giftConfigCategory.id}" onclick="return confirmx('确认要购买该礼包吗？', this.href)">购买</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品评价管理</title>
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
		<li class="active"><a href="javascript:void(0)">商品评价列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsEvaluate" action="${ctx}/goods/goodsEvaluate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="goodsId" name="goodsId" type="hidden" value="${goodsEvaluate.goodsId}"/>

		<ul class="ul-form">
			<li><label>评价：</label>
				<form:input path="evaluate" htmlEscape="false" maxlength="500" class="input-medium"/>
			</li>
			<li><label>级别：</label>
				<form:select path="level">
					<form:option value="">全部</form:option>
					<form:option value="1">一星</form:option>
					<form:option value="2">二星</form:option>
					<form:option value="3">三星</form:option>
					<form:option value="4">四星</form:option>
					<form:option value="5">五星</form:option>
				</form:select>
			</li>
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>用户名称：</label>
				<form:input path="evaluateUserName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/> </li>
			<li class="btns"><input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> </li>

			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>评价</th>
				<th>级别</th>
				<th>标题</th>
				<th>商品名称</th>
				<th>评价时间</th>
				<th>评价用户名称</th>
				<%--<shiro:hasPermission name="goods:goodsEvaluate:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsEvaluate">
			<tr>
				<td>
					${goodsEvaluate.evaluate}
				</td>
				<td>
					${goodsEvaluate.level}
				</td>
				<td>
					${goodsEvaluate.title}
				</td>
				<td>
					${goodsEvaluate.goodsName}
				</td>
				<td>
					<fmt:formatDate value="${goodsEvaluate.evaluateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsEvaluate.evaluateUserName}
				</td>
				<%--<shiro:hasPermission name="goods:goodsEvaluate:edit"><td>--%>
    				<%--&lt;%&ndash;<a href="${ctx}/goods/goodsEvaluate/form?id=${goodsEvaluate.id}">修改</a>&ndash;%&gt;--%>
					<%--&lt;%&ndash;<a href="${ctx}/goods/goodsEvaluate/delete?id=${goodsEvaluate.id}" onclick="return confirmx('确认要删除该商品评价吗？', this.href)">删除</a>&ndash;%&gt;--%>
				<%--</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
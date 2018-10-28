<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包赠送记录管理</title>
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
		<li class="active"><a href="${ctx}/gift/giftGiveLog/">礼包赠送记录列表</a></li>
		<shiro:hasPermission name="gift:giftGiveLog:edit"><li><a href="${ctx}/gift/giftGiveLog/form">礼包赠送记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="giftGiveLog" action="${ctx}/gift/giftGiveLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>购买礼包配置ID：</label>
				<form:input path="giftConfigId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>购买商家礼包ID：</label>
				<form:input path="giftMerchantId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>收到礼包ID：</label>
				<form:input path="giftCustomerId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>赠送商家ID：</label>
				<form:input path="merchantCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>收到会员ID：</label>
				<form:input path="customerCode" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>购买礼包配置ID</th>
				<th>购买商家礼包ID</th>
				<th>收到礼包ID</th>
				<th>赠送商家ID</th>
				<th>收到会员ID</th>
				<th>赠送礼包商品数量</th>
				<th>赠送数量</th>
				<th>create_by</th>
				<th>create_date</th>
				<th>update_by</th>
				<th>update_date</th>
				<th>礼包名称</th>
				<shiro:hasPermission name="gift:giftGiveLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftGiveLog">
			<tr>
				<td><a href="${ctx}/gift/giftGiveLog/form?id=${giftGiveLog.id}">
					${giftGiveLog.giftConfigId}
				</a></td>
				<td>
					${giftGiveLog.giftMerchantId}
				</td>
				<td>
					${giftGiveLog.giftCustomerId}
				</td>
				<td>
					${giftGiveLog.merchantCode}
				</td>
				<td>
					${giftGiveLog.customerCode}
				</td>
				<td>
					${giftGiveLog.giftGoodsCount}
				</td>
				<td>
					${giftGiveLog.giftCount}
				</td>
				<td>
					${giftGiveLog.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${giftGiveLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${giftGiveLog.updateBy.name}
				</td>
				<td>
					<fmt:formatDate value="${giftGiveLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${giftGiveLog.giftName}
				</td>
				<shiro:hasPermission name="gift:giftGiveLog:edit"><td>
    				<a href="${ctx}/gift/giftGiveLog/form?id=${giftGiveLog.id}">修改</a>
					<a href="${ctx}/gift/giftGiveLog/delete?id=${giftGiveLog.id}" onclick="return confirmx('确认要删除该礼包赠送记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
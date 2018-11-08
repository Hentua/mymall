<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包配置管理</title>
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
		<li class="active"><a href="${ctx}/gift/giftConfig/">礼包配置列表</a></li>
		<shiro:hasPermission name="gift:giftConfig:edit"><li><a href="${ctx}/gift/giftConfig/form">礼包配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="giftConfig" action="${ctx}/gift/giftConfig/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>礼包名称：</label>
				<form:input path="giftName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>礼包分类：</label>
				<form:select path="giftCategory">
					<form:option value="" label="全部"/>
					<form:options items="${giftConfigCategoryList}" itemValue="id" itemLabel="categoryName"/>
				</form:select>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${giftConfig.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${giftConfig.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>礼包分类</th>
				<th>礼包价格</th>
				<th>优惠券数量</th>
				<th>商品总数量</th>
				<th>是否在APP显示商品价格</th>
				<th>创建时间</th>
				<th>最后修改时间</th>
				<th>备注</th>
				<shiro:hasPermission name="gift:giftConfig:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftConfig">
			<tr>
				<td><a href="${ctx}/gift/giftConfig/form?id=${giftConfig.id}">
					${giftConfig.giftName}
				</a></td>
				<td>
					${giftConfig.giftCategoryName}
				</td>
				<td>
					${giftConfig.giftCategoryPrice}
				</td>
				<td>
					${giftConfig.couponCount}
				</td>
				<td>
					${giftConfig.goodsCount}
				</td>
				<td>
					<c:choose>
						<c:when test="${giftConfig.showGoodsPrice == '0'}">否</c:when>
						<c:when test="${giftConfig.showGoodsPrice == '1'}">是</c:when>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${giftConfig.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${giftConfig.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${giftConfig.remarks}
				</td>
				<shiro:hasPermission name="gift:giftConfig:edit"><td>
    				<a href="${ctx}/gift/giftConfig/form?id=${giftConfig.id}">详情</a>
					<a href="${ctx}/gift/giftConfig/delete?id=${giftConfig.id}" onclick="return confirmx('确认要删除该礼包配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
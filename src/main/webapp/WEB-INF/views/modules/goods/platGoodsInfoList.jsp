<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品信息管理</title>
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
		<li class="active"><a href="${ctx}/goods/goodsInfo/platList">平台商品信息列表</a></li>
		<shiro:hasPermission name="goods:goodsInfo:edit"><li><a href="${ctx}/goods/goodsInfo/platForm">平台商品信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsInfo" action="${ctx}/goods/goodsInfo/platList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品分类：</label>
				<sys:treeselect notAllowSelectParent="true" id="goodsCategoryId" name="goodsCategoryId" value="${goodsCategory.parentCategoryId}" labelName="parentCategoryName" labelValue="${goodsCategory.parentCategoryName}"
								title="商品分类" url="/goods/goodsCategory/treeData" extId="${goodsCategory.id}" cssClass=""  />
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>商品标题：</label>
				<form:input path="goodsTitle" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>商品状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="1" label="待提交"/>
					<form:option value="2" label="待审核"/>
					<form:option value="3" label="已上架"/>
				</form:select>
			</li>
			<li><label>上架时间：</label>
				<input name="beginOnlinetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${goodsInfo.beginOnlinetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endOnlinetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${goodsInfo.endOnlinetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${goodsInfo.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${goodsInfo.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>商品名称</th>
				<th>商品分类</th>
				<th>商品标题</th>
				<th>单位</th>
				<th>商品价格</th>
				<th>销量</th>
				<th>上架时间</th>
				<th>创建时间</th>
				<th>商品状态</th>
				<shiro:hasPermission name="goods:goodsInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsInfo">
			<tr>
				<td>
					<a href="${ctx}/goods/goodsInfo/goodsDetail?id=${goodsInfo.id}">
					<img src="${goodsInfo.image}" width="50px">
						${goodsInfo.goodsName}
				</a></td>
				<td>
						${goodsInfo.goodsCategoryName}
				</td>
				<td>
					${goodsInfo.goodsTitle}
				</td>
				<td>
					${goodsInfo.unit}
				</td>
				<td>
					${goodsInfo.goodsPrice}
				</td>
				<td>
					${goodsInfo.salesTotal}
				</td>
				<td>
					<fmt:formatDate value="${goodsInfo.onlinetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${goodsInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${goodsInfo.status == '1'}">
						<span style="color: #ffc839">待提交</span>
					</c:if>
					<c:if test="${goodsInfo.status == '2'}">
						<span style="color: #ff0f1e">待审核</span>
					</c:if>
					<c:if test="${goodsInfo.status == '3'}">
						<span style="color: #4cab0b">已上架</span>
					</c:if>
				</td>
				<shiro:hasPermission name="goods:goodsInfo:edit"><td>
					<a href="${ctx}/goods/goodsEvaluate/list?goodsId=${goodsInfo.id}">查看评价</a>
					<c:if test="${goodsInfo.status == '1'}">
						<a href="${ctx}/goods/goodsInfo/updateStatus?id=${goodsInfo.id}&status=2">提交上架</a>
						<a href="${ctx}/goods/goodsInfo/platForm?id=${goodsInfo.id}">修改</a>
						<a href="${ctx}/goods/goodsInfo/delete?id=${goodsInfo.id}" onclick="return confirmx('确认要删除该商品信息吗？', this.href)">删除</a>
					</c:if>
					<c:if test="${goodsInfo.status == '2'}">
						<a href="${ctx}/goods/goodsInfo/updateStatus?id=${goodsInfo.id}&status=1" onclick="return confirmx('确认要下架该商品信息吗？', this.href)">下架</a>
					</c:if>
					<c:if test="${goodsInfo.status == '3'}">
						<a href="${ctx}/goods/goodsInfo/updateStatus?id=${goodsInfo.id}&status=1" onclick="return confirmx('确认要下架该商品信息吗？', this.href)">下架</a>
					</c:if>

				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
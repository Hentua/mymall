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
		<li class="active"><a href="${ctx}/goods/goodsInfo/">商品信息列表</a></li>
		<shiro:hasPermission name="goods:goodsInfo:edit"><li><a href="${ctx}/goods/goodsInfo/form">商品信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsInfo" action="${ctx}/goods/goodsInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品分类：</label>
			</li>
			<li><label>商品名称：</label>
				<form:input path="goodsName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>商品条码：</label>
				<form:input path="goodsBarcode" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>商品标题：</label>
				<form:input path="goodsTitle" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>商品类型：</label>
				<form:select path="goodsType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>商品状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>单位：</label>
				<form:input path="unit" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>商品原价：</label>
				<form:input path="originalPrice" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>商品现价：</label>
				<form:input path="goodsPrice" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>销量：</label>
				<form:input path="salesTotal" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>是否已经删除：</label>
				<form:select path="isDeleted" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>商品分类</th>
				<th>商品名称</th>
				<th>商品条码</th>
				<th>商品标题</th>
				<th>商品类型</th>
				<th>商品状态</th>
				<th>单位</th>
				<th>商品原价</th>
				<th>商品现价</th>
				<th>销量</th>
				<th>是否已经删除</th>
				<th>上架时间</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="goods:goodsInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsInfo">
			<tr>
				<td><a href="${ctx}/goods/goodsInfo/form?id=${goodsInfo.id}">
					${goodsInfo.goodsCategoryId}
				</a></td>
				<td>
					${goodsInfo.goodsName}
				</td>
				<td>
					${goodsInfo.goodsBarcode}
				</td>
				<td>
					${goodsInfo.goodsTitle}
				</td>
				<td>
					${fns:getDictLabel(goodsInfo.goodsType, '', '')}
				</td>
				<td>
					${fns:getDictLabel(goodsInfo.status, '', '')}
				</td>
				<td>
					${goodsInfo.unit}
				</td>
				<td>
					${goodsInfo.originalPrice}
				</td>
				<td>
					${goodsInfo.goodsPrice}
				</td>
				<td>
					${goodsInfo.salesTotal}
				</td>
				<td>
					${fns:getDictLabel(goodsInfo.isDeleted, '', '')}
				</td>
				<td>
					<fmt:formatDate value="${goodsInfo.onlinetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${goodsInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${goodsInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsInfo.remarks}
				</td>
				<shiro:hasPermission name="goods:goodsInfo:edit"><td>
    				<a href="${ctx}/goods/goodsInfo/form?id=${goodsInfo.id}">修改</a>
					<a href="${ctx}/goods/goodsInfo/delete?id=${goodsInfo.id}" onclick="return confirmx('确认要删除该商品信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
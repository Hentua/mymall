<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品选择</title>
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
	<form:form id="searchForm" modelAttribute="goodsInfo" action="${ctx}/goods/goodsInfo/selectList" method="post" class="breadcrumb form-search">
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
	已选择：<span id="selectedGoodsInfo"></span>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品名称</th>
				<th>商品分类</th>
				<th>商品标题</th>
				<th>商品状态</th>
				<th>单位</th>
				<th>商品规格</th>
				<th>销量</th>
				<th>上架时间</th>
				<th>创建时间</th>
				<th>选择</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsInfo">
			<tr>
				<td>
					<%--<a href="${ctx}/goods/goodsInfo/form?id=${goodsInfo.id}">--%>
					<%--<img src="${goodsInfo.fullImageUrl}" width="50px">--%>
						${goodsInfo.goodsName}
				<%--</a>--%></td>
				<td>
						${goodsInfo.goodsCategoryName}
				</td>
				<td>
					${goodsInfo.goodsTitle}
				</td>
				<td>
					<c:if test="${goodsInfo.status == '1'}">
						待提交
					</c:if>
					<c:if test="${goodsInfo.status == '2'}">
						待审核
					</c:if>
					<c:if test="${goodsInfo.status == '3'}">
						已上架
					</c:if>
				</td>
				<td>
					${goodsInfo.unit}
				</td>
				<td>
					${goodsInfo.standardName}
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
					<a href="javascript:void(0)" onclick="selectGoods('${goodsInfo.id}', '${goodsInfo.goodsName}', '${goodsInfo.merchantId}','${goodsInfo.image}', '${goodsInfo.standardId}','${goodsInfo.standardName}')">选择</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<input id="goodsId" type="hidden"/>
	<input id="goodsName" type="hidden"/>
	<input id="merchantCode" type="hidden"/>
	<input id="goodsImage" type="hidden"/>
	<input id="standardId" type="hidden"/>
	<input id="standardName" type="hidden"/>
	<script type="text/javascript">
		function selectGoods(id, name, merchantId,image,standardId,standardName) {
			$('#goodsId').val(id);
			$('#goodsName').val(name);
			$('#merchantCode').val(merchantId);
			$("#goodsImage").val(image);
            $("#standardId").val(standardId);
            $("#standardName").val(standardName);
			$('#selectedGoodsInfo').text(name);
        }
	</script>
	<div class="pagination">${page}</div>
</body>
</html>
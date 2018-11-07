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
		<shiro:hasPermission name="gift:giftMerchant:edit"><li><a href="${ctx}/gift/giftMerchant/form">礼包列表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="giftMerchant" action="${ctx}/gift/giftMerchant/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>礼包分类ID：</label>
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
				<th>礼包分类ID</th>
				<th>已经赠送数量</th>
				<th>礼包数量</th>
				<th>购买时间</th>
				<th>remarks</th>
				<shiro:hasPermission name="gift:giftMerchant:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftMerchant">
			<tr>
				<td><a href="${ctx}/gift/giftMerchant/form?id=${giftMerchant.id}">
					${giftMerchant.giftCategory}
				</a></td>
				<td>
					${giftMerchant.givenCount}
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
				<shiro:hasPermission name="gift:giftMerchant:edit"><td>
    				<a href="${ctx}/gift/giftMerchant/form?id=${giftMerchant.id}">修改</a>
					<a href="${ctx}/gift/giftMerchant/delete?id=${giftMerchant.id}" onclick="return confirmx('确认要删除该礼包列表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
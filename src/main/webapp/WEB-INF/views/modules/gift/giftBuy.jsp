<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包购买</title>
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
		<li class="active"><a href="${ctx}/gift/giftConfig/giftBuyList">礼包购买</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="giftConfig" action="${ctx}/gift/giftConfig/giftBuyList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>礼包名称：</label>
				<form:input path="giftName" htmlEscape="false" maxlength="20" class="input-medium"/>
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
				<th>原价</th>
				<th>现价</th>
				<th>商品总数量</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>最后更新人</th>
				<th>最后更新时间</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftConfig">
			<tr>
				<td>
					${giftConfig.giftName}
				</td>
				<td>
					${giftConfig.originalPrice}
				</td>
				<td>
					${giftConfig.giftPrice}
				</td>
				<td>
					${giftConfig.goodsCount}
				</td>
				<td>
					${giftConfig.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${giftConfig.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${giftConfig.updateBy.name}
				</td>
				<td>
					<fmt:formatDate value="${giftConfig.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${giftConfig.remarks}
				</td>
				<shiro:hasPermission name="gift:giftConfig:edit"><td>
    				<a href="${ctx}/gift/giftConfig/giftDetail?id=${giftConfig.id}">详情</a>
					<a href="${ctx}/gift/giftConfig/giftBuySubmit?id=${giftConfig.id}&buyCount=" onclick="return promptx('购买确认', '购买数量', this.href, '')">购买</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
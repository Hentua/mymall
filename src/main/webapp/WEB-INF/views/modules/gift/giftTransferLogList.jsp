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
		function exportData() {
			window.open('${ctx}/gift/giftTransferLog/exportData?' + $('#searchForm').serialize());
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/gift/giftTransferLog/">礼包赠送记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="giftTransferLog" action="${ctx}/gift/giftTransferLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>礼包名称：</label>
				<form:input path="giftConfigCategoryName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>赠送时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${giftTransferLog.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> -
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${giftTransferLog.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="已使用"/>
					<form:option value="1" label="未使用"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>礼包名称</th>
				<th>金额</th>
				<th>会员</th>
				<th>会员账号</th>
				<th>赠送时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="giftTransferLog">
			<tr>
				<td>
					<a href="${ctx}/gift/giftConfigCategory/giftConfigCategoryDetail?id=${giftTransferLog.giftCategory}">${giftTransferLog.giftConfigCategoryName}</a>
				</td>
				<td>
					${giftTransferLog.giftPrice}
				</td>
				<td>
					${giftTransferLog.customerName}
				</td>
				<td>
					${giftTransferLog.customerAccount}
				</td>
				<td>
					<fmt:formatDate value="${giftTransferLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:choose>
						<c:when test="${giftTransferLog.status == '0'}">
							已使用
						</c:when>
						<c:when test="${giftTransferLog.status == '1'}">
							未使用
						</c:when>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
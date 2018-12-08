<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包赠送记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#allCheck').click(function () {
				var isAllCheck = this.checked;
				$('input[name="itemId"]').each(function() {
					this.checked = isAllCheck;
				});
			})
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function exportData() {
			window.open('${ctx}/gift/giftTransferLog/operatorExportData?' + $('#searchForm').serialize() + itemCheckBoxVal());
		}
		function itemCheckBoxVal() {
			var itemStr = '';
			$('input[name="itemId"]:checked').each(function () {
				itemStr += '&itemIds=' + $(this).val();
			});
			return itemStr;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/gift/giftTransferLog/operatorList">礼包赠送记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="giftTransferLog" action="${ctx}/gift/giftTransferLog/operatorList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商家账号：</label>
				<form:input path="merchantAccount" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>会员账号：</label>
				<form:input path="customerAccount" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>礼包名称：</label>
				<form:select path="giftConfigCategoryName" class="input-xlarge ">
					<form:options items="giftConfigCategoryList" itemLabel="categoryName" itemValue="categoryName"
								  htmlEscape="false"/>
				</form:select>
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
				<th><input type="checkbox" id="allCheck"/></th>
				<th>礼包名称</th>
				<th>商家</th>
				<th>商家账号</th>
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
					<input type="checkbox" name="itemId" value="${giftTransferLog.id}"/>
				</td>
				<td>
					<a href="${ctx}/gift/giftConfigCategory/giftConfigCategoryDetail?id=${giftTransferLog.giftCategory}">${giftTransferLog.giftConfigCategoryName}</a>
				</td>
				<td>
					${giftTransferLog.merchantName}
				</td>
				<td>
					${giftTransferLog.merchantAccount}
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员反馈信息管理</title>
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
			window.open('${ctx}/member/memberFeedback/exportData?' + $('#searchForm').serialize());
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/member/memberFeedback/">会员反馈信息</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="memberFeedback" action="${ctx}/member/memberFeedback/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员昵称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>会员账号：</label>
				<form:input path="customerAccount" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>反馈时间：</label>
				<input name="startDate" id="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${memberFeedback.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endDate" id="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${memberFeedback.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="未处理" />
					<form:option value="1" label="已处理" />
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
				<th>会员昵称</th>
				<th>会员ID</th>
				<th>反馈信息</th>
				<th>处理信息</th>
				<th>创建时间</th>
				<th>状态</th>
				<th>详情</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memberFeedback">
			<tr>
				<td>
					${memberFeedback.customerName}
				</td>
				<td>
					${memberFeedback.customerAccount}
				</td>
				<td>
					${memberFeedback.feedbackDetail}
				</td>
				<td>
					${memberFeedback.reply}
				</td>
				<td>
					<fmt:formatDate value="${memberFeedback.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:choose>
						<c:when test="${empty memberFeedback.reply}">
							未处理
						</c:when>
						<c:otherwise>
							已处理
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<a href="${ctx}/member/memberFeedback/form?id=${memberFeedback.id}">详情</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
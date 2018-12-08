<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
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
			window.open('${ctx}/member/memberInfo/exportOperatorMemberInfo?' + $('#searchForm').serialize() + itemCheckBoxVal());
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
		<li class="active"><a href="${ctx}/member/memberInfo/operatorMemberInfo">会员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="memberInfo" action="${ctx}/member/memberInfo/operatorMemberInfo" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员名称：</label>
				<form:input path="nickname" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>会员账号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>会员推荐人账号：</label>
				<form:input path="refereeAccount" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>注册途径：</label>
				<form:select path="registerWay" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="自主注册"/>
					<form:option value="1" label="后台添加"/>
				</form:select>
			</li>
			<li><label>注册时间：</label>
				<input name="startDate" id="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${memberInfo.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endDate" id="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${memberInfo.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
				<th>会员名称</th>
				<th>会员账号</th>
				<th>会员推荐人</th>
				<th>会员推荐人账号</th>
				<th>注册途径</th>
				<th>注册时间</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memberInfo">
			<tr>
				<td>
					<input type="checkbox" name="itemId" value="${memberInfo.id}"/>
				</td>
				<td>
					${memberInfo.nickname}
				</td>
				<td>
					${memberInfo.mobile}
				</td>
				<td>
					${memberInfo.refereeName}
				</td>
				<td>
					${memberInfo.refereeAccount}
				</td>
				<td>
					${memberInfo.registerWay eq '0' ? '自主注册' : '后台添加'}
				</td>
				<td>
					<fmt:formatDate value="${memberInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${memberInfo.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
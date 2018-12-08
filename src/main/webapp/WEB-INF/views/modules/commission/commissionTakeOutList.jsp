<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>佣金提现管理</title>
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
            window.open('${ctx}/commission/commissionTakeOut/listExportData?' + $('#searchForm').serialize() + itemCheckBoxVal());
        }
        function itemCheckBoxVal() {
            var itemStr = '';
            $('input[name="itemId"]:checked').each(function () {
                itemStr += '&itemIds=' + $(this).val();
            });
            return itemStr;
        }
        
        function comichecks() {
            location.href = '${ctx}/commission/commissionTakeOut/checks?' + itemCheckBoxVal();
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/commission/commissionTakeOut/">佣金提现列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="commissionInfo" action="${ctx}/commission/commissionTakeOut/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户账号：</label>
				<form:input path="userMobile" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>银行账户：</label>
				<form:input path="bankAccount" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>开户人名称：</label>
				<form:input path="bankAccountName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>开户行：</label>
				<form:input path="bankName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="startDate" id="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${commissionInfo.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endDate" id="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${commissionInfo.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="checkStatus" cssClass="select-multiple" cssStyle="width: 170px">
					<form:option value="" label="全部" />
					<form:option value="1" label="待审核" />
					<form:option value="2" label="已打款" />
					<form:option value="3" label="已驳回" />
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/></li>
			<li class="btns"><input id="btnCheck" class="btn btn-primary" type="button" value="批量审核" onclick="comichecks()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input type="checkbox" id="allCheck"/></th>
				<th>用户账号</th>
				<th>用户名称</th>
				<th>提现金额</th>
				<th>应付金额</th>
				<th>创建时间</th>
				<th>银行账户</th>
				<th>开户人名称</th>
				<th>开户行</th>
				<th>状态</th>
				<th>备注</th>
				<shiro:hasPermission name="commission:commissionTakeOut:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="commissionTakeOut">
			<tr>
				<td>
					<c:if test="${commissionTakeOut.checkStatus == '1'}">
						<input type="checkbox" name="itemId" value="${commissionTakeOut.id}"/>
					</c:if>
				</td>
				<td>
						${commissionTakeOut.userMobile}
				</td>
				<td>
						${commissionTakeOut.userName}
				</td>
				<td>
						${commissionTakeOut.amount}
				</td>
				<td>
						${commissionTakeOut.payAmount}
				</td>
				<td>
					<fmt:formatDate value="${commissionTakeOut.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${commissionTakeOut.bankAccount}
				</td>
				<td>
					${commissionTakeOut.bankAccountName}
				</td>
				<td>
					${commissionTakeOut.bankName}
				</td>
				<td>
					<c:if test="${commissionTakeOut.checkStatus == '1'}">
						待审核
					</c:if>
					<c:if test="${commissionTakeOut.checkStatus == '2'}">
						已打款
					</c:if>
					<c:if test="${commissionTakeOut.checkStatus == '3'}">
						已驳回
					</c:if>

				</td>
				<td>
					${commissionTakeOut.checkRemark}
				</td>
				<shiro:hasPermission name="commission:commissionTakeOut:edit"><td>
					<c:if test="${commissionTakeOut.checkStatus == '1'}">
						<a href="${ctx}/commission/commissionTakeOut/form?id=${commissionTakeOut.id}"  >打款审核</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
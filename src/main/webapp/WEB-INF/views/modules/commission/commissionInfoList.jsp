<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>佣金明细管理</title>
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
            window.open('${ctx}/commission/commissionInfo/listExportData?' + $('#searchForm').serialize() + itemCheckBoxVal());
        }
        function itemCheckBoxVal() {
            var itemStr = '';
            $('input[name="itemId"]:checked').each(function () {
                itemStr += '&itemIds=' + $(this).val();
            });
            return itemStr;
        }
	</script>
	<style type="text/css">
		.form-search .ul-form li label{
			width: 100px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/commission/commissionInfo/list">佣金明细列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="commissionInfo" action="${ctx}/commission/commissionInfo/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属用户账号：</label>
				<form:input path="userMobile" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>生成用户账号：</label>
				<form:input path="produceUserMobile" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>关联单号：</label>
				<form:input path="unionId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" cssClass="select-multiple" cssStyle="width: 200px">
					<form:option value="" label="全部" />
					<form:option value="1" label="推荐用户消费返佣" />
					<form:option value="2" label="推荐商家销售返佣" />
					<form:option value="3" label="推荐商家入驻返佣" />
					<form:option value="4" label="推荐商家送出礼包返佣" />
					<form:option value="5" label="商家送出礼包返佣" />
					<form:option value="6" label="提现" />
					<form:option value="7" label="佣金转余额" />
				</form:select>
			</li>
			<li><label>生成时间：</label>
				<input name="startDate" id="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${memberInfo.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endDate" id="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${memberInfo.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" cssClass="select-multiple" cssStyle="width: 170px">
					<form:option value="" label="全部" />
					<form:option value="1" label="已清算" />
					<form:option value="0" label="未清算" />
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
				<th>所属用户账号</th>
				<th>所属用户名称</th>
				<th>生成用户账号</th>
				<th>生成用户名称</th>
				<th>生成规则</th>
				<th>产生金额</th>
				<th>佣金金额</th>
				<th>佣金类型</th>
				<th>产生时间</th>
				<th>关联单号</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="commissionInfo">
			<tr>
				<td>
					<input type="checkbox" name="itemId" value="${commissionInfo.id}"/>
				</td>
				<td>${commissionInfo.userMobile}</td>
				<td>${commissionInfo.userName}</td>
				<td>${commissionInfo.produceUserMobile}</td>
				<td>${commissionInfo.produceUserName}</td>
				<td>
					<c:if test="${commissionInfo.type == '1'}">
						商品分类配置比例
					</c:if>
					<c:if test="${commissionInfo.type != '1'}">
						<c:if test="${commissionInfo.mode == '1'}">
							固定金额-${commissionInfo.number}
						</c:if>
						<c:if test="${commissionInfo.mode == '2'}">
							总额百分比-${commissionInfo.number}%
						</c:if>
					</c:if>
					<c:if test="${commissionInfo.type == '6'}">

					</c:if>
					<c:if test="${commissionInfo.type == '7'}">
					</c:if>
				</td>
				<td>${commissionInfo.originAmount}</td>
				<td>
					<c:if test="${commissionInfo.type == '6' || commissionInfo.type == '7'}">
						<span style="color: red">-${commissionInfo.amount}</span>
					</c:if>
					<c:if test="${commissionInfo.type != '6' && commissionInfo.type != '7'}">
						 ${commissionInfo.amount}
					</c:if>
				</td>

				<td>
					<c:if test="${commissionInfo.type == '1'}">
						推荐用户消费返佣
					</c:if>
					<c:if test="${commissionInfo.type == '2'}">
						推荐商家销售返佣
					</c:if>
					<c:if test="${commissionInfo.type == '3'}">
						推荐商家入驻返佣
					</c:if>
					<c:if test="${commissionInfo.type == '4'}">
						推荐商家送出礼包返佣
					</c:if>
					<c:if test="${commissionInfo.type == '5'}">
						商家送出礼包返佣
					</c:if>
					<c:if test="${commissionInfo.type == '6'}">
						提现
					</c:if>
					<c:if test="${commissionInfo.type == '7'}">
						佣金转余额
					</c:if>
				</td>
				<td><fmt:formatDate value="${commissionInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<c:if test="${commissionInfo.type == '1' || commissionInfo.type == '2'}">
						${commissionInfo.unionId}
					</c:if>
				</td>
				<td>
					<c:if test="${commissionInfo.status == '1'}">
						已清算
					</c:if>
					<c:if test="${commissionInfo.status == '0'}">
						未清算
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
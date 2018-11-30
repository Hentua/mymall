<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>佣金明细管理</title>
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
		<li class="active"><a href="${ctx}/commission/commissionInfo/">佣金明细列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="commissionInfo" action="${ctx}/commission/commissionInfo/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户账号：</label>
				<form:input path="userMobile" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" cssClass="select-multiple" cssStyle="width: 170px">
					<form:option value="" label="全部" />
					<form:option value="1" label="推荐用户消费返佣" />
					<form:option value="2" label="推荐商家销售返佣" />
					<form:option value="3" label="推荐商家入驻返佣" />
					<form:option value="4" label="推荐商家送出礼包返佣" />
					<form:option value="5" label="商家送出礼包返佣" />
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" cssClass="select-multiple" cssStyle="width: 170px">
					<form:option value="" label="全部" />
					<form:option value="1" label="已清算" />
					<form:option value="0" label="未清算" />
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
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
				<td>${commissionInfo.amount}</td>
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
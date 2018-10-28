<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分配优惠券</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/member/memberInfo/">会员列表</a></li>
		<li class="active"><a href="" >分配优惠券</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="memberInfo" action="${ctx}/member/memberInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">昵称：</label>
			<div class="controls">
				${memberInfo.nickname}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				${memberInfo.sex eq '0' ? '男' : '女'}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册途径：</label>
			<div class="controls">
					${memberInfo.registerWay eq '0' ? '自主注册' : '后台添加'}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				${memberInfo.remarks}
			</div>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>
				<th>优惠券类型</th>
				<th>优惠券名称</th>
				<th>过期时间</th>
				<th>扣减比例</th>
				<th>扣减金额</th>
				<th>满减金额限制</th>
				<th>是否可使用</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>备注</th>
				<th>分配</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${couponConfigs.list}" var="couponConfig">
				<tr>
					<td>
						<c:if test="${couponConfig.couponType == '0'}">
							折扣减免
						</c:if>
						<c:if test="${couponConfig.couponType == '1'}">
							金额减免
						</c:if>
						<c:if test="${couponConfig.couponType == '2'}">
							满减
						</c:if>
					</td>
					<td>
						${couponConfig.couponName}
					</td>
					<td>
						${couponConfig.eapiryTime} 天
					</td>
					<td>
						${couponConfig.discountRate}
					</td>
					<td>
						${couponConfig.discountAmount}
					</td>
					<td>
						${couponConfig.limitAmount}
					</td>
					<td>
						${couponConfig.status eq '0' ? '可使用' : '不可使用'}
					</td>
					<td>
						<fmt:formatDate value="${couponConfig.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<fmt:formatDate value="${couponConfig.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						${couponConfig.remarks}
					</td>
					<td>
						<a href="${ctx}/member/memberInfo/memberCouponDistribution?id=${memberInfo.id}&couponId=${couponConfig.id}" onclick="return confirmx('确定要分配该优惠券到会员${memberInfo.nickname}吗？', this.href)">分配</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="pagination">${couponConfigs}</div>
		<div class="form-actions">
			<a id="btnCancel" class="btn" type="button" href="${ctx}/member/memberInfo/">返 回</a>
		</div>
	</form:form>
</body>
</html>
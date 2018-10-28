<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包赠送</title>
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
		<li class="active"><a href="${ctx}/member/memberInfo/couponDistribution?id=${memberInfo.id}" >礼包赠送</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="memberInfo" action="" method="post" class="form-horizontal">
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
				<th>礼包名称</th>
				<th>现价</th>
				<th>单个礼包商品数量</th>
				<th>礼包数量</th>
				<th>购买时间</th>
				<th>备注</th>
				<th>赠送</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${gifts.list}" var="giftMerchant">
				<tr>
					<td><a href="${ctx}/gift/giftConfig/giftDetail?id=${giftMerchant.giftConfigId}">
						${giftMerchant.giftName}
					</a></td>
					<td>
						${giftMerchant.giftPrice}
					</td>
					<td>
						${giftMerchant.goodsCount}
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
					<td>
						<a href="${ctx}/member/memberInfo/memberGiveGift?id=${memberInfo.id}&giftMerchantId=${giftMerchant.id}" onclick="return confirmx('确定要赠送礼包给会员${memberInfo.nickname}吗？', this.href)">赠送</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="pagination">${gifts}</div>
		<div class="form-actions">
			<a id="btnCancel" class="btn" type="button" href="${ctx}/member/memberInfo/">返 回</a>
		</div>
	</form:form>
</body>
</html>
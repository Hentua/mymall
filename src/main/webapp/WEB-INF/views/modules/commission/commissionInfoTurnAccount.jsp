<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>佣金明细管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					// form.submit();
					$.jBox.open("iframe:${ctx}/member/memberInfo/checkPayPasswordForm?id=${fns:getUser().id}&failedCallbackUrl=${ctx}/member/memberInfo/checkPayPasswordResultDialog?checkResult=0&successCallbackUrl=${ctx}/member/memberInfo/checkPayPasswordResultDialog?checkResult=1", "美易验证", 1200, $(top.document).height() - 280, {
						buttons: {"关闭": true}, submit: function (v, h, f) {
						}, loaded: function (h) {
							$(".jbox-content", top.document).css("overflow-y", "hidden");
						},
						closed: function() {
							if(checkResult == '1') {
								loading('正在提交，请稍等...');
								form.submit();
							}else {
								jBox.close();
								closeLoading();
							}
						}
					});
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
		<li  ><a href="${ctx}/sys/user/merchantInfo">首页</a></li>
		<li ><a href="${ctx}/commission/commissionInfo/merchantList">佣金明细列表</a></li>
		<li ><a href="${ctx}/commission/commissionInfo/merchantCommissionTakeOut">佣金提现</a></li>
		<li  class="active"><a href="${ctx}/commission/commissionInfo/commissionInfoTurnAccount">佣金转余额</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="commissionInfo" action="${ctx}/commission/commissionInfo/commissionInfoTurnAccountSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">转出金额：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" class="input-xlarge  number required"/>
				&nbsp;&nbsp;&nbsp;<span>可转出余额：${memberInfo.commission}</span>
			</div>
		</div>
		<div class="form-actions">
			 <input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
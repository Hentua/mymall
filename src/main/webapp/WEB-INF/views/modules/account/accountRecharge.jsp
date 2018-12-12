<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户流水管理</title>
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

        var platBankAccountListJson = eval('(${platBankAccountListJson})');
        $(function () {
            // alert(platBankAccountListJson)
            var x=platBankAccountListJson[0];
            console.log(x)
            $("#platBankAccount_input").val(x.bankAccount);
            $("#bankAccountName").val(x.bankAccountName);
            $("#bankName").val(x.bankName);
            $("#platBankAccount_hide").val(x.title);
        })

        function chesel(t) {
            var x = {};
            for(var i=0;i<platBankAccountListJson.length;i++){
                var w = platBankAccountListJson[i];
                if(w.id == $(t).val()){
                    x=w;
                    break;
                }
            }
            $("#platBankAccount_input").val(x.bankAccount);
            $("#bankAccountName").val(x.bankAccountName);
            $("#bankName").val(x.bankName);
            $("#platBankAccount_hide").val(x.title);

        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li  ><a href="${ctx}/sys/user/merchantInfo">首页</a></li>
		<li ><a href="${ctx}/account/accountFlow/merchantList">余额明细</a></li>
		<li class="active" ><a href="${ctx}/account/accountFlow/recharge">打款充值</a></li>
        <li  ><a href="${ctx}/account/accountFlow/merchantRechargeFlow">打款充值记录</a></li>
    </ul><br/>
	<form:form id="inputForm" modelAttribute="accountFlow" action="${ctx}/account/accountFlow/rechargeSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">转账至：</label>
			<div class="controls">
				<form:select path="" cssStyle="width: 283px" onchange="chesel(this)">
					<c:forEach items="${platBankAccountList}" var="p">
						<form:option value="${p.id}" label="${p.title}" />
					</c:forEach>
					<%--<form:options items="${fns:getDictList('plat_bank_account')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台收款账户：</label>
			<div class="controls">
				<form:input id="platBankAccount_input" path="" htmlEscape="false" maxlength="50"  disabled="true"  class="input-xlarge required"/>
				<form:hidden id="platBankAccount_hide" path="platBankAccount" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台收款人名称：</label>
			<div class="controls">
				<form:input id="bankAccountName" path="" htmlEscape="false" disabled="true" maxlength="50" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台收款银行：</label>
			<div class="controls">
				<form:input id="bankName" path="" htmlEscape="false" maxlength="200" disabled="true" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款人名称：</label>
			<div class="controls">
				<form:input path="bankAccountName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款账号：</label>
			<div class="controls">
				<form:input path="bankAccount" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款银行：</label>
			<div class="controls">
				<form:input path="bankName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">金额：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转账时间：</label>
			<div class="controls">
				<input id="transferDate" style="width: 270px;" name="transferDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate required"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转账截图：</label>
			<div class="controls">
				<form:hidden id="transferImage" path="transferImage" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<sys:ckfinder input="transferImage" type="images" uploadPath="/recharge" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
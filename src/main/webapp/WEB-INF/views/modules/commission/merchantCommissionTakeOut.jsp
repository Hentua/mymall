<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>佣金提现管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

		var areas_1 = eval('${areas_1}');
        var areas_2 = eval('(${areas_2})');
        var areas_3 = eval('(${areas_3})');

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
			console.log(areas_1)
            initarea();
		});

		function initarea() {
			for(var i=0;i<areas_1.length;i++){
			    var area = areas_1[i];
					$("#province").append("<option data-code='"+area["area_code"]+"' value='"+area["area_name"]+"' >"+area["area_name"]+"</option>")
			}
        }

        function provinceCk() {
			var parent_code = $("#province  option:selected").attr("data-code")
            $("#city").text("")

            $("#city").val("")
            $("#area").val("")
			$("#s2id_city").find(".select2-chosen").text("")
            $("#s2id_area").find(".select2-chosen").text("")
            for(var i=0;i<areas_2.length;i++){
                var area = areas_2[i];
                if(area["parent_code"] == parent_code){
                    $("#city").append("<option data-code='"+area["area_code"]+"' value='"+area["area_name"]+"' >"+area["area_name"]+"</option>")
                }
			}
        }

        function cityCk() {
            var parent_code = $("#city  option:selected").attr("data-code")
            $("#area").text("")
            $("#area").val("")
            $("#s2id_area").find(".select2-chosen").text("")
            for(var i=0;i<areas_3.length;i++){
                var area = areas_3[i];
                if(area["parent_code"] == parent_code){
                    $("#area").append("<option   value='"+area["area_name"]+"' >"+area["area_name"]+"</option>")
                }
            }
        }

        function go_a() {
            if($("#amount").val()%1 !=0){
                $("#wa_span").show();
                return;
			}
            $("#inputForm").submit();
            <%--location.href = "${ctx}/goods/goodsInfo/updateStatusCheck?id=${goodsInfo.id}&status="+status;--%>
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li  ><a href="${ctx}/sys/user/merchantInfo">首页</a></li>
		<li ><a href="${ctx}/commission/commissionInfo/merchantList">佣金明细列表</a></li>
		<li class="active" ><a href="${ctx}/commission/commissionInfo/merchantCommissionTakeOut">佣金提现</a></li>
		<li  ><a href="${ctx}/commission/commissionInfo/commissionInfoTurnAccount">佣金转余额</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="commissionInfo" action="${ctx}/commission/commissionInfo/commissionInfoTakeOutSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="checkStatus" name="checkStatus" >
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">收款人名称：</label>
			<div class="controls">
				<form:input path="bankAccountName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款人账户：</label>
			<div class="controls">
				<form:input path="bankAccount" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款银行地址：</label>
			<div class="controls">
				省：<select onchange="provinceCk()" id="province" name="province" style="width: 170px" >
				</select>
				市：<select onchange="cityCk()" id="city" name="city" style="width: 170px" >
				</select>
				区：<select id="area" name="area" style="width: 170px">
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款银行：</label>
			<div class="controls">
				<form:input path="bankName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提现金额：</label>
			<div class="controls">
				<form:input id="amount" path="amount" htmlEscape="false" maxlength="200" class="input-xlarge number required"/>
				&nbsp;&nbsp;&nbsp;<span>可提现余额：${memberInfo.commission}</span>
				<br/><span id="wa_span" style="color: red;display: none">提现金额以1元为基础单位</span>
			</div>
		</div>
		<div class="form-actions">

            <c:if test="${!empty message}">
                <span style="color: red">您的提现申请正在审核，请等审核通过后再提交新的申请！</span>
                <br/>
            </c:if>
            <c:if test="${org != '1'}">
                <input class="btn btn-primary" type="button" onclick="go_a()"    value="提 交"/>
            </c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
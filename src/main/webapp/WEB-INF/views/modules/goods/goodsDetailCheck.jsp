<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品信息管理</title>
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

        function go_a(status) {
			$("#goodsStatus").val(status);
			$("#inputForm").submit();
			<%--location.href = "${ctx}/goods/goodsInfo/updateStatusCheck?id=${goodsInfo.id}&status="+status;--%>
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/goods/goodsInfo/checkList/">商品信息列表</a></li>
		<li class="active"><a href="${ctx}/goods/goodsInfo/goodsDetailCheck?id=${goodsInfo.id}">商品详情</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsInfo" action="${ctx}/goods/goodsInfo/updateStatusCheck"   method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="goodsStatus" name="status" >
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">商品分类：</label>
			<div class="controls">
					${goodsInfo.goodsCategoryName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品名称：</label>
			<div class="controls">
				${goodsInfo.goodsName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品标题：</label>
			<div class="controls">
				${goodsInfo.goodsTitle}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				${goodsInfo.unit}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠类型：</label>
			<div class="controls">
				<c:if test="${goodsInfo.status != '2'}">
					<c:if test="${goodsInfo.discountType == '0'}">
						不可使用优惠
					</c:if>
					<c:if test="${goodsInfo.discountType == '1'}">
						仅可以使用5折优惠
					</c:if>
					<c:if test="${goodsInfo.discountType == '2'}">
						仅可以使用7折优惠
					</c:if>
					<c:if test="${goodsInfo.discountType == '3'}">
						都可使用
					</c:if>
				</c:if>
				<c:if test="${goodsInfo.status == '2'}">
					<form:radiobutton title="不可使用优惠" checked="checked" htmlEscape="false" class="required" path="discountType" value="0" label="不可使用优惠"/>
					<form:radiobutton title="仅可以使用5折优惠" htmlEscape="false" class="required" path="discountType" value="1" label="仅可以使用5折优惠"/>
					<form:radiobutton title="仅可以使用7折优惠" htmlEscape="false" class="required" path="discountType" value="2" label="仅可以使用7折优惠"/>
					<form:radiobutton title="都可使用" htmlEscape="false" class="required" path="discountType" value="3" label="都可使用"/>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格：</label>
			<div class="controls">
				<table id="standardTab">
					<c:if test="${goodsInfo.goodsStandards != null && fn:length(goodsInfo.goodsStandards)>0}">
						<c:forEach items="${goodsInfo.goodsStandards}" var="goodsStandard">
							<tr>
								<td>名称：${goodsStandard.name}，</td>
								<td>价格： ${goodsStandard.price}，</td>
								<c:if test="${goodsInfo.status == '2'}">
									<td>结算金额：<form:input path="settlementsAmounts" htmlEscape="false" class="input-xlarge  number "/></td>
								</c:if>
								<c:if test="${goodsInfo.status != '2'}">
									<td>结算金额：${goodsStandard.settlementsAmount}</td>
								</c:if>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品图片：</label>
			<div class="controls">
				<img src="${goodsInfo.image}" width="50px" alt="商品图片"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品描述图片：</label>
			<div class="controls">
				<form:hidden id="nameImage1" path="despImages" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<%--<form:hidden id="files" path="despImages" htmlEscape="false" maxlength="255" class="input-xlarge"/>--%>
					<%--<sys:ckfinder input="files" type="files" uploadPath="/goods" selectMultiple="false"/>--%>
				<sys:ckfinder input="nameImage1" type="images" uploadPath="/goods" selectMultiple="true" maxWidth="100" maxHeight="100" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品描述：</label>
			<div class="controls">
					<%--${goodsInfo.goodsDesp}--%>
						<form:textarea id="goodsDesp" htmlEscape="false" disabled="true" path="goodsDesp" rows="4" maxlength="200" class="input-xxlarge"/>
						<sys:ckeditor replace="goodsDesp" uploadPath="/goods/goodsDesp" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核备注：</label>
			<div class="controls">
				<form:textarea path="checkRemarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<c:if test="${goodsInfo.status == '2'}">
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="go_a('3')" value="上 架"/>
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="go_a('1')" value="驳 回"/>
			</c:if>
		</div>
	</form:form>
</body>
</html>
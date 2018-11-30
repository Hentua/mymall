<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品信息管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/goods/goodsInfo/list/">商品信息列表</a></li>
		<li class="active"><a href="${ctx}/goods/goodsInfo/goodsDetail?id=${goodsInfo.id}">商品详情</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsInfo" action="" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="status"  />
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
								<td>价格：${goodsStandard.price}，</td>
								<td>结算金额：${goodsStandard.settlementsAmount}</td>
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
			<label class="control-label">商品备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge " readonly="true"/>
			</div>
		</div>
		<c:if test="${!empty goodsInfo.checkRemarks}">
			<div class="control-group">
				<div class="control-group">
					<label class="control-label">审核备注：</label>
					<div class="controls">
						<form:textarea path="checkRemarks" cssStyle="border: 1px solid red" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge " readonly="false"/>
					</div>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
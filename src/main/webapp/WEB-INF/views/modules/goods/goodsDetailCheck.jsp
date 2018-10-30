<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品信息管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/goods/goodsInfo/checkList/">商品信息列表</a></li>
		<li class="active"><a href="${ctx}/goods/goodsInfo/goodsDetailCheck?id=${goodsInfo.id}">商品详情</a></li>
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
			<label class="control-label">商品现价：</label>
			<div class="controls">
				${goodsInfo.goodsPrice}
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
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
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
		
		function addStandard() {
			$("#standardTab").append("<tr>\n" +
                "\t\t\t\t\t\t<td>名称：<input id=\"goodsStandardsName\" name=\"goodsStandardsName\" class=\"input-xlarge required\" type=\"text\" value=\"\" maxlength=\"200\"></td>\n" +
                "\t\t\t\t\t\t<td>价格：<input id=\"goodsStandardsPrice\" name=\"goodsStandardsPrice\" class=\"input-xlarge  number required\" type=\"text\" value=\"\"></td>\n" +
                "\t\t\t\t\t\t<td>&nbsp;&nbsp;<a style=\"cursor: pointer\" onclick=\"delStandard(this)\">×</a></td>\n" +
                "\t\t\t\t\t</tr>")
        }

        function delStandard(t) {
			$(t).parent().parent().remove()
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/goods/goodsInfo/list">商品信息列表</a></li>
		<li class="active"><a href="${ctx}/goods/goodsInfo/form?id=${goodsInfo.id}">商品信息<shiro:hasPermission name="goods:goodsInfo:edit">${not empty goodsInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="goods:goodsInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsInfo" action="${ctx}/goods/goodsInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="status"  />
		<input type="hidden" name="goodsType" value="1">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">商品分类：</label>
			<div class="controls">
				<sys:treeselect notAllowSelectParent="true" id="goodsCategoryId" name="goodsCategoryId" value="${goodsInfo.goodsCategoryId}" labelName="goodsCategoryName" labelValue="${goodsInfo.goodsCategoryName}"
								title="商品分类" url="/goods/goodsCategory/treeData" extId="${goodsInfo.goodsCategoryId}" cssClass=""  />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品名称：</label>
			<div class="controls">
				<form:input path="goodsName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品标题：</label>
			<div class="controls">
				<form:input path="goodsTitle" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">规格：</label>
			<div class="controls">
				<table id="standardTab">

					<c:if test="${goodsInfo.goodsStandards == null ||  fn:length(goodsInfo.goodsStandards) == 0}">
						<tr>
							<td>名称：<input name="goodsStandardsName" class="input-xlarge  required" type="text" /></td>
							<td>价格：<input name="goodsStandardsPrice" class="input-xlarge  number required" type="text" /></td>
							<td>&nbsp;&nbsp;<a style="cursor: pointer" onclick="delStandard(this)">×</a></td>
						</tr>
					</c:if>
					<c:if test="${goodsInfo.goodsStandards != null && fn:length(goodsInfo.goodsStandards)>0}">
						<c:forEach items="${goodsInfo.goodsStandards}" var="goodsStandard">
							<tr>
								<td>名称：<input   name="goodsStandardsName" class="input-xlarge  required" type="text" value="${goodsStandard.name}"></td>
								<td>价格：<input   name="goodsStandardsPrice" class="input-xlarge  number required" type="text" value="${goodsStandard.price}"></td>
								<td>&nbsp;&nbsp;<a style="cursor: pointer" onclick="delStandard(this)">×</a></td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
				<input   class="btn" type="button" value="添加规格" onclick="addStandard()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品主图：</label>
			<div class="controls">
				<form:hidden id="nameImage" path="image" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/goods" selectMultiple="false"    />
				<span style="color: silver"><span style="color: red">*</span>商品图片最佳尺寸300*300</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品描述图片：</label>
			<div class="controls">
				<form:hidden id="nameImage1" path="despImages" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<%--<form:hidden id="files" path="despImages" htmlEscape="false" maxlength="255" class="input-xlarge"/>--%>
					<%--<sys:ckfinder input="files" type="files" uploadPath="/goods" selectMultiple="false"/>--%>
				<sys:ckfinder input="nameImage1" type="images" uploadPath="/goods" selectMultiple="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品详情：</label>
			<div class="controls">
				<form:textarea id="goodsDesp" htmlEscape="false" path="goodsDesp" rows="4" maxlength="200" class="input-xxlarge"/>
				<sys:ckeditor replace="goodsDesp" uploadPath="/goods/goodsDesp" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${!empty goodsInfo.checkRemarks}">
			<div class="control-group">
				<label class="control-label">审核备注：</label>
				<div class="controls">
					<form:textarea path="checkRemarks" cssStyle="border: 1px solid red" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge " readonly="true"/>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="goods:goodsInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
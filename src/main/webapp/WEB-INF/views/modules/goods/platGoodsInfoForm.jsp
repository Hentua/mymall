<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
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
                "\t\t\t\t\t\t<td>名称：<input id=\"goodsStandardsName\" name=\"goodsStandardsName\" class=\"input-xlarge required\" type=\"text\" value=\"\" maxlength=\"10\"></td>\n" +
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
		<li><a href="${ctx}/goods/goodsInfo/platList">平台商品信息列表</a></li>
		<li class="active"><a href="${ctx}/goods/goodsInfo/platForm?id=${goodsInfo.id}">平台商品信息<shiro:hasPermission name="goods:goodsInfo:edit">${not empty goodsInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="goods:goodsInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsInfo" action="${ctx}/goods/goodsInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="goodsType" value="2">
		<form:hidden path="status"  />
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
				<form:input path="goodsTitle" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠类型：</label>
			<div class="controls">
				<form:radiobutton title="不可使用优惠" checked="checked" htmlEscape="false" class="required" path="discountType" value="0" label="不可使用优惠"/>
				<form:radiobutton title="仅可以使用5折优惠" htmlEscape="false" class="required" path="discountType" value="1" label="仅可以使用5折优惠"/>
				<form:radiobutton title="仅可以使用7折优惠" htmlEscape="false" class="required" path="discountType" value="2" label="仅可以使用7折优惠"/>
				<form:radiobutton title="都可使用" htmlEscape="false" class="required" path="discountType" value="3" label="都可使用"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">规格：</label>
			<div class="controls">
				<table id="standardTab">

					<c:if test="${goodsInfo.goodsStandards == null ||  fn:length(goodsInfo.goodsStandards) == 0}">
						<tr>
							<td>名称：<form:input path="goodsStandardsName" htmlEscape="false" maxlength="10" class="input-xlarge required"/></td>
							<td>价格：<form:input path="goodsStandardsPrice" htmlEscape="false" class="input-xlarge  number required"/></td>
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
			<label class="control-label">商品图片：</label>
			<div class="controls">
				<form:hidden id="nameImage" path="image" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/goods" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品描述图片：</label>
			<div class="controls">
				<form:hidden id="nameImage1" path="despImages" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<%--<form:hidden id="files" path="despImages" htmlEscape="false" maxlength="255" class="input-xlarge"/>--%>
					<%--<sys:ckfinder input="files" type="files" uploadPath="/goods" selectMultiple="false"/>--%>
				<sys:ckfinder input="nameImage1" type="images" uploadPath="/goods" selectMultiple="true" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品描述：</label>
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
		<div class="form-actions">
			<shiro:hasPermission name="goods:goodsInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
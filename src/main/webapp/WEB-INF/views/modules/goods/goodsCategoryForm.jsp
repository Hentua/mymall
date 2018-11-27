<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品分类管理</title>
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
        function selGoodsbyjump(t) {
            top.$.jBox.open("iframe:${ctx}/goods/goodsInfo/selectList?goodsType=1", "选择商品", 1200, $(top.document).height()-280, {
                buttons:{"确定":"ok","关闭":true}, submit:function(v, h, f){
                    if (v=="ok"){
                        console.log(h.find("iframe")[0].contentWindow)
                        var goodsInfo = {goodsId: h.find("iframe")[0].contentWindow.$("#goodsId").val(),
                            goodsName: h.find("iframe")[0].contentWindow.$("#goodsName").val(),
                            merchantCode: h.find("iframe")[0].contentWindow.$("#merchantCode").val(),
                            goodsImage: h.find("iframe")[0].contentWindow.$("#goodsImage").val()};
                        console.log(goodsInfo)
                        $("#jump_span").text("")
                        var text = "<span><input type=\"hidden\" name=\"jumpId\" value="+goodsInfo.goodsId+" />\n" +
                            "<img src="+goodsInfo.goodsImage+" width=\"100px\" height=\"100px\">\n" +
                            "<a href=\"javascript:\" onclick=\"goodsDel(this);\">×</a></span>"
                        console.log(text)
                        $("#jump_span").append(text);
                        console.log($("#jump_span").text())
                        $("#selGoods1").hide();
                    }
                }, loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }


        function goodsDel(t) {
            $(t).parent().remove();
            $("#selGoods1").show();
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/goods/goodsCategory/list">商品分类列表</a></li>
		<li class="active"><a href="${ctx}/goods/goodsCategory/form?id=${goodsCategory.id}">商品分类<shiro:hasPermission name="goods:goodsCategory:edit">${not empty goodsCategory.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="goods:goodsCategory:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsCategory" action="${ctx}/goods/goodsCategory/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">上级分类：${message}</label>
			<div class="controls">
				<sys:treeselect  id="parentCategoryId" name="parentCategoryId" value="${goodsCategory.parentCategoryId}" labelName="parentCategoryName" labelValue="${empty goodsCategory.parentCategoryName?'商品分类':goodsCategory.parentCategoryName}"
								title="上级分类" url="/goods/goodsCategory/treeData?parentCategoryId=0" extId="${goodsCategory.id}" cssClass=""   />
				<span class="help-inline"><font color="red">（注：不选择上级分类为默认为添加一级分类）</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分类名称：</label>
			<div class="controls">
				<form:input path="categoryName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">层级：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="depth" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>--%>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">分类图片：</label>
			<div class="controls">
				<form:hidden id="nameImage" path="image" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/goods/category" selectMultiple="false" maxWidth="100" maxHeight="100"/>

				<%--<form:hidden id="image" path="image" htmlEscape="false" maxlength="255" class="input-xlarge"/>--%>
				<%--<sys:ckfinder input="image" type="images" uploadPath="/image" selectMultiple="false" maxWidth="100" maxHeight="100"/>--%>
				<%--<form:input path="status" htmlEscape="false" maxlength="4" class="input-xlarge required digits"/>--%>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序标识：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${goodsCategory.depth != 1}">
			<div class="control-group" id="uninGoods">
				<label class="control-label">广告关联商品：</label>
				<div class="controls">
					<span id="jump_span">
						<span>
						<c:if test="${!empty goodsCategory.jumpId}">
							<input type="hidden" name="jumpId" value="${goodsCategory.jumpId}" />
							<img src="${goodsCategory.jumpGoodsImage}" title="${goodsCategory.jumpGoodsName}" width="100px" height="100px">
							<a href="javascript:" onclick="goodsDel(this);">×</a>
						</c:if>
					</span>
					</span>
					<input class="btn" style="display: ${empty goodsCategory.jumpId?'':'none'}" id="selGoods1" type="button" value="选择商品" onclick="selGoodsbyjump(this)">
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">佣金计算方式：</label>
			<div class="controls">
				<form:radiobutton title="固定金额" htmlEscape="false" class="required" path="commissionMode" value="1" label="固定金额"/>
				<form:radiobutton title="百分比" htmlEscape="false" class="required" path="commissionMode" value="2" label="百分比"/>
			</div>
			<span class="help-inline"><font color="red">（注：不配置佣金计算方式该品类下商品不计算佣金）</font> </span>
		</div>
		<div class="control-group">
			<label class="control-label">数值：</label>
			<div class="controls">
				<form:input path="commissionNumber" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="goods:goodsCategory:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>首页广告位管理</title>
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

		function showHide(t){
		    // console.log($(t).val());
		    if(1 == $(t).val()){
                $("#selGoods").hide();
			}else{
                $("#selGoods").show();
			}
		}

		function selGoods() {
            top.$.jBox.open("iframe:${ctx}/goods/goodsInfo/selectList", "选择商品", 1200, $(top.document).height()-280, {
                buttons:{"确定":"ok","关闭":true}, submit:function(v, h, f){
                    if (v=="ok"){
                        console.log(h.find("iframe")[0].contentWindow)
                        var goodsInfo = {goodsId: h.find("iframe")[0].contentWindow.$("#goodsId").val(),
							goodsName: h.find("iframe")[0].contentWindow.$("#goodsName").val(),
							merchantCode: h.find("iframe")[0].contentWindow.$("#merchantCode").val(),
                            goodsImage: h.find("iframe")[0].contentWindow.$("#goodsImage").val()};

						$("#goodsInfos").append("<span>\n" +
                            "\t\t\t\t\t\t<input type=\"hidden\" name=\"goodsId\" value='"+goodsInfo.goodsId+"' />\n" +
                            "\t\t\t\t\t\t<img src='"+goodsInfo.goodsImage+"' width=\"100px\" height=\"100px\">\n" +
                            "\t\t\t\t\t\t<a href=\"javascript:\" onclick=\"goodsDel(this);\">×</a>\n" +
                            "\t\t\t\t\t</span>")
                    }
                }, loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }

        function goodsDel(t) {
            $(t).parent().remove();
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/billboard/indexBillboard/">首页广告位列表</a></li>
		<li class="active"><a href="${ctx}/billboard/indexBillboard/form?id=${indexBillboard.id}">首页广告位<shiro:hasPermission name="billboard:indexBillboard:edit">${not empty indexBillboard.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="billboard:indexBillboard:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="indexBillboard" action="${ctx}/billboard/indexBillboard/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" onchange="showHide(this)"   class="input-xlarge ">
					<form:option value="1" label="轮播图广告位"/>
					<form:option value="2"  label="独立广告位"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告图片：</label>
			<div class="controls">
				<form:hidden id="image" path="image" htmlEscape="false" maxlength="100" class="input-xlarge"/>
				<sys:ckfinder input="image" type="images" uploadPath="/billboard/indexBillboard" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group" id="selGoods" style="${indexBillboard.type == 1?'display: none':''}">
			<label class="control-label">广告商品：</label>
			<div class="controls">
				<div style="" id="goodsInfos">
					<c:forEach items="${indexBillboard.goodsList}" var="g">
						<span>
							<input type="hidden" name="goodsId" value="${g.id}" />
							<img src="${g.image}" width="100px" height="100px">
							<a href="javascript:" onclick="goodsDel(this);">×</a>
						</span>
					</c:forEach>
				</div>
				<br/>
				 <input class="btn" type="button" value="选择商品" onclick="selGoods()">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="billboard:indexBillboard:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
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
		    console.log($(t).val());
		    if(2 == $(t).val()  ){
                $("#selGoods").show();
			}
			if(2 != $(t).val() ){
                $("#selGoods").hide();
			}
			console.log(3 == $(t).val() || 4 == $(t).val() || 5 == $(t).val())
			console.log(3 !=  $(t).val() || 4 != $(t).val() && 5 != $(t).val())

            if(3 == $(t).val() || 4 == $(t).val() || 5 == $(t).val()){
                $("#uninGoods").hide();
            }
            if(3 !=  $(t).val() && 4 != $(t).val() && 5 != $(t).val()){
                $("#uninGoods").show();
            }

            if(5 != $(t).val()){
                $("#uninGoodsCategory").hide();
			}
            if(5 == $(t).val()){
                $("#uninGoodsCategory").show();
            }
//
            <%--<form:option value="1" label="APP首页轮播图广告位"/>--%>
            <%--<form:option value="2"  label="APP首页标题广告"/>--%>
            <%--<form:option value="3"  label="APP开机广告"/>--%>
            <%--<form:option value="5"  label="APP首页推荐分类"/>--%>
            <%--<form:option value="4"  label="后台首页广告"/>--%>
            //
            if(1 == $(t).val()){
                $("#war_text").text("APP首页轮播图广告图片最佳尺寸1080*450");
			}
            if(2 == $(t).val()){
                $("#war_text").text("APP首页标题广告图片最佳尺寸1080*400");
            }
            if(3 == $(t).val()){
                $("#war_text").text("");
            }
            if(4 == $(t).val()){
                $("#war_text").text("后台首页广告图片最佳尺寸1920*600");
            }
            if(5 == $(t).val()){
                $("#war_text").text("APP首页推荐分类图片最佳尺寸200*200");
            }

            if(3 == $(t).val()){
                $("#scale_div").show();
			}else{
                $("#scale_div").hide();
			}

		}

		function selGoods() {
            top.$.jBox.open("iframe:${ctx}/goods/goodsInfo/selectList?goodsType=1", "选择商品", 1200, $(top.document).height()-280, {
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
					<form:option value="1" label="APP首页轮播图广告位"/>
					<form:option value="2"  label="APP首页标题广告"/>
					<form:option value="3"  label="APP开机广告"/>
					<form:option value="5"  label="APP首页推荐分类"/>
					<form:option value="4"  label="后台首页广告"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group" id="scale_div" style="display: none">
			<label class="control-label">开机广告图片比例：</label>
			<div class="controls">
				<form:radiobutton path="scale" value="16:9" label="16:9" checked="checked" />
				<form:radiobutton path="scale" value="19.5:9" label="19.5:9" />
				&nbsp;&nbsp;&nbsp;<span style="color: silver"><span style="color: red">*</span><span>16:9最佳尺寸（1920*1080），19.5:9最佳尺寸（2340*1080）</span></span>
			</div>
		</div>
		<div class="control-group"  >
			<label class="control-label">广告图片：</label>
			<div class="controls">
				<form:hidden id="image" path="image" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="image" type="images" uploadPath="/billboard/indexBillboard" selectMultiple="false" maxWidth="100" maxHeight="100"/>
				<span style="color: silver"><span style="color: red">*</span><span id="war_text">APP首页轮播图广告图片最佳尺寸1080*450</span></span>
			</div>
		</div>
		<div class="control-group" id="uninGoods" style="${(indexBillboard.type != 5)?'':'display: none'}">
			<label class="control-label">广告关联商品：</label>
			<div class="controls">
				<span id="jump_span">
					<span>
					<c:if test="${!empty indexBillboard.jumpId}">
						<input type="hidden" name="jumpId" value="${indexBillboard.jumpId}" />
						<img src="${indexBillboard.jumpGoodsImage}" title="${indexBillboard.jumpGoodsName}" width="100px" height="100px">
						<a href="javascript:" onclick="goodsDel(this);">×</a>
					</c:if>
				</span>
				</span>
				<input class="btn" style="display: ${empty indexBillboard.jumpId?'':'none'}" id="selGoods1" type="button" value="选择商品" onclick="selGoodsbyjump(this)">
			</div>
		</div>

		<div class="control-group" id="uninGoodsCategory" style="${(indexBillboard.type == 5)?'':'display: none'}">
			<label class="control-label">广告关联分类：</label>
			<div class="controls">
				<form:select path="categoryId" cssStyle="width: 170px">
					<form:option value="">全部</form:option>
					<c:forEach items="${categories}" var="c">
						<form:option value="${c.id}">${c.categoryName}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>

		<div class="control-group" id="selGoods"  style="${(indexBillboard.type == 2)?'':'display: none'}" >
			<label class="control-label">标题广告商品列表：</label>
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
			<label class="control-label">广告详情：</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="false" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/index/content" />
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
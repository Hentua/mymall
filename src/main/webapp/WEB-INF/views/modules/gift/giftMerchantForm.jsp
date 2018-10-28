<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包列表管理</title>
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
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/gift/giftMerchant/">礼包列表列表</a></li>
		<li class="active"><a href="${ctx}/gift/giftMerchant/form?id=${giftMerchant.id}">礼包列表<shiro:hasPermission name="gift:giftMerchant:edit">${not empty giftMerchant.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="gift:giftMerchant:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="giftMerchant" action="${ctx}/gift/giftMerchant/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">购买礼包配置ID：</label>
			<div class="controls">
				<form:input path="giftConfigId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">礼包名称：</label>
			<div class="controls">
				<form:input path="giftName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原价：</label>
			<div class="controls">
				<form:input path="originalPrice" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现价：</label>
			<div class="controls">
				<form:input path="giftPrice" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品总数量：</label>
			<div class="controls">
				<form:input path="goodsCount" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买商家ID：</label>
			<div class="controls">
				<form:input path="merchantCode" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买关联订单号：</label>
			<div class="controls">
				<form:input path="orderNo" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">礼包数量：</label>
			<div class="controls">
				<form:input path="giftCount" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remarks：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">商家礼包商品：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>商品ID</th>
								<th>商品数量</th>
								<th>店铺ID</th>
								<shiro:hasPermission name="gift:giftMerchant:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="giftMerchantGoodsList">
						</tbody>
						<shiro:hasPermission name="gift:giftMerchant:edit"><tfoot>
							<tr><td colspan="5"><a href="javascript:" onclick="addRow('#giftMerchantGoodsList', giftMerchantGoodsRowIdx, giftMerchantGoodsTpl);giftMerchantGoodsRowIdx = giftMerchantGoodsRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="giftMerchantGoodsTpl">//<!--
						<tr id="giftMerchantGoodsList{{idx}}">
							<td class="hide">
								<input id="giftMerchantGoodsList{{idx}}_id" name="giftMerchantGoodsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="giftMerchantGoodsList{{idx}}_delFlag" name="giftMerchantGoodsList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="giftMerchantGoodsList{{idx}}_goodsId" name="giftMerchantGoodsList[{{idx}}].goodsId" type="text" value="{{row.goodsId}}" maxlength="64" class="input-small required"/>
							</td>
							<td>
								<input id="giftMerchantGoodsList{{idx}}_goodsCount" name="giftMerchantGoodsList[{{idx}}].goodsCount" type="text" value="{{row.goodsCount}}" maxlength="11" class="input-small required"/>
							</td>
							<td>
								<input id="giftMerchantGoodsList{{idx}}_merchantCode" name="giftMerchantGoodsList[{{idx}}].merchantCode" type="text" value="{{row.merchantCode}}" maxlength="64" class="input-small required"/>
							</td>
							<shiro:hasPermission name="gift:giftMerchant:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#giftMerchantGoodsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var giftMerchantGoodsRowIdx = 0, giftMerchantGoodsTpl = $("#giftMerchantGoodsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(giftMerchant.giftMerchantGoodsList)};
							for (var i=0; i<data.length; i++){
								addRow('#giftMerchantGoodsList', giftMerchantGoodsRowIdx, giftMerchantGoodsTpl, data[i]);
								giftMerchantGoodsRowIdx = giftMerchantGoodsRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="gift:giftMerchant:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
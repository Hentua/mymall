<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包配置管理</title>
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
		<li><a href="${ctx}/gift/giftConfig/">礼包配置列表</a></li>
		<li class="active"><a href="${ctx}/gift/giftConfig/form?id=${giftConfig.id}">礼包配置<shiro:hasPermission name="gift:giftConfig:edit">${not empty giftConfig.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="gift:giftConfig:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="giftConfig" action="${ctx}/gift/giftConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">礼包名称：</label>
			<div class="controls">
				<form:input path="giftName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
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
			<label class="control-label">是否在APP显示商品价格 0-否 1-是：</label>
			<div class="controls">
				<form:radiobuttons path="showGoodsPrice" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
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
			<label class="control-label">对应分类ID：</label>
			<div class="controls">
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠券数量：</label>
			<div class="controls">
				<form:input path="couponCount" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">礼包优惠券：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>优惠券ID</th>
								<th>优惠券数量</th>
								<shiro:hasPermission name="gift:giftConfig:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="giftConfigCouponList">
						</tbody>
						<shiro:hasPermission name="gift:giftConfig:edit"><tfoot>
							<tr><td colspan="4"><a href="javascript:" onclick="addRow('#giftConfigCouponList', giftConfigCouponRowIdx, giftConfigCouponTpl);giftConfigCouponRowIdx = giftConfigCouponRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="giftConfigCouponTpl">//<!--
						<tr id="giftConfigCouponList{{idx}}">
							<td class="hide">
								<input id="giftConfigCouponList{{idx}}_id" name="giftConfigCouponList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="giftConfigCouponList{{idx}}_delFlag" name="giftConfigCouponList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="giftConfigCouponList{{idx}}_couponId" name="giftConfigCouponList[{{idx}}].couponId" type="text" value="{{row.couponId}}" maxlength="64" class="input-small required"/>
							</td>
							<td>
								<input id="giftConfigCouponList{{idx}}_couponCount" name="giftConfigCouponList[{{idx}}].couponCount" type="text" value="{{row.couponCount}}" maxlength="11" class="input-small required"/>
							</td>
							<shiro:hasPermission name="gift:giftConfig:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#giftConfigCouponList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var giftConfigCouponRowIdx = 0, giftConfigCouponTpl = $("#giftConfigCouponTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(giftConfig.giftConfigCouponList)};
							for (var i=0; i<data.length; i++){
								addRow('#giftConfigCouponList', giftConfigCouponRowIdx, giftConfigCouponTpl, data[i]);
								giftConfigCouponRowIdx = giftConfigCouponRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">礼包配置商品：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>商品ID</th>
								<th>商品数量</th>
								<th>店铺ID</th>
								<shiro:hasPermission name="gift:giftConfig:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="giftConfigGoodsList">
						</tbody>
						<shiro:hasPermission name="gift:giftConfig:edit"><tfoot>
							<tr><td colspan="5"><a href="javascript:" onclick="addRow('#giftConfigGoodsList', giftConfigGoodsRowIdx, giftConfigGoodsTpl);giftConfigGoodsRowIdx = giftConfigGoodsRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="giftConfigGoodsTpl">//<!--
						<tr id="giftConfigGoodsList{{idx}}">
							<td class="hide">
								<input id="giftConfigGoodsList{{idx}}_id" name="giftConfigGoodsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="giftConfigGoodsList{{idx}}_delFlag" name="giftConfigGoodsList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="giftConfigGoodsList{{idx}}_goodsId" name="giftConfigGoodsList[{{idx}}].goodsId" type="text" value="{{row.goodsId}}" maxlength="64" class="input-small required"/>
							</td>
							<td>
								<input id="giftConfigGoodsList{{idx}}_goodsCount" name="giftConfigGoodsList[{{idx}}].goodsCount" type="text" value="{{row.goodsCount}}" maxlength="11" class="input-small required"/>
							</td>
							<td>
								<input id="giftConfigGoodsList{{idx}}_merchantCode" name="giftConfigGoodsList[{{idx}}].merchantCode" type="text" value="{{row.merchantCode}}" maxlength="64" class="input-small required"/>
							</td>
							<shiro:hasPermission name="gift:giftConfig:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#giftConfigGoodsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var giftConfigGoodsRowIdx = 0, giftConfigGoodsTpl = $("#giftConfigGoodsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(giftConfig.giftConfigGoodsList)};
							for (var i=0; i<data.length; i++){
								addRow('#giftConfigGoodsList', giftConfigGoodsRowIdx, giftConfigGoodsTpl, data[i]);
								giftConfigGoodsRowIdx = giftConfigGoodsRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="gift:giftConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
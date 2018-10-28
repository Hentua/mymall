<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包详情</title>
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
		<li class="active"><a href="${ctx}/gift/giftConfig/giftDetail?id=${giftConfig.id}">礼包详情</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="giftConfig" action="${ctx}/gift/giftConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">礼包名称：</label>
			<div class="controls">
				${giftConfig.giftName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原价：</label>
			<div class="controls">
				${giftConfig.originalPrice}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现价：</label>
			<div class="controls">
				${giftConfig.giftPrice}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品总数量：</label>
			<div class="controls">
				${giftConfig.goodsCount}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " readonly="true"/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">礼包配置商品：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>商品名称</th>
								<th>商品数量</th>
							</tr>
						</thead>
						<tbody id="giftConfigGoodsList">
						</tbody>
					</table>
					<script type="text/template" id="giftConfigGoodsTpl">//<!--
						<tr id="giftConfigGoodsList{{idx}}">
							<td>
								<span id="giftConfigGoodsList{{idx}}_goodsName">{{row.goodsName}}</span>
							</td>
							<td>
								<span id="giftConfigGoodsList{{idx}}_goodsCount"/>{{row.goodsCount}}</span>
							</td>
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
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
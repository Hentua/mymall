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
				<form:input path="goodsCount" htmlEscape="false" maxlength="11" class="input-xlarge required digits number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
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
								<shiro:hasPermission name="gift:giftConfig:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="giftConfigGoodsList">
						</tbody>
						<shiro:hasPermission name="gift:giftConfig:edit"><tfoot>
							<tr><td colspan="5"><a href="javascript:void(0)" class="btn" id="addGoodsBtn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="giftConfigGoodsTpl">//<!--
						<tr id="giftConfigGoodsList{{idx}}">
							<td class="hide">
								<input id="giftConfigGoodsList{{idx}}_id" name="giftConfigGoodsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="giftConfigGoodsList{{idx}}_goodsId" name="giftConfigGoodsList[{{idx}}].goodsId" type="hidden" value="{{row.goodsId}}"/>
								<input id="giftConfigGoodsList{{idx}}_merchantCode" name="giftConfigGoodsList[{{idx}}].merchantCode" type="hidden" value="{{row.merchantCode}}"/>
								<input id="giftConfigGoodsList{{idx}}_delFlag" name="giftConfigGoodsList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<span id="giftConfigGoodsList{{idx}}_goodsName">{{row.goodsName}}</span>
							</td>
							<td>
								<input id="giftConfigGoodsList{{idx}}_goodsCount" name="giftConfigGoodsList[{{idx}}].goodsCount" type="text" value="{{row.goodsCount}}" maxlength="11" class="input-small required number"/>
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
					<script type="text/javascript">
                        $("#addGoodsBtn").click(function(){
                            top.$.jBox.open("iframe:${ctx}/goods/goodsInfo/selectList", "选择商品", 1200, $(top.document).height()-280, {
                                buttons:{"确定":"ok","关闭":true}, submit:function(v, h, f){
                                    if (v=="ok"){
                                        addRow('#giftConfigGoodsList', giftConfigGoodsRowIdx, giftConfigGoodsTpl, {goodsId: h.find("iframe")[0].contentWindow.$("#goodsId").val(), goodsName: h.find("iframe")[0].contentWindow.$("#goodsName").val(), merchantCode: h.find("iframe")[0].contentWindow.$("#merchantCode").val()});
                                        giftConfigGoodsRowIdx = giftConfigGoodsRowIdx + 1;
                                    }
                                }, loaded:function(h){
                                    $(".jbox-content", top.document).css("overflow-y","hidden");
                                }
                            });
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
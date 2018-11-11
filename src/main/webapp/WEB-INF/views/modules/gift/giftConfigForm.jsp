<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>礼包配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        var selectedGoodsId = [];
        var selectedCouponIds = [];
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
		function delRow(obj, prefix, type, specialId){
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
			if(1 == type) {
			    selectedCouponIds.remove(specialId);
			}else if (2 == type) {
			    selectedGoodsId.remove(specialId);
			}
		}
		Array.prototype.remove = function(val) {
            var index = this.indexOf(val);
            if (index > -1) {
                this.splice(index, 1);
            }
        };
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
				<c:choose>
					<c:when test="${empty giftConfig.id }">
						<form:input path="giftName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</c:when>
					<c:otherwise>
						${giftConfig.giftName}
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">礼包分类：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${empty giftConfig.id }">
						<form:select path="giftCategory">
							<form:options items="${giftConfigCategoryList}" itemValue="id" itemLabel="categoryName"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</c:when>
					<c:otherwise>
						${giftConfig.giftCategoryName}
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否在APP显示商品价格：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${empty giftConfig.id }">
						<form:radiobutton path="showGoodsPrice" label="是" value="1"/>
						<form:radiobutton path="showGoodsPrice" label="否" value="0" checked="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${giftConfig.showGoodsPrice == '0'}">否</c:when>
							<c:when test="${giftConfig.showGoodsPrice == '1'}">是</c:when>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">礼包优惠券：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>优惠券名称</th>
								<th>优惠券类型</th>
								<th>优惠券金额</th>
								<th>优惠券数量</th>
								<shiro:hasPermission name="gift:giftConfig:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="giftConfigCouponList">
						</tbody>
						<c:if test="${empty giftConfig.id }">
							<tfoot>
							<tr><td colspan="4"><a href="javascript:" id="addCouponBtn" class="btn">新增</a></td></tr>
							</tfoot>
						</c:if>
					</table>
					<script type="text/template" id="giftConfigCouponTpl">//<!--
						<tr id="giftConfigCouponList{{idx}}">
							<td class="hide">
								<input id="giftConfigCouponList{{idx}}_id" name="giftConfigCouponList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="giftConfigCouponList{{idx}}_delFlag" name="giftConfigCouponList[{{idx}}].delFlag" type="hidden" value="0"/>
								<input id="giftConfigCouponList{{idx}}_couponId" name="giftConfigCouponList[{{idx}}].couponId" type="hidden" value="{{row.couponId}}"/>
							</td>
							<td>
								<span id="giftConfigCouponList{{idx}}_couponName">{{row.couponName}}</span>
							</td>
							<td>
								<span id="giftConfigCouponList{{idx}}_couponTypeName">{{row.couponTypeName}}</span>
							</td>
							<td>
								<span id="giftConfigCouponList{{idx}}_limitAmount">{{row.limitAmount}}</span>
							</td>
							<td>
								<input id="giftConfigCouponList{{idx}}_couponCount" name="giftConfigCouponList[{{idx}}].couponCount" type="text" value="{{row.couponCount}}" maxlength="11" class="input-small required digits" ${not empty giftConfig.id ? 'readonly':''}/>
							</td>
							<c:if test="${empty giftConfig.id}">
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#giftConfigCouponList{{idx}}', '1', '{{row.couponId}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
							</c:if>
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
					<script type="text/javascript">
                        $("#addCouponBtn").click(function(){
                            top.$.jBox.open("iframe:${ctx}/coupon/couponConfig/selectList", "选择优惠券", 1200, $(top.document).height()-280, {
                                buttons:{"确定":"ok","关闭":true}, submit:function(v, h, f){
                                    if (v=="ok"){
                                        var couponName = h.find("iframe")[0].contentWindow.$("#couponShowName").val();
                                        var couponId = h.find("iframe")[0].contentWindow.$("#couponId").val();
                                        var limitAmount = h.find("iframe")[0].contentWindow.$("#limitAmount").val();
                                        var couponType = h.find("iframe")[0].contentWindow.$("#couponType").val();
                                        var couponTypeName;
                                        if(couponType == '0') {
                                            couponTypeName = '五折券';
										}else if(couponType == '1') {
                                            couponTypeName = '七折券';
										}
                                        if(couponId == '') {
                                            $.jBox.alert('未选择优惠券', '警告');
										} else if(selectedCouponIds.indexOf(couponId) >= 0) {
                                            $.jBox.alert('该优惠券已选择，不可重复添加', '警告');
										}else {
                                            selectedCouponIds.push(couponId);
                                            addRow('#giftConfigCouponList', giftConfigCouponRowIdx, giftConfigCouponTpl,
                                                {couponName: couponName,
                                                    couponId: couponId,
                                                    limitAmount: limitAmount,
                                                    couponTypeName: couponTypeName
                                                });
                                            giftConfigCouponRowIdx = giftConfigCouponRowIdx + 1;
										}
                                    }
                                }, loaded:function(h){
                                    $(".jbox-content", top.document).css("overflow-y","hidden");
                                }
                            });
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
								<th>商品</th>
								<th>商品规格</th>
								<th>数量</th>
								<th>单个商品结算价格</th>
								<shiro:hasPermission name="gift:giftConfig:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="giftConfigGoodsList">
						</tbody>
						<c:if test="${empty giftConfig.id }">
							<tfoot>
							<tr><td colspan="5"><a href="javascript:void(0)" class="btn" id="addGoodsBtn">新增</a></td></tr>
							</tfoot>
						</c:if>
					</table>
					<script type="text/template" id="giftConfigGoodsTpl">//<!--
						<tr id="giftConfigGoodsList{{idx}}">
							<td class="hide">
								<input id="giftConfigGoodsList{{idx}}_id" name="giftConfigGoodsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="giftConfigGoodsList{{idx}}_delFlag" name="giftConfigGoodsList[{{idx}}].delFlag" type="hidden" value="0"/>
								<input id="giftConfigGoodsList{{idx}}_goodsStandardId" name="giftConfigGoodsList[{{idx}}].goodsStandardId" type="hidden" value="{{row.goodsStandardId}}"/>
								<input id="giftConfigGoodsList{{idx}}_merchantCode" name="giftConfigGoodsList[{{idx}}].merchantCode" type="hidden" value="{{row.merchantCode}}"/>
								<input id="giftConfigGoodsList{{idx}}_goodsId" name="giftConfigGoodsList[{{idx}}].goodsId" type="hidden" value="{{row.goodsId}}"/>
							</td>
							<td>
								<span id="giftConfigGoodsList{{idx}}_goodsName" >{{row.goodsName}}</span>
							</td>
							<td>
								<span id="giftConfigGoodsList{{idx}}_standardName" >{{row.standardName}}</span>
							</td>
							<td>
								<input id="giftConfigGoodsList{{idx}}_goodsCount" name="giftConfigGoodsList[{{idx}}].goodsCount" type="text" value="{{row.goodsCount}}" maxlength="64" class="input-small required digits" ${not empty giftConfig.id ? 'readonly':''}/>
							</td>
							<td>
								<input id="giftConfigGoodsList{{idx}}_goodsSettlementPrice" name="giftConfigGoodsList[{{idx}}].goodsSettlementPrice" type="text" value="{{row.goodsSettlementPrice}}" class="input-small required number" ${not empty giftConfig.id ? 'readonly':''}/>
							</td>
							<c:if test="${empty giftConfig.id}">
							<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#giftConfigGoodsList{{idx}}', '2', '{{row.standardId}}')" title="删除">&times;</span>{{/delBtn}}
							</td>
							</c:if>
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
                            top.$.jBox.open("iframe:${ctx}/goods/goodsInfo/selectList?goodsType=2", "选择商品", 1200, $(top.document).height()-280, {
                                buttons:{"确定":"ok","关闭":true}, submit:function(v, h, f){
                                    if (v=="ok"){
                                        var standardId = h.find("iframe")[0].contentWindow.$("#standardId").val();
                                        if(standardId == '') {
                                            $.jBox.alert('未选择商品', '警告');
										} else if(selectedGoodsId.indexOf(standardId) >= 0) {
                                            $.jBox.alert('该商品规格已选择，不可重复添加', '警告');
                                        }else {
                                            selectedGoodsId.push(standardId);
                                            addRow('#giftConfigGoodsList', giftConfigGoodsRowIdx, giftConfigGoodsTpl,
                                                {goodsId: h.find("iframe")[0].contentWindow.$("#goodsId").val(),
                                                    goodsName: h.find("iframe")[0].contentWindow.$("#goodsShowName").val(),
                                                    merchantCode: h.find("iframe")[0].contentWindow.$("#merchantCode").val(),
                                                    goodsStandardId:standardId,
                                                    standardName: h.find("iframe")[0].contentWindow.$("#standardName").val()
                                                });
                                            giftConfigGoodsRowIdx = giftConfigGoodsRowIdx + 1;
										}
                                    }
                                }, loaded:function(h){
                                    $(".jbox-content", top.document).css("overflow-y","hidden");
                                }
                            });
                        });
					</script>
				</div>
				<div class="control-group">
					<label class="control-label">备注：</label>
					<div class="controls">
						<c:choose>
							<c:when test="${empty giftConfig.id }">
								<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " />
								<span class="help-inline"><font color="red">*</font> </span>
							</c:when>
							<c:otherwise>
								<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " readonly="true"/>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		<div class="form-actions">
			<c:if test="${empty giftConfig.id }">
				<shiro:hasPermission name="gift:giftConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
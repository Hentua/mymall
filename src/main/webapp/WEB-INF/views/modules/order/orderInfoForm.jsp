<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单信息管理</title>
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
		<li><a href="${ctx}/order/orderInfo/">订单信息列表</a></li>
		<li class="active"><a href="${ctx}/order/orderInfo/form?id=${orderInfo.id}">订单信息<shiro:hasPermission name="order:orderInfo:edit">${not empty orderInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:orderInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderInfo" action="${ctx}/order/orderInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">订单号：</label>
			<div class="controls">
				<form:input path="orderNo" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卖家店铺ID：</label>
			<div class="controls">
				<form:input path="merchantCode" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单状态（0-待付款，1-已付款，2-已取消，3-退款申请，4-已退款）：</label>
			<div class="controls">
				<form:input path="orderStatus" htmlEscape="false" maxlength="2" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单类型（0-平台自主下单，1-礼包兑换）：</label>
			<div class="controls">
				<form:input path="orderType" htmlEscape="false" maxlength="2" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品总数量：</label>
			<div class="controls">
				<form:input path="goodsCount" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单商品总金额：</label>
			<div class="controls">
				<form:input path="goodsAmountTotal" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">运费：</label>
			<div class="controls">
				<form:input path="logisticsFee" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单应付总金额：</label>
			<div class="controls">
				<form:input path="orderAmountTotal" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单优惠扣减金额：</label>
			<div class="controls">
				<form:input path="discountAmountTotal" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">买家ID：</label>
			<div class="controls">
				<form:input path="customerCode" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台记录的支付时间：</label>
			<div class="controls">
				<input name="payTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orderInfo.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">支付渠道记录的支付时间：</label>
			<div class="controls">
				<input name="payChannelTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orderInfo.payChannelTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">订单商品表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>商品ID</th>
								<th>商品类别ID</th>
								<th>商品名称</th>
								<th>商品条码</th>
								<th>商品标题</th>
								<th>商品类别</th>
								<th>商品单位</th>
								<th>商品图片</th>
								<th>商品描述</th>
								<th>商品价格</th>
								<th>折扣比例</th>
								<th>折扣金额</th>
								<th>购买数量</th>
								<th>小计金额</th>
								<shiro:hasPermission name="order:orderInfo:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="orderGoodsList">
						</tbody>
						<shiro:hasPermission name="order:orderInfo:edit"><tfoot>
							<tr><td colspan="16"><a href="javascript:" onclick="addRow('#orderGoodsList', orderGoodsRowIdx, orderGoodsTpl);orderGoodsRowIdx = orderGoodsRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="orderGoodsTpl">//<!--
						<tr id="orderGoodsList{{idx}}">
							<td class="hide">
								<input id="orderGoodsList{{idx}}_id" name="orderGoodsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="orderGoodsList{{idx}}_delFlag" name="orderGoodsList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_goodsId" name="orderGoodsList[{{idx}}].goodsId" type="text" value="{{row.goodsId}}" maxlength="64" class="input-small required"/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_goodsCategoryId" name="orderGoodsList[{{idx}}].goodsCategoryId" type="text" value="{{row.goodsCategoryId}}" maxlength="64" class="input-small required"/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_goodsName" name="orderGoodsList[{{idx}}].goodsName" type="text" value="{{row.goodsName}}" maxlength="20" class="input-small "/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_goodsBarcode" name="orderGoodsList[{{idx}}].goodsBarcode" type="text" value="{{row.goodsBarcode}}" maxlength="20" class="input-small "/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_goodsTitle" name="orderGoodsList[{{idx}}].goodsTitle" type="text" value="{{row.goodsTitle}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_goodsType" name="orderGoodsList[{{idx}}].goodsType" type="text" value="{{row.goodsType}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_unit" name="orderGoodsList[{{idx}}].unit" type="text" value="{{row.unit}}" maxlength="10" class="input-small "/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_image" name="orderGoodsList[{{idx}}].image" type="text" value="{{row.image}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_goodsDesp" name="orderGoodsList[{{idx}}].goodsDesp" type="text" value="{{row.goodsDesp}}" class="input-small "/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_goodsPrice" name="orderGoodsList[{{idx}}].goodsPrice" type="text" value="{{row.goodsPrice}}" class="input-small  number"/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_discountRate" name="orderGoodsList[{{idx}}].discountRate" type="text" value="{{row.discountRate}}" class="input-small  number"/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_discountAmount" name="orderGoodsList[{{idx}}].discountAmount" type="text" value="{{row.discountAmount}}" class="input-small  number"/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_count" name="orderGoodsList[{{idx}}].count" type="text" value="{{row.count}}" class="input-small  number"/>
							</td>
							<td>
								<input id="orderGoodsList{{idx}}_subtotal" name="orderGoodsList[{{idx}}].subtotal" type="text" value="{{row.subtotal}}" class="input-small  number"/>
							</td>
							<shiro:hasPermission name="order:orderInfo:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#orderGoodsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var orderGoodsRowIdx = 0, orderGoodsTpl = $("#orderGoodsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(orderInfo.orderGoodsList)};
							for (var i=0; i<data.length; i++){
								addRow('#orderGoodsList', orderGoodsRowIdx, orderGoodsTpl, data[i]);
								orderGoodsRowIdx = orderGoodsRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">订单物流信息表（包括退货订单物流信息）：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>物流编号</th>
								<th>收货人姓名</th>
								<th>收货人联系电话</th>
								<th>备用联系电话</th>
								<th>收货地址</th>
								<th>邮政编码</th>
								<th>物流类型</th>
								<th>物流商家编号</th>
								<th>物流费</th>
								<th>快递公司代收货款费率，如货值的2%-5%，一般月结</th>
								<th>实际物流成本</th>
								<th>物流状态</th>
								<th>物流结算状态（0-未结算，1-已结算，2-部分结算）</th>
								<th>物流最后状态描述</th>
								<th>物流描述</th>
								<th>发货时间</th>
								<th>物流更新时间</th>
								<th>物流结算时间</th>
								<th>物流支付渠道</th>
								<th>物流支付单号</th>
								<th>物流公司对账状态（0-未对账，1-已对账）</th>
								<th>物流公司对账时间</th>
								<shiro:hasPermission name="order:orderInfo:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="orderLogisticsList">
						</tbody>
						<shiro:hasPermission name="order:orderInfo:edit"><tfoot>
							<tr><td colspan="24"><a href="javascript:" onclick="addRow('#orderLogisticsList', orderLogisticsRowIdx, orderLogisticsTpl);orderLogisticsRowIdx = orderLogisticsRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="orderLogisticsTpl">//<!--
						<tr id="orderLogisticsList{{idx}}">
							<td class="hide">
								<input id="orderLogisticsList{{idx}}_id" name="orderLogisticsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="orderLogisticsList{{idx}}_delFlag" name="orderLogisticsList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_expressNo" name="orderLogisticsList[{{idx}}].expressNo" type="text" value="{{row.expressNo}}" maxlength="100" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_consigneeRealname" name="orderLogisticsList[{{idx}}].consigneeRealname" type="text" value="{{row.consigneeRealname}}" maxlength="100" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_consigneeTelphone" name="orderLogisticsList[{{idx}}].consigneeTelphone" type="text" value="{{row.consigneeTelphone}}" maxlength="20" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_consigneeTelphoneBackup" name="orderLogisticsList[{{idx}}].consigneeTelphoneBackup" type="text" value="{{row.consigneeTelphoneBackup}}" maxlength="20" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_consigneeAddress" name="orderLogisticsList[{{idx}}].consigneeAddress" type="text" value="{{row.consigneeAddress}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_consigneeZip" name="orderLogisticsList[{{idx}}].consigneeZip" type="text" value="{{row.consigneeZip}}" maxlength="10" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_logisticsType" name="orderLogisticsList[{{idx}}].logisticsType" type="text" value="{{row.logisticsType}}" maxlength="20" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_logisticsId" name="orderLogisticsList[{{idx}}].logisticsId" type="text" value="{{row.logisticsId}}" maxlength="100" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_logisticsFee" name="orderLogisticsList[{{idx}}].logisticsFee" type="text" value="{{row.logisticsFee}}" class="input-small  number"/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_agencyFee" name="orderLogisticsList[{{idx}}].agencyFee" type="text" value="{{row.agencyFee}}" class="input-small  number"/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_deliveryAmount" name="orderLogisticsList[{{idx}}].deliveryAmount" type="text" value="{{row.deliveryAmount}}" class="input-small  number"/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_orderlogisticsStatus" name="orderLogisticsList[{{idx}}].orderlogisticsStatus" type="text" value="{{row.orderlogisticsStatus}}" maxlength="10" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_logisticsSettlementStatus" name="orderLogisticsList[{{idx}}].logisticsSettlementStatus" type="text" value="{{row.logisticsSettlementStatus}}" maxlength="1" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_logisticsResultLast" name="orderLogisticsList[{{idx}}].logisticsResultLast" type="text" value="{{row.logisticsResultLast}}" maxlength="100" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_logisticsResult" name="orderLogisticsList[{{idx}}].logisticsResult" type="text" value="{{row.logisticsResult}}" maxlength="255" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_logisticsCreateTime" name="orderLogisticsList[{{idx}}].logisticsCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
									value="{{row.logisticsCreateTime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_logisticsUpdateTime" name="orderLogisticsList[{{idx}}].logisticsUpdateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
									value="{{row.logisticsUpdateTime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_logisticsSettlementTime" name="orderLogisticsList[{{idx}}].logisticsSettlementTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
									value="{{row.logisticsSettlementTime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_logisticsPayChannel" name="orderLogisticsList[{{idx}}].logisticsPayChannel" type="text" value="{{row.logisticsPayChannel}}" maxlength="2" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_logisticsPayNo" name="orderLogisticsList[{{idx}}].logisticsPayNo" type="text" value="{{row.logisticsPayNo}}" maxlength="100" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_reconciliationStatus" name="orderLogisticsList[{{idx}}].reconciliationStatus" type="text" value="{{row.reconciliationStatus}}" maxlength="1" class="input-small "/>
							</td>
							<td>
								<input id="orderLogisticsList{{idx}}_reconciliationTime" name="orderLogisticsList[{{idx}}].reconciliationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
									value="{{row.reconciliationTime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<shiro:hasPermission name="order:orderInfo:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#orderLogisticsList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var orderLogisticsRowIdx = 0, orderLogisticsTpl = $("#orderLogisticsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(orderInfo.orderLogistics)};
							for (var i=0; i<data.length; i++){
								addRow('#orderLogisticsList', orderLogisticsRowIdx, orderLogisticsTpl, data[i]);
								orderLogisticsRowIdx = orderLogisticsRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:orderInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
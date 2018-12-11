<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>订单信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });

        function addRow(list, idx, tpl, row) {
            $(list).append(Mustache.render(tpl, {
                idx: idx, delBtn: true, row: row
            }));
            $(list + idx).find("select").each(function () {
                $(this).val($(this).attr("data-value"));
            });
            $(list + idx).find("input[type='checkbox'], input[type='radio']").each(function () {
                var ss = $(this).attr("data-value").split(',');
                for (var i = 0; i < ss.length; i++) {
                    if ($(this).val() == ss[i]) {
                        $(this).attr("checked", "checked");
                    }
                }
            });
        }

        function delRow(obj, prefix) {
            var id = $(prefix + "_id");
            var delFlag = $(prefix + "_delFlag");
            if (id.val() == "") {
                $(obj).parent().parent().remove();
            } else if (delFlag.val() == "0") {
                delFlag.val("1");
                $(obj).html("&divide;").attr("title", "撤销删除");
                $(obj).parent().parent().addClass("error");
            } else if (delFlag.val() == "1") {
                delFlag.val("0");
                $(obj).html("&times;").attr("title", "删除");
                $(obj).parent().parent().removeClass("error");
            }
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/order/orderInfo/merchantList">订单信息列表</a></li>
    <li class="active"><a href="${ctx}/order/orderInfo/orderDelivery?id=${orderInfo.id}">订单详情</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="orderInfo" action="${ctx}/order/orderInfo/orderDeliverySave" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="orderNo"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">订单号：</label>
        <div class="controls">
                ${orderInfo.orderNo}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单状态：</label>
        <div class="controls">
                ${orderInfo.orderStatusZh}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单类型：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${orderInfo.orderType eq '0'}">
                    自主下单
                </c:when>
                <c:when test="${orderInfo.orderType eq '1'}">
                    礼包兑换
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商品总数量：</label>
        <div class="controls">
                ${orderInfo.goodsCount}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">商品总金额：</label>
        <div class="controls">
                ${orderInfo.goodsAmountTotal}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">优惠扣减金额：</label>
        <div class="controls">
                ${orderInfo.discountAmountTotal}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">活动扣减金额：</label>
        <div class="controls">
                ${orderInfo.activityDiscountAmount}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">运费：</label>
        <div class="controls">
                ${orderInfo.logisticsFee}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单金额：</label>
        <div class="controls">
                ${orderInfo.orderAmountTotal}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">应收货款：</label>
        <div class="controls">
                ${orderInfo.settlementsAmount}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">支付时间：</label>
        <div class="controls">
            <fmt:formatDate value="${orderInfo.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">收货人：</label>
        <div class="controls">
                ${orderInfo.orderLogistics.consigneeRealname}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">收货人联系方式：</label>
        <div class="controls">
                ${orderInfo.orderLogistics.consigneeTelphone}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">收货地址：</label>
        <div class="controls">
                ${orderInfo.orderLogistics.consigneeAddress}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">物流公司：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${orderInfo.orderStatus eq '1'}">
                    <form:select path="orderLogistics.logisticsType" class="input-xlarge ">
                        <form:options items="${fns:getDictList('express_type')}" itemLabel="label" itemValue="value"
                                      htmlEscape="false"/>
                    </form:select>
                </c:when>
                <c:otherwise>
                    ${orderInfo.orderLogistics.logisticsType}
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">快递单号：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${orderInfo.orderStatus eq '1'}">
                    <form:input path="orderLogistics.expressNo" htmlEscape="false" class="input-xlarge"/>
                </c:when>
                <c:otherwise>
                    ${orderInfo.orderLogistics.expressNo}
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">买家：</label>
        <div class="controls">
                ${orderInfo.customerName}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">买家留言：</label>
        <div class="controls">
                ${orderInfo.remarks}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单商品：</label>
        <div class="controls">
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th class="hide"></th>
                    <th>商品名称</th>
                    <th>商品条码</th>
                    <th>商品标题</th>
                    <th>购买数量</th>
                    <th>商品单位</th>
                    <th>商品图片</th>
                    <th>商品价格</th>
                    <th>优惠金额</th>
                    <th>折扣金额</th>
                    <th>小计金额</th>
                </tr>
                </thead>
                <tbody id="orderGoodsList">
                </tbody>
            </table>
            <script type="text/template" id="orderGoodsTpl">//<!--
						<tr id="orderGoodsList{{idx}}">
							<td>
								{{row.goodsName}}
							</td>
							<td>
								{{row.goodsBarcode}}
							</td>
							<td>
								{{row.goodsTitle}}
							</td>
							<td>
								{{row.count}}
							</td>
							<td>
								{{row.unit}}
							</td>
							<td>
								<img src="{{row.fullImageUrl}}" width="50px"/>
							</td>
							<td>
								{{row.goodsPrice}}
							</td>
							<td>
								{{row.discountAmount}}
							</td>
							<td>
								{{row.activityDiscountAmount}}
							</td>
							<td>
								{{row.subtotal}}
							</td>
						</tr>//-->
            </script>
            <script type="text/javascript">
                var orderGoodsRowIdx = 0,
                    orderGoodsTpl = $("#orderGoodsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
                $(document).ready(function () {
                    var data = ${fns:toJson(orderInfo.orderGoodsList)};
                    for (var i = 0; i < data.length; i++) {
                        addRow('#orderGoodsList', orderGoodsRowIdx, orderGoodsTpl, data[i]);
                        orderGoodsRowIdx = orderGoodsRowIdx + 1;
                    }
                });
            </script>
        </div>
    </div>
    <div class="form-actions">
        <c:if test="${orderInfo.orderStatus eq '1'}"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                                value="发 货"/>&nbsp;</c:if>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
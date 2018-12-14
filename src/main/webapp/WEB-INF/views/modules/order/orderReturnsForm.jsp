<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>售后申请管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        var checkResult = '';
        var type = '';
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    if(type == 'refund') {
                        $.jBox.open("iframe:${ctx}/member/memberInfo/checkPayPasswordForm?id=${fns:getUser().id}&failedCallbackUrl=${ctx}/member/memberInfo/checkPayPasswordResultDialog?checkResult=0&successCallbackUrl=${ctx}/member/memberInfo/checkPayPasswordResultDialog?checkResult=1", "美易验证", 1200, $(top.document).height() - 280, {
                            buttons: {"关闭": true}, submit: function (v, h, f) {
                            }, loaded: function (h) {
                                $(".jbox-content", top.document).css("overflow-y", "hidden");
                            },
                            closed: function() {
                                if(checkResult == '1') {
                                    loading('正在提交，请稍等...');
                                    form.submit();
                                }else {
                                    jBox.close();
                                    closeLoading();
                                }
                            }
                        });
                    }else {
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
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
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/order/orderReturns/">售后申请列表</a></li>
    <li class="active"><a href="${ctx}/order/orderReturns/form?id=${orderReturns.id}">售后申请详情</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="orderReturns" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">售后单号：</label>
        <div class="controls">
                ${orderReturns.returnsNo}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">原订单号：</label>
        <div class="controls">
                ${orderReturns.orderNo}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">服务类型：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${orderReturns.handlingWay == '0'}">退货退款</c:when>
                <c:when test="${orderReturns.handlingWay == '1'}">换新</c:when>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">退款金额：</label>
        <div class="controls">
                ${orderReturns.returnsAmount}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人：</label>
        <div class="controls">
                ${orderReturns.customerName}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请人账号：</label>
        <div class="controls">
                ${orderReturns.customerMobile}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">售后申请时间：</label>
        <div class="controls">
            <fmt:formatDate value="${orderReturns.returnSubmitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">售后状态：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${orderReturns.status == '0'}">待审核</c:when>
                <c:when test="${orderReturns.status == '1'}">待处理</c:when>
                <c:when test="${orderReturns.status == '2'}">待收货</c:when>
                <c:when test="${orderReturns.status == '3'}">已完成</c:when>
                <c:when test="${orderReturns.status == '4'}">审核未通过</c:when>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">售后原因：</label>
        <div class="controls">
            <form:textarea path="reason" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "
                           readonly="true"/>
        </div>
    </div>
    <c:if test="${orderReturns.handlingWay == '1'}">
        <div class="control-group">
            <label class="control-label">收货人姓名：</label>
            <div class="controls">
                    ${orderReturns.consigneeRealname}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">联系电话：</label>
            <div class="controls">
                    ${orderReturns.consigneeTelphone}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">收货地址：</label>
            <div class="controls">
                    ${orderReturns.consigneeAddress}
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">邮政编码：</label>
            <div class="controls">
                    ${orderReturns.consigneeZip}
            </div>
        </div>
        <c:if test="${orderReturns.status != '0'}">
            <div class="control-group">
                <label class="control-label">物流方式：</label>
                <div class="controls">
                    <c:choose>
                        <c:when test="${orderReturns.status == '1'}">
                            <form:select path="logisticsType" class="input-xlarge ">
                                <form:options items="${fns:getDictList('express_type')}" itemLabel="label" itemValue="value"
                                              htmlEscape="false"/>
                            </form:select>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(orderReturns.logisticsType, 'express_type', '')}
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">快递单号：</label>
                <div class="controls">
                    <c:choose>
                        <c:when test="${orderReturns.status == '1'}">
                            <form:input path="expressNo" htmlEscape="false" maxlength="100" class="input-xlarge "/>
                            <span class="help-inline"><font color="red">*</font> </span>
                        </c:when>
                        <c:otherwise>
                            ${orderReturns.expressNo}
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:if>
    </c:if>
    <div class="control-group">
        <label class="control-label">售后回复：</label>
        <div class="controls">
            <form:textarea path="reply" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge required"/>
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
        <label class="control-label">平台回复：</label>
        <div class="controls">
            <form:textarea path="platformReply" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge" readonly="true"/>
        </div>
    </div>
    <div class="form-actions">
        <c:if test="${orderReturns.status == '0'}">
            <shiro:hasPermission name="order:orderReturns:check">
                <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="this.form.action='${ctx}/order/orderReturns/checkPass';"
                       value="审核通过"/>&nbsp;
            </shiro:hasPermission>
            <shiro:hasPermission name="order:orderReturns:check">
                <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="this.form.action='${ctx}/order/orderReturns/checkNotPass';"
                       value="审核不通过"/>&nbsp;
            </shiro:hasPermission>
        </c:if>
        <shiro:hasPermission name="order:orderReturns:send">
            <c:choose>
                <c:when test="${orderReturns.status == '1' && orderReturns.handlingWay == '1'}">
                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="发货" onclick="this.form.action='${ctx}/order/orderReturns/delivery';"/>&nbsp;
                </c:when>
                <c:when test="${orderReturns.status == '1' && orderReturns.handlingWay == '0'}">
                    <input id="btnSubmit" class="btn btn-primary" type="button" value="退款" onclick="submitRefund()"/>&nbsp;
                </c:when>
            </c:choose>
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
<script>
    function submitRefund() {
        type = 'refund';
        var inputForm = $('#inputForm');
        inputForm[0].action = '${ctx}/order/orderReturns/refund';
        inputForm.submit();
    }
</script>
</body>
</html>
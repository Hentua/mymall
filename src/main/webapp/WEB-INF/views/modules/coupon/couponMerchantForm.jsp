<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商家优惠券管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    // form.submit();
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
    <li><a href="${ctx}/coupon/couponMerchant/">商家优惠券</a></li>
    <li class="active"><a href="${ctx}/coupon/couponMerchant/form?id=${couponMerchant.id}">优惠券赠送</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="couponMerchant" action="${ctx}/coupon/couponMerchant/transfer" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">优惠券类型：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${couponMerchant.couponType == '0'}">
                    五折券
                </c:when>
                <c:when test="${couponMerchant.couponType == '1'}">
                    七折券
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">可用余额：</label>
        <div class="controls">
                ${couponMerchant.balance}
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">赠送给：</label>
        <div class="controls">
            <form:input path="customerReferee" htmlEscape="false" maxlength="20" class="input-xlarge required"
                        onkeyup="getMemberInfo()" onchange="getMemberInfo()"/>
            <span class="help-inline"><font color="red">* 请填写要赠送会员的手机号或ID号</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">获赠会员：</label>
        <div class="controls">
            <span id="transferToMember"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">赠送金额：</label>
        <div class="controls">
            <form:input path="transferAmount" htmlEscape="false" maxlength="20" class="input-xlarge required digits"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="赠 送"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
<script>
    function getMemberInfo() {
        $('#customerReferee').keyup(function () {
            if ($('#customerReferee').val().length == 11 || $('#customerReferee').val().length == 13) {
                $.post('${ctx}/member/memberInfo/getMemberInfo', {referee: $('#customerReferee').val()}, function (data) {
                    if(data.nickname) {
                        $('#transferToMember').text(data.nickname + '(' + data.mobile + ')');
                    }else {
                        $('#transferToMember').text('请填写正确的会员手机号或ID号');
                    }
                })
            }else {
                $('#transferToMember').text('请填写正确的会员手机号或ID号');
            }
        });
    }
</script>
</body>
</html>
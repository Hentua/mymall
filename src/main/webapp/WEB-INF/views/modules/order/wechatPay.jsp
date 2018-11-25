<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>微信支付</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            closeLoading();
            // 每秒查询订单状态
            setInterval(function() {
                $.post("${ctx}/payment/queryPaymentInfo", {id: ${orderPaymentInfo.id}}, function (data) {
                    var paymentStatus = data.paymentStatus;
                    if(paymentStatus == '1') {
                        alertx('付款成功，即将跳转页面');
                        setTimeout(function () {
                            location.href = '${callbackUrl}';
                        }, 1000);
                    }
                });
            }, 2000);
        });
    </script>
</head>
<body style="overflow-x: hidden;">
<div><img src="${ctxStatic}/images/WePayLogo.png" alt="微信支付" width="200px"/></div>
<div style="background-color: #CCCCCC; width: 100%;padding-left: 30px;padding-top: 20px;padding-bottom: 20px;">
    订单编号：${orderPaymentInfo.paymentNo}<br/>
    应付金额：${orderPaymentInfo.amountTotal}
</div>
<div style="width: 100%; align-items: center;" align="center"><img src="${ctx}/payment/genQRCode?codeUrl=${orderPaymentInfo.codeUrl}" alt="二维码"/></div>
<div style="width: 100%; align-items: center;" align="center"><img src="${ctxStatic}/images/weixinPayTips.png" alt="tips"/></div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>验证支付密码</title>
    <meta name="decorator" content="default"/>
    <style>
        .wrap {
            margin: 10px auto;
            width: 329px;
            height: 640px;
            padding-top: 200px;
        }

        .inputBoxContainer {
            width: 240px;
            height: 50px;
            margin: 0 auto;
            position: relative;
        }

        .inputBoxContainer .bogusInput {
            width: 100%;
            height: 100%;
            border: #c3c3c3 1px solid;
            border-radius: 7px;
            -moz-border-radius: 7px;
            -webkit-border-radius: 7px;
            overflow: hidden;
            position: absolute;
            z-index: 0;
        }

        .inputBoxContainer .realInput {
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
            z-index: 1;
            filter: alpha(opacity=0);
            -moz-opacity: 0;
            opacity: 0;
        }

        .inputBoxContainer .bogusInput input {
            padding: 0;
            width: 16.3%;
            height: 100%;
            float: left;
            background: #ffffff;
            text-align: center;
            font-size: 20px;
            border: none;
            border-right: #C3C3C3 1px solid;
        }

        .inputBoxContainer .bogusInput input:last-child {
            border: none;
        }

        .confirmButton {
            width: 240px;
            height: 45px;
            border-radius: 7px;
            -moz-border-radius: 7px;
            -webkit-border-radius: 7px;
            background: #f4f4f4;
            border: #d5d5d5 1px solid;
            display: block;
            font-size: 16px;
            margin: 30px auto;
            margin-bottom: 20px;
        }

        .showValue {
            width: 240px;
            height: 22px;
            line-height: 22px;
            font-size: 16px;
            text-align: center;
            margin: 0 auto;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
        });
    </script>
</head>
<body style="overflow-x: hidden;">
<div style="height: 40px;margin-top: 10px;"><span style="font-size: 30px;">美易验证</span></div>
<div style="background-color: #CCCCCC; width: 90%;padding-left: 30px;padding-top: 20px;padding-bottom: 20px;">
    会员名称：${memberInfo.nickname}<br/>
    会员账号：${memberInfo.mobile}<br/>
    会员ID：${memberInfo.referee}
</div>
<div style="width: 90%; align-items: center; margin-top: 20px;" align="center">
    <div class="wrap">
        <span style="">请输入您的支付密码：</span>
        <div class="inputBoxContainer" id="inputBoxContainer">
            <input type="text" class="realInput"/>
            <div class="bogusInput">
                <input type="password" maxlength="6" disabled/>
                <input type="password" maxlength="6" disabled/>
                <input type="password" maxlength="6" disabled/>
                <input type="password" maxlength="6" disabled/>
                <input type="password" maxlength="6" disabled/>
                <input type="password" maxlength="6" disabled/>
            </div>
        </div>
        <button id="confirmButton" class="confirmButton">提交</button>
    </div>
</div>
<script type="text/javascript">
    (function () {
        var container = document.getElementById("inputBoxContainer");
        boxInput = {
            maxLength: "",
            realInput: "",
            bogusInput: "",
            bogusInputArr: "",
            callback: "",
            init: function (fun) {
                var that = this;
                this.callback = fun;
                that.realInput = container.children[0];
                that.bogusInput = container.children[1];
                that.bogusInputArr = that.bogusInput.children;
                that.maxLength = that.bogusInputArr[0].getAttribute("maxlength");
                that.realInput.oninput = function () {
                    that.setValue();
                }
                that.realInput.onpropertychange = function () {
                    that.setValue();
                }
            },
            setValue: function () {
                this.realInput.value = this.realInput.value.replace(/\D/g, "");
                var real_str = this.realInput.value;
                for (var i = 0; i < this.maxLength; i++) {
                    this.bogusInputArr[i].value = real_str[i] ? real_str[i] : "";
                }
                if (real_str.length >= this.maxLength) {
                    this.realInput.value = real_str.substring(0, 6);
                    this.callback();
                }
            },
            getBoxInputValue: function () {
                var realValue = "";
                for (var i in this.bogusInputArr) {
                    if (!this.bogusInputArr[i].value) {
                        break;
                    }
                    realValue += this.bogusInputArr[i].value;
                }
                return realValue;
            }
        }
    })()
    boxInput.init(function () {
        getValue();
    });
    document.getElementById("confirmButton").onclick = function () {

        loading('提交中，请稍候...');
        $.post("${ctx}/api/validatePayPassword", {
            payPassword: boxInput.getBoxInputValue()
        }, function (data) {
            closeLoading();
            var status = data.status;
            if (status != '200') {
                alertx('验证失败：' + data.message + "，点击确定将跳转页面", function () {
                    location.href = '${failedCallbackUrl}';
                });
            } else {
                $('body').html('验证成功，即将跳转页面');
                setTimeout(function () {
                    location.href = '${successCallbackUrl}';
                }, 5000);
            }
        });
    }

    function getValue() {
        // document.getElementById("showValue").innerText = boxInput.getBoxInputValue();
    }
</script>
</body>
</html>
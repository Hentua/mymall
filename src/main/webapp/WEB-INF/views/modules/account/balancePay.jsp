<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>余额支付</title>
    <meta name="decorator" content="default"/>
    <style>
        * {
            padding: 0;
            margin: 0;
        }

        .content {
            width: 400px;
            height: 50px;
            margin: 0 auto;
            margin-top: 100px;

        }

        .title {
            font-family: '微软雅黑';
            font-size: 16px;
        }

        .box {
            width: 190px;
            height: 30px;
            border: 1px solid #ccc;
            margin-top: 10px;
            line-height: 30px;
        }

        .content .box, .forget {
            display: inline-block;
        }

        .content .forget {
            width: 100px;
            color: lightskyblue;
            vertical-align: super;
            font-size: 14px;
        }

        .box input.paw {
            width: 30px;
            height: 20px;
            line-height: 20px;
            margin-left: -9px;
            border: none;
            border-right: 1px dashed #ccc;
            text-align: center;
        }

        .box input.paw:nth-child(1) {
            margin-left: 0;
        }

        .content .box .pawDiv:nth-child(6) input.paw {
            border: none;
        }

        .content .box input.paw:focus {
            outline: 0;
        }

        .content .box .pawDiv {
            display: inline-block;
            line-height: 30px;
            width: 31px;
            height: 31px;
            margin-top: -2px;
            float: left;
        }

        .point {
            font-size: 14px;
            color: #ccc;
            margin: 5px 0;
        }

        .errorPoint {
            color: red;
            display: none;
        }

        .getPasswordBtn {
            width: 100px;
            height: 30px;
            background-color: cornflowerblue;
            font-size: 14px;
            font-family: '微软雅黑';
            color: white;
            border: none;
        }
    </style>
    <script type="text/javascript">
        /*动态生成*/
        var box = document.getElementsByClassName("box")[0];

        function createDIV(num) {
            for (var i = 0; i < num; i++) {
                var pawDiv = document.createElement("div");
                pawDiv.className = "pawDiv";
                box.appendChild(pawDiv);
                var paw = document.createElement("input");
                paw.type = "password";
                paw.className = "paw";
                paw.maxLength = "1";
                paw.readOnly = "readonly";
                pawDiv.appendChild(paw);
            }
        }

        createDIV(6);


        var pawDiv = document.getElementsByClassName("pawDiv");
        var paw = document.getElementsByClassName("paw");
        var pawDivCount = pawDiv.length;
        /*设置第一个输入框默认选中*/
        pawDiv[0].setAttribute("style", "border: 2px solid deepskyblue;");
        paw[0].readOnly = false;
        paw[0].focus();

        var errorPoint = document.getElementsByClassName("errorPoint")[0];

        /*绑定pawDiv点击事件*/

        function func() {
            for (var i = 0; i < pawDivCount; i++) {
                pawDiv[i].addEventListener("click", function () {
                    pawDivClick(this);
                });
                paw[i].onkeyup = function (event) {
                    console.log(event.keyCode);
                    if (event.keyCode >= 48 && event.keyCode <= 57) {
                        /*输入0-9*/
                        changeDiv();
                        errorPoint.style.display = "none";

                    } else if (event.keyCode == "8") {
                        /*退格回删事件*/
                        firstDiv();

                    } else if (event.keyCode == "13") {
                        /*回车事件*/
                        getPassword();

                    } else {
                        /*输入非0-9*/
                        errorPoint.style.display = "block";
                        this.value = "";
                    }

                };
            }

        }

        func();


        /*定义pawDiv点击事件*/
        var pawDivClick = function (e) {
            for (var i = 0; i < pawDivCount; i++) {
                pawDiv[i].setAttribute("style", "border:none");
            }
            e.setAttribute("style", "border: 2px solid deepskyblue;");
        };


        /*定义自动选中下一个输入框事件*/
        var changeDiv = function () {
            for (var i = 0; i < pawDivCount; i++) {
                if (paw[i].value.length == "1") {
                    /*处理当前输入框*/
                    paw[i].blur();

                    /*处理上一个输入框*/
                    paw[i + 1].focus();
                    paw[i + 1].readOnly = false;
                    pawDivClick(pawDiv[i + 1]);
                }
            }
        };

        /*回删时选中上一个输入框事件*/
        var firstDiv = function () {
            for (var i = 0; i < pawDivCount; i++) {
                console.log(i);
                if (paw[i].value.length == "0") {
                    /*处理当前输入框*/
                    console.log(i);
                    paw[i].blur();

                    /*处理上一个输入框*/
                    paw[i - 1].focus();
                    paw[i - 1].readOnly = false;
                    paw[i - 1].value = "";
                    pawDivClick(pawDiv[i - 1]);
                    break;
                }
            }
        };


        /*获取输入密码*/
        var getPassword = function () {
            var n = "";
            for (var i = 0; i < pawDivCount; i++) {
                n += paw[i].value;
            }
            alert(n);
        };
        var getPasswordBtn = document.getElementsByClassName("getPasswordBtn")[0];

        getPasswordBtn.addEventListener("click", getPassword);

        /*键盘事件*/
        document.onkeyup = function (event) {
            if (event.keyCode == "13") {
                /*回车事件*/
                getPassword();
            }
        };
    </script>
</head>
<body>
<div class="content">
    <div class="title">支付宝支付密码：</div>
    <div class="box"></div>
    <div class="forget">忘记密码？</div>
    <div class="point">请输入6位数字密码</div>
    <button class="getPasswordBtn">一键获取密码</button>
    <div class="errorPoint">请输入数字！</div>
</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
		html,body,table{background-color: white;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:45px;color:#3459a5;}
		.form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 70% 20px;background-color:#fff;border:1px solid #e5e5e5;
			-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
		.form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
		.form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
		.form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
		.form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
		.header{    height:25px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
		label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}

		#link_tab{
			width: 100%;
		}
		#link_tab tr td{
			padding-top: 13px;

		}
	</style>

	<script type="text/javascript" src="${ctxStatic}/jquery/jquery.waterwheelCarousel.js"></script>
	<script type="text/javascript">
        $(document).ready(function () {
            var carousel = $("#carousel").waterwheelCarousel({
                flankingItems: 3,
                movingToCenter: function ($item) {
                    $('#callback-output').prepend('movingToCenter: ' + $item.attr('id') + '<br/>');
                },
                movedToCenter: function ($item) {
                    $('#callback-output').prepend('movedToCenter: ' + $item.attr('id') + '<br/>');
                },
                movingFromCenter: function ($item) {
                    $('#callback-output').prepend('movingFromCenter: ' + $item.attr('id') + '<br/>');
                },
                movedFromCenter: function ($item) {
                    $('#callback-output').prepend('movedFromCenter: ' + $item.attr('id') + '<br/>');
                },
                clickedCenter: function ($item) {
                    $('#callback-output').prepend('clickedCenter: ' + $item.attr('id') + '<br/>');
                }
            });

            $('#prev').bind('click', function () {
                carousel.prev();
                return false
            });

            $('#next').bind('click', function () {
                carousel.next();
                return false;
            });

            $('#reload').bind('click', function () {
                newOptions = eval("(" + $('#newoptions').val() + ")");
                carousel.reload(newOptions);
                return false;
            });

        });
	</script>

	<%--<style type="text/css">--%>
		<%--body {--%>
			<%--font-family:Arial;--%>
			<%--font-size:12px;--%>
		<%--}--%>
		<%--.example-desc {--%>
			<%--margin:3px 0;--%>
			<%--padding:5px;--%>
		<%--}--%>

		<%--#carousel {--%>
			<%--width:1060px;--%>
			<%--height:300px;--%>
			<%--position:relative;--%>
			<%--clear:both;--%>
			<%--overflow:hidden;--%>
		<%--}--%>
		<%--#carousel img {--%>
			<%--visibility:hidden; /* hide images until carousel can handle them */--%>
			<%--cursor:pointer; /* otherwise it's not as obvious items can be clicked */--%>
		<%--}--%>

		<%--.split-left {--%>
			<%--width:450px;--%>
			<%--float:left;--%>
		<%--}--%>
		<%--.split-right {--%>
			<%--width:400px;--%>
			<%--float:left;--%>
			<%--margin-left:10px;--%>
		<%--}--%>
		<%--#callback-output {--%>
			<%--height:250px;--%>
			<%--overflow:scroll;--%>
		<%--}--%>
		<%--textarea#newoptions {--%>
			<%--width:430px;--%>
		<%--}--%>
	<%--</style>--%>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<%--<div class="header">--%>
		<%--<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>--%>
			<%--<label id="loginError" class="error"></label>--%>
		<%--</div>--%>
	<%--</div>--%>
	<%--<label id="loginError" class="error">${message}</label>--%>
	<%--<h1 class="form-signin-heading">${fns:getConfig('productName')}</h1>--%>
	<%--<!-- 轮播图 -->--%>
	<%--<div id="carousel">--%>
		<%--<a href="#"><img style="width: 600px;height: 280px" src="${ctxStatic}/images/a1.png" id="item-1" /></a>--%>
		<%--<a href="#"><img style="width: 600px;height: 280px" src="${ctxStatic}/images/a2.png" id="item-2" /></a>--%>
		<%--<a href="#"><img style="width: 600px;height: 280px" src="${ctxStatic}/images/a3.png" id="item-3" /></a>--%>
		<%--<a href="#"><img style="width: 600px;height: 280px" src="${ctxStatic}/images/a4.png" id="item-4" /></a>--%>
		<%--<a href="#"><img style="width: 600px;height: 280px" src="${ctxStatic}/images/a5.png" id="item-5" /></a>--%>
	<%--</div>--%>
	<div style="background-image:url('${ctxStatic}/images/backgroud-image-login.png');    padding-top: 100px;
			min-height: 400px;">
		<form id="loginForm" style="    border-radius: 4%;" class="form-signin" action="${ctx}/login" method="post">
			<h1 class="form-signin-heading" style="border-bottom: 1px solid;font-size: 25px;    width: 120%;
    margin-left: -10%; text-align: center;margin-bottom: 20px">登录账号</h1>
			<label class="input-label" for="username">登录名</label>
			<input type="text" id="username" name="username" class="input-block-level required" value="${username}">
			<label class="input-label" for="password">密码</label>
			<input type="password" id="password" name="password" class="input-block-level required">
			<c:if test="${isValidateCodeLogin}"><div class="validateCode">
				<label class="input-label mid" for="validateCode">验证码</label>
				<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
			</div></c:if><%--
		<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
			<input class="btn btn-large btn-primary" style="    border-radius: 2px;width: 100%;" type="submit" value="登 录"/>&nbsp;&nbsp;
			<%--<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）</label>--%>
			<%--<div id="themeSwitch" class="dropdown">--%>
				<%--<a class="dropdown-toggle" data-toggle="dropdown" href="#">${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}<b class="caret"></b></a>--%>
				<%--<ul class="dropdown-menu">--%>
					<%--<c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>--%>
				<%--</ul>--%>
				<%--<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->--%>
			<%--</div>--%>
			<div style="color: red">
				${message}
			</div>
		</form>
	</div>
	<div class="footer" style="margin-top: 17px;padding-left: 25%;padding-right: 25%;">
		 <table id="link_tab">
			 <tr style="font-weight: bold">
				 <td>首页</td>
				 <td>媒体中心</td>
				 <td>核心优势</td>
				 <td>联盟商家</td>
				 <td>关于我们</td>
			 </tr>
			 <tr>
				 <td></td>
				 <td><a href="https://www.meiyiyouxuan.com/news/news/type_id/5.html">公司新闻</a></td>
				 <td><a href="https://www.meiyiyouxuan.com/sight/sight/type_id/1.html">品牌优势</a></td>
				 <td></td>
				 <td><a href="https://www.meiyiyouxuan.com/about/about.html">文化理念</a></td>
			 </tr>
			 <tr>
				 <td></td>
				 <td><a href="https://www.meiyiyouxuan.com/news/news/type_id/2.html">公司介绍</a></td>
				 <td><a href="https://www.meiyiyouxuan.com/sight/sight/type_id/2.html">产品供应链</a></td>
				 <td></td>
				 <td><a href="https://www.meiyiyouxuan.com/about/history.html">发展历程</a></td>
			 </tr>
			 <tr>
				 <td></td>
				 <td></td>
				 <td><a href="https://www.meiyiyouxuan.com">专业团队</a></td>
				 <td></td>
				 <td><a href="https://www.meiyiyouxuan.com">战略规划</a></td>
			 </tr>
			 <tr>
				 <td></td>
				 <td></td>
				 <td></td>
				 <td></td>
				 <td><a href="https://www.meiyiyouxuan.com/contact/contact.html">联系我们</a></td>
			 </tr>
		 </table>
	</div>
	<div class="footer" style="margin-top: 30px">
		Copyright &copy; 2012-${fns:getConfig('copyrightYear')} <a href="${pageContext.request.contextPath}${fns:getFrontPath()}">${fns:getConfig('productName')}</a> ${fns:getConfig('version')}
	</div>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="${ctxStatic}/jingle/css/Jingle.css">
    <link rel="stylesheet" href="${ctxStatic}/jingle/css/app.css">
	<style type="text/css">
		.input-row-r{
			border-bottom:1px solid rgba(0, 0, 0, .2);
			width: 90%;
			margin-left: 5%;
		}
	</style>
</head>
<body>
<div id="aside_container">
</div>
<div id="section_container">
    <section id="login_section" class="active">
        <header style="background-color: white;">
            <h1 class="title" style="color: #222;">注&nbsp;&nbsp;册</h1>
            <!-- <nav class="right">
                <a data-target="section" data-icon="info" href="#about_section"></a>
            </nav> -->
        </header>
        <article data-scroll="true" id="login_article">
	        <div class="indented">
	            <form id="loginForm" action="${ctx}/login" method="post">
	            	<div>&nbsp;</div>
	            	<div class="input-group" style="border: 0px;        -webkit-box-shadow: 0 0px 0px rgba(255, 255, 255, .2), inset 0 0px 0px rgba(0, 0, 0, .1);
    box-shadow: 0 0px 0px rgba(255, 255, 255, .2), inset 0 0px 0px rgba(0, 0, 0, .1);
    -webkit-appearance: none;
    -webkit-user-select: text;
    -webkit-font-smoothing: antialiased;
    -wekbit-box-sizing: border-box;">
		                <div class="input-row-r">
		                    <input type="text"   name="username" placeholder="请填写您的用户昵称">
		                </div>
						<div class="input-row-r">
							<input type="text"   name="username" placeholder="请填写您的手机号码">
						</div>
						<div class="input-row-r">
							<input type="password"   name="username" placeholder="请填写您密码">
						</div>
						<div class="input-row-r">
							<input type="password"   name="username" placeholder="请确认您的密码">
						</div>
						<div class="input-row-r">
							<input type="text"  style="width:72%;border: 0px;" name="username" placeholder="请输入验证码"><a style="color: #ff7600;">获取验证码</a>
						</div>
		            </div>
	            	<div>&nbsp;</div>
	                <button id="btn" class="submit block" style="background-color: #ff7600" >注册</button>
	            </form>
	        </div>
	    </article>
    </section>
</div>
<!--<script type="text/javascript" src="${ctxStatic}/jingle/js/lib/cordova.js"></script>-->
<!-- lib -->
<script type="text/javascript" src="${ctxStatic}/jingle/js/lib/zepto.js"></script>
<script type="text/javascript" src="${ctxStatic}/jingle/js/lib/iscroll.js"></script>
<%-- <script type="text/javascript" src="${ctxStatic}/jingle/js/lib/template.min.js"></script> --%>
<script type="text/javascript" src="${ctxStatic}/jingle/js/lib/Jingle.debug.js"></script>
<script type="text/javascript" src="${ctxStatic}/jingle/js/lib/zepto.touch2mouse.js"></script>
<%-- <script type="text/javascript" src="${ctxStatic}/jingle/js/lib/JChart.debug.js"></script> --%>
<!--- app --->
<script type="text/javascript">var ctx = '${ctx}';</script>
<script type="text/javascript" src="${ctxStatic}/jingle/js/app/app.js"></script>
<!--<script src="http://192.168.2.153:8080/target/target-script-min.js#anonymous"></script>-->
<script type="text/javascript">
var sessionid = '${not empty fns:getPrincipal() ? fns:getPrincipal().sessionid : ""}';
$('body').delegate('#login_section','pageinit',function(){
	$("#loginForm").submit(function(){
		if ($('#username').val() == ''){
			J.showToast('请填写账号', 'info');
		}else if ($('#password').val() == ''){
			J.showToast('请填写密码', 'info');
		}else if ($('#validateCodeDiv').is(':visible') && $('#validateCode').val() == ''){
			J.showToast('请填写验证码', 'info');
		}else{
			var loginForm = $("#loginForm");
			$.post(loginForm.attr('action'), loginForm.serializeArray(), function(data){
				if (data && data.sessionid){
					sessionid = data.sessionid;
					J.showToast('登录成功！', 'success');
					J.Router.goTo('#index_section?index');
				}else{
					J.showToast(data.message, 'error');
					if (data.shiroLoginFailure == 'org.apache.shiro.authc.AuthenticationException'){
						$('#validateCodeDiv').show();
					}
					$('#validateCodeDiv a').click();
				}
				//console.log(data);
			});
		}
		return false;
	});
});
$('body').delegate('#login_section','pageshow',function(){
	if (sessionid != ''){
        var targetHash = location.hash;
        if (targetHash == '#login_section'){
    		//J.showToast('你已经登录！', 'success');
    		J.Router.goTo('#index_section?index');
        }
	}else{
		$('#login_article').addClass('active');
	}
});
</script>
</body>
</html>
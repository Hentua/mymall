//var IP ="http://www.szhigreenmg.com/";
//var IPforWx = "http://www.szhigreenmg.com/";

//var IP ="http://localhost:8180/hjxweb/";
//var IPforWx = "http://localhost:8180/hjxweb/";
//var dbcmyContext="http://localhost:8180/hjxweb/app/";//测试环境地址
// 平湖海吉星
//var IP ="http://www.szhigreenmg.com/";
//var IPforWx = "http://www.szhigreenmg.com/";
//var dbcmyContext="http://www.szhigreenmg.com/app/";

//平湖之外的海吉星
// 平湖正式
//var IP ="http://183.62.220.18:808/";
//var IPforWx = "http://183.62.220.18:808/";
//var dbcmyContext="http://183.62.220.18:808/app/";

// 测试
var IP ="http://58.250.204.178:8880/";
var IPforWx = "http://58.250.204.178:8880/";
var dbcmyContext="http://58.250.204.178:8880/app";

//var usedidForMarket;
//var IP = "https://www.dbc61.com";
//var IP = "http://10.200.12.8:8099";
//var IP = "http://dbcpay.dbc61.com:8099";
//var IP = "http://10.200.11.46:8080/webdbc";
var usedidForMarket = get("usedidForMarket");
//var  appidwx = "wx0f8d47beb2ea88e7";//正式环境appid//dbc61.com
//var secretwx = "d3c8092991d4f1d3a6a211ba5c9edc18";//正式环境秘钥
//var secretwx = "4e41b8b8ca552d31993eac25e8d5bb06";//测试环境秘钥
//var appidwx = "wxb2d76676916a5405";//测试环境appid
var appidwx = "wx422bf31b9ffbc08c";
var secretwx = "968e093b060a4ffebbf3f0f90fc67699";
var appidpay = "wx422bf31b9ffbc08c";//测试支付appid
var secretpay = "968e093b060a4ffebbf3f0f90fc67699";//测试支付秘钥
//var zhifubaoid = "2017060107397158";//支付宝id正式
var zhifubaoid = "2016080600179867";//沙箱环境
var urlVersion = location.href;
var paraStringVersion = urlVersion.substring(urlVersion.indexOf("?")+1,urlVersion.length).split("&"); 
var paraObjVersion = {} 
for (i=0; j=paraStringVersion[i]; i++){ 
		paraObjVersion[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
	} 
var appversion = paraObjVersion.appversion;//版本号
//set('appversionForAPP',appversionForAPP);
/*超时设计思路*/
/*
*大白菜APP里面所有的接口参数都需要加入token
appsecretkey是登录时获取到的返回值
timestamp是每个接口都有的返回值
isLoginTag表示登录态，登录时传yes没有登录时传no
*/

//超时参数Token
//var tokenTimeOut = appsecretkey+"@#DBC#"+timestamp+"@#DBC#"+isLoginTag
//假定字符串的每节数都在5位以下
            function toNum(a){
                var a=a.toString();
                //也可以这样写 var c=a.split(/\./);
                var c=a.split('.');
                var num_place=["","0","00","000","0000"],r=num_place.reverse();
                for (var i=0;i<c.length;i++){ 
                    var len=c[i].length;       
                    c[i]=r[len]+c[i];  
                } 
                var res= c.join(''); 
                return res; 
            } 
            function cpr_version(a,b){ 
                var _a=toNum(a),_b=toNum(b);   
                if(_a >  _b){
					console.log("版本号判断成功")
				} 
            }
            
//拨打电话弹框遮罩
function playMobileStyle(){
	$('.mobileBox').empty().append('<div class="mobileTips"><span>拨打后，免费专线将呼叫你的号码：</span><span class="newrel" style="display:block;">***********，请接听</span></div>'+
		'<div class="mobileAction">'+
			'<div class="mobileCancle">取消</div>'+
			'<div class="mobilePlay">立即拨打</div>'+
		'</div>');
}
//点击确定后弹框提示
function afterPlayMobile(){
	$('.mobileBox').empty().append('<div class="loadImg"><img src="../images/load.gif" alt="" /></div>'+
	'<div class="loadTips">专线拨打中，请接听<span>0755-86725203的专线来电</span></div>');
}
//弹框遮罩操作消失
function bagFadeIn(){
	$('.mobileBox').on('tap','.mobileCancle',function(){
		$('.mobileBox').hide();
		$('.tanMobile').hide();
	});
	$('.tanMobile').on('tap',function(){
		$('.mobileBox').hide();
		$('.tanMobile').hide();
	});
	$('.marketGlobal').on('tap','.img_types',function(){
		var phone = $(this).attr('data-mobile');
		var customerid = $(this).attr('data-id');
		if(user_id == ""|| user_id == "undefined" ||user_id == "null"){
				if(platform == "ios"){
					window.location.href = "gotoLogin";
				}else if(platform == "android"){
					window.DbcInterface.jsCallJava("RequestUserLogin","")
				}else{
					set('locationurl',location.href);
					//window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appidwx+"&redirect_uri="+IP+"/newmarket/html/index.html&response_type=code&scope=snsapi_userinfo&state=state%23wechat_redirect&callback=jsonp1&connect_redirect=1#wechat_redirect";
					window.location.href = IP+"/newmarket/html/login.html";
				}
		}else{
			if(customerid == user_id){
				    styles();
					$(".wod").text("不能给自己电话");
					$('.woddbc').text("不能给自己电话");
					setTimeout(dingshiClose,"1000");
			}else{
				$('.mobileBox').show(500);
				$('.tanMobile').show(250);
				playMobileStyle();//弹出框
				$('.mobilePlay').attr("data-mobile",phone);//被拨打用户电话
				$('.mobilePlay').attr("data-id",customerid);//被拨打用户id
				getUserMobile(user_id);
			}
		}
	});
	
	$('.moblieAuth').on('tap','.img_types',function(){
		var phone = $(this).attr('data-mobile');
		var customerid = $(this).attr('data-id');
		if(customerid == user_id){
			    styles();
				$(".wod").text("不能给自己电话");
				$('.woddbc').text("不能给自己电话");
				setTimeout(dingshiClose,"1000");
		}else{
			$('.mobileBox').show(500);
			$('.tanMobile').show(250);
			playMobileStyle();//弹出框
			$('.mobilePlay').attr("data-mobile",phone);//被拨打用户电话
			$('.mobilePlay').attr("data-id",customerid);//被拨打用户id
			getUserMobile(user_id);
		}
	
	});
	$('.mobileBox').on('tap','.mobilePlay',function(){
		var calledEcustomerid = $(this).attr('data-id');
		var phone = $(this).attr('data-mobile');
			getUserMobile(user_id);
			afterPlayMobile();
			turnBackMobile(calledEcustomerid,phone);
	});
	
}
//弹框遮罩我的收藏页面
function bagFadeInForSave(){
	var usersave = get("userid");
	$('.mobileBox').on('tap','.mobileCancle',function(){
		$('.mobileBox').hide();
		$('.tanMobile').hide();
	});
	$('.tanMobile').on('tap',function(){
		$('.mobileBox').hide();
		$('.tanMobile').hide();
	});
	$('.marketGlobal').on('tap','.img_types',function(){
		var phone = $(this).attr('data-mobile');
		var customerid = $(this).attr('data-id');

			if(customerid == usersave){
				    styles();
					$(".wod").text("不能给自己电话");
					$('.woddbc').text("不能给自己电话");
					setTimeout(dingshiClose,"1000");
			}else{
				$('.mobileBox').show(500);
				$('.tanMobile').show(250);
				playMobileStyle();//弹出框
				$('.mobilePlay').attr("data-mobile",phone);//被拨打用户电话
				$('.mobilePlay').attr("data-id",customerid);//被拨打用户id
				getUserMobile(usersave);
			}
		
	});
	
	$('.moblieAuth').on('tap','.img_types',function(){
		var phone = $(this).attr('data-mobile');
		var customerid = $(this).attr('data-id');
		if(customerid == usersave){
			    styles();
				$(".wod").text("不能给自己电话");
				$('.woddbc').text("不能给自己电话");
				setTimeout(dingshiClose,"1000");
		}else{
			$('.mobileBox').show(500);
			$('.tanMobile').show(250);
			playMobileStyle();//弹出框
			$('.mobilePlay').attr("data-mobile",phone);//被拨打用户电话
			$('.mobilePlay').attr("data-id",customerid);//被拨打用户id
			getUserMobile(usersave);
		}
	
	});
	$('.mobileBox').on('tap','.mobilePlay',function(){
		var calledEcustomerid = $(this).attr('data-id');
		var phone = $(this).attr('data-mobile');
			getUserMobile(usersave);
			afterPlayMobile();
			turnBackMobileForSave(calledEcustomerid,phone);
	});
	
}
//弹框遮罩操作消失
function bagFadeIns(){
	$('.mobileBox').on('tap','.mobileCancle',function(){
		$('.mobileBox').hide();
		$('.tanMobile').hide();
	});
	$('.tanMobile').on('tap',function(){
		$('.mobileBox').hide();
		$('.tanMobile').hide();
	});
	$('.marketGlobal').on('tap','.img_types',function(){
		if(usedidForMarket == null){
			set('locationurl',location.href);
			//window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appidwx+"&redirect_uri="+IP+"/newmarket/html/index.html&response_type=code&scope=snsapi_userinfo&state=state%23wechat_redirect&callback=jsonp1&connect_redirect=1#wechat_redirect";
			window.location.href = IP+"/newmarket/html/login.html";
		}else{
		var phone = $(this).attr('data-mobile');
		var customerid = $(this).attr('data-id');
		if(usedidForMarket == null){
			if(customerid == user_id){
					styles();
					$(".wod").text("不能给自己电话");
					$('.woddbc').text("不能给自己电话");
					setTimeout(dingshiClose,"1000");
			}else{
				$('.mobileBox').show(500);
				$('.tanMobile').show(250);
				playMobileStyle();//弹出框
				$('.mobilePlay').attr("data-mobile",phone);//被拨打用户电话
				$('.mobilePlay').attr("data-id",customerid);//被拨打用户id
				getUserMobile(user_id);
			}
		}else{
			if(customerid == usedidForMarket){
					styles();
					$(".wod").text("不能给自己电话");
					$('.woddbc').text("不能给自己电话");
					setTimeout(dingshiClose,"1000");
			}else{
				$('.mobileBox').show(500);
				$('.tanMobile').show(250);
				playMobileStyle();//弹出框
				$('.mobilePlay').attr("data-mobile",phone);//被拨打用户电话
				$('.mobilePlay').attr("data-id",customerid);//被拨打用户id
				getUserMobile(usedidForMarket);
			}
		}
			
		}
	});
	
	$('.moblieAuth').on('tap','.img_types',function(){
		var phone = $(this).attr('data-mobile');
		var customerid = $(this).attr('data-id');
		if(customerid == user_id){
			    styles();
				$(".wod").text("不能给自己电话");
				$('.woddbc').text("不能给自己电话");
				setTimeout(dingshiClose,"1000");
		}else{
			$('.mobileBox').show(500);
			$('.tanMobile').show(250);
			playMobileStyle();//弹出框
			$('.mobilePlay').attr("data-mobile",phone);//被拨打用户电话
			$('.mobilePlay').attr("data-id",customerid);//被拨打用户id
			getUserMobile(user_id);
		}
	
	});
	$('.mobileBox').on('tap','.mobilePlay',function(){
		var calledEcustomerid = $(this).attr('data-id');
		var phone = $(this).attr('data-mobile');
			getUserMobile(user_id);
			afterPlayMobile();
			turnBackMobile(calledEcustomerid,phone);
	});
	
}
//供求详情页、商户主页打电话操作
function mainPlayMobile(){
	$('.mobileBox').on('tap','.mobileCancle',function(){
		$('.mobileBox').hide();
		$('.tanMobile').hide();
	});
	$('.tanMobile').on('tap',function(){
		$('.mobileBox').hide();
		$('.tanMobile').hide();
	});
		$('.mobileBox').on('tap','.mobilePlay',function(){
		var calledEcustomerid = $(this).attr('data-id');
		var phone = $(this).attr('data-mobile');
			afterPlayMobile();
			getUserMobile(user_id);
			turnBackMobile(calledEcustomerid,phone);
		
	});
}
//电话回拨接口
function turnBackMobile(calledEcustomerid,phone){
	if(usedidForMarket == null){
		var data = {'ecustomerid':user_id,'calledEcustomerid':calledEcustomerid,'phone':phone,callType:"0"};
	}else{
		var data = {'ecustomerid':usedidForMarket,'calledEcustomerid':calledEcustomerid,'phone':phone,callType:"0"};
	}
	$.ajax({
		url:IP+"/app/cloudcall/makeCall",
		type:"post",
		dataType:"json",
		data:data,
		success:function(data){
			if(data.code == 0){
				setTimeout(closeHide,3000);
			//	styles();
			//	$(".wod").text(data.data.info);
			//	$('.woddbc').text(data.data.info);
			//	setTimeout(dingshiClose,"1000");
			}else{
				$('.mobileBox').hide();
				$('.tanMobile').hide();
				styles();
				$(".wod").text(data.descs);
				$('.woddbc').text(data.descs);
				setTimeout(dingshiClose,"1000");
			}
				
		}
	});
}
//电话回拨接口
function turnBackMobileForSave(calledEcustomerid,phone){
		var usedidForMarket = get('userid');
		var data = {'ecustomerid':usedidForMarket,'calledEcustomerid':calledEcustomerid,'phone':phone,callType:"0"};
	
	$.ajax({
		url:IP+"/app/cloudcall/makeCall",
		type:"post",
		dataType:"json",
		data:data,
		success:function(data){
			if(data.code == 0){
				setTimeout(closeHide,3000);
			//	styles();
			//	$(".wod").text(data.data.info);
			//	$('.woddbc').text(data.data.info);
			//	setTimeout(dingshiClose,"1000");
			}else{
				$('.mobileBox').hide();
				$('.tanMobile').hide();
				styles();
				$(".wod").text(data.descs);
				$('.woddbc').text(data.descs);
				setTimeout(dingshiClose,"1000");
			}
				
		}
	});
}
function styles(){//遮罩层
		$(".pop-bg").css({opacity:'0.3'});
		$('body').addClass('hidden').removeClass('auto');
		$(".pop-bg").show();
		$(".tishis").show();
		$(".resuleType").hide();
}
 function dingshiClose(){//定时关闭遮罩
		$('body').addClass('auto').removeClass('hidden');
		$(".pop-bg").hide();
		$(".tishis").hide();
		$(".resuleType").show();
	  }

function getUserMobile(id){//获取登录人电话号码
	var data = {"ecustomerid":id};
	$.ajax({
		url:IP+"/app/user/getEcustomer",
		type:"get",
		dataType:"json",
		data:data,
		success:function(data){
			if(data.code == "0"){
					$('.rel').text(data.data.mobile+",请接听");
					$('.newrel').text(data.data.mobile+",请接听");
			}
		}
	});
}
function closeHide(){
	$('.mobileBox').hide();
	$('.tanMobile').hide();
}
/*数据缓存在session中*/
function setSession(key,value){
	sessionStorage.setItem(key,JSON.stringify(value));
}
function getSession(key){
		return JSON.parse(sessionStorage.getItem(key));
}
function removeSession(key){
	sessionStorage.removeItem(key);
}
/*数据缓存在localstorage中*/
function set(key,value){
			localStorage.setItem(key,JSON.stringify(value));
		 }
		
function get(key){
		
				return JSON.parse(localStorage.getItem(key));
			
		}
function getUserid(key){
		if(platform == "ios" || platform == "android"){
				return user_id;
			}else{
				return JSON.parse(localStorage.getItem(key));
			}
}

function remove(key){
			localStorage.removeItem(key);
	  }
//登录接口
function getLogin(username,password){
	var data = {"username":username,"password":password,"verify":"mobileLogin"};
		$.ajax({
				type:"post",
				url:IP+"/app/ajaxLogin",
				dataType:"json",
				data:data,
				success:function(data){
				if(data.code == 0){
					var useridlogin = data.data.id;//买家会员id
					set('userid',useridlogin);
					set("usedidForMarket",useridlogin);
					var locationurl = get('locationurl');
					var openid = get('onlyopenid');//获取openid
					if(openid == null){
						window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appidwx+"&redirect_uri="+IP+"/newmarket/html/bindpage.html&response_type=code&scope=snsapi_base&state=state%23wechat_redirect&callback=jsonp1&connect_redirect=1#wechat_redirect";
					}else{
						if(locationurl == null){
							window.location.href=IP+"/newmarket/my/html/mycenter.html";
						}else{
							window.location.href = locationurl
						}
					}
				$.ajax({//退出接口
					type:"get",
					url:IP+"/app/user/loginOut",
					dataType:"json",
					data:{"ecustomerId":useridlogin},
					success:function(data){}
					});
				}else{
					styles();
				    $(".wod").text(data.descs);
					setTimeout(dingshiClose,"1000");
					return false;
				}
			}
		
	});
}
//注册接口
function getRegisterForWx(mobile,code,password,place){
	//$.dialog();
	$('.pop-bg').show();
	$('.tishi').show();
	var data = {"mobile":mobile,"vdcode":code,"password":password,"customertype":"3","lngLat":place};
	$.ajax({
		type:"post",
		url:IP+"/app/user/register",
		dataType:"json",
		data:data,
		success:function(data){
			if(data.code == "0"){
				$('.pop-bg').hide();
				$('.tishi').hide();
				styles();
				$(".wod").text(data.descs);
				setTimeout(dingshiClose,"1000");
				//set("usedidForMarket",data.data.id);
				//set('userid',data.data.id);
				//window.location.href  = "../my/html/mycenter.html";
				window.location.href = "../html/login.html";
			}else{
				$('.pop-bg').hide();
				$('.tishi').hide();
				styles();
				$(".wod").text(data.descs);
				setTimeout(dingshiClose,"1000");
				return false;
			}
		}
	});
}
//找回密码
function findPassword(phoneValue,phoneCode,password){
	var data = {"mobile":phoneValue,"vdcode":phoneCode,"pwd":password};
	$.ajax({
		type:"post",
		url:IP+"/app/user/find/resetPwd",
		dataType:"json",
		data:data,
		success:function(data){
			if(data.code == "0"){
				//set("usedidForMarket",data.data.id);
				//set('userid',data.data.id);
				//window.location.href = "../my/html/mycenter.html";
				styles();
				$(".wod").text(data.descs);
				setTimeout(dingshiClose,"1000");
				window.location.href = "../html/login.html";
			}else{
				styles();
				$(".wod").text(data.descs);
				setTimeout(dingshiClose,"1000");
				return false;
			}
		}
	});
}
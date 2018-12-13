
var url = location.href;
var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
var paraObj = {} 
for (i=0; j=paraString[i]; i++){ 
	paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
	} 
var user_id = paraObj.user_id;//登录用户ID
var customer_id = paraObj.customer_id;//商户ID
var platform = paraObj.platform;//判断是IOS系统安卓系统
var markerId1 = paraObj.markerid;
var markerTxt = decodeURI(paraObj.markertxt);//市场名称
var shichangId = paraObj.ecompanyid;//原生传过来的市场ID
var marketPlaceType,marketPlaceId ;
var version = paraObj.version;//获取版本信息
var isshare = paraObj.isshare;//分享标识
var ssss = paraObj.ssss;//分享点击
var iscircle = paraObj.iscircle;//是否通过圈子进入
var appversion = paraObj.appversion;
//遮罩清除overflow
function clearOverflow(){
	$('body').css("overflow","hidden");
	$(".cover").fadeIn();
	$(".pop").fadeIn();
}

//图片中插入字符串
function spiltPicString(picUrl){
		var picArray = picUrl.split(".");
		picArray[picArray.length-2] +='_min';
		var newString = picArray.join(".")
		return newString;
}
//校验数据是否为空
function saveIsNulls(stringData){
	if(!stringData){
		return "";
	}else{
		return stringData;
	}
}
//请求数据
function queryMarketSupply(data){
	$(".textListContent").empty().append('<div id="load"><img src="../images/load.gif" alt="" /></div>')
	$.ajax({
		type:"get",
		data:data,
		url:IP+"/app/pub/getDynamicInfoWeb",
		dataType:"json",
		success:function(data){
		$("#load").remove();
		if(data.data.length == 0){
			$(".textListContent").append('<div id="lodeFruit">没有数据！</div>');
		}else{
		for(var i = 0; i<data.data.length;i++){
			$(".textListContent").append('<div class="heigt_box"><div class="clsduo_Dbc" customer_code="'+data.data[i].newBusiess.id+'"  datatype="'+data.data[i].id+'">'+
			'<div class="swiper-container" id="pic'+i+'"></div>'+
			'<div class="content_tap">'+
			'<div class="caigou_type">采购：'+saveIsNulls(data.data[i].eproducttypename)+'</div>'+
			'<div class="caigou_num">采购量：'+saveIsNulls(data.data[i].qty)+saveIsNulls(data.data[i].unit)+'</div>'+
			'<div class="caigou_tap" id="tab'+i+'"></div>'+
			'<div class="caigou_place"><span id="mycode'+i+'"></span><span class="caigou_market">'+saveIsNulls(data.data[i].newBusiess.name)+'</span></div>'+
			'</div></div>'+
			'<div class="img_box"><a id=phone'+i+'><img class="img_content" src="../images/phone.png" alt="" /></a></div>'+
			'<div class="placeTime">'+
			'<div id="placeValue"><img class="timeSunm"src="../images/adress.png" alt="" /><span id="watch'+i+'"></span></div>'+
			'<div id="placeTime">'+data.data[i].paramStr+'</div>'+
			'</div></div>');
				if(data.data[i].edynamicinfopicList.length != 0){//判断商户是否上传图片
					$('#pic'+i+'').append('<img src="'+spiltPicString(data.data[i].edynamicinfopicList[0].pic)+'" alt="" />');
				}else{
					$('#pic'+i+'').hide();
				}
				if(!data.data[i].tabs){//将返回的标签按照数组进行切割
					
				}else{
						var arrTab = data.data[i].tabs.split(',');
						for(var m =0;m<arrTab.length;m++){
						$('#tab'+i+'').append('<span>'+arrTab[m]+'</span>');
						}
				}
				if(data.data[i].newBusiess.customertype == "2"){
					$('#mycode'+i+'').append('<img class="caigou_img" src="../images/pi.png" alt="" />');
					$('#watch'+i+'').append('<span>'+saveIsNulls(data.data[i].newBusiess.ecompanyname)+" "+saveIsNulls(data.data[i].newBusiess.ecustomershopList[0].shopid)+'</span>');
				}else if(data.data[i].newBusiess.customertype == "3"){
					$('#mycode'+i+'').append('<img class="caigou_img" src="../images/cai.png" alt="" />');
					$('#watch'+i+'').append('<span>'+saveIsNulls(data.data[i].newBusiess.cityname)+" "+saveIsNulls(data.data[i].newBusiess.purchasetypename)+'</span>');
				}
				  if(user_id == "null" ||user_id == "undefined" ||user_id == "" ||user_id == undefined){
					$('#phone'+i+'').click(function(){	
							quicklyLogo();
					});
				  }else{
					$('#phone'+i+'').attr("href","tel:"+data.data[i].newBusiess.mobile);//商户手机号
				}  
				
				
			}
			$(".clsduo_Dbc").on("tap",function(){
				var dynamic_id = $(this).attr("datatype");//帖子动态ID
				var customer_id = $(this).attr("customer_code");
				window.location.href ="storeApply.html?user_id="+user_id+"&dynamic_id="+dynamic_id+"&platform="+platform+"&customer_id="+customer_id+"&appversion="+appversion;
			});
			}
		},
		error:function(){
			//alert("网络或服务器异常");
		}
	});
}

//没有登录直接跳转至登录页
function quicklyLogo(){
	if(platform == "ios"){
		IsLogo();
	}else{
	window.DbcInterface.jsCallJava("RequestUserLogin","")
	}
}

//调用原生登录页面IOS
function IsLogo(){
	window.location.href = "gotoLogin";
}
//校验分享，浏览次数是否为空
function saveIsNull(stringData){
	if(!stringData){
		return "0";
	}else{
		return stringData;
	}
}
//校验是否为空
function saveIsUndefined(stringData){
	if(!stringData){
		return "";
	}else{
		return stringData;
	}
}
/*
*农商咨询接口
*/

//变化市场


function farmerNews(){
	//data.data[i].articleData.shareNums分享数目
	//首页图片+data.data[i].image+
	$.ajax({
		url:IP+"/app/pub/newslist?categoryid=9",
		type:"get",
		dataType:"json",
		success:function(data){
		for(var i = 0;i<data.data.length;i++){
			cutstr(saveIsUndefined(data.data[i].description),56);
			cutstrs(saveIsUndefined(data.data[i].title),20)
		//$('.text'+i+'').text(str_cut);
			$(".articleGlobal").append(
		'<div class="articleText"><div class="articleClickEnvents" link="'+data.data[i].link+'" src-url="'+saveIsUndefined(data.data[i].image)+'" article="'+saveIsUndefined(data.data[i].description)+'" article-id="'+data.data[i].id+'">'+
		'<div class="boird"><div class="articleTitle">'+data.data[i].title+'</div>'+
		'<div class="articlePlace ">'+
		'<span class="articleTime">'+saveIsUndefined(data.data[i].createDate).substring(3,8)+'</span>'+
		'<span id="lookPicBook">浏览 '+saveIsNull(data.data[i].hits)+'</span>'+
		'<span id="placePicBook">分享 '+saveIsNull(data.data[i].shareNums)+'</span>'+
		'</div></div>'+
		'<div class="articlePic"><img id=pic'+i+' class="picText_sub" src='+data.data[i].image+' ></div>'+
		'</div>'+
		'</div>'
		);
		if(data.data[i].image == ""){
				$('#pic'+i+'').hide();
			}
		
		$(".articleText").on("tap",".articleClickEnvents",function(){
			var contendid = $(this).attr("article-id");
			var urlSrc = $(this).attr("src-url");
			var articleDdscription = $(this).attr("article");
			var linked = $(this).attr("link");
			//alert(data.data[i].link);
			if(linked == ""){
			window.location.href ="farmeNews.html?contendid="+contendid+"&platform="+platform+"&urlSrcSub="+urlSrc+"&articleDdscription="+articleDdscription+"&user_id="+user_id+"&iscircle="+iscircle+"&appversion="+appversion;
			}else {
				window.location.href = linked;
			}
			});
		}
		},
		error:function(){
			//alert(网络或服务器异常);
		}
	});
}

//控制显示字数
function cutstr(str,len)
{
   var str_length = 0;
   var str_len = 0;
      str_cut = new String();
      str_len = str.length;
      for(var i = 0;i<str_len;i++)
     {
        a = str.charAt(i);
        str_length++;
        if(escape(a).length > 56)
        {
         //中文字符的长度经编码之后大于4
         str_length++;
         }
         str_cut = str_cut.concat(a);
         if(str_length>len)
         {
         str_cut = str_cut.concat("...");
         return str_cut;
         }
    }
    //如果给定字符串小于指定长度，则返回源字符串；
    if(str_length<=len){
     return  str;
    }
}
//控制显示字数
function cutstrs(str,len)
{
   var str_length = 0;
   var str_len = 0;
      str_cuts = new String();
      str_len = str.length;
      for(var i = 0;i<str_len;i++)
     {
        a = str.charAt(i);
        str_length++;
        if(escape(a).length > 20)
        {
         //中文字符的长度经编码之后大于4
         str_length++;
         }
         str_cuts = str_cuts.concat(a);
         if(str_length>len)
         {
         str_cuts = str_cuts.concat("...");
         return str_cut;
         }
    }
    //如果给定字符串小于指定长度，则返回源字符串；
    if(str_length<=len){
     return  str;
    }
}
/*
*点击农场咨询菜单进入详情页
*/
function farmerNewsContent(){
	var url = location.href;
	//alert(url);
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");  
	var paraObj = {} 
	for (i=0; j=paraString[i]; i++){ 
		paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
	} 
	var contendId = paraObj.contendid;
	var data = {"newsid":contendId};
	
	$.ajax({
		url:IP+"/app/pub/getNews",
		type:"get",
		dataType:"json",
		data:data,
		success:function(data){
			$('.checkNum').text(cutstr(data.data.title,10));
			$(".articlrTitle").text(data.data.title);
			$(".timeOutCreat").text(data.data.createDate.substring(3,8));
			$(".placeOutCreat").text(data.data.articleData.copyfrom);
			//console.log(data.data.articleData.content);
			$(".articelText_style").html(data.data.articleData.content);
			if(platform == "ios"){
				window.location.href="setTitle?title="+data.data.title;
				$("title").html(data.data.title);//title获取值
			}else{
				$("title").html(data.data.title);//title获取值
			}
			if(data.data.ecustomerid == ""){
				
			}else{
				$(".get_globals").fadeIn();
				$(".get_acion").on("tap",function(){
					window.location.href =IP+"/newmarket/html/main.html?user_id="+user_id+"&customer_id="+data.data.ecustomerid+"&platform="+platform+"&ssss="+ssss+"&iscircle="+iscircle+"&appversion="+appversion;
				});
				
			}
		}
	});
}

/*
*点击首页轮播图进入活动页面
*/
function dyncActivityPage(){
	var url = location.href;
	//alert(url);
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");  
	var paraObj = {} 
	for (i=0; j=paraString[i]; i++){ 
		paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
	} 
	var eactivityId = paraObj.id;
	var data = {"eactivityId":eactivityId};
	
	$.ajax({
		url:IP+"/app/pub/getActivityInfo",
		type:"get",
		dataType:"json",
		data:data,
		success:function(data){
			$(".articlrTitle").text(data.data.title);
			$(".timeOutCreat").text(data.data.createDate);
			if(platform == "ios"){
						window.location.href="setTitle?title="+saveIsNull(data.data.title);
					}else{
						$("title").html(saveIsNull(data.data.title));
					}
			//$("title").html(data.data.title);//头名
			if(data.data.image == "" || data.data.image.indexOf("null") != -1 ){
				
			}else{
				$(".localImg").attr("src",data.data.image);
			}
			$(".articelText_style").html(data.data.content);
		},
		error:function(){
			//alert("网络或服务器异常");
		}
	});
}
///通过区域获取产地商信息
function buyerInfo(areaId){
	var pageNo = 1;
	var innerHeight = window.innerHeight;
	var timer2 = null;
	$(".marketGlobal").empty().append('<div id="load"><img src="../images/load.gif" alt="" /></div>')
	var data = {"type":"2","emarketareaid":areaId,"pageSize":"40"};
	$.ajax({
		url:IP+"/app/market/findAreaDynamicInfo?pageNo=1",
		type:"get",
		dataType:"json",
		data:data,
		success:function(data){
			$("#load").remove();
			$(".marketGlobal").empty();
			//console.log(data.data.length);
			if(data.code == 19){
				$(".marketGlobal").append('<div id="lodeFruit">没有数据！</div>');
			}else{
			if(data.data.length == 0 ){
					$(".marketGlobal").append('<div id="lodeFruit">没有数据！</div>');
			}
			}
			for(var i = 0;i<data.data.length;i++){
			isPromissString(saveIsNulls(data.data[i].id));//获取是否认证	
			$(".marketGlobal").append(
			'<div class="shop_Place">'+
			'<div class="shopConent_place">'+
			'<div class="shopText_place" id="picType" data-type="'+data.data[i].id+'"><img id=person'+i+' class="img_type" src='+data.data[i].picname+' alt="" /><img class="renzdbc" src="../images/dbcren.png" alt="" /></div>'+
			'<div class="shopText_place dbc_imgaim" id="contentType" data-types='+data.data[i].id+'>'+
			
			'<div class="symbalTime">'+
			'<span id=picName'+i+'>'+
			'<img class="img_type_wcs" src="../images/pi.png" alt="" /></span>'+
			'<span class="shopName_palce">'+saveIsUndefined(data.data[i].name)+'</span>'+
			'</div>'+
			'<div id="shopName_palces'+i+'" class="shopName_palces"></div>'+
			'<div class="customer_tips"  id="sannian'+i+'"></div>'+
			
			'<div class="shopName_Time" id="placeluowei'+i+'"></div>'+
			'</div>'+
			'<div class="shopText_place" id="iphoneType"><a id=phone'+i+' class="phoneNumText"><img class="img_types"src="../images/phone.gif" alt="" /></a></div>'+
			'</div></div>'
			);
				$('#placeluowei'+i+'').empty().append('<img src="../images/adress.png" alt="" />');
			if(data.data[i].ecustomershopList.length == 0){
				$('#placeluowei'+i+'').append('<span>'+saveIsUndefined(data.data[i].ecompanyname)+'</span>');
			}else{
				$('#placeluowei'+i+'').append('<span>'+saveIsUndefined(data.data[i].ecompanyname)+" "+saveIsUndefined(data.data[i].ecustomershopList[0].shopid)+'</span>');
			}
				$('#sannian'+i+'').empty();
			if(data.data[i].management != ""){//企业性质管理
						$('#sannian'+i+'').append('<span class="timeYear">'+data.data[i].management+'</span>');
				}
				if(data.data[i].rent != ""){//租用
						$('#sannian'+i+'').append('<span class="longRent">'+data.data[i].rent+'</span>');
					}
				if(data.data[i].naturetype != ""){//经营
						$('#sannian'+i+'').append('<span class="dbc_company">'+data.data[i].naturetype+'</span>');
					}
			//逛市场打电话功能
			//$('#phone'+i+'').attr("href","tel:"+saveIsUndefined(data.data[i].mobile))
			if(user_id == "null" ||user_id == "undefined" ||user_id == "" ||user_id == undefined){
					$('#phone'+i+'').click(function(){	
							quicklyLogo();
					});
			}else{
				$('#phone'+i+'').attr("href","tel:"+saveIsUndefined(data.data[i].telephone));
			} 	
			if(data.data[i].picname ==""){
				$('#person'+i+'').attr("src","../images/noperson.png");
			}
		
			$(".shopConent_place").on("tap",".dbc_imgaim",function(){
				var idString = $(this).attr("data-types");
				window.location.href ="mianCustomer.html?customer_id="+idString+"&user_id="+user_id+"&platform="+platform+"&appversion="+appversion;
			});
				//主营最多显示三个
				if(data.data[i].ecustomervarietiesList.length>3){
					$('#shopName_palces'+i+'').empty().append('<span>主营:</span>');
					for(var n = 0;n<3;n++){
						$('#shopName_palces'+i+'').append('<span>'+data.data[i].ecustomervarietiesList[n].eproducetypename+'</span>');
					}
				}else{
					$('#shopName_palces'+i+'').empty().append('<span>主营:</span>');
					for(var n = 0;n<data.data[i].ecustomervarietiesList.length;n++){
						$('#shopName_palces'+i+'').append('<span>'+data.data[i].ecustomervarietiesList[n].eproducetypename+'</span>');
					}

				}			
			}
	$(window).scroll(function(){
		var winH = $(window).height(); 
        var pageH = $(document.body).height();  
        var scrollT = $(window).scrollTop(); //滚动条top   
	    if (scrollT >= pageH - winH) {//0.02是个参数  
	    	    pageNo++; 
	    	//$('#discount').append('<div id="load"><img src="../images/load.gif" width="6%" /></div>');
		        $.ajax({
                url:IP+"/app/market/findAreaDynamicInfo?pageNo="+pageNo,
				type:"get",
				dataType:"json",
				data:{"type":"2","emarketareaid":areaId,"pageSize":"40"},
                success: function (data) {
				$("#load").remove();
					for(var i = 0;i<data.data.length;i++){
					isPromissString(saveIsNulls(data.data[i].id));//获取是否认证
					$(".marketGlobal").append(
					'<div class="shop_Place">'+
					'<div class="shopConent_place">'+
					'<div class="shopText_place" id="picType" data-type="'+data.data[i].id+'"><img id=persons'+pageNo+"ss"+i+' class="img_type"src='+data.data[i].picname+' alt="" /><img class="renzdbc" src="../images/dbcren.png" alt="" /></div>'+
					'<div class="shopText_place dbc_imgaim" id="contentType" data-types='+data.data[i].id+'>'+
					'<div class="symbalTime">'+
					'<span id=picName'+pageNo+"ss"+i+'>'+
					'<img class="img_type_wcs" src="../images/pi.png" alt="" /></span>'+
					'<span class="shopName_palce">'+saveIsUndefined(data.data[i].name)+'</span>'+
					'</div>'+
					'<div id="'+pageNo+'shopName_palcess'+i+'" class="shopName_palces"></div>'+
					'<div class="customer_tips"  id="sannian'+pageNo+"ss"+i+'"></div>'+
					
					'<div class="shopName_Time" id="'+pageNo+'placeluowei'+i+'"><img src="../images/adress.png" alt="" /></div>'+
					'</div>'+
					'<div class="shopText_place" id="iphoneType"><a id=phone'+pageNo+"ss"+i+' class="phoneNumText" ><img class="img_types"src="../images/phone.gif" alt="" /></a></div>'+
					'</div></div>'
					);	
					$('#'+pageNo+'placeluowei'+i+'').empty().append('<img src="../images/adress.png" alt="" />');
						if(data.data[i].ecustomershopList.length == 0){
							$('#'+pageNo+'placeluowei'+i+'').append('<span>'+saveIsUndefined(data.data[i].ecompanyname)+'</span>');
						}else{
								$('#'+pageNo+'placeluowei'+i+'').append('<span>'+saveIsUndefined(data.data[i].ecompanyname)+" "+saveIsUndefined(data.data[i].ecustomershopList[0].shopid)+'</span>');
						}
						$('#sannian'+pageNo+"ss"+i+'').empty();
						if(data.data[i].management != ""){//企业性质管理
						$('#sannian'+pageNo+"ss"+i+'').append('<span class="timeYear">'+data.data[i].management+'</span>');
						}
					if(data.data[i].rent != ""){//租用
						$('#sannian'+pageNo+"ss"+i+'').append('<span class="longRent">'+data.data[i].rent+'</span>');
						}
					if(data.data[i].naturetype != ""){//经营
						$('#sannian'+pageNo+"ss"+i+'').append('<span class="dbc_company">'+data.data[i].naturetype+'</span>');
						}
					if(user_id == "null" ||user_id == "undefined" ||user_id == "" ||user_id == undefined){
					$('#phone'+pageNo+"ss"+i+'').click(function(){	
							quicklyLogo();
					});
					}else{
						$('#phone'+pageNo+"ss"+i+'').attr("href","tel:"+saveIsUndefined(data.data[i].mobile));//商户手机号
					} 
					
						
					if(data.data[i].picname ==""){
						$('#persons'+pageNo+"ss"+i+'').attr("src","../images/noperson.png");
					}
					$(".shopConent_place").on("tap",".dbc_imgaim",function(){
						var idString = $(this).attr("data-types");
						window.location.href ="mianCustomer.html?customer_id="+idString+"&user_id="+user_id+"&platform="+platform+"&appversion="+appversion;
					});
						//主营最多显示三个
					if(data.data[i].ecustomervarietiesList.length>3){
						$('#'+pageNo+'shopName_palcess'+i+'').empty().append('<span>主营:</span>');
						for(var n = 0;n<3;n++){
							$('#'+pageNo+'shopName_palcess'+i+'').append('<span>'+data.data[i].ecustomervarietiesList[n].eproducetypename+'</span>');
						}
					}else{
						$('#'+pageNo+'shopName_palcess'+i+'').empty().append('<span>主营:</span>');
						for(var n = 0;n<data.data[i].ecustomervarietiesList.length;n++){
							$('#'+pageNo+'shopName_palcess'+i+'').append('<span>'+data.data[i].ecustomervarietiesList[n].eproducetypename+'</span>');
						}
					}
					}
                }
              });　
	    }
    }) 
			
		},
		error:function(){
			//alert("网络或服务器异常");
		}
	});
}


//调用原生返回首页的方法
function turnPageBack(){
	
	if(platform == "ios"){
		 window.location.href='web_back';
	}else{
	window.DbcInterface.jsCallJava("H5Back","");
	}
}
//商户是否认证

function isPromissString(newBusiessId){
	var data = {"ecustomerid":newBusiessId};
	$.ajax({
		type:"get",
		url:IP+"/app/user/getEcustomer",
		data:data,
		datatype:'json',
		success:function(dataArray){
			var logoSysDbc = dataArray.data.isAuth;
			//console.log(logoSysDbc);
			if(logoSysDbc == "2"){
				$("div[data-type ='"+newBusiessId+"']").find(".renzdbc").show();
			}
		
		}
	});
}

function hideDiv(){
	  var dom=document.getElementByClass("dbc-navigation"); 
		dom.style.display="none";
}

$.setCtxUrl("../../server");//设置请求路径
$.addEffect(eff0);
$.addEffect(eff1);
$.addEffect(eff2);
$.addCheckMethod(1,chk1);
$.addCheckMethod(2,chk2);
$.addDefaultFn(dftFn0);
$.addDefaultFn(dftFn1);
var errorInfoInBox = "";
var SSOTELNUMBER = "";
var Account;
var iTime; 

/*倒计时*/
function startTime(){
    iTime = 5;
    timer();
}
function timer(){
    Account = setTimeout('timer()',1000);
    iTime--;
    $(".load_error_tip").children('p').find('b').html(iTime + 's');
//    if(iTime==0){
//         clearTimeout(Account);
//         window.location.href=_globalVar_relativePath+"/index.html";
//    }
}
function loginBoxShow(){
	//获取cookie值
	function getCookieValue(name){
	    var name = escape(name); //读cookie属性，这将返回文档的所有cookie
	    var allcookies = document.cookie;//查找名为name的cookie的开始位置
	    name += "=";
	    var pos = allcookies.indexOf(name);//如果找到了具有该名字的cookie，那么提取并使用它的值
	    if (pos != -1){                                     //如果pos值为-1则说明搜索"version="失败
	        var start = pos + name.length;                  //cookie值开始的位置
	        var end = allcookies.indexOf(";",start);        //从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
	        if (end == -1) end = allcookies.length;        //如果end值为-1说明cookie列表里只有一个cookie
	        var value = allcookies.substring(start,end);  //提取cookie的值
	        return unescape(value);                           //对它解码
	    }
	    else return "";                                  //搜索失败，返回空字符串
	}

	var logindiv = document.createElement("div");
	logindiv.id="logindiv";
	if(document.getElementById("logindiv")==null||document.getElementById("logindiv")==undefined){
		document.body.appendChild(logindiv);
		$("#logindiv").load(_globalVar_relativePath+"/componant/loginPop.html",function(){
		var login_phoneId = getCookieValue("rememberNum");
		if(login_phoneId != '') {
			$('#SERIAL_NUMBER').val(login_phoneId);
		}
		shareBoxShow("loginBox");
		//展示未登陆提示
		if(null != errorInfoInBox && "" != errorInfoInBox && "null" != errorInfoInBox){
			$("#ErrorInfo").html("<span style='color:red;'>"+errorInfoInBox+"</span>");
			$("#SERIAL_NUMBER").val(SSOTELNUMBER);
		}
		});
		//loadjscssfile("/style/login/pageLogin.css","css");
		
	}else{
		if(!$("#loginBox").is(":visible")&&document.getElementById("shareto_mask")){
			$("#loginBox").show();
		}else{
			shareBoxShow("loginBox");
			$(".addpop.loginPop").show();
			$('#shareto_mask').show();
		}
	}
}


function eff0(params){
	if(params.contentId){
		params.beforeSend = function(XMLHttpRequest){//在请求之前，添加加载效果
	      	$("#"+params.contentId).toggleClass('jz',true);
	    }
	    params.complete = function(XMLHttpRequest, textStatus){//请求完毕后，清除加载效果
	    	$("#"+params.contentId).toggleClass('jz',false);
		}
	    params.error = function(XMLHttpRequest, textStatus){
//	    	$("#"+params.contentId).html("<table cellpadding='0' cellspacing='0' style='width:100%;min-width: 120px;padding-top:10px;'><tr><td style='text-align: center;'><font color='red'>服务器繁忙，请稍后再试！</font>&nbsp;</td></tr></table>");
			$(".busDetailInfo_nodata").show();
			$(".busDetailInfo_main").hide();
			clearTimeout(Account);
        	/*倒计时*/
        	startTime();
		}
	}
	return params;
}

function eff1(params){
	params.beforeSend = function(XMLHttpRequest){//在请求之前，添加加载效果
		shareBoxShow("progress");
    }
    params.complete = function(XMLHttpRequest, textStatus){//请求完毕后，清除加载效果
		shareBoxHide("progress");
	}
    params.error = function(XMLHttpRequest, textStatus){
//		$("#"+params.contentId).html("<table cellpadding='0' cellspacing='0' style='width:100%;min-width: 120px;padding-top:10px;'><tr><td style='text-align: center;'><font color='red'>服务器繁忙，请稍后再试！</font>&nbsp;</td></tr></table>");
		$(".busDetailInfo_nodata").show();
		$(".busDetailInfo_main").hide();
		clearTimeout(Account);
        /*倒计时*/
        startTime();
	}
	return params;
}

function eff2(params){
	var param = params.param;
	var tempData = new Object();
	if(param){
		var temp = param.split("&");
		for(var i in temp){
			if(temp[i]!=""){
				tempData[temp[i].split("=")[0]]=temp[i].split("=")[1];
			}
		}
	}
		
	if(tempData.cond_TRANS_TYPE&&tempData.cond_GOODS_NAME){

	}else{
		params.beforeSend = function(XMLHttpRequest){
			shareBoxShow("secondConfirm");
	    }
	    params.complete = function(XMLHttpRequest, textStatus){
			shareBoxHide("secondConfirm");
		}
	    params.error = function(XMLHttpRequest, textStatus){
//		    if(params.contentId){
//		    	$("#"+params.contentId).html("<table cellpadding='0' cellspacing='0' style='width:100%;min-width: 120px;padding-top:10px;'><tr><td style='text-align: center;'><font color='red'>服务器繁忙，请稍后再试！</font>&nbsp;</td></tr></table>");
//		    }else{
//		    	//跳转到错误页面，暂以百度页面代替
//		    	window.location = 'http://www.baidu.com';
//		    }
		    $(".busDetailInfo_nodata").show();
			$(".busDetailInfo_main").hide();
			clearTimeout(Account);
        	/*倒计时*/
        	startTime();
		}
	}
	return params;
}



function chk1(params){
	loginBoxShow();
	params.needIns = params.needIns==undefined?true:params.needIns;
	if(params.needIns&&params.contentId){
			var loginError=document.createElement("div");
		    loginError.id="loginErrorBox";
		    var img1=document.createElement("img");
		    img1.src=_globalVar_relativePath+"/images/img_noLoginTip.png";
		    img1.setAttribute("style","display: block;margin-left: auto;margin-right: auto;");
		    loginError.appendChild(img1);
		    var p=document.createElement("p");
		    p.setAttribute("style","padding: 5px 0;font-size: 14px; margin-left: auto;margin-right: auto;text-align: center;");
		    p.innerHTML="您当前为未登录状态，请您登录后进行操作";
		    loginError.appendChild(p);
			var loginBtn = document.createElement("a");
		    loginBtn.setAttribute("style","margin-left: auto;cursor: pointer;text-decoration:none;display: block;margin-right: auto;font-size: 14px;margin-top: 10px;padding-bottom: 2px;background-color: #ff3683;color: #fff;padding-top: 2px;height: 26px;line-height: 25px;width: 90px;border-radius: 25px;text-align: center;");
		    loginBtn.setAttribute("onclick","loginBoxShow();");
		    loginBtn.innerHTML="登录";
		    loginError.appendChild(loginBtn);
		    document.getElementById(params.contentId).appendChild(loginError);
	}
	return false;
}


function loadjscssfile(filename,filetype){

    if(filetype == "js"){
        var fileref = document.createElement('script');
        fileref.setAttribute("type","text/javascript");
        fileref.setAttribute("src",filename);
    }else if(filetype == "css"){
    
        var fileref = document.createElement('link');
        fileref.setAttribute("rel","stylesheet");
        fileref.setAttribute("type","text/css");
        fileref.setAttribute("href",filename);
    }
   if(typeof fileref != "undefined"){
        document.getElementsByTagName("head")[0].appendChild(fileref);
    }
    
}
function dftFn0(data,params){
	renderData(data,params.artId,params.contentId);
}
function dftFn1(data,params){
	if(params.handlerBusiResult){
		renderData(data,"evaluate","content");
		shareBoxHide("secondConfirm");

		// 集团插码
		if (wtsi_n && wtsi_s) {
			if (data[0].X_RESULTCODE == "0") {
				if(typeof(_tag)!='undefined'){_tag.dcsMultiTrack('WT.si_n', wtsi_n, 'WT.si_x','99', 'WT.si_s', wtsi_s);}
			} else {
				if(typeof(_tag)!='undefined'){_tag.dcsMultiTrack('WT.si_n', wtsi_n, 'WT.si_x','-99', 'WT.si_s', wtsi_s);}
			}
		}
	}
}

function chk2(){
	window.location.href="/importantNotice/index.html";
}
function showSurveySatisfact(){
	shareBoxShow("surveySatisfact");
}



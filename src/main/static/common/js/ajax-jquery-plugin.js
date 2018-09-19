//根据参数名称获取URL中参数值
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        var temp = unescape(r[2]);

        // 防XSS跨站脚本攻击逻辑
        if (valIsNotEmpty(temp) && temp.indexOf("<") != -1) {
            temp = "";
        }

        if (valIsNotEmpty(temp) && temp.indexOf(">") != -1) {
            temp = "";
        }

        if (valIsNotEmpty(temp) && temp.indexOf("%3C") != -1) {
            temp = "";
        }

        if (valIsNotEmpty(temp) && temp.indexOf("%3E") != -1) {
            temp = "";
        }

        return temp;
    }

    return null;
}

//根据元素值判空
function valIsNotEmpty(obj)
{
    if(null != obj && '' != obj && undefined != obj && typeof(obj) != "undefined")
    {
        if('' != $.trim(obj))
            return true;
        else
            return false;
    }
    else{
        return false;
    }
}

//渲染模板
function renderData(data,artId,contentId){
    if(artId && contentId){
        var html = template(artId, data);
        $("#"+contentId).html(html);
    }
}

!(function($){
	var _staticurl;
	var _checkMethod,_ctxurl,_defaultEff;
	var _checkMethod = new Object();
	var _effect=[];
	var _defaultFn=[];
    /**
     * 从url上获取参数
     * @param name
     * @returns {null}
     */
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
    function desParam(string) {
        var a = "4";var b = "7";var c = "9";
        if(!string || !string.length){return "";}
		return "param="+strEnc(string,a,b,c);
    }
	function parseUrlParams(string){
		if(!string||!string.length){return"";}
		string=string.replace(/&amp;/g,"&");
		string=string.replace(/\n/g,"\\n");
		return string;
	}
	function buildAsyncUrl(url,params) {
		if(!url){ return null; }
		var inUrl= _ctxurl;
		inUrl+="/"+url;
		inUrl+="?"+parseUrlParams(params);
        var sourceid = getUrlParam('source_id');
        var pagecode = getUrlParam('pageCode');
        if(sourceid){
            inUrl+="&source_id="+sourceid;
		}
		if(pagecode){
            inUrl+="&pageCode="+pagecode;
        }
		return inUrl;
	}

	function handleResponse(data,params){
		if(data.X_RESULTCODE != undefined && data.X_RESULTCODE == 'noLogin'){
            window.location.href = '../../login/res_ht/login1.html';
			return;
		}

		if(data.X_RESULTCODE != undefined && data.X_RESULTCODE == '-9'){
            window.location.href = '../../error/res_ht/error.html';
            return;
		}

		//前置校验结果判断，并渲染内容区
		if(params.needCheck==undefined||params.needCheck){
			var checkResult = handleCheck(data,params);
			if(!checkResult){
				return;
			}
		}
		renderData(data,params.artId,params.contentId);
		for(var i in _defaultFn){
			if(typeof _defaultFn[i] == "function"){
				_defaultFn[i].call(this,data,params);
			}
		}

		if(typeof(params.afterFn)=="function"){
			params.afterFn.call(this,data);
		}else if(params.afterFn instanceof Array){
			for(var i in params.afterFn){
				if(typeof(params.afterFn[i])=="function")
				params.afterFn[i].call(this,data);
			}
		}
	}
	
	function getOtherChanId(str){
		var args=new Object();  
	    var query=location.search.substring(1);//获取查询串  
	    var pairs=query.split("&");//在逗号处断开  
	    for(var i=0;i<pairs.length;i++){  
	        var pos=pairs[i].indexOf('=');//查找name=value  
	        if(pos==-1){//如果没有找到就跳过  
	            continue;  
	        }  
	        var argname=pairs[i].substring(0,pos);//提取name  
	        var value=pairs[i].substring(pos+1);//提取value  
	        args[argname]=unescape(value);//存为属性  
	    }  
	    return args[str];//返回对象 
	}
	
	function handleCheck(data,params){
		//var checkInfo = data["checkInfo"];
		var flag = true;
		var checkTag = 0;
		if(data && data["CHECK_TAG"]){
			checkTag = data["CHECK_TAG"];
		}
			//校验未通过
		if(checkTag && checkTag !='' && checkTag != 0){
			flag=_checkMethod[checkTag].call(this,params);
		}
		return flag;
	}
	
	function getCookie(name)
	{
	    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	 
	    if(arr=document.cookie.match(reg))
	 
	        return unescape(arr[2]);
	    else
	        return null;
	}
	
	function addLoadingEffect(params){
		if(params.loadingType){
			var id = parseInt(params.loadingType);
			if(typeof _effect[id] == "function"){
				params=_effect[id].call(this,params);
			}
		}else{
			if(typeof _defaultEff == "function"){
				params=_defaultEff.call(this,params);
			}
		}
		return params;
	}
	

	$.extend({
		getUrlParamValue:function (name) {
		  var value = 	getUrlParam(name);
		  return value;
        },
		setCtxUrl:function(url){
			_ctxurl=url;
		},
		setStaticUrl:function (url) {
			_staticurl = url;
        },
		getCtxUrl:function(url, param){
			return buildAsyncUrl(url,param);
		},
		addEffect:function(effFuc,def){
			_effect.push(effFuc);
			if(def){
				_defaultEff = effFuc;
			}
		},
		addCheckMethod:function(checkTag,checkMethod){
			_checkMethod[checkTag]=checkMethod;
		},
		addDefaultFn:function(fn){
			_defaultFn.push(fn);
		},
		ajaxDirect:function(params){
			/*params.param = desParam(params.param);*/
			var requrl = buildAsyncUrl(params.url,params.param);
			requrl=requrl+"&ajaxSubmitType=post";
			var tokenId = Math.random();
			requrl+="&ajax_randomcode="+tokenId;
			params.url=encodeURI(requrl);
			params=addLoadingEffect(params);
			if(!params.dataType){ params.dataType="json"; }
			params.success=function(data,textStatus, jqXHR){
				handleResponse(data,params);
			}; 
			params.type = "POST";
			params.contentType="application/x-www-form-urlencoded; charset=utf-8";
			$.ajax(params);
			return true;
		},
		ajaxSubmit:function(params){
			var requrl = buildAsyncUrl(params.url,params.param);
			requrl=requrl+"&ajaxSubmitType=post";
			requrl+="&ajax_randomcode="+Math.random();
			params.url=encodeURI(requrl);
			params=addLoadingEffect(params);
			var formParam = $("#"+params.formId).serialize();
			params.data = formParam;
			if(!params.dataType){ params.dataType="json"; }
			params.success=function(data,textStatus, jqXHR){
				handleResponse(data,params);
			};
			params.type = "POST";
			params.contentType="application/x-www-form-urlencoded; charset=utf-8";
			$.ajax(params);
			return true;
		},	
		ajaxOperate:function(params){
			params.handlerBusiResult = true ;
			$.ajaxSubmit(params);
		}
	});
})(jQuery);
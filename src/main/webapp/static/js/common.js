function ajax_post(url,callback,params,errorCallback,eParams){
    $.ajax({
        url: url,
        type: "post",
        dataType: 'json',
        beforeSend: function () {
            $('#loading_gif').show();
        },
        success: function (obj) {
            $('#loading_gif').hide();
            if(obj.success){
                if(callback != null)
                    callback(obj.data,params);
            }else{
                if(errorCallback!=null)
                    errorCallback(obj.data,eParams);
            }
        },
        error: function (xhr, status, errorThrown) {
            console.log(errorThrown)
        }
    });
}
function formatNum(num,n){
    if(!num ){
        return 0;
    }
//参数说明：num 要格式化的数字 n 保留小数位
    num = String(num.toFixed(n));
    var re = /(-?\d+)(\d{3})/;

    while(re.test(num)) {

        num = num.replace(re,"$1,$2");

    }
    return num;
}

//写cookies
function setCookie(c_name, value, expiredays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + expiredays);
    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())+";path=/";

    // $.cookie(c_name,value,{expires:expiredays,path:"/"})
}

function setCookie_new(c_name, value, expiredays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + expiredays);
    //exdate.setTime(exdate.getTime()+expiredays*30*1000);
    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())+";path=/";

    // $.cookie(c_name,value,{expires:expiredays,path:"/"})
}
//读取cookies
function getCookie(name) {

    // return $.cookie(name);
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}

//删除cookies
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null) {
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString()+";path=/";
    }
    // $.cookie(name,null);
}

//判断是否为空
function isEmpty(value){
    if (!value) {
        return true
    }
    if (Array.isArray(value) || ((typeof value === 'string') && value.constructor === String)) {
        return !value.length
    }
    for (var i = 0; i < value.length; i++) {
        if (Object.hasOwnProperty.call(value, value[i])) {
            return false
        }
    }
    return true
}

function getRequestUrl(){
    var allurl = window.location.href;
    var url = allurl;
    if(allurl.indexOf("#")> 0){
        url = allurl.substring(0,allurl.indexOf("#"));
    }
    console.log(url);
    return url;
}

/*手机号码规则验证*/
function isMobile(val) {
    var regPartton = /^1[0-9]+\d{9}$/;
    if (!regPartton.test(val)) {
        return false;
    } else {
        return true;
    }
}
/*姓名只允许中英文数字输入*/
function nameRule(val) {
    var nameText = /[^\a-zA-Z\.\·\ \u4E00-\u9FA5]/g;
    if (!nameText.test(val)) {
        return false;
    } else {
        return true;
    }
}

function engNameRule(val) {
    var nameText = /[^\a-zA-Z\.\·\ ]/g;
    if (!nameText.test(val)) {
        return false;
    } else {
        return true;
    }
}

/*身份证号码认证*/
function isIdNo(val) {
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)|(^[A-Z]\d{6}$)/;
    if (reg.test(val) === false) {
        return false;
    } else {
        return true;
    }
}
//车牌号验证方法
function isIdNoNew(val) {
    var reg = /^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
    if (reg.test(val)) {
        return false;
    }
    else {
        return true;
    }
}
/**
 * <p>替换URL中的某个参数的值</p>
 * @param url
 * @param arg
 * @param val
 * @returns {*}
 */
function changeUrlArg(url, arg, val){
    var pattern = arg+'=([^&]*)';
    var replaceText = arg+'='+val;
    return url.match(pattern) ? url.replace(eval('/('+ arg+'=)([^&]*)/gi'), replaceText) : (url.match('[\?]') ? url+'&'+replaceText : url+'?'+replaceText);
}


function  commonChangeDtUrl(url){
    var thisUrl = url;
    if(url&&url.indexOf("javascript") == -1 && url.indexOf("#")==-1){
        thisUrl = addWeixinId(thisUrl);
        thisUrl = changeUrlArg(thisUrl,"js_id",getCookie("jsessionId"));
        thisUrl = changeUrlArg(thisUrl,"dt",(new Date()).getTime());
    }
    return thisUrl;
}



function addWeixinId(url){
    var thisUrl = url;
    if(typeof currWeixinId != 'undefined'  && currWeixinId != null){
        thisUrl = changeUrlArg(thisUrl,"weixin_id",currWeixinId);
    }
    return thisUrl;
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function regMatch(str) {
    var reg = new RegExp("[`~@#$^&|\\<>+=/~@#￥&]");
    if (reg.test(str)) {
        return true;
    }
    return false;
}

// 格式化价格
function priceFormat(obj){
    if(obj.value.length > 13){
        obj.value = obj.value.substring(0,13);
    }
    obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
    obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}

function keepTwoDecimalFull(num) {
    var result = parseFloat(num);
    if (isNaN(result)) {
        //alert('传递参数错误，请检查！');
        return num;
    }
    result = Math.round(num * 100) / 100;
    var s_x = result.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = s_x.length;
        s_x += '.';
    }
    while (s_x.length <= pos_decimal + 2) {
        s_x += '0';
    }
    return s_x;
}

// 得到表单数据
function getFormValues(formId) {
    var result = {};
    var form = $('#' + formId);
    var inputs = form.find('input');
    for (var i = 0; i < inputs.length; i++) {
        var input = $(inputs[i]);
        var id = input.attr('id');
        if (id) {
            result[(id + '').replace(/form_/g, '')] = $.trim(input.val());
        }
    }
    var selects = form.find('select');
    for (var i = 0; i < selects.length; i++) {
        var select = $(selects[i]);
        var id = select.attr('id');
        if (id) {
            result[(id + '').replace(/form_/g, '')] = $.trim(select.val());
        }
    }
    var textareas = form.find('textarea');
    for (var i = 0; i < textareas.length; i++) {
        var textarea = $(textareas[i]);
        var id = textarea.attr('id');
        if (id) {
            result[(id + '').replace(/form_/g, '')] = $.trim(textarea.val());
        }
    }
    if (!result.Id) {
        delete result.Id;
    }
    return result;
};

function commonAjax(url, postData, successHandle, dataType, contentType, async, errorHandle, timeout, caculateHeight) {
    if (url == null) {
       layer.msg("Ajax 地址错误！");
    }
    if (successHandle == undefined || successHandle == null) {
        successHandle = "";
    }
    if (errorHandle == undefined || errorHandle == null) {
        errorHandle = "";
    }
    var op = {
        type : "POST",
        url : url,
        dataType : dataType ? dataType : 'json',
        data : postData,
        async : typeof (async) === "boolean" ? async : true,// 默认为true
        traditional : true,
        success : function(result) {
            try {
                var json = result;
                if (json == null) {
                    return;
                }
// 如果 dataType为html 时 后台controller出现异常返回的是json时 需要转json做处理
                if (typeof (result) == "string") {
                    json = eval("(" + result + ")");
                }
            } catch (e) {
                console.log(e);
            }
            if (successHandle) {
                successHandle(result);
                /* 重新计算iframe页面高度 */
                try {
                    if(rs.iframe!=undefined){
                        rs.iframe.utils.setIframeHeight(caculateHeight);
                    }
                } catch (e) {
                    console.log(e);
                }
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.responseText);
            if (errorHandle) {
                errorHandle();
                rs.framework.layer.msg(errorThrown + "\n" + XMLHttpRequest.responseText);
            }
        },
        complete : function(XMLHttpRequest, T) {
        }
    }
// op.timeout = 5 * 1000;
    if (timeout) {
        op.timeout = timeout;
    }
    if (contentType) {
        op.contentType = contentType;
    }
    $.ajax(op);
};

// 弹窗内容
function commonMsg(msg){
    var tipsInfo="";
    tipsInfo+="<span style='font-size: 16px'>"+msg+"</span>";
    layer.msg(tipsInfo);
}
function commonNewMsg(msg){
    var tipsInfo="";
    tipsInfo+="<span style='font-size: 13px;'>"+msg+"</span>";
    layer.msg(tipsInfo);
}
// 友盟统计
function commonCzc(title){
    _czc.push(['_trackEvent', title, '点击']);
}
// 友盟统计
function commonCzc1(title,href){
    _czc.push(['_trackEvent', title, '点击']);
    location.href= commonChangeDtUrl(href);
}

// 弹窗内容
function commonMsg(msg,size){
    var tipsInfo="";
    tipsInfo+="<samp style='font-size: "+size+"px'>"+msg+"</samp>";
    layer.msg(tipsInfo);
}
function gotoCommonUrl(e){
    var id = e.getAttribute('id');
    location.href=id;
}
function commonRemMsg(msg,size){
    var tipsInfo="";
    tipsInfo+="<span style='font-size: "+size+"rem'>"+msg+"</span>";
    layer.msg(tipsInfo);
}
function getVipLevel(vipName){
	var ret="会员专享";
	if(vipName!=''&&vipName!='null'){
		var obj=vipName.split(";");
		if(vipName.indexOf('金钻卡;黑钻卡')>=0&&obj.length==2){
			ret="金钻、黑钻专享";
		}
        if(vipName.indexOf('黑钻卡;金钻卡')>=0&&obj.length==2){
            ret="金钻、黑钻专享";
        }
        if(vipName.indexOf('黑钻卡')>=0&&obj.length==1){
			ret="黑钻专享";
		}
        if(vipName.indexOf('金钻卡')>=0&&obj.length==1){
			ret="金钻、黑钻专享";
		}
	}
   return ret;
}
function  changeMainDtUrl(url){
    var thisUrl = url;
    if(url&&url.indexOf("javascript") == -1 && url.indexOf("#")==-1){
        thisUrl = addWMaineixinId(thisUrl);
        thisUrl = changeUrlArg(thisUrl,"js_id", getCookie("jsessionId"));
        thisUrl = changeUrlArg(thisUrl,"dt",(new Date()).getTime());
    }
    return thisUrl;
}
function addWMaineixinId(url){
    var thisUrl = url;
    if(typeof mainWeixinId != 'undefined'  && mainWeixinId != null){
        thisUrl = changeUrlArg(thisUrl,"weixin_id",mainWeixinId);
    }
    return thisUrl;
}

function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

var monthName=['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
Date.prototype.FormatNew = function (fmt) { //author: meizz 
	var o = {
		"M+": this.getMonth() + 1, //月份 
		"d+": this.getDate(), //日 
		"h+": this.getHours(), //小时 
		"m+": this.getMinutes(), //分 
		"s+": this.getSeconds(), //秒 
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		"S": this.getMilliseconds() //毫秒 
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o){
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	}
	return fmt;
}
function getLocalTime(nS) {     
	return new Date(parseInt(nS)).FormatNew("yyyy-MM-dd hh:mm:ss");
}

function getLocalDate(nS) {     
	return new Date(parseInt(nS)).FormatNew("yyyy-MM-dd");
}

function getCNLocalTime(nS) {     
	return new Date(parseInt(nS)).FormatNew("yyyy年MM月dd日hh时");
}
function getDateTime(nS) {     
	return new Date(parseInt(nS));
}
function getUTCString(strDate){
	var date=new Date(strDate.replace(/-/g,"/"));
	var strDate=monthName[date.getMonth()]+" "+date.getDate()+", "+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	return strDate;
}
function getTimeLong(strDate){
	var date=new Date(strDate.replace(/-/g,"/"));
	var time_sec = Math.floor(date.getTime());
	return time_sec;
}
function getQueryStr(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = decodeURI(window.location.search).substr(1).match(reg);
	if (r != null) 
		return unescape(r[2]);
	return "";
}
Date.prototype.pattern=function(fmt) {         
    var o = {         
    "M+" : this.getMonth()+1, //月份         
    "d+" : this.getDate(), //日         
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
    "H+" : this.getHours(), //小时         
    "m+" : this.getMinutes(), //分         
    "s+" : this.getSeconds(), //秒         
    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
    "S" : this.getMilliseconds() //毫秒         
    };         
    var week = {         
    "0" : "/u65e5",         
    "1" : "/u4e00",         
    "2" : "/u4e8c",         
    "3" : "/u4e09",         
    "4" : "/u56db",         
    "5" : "/u4e94",         
    "6" : "/u516d"        
    };         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
}
//获取零点时间
function getZeroDate(tag){
	var startTime=null;
	var now = new Date(); //当前日期 
	var nowDayOfWeek = now.getDay(); //今天本周的第几天 
	var nowDay = now.getDate(); //当前日 
	var nowMonth = now.getMonth(); //当前月 
	var nowYear = now.getYear(); //当前年 
	nowYear += (nowYear < 2000) ? 1900 : 0; //
	var lastMonthDate = new Date(); //上月日期
	lastMonthDate.setDate(1);
	lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
	var lastYear = lastMonthDate.getYear();
	var lastMonth = lastMonthDate.getMonth();
	if(tag){
		switch(tag){
		case 'day':
			startTime=new Date(nowYear,nowMonth,nowDay);
			break;
		case 'week':
			startTime = new Date(nowYear,nowMonth,(nowDay-nowDayOfWeek)+1);
			break;
		case 'month':
			startTime=new Date(nowYear,nowMonth,1);
			break;
		case 'year':
			startTime=new Date(nowYear,0,1);
			break;
			default:
				break;
		}
		return startTime;
	}
}
//获取url参数
function getQueryString(name){
    var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    var result=window.location.search.substr(1).match(reg);
    if(result) {
        return decodeURI(result[2]);
    }
    return null;
}
//获取url参数
function getQueryStringJin(name){
    var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    var result;
    if(window.location.href.indexOf('#')>-1){
        var str =window.location.href.split('?')[1];
        result=str.match(reg);
    }else{
        result=window.location.search.substr(1).match(reg);
    }
    if(result) {
        return decodeURI(result[2]);
    }
    return null;
}

//复制对象
function clone(myObj){
    if(typeof(myObj) != 'object') return myObj;
    if(myObj == null) return myObj;
    var myNewObj = new Object();
    for(var i in myObj)
        myNewObj[i] = clone(myObj[i]);
    return myNewObj;
}

function mergeCells(data,fieldName,colspan,target){
    //声明一个map计算相同属性值在data对象出现的次数和
    var sortMap = {};
    for(var i = 0 ; i < data.length ; i++){
        for(var prop in data[i]){
            if(prop == fieldName[0]){
                var key = data[i][prop]
                if(sortMap.hasOwnProperty(key)){
                    sortMap[key] = sortMap[key] * 1 + 1;
                } else {
                    sortMap[key] = 1;
                }
                break;
            }
        }
    }
    var index = 0;
    for(var prop in sortMap){
        var count = sortMap[prop] * 1;
        for(var j in fieldName) {
            $(target).bootstrapTable('mergeCells', {index: index, field: fieldName[j], colspan: colspan, rowspan: count});
        }
        index += count;
    }
}


//扩展form
(function($) {
    $.fn.extend({
        //表单加载json对象数据
        setForm: function (jsonValue) {
            var obj = this;
			var json=$.parseJSON(jsonValue) ;
            $.each(json, function (name, ival) {
                var $oinput = obj.find("input[name=" + name + "]");
                if ($oinput.attr("type") == "checkbox") {
                    if (ival !== null) {
                        var checkboxObj = $("[name=" + name + "]");
                        var checkArray = ival.split(";");
                        for (var i = 0; i < checkboxObj.length; i++) {
                            for (var j = 0; j < checkArray.length; j++) {
                                if (checkboxObj[i].value == checkArray[j]) {
                                    checkboxObj[i].click();
                                }
                            }
                        }
                    }
                }
                else if ($oinput.attr("type") == "radio") {
                    $oinput.each(function () {
                        var radioObj = $("[name=" + name + "]");
                        for (var i = 0; i < radioObj.length; i++) {
                            if (radioObj[i].value == ival) {
                                radioObj[i].click();
                            }
                        }
                    });
                }
                else if ($oinput.attr("type") == "textarea") {
                    obj.find("[name=" + name + "]").html(ival);
                }
                else {
                    obj.find("[name=" + name + "]").val(ival);
                }
            })




        }
    });
})(jQuery);
var Tables;
var parmArr;
$(function(){
	initTimeBtn();
	initDatetimepicker();
	Tables = new Table();
	Tables.Init();
});


function initDatetimepicker(){
	$(".form_datetime").datetimepicker({
		format: "yyyy-mm-dd",
		autoclose: true,
		todayBtn: true,
		todayHighlight: true,
		showMeridian: true,
		pickerPosition: "bottom-left",
		language: 'zh-CN',//中文，需要引用zh-CN.js包
		startView: 2,//月视图
		minView: 2//日期时间选择器所能够提供的最精确的时间选择视图
	});
}
function success(data) {
	// var time=$("#queryTime").val().substring(5,7).replace("0","");
	// $("#tabTitle").html("设施运行情况汇总");
	$("#table").bootstrapTable("load",data);
}

//加载图表
var Table=function(){
	Table = new Object();
	Table.Init=function(){
		$("#table").bootstrapTable({
			method: 'get',
			toolbar: '#toolbar', //工具按钮用哪个容器
			toggle:"tableCell",
			url:'http://139.159.232.250:8080/agsupport_swj/rest/check/countFacilityStateInfo',//
			rowStyle:"rowStyle",
			cache:false,
			pagination:true,
			dataType:'jsonp',
			striped:true,
			sidePagination:"server",
			pageNumber: 1,
			pageSize: 15,
			pageList: [15, 25, 50, 100],
			clickToSelect:true,
			queryParams:Table.queryParms,
			clickToSelect:true,
			singleSelect:true,
			onLoadSuccess: function(data){  //加载成功时执行
				// $("#tabTitle").html("农村生活污水治理设施运行维护管理定期考核成绩情况表");
				if(data.rows) {
					mergeCells(data.rows, ["week"], 1, $('#table'));
				}
			},
			columns:[
				[
					{field: 'checkStatus',checkbox: 'true',visible:false,colspan: 1,rowspan: 2},
					{title:'序号',align:'center',visible:false,colspan: 1,rowspan: 2,formatter: function (value, row, index) {return index+1;}},
					{field:'id',title:'主键',visible:false,align:'center',colspan: 1,rowspan: 2},
					{field:'orgName',title:' 　区划　 ',visible:true,align:'center',colspan: 1,rowspan: 2
				,formatter: function (value, row, index)
                					 {
                					 if(index==8){
                					 return "<div style='font-weight: bold;'>"+value+"</div>";
                					 }else{
                					 return value;
                					 }
                					 }

                					 },
					{field:'total',title:'总数',visible:true,align:'center',colspan: 1,rowspan: 2
					,formatter: function (value, row, index) {
                  if(value==null){
                   value = 0;
                   }
                   if(index==8){
                   return "<div style='text-align: right;font-weight: bold;'>"+value+"</div>";
                   }else{
                    return "<div style='text-align: right'>"+value+"</div>";
                   }
                   }
                   },

					// {field:'rank',title:'状态',visible:true,align:'center',colspan: 6,rowspan: 1},
					{field:'rank',title:'正常运行',visible:true,align:'center',colspan: 2,rowspan: 1},
					{field:'rank',title:'带病运行',visible:true,align:'center',colspan: 1,rowspan: 1},
					{field:'rank',title:'未运行',visible:true,align:'center',colspan:1,rowspan: 1},
					// {field:'assessvillagenum',title:'数量',visible:true,align:'center'},
					// {field:'percent',title:'百分比',visible:true,align:'center'},


				],
				// [
				// 	{field:'rank',title:'正常运行',visible:true,align:'center',colspan: 2,rowspan: 1},
				// 	{field:'rank',title:'带病运行',visible:true,align:'center',colspan: 1,rowspan: 1},
				// 	{field:'rank',title:'未运行',visible:true,align:'center',colspan:1,rowspan: 1},
				// ],
				[
					{field:'numZc',title:'数量',visible:true,align:'center',
					formatter: function (value, row, index) {
                      if(value==null){
                           value = 0;
                       }
                   if(index==8){
                   return "<div style='text-align: right;font-weight: bold;'>"+value+"</div>";
                   }else{
                     return "<div style='text-align: right'>"+value+"</div>";
                   }
                   }},

					{field:'percentZc',title:'正常<br>运行率',visible:true,align:'center',formatter: function (value, row, index) {
                   if(value==null){
                       value = 0;
                   }
                    if(index==8){
                    return "<div style='text-align: right;font-weight: bold;'>"+parseInt(value)+"%"+"</div>";
                    }else{

                      return "<div style='text-align: right'>"+parseInt(value)+"%"+"</div>";
                    }
                    }},

					{field:'numDb',title:'数量',visible:true,align:'center'
					,formatter: function (value, row, index) {
                   if(value==null){
                       value = 0;
                   }
                    if(index==8){
                   return "<div style='text-align: right;font-weight: bold;'>"+value+"</div>";
                   }else{
                     return "<div style='text-align: right'>"+value+"</div>";
                   }
                   }},

					{field:'numWyx',title:'数量',visible:true,align:'center'
					,formatter: function (value, row, index) {
                    if(value==null){
                      value = 0;
                    }
                   if(index==8){
                   return "<div style='text-align: right;font-weight: bold;'>"+value+"</div>";
                   }else{
                     return "<div style='text-align: right'>"+value+"</div>";
                   }}},
				]
			]
		});
	},
		Table.queryParms=function(params){
			var temp = {
				type:"维管单位",
				parmArr:parmArr,
			};
			return temp;
		}
	return Table;
}

function replaceClass(id,srcClass,destClass){
	$('#'+id).removeClass(srcClass);
	$('#'+id).addClass(destClass);
}
//格式化时间
function format_date(val,rows,index){
	if(val)
		return getLocalTime(val.time);
	else
		return "";
}
//刷新
function refreshTable(){
	$("#table").bootstrapTable('refresh');
}
//搜索
function search(){
	refreshTable();
}
//清空
function resets(){
	$('#myform')[0].reset();
	refreshTable();
	initTimeBtn();
}

function wgdwDate() {
	initTimeBtn();
	search();
}

function zjDate() {
	initTimeBtn();
	search();
}

function getDate(strDate) {
	var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
			function(a) {
				return parseInt(a, 10) - 1;
			}).match(/\d+/g) + ')');
	return date;
}


function defineDate() {
	var defineDate = $("#timeDefine").val();
	var type= $("input[type='radio']:checked").val();
	var now = new Date(Date.parse(defineDate.replace(/-/g,"/"))) ;//da_2 =  Tue Aug 08 2017 00:00:00 GMT+0800 (中国标准时间)
	var timeOne = now.getFullYear() + "-" +((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1);
	var timeTwo = now.getFullYear() + "-" +((now.getMonth())<10?"0":"")+(now.getMonth());

	var dateArrayMonth = getInfo(timeOne.substr(0,4),timeOne.substr(5,2))
	var dateArrayPreviousMonth = getInfo(timeTwo.substr(0,4),timeTwo.substr(5,2))
	// var dateArrayMonth1=[dateArrayPreviousMonth[dateArrayPreviousMonth.length-1]].concat(dateArrayMonth);
	// dateArrayMonth1.pop();
	dateArrayMonth.unshift(dateArrayPreviousMonth[dateArrayPreviousMonth.length-1]);
	dateArrayMonth.pop();
	console.log(dateArrayMonth);
	parmArr=dateArrayMonth;
	$('#timeDefine').val(timeOne);
	search();
}

//获取某年某月的星期信息
function getInfo(year, month) {
	var dateArray = new Array();
	var d = new Date();
	console.log(d)
	// what day is first day
	d.setFullYear(year, month-1, 1);
	var w1 = d.getDay();
	if (w1 == 0) w1 = 7;
	// total day of month
	d.setFullYear(year, month, 0);
	var dd = d.getDate();
	// first Monday
	if (w1 != 1) d1 = 7 - w1 + 2;
	else d1 = 1;
	week_count = Math.ceil((dd-d1+1)/7);
	for (var i = 0; i < week_count; i++) {
		var monday = d1+i*7;
		var sunday = monday + 6;
		var from = year+"-"+month+"-"+(monday<10?0:"")+monday;
		var to;
		if (sunday <= dd) {
			to = year+"-"+month+"-"+(sunday<10?0:"")+sunday;
		} else {
			d.setFullYear(year, month-1, sunday);
			to = d.getFullYear()+"-"+((d.getMonth()+1)<10?"0":"")+(d.getMonth()+1)+"-"+(d.getDate()<10?"0":"")+d.getDate();
		}
		dateArray[i]=from + "/" + to + "";

	}
	return dateArray;
}

function initTimeBtn() {
	var type= $("input[type='radio']:checked").val();
	var now = new Date(); //da_2 =  Tue Aug 08 2017 00:00:00 GMT+0800 (中国标准时间)
	//当前时间字符串
	var timeOne = now.getFullYear() + "-" +((now.getMonth())<10?"0":"")+(now.getMonth());
	var timeTwo = now.getFullYear() + "-" +((now.getMonth()-1)<10?"0":"")+(now.getMonth()-1);

	var dateArrayMonth = getInfo(timeOne.substr(0,4),timeOne.substr(5,2))
	var dateArrayPreviousMonth = getInfo(timeTwo.substr(0,4),timeTwo.substr(5,2))
	// var dateArrayMonth1=[dateArrayPreviousMonth[dateArrayPreviousMonth.length-1]].concat(dateArrayMonth);
	// dateArrayMonth1.pop();
	dateArrayMonth.unshift(dateArrayPreviousMonth[dateArrayPreviousMonth.length-1]);
	dateArrayMonth.pop();
	console.log(dateArrayMonth);
	parmArr=dateArrayMonth;


	$('#timeDefine').val(timeOne);
	// $('#startTime').val(dateWg);
	// $('#endTime').val(enddateWg);

	// $('#endTime').val(getZeroDate('day').pattern('yyyy-MM-dd'));
	$("#timeDefine").datetimepicker({
		format: 'yyyy-mm',
		autoclose: true,
		todayBtn: true,
		startView: 'year',
		minView:'year',
		maxView:'decade',
		language:  'zh-CN',
	}).on('changeDate',function(ev){
		if(ev.date){
			var today=getZeroDate('day').pattern('yyyy-MM-dd');
			var setDate=ev.date.pattern('yyyy-MM-dd');
			//今日按钮的样式调整
			replaceClass('allTimeBtn', 'btn-primary', 'btn-default');
		}
	});
	$("#startTime").datetimepicker({
		format: 'yyyy-mm-dd',
		autoclose:true,
		pickerPosition:'bottom-right', // 样式
		minView: 2,    // 显示到天
		initialDate:new Date(),  // 初始化日期
		todayBtn: true,  //默认显示今日按钮
		language:'zh-CN'//显示中文
	}).on('changeDate',function(ev){
		if(ev.date){
			var today=getZeroDate('day').pattern('yyyy-MM-dd');
			var setDate=ev.date.pattern('yyyy-MM-dd');
			//今日按钮的样式调整
			replaceClass('allTimeBtn', 'btn-primary', 'btn-default');
		}
	});
	$("#endTime").datetimepicker({
		format: 'yyyy-mm-dd',
		autoclose:true,
		pickerPosition:'bottom-right', // 样式
		minView: 2,    // 显示到天
		initialDate: new Date(),  // 初始化日期
		todayBtn: true,  //默认显示今日按钮
		language:'zh-CN'//显示中文
	}).on('changeDate',function(ev){
		if(ev.date){
			// var today=getZeroDate('day').pattern('yyyy-MM-dd');
			// var setDate=ev.date.pattern('yyyy-MM-dd');
			//今日按钮的样式调整
			replaceClass('allTimeBtn', 'btn-primary', 'btn-default');
		}
	});
}


function  exportExcle() {
	var queryTime=$("#queryTime").val();
	var queryTimeEnd=$("#queryTimeEnd").val();
	window.open( '/agsupport_swj/rest/reportForm/getfacilitiesAssessDetail?startTime="'+queryTime+'"&endTime="'+queryTimeEnd+'"&export=true');
}



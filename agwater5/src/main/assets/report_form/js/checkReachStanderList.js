var Tables;
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
			url:'http://210.72.5.185:8080/agsupport_swj/rest/check/getCheckReachStander',//
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
					mergeCells(data.rows, ["assesstime","townname"], 1, $('#table'));
				}
			},
			columns:[
				[
					{field: 'checkStatus',visible:false,checkbox: 'true'},
					{title:'序号',align:'center',formatter: function (value, row, index) {
					if(index==8){
					return "";
					}
					return index+1;
					}},
					{field:'id',title:'主键',visible:false,align:'center'},
					{field:'orgName',title:'区划',visible:true,align:'center',formatter: function (value, row, index)
					 {
					 if(index==8){
					 return "<div style='font-weight: bold;'>"+value+"</div>";
					 }else{
					 return value;
					 }
					 }

					 },
					{field:'num',title:'达标数',visible:false,align:'center'},
					{field:'num',title:'应巡检数',visible:false,align:'center'},
					{field:'percent',title:'达标率',visible:true,align:'center',formatter:function (value,row,index) {
						if(index==8){
						  if(row.percent.toString().indexOf(".")>-1)
                            return "<div style='text-align: right;font-weight: bold;'>"+row.percent+"%"+"</div>";
                          else
                          	return "<div style='text-align: right;font-weight: bold;'>"+row.percent+".00"+"%"+"</div>";
						}

						if(row.percent.toString().indexOf(".")>-1)
							return "<div style='text-align: right'>"+row.percent+"%"+"</div>";
						else
							return "<div style='text-align: right'>"+row.percent+".00"+"%"+"</div>";
					}},
				],
			]
		});
	},
		Table.queryParms=function(params){
			var temp = {
				startTime:$("#startTime").val(),
				endTime:$("#endTime").val(),
				type:$("input[type='radio']:checked").val(),
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
	//当前时间字符串
	var time = now.getFullYear() + "-" +((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
	var timeDay= time.substr(0,10);
	if(parseInt(now.getDay())==0){
		var wg=13;
	}else{
		var wg=parseInt(now.getDay())+6;
	}

	var dateWg = new Date(now.getTime() - wg * 24 * 3600 * 1000);
	var yearWg = dateWg.getFullYear();
	var monthWg =((dateWg.getMonth()+1)<10?"0":"")+(dateWg.getMonth()+1);
	var dayWg = (dateWg.getDate()<10?"0":"")+dateWg.getDate();
	var dateWg = yearWg + '-' + monthWg + '-' + dayWg;

	//镇街
	// var nowZj = new Date(Date.parse(dateWg.replace(/-/g,"/"))) //da_2 =  Tue Aug 08 2017 00:00:00 GMT+0800 (中国标准时间)
	var nowZj = new Date(time.replace(/-/g,"/")) //da_2 =  Tue Aug 08 2017 00:00:00 GMT+0800 (中国标准时间)
	// if(parseInt(nowZj.getDay())==0){
	// 	var zj=13;
	// }else{
	// 	var zj=parseInt(nowZj.getDay())+6;
	// }
	// var dateZj = new Date(nowZj.getTime() - zj * 24 * 3600 * 1000);
	var dateZj = nowZj;
	var yearZj = dateZj.getFullYear();
	var monthZj = ((dateZj.getMonth()+1)<10?"0":"")+(dateZj.getMonth()+1);
	var dayZj = (dateZj.getDate()<10?"0":"")+dateZj.getDate();
	var dayZjStart;
	var dayZjEnd;
	if(dateZj.getDate()<15){
		monthZj = ((dateZj.getMonth())<10?"0":"")+(dateZj.getMonth());
		dateZj.setMonth(((dateZj.getMonth())<10?"0":"")+(dateZj.getMonth()))
		dateZj.setDate(0);
		dayZjStart="16";
		dayZjEnd=dateZj.getDate();
	}else{
		monthZj = ((dateZj.getMonth()+1)<10?"0":"")+(dateZj.getMonth()+1);
		dateZj.setMonth(((dateZj.getMonth()+1)<10?"0":"")+(dateZj.getMonth()+1))
		dayZjStart="01";
		dayZjEnd="15";
	}
	var dateZjStart= yearZj + '-' + monthZj + '-' + dayZjStart;
	var dateZjEnd= yearZj + '-' + monthZj + '-' + dayZjEnd;

	var enddateWg = new Date(now.getTime() - (now.getDay()==0?7:now.getDay()) * 24 * 3600 * 1000);
	var endyearWg = enddateWg.getFullYear();
	var endmonthWg =((enddateWg.getMonth()+1)<10?"0":"")+(enddateWg.getMonth()+1);
	var enddayWg = (enddateWg.getDate()<10?"0":"")+enddateWg.getDate();
	var enddateWg = endyearWg + '-' + endmonthWg + '-' + enddayWg;


	$('#timeDefine').val(time);
	if(type=="维管单位"){
		$('#startTime').val(dateWg);
		$('#endTime').val(enddateWg);
	}else{
		$('#startTime').val(dateZjStart);
		$('#endTime').val(dateZjEnd);
	}
	search();
}

function initTimeBtn() {
	var type= $("input[type='radio']:checked").val();
	var now = new Date(); //da_2 =  Tue Aug 08 2017 00:00:00 GMT+0800 (中国标准时间)
	//当前时间字符串
	var time = now.getFullYear() + "-" +((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
	var timeDay= time.substr(0,10);
	if(parseInt(now.getDay())==0){
		var wg=13;
	}else{
		var wg=parseInt(now.getDay())+6;
	}
	//维管单位
	var dateWg = new Date(now.getTime() - wg * 24 * 3600 * 1000);
	var yearWg = dateWg.getFullYear();
	var monthWg =((dateWg.getMonth()+1)<10?"0":"")+(dateWg.getMonth()+1);
	var dayWg = (dateWg.getDate()<10?"0":"")+dateWg.getDate();
	var dateWg = yearWg + '-' + monthWg + '-' + dayWg;

	//镇街
	// var nowZj = new Date(Date.parse(dateWg.replace(/-/g,"/"))) //da_2 =  Tue Aug 08 2017 00:00:00 GMT+0800 (中国标准时间)
	var nowZj = new Date(time.replace(/-/g,"/")) //da_2 =  Tue Aug 08 2017 00:00:00 GMT+0800 (中国标准时间)
	// if(parseInt(nowZj.getDay())==0){
	// 	var zj=13;
	// }else{
	// 	var zj=parseInt(nowZj.getDay())+6;
	// }
	// var dateZj = new Date(nowZj.getTime() - zj * 24 * 3600 * 1000);
	var dateZj = nowZj;
	var yearZj = dateZj.getFullYear();
	var monthZj = ((dateZj.getMonth()+1)<10?"0":"")+(dateZj.getMonth()+1);
	var dayZj = (dateZj.getDate()<10?"0":"")+dateZj.getDate();
	var dayZjStart;
	var dayZjEnd;
	if(dateZj.getDate()<15){
		monthZj = ((dateZj.getMonth())<10?"0":"")+(dateZj.getMonth());
		dateZj.setMonth(((dateZj.getMonth())<10?"0":"")+(dateZj.getMonth()))
		dateZj.setDate(0);
		dayZjStart="16";
		dayZjEnd=dateZj.getDate();
	}else{
		monthZj = ((dateZj.getMonth()+1)<10?"0":"")+(dateZj.getMonth()+1);
		dateZj.setMonth(((dateZj.getMonth()+1)<10?"0":"")+(dateZj.getMonth()+1))
		dayZjStart="01";
		dayZjEnd="15";
	}
	var dateZjStart= yearZj + '-' + monthZj + '-' + dayZjStart;
	var dateZjEnd= yearZj + '-' + monthZj + '-' + dayZjEnd;

	var enddateWg = new Date(now.getTime() - (now.getDay()==0?7:now.getDay()) * 24 * 3600 * 1000);
	var endyearWg = enddateWg.getFullYear();
	var endmonthWg =((enddateWg.getMonth()+1)<10?"0":"")+(enddateWg.getMonth()+1);
	var enddayWg = (enddateWg.getDate()<10?"0":"")+enddateWg.getDate();
	var enddateWg = endyearWg + '-' + endmonthWg + '-' + enddayWg;

	console.log(dateWg);
	$('#timeDefine').val(time);
	if(type=="维管单位"){
		$('#startTime').val(dateWg);
		$('#endTime').val(enddateWg);
	}else{
		$('#startTime').val(dateZjStart);
		$('#endTime').val(dateZjEnd);
	}
	// $('#endTime').val(getZeroDate('day').pattern('yyyy-MM-dd'));
	$("#timeDefine").datetimepicker({
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



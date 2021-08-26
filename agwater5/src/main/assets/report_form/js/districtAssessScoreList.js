var Tables;
$(function(){
	initTimeBtn();
	initDatetimepicker();
	Tables = new Table();
	Tables.Init();
});
function sdasdasdas(data) {
	var time=$("#queryTime").val().substring(5,7).replace("0","");
	$("#tabTitle").html(time+"月份各区区级考核评分结果");
	$("#table").bootstrapTable("load",data);
}

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


//加载图表
var Table=function(){
	Table = new Object();
	Table.Init=function(){
		$("#table").bootstrapTable({
			method: 'get',
			toolbar: '#toolbar', //工具按钮用哪个容器
			toggle:"tableCell",
			url:'http://210.72.5.185:8080/agsupport_swj/rest/reportForm/getDistricAssessScoreList',//
			rowStyle:"rowStyle",
			cache:false,
			pagination:true,
			dataType:'jsonp',
			striped:true,
			sidePagination:"server",
			pageNumber: 1,
			pageSize: 10,
			pageList: [10, 25, 50, 100],
			clickToSelect:true,
			queryParams:Table.queryParms,
			clickToSelect:true,
			singleSelect:true,
			onLoadSuccess: function(){  //加载成功时执行
				var time=$("#queryTime").val().substring(5,7).replace("0","");
				$("#tabTitle").html(time+"月份各区区级考核评分结果");
			},
			columns:[
				[
					{field: 'checkStatus',visible:false,checkbox: 'true'},
					{title:'序号',align:'center',formatter: function (value, row, index) {
						console.log(row);
						if(row.districtName==""||row.districtName==null){
							return "合计";
						}else{
							return index+1;
						}
					}
					},
					{field:'assessDate',title:'考核月份',visible:false,align:'center'},
					{field:'id',title:'主键',visible:false,align:'center'},
					{field:'districtName',title:'行政区/单位',visible:true,align:'center',formatter: function (value, row, index) {
						if(value=="林业和园林(农污)"){
							return "林场";
						}else{
							return value.substring(0,3);
						}
					}},
					{field:'villageSum',title:'行政村（社区）<br>总数',visible:true,align:'center'},
					{field:'totalScore',title:'区级考核<br>评分',visible:true,align:'center'},
				]
			]
		});
	},
		Table.queryParms=function(params){
			var temp = {
				pagesize:params.limit,
				pageno: params.offset/params.limit+1,
				queryTime:$("#queryTime").val(),
				startTime:$("#startTime").val(),
				endTime:$("#endTime").val(),
			};
			return temp;
		}
	return Table;
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
}



function initTimeBtn() {
	$('#startTime').val(getZeroDate('year').pattern('yyyy-MM-dd'));
	$('#endTime').val(getZeroDate('day').pattern('yyyy-MM-dd'));
	$('#exportTime').val(getZeroDate('month').pattern('yyyy-MM'));
	$('#queryTime').val(getZeroDate('month').pattern('yyyy-MM'));
	$("#startTime").datetimepicker({
		format: 'yyyy-mm-dd',
		autoclose: true,
		pickerPosition: 'bottom-right', // 样式
		minView: 2,    // 显示到天
		// initialDate: dateOne,  // 初始化日期
		todayBtn: true,  //默认显示今日按钮
		language: 'zh-CN'//显示中文
	}).on('changeDate', function (ev) {
		if (ev.date) {
			// var today=getZeroDate('day').pattern('yyyy-MM-dd');
			// var setDate=ev.date.pattern('yyyy-MM-dd');
			//今日按钮的样式调整
			replaceClass('allTimeBtn', 'btn-primary', 'btn-default');
		}
	});
	$("#endTime").datetimepicker({
		format: 'yyyy-mm-dd',
		autoclose: true,
		pickerPosition: 'bottom-right', // 样式
		minView: 2,    // 显示到天
		initialDate: new Date(),  // 初始化日期
		todayBtn: true,  //默认显示今日按钮
		language: 'zh-CN'//显示中文
	}).on('changeDate', function (ev) {
		if (ev.date) {
			// var today=getZeroDate('day').pattern('yyyy-MM-dd');
			// var setDate=ev.date.pattern('yyyy-MM-dd');
			//今日按钮的样式调整
			replaceClass('allTimeBtn', 'btn-primary', 'btn-default');
		}
	});
	$("#exportTime").datetimepicker({
		format: 'yyyy-mm',
		autoclose: true,
		todayBtn: true,
		startView: 'year',
		minView:'year',
		maxView:'decade',
		language:  'zh-CN',
	}).on('changeDate', function (ev) {
		if (ev.date) {
			// var today=getZeroDate('day').pattern('yyyy-MM-dd');
			// var setDate=ev.date.pattern('yyyy-MM-dd');
			//今日按钮的样式调整
			replaceClass('allTimeBtn', 'btn-primary', 'btn-default');
		}
	});
	$("#queryTime").datetimepicker({
		format: 'yyyy-mm',
		autoclose: true,
		todayBtn: true,
		startView: 'year',
		minView:'year',
		maxView:'decade',
		language:  'zh-CN',
	}).on('changeDate', function (ev) {
		if (ev.date) {
			// var today=getZeroDate('day').pattern('yyyy-MM-dd');
			// var setDate=ev.date.pattern('yyyy-MM-dd');
			//今日按钮的样式调整
			replaceClass('allTimeBtn', 'btn-primary', 'btn-default');
		}
	});
}


function  exportExcle() {
	var queryTime=$("#queryTime").val();
	window.open( '/agsupport_swj/rest/reportForm/getDistricAssessScoreList?queryTime="'+queryTime+'"&export=true');
}



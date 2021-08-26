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
function sdasdasdas(data) {
	$("#tabTitle").html("区考核标准");
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
			url:'http://139.159.232.250:8080/agsupport_swj/rest/reportForm/getAssessIndexDefList',//
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
			onLoadSuccess: function(data){  //加载成功时执行
				$("#tabTitle").html("区考核标准");
			},
			columns:[
				[
					{field:'id',title:'主键',visible:true,align:'center'},
					{field:'index',title:'记录数',visible:true,align:'center',formatter:function(value,row,index){return index+1;}},
					{field:'actualRank',title:'序号',visible:true,align:'center'},
					{field:'indexName',title:'项目',visible:true,align:'left'},
					{field:'requirement',title:'质量要求',visible:true,align:'left'},
					{field:'assessMethod',title:'考核办法',visible:true,align:'left'},
					{field:'assessStandard',title:'考核标准',visible:true,align:'left'},
					{field:'total',title:'总分',visible:true,align:'center'},
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
				appRanking:"false",
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
	window.open( '/agsupport_swj/rest/reportForm/getAssessIndexDefList?export=true');
}



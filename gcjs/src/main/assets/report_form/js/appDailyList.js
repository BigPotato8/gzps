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
	var time=$("#queryTime").val().substring(5,7).replace("0","");
	$("#tabTitle").html("区签到排名");
	$("#table").bootstrapTable("load",data);
}

//加载图表
var Table=function(){
	Table = new Object();
	Table.Init=function(data){
		$("#table").bootstrapTable({
			method: 'get',
			toolbar: '#toolbar', //工具按钮用哪个容器
			toggle:"tableCell",
			url:'http://139.159.232.250:8080/agsupport_swj/rest/dailySign/dailySignCountByTown?callback=success',//
			data:data,
			rowStyle:"rowStyle",
			cache:false,
			dataType:'jsonp',
			pagination:false,
			//jsonp: "jsonpCallback", //服务端用于接收callback调用的function名的参数
			//jsonpCallback: "success_jsonpCallback", //callback的function名称,服务端会把名称和data一起传递回来
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
				dataOld=data;
				alert(dataOld);
				var time=$("#queryTime").val().substring(5,7).replace("0","");
				$("#tabTitle").html("区签到排名");
				refreshTable();
			},
			columns:[
				[
					{field: 'checkStatus',checkbox: 'true',visible:false},
					{title:'排名',align:'center',formatter: function (value, row, index) {
							return index+1;
					}
					},
					{field:'id',title:'主键',visible:false,align:'center'},
					{field:'ORG_NAME',title:'区',visible:true,align:'center',formatter: function (value, row, index) {
						if(value=="林业和园林局(农污)"){
							return "林场";
						}else{
							return value.substring(0,3);
						}
					}},
					{field:'percent',title:'签到率',visible:true,align:'center',formatter: function (value, row, index) {
							 return "<div style='text-align: right'>"+value+"</div>";
					}},
				]
			]
		});
	},
		Table.queryParms=function(params){
			var temp = {
				pagesize:params.limit,
				pageno: params.offset/params.limit+1,
				queryTime:$("#queryTime").val(),
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


function replaceClass(id,srcClass,destClass){
	$('#'+id).removeClass(srcClass);
	$('#'+id).addClass(destClass);
}


function initTimeBtn() {
	$('#queryTime').val(getZeroDate('day').pattern('yyyy-MM-dd'));
	$("#queryTime").datetimepicker({
		format: 'yyyy-mm-dd',
		autoclose: true,
		todayBtn: true,
		// startView: 'year',
		// minView:'year',
		minView:2,
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




<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- 此页面暂时未使用，是由变更提醒页面跳转而来，按照图纸编号、料单号等汇总统一更改状态 -->
<head>
<title>结构专业变更信息提醒</title>
</head>
<body>
	<h3 align="center">结构专业变更信息提醒</h3>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div class="search_all_div">
				<div class="search_item">
					<label class="w100">变更日期</label>
					<span class="ml50">
						<input id="update_date_search" name="update_date" class="easyui-datebox" data-options="width:150,editable:false" dkd-search-element="stt.update_date = text"/>
					</span>
				</div>
				  <div class="search_item">
					<label class="w100">项目名称</label>
					<span class="ml50">
						<input id="project_name_search" name="job_no" class="edit_combobox" data-options="width:150,url:'${ctx}/project/show.do'" dkd-search-element="job_no eq text"/>
					</span>
				</div>
			</div>
		</div>
	</form>
	<div style="margin-top:5px;width:100%;text-align:center">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDataChange()">查询</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="save()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="editReason()">变更状态</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('结构数据变更提醒','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackChange&module=remind')">返回</a>
	</div>
    <div class="datagrid_div" style="padding-left:10px">
	    <table id="data_remind_collect">
			<thead>
				<tr>
					<th data-options="field:'state',width:100,editor:{type:'combobox',options:{valueField:'value',textField:'text',editable:false,panelHeight:50}}">状态</th>
					<th data-options="field:'state_change_date',width:140">状态变更日期</th>
					<th data-options="field:'state_confirm_date',width:140">状态变更确认日期</th>
					<th data-options="field:'state_confirm_user',width:140">状态变更确认人</th>
					<th data-options="field:'shop_draw_no',width:160">图纸编号</th>
					<th data-options="field:'project_name',width:160">所属项目</th>
					<th data-options="field:'module_name',width:140">模块</th>
					<th data-options="field:'level_no',width:140">层数</th>
					<th data-options="field:'mto_no',width:140">料单号</th>
					<th data-options="field:'mto_row_no',width:140">料单行号</th>
					<th data-options="field:'update_date',width:160">变更日期</th>
					<th data-options="field:'change_cause_mark_user',width:140">变更标注人</th>
					<th data-options="field:'reason_for_change',width:140">变更原因</th>
				</tr>
			</thead>
		</table>
    </div>
<script type="text/javascript">
	var clickIndex;
	var editIndex;
	var stateOption = [{'text':'删除','value':'删除'},{'text':'作废','value':'作废'}];
	var stateUpdateOption = [{'text':'升版增加','value':'升版增加'},{'text':'新增','value':'新增'}];
	$(function(){
		$("#data_remind_collect").datagrid({
			rownumbers:true,
			url:"${ctx}/designDataChange/findByProfessional.do",
            onClickRow:onClickRow,
			singleSelect:true,
			pagination:true,
			queryParams:{
				"module":"remind"
			},
            onAfterEdit:function(){
            	$(".search_button").linkbutton();
            },
            onLoadSuccess:function(data){
            	$(".search_button").linkbutton();
            	clickIndex=null;
            	editIndex=null;
            },
	        view: detailview,
	        detailFormatter:function(index,row){
	          return '<div style="padding:2px"><table class="ddv"></table></div>';
	        },
	        onExpandRow: function(index,row){
	          var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
	          var updateDate=$("#update_date_search").datebox("getValue");
	          row.update_date=updateDate;
	          ddv.datagrid({
	            url:'${ctx}/designDataChange/findChangeDetails.do?project_name='+row.project_name+'&shop_draw_no='+row.shop_draw_no+'&module_name='+row.module_name+'&level_no='+row.level_no+'&update_date='+row.update_date
	            		+'&mtoNo='+row.mto_no+'&mtoRowNo='+row.mto_row_no+'&module=${module}',
	            fitColumns:true,
	            rownumbers:true,
	            loadMsg:'',
	            height:'auto',
	            columns:[[
				  {field:'id',title:'id',width:90,align:'center',hidden:'true'},
				  {field:'state',title:'状态',width:90,align:'center'},
				  {field:'state_change_date',title:'状态变更日期',width:90,align:'center'},
				  {field:'state_confirm_date',title:'状态变更确认日期',width:90,align:'center'},
	              {field:'state_confirm_user',title:'状态变更确认人',width:90,align:'center'},
	              {field:'shop_draw_no',title:'图纸编号',width:90,align:'center'},
	              {field:'project_name',title:'所属项目',width:90,align:'center'},
	              {field:'module_name',title:'模块',width:90,align:'center'},
	              {field:'level_no',title:'层数',width:90,align:'center'},
	              {field:'part_no',title:'零件编号',width:90,align:'center'},
	              {field:'part_name',title:'零件名称',width:90,align:'center'},
	              {field:'mto_no',title:'料单号',width:90,align:'center'},
	              {field:'mto_row_no',title:'料单行号',width:90,align:'center'},
	              {field:'change_cause_mark_user',title:'变更标注人',width:90,align:'center'},
	              {field:'reason_for_change',title:'变更原因',width:90,align:'center'},
	              {field:'change_cause_mark_date',title:'原因标注日期',width:90,align:'center'},
	              {field:'update_date',title:'变更日期',width:90,align:'center'}
	            ]],
	            onResize:function(){
	              $('#data_remind_collect').datagrid('fixDetailRowHeight',index);
	            },
	            onLoadSuccess:function(){
	              setTimeout(function(){
	                $('#data_remind_collect').datagrid('fixDetailRowHeight',index);
	              },0);
	            }
	          });
	          $('#data_remind_collect').datagrid('fixDetailRowHeight',index);
	        }	
		});
	});
	//结束编辑
    function endEditing(){
        if (editIndex == undefined){return true;}
        if ($("#data_remind_collect").datagrid("validateRow", editIndex)){
            $("#data_remind_collect").datagrid("endEdit", editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    //点击行
    function onClickRow(index,rowData){
    	clickIndex=index;
	}
    //标注变更原因
    function editReason(){
    	if(isEmpty(clickIndex)&&0!=clickIndex){
			$.messager.show({
				title:"警告",
				msg:"请选择要操作的单据！"
			});
    	}else{
    	    if (editIndex != clickIndex){
    	        if (endEditing()){
    	        	$("#data_remind_collect").datagrid("beginEdit", clickIndex);
    	            editIndex = clickIndex;
    	            $(".datagrid-editable-input").css({"width":"100%","height":"26px"});
    	            $(".datagrid-editable-input:eq(0)").focus();
    	            var statecollect = $('#data_remind_collect').datagrid('getEditor', { 'index': clickIndex,field:'state'});
    	            if("作废"== $(statecollect.target).combobox("getValue")){
    	            	$(statecollect.target).combobox('loadData', stateOption);
    	            }else{
    	            	$(statecollect.target).combobox('loadData', stateUpdateOption);
    	            }
    	        } 
    	    }
    	}
    }
    //设置初始化时间
    window.onload = function() {
    	var yesterday = getDay(-1, "-");
		$("#update_date_search").datebox("setValue",yesterday);
 	};
 	//查询按钮
 	function searchDataChange(){
 		var updateDate=$("#update_date_search").datebox("getValue");
 		var project_name=$("#project_name_search").textbox("getText");
		if("请选择"==project_name){
			project_name="";
		}
 		$("#data_remind_collect").datagrid({
  		   url:"${ctx}/designDataChange/findByProfessional.do",
	       queryParams: {          
	          	"is_history":'Y',
	          	"professional":"${professional}",
	          	"update_date":updateDate,
	          	'project_name':project_name,
	          	"module":"remind"
	       }
  		});
 	}
 	//获取昨天今天明天等日期的方法；假如昨天则num=-1，str为日期分割格式，假如为2017-12-25，则str设置为-
 	function getDay(num, str) {
 	    var today = new Date();
 	    var nowTime = today.getTime();
 	    var ms = 24*3600*1000*num;
 	    today.setTime(parseInt(nowTime + ms));
 	    var oYear = today.getFullYear();
 	    var oMoth = (today.getMonth() + 1).toString();
 	    if (oMoth.length <= 1) oMoth = '0' + oMoth;
 	    var oDay = today.getDate().toString();
 	    if (oDay.length <= 1) oDay = '0' + oDay;
 	    return oYear + str + oMoth + str + oDay;
 	}
 	//保存方法
 	function save(){
 		var rowIndex = editIndex;
 		if(endEditing()){
 	 		var rows = $('#data_remind_collect').datagrid('getRows');
 	 		if(isEmpty(rowIndex)&&0!=rowIndex){
 				$.messager.show({
 					title:"警告",
 					msg:"请选择一条数据更改状态!"
 				});
 	 			return;
 	 		}
 	 		var row = rows[rowIndex];
 	 		delete row.professional;
 			$.ajax({
 	 			type:"post",
 	 			url:"${ctx}/designDataChange/saveState.do",
 	 			contentType : "application/json;charset=utf-8",
 	 			data:JSON.stringify(row),
 	 			success:function(result){
 	 				handleReturn(result,function(){
 	 					$("#data_remind_collect").datagrid('reload');
 	 				});
 				}
 	 		});
 		}
 	}
</script>
</body>
</html>
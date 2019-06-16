<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>结构数据跟踪表</title>
<style type="text/css">
	#st_data_track_div a{
		display:block;
	    text-decoration: none;
	}
</style>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div class="search_all_div">
				<div class="search_item" ignore>
					<label class="w100">变更日期</label>
					<span class="ml50">
						<input id="update_date_search" name="update_date" class="easyui-datebox" data-options="width:200,editable:false" dkd-search-element="stt.update_date le text"/>
						<input id="update_date_hide" name = "update_date_hide" type="hidden"/>
					</span>
				</div>
				<div class="search_item" ignore>
					<label class="w100">项目名称</label>
					<span class="ml50">
						<input id="project_name_search" name="job_no" class="edit_combobox" data-options="width:200,url:'${ctx}/project/show.do'" dkd-search-element="job_no eq text"/>
					</span>
				</div>
			</div>
			<div style="margin-top:5px;width:100%;text-align:center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addStCondition()">新增条件</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeCondition()">删除条件</a>
				<c:if test="${module=='change'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="changeReason()">变更原因维护</a>
				</c:if>
				<c:if test="${module=='remind'}">
					<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="changeState()">状态变更</a> -->
					<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="stateConfirm()">变更原因确认</a> -->
				</c:if>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDesignDataChange()">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="resetSearch()">重置</a>
				<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="viewRecord()">历史记录</a> -->
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-print" onclick="exportData()">导出</a> -->
			</div>
		</div>
	</form>
	<div class="datagrid_div" id="st_data_track_div">
		<table id="data_track_change" align="center" style="width:100%;">
			<thead>
				<tr>
					<th data-options="field:'id',width:75,halign:'center',hidden:true,sortable:true">ID</th>
					<th data-options="field:'state',width:75,halign:'center',sortable:true">状态</th>
					<th data-options="field:'update_date',width:75,halign:'center',sortable:true">变更日期</th>
					<th data-options="field:'state_change_date',width:150,halign:'center',sortable:true,hidden:true">提示日期</th>
					<th data-options="field:'mark_date',width:150,halign:'center',sortable:true,hidden:true">标记日期</th>
					<th data-options="field:'mark_user',width:150,halign:'center',sortable:true,hidden:true">标记人</th>
					<th data-options="field:'reason_for_change',width:100,halign:'center',sortable:true,hidden:true">变更原因</th>
					<th data-options="field:'change_cause_mark_date',width:120,halign:'center',sortable:true,hidden:true">原因标注日期</th>
					<th data-options="field:'change_cause_mark_user',width:100,halign:'center',sortable:true,hidden:true">原因标注人</th>
					<th data-options="field:'project_name',width:100,halign:'center',sortable:true,hidden:true">项目名称</th>
					<th data-options="field:'job_no',width:100,halign:'center',sortable:true,hidden:true">项目工作号</th>
					<th data-options="field:'module_name',width:100,halign:'center',sortable:true,hidden:true">模块名称</th>
					<th data-options="field:'shop_draw_no',width:100,halign:'center',sortable:true,hidden:true">加设图纸编号</th>
					<th data-options="field:'shop_draw_rev',width:100,halign:'center',sortable:true,hidden:true">加设图纸版本</th>
					<th data-options="field:'contractor_draw_no',width:100,halign:'center',sortable:true,hidden:true">业主图纸编号</th>
					<th data-options="field:'contractor_draw_rev',width:100,halign:'center',sortable:true,hidden:true">业主图纸版本</th>
					<th data-options="field:'part_no',width:100,halign:'center',sortable:true,hidden:true">零件编号</th>
					<th data-options="field:'part_name',width:80,halign:'center',sortable:true,hidden:true">零件名称</th>
					<th data-options="field:'steel_type',width:100,halign:'center',sortable:true,hidden:true">零件类型</th>
					<th data-options="field:'structure_type',width:100,halign:'center',sortable:true,hidden:true">结构类型</th>
					<th data-options="field:'area_of_part',width:100,halign:'center',sortable:true,hidden:true">零件区域</th>
					<th data-options="field:'level_no',width:100,halign:'center',sortable:true,hidden:true">层数</th>
					<th data-options="field:'work_package_no',width:100,halign:'center',sortable:true,hidden:true">模块工作包号</th>
					<th data-options="field:'part_profile_name',width:100,halign:'center',sortable:true,hidden:true">零件规格名称</th>
					<th data-options="field:'part_profile',width:100,halign:'center',sortable:true,hidden:true">零件规格</th>
					<th data-options="field:'grade',width:100,halign:'center',sortable:true,hidden:true">材质</th>
					<th data-options="field:'qty',width:80,halign:'center',sortable:true,hidden:true">数量</th>
					<th data-options="field:'quantity_in_dm_mto',width:120,halign:'center',sortable:true,hidden:true">设计料单中的数量</th>
					<th data-options="field:'quantity_in_pd_mto',width:120,halign:'center',sortable:true,hidden:true">采办料单中的数量</th>
					<th data-options="field:'net_single_length',width:100,halign:'center',sortable:true,hidden:true">净长度</th>
					<th data-options="field:'net_single_width',width:100,halign:'center',sortable:true,hidden:true">净宽度</th>
					<th data-options="field:'unit_weight',width:100,halign:'center',sortable:true,hidden:true">单重</th>
					<th data-options="field:'net_single_weight',width:100,halign:'center',sortable:true,hidden:true">单净重</th>
					<th data-options="field:'net_total_weight',width:100,halign:'center',sortable:true,hidden:true">总净重</th>
					<th data-options="field:'gross_single_weight',width:100,halign:'center',sortable:true,hidden:true">单毛重</th>
					<th data-options="field:'gross_total_weight',width:80,halign:'center',sortable:true,hidden:true">总毛重</th>
					<th data-options="field:'net_area',width:100,halign:'center',sortable:true,hidden:true">净表面积</th>
					<th data-options="field:'surface_treatment',width:100,halign:'center',sortable:true,hidden:true">表面处理</th>
					<th data-options="field:'coating_system',width:100,halign:'center',sortable:true,hidden:true">油漆配套</th>
					<th data-options="field:'coating_area',width:100,halign:'center',sortable:true,hidden:true">油漆面积</th>
					<th data-options="field:'fireproof_type',width:100,halign:'center',sortable:true,hidden:true">防火类型</th>
					<th data-options="field:'fireproof_area',width:100,halign:'center',sortable:true,hidden:true">防火面积</th>
					<th data-options="field:'fireproof_length',width:100,halign:'center',sortable:true,hidden:true">防火长度</th>
					<th data-options="field:'fireproof_thickness',width:100,halign:'center',sortable:true,hidden:true">防火厚度</th>
					<th data-options="field:'wasted_by_drawing_update',width:160,halign:'center',align:'center',sortable:true,formatter:formatValue,hidden:true">材料是否因升版作废</th>
					<th data-options="field:'added_by_drawing_update',width:160,halign:'center',align:'center',sortable:true,formatter:formatValue,hidden:true">材料是否因升版增加</th>
					<th data-options="field:'nesting_draw_no',width:100,halign:'center',sortable:true,hidden:true">套料图纸编号</th>
					<th data-options="field:'nesting_draw_rev',width:100,halign:'center',sortable:true,hidden:true">套料图纸版本</th>
					<th data-options="field:'bulk_material_no',width:100,halign:'center',sortable:true,hidden:true">对应母材编号</th>
					<th data-options="field:'remnant_part_no',width:100,halign:'center',sortable:true,hidden:true">使用余料编号</th>
					<th data-options="field:'material_code',width:100,halign:'center',sortable:true,hidden:true">物料编码</th>
					<th data-options="field:'supplier',width:100,halign:'center',sortable:true,hidden:true">供货方</th>
					<th data-options="field:'mto_no',width:100,halign:'center',sortable:true,hidden:true">料单编号</th>
					<th data-options="field:'mto_row_no',width:100,halign:'center',sortable:true,hidden:true">料单行号</th>
					<th data-options="field:'ident_code',width:100,halign:'center',sortable:true,hidden:true">标签编号</th>
					<th data-options="field:'pay_item_no',width:100,halign:'center',sortable:true,hidden:true">业主付款编号</th>
					<th data-options="field:'bar_code_no',width:80,halign:'center',sortable:true,hidden:true">条形码编号</th>
					<th data-options="field:'vendor',width:100,halign:'center',sortable:true,hidden:true">厂家</th>
					<th data-options="field:'site',width:100,halign:'center',sortable:true,hidden:true">设备厂址</th>
					<th data-options="field:'website',width:100,halign:'center',sortable:true,hidden:true">设备网址</th>
					<th data-options="field:'tellphone_no',width:100,halign:'center',sortable:true,hidden:true">联系电话</th>
					<th data-options="field:'column1',width:100,halign:'center',sortable:true,hidden:true">补充列1</th>
					<th data-options="field:'column2',width:100,halign:'center',sortable:true,hidden:true">补充列2</th>
					<th data-options="field:'column3',width:100,halign:'center',sortable:true,hidden:true">补充列3</th>
					<th data-options="field:'column4',width:100,halign:'center',sortable:true,hidden:true">补充列4</th>
					<th data-options="field:'column5',width:100,halign:'center',sortable:true,hidden:true">补充列5</th>
					<th data-options="field:'column6',width:100,halign:'center',sortable:true,hidden:true">补充列6</th>
					<th data-options="field:'column7',width:100,halign:'center',sortable:true,hidden:true">补充列7</th>
					<th data-options="field:'column8',width:100,halign:'center',sortable:true,hidden:true">补充列8</th>
					<th data-options="field:'column9',width:100,halign:'center',sortable:true,hidden:true">补充列9</th>
					<th data-options="field:'column10',width:100,halign:'center',sortable:true,hidden:true">补充列10</th>
					<th data-options="field:'column11',width:100,halign:'center',sortable:true,hidden:true">补充列11</th>
					<th data-options="field:'column12',width:100,halign:'center',sortable:true,hidden:true">补充列12</th>
					<th data-options="field:'column13',width:100,halign:'center',sortable:true,hidden:true">补充列13</th>
					<th data-options="field:'column14',width:100,halign:'center',sortable:true,hidden:true">补充列14</th>
					<th data-options="field:'column15',width:100,halign:'center',sortable:true,hidden:true">补充列15</th>
					<th data-options="field:'column16',width:100,halign:'center',sortable:true,hidden:true">补充列16</th>
					<th data-options="field:'column17',width:100,halign:'center',sortable:true,hidden:true">补充列17</th>
					<th data-options="field:'column18',width:100,halign:'center',sortable:true,hidden:true">补充列18</th>
					<th data-options="field:'column19',width:100,halign:'center',sortable:true,hidden:true">补充列19</th>
					<th data-options="field:'column20',width:100,halign:'center',sortable:true,hidden:true">补充列20</th>
					<th data-options="field:'quantity_of_inwarehouse',width:100,halign:'center',sortable:true,hidden:true">入库数量</th>
					<th data-options="field:'recipients_of_warehouse',width:100,halign:'center',sortable:true,hidden:true">领用出库数量</th>
					<th data-options="field:'waste_materials_outbound',width:120,halign:'center',sortable:true,hidden:true">废旧物资出库数量</th>
					<th data-options="field:'in_allocating_outbound',width:100,halign:'center',sortable:true,hidden:true">调拨出库数量</th>
					<th data-options="field:'construction_of_withdrawing',width:100,halign:'center',sortable:true,hidden:true">建造退库数量</th>
					<th data-options="field:'stock',width:100,halign:'center',sortable:true,hidden:true">库存</th>
					<th data-options="field:'generate_request_number',width:150,halign:'center',sortable:true,hidden:true">生成领料单数量</th>
				</tr>
			</thead>
		</table>
	</div>
    <script type="text/javascript">
    var currentRow,YN=[{"text":"是","value":"Y"},{"text":"否","value":"N"}],columns={},project="",init=true,isProjectChange=true;
 	$(function(){
 		$("#data_track_change").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
            onClickRow:function(rowIndex, rowData){
            	currentRow = rowData;
            },
            onBeforeLoad:function(){
            	if(init){
            		getColumns();
            	}
            },
            onLoadSuccess:function(data){
            	$(".search_button").linkbutton();
            	currentRow=null;
            	isProjectChange=true;
            	projectChange();
            }
		});
 		$("#data_track_change").datagrid("loadData",{total:0,rows:[]});
 		$('#MySelfWindow').dialog({  
            onClose:function(){
            	if(init){
            		jumpPage('结构数据跟踪表','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackTable');
            	}
            }  
        });
 	});
 	//查询显示列名
 	function getColumns(){
 		var data;
 		$.ajax({
 			type:"post",
 			url:"${ctx}/stDesignDataManager/getColumns.do",
 			contentType:"application/json;charset=utf-8",
 			async:false,
 			data:JSON.stringify({"professional":"professional_st"}),
 			success:function(result){
 				data=result;
 			}
 		})
 		if(!isEmpty(data[0])){
    		var showColumns;
    		showColumns=data[0].show_columns.split(",");
    		for(var i=0;i<showColumns.length;i++){
        		$("#data_track_change").datagrid("showColumn",showColumns[i].substring(4,showColumns[i].length));
       		}
    		$("#data_track_change").datagrid("showColumn","state");
        	$("#data_track_change").datagrid("showColumn","update_date");
        	$("#data_track_change").datagrid("showColumn","reason_for_change");
        	$("#data_track_change").datagrid("showColumn","change_cause_mark_date");
        	$("#data_track_change").datagrid("showColumn","change_cause_mark_user");
        	if("remind" == "${module}"){
        		$("#data_track_change").datagrid("showColumn","state_change_date");
        		$("#data_track_change").datagrid("showColumn","mark_date");
        		$("#data_track_change").datagrid("showColumn","mark_user");
        	}
    	}else{
    		var allColumns = ["state","update_date","mark_date","mark_user","state_change_date","reason_for_change",
    		                  "change_cause_mark_date","","change_cause_mark_user","project_name","job_no","module_name","shop_draw_no",
    		                  "shop_draw_rev","contractor_draw_no","contractor_draw_rev",
    		                  "part_no","part_name","steel_type","structure_type","area_of_part",
    		                  "level_no","work_package_no","part_profile_name","part_profile",
    		                  "grade","qty","quantity_in_dm_mto","quantity_in_pd_mto","net_single_length",
    		                  "net_single_width","unit_weight","net_single_weight","net_total_weight",
    		                  "gross_single_weight","gross_total_weight","net_area","surface_treatment",
    		                  "coating_system","coating_area","fireproof_type","fireproof_area",
    		                  "fireproof_length","fireproof_thickness","wasted_by_drawing_update","added_by_drawing_update",
    		                  "nesting_draw_no","nesting_draw_rev","bulk_material_no","remnant_part_no","material_code",
    		                  "supplier","mto_no","mto_row_no","ident_code","pay_item_no","bar_code_no","vendor","site",
    		                  "website","tellphone_no","column1","column2","column3","column4","column5","column6",
    		                  "column7","column8","column9","column10","column11","column12","column13","column14","column15",
    		                  "column16","column17","column18","column19","column20","quantity_of_inwarehouse","recipients_of_warehouse",
    		                  "waste_materials_outbound","in_allocating_outbound","construction_of_withdrawing","stock","generate_request_number"];
    		for(var i=0;i<allColumns.length;i++){
    			$("#data_track_change").datagrid("showColumn",allColumns[i]);
    		}
    	}
 	}
 	$("#project_name_search").combobox({  
 		onLoadSuccess: function(){
 			$("#project_name_search").combobox("setValue",project);
		},
	    onChange : function(newValue,oldValue){
	    	if(isProjectChange){
	    		projectChange();
			  	removeAll();
	    	}
	    }
	});
 	//根据项目变化修改列名
 	function projectChange(){
 		var jobNo=$("#project_name_search").combobox("getValue");
 		if(isEmpty(jobNo)){
 			for(var i=1;i<=20;i++){
 	    		$("div[class$=column"+i+"] span:first").html("补充列"+i);
 	    	}
 			return;
 		}
 		project=jobNo;
 		//加载数据前查询对应专业的补充列名称
    	getColumnDisplaySetting("professional_st",jobNo);
 		if(null!=columns){
 			for(var i=1;i<=20;i++){
 	    		if(!isEmpty(columns["column"+i])){
 	    			$("div[class$=column"+i+"] span:first").html(columns["column"+i]);
 	    		}
 	    	}
 		}else{
 			for(var i=1;i<=20;i++){
 	    		$("div[class$=column"+i+"] span:first").html("补充列"+i);
 	    	}
 		}
 	}
 	//动态显示列名
 	function addStCondition(){
 		var jobNo=$("#project_name_search").combobox("getValue");
 		if(isEmpty(jobNo)){
 			$.messager.show({
				title:"警告",
				msg:"请先选择项目!"
			});
 			return;
 		}
 		addCondition('StDataTrack','professional_st',jobNo);
 	}
 	//查询对应专业的补充列名称
 	function getColumnDisplaySetting(code,jobNo){
 		$.ajax({
 			type:"post",
 			url:"${ctx}/columnDisplaySet/getColumnsByMajor.do",
 			async:false,
 			contentType : "application/json;charset=utf-8",
 			data:JSON.stringify({"code":code,"jobNo":jobNo}),
 			success:function(result){
 				columns=result;
			}
 		});
 	}
 	//查询按钮
 	function searchDesignDataChange(){
 		var condition={};
 		var now = formatterDate(new Date());
 		var updateDate=$("#update_date_search").datebox("getValue");
 		if(isEmpty(updateDate)){
 			updateDate=now;
 			$("#update_date_search").datebox("setValue",now);
 		}
 		$("#update_date_hide").val(updateDate);
 		if("change"=="${module}"){
 			condition={"is_history":"Y","professional":"professional_st"};
 		}else if("remind"=="${module}"){
 			condition={"is_history":"Y","type":"remind","professional":"professional_st"};
 		}
 		$("#data_track_change").datagrid({
 		   url:"${ctx}/designDataChange/query.do",
 		   queryParams:mosaicActivityParams(mosaicParams(condition))
 		});
 	}
 	//格式化数据（是否因图纸升版增加、作废）
 	function formatValue(key){
 		for(var i=0;i<YN.length;i++){
    		if(YN[i].value==key){
    			return YN[i].text;
    		}
    	}
        return "";
 	}
 	//删除所有动态查询条件
 	function removeAll(){
 		var condition = $(".search_all_div div.dynamic");
 		for(var i=0;i<condition.length;i++){
 			removeCondition();
 		}
 	}
 	window.onload = function() {
 		var updata_date=formatterDate(new Date());
		$("#update_date_search").datebox("setValue",updata_date);
 	};
 	function changeReason(){
 		jumpPage("变更信息图纸汇总", "${ctx}/designDataChange/drawChangeCollect.do?professional=professional_st&module=${module}");
 	}
 	function changeState(){
 		jumpPage("结构数据变更提醒", "${ctx}/designDataChange/drawRemindCollect.do?professional=professional_st&module=${module}");
 	}
 	function stateConfirm(){
 		if(null==currentRow){
 			$.messager.show({
				title:"警告",
				msg:"请选择一条数据!"
			});
 			return;
 		}
 		if(!isEmpty(currentRow.state_confirm_date)){
 			$.messager.show({
				title:"警告",
				msg:"该数据已确认!请选择其它数据!"
			});
 			return;
 		}
 		$.messager.confirm("是否确认","确认后无法更改状态！是否确认更改？",function(r){
			if (r){
				$.ajax({
		 			type:"post",
		 			url:"${ctx}/designDataChange/stateConfirm.do",
		 			async:false,
		 			contentType : "application/json;charset=utf-8",
		 			data:JSON.stringify({"id":currentRow.id}),
		 			success:function(result){
		 				handleReturn(result,function(){
		 					jumpPage("结构数据变更提醒", "${ctx}/login/showList.do?url=business/designDataManager/stDataTrackChange&module=remind");
		 				});
					}
		 		});
			}
		});
 	}
</script>
</body>
</html>
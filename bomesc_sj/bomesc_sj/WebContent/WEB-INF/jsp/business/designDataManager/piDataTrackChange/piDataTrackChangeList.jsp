<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>管线数据跟踪表</title>
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
						<input id="update_date_search" name="update_date" class="easyui-datebox" data-options="width:200,editable:false" dkd-search-element="pit.update_date le text"/>
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
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="changeReason()">变更原因维护</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDesignDataChange()">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="resetSearch()">重置</a>
			</div>
		</div>
	</form>
 <div class="datagrid_div" id="st_data_track_div">
	<table id="pi_data_track_change" align="center" style="width:100%;">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true">ID</th>
				<th data-options="field:'state',width:75,halign:'center',sortable:true">状态</th>
				<th data-options="field:'update_date',width:75,halign:'center',sortable:true">更新日期</th>
				<th data-options="field:'reason_for_change',width:100,halign:'center',sortable:true,hidden:true">变更原因</th>
				<th data-options="field:'change_cause_mark_date',width:120,halign:'center',sortable:true,hidden:true">原因标注日期</th>
				<th data-options="field:'change_cause_mark_user',width:100,halign:'center',sortable:true,hidden:true">原因标注人</th>
				<th data-options="field:'project_name',width:100,halign:'center',sortable:true,hidden:true">项目名称</th>
				<th data-options="field:'job_no',width:100,halign:'center',sortable:true,hidden:true">项目工作号</th>
				<th data-options="field:'module_no',width:100,halign:'center',sortable:true,hidden:true">模块号</th>
				<th data-options="field:'work_package_no',width:100,halign:'center',sortable:true,hidden:true">模块工作包号</th>
				<th data-options="field:'test_package_no',width:100,halign:'center',sortable:true,hidden:true">试压包号</th>
				<th data-options="field:'iso_drawing_no',width:100,halign:'center',sortable:true,hidden:true">ISO图纸编号</th>
				<th data-options="field:'page_no',width:100,halign:'center',sortable:true,hidden:true">页码</th>
				<th data-options="field:'total_page',width:100,halign:'center',sortable:true,hidden:true">总页数</th>
				<th data-options="field:'iso_drawing_rev',width:100,halign:'center',sortable:true,hidden:true">ISO图纸版本</th>
				<th data-options="field:'shop_draw_no',width:80,halign:'center',sortable:true,hidden:true">加设图纸编号</th>
				<th data-options="field:'shop_draw_rev',width:100,halign:'center',sortable:true,hidden:true">加设图纸版本</th>
				<th data-options="field:'system_code',width:100,halign:'center',sortable:true,hidden:true">系统代码</th>
				<th data-options="field:'pipe_class',width:100,halign:'center',sortable:true,hidden:true">管线级别</th>
				<th data-options="field:'line_no',width:100,halign:'center',sortable:true,hidden:true">管线号</th>
				<th data-options="field:'spool_no',width:100,halign:'center',sortable:true,hidden:true">管段号</th>
				<th data-options="field:'part_name',width:100,halign:'center',sortable:true,hidden:true">零件名称</th>
				<th data-options="field:'part_type',width:100,halign:'center',sortable:true,hidden:true">零件类型</th>
				<th data-options="field:'part_no',width:100,halign:'center',sortable:true,hidden:true">零件编号</th>
				<th data-options="field:'client_part_no',width:80,halign:'center',sortable:true,hidden:true">业主零件编号</th>
				<th data-options="field:'ident_code',width:120,halign:'center',sortable:true,hidden:true">识别编号</th>
				<th data-options="field:'material_code',width:120,halign:'center',sortable:true,hidden:true">物料编码</th>
				<th data-options="field:'tag_no',width:100,halign:'center',sortable:true,hidden:true">位号</th>
				<th data-options="field:'part_main_size',width:100,halign:'center',sortable:true,hidden:true">零件主尺寸</th>
				<th data-options="field:'part_vice_size',width:100,halign:'center',sortable:true,hidden:true">零件副尺寸</th>
				<th data-options="field:'pressure_class',width:100,halign:'center',sortable:true,hidden:true">压力级别</th>
				<th data-options="field:'main_thickness_grade',width:100,halign:'center',sortable:true,hidden:true">主壁厚级别</th>
				<th data-options="field:'vice_thickness_grade',width:100,halign:'center',sortable:true,hidden:true">次壁厚级别</th>
				<th data-options="field:'main_connection_type',width:80,halign:'center',sortable:true,hidden:true">主连接方式</th>
				<th data-options="field:'vice_connection_type',width:100,halign:'center',sortable:true,hidden:true">次连接方式</th>
				<th data-options="field:'grade',width:100,halign:'center',sortable:true,hidden:true">材质</th>
				<th data-options="field:'special_requirement',width:100,halign:'center',sortable:true,hidden:true">特殊要求</th>
				<th data-options="field:'welding_no_one',width:100,halign:'center',sortable:true,hidden:true">焊口编号1</th>
				<th data-options="field:'welding_no_two',width:100,halign:'center',sortable:true,hidden:true">焊口编号2</th>
				<th data-options="field:'welding_no_three',width:100,halign:'center',sortable:true,hidden:true">焊口编号3</th>
				<th data-options="field:'welding_no_four',width:100,halign:'center',sortable:true,hidden:true">焊口编号4</th>
				<th data-options="field:'qty',width:100,halign:'center',sortable:true,hidden:true">数量</th>
				<th data-options="field:'quantity_in_dm_mto',width:120,halign:'center',sortable:true,hidden:true">设计料单中的数量</th>
				<th data-options="field:'quantity_in_pd_mto',width:120,halign:'center',sortable:true,hidden:true">采办料单中的数量</th>
				<th data-options="field:'net_length',width:100,halign:'center',sortable:true,hidden:true">净长度</th>
				<th data-options="field:'additional_length',width:100,halign:'center',sortable:true,hidden:true">余量</th>
				<th data-options="field:'final_length',width:100,halign:'center',sortable:true,hidden:true">总长度</th>
				<th data-options="field:'material_requisition_no',width:100,halign:'center',sortable:true,hidden:true">施工领料单编号</th>
				<th data-options="field:'nesting_draw_no',width:100,halign:'center',sortable:true,hidden:true">套料图纸编号</th>
				<th data-options="field:'nesting_draw_rev',width:100,halign:'center',sortable:true,hidden:true">套料图纸版本</th>
				<th data-options="field:'bulk_material_no',width:100,halign:'center',sortable:true,hidden:true">对应母材编号</th>
				<th data-options="field:'remnant_part_no',width:100,halign:'center',sortable:true,hidden:true">余料编号</th>
				<th data-options="field:'remnant_part_size',width:100,halign:'center',sortable:true,hidden:true">余料尺寸</th>
				<th data-options="field:'bar_code_no',width:100,halign:'center',sortable:true,hidden:true">条形码编号</th>
				<th data-options="field:'unit_net_weight',width:80,halign:'center',sortable:true,hidden:true">单位净重</th>
				<th data-options="field:'net_weight',width:100,halign:'center',sortable:true,hidden:true">净重</th>
				<th data-options="field:'surface_treatment',width:100,halign:'center',sortable:true,hidden:true">表面处理</th>
				<th data-options="field:'coating_system',width:100,halign:'center',sortable:true,hidden:true">油漆配套</th>
				<th data-options="field:'unit_net_area',width:100,halign:'center',sortable:true,hidden:true">单位净表面积</th>
				<th data-options="field:'net_area',width:100,halign:'center',sortable:true,hidden:true">净表面积</th>
				<th data-options="field:'insultation_type',width:100,halign:'center',sortable:true,hidden:true">保温类型</th>
				<th data-options="field:'insultation_thk',width:100,halign:'center',sortable:true,hidden:true">保温厚度</th>
				<th data-options="field:'insultation_surface_area',width:100,halign:'center',sortable:true,hidden:true">保温表面积</th>
				<th data-options="field:'insultation_equivalent_length',width:100,halign:'center',sortable:true,hidden:true">保温延米</th>
				<th data-options="field:'supplier',width:100,halign:'center',sortable:true,hidden:true">供货方</th>
				<th data-options="field:'mto_no',width:100,halign:'center',sortable:true,hidden:true">料单编号</th>
				<th data-options="field:'mto_row_no',width:100,halign:'center',sortable:true,hidden:true">料单行号</th>
				<th data-options="field:'recommend_surplus',width:100,halign:'center',sortable:true,hidden:true">采购余量</th>
				<th data-options="field:'wasted_by_drawing_update',width:160,halign:'center',align:'center',sortable:true,formatter:formatValue,hidden:true">材料是否因升版作废</th>
				<th data-options="field:'added_by_drawing_update',width:160,halign:'center',align:'center',sortable:true,formatter:formatValue,hidden:true">材料是否因升版增加</th>
				<th data-options="field:'pay_item_no',width:100,halign:'center',sortable:true,hidden:true">业主付款编号</th>
				<th data-options="field:'remark',width:100,halign:'center',sortable:true,hidden:true">备注</th>
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
		$("#pi_data_track_change").datagrid({ 
			rownumbers:true,
			singleSelect:true,
			pagination:true,
            onClickRow:function(rowIndex, rowData){
            	currentRow = rowData;
            },
            onLoadSuccess:function(data){
            	if(!isEmpty(data.rows[0])){
            		for(var key in data.rows[0]){
                		$("#pi_data_track_change").datagrid("showColumn",key);
               		}
            	}
            	//隐藏ID列
            	$("#pi_data_track_change").datagrid("hideColumn","id");
            	//防止用户不选择零件编号时查看当条数据的历史记录
            	$(".search_button").linkbutton();
            	currentRow=null;
            	if(init){
            		$("#pi_data_track_change").datagrid("reload");
            		init=false;
            	}
            	isProjectChange=true;
            }
		});
 		$("#pi_data_track_change").datagrid("loadData",{total:0,rows:[]});
 		$('#MySelfWindow').dialog({  
            onClose:function(){
            	if(init){
            		jumpPage('管线数据跟踪表','${ctx}/login/showList.do?url=business/designDataManager/piDataTrackTable');
            	}
            }  
        });
	});
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
    	getColumnDisplaySetting("professional_pi",jobNo);
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
 		condition={"is_history":"Y","professional":"professional_pi"};
 		$("#pi_data_track_change").datagrid({
 		   url:"${ctx}/piDesignDataChange/query.do",
 		});
 		$("#pi_data_track_change").datagrid("reload",mosaicActivityParams(mosaicParams()));
//  		projectChange();
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
 	window.onload = function() {
 		var updata_date=formatterDate(new Date());
		$("#update_date_search").datebox("setValue",updata_date);
 	};
 	
 	
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
 		addCondition('PiDataTrack','professional_pi',jobNo);
 	}
	//删除所有动态查询条件
 	function removeAll(){
 		var condition = $(".search_all_div div.dynamic");
 		for(var i=0;i<condition.length;i++){
 			removeCondition();
 		}
 	}
	//变更原因维护
 	function changeReason(){
 		jumpPage("变更信息图纸汇总", "${ctx}/piDesignDataChange/drawChangeCollect.do?professional=professional_pi");
 	}
 </script>				
</body>
</html>
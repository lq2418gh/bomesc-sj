<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>结构数据跟踪表</title>
</head>
<body>
	<div class="datagrid_div" id="st_record_div">
		<h3 align="center">变更明细</h3>
		<table id="st_record" align="center" style="width:100%;">
			<thead>
				<tr>
					<th data-options="field:'change_size',width:75,halign:'center',hidden:true">变更次数</th>
					<th data-options="field:'id',editor:{type:'textbox'},hidden:true">ID</th>
					<th data-options="field:'update_date',width:75,halign:'center',sortable:true,hidden:true">变更日期</th>
					<th data-options="field:'reason_for_change',width:200,halign:'center',sortable:true,hidden:true">变更原因</th>
					<th data-options="field:'change_cause_mark_date',width:120,halign:'center',sortable:true,hidden:true">变更原因标注日期</th>
					<th data-options="field:'change_cause_mark_user',width:120,halign:'center',sortable:true,hidden:true">变更原因标记人</th>
					<th data-options="field:'project_name',width:100,halign:'center',sortable:true,hidden:true">项目名称</th>
					<th data-options="field:'job_no',width:100,halign:'center',sortable:true,hidden:true">项目工作号</th>
					<th data-options="field:'module_name',width:100,halign:'center',sortable:true,hidden:true">模块名称</th>
					<th data-options="field:'shop_draw_no',width:100,halign:'center',sortable:true,hidden:true">加设图纸编号</th>
					<th data-options="field:'shop_draw_rev',width:100,halign:'center',sortable:true,hidden:true">加设图纸版本</th>
					<th data-options="field:'contractor_draw_no',width:100,halign:'center',sortable:true,hidden:true">业主图纸编号</th>
					<th data-options="field:'contractor_draw_rev',width:100,halign:'center',sortable:true,hidden:true">业主图纸版本</th>
					<th data-options="field:'part_no',width:100,halign:'center',sortable:true,hidden:true">零件编号</th>
					<th data-options="field:'part_no_hide',width:100,halign:'center',sortable:true,hidden:true">零件编号(隐藏)</th>
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
					<th data-options="field:'construction_of_withdrawing',width:150,halign:'center',sortable:true,hidden:true">建造退库数量</th>
					<th data-options="field:'stock',width:100,halign:'center',sortable:true,hidden:true">库存</th>
					<th data-options="field:'generate_request_number',width:150,halign:'center',sortable:true,hidden:true">生成领料单数量</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="margin-top:5px;width:100%;text-align:center">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('结构数据跟踪表','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackTable')">返回</a>
	</div>
    <script type="text/javascript">
    YN=[{"text":"是","value":"Y"},{"text":"否","value":"N"}],init=true,columns={};
 	$(function(){
 		$('#st_record').datagrid({
 			rownumbers:true,
 			singleSelect:true,
 			onBeforeLoad:function(){
 				setColumnDisSet();
 			}
 		});
 		//查看历史记录
		$.post("${ctx}/stDesignDataManager/query.do",{"part_no":"${part_no}","id !=":"${id}","sort":"update_date","change_size":"${change_size}","update_date":"${update_date}","order":"desc","is_history":'Y'},function(result){
   			if(!isEmpty(result.rows[0])){
           		for(var key in result.rows[0]){
           			if("quantity_in_dm_mto"==key||"quantity_in_pd_mto"==key||"quantity_of_inwarehouse"==key
           					||"recipients_of_warehouse"==key||"stock"==key||"generate_request_number"==key
           					||"waste_materials_outbound"==key||"in_allocating_outbound"==key||"construction_of_withdrawing"==key){
           				continue
           			}
            		$("#st_record").datagrid("showColumn",key);
           		}
           	}
   			$("#st_record").datagrid("hideColumn","id");
   			$("#st_record").datagrid("hideColumn","change_size");
   			$("#st_record").datagrid("hideColumn","part_no_hide");
	        $("#st_record").datagrid("loadData",{rows:result.rows});
 	   });
 	});
 	//格式化数据（是否因图纸升版增加、作废）
 	function formatValue(key){
 		for(var i=0;i<YN.length;i++){
    		if(YN[i].value==key){
    			return YN[i].text;
    		}
    	}
        return "";
 	}
 	//设置自定义配置列的列名
 	function setColumnDisSet(){
 		//加载数据前查询对应专业的补充列名称
    	getColumnDisplaySetting("professional_st","${jobNo}");
 		if(null!=columns){
 			for(var i=1;i<=20;i++){
 	    		if(!isEmpty(columns["column"+i])){
 	    			$("#st_record_div div[class$=column"+i+"] span:first").html(columns["column"+i]);
 	    		}
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
</script>
</body>
</html>
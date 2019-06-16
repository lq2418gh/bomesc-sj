<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>结构领料单查看页</title>
</head>
<body>
<div id="st_data_picking_view" >
		<table width="100%" class="view_table">
			<tr>
				<td>领料单号:</td>
				<td column="picking_no"></td>
				<td>单据状态:</td>
				<td column="state"></td>
				<td>项目名称:</td>
				<td column="project_name"></td>
			</tr>
			<tr>
				<td>项目工作号:</td>
				<td column="job_no"></td>
				<td>专业名称:</td>
				<td > 结构专业-ST</td>
				<td>录入人:</td>
				<td column="entity_createuser"></td>
			</tr>
			<tr>
				<td>录入日期:</td>
				<td column="entity_createdate"></td>
				<td>修改日期:</td>
				<td column="entity_modifydate"></td>
				<td>修改人:</td>
				<td column="entity_modifyuser"></td>
			</tr>
			<tr>
				<td>供货方:</td>
				<td column="supplier"></td>
				<td>库房:</td>
				<td column="storehouse"></td>
				<td>材料类别:</td>
				<td column="material_category"></td>
			</tr>
			<tr>
				<td>模块名称:</td>
				<td column="module_name"></td>
				<td>发料方:</td>
				<td column="issuing_material"></td>
				<td >材料用途:</td>
				<td column="purpose" ></td>
			</tr>
			<tr>
				<td>材料使用区域描述:</td>
				<td column="materials_used_for" colspan="5"></td>
			</tr>
			<tr>
				<td>领料说明:</td>
				<td column="notes" colspan="5"></td>
			</tr>
			<tr>
				<td colspan="8" style="text-align: center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="toEdit()">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="approve()">审批</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('结构领料单','${ctx}/login/showList.do?url=business/designDataManager/stDataPicking')">返回</a>
				</td>
			</tr>
		</table>
		<div style="padding-left:10px">
		    <table id="st_data_picking_deatil_view" align="center">
				<thead>
					<tr>
<!-- 						<th data-options="field:'material_type',width:100" >材料类型</th> -->
						<th data-options="field:'material_name',width:100">材料名称</th>
						<th data-options="field:'grade',width:100">材质</th>
						<th data-options="field:'unit',width:100">单位</th>
						<th data-options="field:'use_quantity',width:100">使用数量</th>
						<th data-options="field:'mto_no',width:100">料单编号</th>
						<th data-options="field:'remnant_part_no',width:100">使用余料编号</th>
						<th data-options="field:'coating_area',width:100">油漆面积</th>
						<th data-options="field:'material_weight',width:100">材料重量</th>
						<th data-options="field:'use_reason',width:100">使用原因</th>
						<th data-options="field:'section_circumference',width:100">截面周长</th>
						<th data-options="field:'unit_weight',width:100">单位重量</th>
					</tr>
				</thead>
			</table>
	    </div>
	</div>
	<script type="text/javascript">
		var dataview;
		$(function(){
			$.post("${ctx}/stDataPicking/view.do",{"id":"${id}"},function(result){
				dataview = result;
		        loadData(dataview);
		        loadDetailListView(dataview.stDataPickingDetail);
	 	   });
		})
			
		function loadDetailListView(stDataPickingDetail){
			$("#st_data_picking_deatil_view").datagrid({
				rownumbers:true,
				data:stDataPickingDetail
			});
		}
		//编辑 跳转方法
		function toEdit(){
			jumpPage("结果领料单编辑页", "${ctx}/stDataPicking/toEdit.do?id="+"${id}");
		}
		
		//审批
		function approve(){
			var picking_no=$("td[column='picking_no']").text()
			checkBill(picking_no);
		}
	</script>
</body>
</html>
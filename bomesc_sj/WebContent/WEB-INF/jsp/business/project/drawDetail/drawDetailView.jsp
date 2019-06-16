<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>图纸明细管理</title>
</head>
<body>
	<!--查看页面 -->
	<table width="100%" class="view_table">
		<tr>
			<th>单据编码:</th>
			<td column="list_no"></td>
			<th>项目名称:</th>
			<td column="project_name"></td>
			<th>项目工作号:</th>
			<td column="job_no"></td>
			<th>专业名称:</th>
			<td column="major_name"></td>
		</tr>
		<tr>
			<th>统计月份:</th>
			<td column="statistical_month"></td>
			<th>录入人:</th>
			<td column="entity_createuser"></td>
			<th>录入日期:</th>
			<td column="entity_createdate"></td>
			<th>修改日期:</th>
			<td column="entity_modifydate"></td>
		</tr>
		<tr>
			<th>修改人:</th>
			<td column="entity_modifyuser"></td>
		</tr>
		<tr>
			<td colspan="8" style="text-align: center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="editDrawDetail()">修改</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('图纸管理','${ctx}/login/showList.do?url=business/project/drawDetail')">返回</a>
			</td>
		</tr>
	</table>
	<div style="padding-left:10px">
		<div class="titleMessage">图纸数量信息-Quantity information of drawing</div>
	    <table id="draw_detail">
			<thead>
				<tr>
					<th data-options="field:'draw_type_name',width:500" rowspan="2">图纸类型</th>
					<th data-options="field:'total_draw_quantity',width:300" rowspan="2">图纸总数量</th>
					<th data-options="width:800" colspan="3">上月完成图纸数量</th>
					<th data-options="width:800" colspan="3">当月完成图纸数量</th>
					<th data-options="width:800" colspan="3">当前完成图纸数量</th>
				</tr>
				<tr>
					<th data-options="field:'pre_draw_forecast',width:300">计划完成数量</th>
					<th data-options="field:'pre_draw_actual',width:300">实际完成数量</th>
					<th data-options="field:'pre_discrepancy',width:300">偏差值(%)</th>
					<th data-options="field:'this_draw_forecast',width:300">计划完成数量</th>
					<th data-options="field:'this_draw_actual',width:300">实际完成数量</th>
					<th data-options="field:'this_discrepancy',width:300">偏差值(%)</th>
					<th data-options="field:'cumulative_draw_forecast',width:300">计划完成数量</th>
					<th data-options="field:'cumulative_draw_actual',width:300">实际完成数量</th>
					<th data-options="field:'cumulative_discrepancy',width:300">偏差值(%)</th>
				</tr>
			</thead>
		</table>
    </div>
    <script type="text/javascript">
    var data;
 	$(function(){
 		$("#draw_detail").datagrid({
			rownumbers:true,
            fitColumns:true,
            onLoadSuccess:function(data){
            	$(".search_button").linkbutton();
            }
		});
		$.post("${ctx}/drawDetail/findByNo.do",{"id":"${id}"},function(result){
			loadData(result);
			data = result;
	        $("#draw_detail").datagrid("loadData",{total:data.drawDetailHeads.length,rows:data.drawDetailHeads});
 	   });
 	});
 	function editDrawDetail(){
 		jumpPage("图纸明细信息编辑", "${ctx}/drawDetail/edit.do?id="+data.id);
 	}
</script>
</body>
</html>
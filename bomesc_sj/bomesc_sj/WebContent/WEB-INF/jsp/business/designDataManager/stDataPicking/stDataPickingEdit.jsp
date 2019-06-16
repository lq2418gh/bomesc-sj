<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>结构领料单</title>
</head>
<body>
		<div class="search_div" >
			<form id="st_data_picking_from"  method="post" action="${ctx}/stDataPicking/save.do" class="search_form dkd-search">
			<div class="search_item">
				<label class="w80">领料单号:</label>
				<span class="ml10">
					<input id="picking_no_edit" name="picking_no"  class="easyui-textbox w200" />
				</span>
			</div>
			<div class="search_item">
				<label class="w80">项目名称:</label>
				<span class="ml10">
					<input id="project_name_edit" name="project_name" class="easyui-textbox w200" readonly="readonly"/>
					<input type="hidden" id="project_edit" name="project" value="" />
				</span>
			</div>
			<div class="search_item">
				<label class="w80">项目工作号:</label>
				<span class="ml10">
					<input id="job_no_edit" name="job_no" class="easyui-textbox w200"  readonly="readonly" >
				</span>
			</div>
			<div class="search_item">
				<label class="w80">材料类别:</label>
				<span class="ml10">
					<input id="material_category_edit" name="material_category" class="easyui-textbox w200" />
				</span>
			</div>
			<div class="search_item">
				<label class="w80">专业名称:</label>
				<span class="ml10">
					<input  value="结构专业-ST" class="easyui-textbox w200" readonly="readonly"/>
				</span>
			</div>
			<div class="search_item">
				<label class="w80">供&nbsp;&nbsp;&nbsp;货&nbsp;&nbsp;&nbsp;&nbsp;方:</label>
				<span class="ml10">
					<select id="supplier_edit" class="easyui-combobox w200"  name="supplier" data-options="editable:false,panelHeight:'auto'">  
						<option value="">-请选择-</option>  
						<option value="Free Issue">Free Issue</option>  
						<option value="BOMESC">BOMESC</option>  
						<option value="BOMESC">BOMESC,Free Issue</option>  
					</select> 
				</span>
			</div>
			<div class="search_item">
				<label class="w80">模块名称:</label>
				<span class="ml10">
					<input id="module_name_edit" name="module_name"  class="easyui-textbox  w200" />
				</span>
			</div>
			<div class="search_item">
				<label class="w80">编制时间:</label>
				<span class="ml10">
					<input id="compilation_time_edit" name="compilation_time" data-options="editable:false" class="easyui-datebox w200" />
				</span>
			</div>
			<div class="search_item">
				<label class="w80">发&nbsp;&nbsp;&nbsp;料&nbsp;&nbsp;&nbsp;&nbsp;方:</label>
				<span class="ml10">
					<input id="issuing_material_edit" name="issuing_material" class="easyui-textbox w200" />
				</span>
			</div>
			<div class="search_item">
				<label class="w80">材料用途:</label>
				<span class="ml10">
					<input id="purpose_edit" name="purpose" class="easyui-textbox w200" />
				</span>
			</div>
			<div class="search_item">
				<label class="w80">单据状态:</label>
				<span class="ml10">
					<input id="state" name="state" class="easyui-textbox w200" readonly="readonly"/>
				</span>
			</div>
			<div class="search_item">
				<label class="w80">库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;房:</label>
				<span class="ml10">
					<input id="storehouse_edit" name="storehouse" class="easyui-textbox w200" />
				</span>
			</div>
			<div class="search_item">
				<label class="w80">材料使用区域描述:</label>
				<span class="ml10">
					<textarea id="materials_used_for_edit"  name="materials_used_for" style="height:50px;width:600px" class="easyui-textbox"></textarea>
				</span>
			</div>
			<div class="search_item">
				<label>领&nbsp;&nbsp;&nbsp;&nbsp;料&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;说&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;明:</label>
				<span class="ml10">
					<textarea id="notes_edit"  name="notes" style="height:50px;width:600px" class="easyui-textbox"></textarea>
				</span>
			</div>
			</form>
			<div class="search_item" style="margin-top:50px;width:100%;text-align:center;float:left">
				<a href="javascript:save()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('结构领料单','${ctx}/login/showList.do?url=business/designDataManager/stDataPicking')">返回</a>
			</div>
			<div  class="search_item" style="width:100%;height:30%;overflow-y:auto;" >
				<table id="table_pick_data"  title="" >
					<thead>
						<tr>
						    <th data-options="field:'material_name',width:100,halign:'center'">材料名称</th>
<!-- 							<th data-options="field:'species_name',width:100,halign:'center'">材料类型</th> -->
							<th data-options="field:'grade',width:100,halign:'center'">材质</th>
							<th data-options="field:'unit',width:100,halign:'center'">单位</th>
							<th data-options="field:'use_quantity',width:100,halign:'center',editor:{type:'numberbox',options: {precision:0,required:true}}">使用数量</th>
							<th data-options="field:'mto_no',width:100,halign:'center'">料单编号</th>
							<th data-options="field:'remnant_part_no',width:100,halign:'center'">使用余料编号</th>
							<th data-options="field:'coating_area',width:100,halign:'center'">油漆面积</th>
							<th data-options="field:'material_weight',width:100,halign:'center'">材料重量</th>
							<th data-options="field:'use_reason',width:100,halign:'center',editor:{type:'textbox',options: {required:true}}">使用原因</th>
							<th data-options="field:'section_circumference',width:100,halign:'center'">截面周长</th>
							<th data-options="field:'unit_weight',width:100,halign:'center'">单位重量</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	
<script type="text/javascript">
	var detailData={},dataHead={};
	$(function(){
		if("${id}"!="undefined"&&"${id}"!=""){
			$.ajax({
				url:"${ctx}/stDataPicking/edit.do",
				data:{"id":"${id}"},
				dataType:"json",
				async: false,
				success:function(result){
					dataHead=result;
					$("#st_data_picking_from").form("load",dataHead);
					loadDataDetail(dataHead.stDataPickingDetail);
				}
			});
		}else if("${ids}"!="undefined"&&"${ids}"!=""){
			$.ajax({
				url:"${ctx}/stDataPicking/editBySupp.do",
				data:{"ids":"${ids}"},
				dataType:"json",
				async: false,
				success:function(result){
					detailData=result;
					var project_name=result[0].project_name;
					var project = result[0].project;
					var job_no =  result[0].job_no;
					var module_name=result[0].module_name;
					
					dataHead.project_name=project_name;
					dataHead.job_no=job_no;
					dataHead.project=project;
					dataHead.module_name=module_name;
					
					$("#st_data_picking_from").form("load",dataHead);
					loadDataDetail(detailData);
				}
				
			});
		}
		
	});
	function loadDataDetail(detailData){
		 $('#table_pick_data').datagrid({
			rownumbers:true,
			singleSelect:true,
	        data:detailData,
	        onClickRow:onClickRow
	    });
	}
	
	
   var editIndex = undefined;
   function onClickRow(index,rowData){
      if(endEditing()){
    	  $('#table_pick_data').datagrid('selectRow', index).datagrid('beginEdit', index);  
           editIndex = index;//给editIndex对象赋值，index为当前行的索引
      }else {
    	   console.log(rowData)
    	    $('#table_pick_data').datagrid('selectRow', editIndex);  
      }
   } 
	
   function endEditing() {
      if(editIndex == undefined) {
    	  return true;
      }//如果为undefined的话，为真，说明可以编辑
      if($('#table_pick_data').datagrid('validateRow',editIndex)) {
           $('#table_pick_data').datagrid('endEdit',editIndex);
           editIndex = undefined; 
           return true;//重置编辑行索引对象，返回真，允许编辑
      }else{
     	   return false;
      }//否则，为假，返回假，不允许编辑
   }

	
	
	
	function save(){
		$('#table_pick_data').datagrid('endEdit',editIndex);
		var rows=$('#table_pick_data').datagrid('getRows'); 
		if(rows){
			for(var i = 0;i < rows.length;i++){
				if(isEmpty(rows[i].use_quantity)){
					alert("第"+(i+1)+"使用数量不能为空");
					return false;
				}
				if(isEmpty(rows[i].use_reason)){
					alert("第"+(i+1)+"使用原因不能为空");
					return false;
				}
				delete rows[i].job_no;
				delete rows[i].module_name;
				delete rows[i].project_name;
			}
		}
		var details = getDataBase("st_data_picking_from",dataHead,"stDataPickingDetail",rows);
		$.ajax({
 			url:"${ctx}/stDataPicking/save.do",
 			contentType:"application/json;charset=utf-8",
 			data:JSON.stringify(details),
 			success:function(result){
 				var str=result.msg;
 				jumpPage("结构领料单查看页", "${ctx}/stDataPicking/toView.do?id="+str);
			}
 		});
	}

</script>
</body>
</html>
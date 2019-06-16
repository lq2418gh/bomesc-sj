<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>结构数据领料单</title>
</head>
<body>
<form class="search_form dkd-search">
		<div class="search_div">
			<div class="search_all_div">
				<div class="search_item" ignore>
					<label class="w80">项目名称:</label>
					<span class="ml10">
						<input id="project_name_search" name="job_no" class="edit_combobox" data-options="width:200,url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">领料单号:</label>
					<span class="ml10">
						<input id="picking_no_search" name="picking_no" size="13" class="easyui-textbox w200" dkd-search-element="picking_no like text" >
					</span>
				</div>
				<div class="search_item">
					<label >项目工作号:</label>
					<span class="ml01">
						<input id="job_no_search" type="text" class="easyui-textbox w200" dkd-search-element="job_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">材料类别:</label>
					<span class="ml10">
						<input id="material_category_search" name="material_category" size="13" class="easyui-textbox w200" dkd-search-element="material_category like text" >
					</span>
				</div>
				<div class="search_item">
					<label class="w80">模块名称:</label>
					<span class="ml10">
						<input id="module_name_search" name="module_name" size="13" class="easyui-textbox w200" dkd-search-element="module_name like text" >
					</span>
				</div>
				<div class="search_item">
					<label class="w80">材料类别:</label>
					<span class="ml10">
						<input id="storehouse_search" name="storehouse" size="13" class="easyui-textbox w200" dkd-search-element="storehouse like text" >
					</span>
				</div>
				<div class="search_item">
					<label class="w80">编制时间:</label>
					<span class="ml10">
						<input id="compilation_time_search" type="text" class="easyui-datebox w100"  data-options="editable:false" dkd-search-element="compilation_time >= text"/>
					</span>
					<label class="w110">至</label>
					<span class="ml10">
						<input id="compilation_time_search" type="text" class="easyui-datebox w100"  data-options="editable:false" dkd-search-element="compilation_time <= text"/>
					</span>
				</div>
			</div>
			<div style="margin-top:5px;text-align:center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteData()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchstSupp()">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="resetSearch()">重置</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="toEdit()">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchHistory()">查看历史记录</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" dkd-auth="AUTH_PUR_ORDER_SUBMIT" iconCls="icon-redo" onclick="submitData()">提交</a>
			</div>
		</div>
</form>
<div class="datagrid_div" >
	<table id="st_data_picking_list" align="center" >
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true">ID</th>
<!-- 				<th data-options="field:'_operate',halign:'center',width:80,formatter:formatSalOrderOper">操作</th> -->
				<th data-options="field:'state',width:100,halign:'center'">单据状态</th>
				<th data-options="field:'project_name',width:100,halign:'center'">项目名称</th>
				<th data-options="field:'job_no',width:100,halign:'center'">项目工作号</th>
<!-- 				<th data-options="field:'',width:100,halign:'center'" >专业名称</th> -->
				<th data-options="field:'compilation_time',width:100,halign:'center'" >编制时间</th>
				<th data-options="field:'supplier',width:100,halign:'center'">供货方</th>
				<th data-options="field:'storehouse',width:100,halign:'center'">库房</th>
				<th data-options="field:'material_category',width:100,halign:'center'">材料类别</th>
				<th data-options="field:'module_name',width:100,halign:'center'">模块名称</th>
				<th data-options="field:'picking_no',width:100,halign:'center'">领料单号</th>
				<th data-options="field:'issuing_material',width:100,halign:'center'">发料方</th>
				<th data-options="field:'purpose',width:150,halign:'center'">材料用途</th>
				<th data-options="field:'materials_used_for',width:300,halign:'center'">材料使用区域描述</th>
				<th data-options="field:'notes',width:300,halign:'center'">领料说明</th>
			</tr>
		</thead>
	</table>
</div>
<script type="text/javascript">
	var currentRow,init = false,data={};
	$(function(){
		$('#st_data_picking_list').datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
	        url:"${ctx}/stDataPicking/query.do",
 			onClickRow:function(rowIndex, rowData){
	        	currentRow = rowData;
	        },
	        onDblClickRow:function(index,data){
	        	findView(data.id);
	        },
	        onLoadSuccess:function(data){
	        	currentRow="";
	        }
 		});
	});
	
	//双击
	function findView(id){
		jumpPage("结构领料单查看页", "${ctx}/stDataPicking/toView.do?id="+id);
	}
	//编辑 跳转方法
	function toEdit(){
		if(isEmpty(currentRow)){
			$.messager.show({
					title:"警告",
					msg:"请选择一行数据"
				});
	 			return;
		}else{
			var id=currentRow.id;
			jumpPage("结果领料单编辑页", "${ctx}/stDataPicking/toEdit.do?id="+id);
		}
	}
	
	//查询
	function searchstSupp(){
		$("#st_data_picking_list").datagrid("reload",mosaicParams({"sort":"compilation_time","order":"desc"}));
	}
	//按钮--删除
	function deleteData(){
		if(isEmpty(currentRow)){
 			$.messager.show({
				title:"警告",
				msg:"请选择一行数据"
			});
 			return false;
	 	}else{
	 		var state=currentRow.state;
	 		if(state=='提交'){
	 			$.messager.show({
					title:"警告",
					msg:"该条数据是审批状不能删除"
				});	
	 		}else{
				$.messager.confirm("是否删除","准备执行数据删除操作，请确认或取消。",function(r){
					if (r){
						$.post("${ctx}/stDataPicking/delete.do",{"id":currentRow.id},function(result){
							handleReturn(result,function(){
								$("#st_data_picking_list").datagrid("reload");
							});
						});
					}
				});
	 		}
		}
	}
// 	function formatSalOrderOper(val,row,index){
//  		if(row.state == "修改"){
//  			return "<a class=\"editButton m5\" dkd-auth=\"AUTH_PUR_ORDER_SUBMIT\" href=\"javascript:void(0)\" iconCls=\"icon-redo\" onclick=\"submitSaleOrder('" + row.picking_no + "')\">提交</a>";
//  		}else{
//  			return "";
//  		}
//  	}
	
// 	function submitSaleOrder(picking_no){
//  		$.post("${ctx}/stDataPicking/submit.do",{"pickingNo":picking_no},function(result){
//  			handleReturn(result,function(){
//  				$("#st_data_picking_list").datagrid("reload");
//  			});
//  		});
//  	}
	//查看历史纪录
	function searchHistory(){
		if(isEmpty(currentRow)){
 			$.messager.show({
				title:"警告",
				msg:"请选择一行数据"
			});
 			return false;
	 	}else{
	 		var picking_no=currentRow.picking_no;
	 		if(!isEmpty(picking_no)){
	 			loadCheckHistory(picking_no);
	 		}
	 	}
	}
	//提交
	function submitData(){
		if(isEmpty(currentRow)){
 			$.messager.show({
				title:"警告",
				msg:"请选择一行数据"
			});
 			return false;
	 	}else{
	 		var state=currentRow.state;
	 		if(state=="提交"){
	 			$.messager.show({
					title:"警告",
					msg:"该单据已提交"
				});
	 			return false;
	 		}else{
	 			var picking_no=currentRow.picking_no;
	 			var job_no=currentRow.job_no;
	 			$.post("${ctx}/stDataPicking/submit.do",{"pickingNo":picking_no,"job_no":job_no},function(result){
	 	 			handleReturn(result,function(){
	 	 				$("#st_data_picking_list").datagrid("reload");
	 	 			});
	 	 		});
	 		}
	 	}
	}
</script>
</body>
</html>
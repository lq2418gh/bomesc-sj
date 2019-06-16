<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单管理</title>
<style type="text/css">
	label {
		min-width: 160px;
		float: left;
		display: block;
   	}
</style>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div>
				<div class="search_item">
					<label >项目名称：</label>
					<span class="ml10">
						<input id="project_name_search" name="project_name_search" class="edit_combobox" ignore data-options="width:90,url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">项目工作号：</label>
					<span class="ml10">
						<input id="job_no_search" type="text" class="easyui-textbox w120" dkd-search-element="job_no like text"/>
					</span>
				</div>
			</div>
			<div class="search_button" style="margin-top:5px;width:100%;text-align:center">
				<a href="javascript:searchProject()"  class="easyui-linkbutton" iconCls="icon-search">查询</a>
				<a href="javascript:resetSearch()"  class="easyui-linkbutton" iconCls="icon-undo">重置</a>
				<a href="javascript:newProject()"  class="easyui-linkbutton" iconCls="icon-add">新增</a>
				<a href="javascript:deleteProject()"  class="easyui-linkbutton" iconCls="icon-cancel">删除</a>
			</div>
		</div>
	</form>
	<div class="datagrid_div">
		<table id="project_data" align="center">
			<thead >
				<tr>
					<th rowspan="2" data-options="field:'project_name',width:180">项目名称</th>
					<th rowspan="2" data-options="field:'job_no',width:180">项目工作号</th>
					<th colspan="5" data-options="width:180,align:'center'">图纸数量信息</th>
					<th colspan="5" data-options="width:180,align:'center'">额定工时</th>
				</tr>
				<tr>
					<th data-options="field:'draw_quantity_st',width:150">结构专业</th>
					<th data-options="field:'draw_quantity_pi',width:150">机管专业</th>
					<th data-options="field:'draw_quantity_ei',width:150">电仪专业</th>
					<th data-options="field:'draw_quantity_hvac',width:150">空调专业</th>
					<th data-options="field:'draw_quantity_ar',width:150">舾装专业</th>
					<th data-options="field:'rate_man_hour_st',width:150">结构专业</th>
					<th data-options="field:'rate_man_hour_pi',width:150">机管专业</th>
					<th data-options="field:'rate_man_hour_ei',width:150">电仪专业</th>
					<th data-options="field:'rate_man_hour_hvac',width:150">空调专业</th>
					<th data-options="field:'rate_man_hour_ar',width:150">舾装专业</th>
				</tr>
			</thead>
		</table>
	</div>
<script type="text/javascript">
	var currentRow;
 	$(function(){
 		$("#project_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
            url:"${ctx}/project/query.do",
            onDblClickRow:function(index,data){
            	jumpPage("项目查看","${ctx}/project/view.do?id=" + data.id);
            },
            onLoadSuccess:function(){
            	loadSuccess;
            	currentRow="";
            },
            onClickRow:function(rowIndex, rowData){
            	currentRow=rowData;
            }
		});
 	});
 	function searchProject(){
 		$("#project_data").datagrid("reload",mosaicParams());
 	}
 	function deleteProject(){
 		if(!currentRow){
 			$.messager.show({
				title:"警告",
				msg:"请选择要删除的项目！"
			});
 			return;
 		}
		$.messager.confirm("是否删除","准备执行数据删除操作，请确认或取消。",function(r){
		    if (r){
		    	$.post("${ctx}/project/deleteProject.do",{"id":currentRow.id},function(result){
		    		handleReturn(result,function(){
		    			$("#project_data").datagrid("reload");
 	 				});
		 		});
		    }
		});
 	}
 	function newProject(){
 		jumpPage("新建项目","${ctx}/project/edit.do");
 	}
</script>
</body>
</html>
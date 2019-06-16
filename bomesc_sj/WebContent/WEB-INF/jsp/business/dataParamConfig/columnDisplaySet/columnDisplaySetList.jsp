<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>补充列显示设置查询页面</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div>
				<div class="search_item">
					<label class="w80">项目名称</label>
					<span class="ml10">
						<input id="project_name_search" name="project_name" class="edit_combobox" data-options="width:250,url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">项目工作号</label>
					<span class="ml10">
						<input id="job_no_search" class="easyui-textbox w250" dkd-search-element="job_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">专业名称</label>
					<span class="ml10">
						<input id="major_search" name="major" class="easyui-combobox w250" data-options="panelHeight:'auto',editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'" dkd-search-element="major = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">&nbsp;&nbsp;&nbsp;录入人</label>
					<span class="ml10">
						<input id="entity_createuser_search" name="entity_createuser" class="easyui-textbox w250" data-options="panelHeight:'auto'" dkd-search-element="entity_createuser like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">&nbsp;&nbsp;&nbsp;录入日期</label>
					<span class="ml10">
						<input id="entity_createdate_search" class="easyui-datebox w100" dkd-search-element="entity_createdate >= text" data-options="editable:false">
					</span>
					<label class="w80">至</label>
					<span class="ml10">
						<input id="entity_createdate_search" class="easyui-datebox w100" dkd-search-element="entity_createdate <= text" data-options="editable:false">
					</span>
				</div>
			</div>
			<div class="search_button" style="margin-top:5px;width:100%;text-align:center">
				<a href="javascript:search_data()"  class="easyui-linkbutton" iconCls="icon-search">查询</a>
				<a href="javascript:resetSearch()"  class="easyui-linkbutton" iconCls="icon-undo">重置</a>
				<a href="javascript:add_data()"  class="easyui-linkbutton" iconCls="icon-add">新增</a>
				<a href="javascript:update_data()"  class="easyui-linkbutton" iconCls="icon-edit">修改</a>
				<a href="javascript:delete_data()"  class="easyui-linkbutton" iconCls="icon-remove">删除</a>
			</div>
		</div>
	</form>
	<div class="datagrid_div">
		<table id="table_data" align="center">
			<thead >
				<tr>
					<th data-options="field:'project_name',width:180,align:'center'">项目名称</th>
					<th data-options="field:'job_no',width:180,align:'center'">项目工作号</th>
					<th data-options="field:'major_name',width:180,align:'center'">专业名称</th>
					<th data-options="field:'column1',width:180,align:'center'">补充列1</th>
					<th data-options="field:'column2',width:180,align:'center'">补充列2</th>
					<th data-options="field:'column3',width:180,align:'center'">补充列3</th>
					<th data-options="field:'column4',width:180,align:'center'">补充列4</th>
					<th data-options="field:'column5',width:180,align:'center'">补充列5</th>
					<th data-options="field:'column6',width:180,align:'center'">补充列6</th>
					<th data-options="field:'column7',width:180,align:'center'">补充列7</th>
					<th data-options="field:'column8',width:180,align:'center'">补充列8</th>
					<th data-options="field:'column9',width:180,align:'center'">补充列9</th>
					<th data-options="field:'column10',width:180,align:'center'">补充列10</th>
					<th data-options="field:'column11',width:180,align:'center'">补充列11</th>
					<th data-options="field:'column12',width:180,align:'center'">补充列12</th>
					<th data-options="field:'column13',width:180,align:'center'">补充列13</th>
					<th data-options="field:'column14',width:180,align:'center'">补充列14</th>
					<th data-options="field:'column15',width:180,align:'center'">补充列15</th>
					<th data-options="field:'column16',width:180,align:'center'">补充列16</th>
					<th data-options="field:'column17',width:150,align:'center'">补充列17</th>
					<th data-options="field:'column18',width:150,align:'center'">补充列18</th>
					<th data-options="field:'column19',width:150,align:'center'">补充列19</th>
					<th data-options="field:'column20',width:150,align:'center'">补充列20</th>
					<th data-options="field:'entity_createdate',width:150,align:'center'">录入日期</th>
					<th data-options="field:'entity_createuser',width:150,align:'center'">录入人</th>
				</tr>
			</thead>
		</table>
	</div>
<script type="text/javascript">
	var currentId;
	var currentState;
 	$(function(){
 		$("#table_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
            url:"${ctx}/columnDisplaySet/query.do",
            onDblClickRow:function(rowIndex,rowData){
            	showSelfWindow({
            		closed:false,
          			title:"查看页",
          			href:"${ctx}/columnDisplaySet/view.do?id=" + rowData.id,
          			width:'60%',
          			height:400
          		});
            },
            onLoadSuccess:function(){
            	loadSuccess;
            	currentId=null;
            },
            onClickRow:function(rowIndex, rowData){
            	currentId = rowData.id;
            	currentState=rowData.state;
            }
		});
 	});
 	function search_data(){
 		$("#table_data").datagrid("reload",mosaicParams());
 	}
 	function delete_data(){
 		if(!currentId){
 			$.messager.alert("操作提示","请选择一行进行删除！","warning");
 			return false;
 		}
 		$.messager.confirm("是否删除","准备执行数据删除操作，请确认或取消。",function(r){
			if (r){
				$.post("${ctx}/columnDisplaySet/delete.do",{"id":currentId},function(result){
					handleReturn(result,function(){
						jumpPage("补充列显示设置查询页面","${ctx}/login/showList.do?url=business/dataParamConfig/columnDisplaySet");
					});
				});
			}
		});
 	}
 	function add_data(){
 		showSelfWindow({
 			closed:false,
  			title:"编辑页",
  			href:"${ctx}/columnDisplaySet/edit.do",
  			width:'70%',
  			height:500
  		});
 	}
 	
 	function update_data(){
 		if(!currentId){
 			$.messager.alert("操作提示","请选择一行进行修改！","warning");
 			return false;
 		}else{
 			showSelfWindow({
 				closed:false,
 	  			title:"编辑页",
 	  			href:"${ctx}/columnDisplaySet/edit.do?id="+currentId,
 	  			width:'70%',
 	  			height:500
 	  		});
 		}
 	}
 	
	function reset_data(){
		
	}

</script>
</body>
</html>
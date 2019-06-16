<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>数据负责人管理</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div>
				<div class="search_item">
					<label class="w80">单据编码</label>
					<span class="ml10">
						<input id="manager_no_search" type="text" class="easyui-textbox w220" dkd-search-element="manager_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">项目名称</label>
					<span class="ml10">
						<input id="project_name_search" name="job_no" class="edit_combobox" data-options="width:220,url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">项目工作号</label>
					<span class="ml10">
						<input id="job_no_search" type="text" class="easyui-textbox w220" dkd-search-element="job_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">录入人</label>
					<span class="ml23">
						<input id="entity_createuser_search" type="text" class="easyui-textbox w220" dkd-search-element="mh.entity_createuser like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">录入日期</label>
					<span class="ml10">
						<input id="entity_createdate_search" type="text" class="easyui-datebox w80" data-options="editable:false" dkd-search-element="mh.entity_createdate >= text"/>
					</span>
					<label class="w80 ml10">至</label>
					<span class="ml20">
						<input id="entity_createdate_search" type="text" class="easyui-datebox w90" data-options="editable:false" dkd-search-element="mh.entity_createdate <= text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">邮箱</label>
					<span class="ml50">
						<input id="email_search" type="text" class="easyui-textbox w220" dkd-search-element="email like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">专业名称</label>
					<span class="ml10">
						<input id="major_search" name="major" size="13" class="easyui-combobox" data-options="width:220,panelHeight:'auto',editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'" dkd-search-element="major = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">数据负责人</label>
					<span>
						<input id="manager_user_search" type="text" class="easyui-textbox w220" dkd-search-element="manager_user_name like text"/>
					</span>
				</div>
				<div align="center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDataManager()">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="addDataManager('edit')">修改</a>
					<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeDataManager()">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDataManager()">查询</a>
				</div>
			</div>
		</div>
	</form>
	<div class="datagrid_div">
		<table id="data_manager_data" align="center" style="width:100%">
			<thead>
				<tr>
					<th data-options="field:'manager_no',width:100,halign:'center',sortable:true" rowspan="2">单据编码</th>
					<th data-options="field:'project_name',width:100,halign:'center',sortable:true" rowspan="2">项目名称</th>
					<th data-options="field:'job_no',width:100,halign:'center',sortable:true" rowspan="2">项目工作号</th>
					<th data-options="field:'major_name',width:100,halign:'center',sortable:true" rowspan="2">专业名称</th>
					<th data-options="width:500,halign:'center'" colspan="6">邮箱提示配置</th>
					<th data-options="field:'entity_createuser',width:100,halign:'center',sortable:true" rowspan="2">录入人</th>
					<th data-options="field:'entity_createdate',width:120,halign:'center',sortable:true" rowspan="2">录入日期</th>
				</tr>
				<tr>
					<th data-options="field:'job_number',width:100,halign:'center',sortable:true" >人员编码</th>
					<th data-options="field:'manager_user_name',width:100,halign:'center',sortable:true" >姓名</th>
					<th data-options="field:'department',width:100,halign:'center',sortable:true" >部门</th>
					<th data-options="field:'email',width:100,halign:'center',sortable:true" >邮箱</th>
					<th data-options="field:'export_fail',width:100,halign:'center',sortable:true,formatter:getValueByKey" >导入失败提示</th>
					<th data-options="field:'lack_material',width:100,halign:'center',sortable:true,formatter:getValueByKey" >缺料提示</th>
				</tr>
			</thead>
		</table>
	</div>
    <script type="text/javascript">
    var currentRow,YN=[{"text":"是","value":"Y"},{"text":"否","value":"N"}];
 	$(function(){
 		$("#data_manager_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
            url:"${ctx}/dataManager/query.do",
            queryParams: {          
                "sort":"mh.entity_createdate",
                "order":"desc"
            },
            onClickRow:function(rowIndex, rowData){
            	currentRow = rowData;
            },
            onDblClickRow:function(index,data){
            	findData(data.id);
            },
            onLoadSuccess:function(data){
            	currentRow=null;
            }
		});
 	});
 	function addDataManager(no){
 		if(null==currentRow&&!isEmpty(no)){
 			$.messager.show({
				title:"警告",
				msg:"请选择要修改的数据负责人信息！"
			});
 			return;
 		}
 		if(isEmpty(no)){
 			jumpPage("数据负责人信息添加", "${ctx}/dataManager/edit.do");
 		}else{
 			jumpPage("数据负责人信息编辑", "${ctx}/dataManager/edit.do?id="+currentRow.id);
 		}
 	}
 	function searchDataManager(){
 		var items = $("#project_name_search").combobox("getData");
 		var itemStr = ",";
		for(var i = 0;i < items.length;i++){
			itemStr += items[i].value + ",";
		}
 		//获取当前值
 		var value =$("#project_name_search").combobox("getValue");
		if(!isEmpty(value) && itemStr.indexOf("," + value + ",") == -1){
			$.messager.show({
				title:"警告",
				msg:"请选择正确的数据!"
			});
 			return;
		}
 		$("#data_manager_data").datagrid("reload",mosaicParams({"sort":"mh.entity_createdate","order":"desc"}));
 		currentRow=null;
 	}
 	function findData(id){
 		jumpPage("数据负责人查看", "${ctx}/dataManager/view.do?id="+id);
 	}
 	function removeDataManager(){
 		if(null==currentRow){
 			$.messager.show({
				title:"警告",
				msg:"请选择要删除的明细！"
			});
 			return;
 		}
 		$.messager.confirm("是否删除","正在执行数据删除操作，请确认或取消",function(r){
			if (r){
				$.post("${ctx}/dataManager/delete.do",{"id":currentRow.cid},function(result){
					handleReturn(result,function(){
						$("#data_manager_data").datagrid("reload");
						currentRow=null;
					});
				});
			}
		});
 	}
 	//格式化字典id为name
    function getValueByKey(key) {
    	for(var i=0;i<YN.length;i++){
    		if(YN[i].value==key){
    			return YN[i].text;
    		}
    	}
        return "";
    }
</script>
</body>
</html>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>字典管理</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div class="search_all_div">
				<div class="search_item">
					<label class="w80">字典编号</label>
					<span class="ml10">
						<input id="code_search" type="text" class="easyui-textbox w250" dkd-search-element="code like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">字典名称</label>
					<span class="ml10">
						<input id="name_search" type="text" class="easyui-textbox w250" dkd-search-element="name like text"/>
					</span>
				</div>
			</div>
			<div class="search_button" style="margin-top:5px;width:100%;text-align:center">
				<a href="javascript:searchDictionary()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
				<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
				<a href="javascript:newDictionary()" dkd-auth="AUTH_SYS_DIC_ADD" class="easyui-linkbutton" iconCls="icon-add">新增字典</a>
			</div>
		</div>
	</form>
	<div style="padding-left:10px;width:49%;float:left">
		<table id="dictionary_data" align="center">
			<thead>
				<tr>
					<th data-options="field:'code',width:50">编号</th>
					<th data-options="field:'name',width:50">名称</th>
					<th data-options="field:'_operate',width:200,formatter:formatDictionaryOper">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="padding-left:10px;width:49%;float:left">
		<table id="dictionary_children_data" align="center">
			<thead>
				<tr>
					<th data-options="field:'code',width:100">编号</th>
					<th data-options="field:'name',width:100">名称</th>
					<th data-options="field:'_operate',width:150,formatter:formatDictionaryChildrenOper">操作</th>
				</tr>
			</thead>
		</table>
	</div>
     <div id="dictionary_add" class="easyui-dialog pop" style="width:400px;height:350px;top:10px;padding:10px 20px" closed="true" buttons="#dlg-dictionary-buttons" maximizable=true modal=true>
            <form id="dictionaryForm" method="post" class="editForm">
            	<input id="id" name="id" type="hidden"/>
            	<input id="parent" name="parent" type="hidden"/>
	            <div class="fitem">
	                <label>字典编号<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
	                <input id="code" name="code" class="easyui-validatebox textbox" data-options="required:true"/>
	            </div>
	            <div class="fitem">
	                <label>字典名称<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
	                <input id="name" name="name" class="easyui-validatebox textbox"  data-options="required:true" />
	            </div>
	            <div class="fitem">
	                <label>字典序号<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
	                <input id="sort_no" name="sort_no" class="easyui-numberbox" data-options="required:true"/>
	            </div>
            </form>
    </div>
    <div id="dlg-dictionary-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveDictionary()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dictionary_add').dialog('close')">取消</a>
    </div>
    <script type="text/javascript">
 	$(function(){
 		$("#dictionary_data").datagrid({
 			title:"一级字典",
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			fitColumns:true,
            url:"${ctx}/dictionary/query.do",
            onLoadSuccess:loadSuccess
		});
 		$("#dictionary_children_data").datagrid({
 			title:"二级字典",
			rownumbers:true,
			singleSelect:true,
			fitColumns:true,
            onLoadSuccess:loadSuccess
		});
 	});
 	function formatDictionaryOper(val,row,index){  
 	    return "<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_DIC_MOD\" href=\"javascript:void(0)\" iconCls=\"icon-edit\" onclick=\"editDictionary('" + row.id + "',0)\">修改</a>" + 
 	   		"<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_DIC_DEL\" href=\"javascript:void(0)\" iconCls=\"icon-cancel\" onclick=\"deleteDictionary('" + row.id + "',0)\">删除</a>" +
 	  		"<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_NEXTDIC_CHECK\" href=\"javascript:void(0)\" iconCls=\"icon-search\" onclick=\"findNextDictionary('" + row.id + "')\">查看</a>" + 
 	  		"<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_NEXTDIC_ADD\" href=\"javascript:void(0)\" iconCls=\"icon-add\" onclick=\"newChildrenDictionary('" + row.id + "')\">添加下级字典</a>";  
 	}
 	function formatDictionaryChildrenOper(val,row,index){  
 	    return "<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_NEXTDIC_MOD\" href=\"javascript:void(0)\" iconCls=\"icon-edit\" onclick=\"editDictionary('" + row.id + "',1)\">修改</a>" +
 	   		"<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_NEXTDIC_DEL\" href=\"javascript:void(0)\" iconCls=\"icon-cancel\" onclick=\"deleteDictionary('" + row.id + "',1," + index + ")\">删除</a>";
 	}
 	var data;
 	function editDictionary(dicId,levels){  
		$.post("${ctx}/dictionary/findById.do",{"id":dicId},function(result){
			data = result;
			$("#dictionary_add").dialog("open").dialog("setTitle","修改字典信息");  
	        $("#dictionaryForm").form("load",data);
	        if(levels == 0){
	        	$("#code").attr("readonly",true);
	        }else{
	        	$("#code").attr("readonly",false);
	        }
 	   });
 	}
 	function searchDictionary(){
 		$("#dictionary_data").datagrid("reload",mosaicParams());
 	}
 	function newDictionary(){
 		$("#dictionary_add").dialog("open").dialog("setTitle","添加字典");
 		$("#code").attr("readonly",false);
 		$("#dictionaryForm").form("reset");
 		$("#parent").val("0");
 		data = {};
 	}
 	function newChildrenDictionary(dicId){
 		$("#dictionary_add").dialog("open").dialog("setTitle","添加字典");
 		$("#code").attr("readonly",false);
 		$("#dictionaryForm").form("reset");
 		$("#parent").val(dicId);
 		data = {};
 	}
 	function saveDictionary(){
 		if($("#dictionaryForm").form("validate")){
 			$.ajax({
 	 			url:"${ctx}/dictionary/save.do",
 	 			contentType : "application/json;charset=utf-8",
 	 			data:getData("dictionaryForm",data),
 	 			success:function(result){
 	 				handleReturn(result,function(){
 	 					$("#dictionary_add").dialog("close");
 	 					if(data.parent == "0"){
 	 	    				$("#dictionary_data").datagrid("reload");
 	 					}else{
 	 						findNextDictionary(data.parent);
 	 					}
 	 				});
 				}
 	 		});
 		}
	}
 	function findNextDictionary(dicId){
 		$.post("${ctx}/dictionary/getNextDictionary.do",{"pid":dicId},function(result){
 			$("#dictionary_children_data").datagrid("loadData",result); 			
 		});
 	}
 	function deleteDictionary(dicId,level,index){
 		if(level == 0){
 			$.messager.confirm('是否删除','此操作会删除该字典下级节点，是否确认删除？',function(r){
 			    if (r){
 			    	$.post("${ctx}/dictionary/deleteDictionary.do",{"id":dicId},function(result){
 			    		handleReturn(result,function(){
 			    			$("#dictionary_data").datagrid("reload");
 			    			$("#dictionary_children_data").datagrid("loadData",{total:0,rows:[]});
 	 	 				});
 			 		});
 			    }
 			});
 		}else{
 			$.messager.confirm('是否删除','是否确认删除？',function(r){
 			    if (r){
 			    	$.post("${ctx}/dictionary/deleteDictionary.do",{"id":dicId},function(result){
 			    		handleReturn(result,function(){
 			    			$("#dictionary_children_data").datagrid("deleteRow",index);
 	 	 				});
 			 		});
 			    }
 			});
 		}
 	}
</script>
</body>
</html>
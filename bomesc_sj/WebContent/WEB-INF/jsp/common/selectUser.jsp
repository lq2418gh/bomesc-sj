<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>选择用户</title>
</head>
<body>
	<ul id="deptTree" class="treeUl" style="width:23%">
  	</ul>
  	<div style="float:left;width:74%">
		<form class="search_form dkd-search">
			<table class="search_table">
				<tr>
					<td>姓名</td>
					<td><input id="name" dkd-search-element="au.name like text" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchUser()">查询</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="ok()">确定</a>
					</td>
				</tr>
			</table>
		</form>
		<div style="padding-left:10px">
			<table id="userData" align="center">
				<thead>
					<tr>
						<th data-options="field:'id',width:40,hidden:true" >id</th>
						<th data-options="field:'job_number',width:180" >工号</th>
						<th data-options="field:'name',width:300">名称</th>
						<th data-options="field:'birthday',width:180" >生日</th>
						<th data-options="field:'age',width:180" >年龄</th>
						<th data-options="field:'email',width:180" >邮箱</th>
						<th data-options="field:'department',width:180" >部门</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
    <script type="text/javascript">
    var id = "0";
 	$(function(){
 		$("#deptTree").tree({
			url:"${ctx}/login/selectDept.do",
			onLoadSuccess:function(node,param){
                $("#deptTree").tree("options").url = "";
            },
            onClick:function(node){
            	 id = node.id;
            	 if(node.attributes){
            		levels = node.attributes.levels;	 
            	 }else{
            		levels = 0;
            	 }
            	 
            	 if(id == "0"){
            		$("#userData").datagrid("reload",mosaicParams({"department isnull":id}));
            	 }else{
					$("#userData").datagrid("reload",mosaicParams({"department":id}));            			 
            	 }
             }
		});
 		$("#userData").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			fitColumns:true,
            url:"${ctx}/login/selectUser.do",
            queryParams:{"department isnull":""},
            method:"post"
		});
 	});
 	function searchUser(){
 		if(id == "0"){
			$("#userData").datagrid("reload",mosaicParams({"department isnull":id}));
		}else{
			$("#userData").datagrid("reload",mosaicParams({"department":id}));
		}
 	}
 	function formatUserCheck(val,row,index){  
 	    return "<input type='radio' name='checkUser' />";  
 	}
 	function ok(){
 		var row = $("#userData").datagrid("getSelected");
 		if(row){
 			var userDataHandle = getHandleFn("userDataHandle");
 			if(userDataHandle){
 				$.messager.confirm("是否确认","确定选择么？",function(r){
 	 				if (r){
 	 					userDataHandle(row);
 	 				}
 	 			});
 			}
 		}else{
 			$.messager.show({
				title:"警告",
				msg:"请选中一行数据"
			}); 			
 		}
 	}
</script>
</body>
</html>
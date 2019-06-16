<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>物料分类管理</title>
</head>
<body>
	<ul id="material_class_tree" class="treeUl">
  	</ul>
	<div style="float:left;width:69%">
		<form class="search_form dkd-search">
			<div class="search_div">
				<div class="search_all_div">
					<div class="search_item">
						<label class="w80">编码</label>
						<span class="ml10">
							<input id="code_search" type="text" class="easyui-textbox w250" dkd-search-element="a.code like text"/>
						</span>
					</div>
					<div class="search_item">
						<label class="w80">名称</label>
						<span class="ml10">
							<input id="name_search" type="text" class="easyui-textbox w250" dkd-search-element="a.name like text"/>
						</span>
					</div>
				</div>
				<div class="search_button" style="margin-top:5px;width:100%;text-align:center">
					<a href="javascript:searchClass()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
					<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
					<a href="javascript:newClass()" class="easyui-linkbutton" iconCls="icon-add" >新增</a>
					<a href="javascript:editClass()" class="easyui-linkbutton" iconCls="icon-edit" >修改</a>
					<a href="javascript:deleteClass()" class="easyui-linkbutton" iconCls="icon-edit" >删除</a>
				</div>
			</div>
		</form>
		<div class="datagrid_div">
			<table id="class_data" align="center" width="98%">
				<thead>
					<tr>
						<th data-options="field:'parent_code',width:80">总分类编码</th>
						<th data-options="field:'code',width:80">当前编码</th>
						<th data-options="field:'name',width:80">名称</th>
						<th data-options="field:'sort_no',width:20">排序</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
     <div id="class_add" class="easyui-dialog pop" style="width:400px;height:350px;top:10px;padding:10px 20px" closed="true" buttons="#dlg-class-buttons" maximizable=true modal=true>
		<form id="class_form" method="post" class="edit_form">
			<div class="fitem">
				<label>编码<span class="requiredSpan">*</span>：</label>
				<input id="code" name="code" class="easyui-validatebox textbox" data-options="required:true,validType:{length:[1,40]}"/>
			</div>
			<div class="fitem">
				<label id="name_show"></label>
				<input id="name" name="name" class="easyui-validatebox textbox" data-options="required:true,validType:{length:[1,40]}" />
			</div>
			<div class="fitem">
				<label>排序<span class="requiredSpan">*</span>：</label>
				<input id="sort_no" name="sort_no" class="easyui-numberbox" data-options="required:true"/>
			</div>
		</form>
    </div>
    <div id="dlg-class-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveClass()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#class_add').dialog('close')">取消</a>
    </div>
    <script type="text/javascript">
    var data,id = "0",levels = 0,currentId;
 	$(function(){
 		$("#material_class_tree").tree({
			url:"${ctx}/class/findNextClass.do",
			onLoadSuccess:function(node,param){
                $("#material_class_tree").tree("options").url = "";
            },
            onClick:function(node){
            	 id = node.id;
            	 if(node.attributes){
            		 levels = node.attributes.levels;
            	 }else{
            		 levels = 0;
            	 }
            	 if(id == "0"){
            		 $("#class_data").datagrid("reload",mosaicParams({"parent isnull":""}));
            	 }else{
            		 if(levels != 12){
            			 $("#class_data").datagrid("reload",mosaicParams({"parent":id}));            			 
            		 }
            	 }
             }
		});
 		$("#class_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			fitColumns:true,
            url:"${ctx}/class/query.do",
            queryParams:{"parent isnull":""},
            onClickRow:function(rowIndex, rowData){
            	currentId = rowData.id;
            }
		});
 	});
 	function editClass(){
 		if(isEmpty(currentId)){
 			$.messager.show({
				title:"警告",
				msg:"请选择一行数据"
			});
 			return;
 		}
		$.post("${ctx}/class/findById.do",{"id":currentId},function(result){
			data = result;
			formateName();
			$("#class_add").dialog("open").dialog("setTitle","修改分类信息"); 
	        $("#class_form").form("load",data);
 	   });
 	}
 	function searchClass(){
		if(id == "0"){
			$("#class_data").datagrid("reload",mosaicParams({"parent isnull":""}));
		}else{
			$("#class_data").datagrid("reload",mosaicParams({"parent":id}));            		 
		}
 	}
 	function newClass(){
 		if(levels==11){
 			$.messager.show({
				title:"警告",
				msg:"规格型号5下不能再创建其他分类!"
			});
 			return;
 		}
 		formateName();
 		$("#class_add").dialog("open").dialog("setTitle","添加分类");
 		$("#class_form").form("reset");
 		data = {};
 		if(id != "0"){
 			data.parent = {"id":id};
 		}
 	}
 	function saveClass(){
		if((levels==0||levels==6||levels==7||levels==8||levels==9||levels==10)&&$("#code").val().length!=1){
 			$.messager.show({
				title:"警告",
				msg:"该分类编码长度为1!"
			});
 			return;
		}
		if((levels==1||levels==2||levels==3||levels==4||levels==5)&&$("#code").val().length!=2){
 			$.messager.show({
				title:"警告",
				msg:"该分类编码长度为2!"
			});
 			return;
		}
 		if($("#class_form").form("validate")){
 			$.ajax({
 	 			url:"${ctx}/class/save.do",
 	 			contentType : "application/json;charset=utf-8",
 	 			data:getData("class_form",data),
 	 			success:function(result){
 	 				handleReturn(result,function(){
 	 					$("#class_add").dialog("close");
 	    				$("#class_data").datagrid("reload");
 	    				if(data.id){
 	    					//修改
 	    					var node = $("#material_class_tree").tree("find", data.id);
 	    					if(node){
 	    						$("#material_class_tree").tree('update', {
 	 	    						target: node.target,
 	 	    						text: data.name,
 	 	    						attributes:{"code":data.code}
 	 	    					});
 	    					}
 	    				}else{
 	    					//新增
 	    					//$("#material_class_tree").tree("options").url = "${ctx}/class/findNextClass.do";
 	    					//$("#material_class_tree").tree('reload');
 	    					var node = $("#material_class_tree").tree('find', id);
 	    					if(node){
 	    						$("#material_class_tree").tree("append", {
 	    							parent: node.target,
 	    							data: [{
 	    								id: result.id,
 	    								text: data.name,
 	 	 	    						attributes:{"code":data.code,"levels":parseInt(levels)+1}
 	    							}]
 	    						});
 	    					}
 	    				}
 	    				//$("#material_class_tree").tree("reload");
 	 				});
 				}
 	 		});
 		}
	}
 	function formateName(){
		if(levels==0){
			$("#name_show").html("产品专业<span class='requiredSpan'>*</span>：");
		}
		if(levels==1){
			$("#name_show").html("产品大类<span class='requiredSpan'>*</span>：");
		}
		if(levels==2){
			$("#name_show").html("产品中类<span class='requiredSpan'>*</span>：");
		}
		if(levels==3){
			$("#name_show").html("设计标准<span class='requiredSpan'>*</span>：");
		}
		if(levels==4){
			$("#name_show").html("材质<span class='requiredSpan'>*</span>：");
		}
		if(levels==5){
			$("#name_show").html("尺寸<span class='requiredSpan'>*</span>：");
		}
		if(levels==6){
			$("#name_show").html("规格型号1<span class='requiredSpan'>*</span>：");
		}
		if(levels==7){
			$("#name_show").html("规格型号2<span class='requiredSpan'>*</span>：");
		}
		if(levels==8){
			$("#name_show").html("规格型号3<span class='requiredSpan'>*</span>：");
		}
		if(levels==9){
			$("#name_show").html("规格型号4<span class='requiredSpan'>*</span>：");
		}
		if(levels==10){
			$("#name_show").html("规格型号5<span class='requiredSpan'>*</span>：");
		}
 	}
 	function deleteClass(){
 		if(isEmpty(currentId)){
 			$.messager.show({
				title:"警告",
				msg:"请选择一行数据"
			});
 			return;
 		}
    	$.post("${ctx}/class/delete.do",{"id":currentId},function(result){
    		handleReturn(result,function(){
    			$("#class_data").datagrid("reload");
    			$("#material_class_tree").tree("options").url = "${ctx}/class/findNextClass.do";
    			$("#material_class_tree").tree('reload');
    			currentId=null;
			});
 		});
 	}
</script>
</body>
</html>
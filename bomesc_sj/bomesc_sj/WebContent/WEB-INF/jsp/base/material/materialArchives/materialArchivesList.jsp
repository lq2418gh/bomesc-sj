<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>物料分类管理</title>
</head>
<script type=""></script>
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
							<input id="material_code_search" type="text" class="easyui-textbox w250" dkd-search-element="material_code like text"/>
						</span>
					</div>
					<div class="search_item">
						<label class="w80">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称</label>
						<span class="ml10">
							<input id="material_name_search" type="text" class="easyui-textbox w250" dkd-search-element="material_name like text"/>
						</span>
					</div>
					<div class="search_item">
						<label class="w80">单位</label>
						<span class="ml10">
							<input id="unit_search" type="text" class="easyui-textbox w250" dkd-search-element="unit like text"/>
						</span>
					</div>
					<div class="search_item">
						<label class="w80">采办物料编码</label>
						<span class="ml10">
							<input id="purchase_material_code_search" type="text" class="easyui-textbox w250" dkd-search-element="purchase_material_code like text"/>
						</span>
					</div>
				</div>
				<div class="search_button" style="margin-top:5px;width:100%;text-align:center">
					<a href="javascript:searchArchives()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
					<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
					<a href="javascript:newArchives()" class="easyui-linkbutton" iconCls="icon-add" >新增</a>
					<a href="javascript:editArchives()" class="easyui-linkbutton" iconCls="icon-edit" >修改</a>
					<a href="javascript:deleteArchives()" class="easyui-linkbutton" iconCls="icon-edit" >删除</a>
				</div>
			</div>
		</form>
		<div class="datagrid_div">
			<table id="archives_data" align="center" width="100%">
				<thead>
					<tr>
						<th data-options="field:'id',width:100,hidden:true">物料编码</th>
						<th data-options="field:'material_code',width:100">物料编码</th>
						<th data-options="field:'material_name',width:100">物料名称</th>
						<th data-options="field:'unit',width:100">单位</th>
						<th data-options="field:'purchase_material_code',width:100">采办物料编码</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
     <div id="archives_add" class="easyui-dialog" style="width:69%;height:200px;top:10px;padding:10px 20px" closed="true" buttons="#dlg-archives-buttons" maximizable=true modal=true>
		<form id="archives_form" method="post" class="edit_form">
			<table class="lines-both">
				<tr>
					<td><label>物料分类编码<span class="requiredSpan">*</span>：</label></td>
					<td><input id="material_code" name="material_code" class="easyui-validatebox textbox" data-options="editable:false,validType:{length:[16,16]}"/></td>
					<td><label>流水号<span class="requiredSpan">*</span>：</label></td>
					<td><input id="serial_number" name="serial_number" ignore class="easyui-textbox" data-options="editable:false"/></td>
					<!--  <td><label id="name_show">物料明细编码<span class="requiredSpan">*</span>：</label></td>
					<td><input id="material_detail_code" name="material_detail_code" class="easyui-validatebox textbox" data-options="required:true,validType:{length:[11,11]}" onBlur="checkCode()"/></td>-->
				</tr>
				<tr>
					<td><label>物料名称<span class="requiredSpan">*</span>：</label></td>
					<td><input id="material_name" name="material_name" class="easyui-textbox" data-options="required:true,validType:{length:[1,500]}"/></td>
					<td><label>计量单位<span class="requiredSpan">*</span>：</label></td>
					<td><input id="unit" name="unit" class="easyui-textbox" data-options="required:true,validType:{length:[1,20]}"/></td>					
				</tr>
				<tr>
					<td><label>采办物料编码：</label></td>
					<td><input id="purchase_material_code" name="purchase_material_code" class="easyui-textbox" data-options="validType:{length:[1,500]}"/></td>
					<td></td>
					<td></td>					
				</tr>
				<tr>
					<td colspan="4" style="text-align: center">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveArchives()">保存</a>
       					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#archives_add').dialog('close')">取消</a>
					</td>
				</tr>																
			</table>
		</form>
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
        		 if(parseInt(levels)<12&&parseInt(levels)>0){
        			 $("#archives_data").datagrid("reload",mosaicParams({"id":id,"levels":levels}));   
            	 }
             }
		});
 		$("#archives_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			fitColumns:true,
            url:"${ctx}/archives/query.do",
            queryParams:{"id":id,"levels":levels},
            onLoadSuccess:function(){
            	currentId=null;
            },
            onClickRow:function(rowIndex, rowData){
            	currentId = rowData.id;
            }
		});
 	});
 	function editArchives(){
 		if(isEmpty(currentId)){
 			$.messager.show({
				title:"警告",
				msg:"请选择一行数据"
			});
 			return;
 		}
		$.post("${ctx}/archives/findById.do",{"id":currentId},function(result){
			data = result;
			data.serial_number=data.material_code.substring(16,18);
			data.material_code=data.material_code.substring(0,16);
			$("#archives_add").dialog("open").dialog("setTitle","修改分类信息"); 
	        $("#archives_form").form("load",data);
 	   });
 	}
 	function searchArchives(){
		$("#archives_data").datagrid("reload",mosaicParams({"id":id,"levels":levels}));            		 
 	}
 	function newArchives(){//新建
 		if(levels!=11){//校验是否选中了产品中类
 			$.messager.show({
				title:"警告",
				msg:"请选择相应的规格型号5!"
			});
 			return;
 		}
 		$.ajax({//根据产品中类的id后台获取相应的总的编码，产品专业+产品大类+产品中类
 			type:"post",
 			url:"${ctx}/archives/findTotalCode.do",
 			async:false,
 			dataType:"text",
 			contentType : "application/json;charset=utf-8",
 			data:JSON.stringify({"id":id}),
 			success:function(result){
 				$("#material_code").val(result);
			}
 		});
 		$("#archives_add").dialog("open").dialog("setTitle","添加物料档案");
 		data = {};
 	}
 	function saveArchives(){
 		if(isEmpty(currentId)){
 	 		data.standard5={"id":id};//传入这个规格型号5的id
 	 		if($("#archives_form").form("validate")){
 	 			$.ajax({
 	 	 			url:"${ctx}/archives/save.do",
 	 	 			contentType : "application/json;charset=utf-8",
 	 	 			data:getData("archives_form",data),
 	 	 			success:function(result){
 	 	 				handleReturn(result,function(){
 	 	 					$("#archives_add").dialog("close");//关闭弹框
 	 	    				$("#archives_data").datagrid("reload");//重新加载当前表
 	 	    				$("#archives_form").form("clear");//保存完毕清空form
 	 	 				});
 	 				}
 	 	 		});
 	 		}
 		}else{
 	 		if($("#archives_form").form("validate")){
 	 			var newData=getDataBase("archives_form",data);
 	 			newData.material_code=data.material_code+data.serial_number;
 	 			delete newData.serial_number;
 	 			$.ajax({
 	 	 			url:"${ctx}/archives/update.do",
 	 	 			contentType : "application/json;charset=utf-8",
 	 	 			data:JSON.stringify(newData),
 	 	 			success:function(result){
 	 	 				handleReturn(result,function(){
 	 	 					$("#archives_add").dialog("close");//关闭弹框
 	 	    				$("#archives_data").datagrid("reload");//重新加载当前表
 	 	    				$("#archives_form").form("clear");//保存完毕清空form
 	 	 				});
 	 				}
 	 	 		});
 	 		}
 		}
	}
 	function deleteArchives(){
 		if(isEmpty(currentId)){
 			$.messager.show({
				title:"警告",
				msg:"请选择一行数据！"
			});
 			return;
 		}
    	$.post("${ctx}/archives/delete.do",{"id":currentId},function(result){
    		handleReturn(result,function(){
    			$("#archives_data").datagrid("reload");
			});
 		});
    	
 	}
 	/**function checkCode(){
 		var material_detail_code = $("#material_detail_code").val();
 		if(material_detail_code.length!=11){
 			$.messager.show({
				title:"警告",
				msg:"物料明细编码长度必须为11位！"
			});
 			$("#material_detail_code").val("");
 			return;
 		}else{
 	 		$.ajax({//根据物料分类编码+输入的11位明细编码获取后台的流水号，赋值到相应的input框，然后将拼接好的物料编码赋值到物料编码框
 	 			type:"post",
 	 			url:"${ctx}/archives/findSerialNum.do",
 	 			async:false,
 	 			dataType:"text",
 	 			contentType : "application/json;charset=utf-8",
 	 			data:JSON.stringify({"totalCode":$("#material_code").val(),"detailCode":$("#material_detail_code").val()}),
 	 			success:function(result){
 	 				alert(parseInt(result));
 	 				if(parseInt(result)<1000){
 	 	 				$("#serial_number").textbox("setValue",result);
 	 	 				$("#material_code").textbox("setValue",$("#material_code").val()+$("#material_detail_code").val()+$("#serial_number").val());
 	 				}else{
 	 		 			$.messager.show({
 	 						title:"警告",
 	 						msg:"该分类和明细下流水号已达最大值！"
 	 					});
 	 		 			$("#material_detail_code").val("");
 	 				}
 				}
 	 		});
 		}
 	}*/
</script>
</body>
</html>
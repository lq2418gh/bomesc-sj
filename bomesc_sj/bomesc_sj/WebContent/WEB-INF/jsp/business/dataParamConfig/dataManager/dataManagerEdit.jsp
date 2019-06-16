<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>数据负责人管理</title>
</head>
<body>
	<!-- 编辑页面 -->
	<form id="dataManager_form" method="post" action="${ctx}/dataManager/save.do">
		<table width="100%" class="search_table">
			<tr>
				<th>单据编码:</th>
				<td><input id="manager_no_edit" name="manager_no" size="15" class="easyui-textbox" readonly="true"/></td>
				<th>项目名称:</th>
				<td><input id="project_edit" name="project" class="edit_combobox" data-options="width:150,url:'${ctx}/project/show.do'"/>
					<input type="hidden" id="project_name" name="project_name" value="" /></td>
				<th>项目工作号:</th>
				<td><input id="job_no" name="job_no" size="15" class="easyui-textbox" readonly="true"/></td>
				<th>专业名称:</th>
				<td><select id="major_edit" name="major" is-object class="easyui-combobox" data-options="width:150,panelHeight:'auto',required:true,editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'"></select></td>
			</tr>
			<tr>
				<th>录入人:</th>
				<td><input id="entity_createuser_edit" name="entity_createuser" size="15" class="easyui-textbox" readonly="true"/></td>
				<th>录入日期:</th>
				<td><input id="entity_createdate_edit" name="entity_createdate" size="12" class="easyui-datetimebox" readonly="true"/></td>
				<th>修改人:</th>
				<td><input id="entity_modifyuser_edit" name="entity_modifyuser" size="15" class="easyui-textbox" readonly="true"/></td>
				<th>修改日期:</th>
				<td><input id="entity_modifydate_edit" name="entity_modifydate" size="12" class="easyui-datetimebox" readonly="true"/></td>
			</tr>
			<tr>
				<td colspan="8" style="text-align: center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveDataManager()">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('数据负责人管理','${ctx}/login/showList.do?url=business/dataParamConfig/dataManager')">返回</a>
				</td>
			</tr>
			<tr>
				<td colspan="8">
					<center>
						<div id="toolbar_dataManagerEdit" class="dkd-toolbar-wrap">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addSoLine()">增行</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteSoLine()">删行</a>
					    </div>
					</center>
				</td>
			</tr>
		</table>
	</form>
	<div style="padding-left:10px">
	    <table id="dataManager_detail" style="width:100%">
			<thead>
				<tr>
					<th data-options="field:'job_number',width:200">人员编码</th>
					<th data-options="field:'manager_user',hidden:true,editor:{type:'textbox',options:{required:true}}">申购人</th>
					<th data-options="field:'manager_user_name',width:250,formatter:formatselectUserOper">姓名</th>
					<th data-options="field:'department',width:200">部门</th>
					<th data-options="field:'email',width:200,editor:{type:'textbox',options:{required:true,validType:['email','length[0,20]']}}">邮箱</th>
					<th data-options="field:'export_fail',width:100,editor:{type:'combobox',options:{valueField:'value',textField:'text',panelHeight:50,required:true,editable:false}},formatter:getValueByKey">导入失败提示</th>
					<th data-options="field:'lack_material',width:120,editor:{type:'combobox',options:{valueField:'value',textField:'text',panelHeight:50,required:true,editable:false}},formatter:getValueByKey">缺料提示</th>
				</tr>
			</thead>
		</table>
    </div>
    <script type="text/javascript">
    var currentRow,data={},YN=[{"text":"是","value":"Y"},{"text":"否","value":"N"}];
    //初始化页面调用方法
 	$(function(){
 		$("#dataManager_detail").datagrid({
 			rownumbers:true,
 			fitColumns:true,
            toolbar:"#toolbar_dataManagerEdit",
            singleSelect:true,
            onClickRow:onClickRow,
            onAfterEdit:function(){
            	$(".search_button").linkbutton();
            },
            onLoadSuccess:function(data){
            	$(".search_button").linkbutton();
            }
		});
 		$("#project_edit").combobox({
    		onChange: function (n,o) {
    			//自动填充项目工作号
    			fillJobNo();
    			//检查是否合法（是否已录入）
    			checkValueValidate("project");
    			//删除明细
    			removeAll();
    		}
    	})
 		$("#major_edit").combobox({
    		onChange: function (n,o) {
    			//检查是否合法（是否已录入）
    			checkValueValidate("major");
    			removeAll();
    		}
    	})
		if(isEmpty("${id}")){
			//新增方法
			$("#dataManager_detail").datagrid("loadData",{total:0,rows:[]});
		}else{
			//编辑方法
			$.post("${ctx}/dataManager/findByNo.do",{"id":"${id}"},function(result){
				data = result;
				data.project=data.job_no;
		        $("#dataManager_form").form("load",data);
		        $("#dataManager_detail").datagrid("loadData",{total:data.dataManagerHeads.length,rows:data.dataManagerHeads});
	 	   });
		}
 	});
    //填充项目工作号
    function fillJobNo(){
    	$("#job_no").textbox("setValue",$("#project_edit").combobox("getValue"));
    	$("#project_name").val($("#project_edit").combobox("getText"));
    }
    //检查当前项目及专业是否已经录入
    function checkValueValidate(type){
    	var project= $("#project_edit").combobox("getValue");//项目编号
    	var major = $("#major_edit").combobox("getValue");//专业
    	if(isEmpty(project)||isEmpty(major)){
    		return;
    	}
    	var flag = false;
    	$.ajax({
    		type:"post",
    		async:false,
    		contentType : "application/json;charset=utf-8",
    		url:"${ctx}/dataManager/checkValidate.do",
    		data:JSON.stringify({"project":project,"major":major,"id":"${id}"}),
    		success:function(result){
    			flag=result;
 			}
    	})
    	if(flag){
    		if("project"==type){
    			$("#project_edit").combobox("setValue",null);
    			$("#job_no").textbox("setValue",null);
    		}else{
    			$("#major_edit").combobox("setValue",null);
    		}
    		$.messager.show({
				title:"警告",
				msg:"已存在相同项目、相同专业的单据，请检查!"
			});
			return;
    	}
    }
    //增加一行
 	function addSoLine(){
 		var project= $("#project_edit").combobox("getValue");//项目编号
    	var major = $("#major_edit").combobox("getValue");//专业
    	if(isEmpty(project)){
    		$.messager.show({
				title:"警告",
				msg:"请先选择项目！"
			});
			return;
    	}
		if(isEmpty(major)){
			$.messager.show({
				title:"警告",
				msg:"请先选择专业!"
			});
			return;
    	}
		$("#dataManager_detail").datagrid("appendRow",{});
		$(".search_button").linkbutton();
	}
 	var editIndex;
    //删除一行
 	function deleteSoLine(){
		var rows = $("#dataManager_detail").datagrid("getSelections");
		if(rows){
			for(var i = 0;i < rows.length;i++){
				 $("#dataManager_detail").datagrid("deleteRow",$("#dataManager_detail").datagrid("getRowIndex",rows[i]));
			}
		}
		editIndex = undefined;
		endEditing();
	}
 	//结束编辑
    function endEditing(){
    	if (editIndex == undefined){return true;}
        if ($("#dataManager_detail").datagrid("validateRow", editIndex)){
            $("#dataManager_detail").datagrid("endEdit", editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    var editors;
    //点击行
 	function onClickRow(index,rowData){
 	    if (editIndex != index){
 	        if (endEditing()){
 	            $("#dataManager_detail").datagrid("beginEdit", index);
 	            editors= $('#dataManager_detail').datagrid('getEditors',index);
 	            editIndex = index;
 	            $(".datagrid-editable-input").css({"width":"100%","height":"26px"});
 	            $(".datagrid-editable-input:eq(4)").focus();
 	         	//填充下拉框
 	 	 	    fillCombobox(index);
 	        }
 	    }
 	}
 	function formatselectUserOper(val,row,index){
		return "<input name='manager_user_name' size='20' class='easyui-validatebox fleft' data-options='required:true' readonly='readonly' value='" + (val ? val : "") + "'/>"+
		"<a href='javascript:void(0)' class='easyui-linkbutton search_button' iconCls='icon-search' onclick='selectUser(" + index + ")'>查询</a>";
	}
 	function selectUser(index){
		clickIndex = index;
		showWindow({
  			title:"选择用户",
  			href:"${ctx}/login/selectUserList.do",
  			width:820,
  			height:550
  		});
	}
 	function userDataHandle(row){
		var gridRow = $("#dataManager_detail").datagrid("getRows")[clickIndex];
		gridRow.manager_user = row.id;
		gridRow.manager_user_name = row.name;
		gridRow.job_number = row.job_number;
		gridRow.department = row.department;
		gridRow.email = row.email;
		$("#dataManager_detail").datagrid("refreshRow", clickIndex);
		$(".search_button").linkbutton();
		if(clickIndex == editIndex){
			$("#dataManager_detail").datagrid("selectRow", clickIndex).datagrid(
                    "beginEdit", clickIndex);
		}
		closeWindow();
		//填充下拉框
 	    fillCombobox(clickIndex);
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
 	//保存数据负责人
 	function saveDataManager(){
 		endEditing();
 		if(!$("#dataManager_form").form("validate")){
			return;
		}
 		//获取所有选项，拼接
 		var items = $("#project_edit").combobox("getData");
 		var itemStr = ",";
		for(var i = 0;i < items.length;i++){
			itemStr += items[i].value + ",";
		}
 		//获取当前值
 		var value =$("#project_edit").combobox("getValue");
		if(isEmpty(value) || itemStr.indexOf("," + value + ",") == -1){
			$.messager.show({
				title:"警告",
				msg:"请选择正确的项目！"
			});
			return;
		}
 		var rows = $("#dataManager_detail").datagrid("getRows");
 		if(rows.length==0){
 			$.messager.show({
				title:"警告",
				msg:"请增加明细！"
			});
			return;
 		}
 		for(var i=0;i<rows.length;i++){
 			//$("#dataManager_detail").datagrid("beginEdit", i);
 			var newData = $("#dataManager_detail").datagrid("getRows")[i];
			onClickRow(i,newData);
 			var userId = $('#dataManager_detail').datagrid('getEditor', { 'index': i,field:'manager_user'});
 			if(isEmpty(userId)){
 				continue;
 			}
 			var userIdValue=$(userId.target).textbox("getValue");
			if(isEmpty(userIdValue)){
 				$.messager.show({
					title:"警告",
					msg:"请选择数据负责人!"
				});
 				$("#dataManager_detail").datagrid("endEdit", i);
				return;
 			}
 			$("#dataManager_detail").datagrid("endEdit", i);
 		}
 		for(var i=0;i<rows.length;i++){
 			for(var j=i+1;j<rows.length;j++){
 				if(!isEmpty(rows[j].job_number)&&!isEmpty(rows[i].job_number)&&(rows[i].job_number==rows[j].job_number)){
 					$.messager.show({
 						title:"警告",
 						msg:"数据负责人有重复人员，请核查!"
 					});
 					return;
 				}
 			}
 		}
 		$.ajax({
 			type:"post",
 			url:"${ctx}/dataManager/save.do",
 			contentType : "application/json;charset=utf-8",
 			data:getData("dataManager_form",data,"dataManagerHeads",rows),
 			success:function(result){
 				handleReturn(result,function(){
 					jumpPage("数据负责人查看", "${ctx}/dataManager/view.do?id="+result.id);
 				});
			}
 		});
 	}
 	//加载字典下拉框的值(index为当前点击的下标)
 	function fillCombobox(index){
 		var exportFail = $('#dataManager_detail').datagrid('getEditor', { 'index': index,field:'export_fail'});
 	    var lackMaterial = $('#dataManager_detail').datagrid('getEditor', { 'index': index,field:'lack_material'});
		$(exportFail.target).combobox("loadData", YN);
		var exportFailNum=$(exportFail.target).textbox("getValue");
		if(isEmpty(exportFailNum)){
			$(exportFail.target).combobox("setValue", "Y");
		}else{
			$(exportFail.target).combobox("setValue",exportFailNum);
		}
		$(lackMaterial.target).combobox("loadData", YN);
		var lackMaterialNum=$(lackMaterial.target).textbox("getValue");
		if(isEmpty(lackMaterialNum)){
			$(lackMaterial.target).combobox("setValue", "Y");
		}else{
			$(lackMaterial.target).combobox("setValue",lackMaterialNum);
		}
 	}
 	function removeAll(){
 		var rows = $("#dataManager_detail").datagrid("getRows");
 		var length = rows.length;
 		if(rows){
			for(var i = 0;i < length;i++){
				 $("#dataManager_detail").datagrid("deleteRow",$("#dataManager_detail").datagrid("getRowIndex",rows[i]));
			}
		}
 		editIndex = undefined;
 	}
</script>
</body>
</html>
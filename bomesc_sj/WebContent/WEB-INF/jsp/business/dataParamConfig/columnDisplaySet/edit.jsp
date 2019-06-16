<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>补充列显示设置编辑页面</title>
   
</head>
<body>
	<form id="project_form" method="post" action="${ctx}/project/save.do" class="search_form dkd-search">
		<div class="search_div" style="margin-top:20px">
			<div class="search_item">
				<label>项目名称</label>
				<span class="ml10">
				<select id="project" name="project" class="easyui-combobox w200" data-options="url:'${ctx}/project/show.do'"></select>
				<input id="project_name" name="project_name" class="easyui-textbox" type="hidden">
				</span>
			</div>
			<div class="search_item">
				<label>工作号</label>
				<span class="ml10"><input id="job_no" name="job_no" class="easyui-textbox w200" data-options="editable:false" ></span>
			</div>
			<div class="search_item">	
				<label>专业名称</label>
				<span class="ml10"><select id="major"  required="true"  is-object name="major" class="easyui-combobox w200" data-options="editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'"></select></span>	
			</div>
			<div class="search_item">
				<label>&nbsp;&nbsp;录入人</label>
				<span class="ml10"><input id="entity_createuser" ignore name="entity_createuser" class="easyui-textbox w200" readonly></span>
			</div>
			
			<div class="search_item">
				<label>录入日期</label>
				<span class="ml10"><input id="entity_createdate" ignore name="entity_createdate" class="easyui-datebox w200" readonly></span>
			</div>
			<div class="search_item">
				<label>&nbsp;&nbsp;修改人</label>
				<span class="ml10"><input id="entity_modifyuser" ignore name="entity_modifyuser" class="easyui-textbox w200" readonly></span>
			</div>
			<div class="search_item">
				<label>修改日期</label>
				<span class="ml10"><input id="entity_modifydate" ignore name="entity_modifydate" class="easyui-datebox w200" readonly></span>
			</div>
			<div class="search_item" style="margin-left:30%;width:60%;height:50%;float:left;overflow-y:auto;">
				<table id="draw_detail" >
					<thead>
						<tr>
							<th>列名</th>
							<th>显示名</th>
						</tr>
						<tr>
							<th>补充列1:</th>
							<th><input id ="column1" name ="column1" class="easyui-textbox w200" data-options="field:'column1',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列2:</th>
							<th><input id ="column2" name ="column2" class="easyui-textbox w200" data-options="field:'column2',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列3:</th>
							<th><input id ="column3" name ="column3" class="easyui-textbox w200" data-options="field:'column3',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列4:</th>
							<th><input id ="column4" name ="column4" class="easyui-textbox w200" data-options="field:'column4',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列5:</th>
							<th><input id ="column5" name ="column5" class="easyui-textbox w200" data-options="field:'column5',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列6:</th>
							<th><input id ="column6" name ="column6" class="easyui-textbox w200" data-options="field:'column6',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列7:</th>
							<th><input id ="column7" name ="column7" class="easyui-textbox w200" data-options="field:'column7',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列8:</th>
							<th><input id ="column8" name ="column8" class="easyui-textbox w200" data-options="field:'column8',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列9:</th>
							<th><input id ="column9" name ="column9" class="easyui-textbox w200" data-options="field:'column9',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列10:</th>
							<th><input id ="column10" name ="column10" class="easyui-textbox w200" data-options="field:'column10',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列11:</th>
							<th><input id ="column11" name ="column11" class="easyui-textbox w200" data-options="field:'column11',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列12:</th>
							<th><input id ="column12" name ="column12" class="easyui-textbox w200" data-options="field:'column12',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列13:</th>
							<th><input id ="column13" name ="column13" class="easyui-textbox w200" data-options="field:'column13',editor:{type:'textbox'},validType:{length:[1,100]}"></th>
						</tr>
						<tr>
							<th>补充列14:</th>
							<th><input id ="column14" name ="column14" class="easyui-textbox w200" data-options="field:'column14'"></th>
						</tr>
						<tr>
							<th>补充列15:</th>
							<th><input id ="column15" name ="column15" class="easyui-textbox w200" data-options="field:'column15'"></th>
						</tr>
						<tr>
							<th>补充列16:</th>
							<th><input id ="column16" name ="column16" class="easyui-textbox w200" data-options="field:'column16'"></th>
						</tr>
						<tr>
							<th>补充列17:</th>
							<th><input id ="column17" name ="column17" class="easyui-textbox w200" data-options="field:'column17'"></th>
						</tr>
						<tr>
							<th>补充列18:</th>
							<th><input id ="column18" name ="column18" class="easyui-textbox w200" data-options="field:'column18'"></th>
						</tr>
						<tr>
							<th>补充列19:</th>
							<th><input id ="column19" name ="column19" class="easyui-textbox w200" data-options="field:'column19'"></th>
						</tr>
						<tr>
							<th>补充列20:</th>
							<th><input id ="column20" name ="column20" class="easyui-textbox w200" data-options="field:'column20'"></th>
						</tr>
						
					</thead>
				</table>
			</div>
			<div class="search_item" style="margin-top:20px;width:100%;text-align:center">
				<a href="javascript:save_data()" class="easyui-linkbutton" iconCls="icon-save">保存</a>
				<a href="javascript:close_windows()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>		
			</div>
		</div>	
	</form>
<script type="text/javascript">
	var data={}, init = false;
	$(function(){
        $("#project_form .search_button").linkbutton();
		if(isEmpty("${id}")){
			$("#project_form").form("load",data);
		}else{
			$.post("${ctx}/columnDisplaySet/edit_load_data.do",{"id":"${id}"},function(result){
				data = result;
		        $("#project_form").form("load",data);
		        $('#project').combobox('setValue',data.job_no);
		        $('#project_name').val(data.project_name);
	 	   });
		}
	});
	//1选择项目名称 onchange事件
	$("#project").combobox({
		onChange: function (newValue, oldValue) {
			if(!init){
				fillProjectNo(newValue, oldValue);
			}
		}
	});
	function fillProjectNo(newValue, oldValue){	
 		$("#job_no").textbox("setValue",newValue);
 		$("#project_name").textbox("setValue",$("#project").combobox("getText"));
 	}

	function save_data(){
		if(!$("#project_form").form("validate")){
			return false;
		}
		if(!$('#project').combobox('getValue')){
			$.messager.alert("操作提示","请添加项目名称！","warning");
			return false;
		}
		if(!$('#major').combobox('getValue')){
			$.messager.alert("操作提示","请添加专业名称！","warning");
			return false;
		}
		$.ajax({
 			type:"post",
 			url:"${ctx}/columnDisplaySet/save.do",
 			contentType : "application/json;charset=utf-8",
 			data:getData("project_form",data),
 			success:function(result){
 				handleReturn(result,function(){
 					closeSelfWindow();
 					jumpPage('补充列显示名称设置','${ctx}/login/showList.do?url=business/dataParamConfig/columnDisplaySet');
				});
			}
	 	});
	}
	function close_windows(){
		closeSelfWindow();
	}
</script>
</body>
</html>
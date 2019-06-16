<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单编辑页面</title>
<style type="text/css">
   label{
  		min-width: 85px;
 		float: left;
  		display: block;
   }
   input{
   		width:95px;
   }
   .search_div{
   		height:500px;
   }
   .titleMessage{
   		font-size:17px;
   		background:#99CCFF; 
   		color:#FFF;
   		margin:10px,10px,10xp,10px;
   }
</style>      
</head>
<body>
	<form id="project_form" method="post" action="${ctx}/project/save.do" class="search_form dkd-search">
		<div class="search_div">
			<div class="titleMessage">项目基本信息-Basic project information</div>
			<div class="search_item">
				<label>项目名称</label>
				<span class="ml10">
					<input id="project_name" name="project_name_edit" class="edit_combobox" ignore data-options="width:90,url:'${ctx}/project/show.do'"/>
					<input name="project_name" type="hidden" />
				</span>
			</div>
			<div class="search_item">	
				<label>合同号</label>
				<span class="ml10"><input id="project_contract_no" name="project_contract_no" class="easyui-textbox" ignore data-options="editable:false"/></span>
			</div>
			<div class="search_item">	
				<label>项目工作号</label>
				<span class="ml10"><input id="job_no" name="job_no" class="easyui-textbox" data-options="editable:false"/></span>
			</div>
			<div class="search_item">	
				<label>项目签订公司</label>
				<span class="ml10"><input id="company" name="company_name" class="easyui-textbox" ignore data-options="editable:false"/></span>	
			</div>
			<div class="search_item">	
				<label>合同甲方</label>
				<span class="ml10"><input id="contractor" name="contractor" class="easyui-textbox" ignore data-options="editable:false"></span>
			</div>
			<div class="search_item">
				<label>合同类型</label>
				<span class="ml10"><input id="contract_type" name="contract_type_name" class="easyui-textbox" ignore data-options="editable:false"/></span>
			</div>
			<div class="search_item">	
				<label>项目类型</label>
				<span class="ml10"><input id="project_type" name="project_type" class="easyui-textbox" ignore data-options="editable:false"></span>
			</div>
			<div class="search_item">	
				<label>项目分类</label>
				<span class="ml10"><input id="project_category" name="project_category" class="easyui-textbox" ignore data-options="editable:false"/></span>
			</div>
			<div class="search_item">	
				<label>设计类型</label>
				<span class="ml10"><input id="design_type" name="design_type_name" class="easyui-textbox" ignore data-options="editable:false"/></span>									
			</div>
			<div class="titleMessage">项目时间信息-Project time information</div>
			<div class="search_item">
				<label>项目合同开始时间：</label>
				<span class="ml10">
					<input id="contract_start_time" name="contract_start_time" type="text" class="easyui-datebox w120" ignore data-options="editable:false" readonly/>
				</span>
			</div>
			<div class="search_item">	
				<label>项目合同结束时间：</label>
				<span class="ml10">
					<input id="contract_finish_time" name="contract_finish_time" type="text" class="easyui-datebox w120" ignore data-options="editable:false" readonly/>
				</span>
			</div>
			<div class="search_item">		
				<label>项目实际结束时间：</label>
				<span class="ml10">
					<input id="actual_finish_time" name="actual_finish_time" type="text" class="easyui-datebox w120" ignore data-options="editable:false" readonly/>
				</span>								
			</div>
			<div class="titleMessage">项目规模-Project Size</div>
			<div class="search_item">	
				<label>项目重量（吨重）</label>
				<span class="ml10"><input id="dry_weight" name="dry_weight" class="easyui-numberbox" ignore data-options="min:0.0000,precision:4,max:9999999999,editable:false"></span>
			</div>
			<div class="search_item">	
				<label>项目面积（面积）</label>
				<span class="ml10"><input id="project_area" name="project_area" class="easyui-numberbox" ignore data-options="min:0.0000,precision:4,max:9999999999,editable:false"></span>
			</div>
			<div class="titleMessage">图纸数量信息-Quantity Information of Drawing</div>
			<div class="search_item">	
				<label>结构专业</label>
				<span class="ml10"><input id="draw_quantity_st" name="draw_quantity_st" class="easyui-numberbox" data-options="min:0,max:9999999999"></span>
			</div>
			<div class="search_item">	
				<label>管线专业</label>
				<span class="ml10"><input id="draw_quantity_pi" name="draw_quantity_pi" class="easyui-numberbox" data-options="min:0,max:9999999999"></span>
			</div>
			<div class="search_item">	
				<label>电仪专业</label>
				<span class="ml10"><input id="draw_quantity_ei" name="draw_quantity_ei" class="easyui-numberbox" data-options="min:0,max:99999999999"></span>
			</div>
			<div class="search_item">	
				<label>空调专业</label>
				<span class="ml10"><input id="draw_quantity_hvac" name="draw_quantity_hvac" class="easyui-numberbox" data-options="min:0,max:9999999999"></span>
			</div>
			<div class="search_item">	
				<label>舾装专业</label>
				<span class="ml10"><input id="draw_quantity_ar" name="draw_quantity_ar" class="easyui-numberbox" data-options="min:0,max:9999999999"></span>
			</div>
			<div class="titleMessage">额定工时-Total Rated Man-hour</div>
			<div class="search_item">	
				<label>结构专业</label>
				<span class="ml10"><input id="rate_man_hour_st" name="rate_man_hour_st" class="easyui-numberbox" data-options="min:0.0,precision:1,max:9999999999"></span>
			</div>
			<div class="search_item">	
				<label>管线专业</label>
				<span class="ml10"><input id="rate_man_hour_pi" name="rate_man_hour_pi" class="easyui-numberbox" data-options="min:0.0,precision:1,max:9999999999"></span>
			</div>
			<div class="search_item">	
				<label>电仪专业</label>
				<span class="ml10"><input id="rate_man_hour_ei" name="rate_man_hour_ei" class="easyui-numberbox" data-options="min:0.0,precision:1,max:9999999999"></span>
			</div>
			<div class="search_item">	
				<label>空调专业</label>
				<span class="ml10"><input id="rate_man_hour_hvac" name="rate_man_hour_hvac" class="easyui-numberbox" data-options="min:0.0,precision:1,max:9999999999"></span>
			</div>
			<div class="search_item">	
				<label>舾装专业</label>
				<span class="ml10"><input id="rate_man_hour_ar" name="rate_man_hour_ar" class="easyui-numberbox" data-options="min:0.0,precision:1,max:9999999999"></span>
			</div>
			<div class="titleMessage">终端用户－Project Users</div>
			<div class="search_item">	
				<label>终端用户1</label>
				<span class="ml10"><input id="project_owner_1" name="project_owner_1" class="easyui-textbox" ignore data-options="editable:false" style="width:300px"/></span>
			</div>
			<div class="search_item">
				<label>终端用户2</label>
				<span class="ml10"><input id="project_owner_2" name="project_owner_2" class="easyui-textbox" ignore data-options="editable:false" style="width:300px"/></span>
			</div>
			<div class="titleMessage">Message－Project Users</div>
			<div class="search_item">
				<span class="ml10"><input id="confirm_user_name" name="confirm_user_name"
					class="easyui-textbox" data-options="label:'确认人:'" style="width: 200px" readonly ignore></span>
			</div>
			<div class="search_item">
				<span class="ml10"><input id="confirm_date" name="confirm_date"
					class="easyui-datebox" data-options="label:'确认时间:'" style="width: 200px" readonly ignore></span>
			</div>
			<div class="search_item" style="margin-top:15px;width:100%;text-align:center">
				<a href="javascript:saveProject()" class="easyui-linkbutton" iconCls="icon-save">保存</a>
				<a href="javascript:jumpPage('项目信息管理','${ctx}/login/showList.do?url=business/project/project')" class="easyui-linkbutton" iconCls="icon-back">返回</a>
			</div>
		</div>
	</form>

<script type="text/javascript">
	var id = "${id}";
	var data={};
	var projects={};
	var init=true;
	$(function(){
		if(isEmpty("${id}")){
			bindAction();
			$("#project_form").form("load",data);
		}else{
			$.post("${ctx}/project/findById.do",{"id":"${id}"},function(result){
				data = result;
				data.project_name_edit=data.job_no;
				bindAction();
				//图纸数量、工时输入框绑定onchange事件
		        $("#project_form").form("load",data);
	 	    });
		}
	});
	function saveProject(){
		var project = $("#project_name").combobox("getValue");
		if(isEmpty(project)){
			$.messager.show({
				title:"警告",
				msg:"请选择项目!"
			});
			return;
		}
		var ok=true;
		$("input[name^='draw_quantity']").each(function(){
			if(isEmpty($(this).val())||$(this).val()=="0"){
				ok=false;
			}
		});
		if(!ok){
			$.messager.show({
				title:"警告",
				msg:"图纸数量信息有空值或者0，请检查！"
			});
			return false;
		}
		$("input[name^='rate_man_hour']").each(function(){
			if(isEmpty($(this).val())||$(this).val()=="0.0"){
				ok=false;
			}
		});
		if(!ok){
			$.messager.show({
				title:"警告",
				msg:"额定工时信息有空值或者0，请检查！"
			});
			return false;
		}
		delete data.project_name_edit;
		delete data.role_users;
		$.ajax({
 			type:"post",
 			url:"${ctx}/project/save.do",
 			contentType : "application/json;charset=utf-8",
 			data:getData("project_form",data),
 			success:function(result){
 				handleReturn(result,function(){
 					jumpPage("项目管理","${ctx}/project/view.do?id="+result.id);
 				});
			}
	 	});
	}
	//项目选择框变化自动填充项目公共部分信息
	function projectChange(){
		if(init){
			return;
		}
		var project = $("#project_name").combobox("getValue");
		resetProjectInfo();
		resetProjectSJInfo();
		if(isEmpty(project)){
			return;
		}
		var flag;
		$.ajax({
			type:"post",
			async:false,
			url:"${ctx}/project/checkValidate.do",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify({"jobNo":project,"id":"${id}"}),
			success:function(result){
				flag=result;
			}
		});
		if(!flag){
			$.messager.show({
				title:"警告",
				msg:"该项目已录入!请重新选择!"
			});
			$("#project_name").combobox("setValue","");
			return;
		}
		fillProjectInfo(project);//填充项目信息
	}
	//重置项目信息
	function resetProjectInfo(){
		$("#project_contract_no").textbox("setValue",null);
		$("#job_no").textbox("setValue",null);
		$("#company").textbox("setValue",null);
		$("#contractor").textbox("setValue",null);
		$("#contract_type").textbox("setValue",null);
		$("#project_type").textbox("setValue",null);
		$("#design_type").textbox("setValue",null);
		$("#contract_start_time").datebox("setValue",null);
		$("#contract_finish_time").datebox("setValue",null);
		$("#actual_finish_time").datebox("setValue",null);
		$("#dry_weight").textbox("setValue",null);
		$("#project_area").textbox("setValue",null);
		$("#project_owner_1").textbox("setValue",null);
		$("#project_owner_2").textbox("setValue",null);
		$("#confirm_user_name").textbox("setValue",null);
		$("#confirm_date").datebox("setValue",null);
	}
	//重置图纸数量及工时
	function resetProjectSJInfo(){
		$("#draw_quantity_st").numberbox("setValue",null);
		$("#draw_quantity_pi").numberbox("setValue",null);
		$("#draw_quantity_ei").numberbox("setValue",null);
		$("#draw_quantity_hvac").numberbox("setValue",null);
		$("#draw_quantity_ar").numberbox("setValue",null);
		$("#rate_man_hour_st").numberbox("setValue",null);
		$("#rate_man_hour_pi").numberbox("setValue",null);
		$("#rate_man_hour_ei").numberbox("setValue",null);
		$("#rate_man_hour_hvac").numberbox("setValue",null);
		$("#rate_man_hour_ar").numberbox("setValue",null);
	}
	//检测是否选择了项目
	function checkProject(inputId){
		var project = $("#project_name").combobox("getValue");
		if(isEmpty(project)&&!init){
			$.messager.show({
				title:"警告",
				msg:"请先选择项目!"
			});
			resetProjectInfo();
			resetProjectSJInfo();
			return false;
		}
		return true;
	}
	//图纸数量、工时绑定onchange事件、
	function bindAction(){
		var objects=["draw_quantity_st","draw_quantity_pi","draw_quantity_ei","draw_quantity_hvac","draw_quantity_ar",
		             "rate_man_hour_st","rate_man_hour_pi","rate_man_hour_ei","rate_man_hour_hvac","rate_man_hour_ar"];
		for(var i=0;i<objects.length;i++){
			$("#"+objects[i]).numberbox({  
			    onChange: function(value) {
			    	if(!isEmpty(value)){
			    		checkProject(objects[i]);
			    	}
			    }
			});
		}
		$("#project_name").combobox({  
			onLoadSuccess: function(){
				init=false;
			},
		    onChange: function(newValue,oldValue) {
		    	if(!init){
		    		projectChange();
		    	}
		    }
		});
	}
	//填充项目信息
	function fillProjectInfo(project){
		$.ajax({
			type:"post",
			async:false,
			url:"${ctx}/project/findByNo.do",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify({"jobNo":project}),
			success:function(result){
				projects=result;
			}
		});
		$("#project_form").form("load",projects);
		resetProjectSJInfo();
	}
</script>
</body>
</html>
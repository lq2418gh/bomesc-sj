<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单查看页面</title>
<style type="text/css">
	 .titleMessage{
   		background:#99CCFF; 
   		color:#FFF;
   		margin:10px,10px,10xp,10px;
   }
   th{
   		text-align:left;
   }
   td{
   		text-align:left;
   		width:120px;
   }
   .view_table{
   		font-size:14px;
   }
   
</style>
</head>
<body>
	<div class="titleMessage">项目基本信息</div>
	<table class="view_table">
		<tr>
			<th>项目名称:</th>
			<td column="project_name"></td>
			<th>合同号:</th>
			<td column="project_contract_no"></td>
			<th>项目工作号:</th>
			<td column="job_no"></td>
			<th>项目签订公司:</th>
			<td column="company_name"></td><!-- 字典 -->
			<th>合同甲方:</th>
			<td column="contractor"></td>
		</tr>
		<tr>
			<th>合同类型:</th>
			<td column="contract_type_name"></td><!-- 字典 -->
			<th>项目类型:</th>
			<td column="project_type"></td>
			<th>项目分类:</th>
			<td column="project_category"></td>	
			<th>设计类型:</th>
			<td column="design_type_name"></td>	<!-- 字典 -->	
		</tr>
	</table>
	<div class="titleMessage">项目时间信息-Project time information</div>	
	<table class="view_table">	
		<tr>
			<th>项目合同开始时间:</th>
			<td column="contract_start_time"></td>
			<th>项目合同结束时间:</th>
			<td column="contract_finish_time"></td>	
			<th>项目实际结束时间:</th>
			<td column="actual_finish_time"></td>										
		</tr>
	</table>
	<div class="titleMessage">项目规模-Project Size</div>	
	<table class="view_table">		
		<tr>
			<th>项目重量（吨重）:</th>
			<td column="dry_weight" ></td>
			<th>项目面积（面积）:</th>
			<td column="project_area" ></td>	
		</tr>
	</table>
	<div class="titleMessage">图纸数量信息-Quantity Information of Drawing</div>	
	<table class="view_table" id="drawInfo">			
		<tr>
			<th>结构专业:</th>
			<td column="draw_quantity_st"></td>
			<th>管线专业:</th>
			<td column="draw_quantity_pi"></td>	
			<th>电仪专业:</th>
			<td column="draw_quantity_ei"></td>
			<th>空调专业:</th>
			<td column="draw_quantity_hvac"></td>
			<th>舾装专业:</th>
			<td column="draw_quantity_ar"></td>
		</tr>
	</table>
	<div class="titleMessage">额定工时-Total Rated Man-hour</div>	
	<table class="view_table" id="hourInfo">			
		<tr>
			<th>结构专业:</th>
			<td column="rate_man_hour_st"></td>
			<th>管线专业:</th>
			<td column="rate_man_hour_pi"></td>	
			<th>电仪专业:</th>
			<td column="rate_man_hour_ei"></td>
			<th>空调专业:</th>
			<td column="rate_man_hour_hvac"></td>
			<th>舾装专业:</th>
			<td column="rate_man_hour_ar"></td>
		</tr>				
	</table>
	<div class="titleMessage">终端用户－Project Users</div>
	<table class="view_table">			
		<tr>
			<th>终端用户1:</th>
			<td column="project_owner_1"></td>
			<th>终端用户2:</th>
			<td column="project_owner_2"></td>	
		</tr>
	</table>
	<div class="titleMessage">Message－Project Users</div>
	<table class="view_table">	
		<tr>
			<th>确认人:</th>
			<td column="confirm_user_name"></td>	
			<th>确认时间:</th>
			<td column="confirm_date"></td>									
		</tr>
	</table>
	<div class="search_button" style="margin-top:5px;width:100%;text-align:center">
			<a id="editBtn" href="javascript:editProject()"  class="easyui-linkbutton" iconCls="icon-edit" >修改</a><!-- ok -->
			<a id="deleteBtn" href="javascript:deleteProject()"  class="easyui-linkbutton" iconCls="icon-cancel" >删除</a><!-- ok -->
			<a href="javascript:jumpPage('项目管理','${ctx}/login/showList.do?url=business/project/project')" class="easyui-linkbutton" iconCls="icon-back">返回</a><!-- ok -->
		</div>
<script type="text/javascript">
	var data;
	$(function(){
		$.post("${ctx}/project/findById.do",{"id":"${id}"},function(result){
			data = result;
			if(data.confirm_user){
				data.confirm_user_name= data.confirm_user.name;
			}
	        loadData(data);
 	   });
	});
	function deleteProject(){
		$.messager.confirm("是否删除","准备执行数据删除操作，请确认或取消。",function(r){
		    if (r){
		    	$.post("${ctx}/project/deleteProject.do",{"id":data.id},function(result){
					handleReturn(result,function(){
						jumpPage("项目查询页面","${ctx}/login/showList.do?url=business/project/project");
					});
				});
		   	}
		});
	}
	function editProject(){
		jumpPage("新建项目","${ctx}/project/edit.do?id="+data.id);
	}
</script>
</body>
</html>
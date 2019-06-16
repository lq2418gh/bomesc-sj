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
	<form id="project_form" method="post" action="${ctx}/project/save.do" class="search_form dkd-search">
		<table class="view_table">
			<tr>
				<th>项目名称:</th>
				<td column="project_name"></td>
				<th>项目工作号:</th>
				<td column="job_no"></td>
				<th>专业名称:</th>
				<td column="major_name"></td><!-- 字典 -->
			</tr>
			<tr>
				<th>录入人:</th>
				<td column="entity_createuser"><input name="entity_createuser" class="easyui-textbox" style="width: 200px" readonly></td>
				<th>录入时间:</th>
				<td column="entity_createdate"><input  name="entity_createdate" class="easyui-textbox" style="width: 200px" readonly></td>
				<th>修改人:</th>
				<td column="entity_modifyuser"><input name="entity_modifyuser" class="easyui-textbox" style="width: 200px" readonly></td>
				<th>修改时间:</th>
				<td column="entity_modifydate"><input name="entity_modifydate" class="easyui-textbox" style="width: 200px" readonly></td>
			</tr>
		</table>
		<div style="margin-left:30%;width:60%;height:50%;float:left;overflow-y:auto;">
			<table class="view_table" >
				<thead>
					<tr>
						<th>列名</th>
						<th>显示名</th>
					</tr>
					<tr>
						<th>补充列1:</th>
						<td column="column1"></td>
					</tr>
					<tr>
						<th>补充列2:</th>
						<td column="column2"></td>					
					</tr>
					<tr>
						<th>补充列3:</th>
						<td column="column3"></td>
					</tr>
					<tr>
						<th>补充列4:</th>
						<td column="column4"></td>
					</tr>
					<tr>
						<th>补充列5:</th>
						<td column="column5"></td>
					</tr>
					<tr>
						<th>补充列6:</th>
						<td column="column6"></td>
					</tr>
					<tr>
						<th>补充列7:</th>
						<td column="column7"></td>
					</tr>
					<tr>
						<th>补充列8:</th>
						<td column="column8"></td>
					</tr>
					<tr>
						<th>补充列9:</th>
						<td column="column9"></td>
					</tr>
					<tr>
						<th>补充列10:</th>
						<td column="column10"></td>
					</tr>
					<tr>
						<th>补充列11:</th>
						<td column="column11"></td>
					</tr>
					<tr>
						<th>补充列12:</th>
						<td column="column12"></td>
					</tr>
					<tr>
						<th>补充列13:</th>
						<td column="column13"></td>
					</tr>
					<tr>
						<th>补充列14:</th>
						<td column="column14"></td>
					</tr>
					<tr>
						<th>补充列15:</th>
						<td column="column15"></td>
					</tr>
					<tr>
						<th>补充列16:</th>
						<td column="column16"></td>
					</tr>
					<tr>
						<th>补充列17:</th>
						<td column="column17"></td>
					</tr>
					<tr>
						<th>补充列18:</th>
						<td column="column18"></td>
					</tr>
					<tr>
						<th>补充列19:</th>
						<td column="column19"></td>
					</tr>
					<tr>
						<th>补充列20:</th>
						<td column="column20"></td>
					</tr>
				</thead>
			</table>
		</div>
		<div class="search_button" style="margin-top:50px;width:100%;text-align:center;float:left">
			<a href="javascript:update_data()" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
			<a href="javascript:close_data()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>	
		</div>
	</form>
<script type="text/javascript">
	var data;
	var state;
	$(function(){
		$.post("${ctx}/columnDisplaySet/view_load_data.do",{"id":"${id}"},function(result){
			data = result;
	        loadData(data);
 	   });
	});
	
	function update_data(){
		closeSelfWindow();
		showSelfWindow({
			closed:false,
  			title:"编辑页",
  			href:"${ctx}/columnDisplaySet/edit.do?id="+data.id,
  			width:'80%',
  			height:500
  		});
	}

	function close_data(){
		closeSelfWindow();
		jumpPage('补充列显示名称设置','${ctx}/login/showList.do?url=business/dataParamConfig/columnDisplaySet');
	}


	
</script>
</body>
</html>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>欢迎也</title>
</head>
<body>
	<div style="padding-left:10px;padding-bottom:10px">
		<table id="task_data" align="center">
			<thead>
				<tr>
					<th data-options="field:'flowset_name',width:200">审批类型名称</th>
					<th data-options="field:'name',width:200">节点名称</th>
					<th data-options="field:'work_no',width:300">单据号</th>
					<th data-options="field:'entity_createdate',width:300">提交日期</th>
					<th data-options="field:'_operate',width:180,formatter:formatTaskOper" >操作</th>
				</tr>
			</thead>
		</table>
		<div class="panel-header" style="margin-top:10px;">
			<div class="panel-title">您有
			<a id="myA" href="#" class="easyui-menubutton" menu="#menuProfessional" iconCls="icon-search" style="margin-top:-5px;">全部</a>
			<label id=allProfessional style="color:red;"></label>变更记录待确认！</div>
		</div>
		<div id="menuProfessional" style="width:150px;">
			<div id="ST" iconCls="icon-search" onclick="addTab('stDesignDataRemind','结构数据变更提醒','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackChange&module=remind')">结构专业</div>
			<div id="PI" iconCls="icon-search" onclick="addTab('piDesignDataRemind','管线数据变更提醒','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackChange&module=remind')">管线专业</div>
			<div id="EI" iconCls="icon-search" onclick="addTab('eiDesignDataRemind','电仪数据变更提醒','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackChange&module=remind')">电仪专业</div>
			<div id="HVAC" iconCls="icon-search" onclick="addTab('hvacDesignDataRemind','空调数据变更提醒','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackChange&module=remind')">空调专业</div>
			<div id="AR" iconCls="icon-search" onclick="addTab('arDesignDataRemind','舾装数据变更提醒','${ctx}/login/showList.do?url=business/designDataManager/stDataTrackChange&module=remind')">舾装专业</div>
		</div>
	</div>
	<script type="text/javascript">
	    var data,id,totalNum;
	 	$(function(){
	 		$("#task_data").datagrid({
	 			title:"待审任务",
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				fitColumns:true,
	            url:"${ctx}/login/task.do",
	            onLoadSuccess:function(data){
	            	$(".editButton").linkbutton();
				}
			});
	 	});
	 	findTotalTask();
	 	function findTotalTask(){
	 		$.ajax({
		 		type:"post",
		 		url:"${ctx}/designDataChange/findTotalTask.do",
		 		contentType:"application/json;charset=utf-8",
		 		async:false,
		 		success:function(result){
		 			totalNum=result;
		 		}
		 	});
		 	for(var i=0;i<totalNum.length;i++){
		 		$("#"+totalNum[i].professional).html($("#"+totalNum[i].professional).html()+"("+totalNum[i].num+"条)");
		 	}
	 	}
	 	function formatTaskOper(val,row,index){  
	 	    return "<a class=\"editButton m5\" href=\"javascript:void(0)\" iconCls=\"icon-edit\" onclick=\"viewBill('" + row.work_no + "','" + row.view_url + "')\">审批</a>";
	 	}
	 	function viewBill(work_no,view_url){
	 		addTab("viewBill","审批单据","${ctx}/" + view_url + work_no);
	 	}
	 </script>
</body>
</html>
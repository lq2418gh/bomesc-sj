<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>查看审核记录</title>
</head>
<body>
	<div class="datagrid_div">
		<table id="history_data" align="center">
			<thead>
				<tr>
					<th data-options="field:'name',width:100">审核名称</th>
					<th data-options="field:'role_name',width:120">角色名称</th>
					<th data-options="field:'check_user_name',width:100">审核人</th>
					<th data-options="field:'check_opinion',width:300">审核意见</th>
					<th data-options="field:'is_pass',width:100,formatter:formatResult">审核结果</th>
					<th data-options="field:'check_date',width:150">审核时间</th>
				</tr>
			</thead>
		</table>
	</div>
    <script type="text/javascript">
	 	$(function(){
	 		$("#history_data").datagrid({
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				fitColumns:true,
				queryParams:{"work_no":"${work_no}"},
	            url:"${ctx}/flowset/loadCheckHistoryData.do"
			});
	 	});
	 	function formatResult(val,row,index){
	 		if(row.is_check == "Y"){
	 			if(row.is_pass = "Y"){
	 				return "<span class='green'>通过</span>";
	 			}else{
	 				return "<span class='red'>退回</span>";
	 			}
	 		}else{
	 			return "<span class='red'>未审核</span>";
	 		}
	 	}
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>图纸明细管理</title>
</head>
<body>
	<!--查看页面 -->
	<table width="100%" class="view_table">
		<tr>
			<th>单据编码:</th>
			<td column="manager_no"></td>
			<th>项目名称:</th>
			<td column="project_name"></td>
			<th>项目工作号:</th>
			<td column="job_no"></td>
			<th>专业名称:</th>
			<td column="major_name"></td>
		</tr>
		<tr>
			<th>录入人:</th>
			<td column="entity_createuser"></td>
			<th>录入日期:</th>
			<td column="entity_createdate"></td>
			<th>修改人:</th>
			<td column="entity_modifyuser"></td>
			<th>修改日期:</th>
			<td column="entity_modifydate"></td>
		</tr>
		<tr>
			<td colspan="8" style="text-align: center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="editDataManager()">修改</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('数据负责人管理','${ctx}/login/showList.do?url=business/dataParamConfig/dataManager')">返回</a>
			</td>
		</tr>
	</table>
	<div style="padding-left:10px">
		<div class="titleMessage">数据负责人信息-Data manager information</div>
	    <table id="dataManager_detail">
			<thead>
				<tr>
					<th data-options="field:'job_number',width:200">人员编码</th>
					<th data-options="field:'manager_user_id',hidden:true">申购人</th>
					<th data-options="field:'manager_user_name',width:220">姓名</th>
					<th data-options="field:'department',width:200">部门</th>
					<th data-options="field:'email',width:200">邮箱</th>
					<th data-options="field:'export_fail',width:100,formatter:getValueByKey">导入失败提示</th>
					<th data-options="field:'lack_material',width:100,formatter:getValueByKey">缺料提示</th>
				</tr>
			</thead>
		</table>
    </div>
    <script type="text/javascript">
    var data,YN=[{"text":"是","value":"Y"},{"text":"否","value":"N"}];
 	$(function(){
 		$("#dataManager_detail").datagrid({
			rownumbers:true,
            fitColumns:true,
            onLoadSuccess:function(data){
            	$(".search_button").linkbutton();
            }
		});
		$.post("${ctx}/dataManager/findByNo.do",{"id":"${id}"},function(result){
			loadData(result);
			data = result;
	        $("#dataManager_detail").datagrid("loadData",{total:data.dataManagerHeads.length,rows:data.dataManagerHeads});
 	    });
 	});
 	function editDataManager(){
 		jumpPage("数据负责人信息编辑", "${ctx}/dataManager/edit.do?id="+data.id);
 	}
 	function getValueByKey(key) {
    	for(var i=0;i<YN.length;i++){
    		if(YN[i].value==key){
    			return YN[i].text;
    		}
    	}
        return "";
    }
</script>
</body>
</html>
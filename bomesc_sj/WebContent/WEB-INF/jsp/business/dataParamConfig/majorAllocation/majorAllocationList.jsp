<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>多专业公用优先级配置</title>
</head>
<body>
	<table width="100%" class="view_table" align="center">
		<tr>
			<td>录入人:</td>
			<td id="entity_createuser_view"></td>
			<td>录入日期:</td>
			<td id="entity_createdate_view"></td>
			<td>修改日期:</td>
			<td id="entity_modifydate_view"></td>
			<td>修改人:</td>
			<td id="entity_modifyuser_view"></td>
		</tr>
		<tr>
			<td colspan="8" style="text-align: center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit()">修改</a>
				<a href="javascript:backup()" class="easyui-linkbutton" iconCls="icon-cancel">返回</a>
			</td>
		</tr>
	</table>
	<div style="padding-left:10px;margin-left: 300px">
	    <table id="allocation_data" align="center" width="400">
			<thead>
				<tr>
					<th data-options="field:'name',width:200,formatter:forA" >专业</th>
					<th data-options="field:'major_no',width:200,formatter:forB">优先级顺序</th>
				</tr>
			</thead>
		</table>
    </div>
    <script type="text/javascript">
    $(function(){
    	$.ajax({
    		type:"post",
    		data:null,
    		url:"${ctx}/majorAllocation/view.do",
    		success:function(result){
    			$("#entity_createuser_view").text(result.rows[0].entity_createuser);
    			$("#entity_createdate_view").text(result.rows[0].entity_createdate);
    			$("#entity_modifydate_view").text(result.rows[0].entity_modifydate);
    			$("#entity_modifyuser_view").text(result.rows[0].entity_modifyuser);
    			loadDataList(result);//---------
    		}
    	})
	});
    //---------
    function loadDataList(result){
    	$("#allocation_data").datagrid({
			singleSelect:true,
			data:result
		});
    }
    //专业
    function forA(value,row,index){
		return row.name;
	}
   //优先级顺序
    function forB(value,row,index){  
	   if(isEmpty(row.sort_no)){
		    return value;
	   }else{
		    row.major_no=row.sort_no;
	        return row.major_no; 
	   }
     }  
   
   function edit(){
	   jumpPage("多专业公用优先级编辑页", "${ctx}/majorAllocation/edit.do");
   }
	function backup(){
	  jumpPage("多专业公用优先级配置", "${ctx}/login/showList.do?url=business/dataParamConfig/majorAllocation");
	}
    </script>
</body>
</html>
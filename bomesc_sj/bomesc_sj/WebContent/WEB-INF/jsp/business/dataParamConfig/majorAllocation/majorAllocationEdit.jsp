<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑页面</title>
</head>
<body>
	<form id="majorAllocation_form" method="post"  >
		<div class="search_div" >
			<div class="search_item">
				<label>&nbsp;&nbsp;&nbsp;&nbsp;录入人</label>
				<span class="ml10"><input id="entity_createuser_edit" name="entity_createuser" class="easyui-textbox w200" readonly></span>
			</div>
			
			<div class="search_item">
				<label>&nbsp;录入日期</label>
				<span class="ml10"><input id="entity_createdate_edit" name="entity_createdate" class="easyui-datebox w200" readonly></span>
			</div>
			<div class="search_item">
				<label>&nbsp;&nbsp;&nbsp;&nbsp;修改人</label>
				<span class="ml10"><input id="entity_modifyuser_edit" name="entity_modifyuser" class="easyui-textbox w200" readonly></span>
			</div>
			
			<div class="search_item">
				<label>&nbsp;修改日期</label>
				<span class="ml10"><input id="entity_modifydate_edit" name="entity_modifydate" class="easyui-datebox w200" readonly></span>
			</div>
		</div>
		<div class="search_item" style="margin-top:50px;width:100%;text-align:center;float:left">
				<a href="javascript:save()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
				<a href="javascript:backup()" class="easyui-linkbutton" iconCls="icon-cancel">返回</a>		
		</div>
		<div class="search_item" style="margin-left:30%;width:50%;height:50%;float:left;">
		 	 <form id="majorAllocationList_form" action="${ctx}/majorAllocation/save.do">
				<table id="table_data"  title="" style="width:400">
					<thead>
						<tr>
							<th data-options="field:'major',hidden:'true',formatter:forA"></th>
							<th data-options="field:'name',width:200" >专业</th>
							<th data-options="field:'major_no',width:200,formatter:forB">优先级顺序</th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
	</form>
	<script type="text/javascript">
   data={};
	$(function(){
		loadApplication();
	})
	              
	function loadApplication(){
		$.ajax({
    		type:"post",
    		data:null,
    		url:"${ctx}/majorAllocation/view.do",
    		success:function(result){
    			$("#entity_createuser_edit").textbox("setValue",result.rows[0].entity_createuser);
    			$("#entity_createdate_edit").textbox("setValue",result.rows[0].entity_createdate);
    			$("#entity_modifydate_edit").textbox("setValue",result.rows[0].entity_modifydate);
    			$("#entity_modifyuser_edit").textbox("setValue",result.rows[0].entity_modifyuser);
    			loadDataList(result);
    		}
    	})
	}
	function loadDataList(result){
    	$("#table_data").datagrid({
			singleSelect:true,
			data:result,
			toolbar: [{
	    		iconCls: 'icon-add',
	    		text: '上移',
	    		handler: function () {
	    	        up_move();
	    	    }
	    	},'-',{
	    		iconCls: 'icon-remove',
	    		text: '下移',
	    		handler: function () {
	    	        down_move();
	    	    }
	    	}]
		});
	 }
	
    //专业
    function forA(value,row,index){
    	 if(isEmpty(row.major)){
	    	row.major=row.id;
			return row.major;
    	}else{
    		row.major = row.major;
    		return row.major;
    	} 
	}
   //优先级顺序
    function forB(value,row,index){  
        row.major_no= parseInt(index+1);  
        return  row.major_no;
     } 
   //保存
	function save(){
		var rows=$('#table_data').datagrid('getRows'); 
		var details = getDataBase("majorAllocationList_form",rows);
		for(var i=0;i<details.length;i++){
				delete details[i].name;
			if(details[i].code){
				details[i].major={"id":details[i].id};
				delete details[i].sort_no;
				delete details[i].code;
				delete details[i].id;
			}else{
				details[i].major={"id":details[i].major};
			}
		}
		$.ajax({
 			type:"post",
 			url:"${ctx}/majorAllocation/save.do",
 			contentType:"application/json;charset=utf-8",
 			data:JSON.stringify(details),
 			success:function(result){
 				handleReturn(result,function(){
 					jumpPage("多专业公用优先级配置", "${ctx}/login/showList.do?url=business/dataParamConfig/majorAllocation");
 				});
 			}
 		});
		
	 }
   
   
   
   
   
	//上移
	function up_move(){
		var old_index = "";
		var rows=$('#table_data').datagrid('getRows');  
		var rowlength=rows.length; 
		var selectrow=$('#table_data').datagrid('getSelected');  
		var rowIndex=$('#table_data').datagrid('getRowIndex', selectrow);
		old_index = rowIndex;
		if(rowIndex==-1){
	        $.messager.alert('提示', '请选中一行!', 'warning');  
	        return;
		}
		if(rowIndex==0){  
	        $.messager.alert('提示', '顶行无法上移!', 'warning');  
	        return;
	    }else{ 
	     	var upperIndex=parseInt(rowIndex-1);
	     	var upperRow = rows[upperIndex];//上面的行数据       
	        $('#table_data ').datagrid('deleteRow', rowIndex);//删除一行  
	        $('#table_data ').datagrid('deleteRow', upperIndex);//删除上一行  	       
	        $('#table_data').datagrid('insertRow', {  
	            index:upperIndex,  
	            row:selectrow  
	        });
	        $('#table_data').datagrid('insertRow', {  
	            index:old_index,  
	            row: upperRow
	        }); 
	    }  
		$('#table_data').datagrid('selectRow', upperIndex); 
	}
	//下移
	function down_move(){
		var rows=$('#table_data').datagrid('getRows');  
		var rowlength=rows.length; 
		var selectrow=$('#table_data').datagrid('getSelected');  
		var rowIndex=$('#table_data').datagrid('getRowIndex', selectrow); 
		if(rowIndex==-1){  
	        $.messager.alert('提示', '请选中一行!', 'warning');  
	        return;
	    } 
		if(rowIndex==rowlength-1){  
	        $.messager.alert('提示', '底行无法下移!', 'warning');  
	        return;
	    }else{  
	    	var lowerIndex=parseInt(rowIndex+1);
	     	var lowerRow = rows[lowerIndex];//下面的行数据       
	        $('#table_data ').datagrid('deleteRow', lowerIndex);//删除下一行  
	        $('#table_data ').datagrid('deleteRow', rowIndex);//删除一行  
	        $('#table_data').datagrid('insertRow', {  
	            index:rowIndex,  
	            row:lowerRow
	        }); 
	        $('#table_data').datagrid('insertRow', {  
	            index:lowerIndex,  
	            row:selectrow  
	        });
	    }  
		  $('#table_data').datagrid('selectRow', lowerIndex);  
	}
	
	function backup(){
	  jumpPage("多专业公用优先级配置", "${ctx}/login/showList.do?url=business/dataParamConfig/majorAllocation");
	}
	</script>
</body>
</html>
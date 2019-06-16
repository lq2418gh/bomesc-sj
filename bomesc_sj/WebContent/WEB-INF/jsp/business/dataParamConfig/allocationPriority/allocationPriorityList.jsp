<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>物资分配优先级查询</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div>
				<div class="search_item">
					<label class="w80">单据编码:</label>
					<span class="ml10">
						<input id="rule_no_search" type="text" class="easyui-textbox w240" dkd-search-element="rule_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">专业名称:</label>
					<span class="ml10">
						<input id="major_search" name="major" size="13" class="easyui-combobox" data-options="width:240,panelHeight:'auto',editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'" dkd-search-element="major = text"/>
					</span>
				</div>
				<div class="search_item">
					<label >项目工作号:</label>
					<span class="ml15">
						<input id="job_no_search" type="text" class="easyui-textbox w220" dkd-search-element="job_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">项目名称:</label>
					<span class="ml10">
						<input id="project_name_search" name="project" class="edit_combobox" data-options="width:240,panelHeight:'auto',url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">录入日期:</label>
					<span class="ml10">
						<input id="entity_createdate_search" type="text" class="easyui-datebox w100" data-options="editable:false" dkd-search-element="entity_createdate >= text"/>
					</span>
					<label class="w80">至</label>
					<span class="ml10">
						<input id="entity_createdate_search" type="text" class="easyui-datebox w100" data-options="editable:false" dkd-search-element="entity_createdate <= text"/>
					</span>
				</div>
				<div class="search_item">
					<label >&nbsp;&nbsp;&nbsp;&nbsp;录入人:</label>
					<span class="ml15">
						<input id="entity_createuser_search" type="text" class="easyui-textbox w240" dkd-search-element="entity_createuser like text"/>
					</span>
				</div>
				<div align="center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="edit()">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit('type')">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeAllocationPriority()">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchAllocationPriority()">查询</a>
					<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
				</div>
			</div>
		</div>
	</form>
	<div style="padding-left:10px;overflow:auto">
		<table id="allocationPriority_data" align="center" style="width:100%">
			<thead>
				<tr>
					<th data-options="field:'rule_no',width:400,halign:'center'" >单据编码</th>
					<th data-options="field:'major_name',width:300,halign:'center'" >专业名称</th>
					<th data-options="field:'project_name',width:300,halign:'center'" >项目名称</th>
					<th data-options="field:'job_no',width:300,halign:'center'" >项目工作号</th>
					<th data-options="field:'entity_createuser',width:300,halign:'center'" >录入人</th>
					<th data-options="field:'entity_createdate',width:300,halign:'center',formatter:for_entity_createdate" >录入日期</th>
					<th data-options="field:'sortColumns',width:600,halign:'center',formatter:forSortColumns" >分配规则</th>
				</tr>
			</thead>
		</table>
	</div>
	<!--查看页面 -->
	<div id="allocationPriority_view" class="easyui-dialog pop" style="width:800;height:500;top:100px;padding:10px 20px;overflow:auto;" closed="true" buttons="#dlg-auth-buttons" maximizable=true modal=true>
		<table width="100%" class="view_table">
			<tr>
				<td>单据编码:</td>
				<td column="rule_no"></td>
				<td>项目名称:</td>
				<td column="project_name"></td>
				<td>项目工作号:</td>
				<td column="job_no"></td>
				<td>专业名称:</td>
				<td column="major_name"></td>
			</tr>
			<tr>
				<td>录入人:</td>
				<td column="entity_createuser"></td>
				<td>录入日期:</td>
				<td column="entity_createdate"></td>
				<td>修改日期:</td>
				<td column="entity_modifydate"></td>
				<td>修改人:</td>
				<td column="entity_modifyuser"></td>
			</tr>
			<tr>
				<td colspan="8" style="text-align: center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="edit('type')">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('物资分配优先级','${ctx}/login/showList.do?url=business/dataParamConfig/allocationPriority')">返回</a>
				</td>
			</tr>
		</table>
		<div style="padding-left:10px">
			<div class="titleMessage">优先级顺序</div>
		    <table id="allocation_data" align="center" width="400">
				<thead>
					<tr>
						<th data-options="field:'sort_column_name',width:200,formatter:forA" >物资项目配置</th>
						<th data-options="field:'sort_no',width:200,formatter:forB">优先级顺序</th>
					</tr>
				</thead>
			</table>
	    </div>
	</div>
	<!-- 添加编辑页面 -->
	<div id="allocationPriority_add" class="easyui-dialog pop" style="width:800;height:500;top:100px;padding:10px 20px;overflow:auto;" closed="true" buttons="#dlg-auth-buttons" maximizable=true modal=true>
		<form id="allocationPriority_form" method="post" action="${ctx}/allocationPriority/save.do" >
			<div class="search_div" >
				<div class="search_item">
					<label class="w80">&nbsp;&nbsp;单据编号:</label>
					<span class="ml10">
						<input id="rule_no_edit" name="rule_no" size="13" class="easyui-textbox w200" data-options="editable:false" >
					</span>
				</div>
				<div class="search_item">
					<label class="w80">项目名称:</label>
					<span class="ml10">
						<input id="project_edit" name="project" class="edit_combobox" data-options="width:200,url:'${ctx}/project/show.do'" />
						<input type="hidden" id="project_name_edit" name="project_name" value="" />
					</span>
				</div>
				<div class="search_item">
					<label>项目工作号</label>
					<span class="ml10"><input id="job_no_edit" name="job_no" class="easyui-textbox w200" readonly ></span>
				</div>
				<div class="search_item">
					<label class="w80">专业名称:</label>
					<span class="ml10">
						<select id="major_edit" name="major" is-object class="easyui-combobox" data-options="width:200,panelHeight:'auto',required:true,editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'"></select>
					</span>
				</div>
				<div class="search_item">
					<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录入人</label>
					<span class="ml10"><input id="entity_createuser_edit" is-object name="entity_createuser" class="easyui-textbox w200" readonly></span>
				</div>
				
				<div class="search_item">
					<label>&nbsp;录入日期</label>
					<span class="ml10"><input id="entity_createdate_edit" name="entity_createdate" class="easyui-datetimebox w200" readonly></span>
				</div>
				<div class="search_item" style="margin-top:50px;width:100%;text-align:center;float:left">
					<a href="javascript:save()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('物资分配优先级','${ctx}/login/showList.do?url=business/dataParamConfig/allocationPriority')">返回</a>
				</div>
				<div class="search_item" style="margin-left:150;width:400;height:216;float:left;overflow-y:auto;">
					<table id="table_data"  title="" >
						<thead>
							<tr>
								<th data-options="field:'sort_column',hidden:true,formatter:for_sort_column"></th>
								<th data-options="field:'sort_column_name',title: '优先级配置项目',width:200,formatter:for_sort_column_name"></th>
								<th data-options="field:'sort_no',width:200,title: '优先级顺序',formatter:for_sort_no"></th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</form>
	</div>
<script type="text/javascript">
	//初始化方法
	var currentRow,init = true,data={};
	$(function(){
		$("#allocationPriority_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			fitColumns:true,
			queryParams: {          
                "sort":"entity_createdate",
                "order":"desc"
            },
	        url:"${ctx}/allocationPriority/query.do",
	        onClickRow:function(rowIndex, rowData){
	        	currentRow = rowData;
	        },
	        onDblClickRow:function(index,data){
				findData(data.id);
	        },
	        onLoadSuccess:function(data){
	        	currentRow="";
			}
		});
	});
	$('#allocationPriority_view').dialog({  
        onClose:function(){  
			jumpPage('物资分配优先级','${ctx}/login/showList.do?url=business/dataParamConfig/allocationPriority');
        }  
    });
	$('#allocationPriority_add').dialog({  
        onClose:function(){  
			jumpPage('物资分配优先级','${ctx}/login/showList.do?url=business/dataParamConfig/allocationPriority');
        }  
    });
	//1选择项目名称 onchange事件
   	$("#project_edit").combobox({
   		onChange: function (newValue, oldValue) {
   			fillProjectNo();
   			checkIsData();
   		}
   	});
	//2选择专业名称 onchange事件
	$("#major_edit").combobox({
		onChange: function (newValue, oldValue) {
			if(!isEmpty(oldValue)){
				init=false;
			}
			if(!init){
				var flag=checkIsData();
				findallcationPriority();
				init=false;
			}
		}
	});
	
	/**----查询页面开始-----------------------------------------------------------------**/
	//按钮--查询	
	function searchAllocationPriority(){
		$("#allocationPriority_data").datagrid("reload",mosaicParams({"sort":"entity_createdate","order":"desc"}));
	}
	//按钮--删除
	function removeAllocationPriority(){
		if(isEmpty(currentRow)){
 			$.messager.show({
				title:"警告",
				msg:"请选择一行数据"
			});
 			return false;
	 	}else{
			$.messager.confirm("是否删除","准备执行数据删除操作，请确认或取消。",function(r){
				if (r){
					$.post("${ctx}/allocationPriority/delete.do",{"id":currentRow.id},function(result){
						handleReturn(result,function(){
							$("#allocationPriority_data").datagrid("reload");
						});
					});
				}
			});
		}
	}
	//-----规则----
	function forSortColumns(value,row,index){
		var sortColumns = "";
		var objdetail=row.allocationPriorityDetail;
		if(objdetail.length > 0){
			for(var i=0;i<objdetail.length;i++){
				sortColumns+=objdetail[i].sort_column_name;
				if(i != objdetail.length - 1){
					sortColumns += "/";
				}
			}
		}
		row.sortColumns = sortColumns;
		return row.sortColumns;		
	}
	//处理时间格式
	function for_entity_createdate(value,row,index){
		var ruleNo=row.entity_createdate;
		row.entity_createdate=ruleNo.substr(0,10);
		return row.entity_createdate;
	}
	/**----查询页面结束-----------------------------------------------------------------**/
	
	
	
	/**-------------------------------------编辑添加页面开始--------------------------------**/
	//---点击项目名称改变项目工作号---
	function fillProjectNo(){
		$("#job_no_edit").textbox("setValue",$("#project_edit").combobox("getValue"));
 		$("#project_name_edit").val($("#project_edit").combobox("getText"));
	}
	//------点击专业改变的明细---------
	function findallcationPriority(){
		var major = $("#major_edit").combobox("getValue");//专业名称
		if(isEmpty(major)){
			return false;
		}else{
			freshTableData(major);
		}
	}
	//根据专业查询字典
	function freshTableData(major){
		$.ajax({
 			url:"${ctx}/dictionary/selectDictionaryByMajor.do?major="+major,
 			contentType : "application/json;charset=utf-8",
 			success:function(result){
 				if(result){
 					loadDataListedit(result);
 				}
			}
	 	});
	}
	
	//明细列表查询
	function loadDataListedit(data_edit){
		$('#table_data').datagrid({
	        singleSelect: true,
	        data:data_edit,
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
	
	
	//-------明细列表formatter方法开始-------
	function for_sort_column(value,row,index){
		if(!isEmpty(row.code)){
 			row.sort_column=row.id;
 			return row.sort_column;
 		}else{
 			$.post("${ctx}/allocationPriority/findDicId.do",{"id":row.id},function(result){
 				row.sort_column=result.id;
 			});
    		return row.sort_column;
        }
	}
	function for_sort_column_name(value,row,index){
		if(row.sort_column_name==undefined){
			row.sort_column_name = row.name;
    		return row.sort_column_name;
		}else{
			return value;
		}
	}
	function for_sort_no(value,row,index){
		if(row.sort_no){
			row.sort_no=parseInt(index+1);
			return  row.sort_no;
		}else{
			row.sort_no = parseInt(index+1);
    		return  row.sort_no;
		}
	}
	//---------明细列表formatter方法结束----------
	

	//校验
	function checkIsData(){
		var major=$("#major_edit").combobox("getValue");
		var project=$("#project_edit").combobox("getValue");
		if(isEmpty(project)||isEmpty(major)){
			return false;
		}else{
			$.ajax({
	 			url:"${ctx}/allocationPriority/checkIsData.do",//校验同一个项目同一个专业不能重复
	 			contentType : "application/json;charset=utf-8",
	 			data:JSON.stringify({"project":project,"major":major,"id":currentRow.id}),
	 			success:function(result){
	 				if(result.success){
	 					$.messager.show({
	 						title:"警告",
	 						msg:"已存在相同项目、相同专业的单据，请检查！"
	 					});
	 					$("#major_edit").combobox("reset");
	 					return "Y";	//FALSE
	 				}else{
	 					return "N";	
	 				}
	 			}
			});
		}
	}
	//回显
	var dataEdit={};
	function edit(type){
		if(isEmpty(type)){
			$("#allocationPriority_add").dialog("open").dialog("setTitle","物资分配优先级编辑");
	        $("#allocationPriority_form").form("load",{});
			init=false;
		}else{
			if(isEmpty(currentRow)){
 	 			$.messager.show({
 					title:"警告",
 					msg:"请选择一行数据"
 				});
 	 			return;
 	 		}
 			var id = currentRow.id;
 			$.post("${ctx}/allocationPriority/edit_load_data.do",{"id":id},function(result){
 				dataEdit = result;
 				$("#allocationPriority_add").dialog("open").dialog("setTitle","物资分配优先级编辑");
				$("#allocationPriority_form").form("load",dataEdit);
		        $('#project_edit').combobox('setValue',dataEdit.job_no);
		        $('#project_name_edit').val(dataEdit.project_name);
		        loadDataListedit(dataEdit.allocationPriorityDetail);
	 	   });
		}
	}
	

	//保存
	function save(){
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
		var major=$("#major_edit").combobox("getValue");
		if(isEmpty(major)){
			$.messager.show({
				title:"警告",
				msg:"请选择专业！"
			});
			return;
		}
		var rows=$('#table_data').datagrid('getRows'); 
		var details = getDataBase("allocationPriority_form",dataEdit,"allocationPriorityDetail",rows)
		for(var i=0;i<details.allocationPriorityDetail.length;i++){
			details.allocationPriorityDetail[i].sort_column={"id":details.allocationPriorityDetail[i].sort_column};
			delete details.allocationPriorityDetail[i].id;
			delete details.allocationPriorityDetail[i].sort_column_name;
			delete details.allocationPriorityDetail[i].name;
			delete details.allocationPriorityDetail[i].code;
		}
		$.ajax({
 			url:"${ctx}/allocationPriority/save.do",
 			contentType:"application/json;charset=utf-8",
 			data:JSON.stringify(details),
 			success:function(result){
 				handleReturn(result,function(){
 					jumpPage('物资分配优先级','${ctx}/login/showList.do?url=business/dataParamConfig/allocationPriority');
				});
			}
 		});
	}
	
	/**-------------------------------------编辑添加页面结束--------------------------------**/
	
	

	
	
	/**---------------------------------------------------------------------------查看开始---**/
	var dataview;
	function findData(id){
		$.post("${ctx}/allocationPriority/edit_load_data.do",{"id":id},function(result){
			$("#allocationPriority_view").dialog("open").dialog("setTitle","物资优先级查看页");
			dataview = result;
	        loadData(dataview);
	        loadDetailListView(dataview.allocationPriorityDetail);
 	   });
	}
	function loadDetailListView(allocationPriorityDetailList){
		$("#allocation_data").datagrid({
			singleSelect:true,
			data:allocationPriorityDetailList
		});
	}
	function forA(value,row,index){
		return row.sort_column_name;
	}
	function forB(value,row,index){
		return row.sort_no;
	}
	/**---------------------------------------------------------------------------查看结束---**/
	
	
	//编辑页面上移
	function up_move(){
		var old_index = "";
		var rows=$('#table_data').datagrid('getRows');  
		var rowlength=rows.length; 
		var selectrow=$('#table_data').datagrid('getSelected');  
		var rowIndex=$('#table_data').datagrid('getRowIndex', selectrow);
		old_index = rowIndex;
		if(rowIndex==-1){
			$.messager.alert('提示', '请选中一行!', 'warning')
			return;
		}
		if(rowIndex==0){  
	        $.messager.alert('提示', '顶行无法上移!', 'warning');
	        return;
	    }else{ 
	     	var upperIndex=parseInt(rowIndex-1);
	     	var upperRow = rows[upperIndex];//上面的行数据       
	        $('#table_data').datagrid('deleteRow', rowIndex);//删除一行  
	        $('#table_data').datagrid('deleteRow', upperIndex);//删除上一行  	       
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
	//编辑页面下移
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
</script>
</body>
</html>
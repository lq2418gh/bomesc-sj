<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>项目工时查询</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div>
				<div class="search_item">
					<label class="w80">单据编码:</label>
					<span class="ml10">
						<input id="hour_no_search" type="text" class="easyui-textbox w250" dkd-search-element="hour_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label >项目工作号:</label>
					<span class="ml15">
						<input id="job_no_search" type="text" class="easyui-textbox w240" dkd-search-element="job_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">专业名称:</label>
					<span class="ml10">
						<input id="major_search" name="major" size="13" class="easyui-combobox" data-options="width:180,panelHeight:'auto',editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'" dkd-search-element="major = text"/>
					</span>
				</div>

				<div class="search_item">
					<label class="w80">项目名称:</label>
					<span class="ml10">
						<input id="project_name_search" name="project" class="edit_combobox" data-options="width:250,panelHeight:'auto',url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">&nbsp;&nbsp;&nbsp;&nbsp;统计月份:</label>
					<span class="ml10">
						<input id="statistical_month_search" type="text" class="datebox_month w100" data-options="editable:false" dkd-search-element="statistical_month >= text"/>
					</span>
					<label class="ml10">至</label>
					<span class="ml10">
						<input id="statistical_month_search" type="text" class="datebox_month w100" data-options="editable:false" dkd-search-element="statistical_month <= text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">&nbsp;&nbsp;&nbsp;录入人:</label>
					<span class="ml15">
						<input id="entity_createuser_search" type="text" class="easyui-textbox w190" dkd-search-element="entity_createuser like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">录入日期:</label>
					<span class="ml10">
						<input id="entity_createdate_search" type="text" class="easyui-datebox w100"  data-options="editable:false" dkd-search-element="entity_createdate >= text"/>
					</span>
					<label class="w110">至</label>
					<span class="ml10">
						<input id="entity_createdate_search" type="text" class="easyui-datebox w100"  data-options="editable:false" dkd-search-element="entity_createdate <= text"/>
					</span>
				</div>
				<div align="center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="editWorkHours()">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="editWorkHours('edit')">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeWorkHours()">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchWorkHours()">查询</a>
					<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
				</div>
			</div>
		</div>
	</form>
	<div style="padding-left:10px;overflow:auto">
		<table id="workHours_data" align="center" style="width:100%">
			<thead>
				<tr>
					<th data-options="field:'hour_no',width:400,halign:'center'" >单据编码</th>
					<th data-options="field:'statistical_month',width:300,halign:'center'" >统计月份</th>
					<th data-options="field:'major_name',width:300,halign:'center'" >专业名称</th>
					<th data-options="field:'project_name',width:300,halign:'center'" >项目名称</th>
					<th data-options="field:'job_no',width:300,halign:'center'" >项目工作号</th>
					<th data-options="field:'total_rate_man_hour',width:300,halign:'center'" >项目额定工时</th>
					<th data-options="field:'forecast_man_hour',width:300,halign:'center'" >本月计划工时</th>
					<th data-options="field:'actual_man_hour',width:300,halign:'center'" >本月实际工时</th>
					<th data-options="field:'cumulative_man_hour',width:300,halign:'center'" >项目累计工时</th>
					<th data-options="field:'entity_createuser',width:300,halign:'center'" >录入人</th>
					<th data-options="field:'entity_createdate',width:300,halign:'center'" >录入日期</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="workHours_add" class="easyui-dialog pop" style="width:800;height:400;top:100px;padding:10px 20px;overflow:auto;" closed="true" buttons="#dlg-auth-buttons" maximizable=true modal=true>
		<form id="workHours_form" method="post" action="${ctx}/workHours/save.do">
			<table class="lines-both">
				<tr>
					<td>单据编码:</td>
					<td><input id="hour_no_edit" name="hour_no" size="13" class="easyui-textbox" readonly="readonly"/></td>
					<td>项目名称:</td>
					<td>
						<input id="project_edit" name="project" class="edit_combobox" data-options="width:130,url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
						<input type="hidden" id="project_name_edit" name="project_name" value="" />
					</td>
					<td>专业名称:</td>
					<td><select id="major_edit" name="major" is-object class="easyui-combobox" data-options="required:true,width:130,panelHeight:'auto',editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=hour_profession'"></select></td>
				</tr>
				<tr>
					<td>统计月份:</td>
					<td><input id="statistical_month_edit" name="statistical_month" size="13" class="datebox_month" data-options="required:true" /></td>
					<td>项目工作号:</td>
					<td><input id="job_no_edit" name="job_no" size="13" class="easyui-validatebox textbox" readonly="readonly"/></td>
					<td>录入人:</td>
					<td><input id="entity_createuser_edit" name="entity_createuser" size="13" class="easyui-textbox" readonly="readonly"/></td>
				</tr>
				<tr>
					<td>录入日期:</td>
					<td><input id="entity_createdate_edit" name="entity_createdate" size="13" class="easyui-datebox" readonly="readonly"/></td>
					<td colspan="4"></td>
				<tr>
				
				<tr>
					<td colspan="6">
						<div class="titleMessage">项目工时-Progect Man-hour</div>
					</td>
				</tr>
				<tr>
					<td>项目额定工时:</td>
					<td>
						<input id="total_rate_man_hour_edit" name="total_rate_man_hour" size="13" class="easyui-numberbox" data-options="min:0.0,precision:1,max:9999999999" readonly="readonly" />
					</td>
					<td>本月计划工时:</td>
					<td>
						<input id="forecast_man_hour_edit" name="forecast_man_hour" size="13" class="easyui-numberbox" data-options="min:0.0,precision:1,max:9999999999,required:true" data-options="required:true"/>
					</td>
					<td>本月变更工时:</td>
					<td>
						<input id="change_man_hour_edit" name="change_man_hour" size="13" class="easyui-numberbox" value="0" readonly="readonly" data-options="min:0.0,precision:1,max:9999999999,required:true"/>
					</td>
				</tr>
				<tr>	
					<td>本月实际工时:</td>
					<td>
						<input id="actual_man_hour_edit" name="actual_man_hour" size="13" class="easyui-numberbox" data-options="min:0.0,precision:1,max:9999999999,required:true,required:true"/>
					</td>
					<td>项目累计工时:</td>
					<td>
						<input id="cumulative_man_hour_edit" name="cumulative_man_hour" size="13" class="easyui-numberbox" data-options="min:0.0,precision:1,max:9999999999,required:true,required:true" readonly="readonly"/>
					</td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveWorkHours()">保存</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('项目工时维护','${ctx}/login/showList.do?url=business/project/workHours')">返回</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="workHours_view" class="easyui-dialog pop" style="width:800;height:300;top:100px;padding:10px 20px;overflow:auto;" closed="true" buttons="#dlg-auth-buttons" maximizable=true modal=true>
		<form id="workHours_form_view" >
			<table class="view_table">
				<tr>
					<td>单据编码:</td>
					<td column="hour_no"></td>
					<td>项目名称:</td>
					<td column="project_name" ></td>
					<td>专业名称:</td>
					<td column="major_name" ></td>
				</tr>
				<tr>
					<td>统计月份:</td>
					<td column="statistical_month"></td>
					<td>项目工作号:</td>
					<td column="job_no"></td>
					<td>录入人:</td>
					<td column="entity_createuser"></td>
				</tr>
				<tr>
					<td>录入日期:</td>
					<td column="entity_createdate"></td>
					<td>修改日期:</td>
					<td column="entity_modifydate"></td>
					<td>修改人:</td>
					<td column="entity_modifyuser"></td>
				</tr>
				<tr>
					<td colspan="6">
						<div class="titleMessage">项目工时-Progect Man-hour</div>
					</td>
				</tr>
				<tr>
					<td>项目额定工时:</td>
					<td column="total_rate_man_hour"></td>
					<td>本月计划工时:</td>
					<td column="forecast_man_hour" ></td>
					<td>本月变更工时:</td>
					<td column="change_man_hour"></td>
				</tr>
				<tr>	
					<td>本月实际工时:</td>
					<td column="actual_man_hour"></td>
					<td>项目累计工时:</td>
					<td column="cumulative_man_hour"></td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: center">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="editWorkHours('edit')">编辑</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('项目工时维护','${ctx}/login/showList.do?url=business/project/workHours')">返回</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">
//初始化方法
var currentRow,init = false,data={};
// 	$(function(){
		$("#workHours_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			fitColumns:true,
	        url:"${ctx}/workHours/query.do",
	        onClickRow:function(rowIndex, rowData){
	        	currentRow = rowData;
	        },
	        onDblClickRow:function(index,data){
				findData(data.id);
	        },
	        onLoadSuccess:function(data){
	        	currentRow="";
	        	//1选择项目名称 onchange事件
	        	$("#project_edit").combobox({
	        		onChange: function (newValue, oldValue) {
	        			if(!init){
	        				clearh_three_hour();
	        				fillProjectNo(newValue, oldValue);
	        				change_total_rate_man_hour();
	        				checkIfExist();
	        				change_cumulative_man_hour();
	        			}
	        		}
	        	});
	        	//2选择专业名称 onchange事件
	        	$("#major_edit").combobox({
	        		onChange: function (newValue, oldValue) {
	        			if(!init){
	        				clearh_three_hour();
	        				change_total_rate_man_hour();
	        				checkIfExist();
	        				change_cumulative_man_hour();
	        			}
	        		}
	        	});
	        	//3选择统计月份onchange事件
	        	$("#statistical_month_edit").datebox({
	        		onChange: function (newValue, oldValue) {
	        			if(!init){
	        				checkIfExist();
	        				change_cumulative_man_hour();
	        			}
	        		}
	        	});
	        	//4填写本月实际工时onchange事件
	        	$("#actual_man_hour_edit").textbox({
	        		onChange:function(newValue,oldValue){
	        			if(!init){
	        				checkactual_man_hour_edit(newValue);
	        				change_cumulative_man_hour();
	        			}
	        		}
	        	});
	        	//5填写本月计划工时onchange事件
	        	$("#forecast_man_hour_edit").textbox({
	        		onChange:function(newValue,oldValue){
	        			if(!init){
	        				checkforecast_man_hour_edit(newValue);
	        			}
	        		}
	        	});
	        	$('#workHours_add').dialog({  
	                onClose:function(){  
	                	jumpPage("项目工时维护","${ctx}/login/showList.do?url=business/project/workHours");
	                }  
	            });
	        	$('#workHours_view').dialog({  
	                onClose:function(){  
	                	jumpPage("项目工时维护","${ctx}/login/showList.do?url=business/project/workHours");
	                }  
	            });
			}
		});
// 	});
	
	//---------判断项目是否录入-------
 	function checkProjectValidate(jobNo){
 		var flag=false;
 		$.ajax({
 			type:"post",
 			url:"${ctx}/project/checkValidate.do",
 			async:false,
 			contentType : "application/json;charset=utf-8",
 			data:JSON.stringify({"jobNo":jobNo}),
 			success:function(result){
 				flag=result;
			}
 		});
 		return flag;
 	}
	//----当项目额定工时有值的时候改变本月计划工时事件---
	function checkforecast_man_hour_edit(newValue){
		var total_rate_man_hour=$("#total_rate_man_hour_edit").textbox("getValue");
		if(newValue){
			if(parseFloat(newValue)>parseFloat(total_rate_man_hour)){
				$("#forecast_man_hour_edit").textbox("setValue","");
				$.messager.show({
 					title:"警告",
 					msg:"本月计划工时不能超过项目额定工时"
 				});
				$("#forecast_man_hour_edit").focus();
				return;
			}
		}
	}
	//----当项目额定工时有值的时候改变本月实际工时事件---
	function checkactual_man_hour_edit(newValue){
		var total_rate_man_hour=$("#total_rate_man_hour_edit").textbox("getValue");
		if(newValue){
			if(parseFloat(newValue)>parseFloat(total_rate_man_hour)){
				$("#actual_man_hour_edit").textbox("setValue","");
				$.messager.show({
 					title:"警告",
 					msg:"本月实际工时不能超过项目额定工时"
 				});
				$("#actual_man_hour_edit").focus();
				return;
			}
		}
	}
	//-----改变项目工作号----
 	function fillProjectNo(newValue, oldValue){
 		$("#job_no_edit").val(newValue);
 		$("#project_name_edit").val($("#project_edit").combobox("getText"));
 		if(checkProjectValidate(newValue)){
			$.messager.show({
				title:"警告",
				msg:"该项目未在系统中录入，请确认！"
			});
			$("#project_edit").combobox("setValue","");
			$("#project_name_edit").val("");
			$("#job_no_edit").val(null);
			return;
		}
 	}
	
	//-----改变项目额定工时方法-----
 	function change_total_rate_man_hour(){
 		var project = $("#project_edit").combobox("getValue");//项目名称
 		var major = $("#major_edit").combobox("getValue");//专业名称
 		if(isEmpty(project) || isEmpty(major) ){
 			return false;
 		}else{
 			//根据项目名称和专业查询项目额定工时
 			$.ajax({
 				url:"${ctx}/project/showDrawQty.do",
 				data:JSON.stringify({"project":project,"major":major,"type":"hour"}),
 				type: 'post',
 				contentType : "application/json;charset=utf-8",
 				success:function(result){
 					if(result==0){
 	 	 				$("#total_rate_man_hour_edit").textbox("setValue", "");
 					}else{
 	 	 				$("#total_rate_man_hour_edit").textbox("setValue", result);
 					}
 				}
 			});
 		}
 	}
	//-----本月已存在相同项目、相同专业的单据，请检查----------
	function checkIfExist(){
		var project = $("#project_edit").combobox("getValue");//项目名称
 		var major = $("#major_edit").combobox("getValue");//专业名称
 		var statistical_month=$("#statistical_month_edit").datebox("getValue");//统计月份
 		if(isEmpty(project) || isEmpty(major)|| isEmpty(statistical_month)){
 			return false;
 		}else{
			var id="";
 	 		var data="";
 	 		if(undefined!=currentRow){
 	 			id=currentRow.id;
 	 		}
 	 		$.ajax({
 				url:"${ctx}/workHours/checkIfExist.do",
 				data:JSON.stringify({"project":project,"major":major,"statistical_month_edit":statistical_month,"id":id}),
 				contentType : "application/json;charset=utf-8",
 				success:function(result){
					if(result.success){
						$.messager.show({
		 					title:"警告",
							msg:"本月已存在相同项目、相同专业的单据，请检查!"
		 				});
						$("#statistical_month_edit").datebox("setValue","");
						return;
					}
 				}
 			});
 		}
	}
	//-------改变项目累计工时方法-------
	function change_cumulative_man_hour(){
		var project = $("#project_edit").combobox("getValue");//项目名称
 		var major = $("#major_edit").combobox("getValue");//专业名称
 		var statistical_month=$("#statistical_month_edit").datebox("getValue");//统计月份
 		var actual_man_hour=$("#actual_man_hour_edit").textbox("getValue");//本月实际工时
 		if(isEmpty(project) || isEmpty(major)|| isEmpty(statistical_month) || isEmpty(actual_man_hour)){
 			return false;
 		}else{
 			var id="";
 	 		var data="";
 	 		if(undefined!=currentRow){
 	 			id=currentRow.id;
 	 		}
 	 		$.ajax({
 	 			url:"${ctx}/workHours/findCNH.do",
 				data:JSON.stringify({"project":project,"major":major,"statistical_month_edit":statistical_month,"id":id}),
 				type: 'post',
 				contentType : "application/json;charset=utf-8",
 				success:function(result){
					if(result=="0"){
						$("#cumulative_man_hour_edit").textbox("setValue",parseFloat(actual_man_hour));
					}else{
						var cumulative_man_hour_edit=(parseFloat(actual_man_hour)+parseFloat(result)).toFixed(1);
				 		var total_rate_man_hour=$("#total_rate_man_hour_edit").textbox("getValue");
				 		if(!isEmpty(total_rate_man_hour)){
					 		if(parseFloat(cumulative_man_hour_edit)>parseFloat(isEmpty(total_rate_man_hour)?0:total_rate_man_hour)){
			 					$("#actual_man_hour_edit").textbox("setValue","");
			 					$.messager.show({
			 		 					title:"警告",
			 		 					msg:"项目累计工时不能超过项目额定工时"
			 		 			});
			 					$("#actual_man_hour_edit").focus();
			 					return;
					 		}
				 		}
				 		$("#cumulative_man_hour_edit").textbox("setValue", cumulative_man_hour_edit);
					}
 				}
 	 		});
 		}
	}
 	//按钮--双击查看
	function findData(id){
		$.post("${ctx}/workHours/edit.do",{"id":id},function(result){
			$("#workHours_view").dialog("open").dialog("setTitle","项目工时维护查看");
	        loadData(result);
	 	});
 	}
	//按钮--查询
	function searchWorkHours(){
		$("#workHours_data").datagrid("reload",mosaicParams());
	}
	//按钮--编辑回显
	function editWorkHours(type){
 		init = true;
 		if(isEmpty(type)){
			$("#workHours_add").dialog("open").dialog("setTitle","项目工时新增");
	        $("#workHours_form").form("load",{});
	        init = false;
 		}else{
 			if(isEmpty(currentRow)){
 	 			$.messager.show({
 					title:"警告",
 					msg:"请选择一行数据"
 				});
 	 			return;
 	 		}
 			var id = currentRow.id;
 			$.post("${ctx}/workHours/edit.do",{"id":id},function(result){
 				data = result;
 				$("#workHours_add").dialog("open").dialog( "setTitle",'项目工时修改');
                data.project=data.job_no;
 		        $("#workHours_form").form("load",data);
 		        init = false;
 	 	    });
 		}
	 }
	//按钮--保存
	function saveWorkHours(){
		var project = $("#project_edit").combobox("getValue");//项目名称
 		var major = $("#major_edit").combobox("getValue");//专业名称
 		var statistical_month=$("#statistical_month_edit").datebox("getValue");//统计月份
 		var total_rate_man_hour=$("#total_rate_man_hour_edit").textbox("getValue");//项目额定工时
 		if(isEmpty(project)){
 			$.messager.show({
					title:"警告",
					msg:"请选择项目名称"
				});
 			return false;
 		}
 		if(isEmpty(major)){
 			$.messager.show({
					title:"警告",
					msg:"请选择专业名称"
				});
 			return false;
 		}
		if(!$("#workHours_form").form("validate")){
			return false;
		}
         //获取所有选项，拼接
         //var opts = $("#project_edit").combobox("options");
         var items = $("#project_edit").combobox("getData");
         var itemStr = ",";
         for(var i = 0;i < items.length;i++){
             itemStr += items[i].value + ",";
         }
         //获取当前值
         var value =$("#project_edit").combobox("getValue");
         if(isEmpty(value) || itemStr.indexOf("," + value + ",") == -1){
             return;
         }
  		 $.ajax({
			type:"post",
			url:"${ctx}/workHours/save.do",
			contentType : "application/json;charset=utf-8",
			data:getData("workHours_form", data),
			success:function(result){
				handleReturn(result,function(){
					jumpPage("项目工时维护","${ctx}/login/showList.do?url=business/project/workHours");
				});
			}
		});
	}
	//按钮--删除
	function removeWorkHours(){
		if(isEmpty(currentRow)){
 			$.messager.show({
				title:"警告",
				msg:"请选择一行数据"
			});
 			return false;
	 	}else{
			$.messager.confirm("是否删除","准备执行数据删除操作，请确认或取消。",function(r){
				if (r){
					$.post("${ctx}/workHours/delete.do",{"id":currentRow.id},function(result){
						handleReturn(result,function(){
							$("#workHours_data").datagrid("reload");
						});
					});
				}
			});
		}
	}
	//--------清空方法-------
	function clearh_three_hour(){
		$("#forecast_man_hour_edit").textbox("setValue","");
		$("#actual_man_hour_edit").textbox("setValue","");
		$("#cumulative_man_hour_edit").textbox("setValue","");
	}
</script>
</body>
</html>
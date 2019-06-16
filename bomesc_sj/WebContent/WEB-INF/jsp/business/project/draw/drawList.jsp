<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>图纸管理</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div>
				<div class="search_item">
					<label class="w80">单据编码</label>
					<span class="ml10">
						<input id="overview_no_search" type="text" class="easyui-textbox w250" dkd-search-element="overview_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">统计月份</label>
					<span class="ml10">
						<input id="statistical_month_search" type="text" class="datebox_month w100" data-options="editable:false" dkd-search-element="statistical_month >= text"/>
					</span>
					<label class="ml10">至</label>
					<span class="ml10">
						<input id="statistical_month_search" type="text" class="datebox_month w100" data-options="editable:false" dkd-search-element="statistical_month <= text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">专业名称</label>
					<span class="ml10">
						<input id="major_search" name="major" size="13" class="easyui-combobox" data-options="width:180,panelHeight:'auto',editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'" dkd-search-element="major = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">项目名称</label>
					<span class="ml10">
						<input id="project_name_search" name="job_no" class="edit_combobox" data-options="width:250,url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">项目工作号</label>
					<span>
						<input id="job_no_search" type="text" class="easyui-textbox w240" dkd-search-element="job_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">录入人</label>
					<span class="ml23">
						<input id="entity_createuser_search" type="text" class="easyui-textbox w250" dkd-search-element="entity_createuser like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">录入日期</label>
					<span class="ml10">
						<input id="entity_createdate_search" type="text" class="easyui-datebox w100" data-options="editable:false" dkd-search-element="entity_createdate >= text"/>
					</span>
					<label class="w80">至</label>
					<span class="ml10">
						<input id="entity_createdate_search" type="text" class="easyui-datebox w100" data-options="editable:false" dkd-search-element="entity_createdate <= text"/>
					</span>
				</div>
				<div align="center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="editDraw()">添加</a>
					<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="editDraw('edit')">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeDraw()">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDraw()">查询</a>
				</div>
			</div>
		</div>
	</form>
	<div style="padding-left:10px;overflow:auto">
		<table id="draw_data" align="center" style="width:100%">
			<thead>
				<tr>
					<th data-options="field:'overview_no',width:100,halign:'center',sortable:true" rowspan="2">单据编码</th>
					<th data-options="field:'statistical_month',width:100,halign:'center',sortable:true" rowspan="2">统计月份</th>
					<th data-options="field:'major_name',width:100,halign:'center',sortable:true" rowspan="2">专业名称</th>
					<th data-options="field:'project_name',width:100,halign:'center',sortable:true" rowspan="2">项目名称</th>
					<th data-options="field:'job_no',width:100,halign:'center',sortable:true" rowspan="2">项目工作号</th>
					<th data-options="field:'entity_createuser',width:60,halign:'center',sortable:true" rowspan="2">录入人</th>
					<th data-options="field:'entity_createdate',width:100,halign:'center',sortable:true" rowspan="2">录入日期</th>
					<th data-options="field:'total_draw_quantity',width:120,align:'left',halign:'center',sortable:true" rowspan="2">项目图纸总数量</th>
					<th colspan="2" data-options="width:300,align:'left',halign:'center'">上月完成图纸数量</th>
					<th data-options="width:300,halign:'center'" colspan="2">当月完成图纸数量</th>
					<th data-options="width:300,halign:'center'" colspan="2">当前完成图纸总数量</th>
				</tr>
				<tr>
					<th data-options="field:'pre_draw_quantity',width:50,halign:'center',sortable:true" >张数</th>
					<th data-options="field:'pre_percentage',width:100,halign:'center',sortable:true" >百分比(C/B)</th>
					<th data-options="field:'this_draw_quantity',width:50,halign:'center',sortable:true" >张数</th>
					<th data-options="field:'this_percentage',width:100,halign:'center',sortable:true" >百分比(C/B)</th>
					<th data-options="field:'draw_quantity',width:50,halign:'center',sortable:true" >张数</th>
					<th data-options="field:'total_percentage',width:120,halign:'center',sortable:true" >百分比(C/B)</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 编辑页面 -->
	<div id="draw_add" class="easyui-dialog pop" style="width:100%;height:60%;top:100px;padding:10px 20px;overflow:auto;" closed="true" buttons="#dlg-auth-buttons" maximizable=true modal=true>
		<form id="draw_form" method="post" action="${ctx}/draw/save.do">
			<table class="lines-both">
				<tr>
					<th>单据编码:</th>
					<td><input id="overview_no_edit" name="overview_no" size="13" class="easyui-textbox" readonly="readonly"/></td>
					<th>项目名称:</th>
					<td><input id="project_edit" name="project" class="edit_combobox" data-options="width:120,url:'${ctx}/project/show.do'"/>
						<input type="hidden" id="project_name_edit" name="project_name" value="" /></td>
					<th>专业名称:</th>
					<td><select id="major_edit" name="major" is-object data-options="width:150,panelHeight:'auto',editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'"></select></td>
					<th>统计月份:</th>
					<td><input id="statistical_month_edit" name="statistical_month" size="13" class="datebox_month" data-options="required:true,editable:false" /></td>
				</tr>
				<tr>
					<th>项目工作号:</th>
					<td><input id="job_no_edit" name="job_no" size="13" class="easyui-textbox" data-options="required:true" readonly="readonly"/></td>
					<th>录入人:</th>
					<td><input id="entity_createuser_edit" name="entity_createuser" size="13" class="easyui-textbox" readonly="readonly"/></td>
					<th>录入日期:</th>
					<td><input id="entity_createdate_edit" name="entity_createdate" size="13" class="easyui-datetimebox" readonly="readonly" data-options="width:100"/></td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="titleMessage">项目图纸总数量-Total Quantity of Drawing</div>
					</td>
				</tr>
				<tr>
					<th>项目图纸总数量：</th>
					<td>
						<input id="total_draw_quantity_edit" name="total_draw_quantity" size="13" class="easyui-textbox" data-options="required:true" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="titleMessage">上月完成图纸数量-Previous Month</div>
					</td>
				</tr>
				<tr>
					<th>数量:</th>
					<td><input id="pre_draw_quantity_edit" name="pre_draw_quantity" size="13" class="easyui-validatebox textbox" readonly="readonly"/></td>
					<th>百分比(%):</th>
					<td><input id="pre_percentage_edit" name="pre_percentage" size="13" class="easyui-validatebox textbox" readonly="readonly"/></td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="titleMessage">当月完成图纸数量-This Month</div>
					</td>
				</tr>
				<tr>
					<th>数量:</th>
					<td><input id="this_draw_quantity_edit" name="this_draw_quantity" size="13" class="easyui-validatebox textbox" data-options="required:true,validType:'length[1,20]'" onchange="calcate(this)" onKeyUp="this.value=this.value.replace(/\D/g,'');"/></td>
					<th>百分比(%):</th>
					<td><input id="this_percentage_edit" name="this_percentage" size="13" class="easyui-validatebox textbox" readonly="readonly"/></td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="titleMessage">当前完成图纸数量-Cumulative This Month</div>
					</td>
				</tr>
				<tr>
					<th>数量:</th>
					<td><input id="draw_quantity_edit" name="draw_quantity" size="13" class="easyui-validatebox textbox" readonly="readonly"/></td>
					<th>百分比(%):</th>
					<td><input id="total_percentage_edit" name="total_percentage" size="13" class="easyui-validatebox textbox" readonly="readonly"/></td>
				</tr>
				<tr>
					<td colspan="8" style="text-align: center">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveDraw()">保存</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('图纸管理','${ctx}/login/showList.do?url=business/project/draw')">返回</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="draw_view" class="easyui-dialog pop" style="width:80%;height:80%;top:100px;padding:10px 20px;overflow:auto;" closed="true" buttons="#dlg-auth-buttons" maximizable=true modal=true>
		<form id="draw_form_view">
			<table class="view_table">
				<tr>
					<th>单据编码:</th>
					<td column="overview_no"></td>
					<th>项目名称:</th>
					<td column="project_name"></td>
					<th>专业名称:</th>
					<td column="major_name"></td>
					<th>统计月份:</th>
					<td column="statistical_month"></td>
				</tr>
				<tr>
					<th>项目工作号:</th>
					<td column="job_no"></td>
					<th>录入人:</th>
					<td column="entity_createuser"></td>
					<th>录入日期:</th>
					<td column="entity_createdate"></td>
					<th>修改日期:</th>
					<td column="entity_modifydate"></td>
				</tr>
				<tr>
					<th>修改人:</th>
					<td column="entity_modifyuser"></td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="titleMessage">项目图纸总数量-Total Quantity of Drawing</div>
					</td>
				</tr>
				<tr>
					<th>项目图纸总数量：</th>
					<td column="total_draw_quantity"></td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="titleMessage">上月完成图纸数量-Previous Month</div>
					</td>
				</tr>
				<tr>
					<th>数量:</th>
					<td column="pre_draw_quantity"></td>
					<th>百分比(%):</th>
					<td column="pre_percentage"></td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="titleMessage">当月完成图纸数量-This Month</div>
					</td>
				</tr>
				<tr>
					<th>数量:</th>
					<td column="this_draw_quantity"></td>
					<th>百分比(%):</th>
					<td column="this_percentage"></td>
				</tr>
				<tr>
					<td colspan="8">
						<div class="titleMessage">当前完成图纸数量-Cumulative This Month</div>
					</td>
				</tr>
				<tr>
					<th>数量:</th>
					<td column="draw_quantity"></td>
					<th>百分比(%):</th>
					<td column="total_percentage"></td>
				</tr>
				<tr>
					<td colspan="8" style="text-align: center">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="editDraw('edit')">修改</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('图纸管理','${ctx}/login/showList.do?url=business/project/draw')">返回</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
    <script type="text/javascript">
    var currentRow,init = false,data={};
 	$(function(){
 		$("#draw_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
            url:"${ctx}/draw/query.do",
            queryParams: {          
                "sort":"entity_createdate",
                "order":"desc"
            },
            onClickRow:function(rowIndex, rowData){
            	currentRow = rowData;
            },
            onDblClickRow:function(index,data){
   				findData(data.id);
            },
            onLoadSuccess:function(data){
            	$("#major_edit").combobox({
            		onChange: function (n,o) {
            			if(!init){
            				findTotal();
            			}
            		}
            	});
            	$("#project_edit").combobox({
            		onChange: function (n,o) {
            			if(!init){
            				fillProjectNo(n,o);
            			}
            		}
            	});
            	$("#statistical_month_edit").datebox({
            		onChange: function (n,o) {
            			if(!init){
            				findPre("month");
            			}
            		}
            	});
            	currentRow=null;
			}
		});
 		$('#draw_add').dialog({  
            onClose:function(){  
            	if(!isEmpty(currentRow)){
            		findData(currentRow.id);
            	}else{
            		jumpPage("图纸管理","${ctx}/login/showList.do?url=business/project/draw");
            	}
            }  
        });
 		$('#draw_view').dialog({  
            onClose:function(){  
            	jumpPage("图纸管理","${ctx}/login/showList.do?url=business/project/draw");
            }  
        });
 	});
 	function searchDraw(){
 		var items = $("#project_name_search").combobox("getData");
 		var itemStr = ",";
		for(var i = 0;i < items.length;i++){
			itemStr += items[i].value + ",";
		}
 		//获取当前值
 		var value =$("#project_name_search").combobox("getValue");
		if(!isEmpty(value) && itemStr.indexOf("," + value + ",") == -1){
			$.messager.show({
				title:"警告",
				msg:"请选择正确的数据!"
			});
 			return;
		}
 		$("#draw_data").datagrid("reload",mosaicParams({"sort":"entity_createdate","order":"desc"}));
 	}
 	function findData(id){
 		$.post("${ctx}/draw/edit.do",{"id":id},function(result){
			$("#draw_view").dialog("open").dialog("setTitle","图纸查看");
	        loadData(result);
	 	});
 	}
 	function editDraw(type){
 		init = true;
 		if(isEmpty(type)){
 			currentRow=null;
			$("#draw_add").dialog("open").dialog("setTitle","图纸新增");
	        $("#draw_form").form("load",{});
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
 			$.post("${ctx}/draw/edit.do",{"id":id},function(result){
 				data = result;
 				$("#draw_add").dialog("open").dialog("setTitle","图纸修改");
 		        $("#draw_form").form("load",data);
 		        $("#project_edit").combobox("setValue",data.job_no);
 		        init = false;
 	 	    });
 		}
 	}
 	function saveDraw(){
 		if(!$("#draw_form").form("validate")){
			return;
		}
 		var this_draw_quantity=$("#this_draw_quantity_edit").val();
 		if(""==this_draw_quantity||null==this_draw_quantity||0==parseFloat(this_draw_quantity)){
 			$.messager.show({
				title:"警告",
				msg:"请输入正确的数量！"
			});
 			$("#this_draw_quantity_edit").val("0")
 			$("#this_draw_quantity_edit").focus();
 			return;
 		}
 		$.ajax({
 			type:"post",
 			url:"${ctx}/draw/save.do",
 			contentType : "application/json;charset=utf-8",
 			data:getData("draw_form",data),
 			success:function(result){
 				handleReturn(result,function(){
 					if(!isEmpty(data.id)){
 						$("#draw_add").dialog("close");
 						findData(data.id);
 					}else{
 						currentRow={"id":result.id};
 						$("#draw_add").dialog("close");
 						//jumpPage("图纸管理","${ctx}/login/showList.do?url=business/project/draw");
 					}
 				});
			}
 		});
 	}
 	function calcate(o){
 		//本月数量
 		var num= $(o).val();
 		//图纸总数量
 		var totalNum = $("#total_draw_quantity_edit").val();
 		//上月完成数量
 		var month = $("#statistical_month_edit").datebox("getValue");
 		var project = $("#project_edit").combobox("getValue");
 		var major = $("#major_edit").combobox("getValue");
 		if(isEmpty(project)){
 			$.messager.show({
				title:"警告",
				msg:"请先选择项目！"
			});
 			clearTextBox();
 			return;
 		}
 		if(isEmpty(major)){
 			$.messager.show({
				title:"警告",
				msg:"请先选择专业！"
			});
 			clearTextBox();
 			return;
 		}
 		if(isEmpty(month)){
 			$.messager.show({
				title:"警告",
				msg:"请先选择统计月份！"
			});
 			clearTextBox();
 			return;
 		}
 		var id="";
 		if(!isEmpty(currentRow)){
 			id=currentRow.id;
 		}
 		var dataResult="";
 		$.ajax({
 			type:"post",
 			url:"${ctx}/draw/findTotalPre.do",
 			async:false,
 			contentType : "application/json;charset=utf-8",
 			data:JSON.stringify({"project":project,"major":major,"month":month,"id":id}),
 			success:function(result){
 				dataResult=result;
			}
 		});
 		var numNow=parseFloat(num)+parseFloat(isEmpty(dataResult.this_draw_quantity)?0:dataResult.this_draw_quantity);
 		if(parseFloat(numNow)>parseFloat(totalNum)){
 			$.messager.show({
				title:"警告",
				msg:"当月或当前图纸完成数量不能大于图纸总数量！"
			});
 			$("#this_draw_quantity_edit").val("0");
 			$("#this_percentage_edit").val("0");
 			$("#draw_quantity_edit").val("0");
 			$("#total_percentage_edit").val("0");
 			return;
 		}
 		$("#draw_quantity_edit").val(numNow);
 		if(isEmpty(totalNum)||totalNum==0){
 			$("#this_percentage_edit").val("100");
 			$("#total_percentage_edit").val("100");
 		}else{
 			$("#this_percentage_edit").val((num/totalNum*100).toFixed(2));
 			$("#total_percentage_edit").val((numNow/totalNum*100).toFixed(2));
 		}
 	}
 	function findPre(type){
 		clearTextBox();
 		var month = $("#statistical_month_edit").datebox("getValue");
 		var project = $("#project_edit").combobox("getValue");
 		var major = $("#major_edit").combobox("getValue");
 		var id="";
 		var data="";
 		if(undefined!=currentRow){
 			id=currentRow.id;
 		}
 		$.ajax({
 			type:"post",
 			url:"${ctx}/draw/findPre.do",
 			async:false,
 			contentType : "application/json;charset=utf-8",
 			data:JSON.stringify({"project":project,"major":major,"month":month,"id":id}),
 			success:function(result){
 				data=result;
			}
 		});
 		if(data==true){
 			if(null!=month&&""!=month){
 				$.messager.show({
 					title:"警告",
 					msg:"本月已存在相同项目、相同专业的单据，请检查！"
 				});
 			}
 			if("project"==type){
 				$("#project_edit").combobox("setValue","");
 			}else if("major"==type){
 				$("#major_edit").combobox("setValue","");
 			}else{
 				$("#statistical_month_edit").datebox("setValue",null);
 			}
 			return;
 		}else{
 			$("#pre_draw_quantity_edit").val((data.this_draw_quantity==null||data.this_draw_quantity=="")?0:data.this_draw_quantity);
 	 		$("#pre_percentage_edit").val((data.this_percentage==null||data.this_percentage=="")?0:data.this_percentage);
 		}
 	}
 	function findTotal(){
 		clearTextBox();
 		var project = $("#project_edit").combobox("getValue");
		if(null==project||""==project){
			$("#total_draw_quantity_edit").textbox("setValue",null);
 			return;
 		}
 		var major = $("#major_edit").combobox("getValue");
 		if(null==major||""==major){
 			$("#total_draw_quantity_edit").textbox("setValue",null);
 			return;
 		}
 		var num="";
 		$.ajax({
 			type:"post",
 			url:"${ctx}/project/showDrawQty.do",
 			async:false,
 			contentType : "application/json;charset=utf-8",
 			data:JSON.stringify({"project":project,"major":major,"type":"qty"}),
 			success:function(result){
 				num=result;
			}
 		});
 		$("#total_draw_quantity_edit").textbox("setValue", num);
 		var statistical_month=$("#statistical_month_edit").textbox("getValue");
 		if(null!=statistical_month&&""!=statistical_month){
 			findPre("major");
 		} 
 	}
 	function removeDraw(){
 		if(null==currentRow){
 			$.messager.show({
				title:"警告",
				msg:"请选择要删除的图纸信息！"
			});
 			return;
 		}
 		$.messager.confirm("是否删除","准备执行数据删除操作，请确认或取消。",function(r){
			if (r){
				$.post("${ctx}/draw/delete.do",{"id":currentRow.id},function(result){
					handleReturn(result,function(){
						$("#draw_data").datagrid("reload");
					});
				});
			}
		});
 	}
 	function fillProjectNo(n,o){
 		$("#job_no_edit").textbox("setValue",n);
 		$("#project_name_edit").val($("#project_edit").combobox("getText"));
 		clearTextBox();
 		var project = $("#project_edit").combobox("getValue");
		if(null==project||""==project){
			$("#total_draw_quantity_edit").textbox("setValue",null);
 			return;
 		}
		if(checkProjectValidate(project)){
			$.messager.show({
				title:"警告",
				msg:"该项目未在系统中录入，请确认！"
			});
			$("#project_edit").combobox("setValue","");
			$("#project_name_edit").val(null);
			$("#job_no_edit").textbox("setValue",null);
			return;
		}
 		var major = $("#major_edit").combobox("getValue");
 		if(null==major||""==major){
 			$("#total_draw_quantity_edit").textbox("setValue",null);
 			return;
 		}
 		var num="";
 		$.ajax({
 			type:"post",
 			url:"${ctx}/project/showDrawQty.do",
 			async:false,
 			contentType : "application/json;charset=utf-8",
 			data:JSON.stringify({"project":project,"major":major,"type":"qty"}),
 			success:function(result){
 				num=result;
			}
 		});
 		$("#total_draw_quantity_edit").textbox("setValue", num);
 		clearTextBox();
 		var statistical_month=$("#statistical_month_edit").textbox("getValue");
 		if(null!=statistical_month&&""!=statistical_month){
 			findPre("project");
 		} 
 	}
 	function clearTextBox(){
 		$("#pre_draw_quantity_edit").val("0");
 		$("#pre_percentage_edit").val("0");
 		$("#this_draw_quantity_edit").val("0");
 		$("#this_percentage_edit").val("0");
 		$("#draw_quantity_edit").val("0");
 		$("#total_percentage_edit").val("0");
 	}
 	//判断项目是否录入
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
</script>
</body>
</html>
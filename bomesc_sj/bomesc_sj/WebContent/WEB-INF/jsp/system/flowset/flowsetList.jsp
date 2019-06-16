<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>审批流程管理</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div class="search_all_div">
				<div class="search_item">
					<label class="w80">流程编号</label>
					<span class="ml10">
						<input id="code_search" type="text" class="easyui-textbox w250" dkd-search-element="code like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">流程名称</label>
					<span class="ml10">
						<input id="name_search" type="text" class="easyui-textbox w250" dkd-search-element="name like text"/>
					</span>
				</div>
			</div>
			<div class="search_button" style="margin-top:5px;width:100%;text-align:center">
				<a href="javascript:searchFlowset()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
				<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
				<a href="javascript:newFlowset()" dkd-auth="AUTH_SYS_FLOWSET_ADD" class="easyui-linkbutton" iconCls="icon-add">新增审批流程</a>
			</div>
		</div>
	</form>
	<div style="padding-left:10px;width:49%;float:left">
		<table id="flowset_data" align="center">
			<thead>
				<tr>
					<th data-options="field:'code',width:100">编号</th>
					<th data-options="field:'name',width:150">名称</th>
					<th data-options="field:'table_name',width:150">操作表名</th>
					<th data-options="field:'levels',width:100">审批层级</th>
					<th data-options="field:'_operate',width:300,formatter:formatFlowsetOper" >操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="padding-left:10px;width:49%;float:left">
		<table id="flowset_detail_data" align="center">
			<thead>
				<tr>
					<th data-options="field:'order_no',width:60">序号</th>
					<th data-options="field:'name',width:180">名称</th>
					<th data-options="field:'detail_roles',width:300,formatter:formatRoles">角色</th>
					<th data-options="field:'is_validate',width:100,formatter:formatState">状态</th>
					<th data-options="field:'remark',width:200">备注</th>
					<th data-options="field:'_operate',width:280,formatter:formatFlowsetDetailOper" >操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 审批流程添加 -->
    <div id="flowset_add" class="easyui-dialog pop" style="width:600px;height:535px;top:10px;padding:10px 20px" closed="true" buttons="#dlg-flowset-buttons" maximizable=true modal=true>
		<form id="flowset_form" method="post" class="edit_form_big">
			<input id="id" name="id" type="hidden"/>
			<div class="fitem">
				<label>流程编号<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="code" name="code" maxlength="20" class="easyui-validatebox textbox" data-options="required:true"/>
			</div>
			<div class="fitem">
				<label>流程名称<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="name" name="name" maxlength="20" class="easyui-validatebox textbox"  data-options="required:true" />
			</div>
			<div class="fitem">
				<label>业务单据名称<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="business_name" name="business_name" maxlength="20" class="easyui-validatebox textbox"  data-options="required:true" />
			</div>
			<div class="fitem">
				<label>操作表名称<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="table_name" name="table_name" maxlength="50" class="easyui-validatebox textbox" data-options="required:true"/>
			</div>
			<div class="fitem">
				<label>业务主键字段名称<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="no_col" name="no_col" maxlength="20" class="easyui-validatebox textbox" data-options="required:true"/>
			</div>
			<div class="fitem">
				<label>状态字段名称<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="state_col" name="state_col" maxlength="20" class="easyui-validatebox textbox" data-options="required:true"/>
			</div>
			<div class="fitem">
				<label>审核通过状态值<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="pass_val" name="pass_val" maxlength="50" class="easyui-validatebox textbox" data-options="required:true"/>
			</div>
			<div class="fitem">
				<label>审核不通过状态值<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="unpass_val" name="unpass_val" maxlength="50" class="easyui-validatebox textbox" data-options="required:true"/>
			</div>
			<div class="fitem">
				<label>对应service名称<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="service_bean" name="service_bean" maxlength="50" class="easyui-validatebox textbox" data-options="required:true"/>
			</div>
			<div class="fitem">
				<label>审核通过回调函数：</label>
				<input id="handle_pass_method" name="handle_pass_method" maxlength="20" class="easyui-textbox textbox" />
			</div>
			<div class="fitem">
				<label>审核不通过回调函数：</label>
				<input id="handle_unpass_method" name="handle_unpass_method" maxlength="20" class="easyui-textbox textbox" />
			</div>
			<div class="fitem">
				<label>备注：</label>
				<input id="remark" name="remark" class="easyui-textbox" data-options="multiline:true" style="width:300px;height:50px"/>
			</div>
		</form>
    </div>
    <div id="dlg-flowset-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveFlowset()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#flowset_add').dialog('close')">取消</a>
    </div>
    <!-- 审批流程添加 -->
    <!-- 审批节点添加 -->
    <div id="flowset_detail_add" class="easyui-dialog pop" style="width:600px;height:455px;top:10px;padding:10px 20px" closed="true" buttons="#dlg-flowset-detail-buttons" maximizable=true modal=true>
		<form id="flowset_detail_form" method="post" class="edit_form_big">
			<input id="id" name="id" type="hidden"/>
			<div class="fitem">
				<label>节点序号<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="order_no" name="order_no" class="easyui-numberbox" data-options="required:true"/>
			</div>
			<div class="fitem">
				<label>节点名称<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="name" name="name" maxlength="20" class="easyui-validatebox textbox"  data-options="required:true" />
			</div>
			<div class="fitem">
				<label>对应角色<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<div style="max-height: 200px;overflow: auto;display:inline-block">
					<table class="roleTable">
					</table>
				</div>
			</div>
			<div class="fitem">
				<label>审批类型<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<span class="left inbl"><input type="radio" name="is_parallel" value="0" int/></span>
				<span class="right inbl">单级审批</span>
				<span class="left inbl"><input type="radio" name="is_parallel" value="1" int/></span>
				<span class="right inbl">并行审批</span>
			</div>
			<div class="fitem hide" name="all">
				<label>是否全部审批<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<span class="left inbl"><input type="radio" name="is_parallel_all" value="0" int/></span>
				<span class="right inbl">否</span>
				<span class="left inbl"><input type="radio" name="is_parallel_all" value="1" int/></span>
				<span class="right inbl">是</span>
			</div>
			<div class="fitem">
				<label>是否发送提醒邮件<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<span class="left inbl"><input type="radio" name="is_email" value="0" int/></span>
				<span class="right inbl">否</span>
				<span class="left inbl"><input type="radio" name="is_email" value="1" int/></span>
				<span class="right inbl">是</span>
			</div>
			<div class="fitem">
				<label>审核通过回调函数：</label>
				<input id="handle_pass_method" name="handle_pass_method" maxlength="20" class="easyui-textbox textbox" />
			</div>
			<div class="fitem">
				<label>备注：</label>
				<input id="remark" name="remark" class="easyui-textbox" data-options="multiline:true" style="width:300px;height:50px"/>
			</div>
		</form>
    </div>
    <div id="dlg-flowset-detail-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveFlowsetDetail()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#flowset_detail_add').dialog('close')">取消</a>
    </div>
    <!-- 审批节点添加 -->
    <script type="text/javascript">
 	$(function(){
 		$("#flowset_data").datagrid({
 			title:"审批流程",
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			fitColumns:true,
			striped:true,
            url:"${ctx}/flowset/query.do",
            onLoadSuccess:loadSuccess
		});
 		$("#flowset_detail_data").datagrid({
 			title:"流程子节点",
			rownumbers:true,
			singleSelect:true,
			fitColumns:true,
			striped:true,
            onLoadSuccess:loadSuccess
		});
 		$("input[name='is_parallel'][value='0']").click(function(){
 			$(".roleTable").find("input[name='roleId']").prop("type","radio");
 			$("div[name='all']").hide();
 			$("input[name='is_parallel_all'][value='0']").prop("checked",true);
 		});
 		$("input[name='is_parallel'][value='1']").click(function(){
 			$(".roleTable").find("input[name='roleId']").prop("type","checkbox");
 			$("div[name='all']").show();
 			$("input[name='is_parallel_all'][value='1']").prop("checked",true);
 		});
 	});
 	var data,detailData;
 	//审批流程方法
 	function formatFlowsetOper(val,row,index){  
 	    return "<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_FLOWSET_MOD\" href=\"javascript:void(0)\" iconCls=\"icon-edit\" onclick=\"editFlowset('" + row.id + "')\" title=\"修改\"></a>" + 
 	   		"<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_FLOWSET_DEL\" href=\"javascript:void(0)\" iconCls=\"icon-remove\" onclick=\"deleteFlowset('" + row.id + "')\" title=\"删除\"></a>" +
 	  		"<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_FLOWSET_CHECK\" href=\"javascript:void(0)\" iconCls=\"icon-search\" onclick=\"findFlowsetDetail('" + row.id + "')\" title=\"查看\"></a>" + 
 	  		"<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_FLOWSETDETAIL_ADD\" href=\"javascript:void(0)\" iconCls=\"icon-add\" onclick=\"newFlowsetDetail('" + row.id + "')\" title=\"添加审批节点\"></a>";  
 	}
 	function editFlowset(flowsetId){  
		$.post("${ctx}/flowset/findById.do",{"id":flowsetId},function(result){console.log(result);
			data = result;
			$("#flowset_add").dialog("open").dialog("setTitle","修改流程信息");  
	        $("#flowset_form").form("load",data);
 	   });
 	}
 	function findFlowsetDetail(flowId){
 		$.post("${ctx}/flowset/findFlowsetDetail.do",{"id":flowId},function(result){
 			$("#flowset_detail_data").datagrid("loadData",result); 			
 		});
 	}
 	function searchFlowset(){
 		$("#flowset_data").datagrid("reload",mosaicParams());
 	}
 	function newFlowset(){
 		$("#flowset_add").dialog("open").dialog("setTitle","添加审批流程");
 		$("#flowset_form").form("reset");
 		data = {};
 	}
 	function saveFlowset(){
 		if($("#flowset_form").form("validate")){
 			$.ajax({
 	 			url:"${ctx}/flowset/save.do",
 	 			contentType : "application/json;charset=utf-8",
 	 			data:getData("flowset_form",data),
 	 			success:function(result){
 	 				handleReturn(result,function(){
 	 					$("#flowset_add").dialog("close");
 	 					$("#flowset_data").datagrid("reload");
 	 				});
 				}
 	 		});
 		}
	}
 	function deleteFlowset(flowsetId){
 		$.messager.confirm("是否删除","此操作会删除该流程下级节点，是否确认删除？",function(r){
			if (r){
				$.post("${ctx}/flowset/delete.do",{"id":flowsetId},function(result){
					handleReturn(result,function(){
						$("#flowset_data").datagrid("reload");
						$("#flowsetChildrenData").datagrid("loadData",{total:0,rows:[]});
					});
				});
			}
		});
 	}
 	//审批节点方法
 	function formatRoles(val,row,index){
 		var rolesStr = "";
 		if(val.length > 0){
 			for(var i = 0;i < val.length;i++){
 				rolesStr += val[i].role_name;
 				if(i != val.length - 1){
 					rolesStr += "<br />";
 				}
 			}
 		}
 		return rolesStr;
 	}
 	function formatState(val,row,index){
 		if(val){
 			return "<span class='green'>启用</span>";
 		}else{
 			return "<span class='red'>禁用</span>";
 		}
 	}
 	function formatFlowsetDetailOper(val,row,index){
 	    return "<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_FLOWSETDETAIL_MOD\" href=\"javascript:void(0)\" iconCls=\"icon-edit\" onclick=\"editFlowsetDetail('" + row.id + "')\" title=\"修改\"></a>" +
 	   		(row.is_validate ? "<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_FLOWSETDETAIL_DISABLE\" href=\"javascript:void(0)\" iconCls=\"icon-no\" onclick=\"disableFlowsetDetail('" + row.id + "'," + index + ")\" title=\"禁用\"></a>" : 
 	   		"<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_FLOWSETDETAIL_ENABLE\" href=\"javascript:void(0)\" iconCls=\"icon-ok\" onclick=\"enableFlowsetDetail('" + row.id + "'," + index + ")\" title=\"启用\"></a>") +
 	   		"<a class=\"editButton m5\" dkd-auth=\"AUTH_SYS_FLOWSETDETAIL_DEL\" href=\"javascript:void(0)\" iconCls=\"icon-remove\" onclick=\"deleteFlowsetDetail('" + row.id + "'," + index + ",'"+row.flowset.id+"')\" title=\"删除\"></a>";
 	}
 	function roleDom(roles,handleFn){
 		var roleStr = "";
		for(var i = 0;i < roles.length;i++){
			if(i % 2 == 0){
				roleStr += "<tr>";
			}
			roleStr += "<td class='left'><input type='radio' ignore name='roleId' value='" + roles[i].id + "'></td>" + 
						"<td class='right'>" + roles[i].name + "</td>";
			if(i % 2 == 1){
				roleStr += "</tr>";
			}
		}
		if(roles.length % 2 == 1){
			roleStr += "</tr>";
		}
		$(".roleTable").html(roleStr);
		handleFn.apply();
 	}
 	function loadRoles(handleFn){
 		if("${allRolesStr}" == ""){
 			$.post("${ctx}/login/findRole.do",null,function(roles){
 				roleDom(roles,handleFn);
 	 		});
 		}else{
 			var roles = JSON.parse("${allRolesStr}");
 			console.log(roles);
 			roleDom(roles,handleFn);
 		}
 	}
 	function newFlowsetDetail(flowsetId){
 		loadRoles(function(){
 			$("#flowset_detail_add").dialog("open").dialog("setTitle","添加审批流程节点");
 	 		$("#flowset_detail_form").form("reset");
 	 		$("input[name='is_parallel'][value='0']").prop("checked",true);
 	 		$("input[name='is_parallel_all'][value='0']").prop("checked",true);
 	 		$("input[name='is_email'][value='0']").prop("checked",true);
 	 		$("div[name='all']").hide();
 	 		detailData = {"flowset":{"id":flowsetId}};
 		});
 	}
 	function editFlowsetDetail(detailId){
 		loadRoles(function(){
 			$.post("${ctx}/flowset/findByDetailId.do",{"id":detailId},function(result){
 				detailData = result;
 				$("#flowset_detail_add").dialog("open").dialog("setTitle","修改审批流程节点");
 	 	 		$("#flowset_detail_form").form("load",detailData);
 	 	 		if(detailData.is_parallel){
 	 	 			$("div[name='all']").show();
 	 	 			$(".roleTable").find("input[name='roleId']").prop("type","checkbox");
 	 	 			$("input[name='is_parallel'][value='1']").prop("checked",true);
 	 	 			if(detailData.is_parallel_all){
 	 	 				$("input[name='is_parallel_all'][value='1']").prop("checked",true);
 	 	 			}else{
 	 	 				$("input[name='is_parallel_all'][value='0']").prop("checked",true);
 	 	 			}
 	 	 		}else{
 	 	 			$("div[name='all']").hide();
 	 	 			$(".roleTable").find("input[name='roleId']").prop("type","radio");
 	 	 			$("input[name='is_parallel'][value='0']").prop("checked",true);
 	 	 		}
 	 	 		if(detailData.is_email){
 	 	 			$("input[name='is_email'][value='1']").prop("checked",true);
 	 	 		}else{
 	 	 			$("input[name='is_email'][value='0']").prop("checked",true);
 	 	 		}
 	 	 		if(detailData.detail_roles.length > 0){
 	 	 			for(var i = 0;i < detailData.detail_roles.length;i++){
 	 	 				$("input[name='roleId'][value='" + detailData.detail_roles[i].role.id + "']").attr("checked",true);
 	 	 			}
 	 	 		}
 	 	   });
 		});
 	}
 	function saveFlowsetDetail(){
 		if($("#flowset_detail_form").form("validate")){
 			var is_parallel = $("input[name='is_parallel']:checked").val();
 			var roles = $("input[type='"+(is_parallel == "0" ? "radio" : "checkbox")+"'][name='roleId']:checked");
 			if(roles.length == 0){
 				$.messager.show({
 					title:"警告",
 					msg:"至少选择一个角色"
 				});
 			}else{
 				var detail_roles = [];
 				roles.each(function(){
 					detail_roles.push({"role_id":$(this).val(),"role_name":$(this).parent().next().html()});
 	 			});
 				$.ajax({
 	 	 			url:"${ctx}/flowset/saveDetail.do",
 	 	 			contentType : "application/json;charset=utf-8",
 	 	 			data:getData("flowset_detail_form",detailData,"detail_roles",detail_roles),
 	 	 			success:function(result){
 	 	 				handleReturn(result,function(){
 	 	 					$("#flowset_detail_add").dialog("close");
 	 	 					$("#flowset_data").datagrid("reload");
 	 	 					findFlowsetDetail(detailData.flowset.id);
 	 	 				});
 	 				}
 	 	 		});
 			}
 		}
 	}
 	function disableFlowsetDetail(detailId,index){
 		$.messager.confirm('是否禁用','是否确认禁用？',function(r){
		    if (r){
		    	$.post("${ctx}/flowset/disableFlowsetDetail.do",{"id":detailId},function(result){
		    		handleReturn(result,function(){
		    			var row = $("#flowset_detail_data").datagrid("getRows")[index];
		    			row.is_validate = false;
		    			$("#flowset_detail_data").datagrid("updateRow",{"index":index,"row":row});
		    			$("#flowset_detail_data").datagrid("refreshRow",index);
		    			$(".editButton").linkbutton();
 	 				});
		 		});
		    }
		});
 	}
 	function enableFlowsetDetail(detailId,index){
 		$.messager.confirm('是否启用','是否确认启用？',function(r){
		    if (r){
		    	$.post("${ctx}/flowset/enableFlowsetDetail.do",{"id":detailId},function(result){
		    		handleReturn(result,function(){
		    			var row = $("#flowset_detail_data").datagrid("getRows")[index];
		    			row.is_validate = true;
		    			$("#flowset_detail_data").datagrid("updateRow",{"index":index,"row":row});
		    			$("#flowset_detail_data").datagrid("refreshRow",index);
		    			$(".editButton").linkbutton();
 	 				});
		 		});
		    }
		});
 	}
 	function deleteFlowsetDetail(detailId,index,flowsetId){
 		$.messager.confirm('是否删除','是否确认删除？',function(r){
		    if (r){
		    	$.post("${ctx}/flowset/deleteFlowsetDetail.do",{"id":detailId,"flowsetId":flowsetId},function(result){
		    		handleReturn(result,function(){
		    			$("#flowset_detail_data").datagrid("deleteRow",index);
 	 				});
		 		});
		    }
		});
 	}
</script>
</body>
</html>
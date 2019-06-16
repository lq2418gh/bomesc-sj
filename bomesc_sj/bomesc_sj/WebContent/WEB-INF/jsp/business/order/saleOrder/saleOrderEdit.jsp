<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单编辑页面</title>
</head>
<body>
	<form id="sale_order_form" method="post" action="${ctx}/saleOrder/save.do">
		<table class="search_table">
			<tr>
				<th>订单号</th>
				<td><input id="order_no" name="order_no" size="20" class="easyui-validatebox textbox" data-options="required:true"/></td>
				<th>客户</th>
				<td><input id="customer" name="customer" size="20" class="easyui-validatebox textbox" data-options="required:true"/></td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSaleOrder()">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('订单管理','${ctx}/login/showList.do?url=business/order/saleOrder')">返回</a>
				</td>
			</tr>
		</table>
	</form>
	<div id="toolbar_saleOrderEdit" class="dkd-toolbar-wrap">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addSoLine()">增行</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteSoLine()">删行</a>
    </div>
    <div style="padding-left:10px">
	    <table id="sale_order_detail">
			<thead>
				<tr>
					<th data-options="field:'commodity_name',width:100,editor:{type:'text'}">物料名称</th>
					<th data-options="field:'purchaser_id',hidden:true">申购人</th>
					<th data-options="field:'purchaser_name',width:300,formatter:formatselectUserOper">申购人</th>
				</tr>
			</thead>
		</table>
    </div>

<script type="text/javascript">
	var data;
	$(function(){
		$("#sale_order_detail").datagrid({
			rownumbers:true,
            fitColumns:true,
            toolbar:"#toolbar_saleOrderEdit",
            onClickRow:onClickRow,
            onAfterEdit:function(){
            	$(".search_button").linkbutton();
            },
            onLoadSuccess:function(data){
            	$(".search_button").linkbutton();
            }
		});
		if("${order_no}" == ""){
	        $("#sale_order_detail").datagrid("loadData",{total:0,rows:[]});
		}else{
			$.post("${ctx}/saleOrder/findByNo.do",{"order_no":"${order_no}"},function(result){
				data = result;
		        $("#sale_order_form").form("load",data);
		        $("#sale_order_detail").datagrid("loadData",{total:data.details.length,rows:data.details});
	 	   });
		}
	});
	var clickIndex;
	function selectUser(index){
		clickIndex = index;
		showWindow({
  			title:"选择用户",
  			href:"${ctx}/login/selectUserList.do",
  			width:820,
  			height:550
  		});
	}
	function userDataHandle(row){
		if(clickIndex == editIndex){
			endEditing();
		}
		var gridRow = $("#sale_order_detail").datagrid("getRows")[clickIndex];
		gridRow.purchaser_id = row.id;
		gridRow.purchaser_name = row.name;
		$("#sale_order_detail").datagrid("refreshRow", clickIndex);
		$(".search_button").linkbutton();
		if(clickIndex == editIndex){
			$("#sale_order_detail").datagrid("selectRow", clickIndex).datagrid(
                    "beginEdit", clickIndex);
		}
		closeWindow();
	}
	function saveSaleOrder(){
		endEditing();
		if(!$("#sale_order_form").form("validate")){
			return;
		}
		var rows = $("#sale_order_detail").datagrid("getRows");
		if(rows == null || rows.length == 0){
			$.messager.show({
				title:"警告",
				msg:"请添加明细！"
			});
		}else{
			for(var i = 0;i < rows.length;i++){
				if(isEmpty(rows[i].commodity_name)){
					$.messager.show({
						title:"警告",
						msg:"物料名称不能为空！"
					});
					return;
				}
			}
			$.ajax({
 	 			type:"post",
 	 			url:"${ctx}/saleOrder/save.do",
 	 			contentType : "application/json;charset=utf-8",
 	 			data:getData("sale_order_form",data,"details",rows),
 	 			success:function(result){
 	 				handleReturn(result,function(){
 	 					jumpPage("订单管理","${ctx}/login/showList.do?url=business/order/saleOrder");
 	 					//setMain('订单管理','${ctx}/login/showList.do?url=business/saleOrder');
 	 				});
 				}
 	 		});
		}
		
	}
	function formatselectUserOper(val,row,index){
		return "<input name='purchaserName' size='20' class='easyui-validatebox fleft' data-optons='required:true' readonly='readonly' value='" + (val ? val : "") + "'/>"+
		"<a href='javascript:void(0)' class='easyui-linkbutton search_button' iconCls='icon-search' onclick='selectUser(" + index + ")'>查询</a>";
	}
	function addSoLine(){
		$("#sale_order_detail").datagrid("appendRow",{});
		$(".search_button").linkbutton();
	}
	function deleteSoLine(){
		var rows = $("#sale_order_detail").datagrid("getSelections");
		if(rows){
			for(var i = 0;i < rows.length;i++){
				 $("#sale_order_detail").datagrid("deleteRow",$("#sale_order_detail").datagrid("getRowIndex",rows[i]));
			}
		}
	}
	var editIndex;
    function endEditing(){
        if (editIndex == undefined){return true;}
        if ($("#sale_order_detail").datagrid("validateRow", editIndex)){
            $("#sale_order_detail").datagrid("endEdit", editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onClickRow(index,rowData){
	    if (editIndex != index){
	        if (endEditing()){
	            $("#sale_order_detail").datagrid("beginEdit", index);
	            editIndex = index;
	            $(".datagrid-editable-input").css({"width":"100%","height":"26px"});
	            $(".datagrid-editable-input:eq(0)").focus();
	        } 
	    }
	}
</script>
</body>
</html>
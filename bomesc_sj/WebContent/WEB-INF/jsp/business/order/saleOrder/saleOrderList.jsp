<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单管理</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div class="search_all_div">
				
			</div>
			<div class="search_button" style="margin-top:5px;width:100%;text-align:center">
				<a href="javascript:void(0)" dkd-auth="AUTH_PUR_ORDER_CONDITIONNUM" class="easyui-linkbutton" iconCls="icon-add" onclick="addCondition('SaleOrder')">新增条件</a>
				<a href="javascript:void(0)" dkd-auth="AUTH_PUR_ORDER_CONDITIONNUM" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeCondition()">删除条件</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchSaleOrder()">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="resetSearch()">重置</a>
				<a href="javascript:void(0)" dkd-auth="AUTH_PUR_ORDER_ADD" class="easyui-linkbutton" iconCls="icon-add" onclick="editSaleOrder()">新增订单</a>
			</div>
		</div>
	</form>
	<div style="padding-left:10px">
		<table id="sale_order_data" align="center">
			<thead>
				<tr>
					<th data-options="field:'order_no',width:300">订单号</th>
					<th data-options="field:'customer',width:180">客户</th>
					<th data-options="field:'state',width:180">状态</th>
					<th data-options="field:'entity_createdate',width:180">创建时间</th>
					<th data-options="field:'_operate',width:180,formatter:formatSalOrderOper">操作</th>
				</tr>
			</thead>
		</table>
	</div>
    <script type="text/javascript">
 	$(function(){
 		$("#sale_order_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			fitColumns:true,
            url:"${ctx}/saleOrder/query.do",
            onDblClickRow:function(index,data){
            	jumpPage("订单查看","${ctx}/saleOrder/view.do?order_no=" + data.order_no);
            	//setMain("订单查看","${ctx}/saleOrder/view.do?order_no=" + data.order_no);
            },
            onLoadSuccess:function(data){
            	$(".editButton").linkbutton();
			}
		});
 		initCondition("SaleOrder",["订单号","状态"]);
 	});
 	function formatSalOrderOper(val,row,index){
 		var returnStr = "<a class=\"editButton m5\" dkd-auth=\"AUTH_PUR_ORDER_MOD\" href=\"javascript:void(0)\" iconCls=\"icon-edit\" onclick=\"editSaleOrder('" + row.order_no + "')\">修改</a>";
 		if(row.state == "新建"){
 			returnStr += "<a class=\"editButton m5\" dkd-auth=\"AUTH_PUR_ORDER_SUBMIT\" href=\"javascript:void(0)\" iconCls=\"icon-redo\" onclick=\"submitSaleOrder('" + row.order_no + "')\">提交</a>";
 		}
 	    return returnStr;  
 	}
 	function searchSaleOrder(){
 		$("#sale_order_data").datagrid("reload",mosaicActivityParams());
 	}
 	function editSaleOrder(order_no){
 		if(isEmpty(order_no)){
 			jumpPage("订单新增","${ctx}/saleOrder/edit.do");
 			//setMain("订单新增","${ctx}/saleOrder/edit.do");
 		}else{
 			jumpPage("订单修改","${ctx}/saleOrder/edit.do?order_no=" + order_no);
 			//setMain("订单修改","${ctx}/saleOrder/edit.do?order_no=" + order_no);
 		}
 	}
 	function submitSaleOrder(order_no){
 		$.post("${ctx}/saleOrder/submit.do",{"order_no":order_no},function(result){
 			handleReturn(result,function(){
 				$("#sale_order_data").datagrid("reload",mosaicActivityParams());
 			});
 		});
 	}
</script>
</body>
</html>
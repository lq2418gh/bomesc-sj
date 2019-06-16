<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单查看页面</title>
</head>
<body>
	<form id="sale_order_form" method="post" action="${ctx}/saleOrder/save.do">
		<table class="view_table search_table">
			<tr>
				<th>订单号</th>
				<td column="order_no"></td>
				<th>客户</th>
				<td column="customer"></td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: center">
					<a href="javascript:void(0)" class="easyui-linkbutton" dkd-check="${order_no}" iconCls="icon-tip" onclick="checkBill('${order_no}')">审核</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="loadCheckHistory('${order_no}')">查看审核记录</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="viewFileInfo()">文件管理</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('订单管理','${ctx}/login/showList.do?url=business/order/saleOrder')">返回</a>
				</td>
			</tr>
		</table>
	</form>
	 <div style="padding-left:10px">
	 	<table id="sale_order_detail">
			<thead>
				<tr>
					<th data-options="field:'commodity_name',width:300">物料名称</th>
					<th data-options="field:'purchaser_name',width:300">申购人</th>
				</tr>
			</thead>
		</table>
	 </div>

<script type="text/javascript">
	var data;
	$(function(){
		$("#sale_order_detail").datagrid({
			rownumbers:true,
			singleSelect:true,
            fitColumns:true,
            onLoadSuccess:function(data){
            	$(".search_button").linkbutton();
            }
		});
		if("${order_no}" == ""){
	        $("#sale_order_detail").datagrid("loadData",{total:0,rows:[]});
		}else{
			$.post("${ctx}/saleOrder/findByNo.do",{"order_no":"${order_no}"},function(result){
				data = result;
		        loadData(data);
		        $("#sale_order_detail").datagrid("loadData",{total:data.details.length,rows:data.details});
	 	   });
		}
	});
	function viewFileInfo(){
		fileInfo("saleOrder",data.id);
	}
</script>
</body>
</html>
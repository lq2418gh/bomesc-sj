<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单查看页面</title>
</head>
<body>
	<div>
		<table class="view_table search_table">
			<tr>
				<th>料单编号:</th>
				<td column="mto_no"></td>
				<th>项目名称:</th>
				<td column="project_name"></td>
				<th>项目工作号:</th>
				<td column="job_no"></td>
				<th>专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</th>
				<td column="major_name"></td>	
			</tr>
			<tr>
				<th>采购类型:</th>
				<td column="purchase_type"></td>
				<th>料单状态:</th>
				<td column="state"></td>
				<th>确&nbsp;&nbsp;&nbsp;认&nbsp;&nbsp;&nbsp;人:</th>
				<td column="confirm_user"></td>
				<th>确认日期:</th>
				<td column="confirm_date"></td>				
			</tr>
			<tr>
				<th>申购单位:</th>
				<td column="purchase_company"></td>
				<th>导入人:&nbsp;&nbsp;&nbsp;</th>
				<td column="entity_createuser"></td>
				<th>导入日期:</th>
				<td column="entity_createdate"></td>
			</tr>
			<tr>
				<td colspan="10" style="text-align: center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('材料申购单管理','${ctx}/login/showList.do?url=business/mto/mtoHead')">返回</a>
					<a id="delBtn" href="javascript:deleteMto()" class="easyui-linkbutton" iconCls="icon-cancel" >删除</a>
					<a id="confirmBtn" href="javascript:mtoState('confirm')" class="easyui-linkbutton" iconCls="icon-redo" >确认</a>
					<a id="concelConfirmBtn" href="javascript:mtoState('concelConfirm')" class="easyui-linkbutton" iconCls="icon-undo" >取消确认</a>
				</td>
			</tr>
		</table>
	</div>	
	 <div style="padding-left:10px">
	 	<table id="mto_detail">
			<thead>
				<tr>
					<th data-options="field:'mto_row_no',width:300">料单行号</th>
					<th data-options="field:'material_code',width:300">物料编码</th>
					<th data-options="field:'material_name',width:300">物料名称</th>
					<th data-options="field:'member_num',width:300">杆件号</th>
					<th data-options="field:'size',width:300">规格尺寸</th>
					<th data-options="field:'material',width:300">材质</th>
					<th data-options="field:'unit',width:300">单位</th>
					<th data-options="field:'design_qty',width:300">设计数量</th>
					<th data-options="field:'recommend_surplus',width:300">建议余量</th>
					<th data-options="field:'purchase_qty',width:300">采购数量</th>
					<th data-options="field:'stock_qty',width:300">库存使用量</th>
					<th data-options="field:'description',width:300">技术要求</th>
					<th data-options="field:'standard',width:300">执行标准</th>
					<th data-options="field:'recommend_vendor',width:300">推荐厂家</th>
					<th data-options="field:'certificate_requirement',width:300">证书要求</th>
					<th data-options="field:'arrival_date',width:300">到货时间</th>
					<th data-options="field:'remark',width:300">备注</th>
				</tr>
			</thead>
		</table>
	 </div>

<script type="text/javascript">
	//页面初始化
	var data;
	$(function(){
		$("#mto_detail").datagrid({
			//rownumbers:true,
			singleSelect:true,
            fitColumns:true,
            onLoadSuccess:function(data){
            	$(".search_button").linkbutton();
            }
		});
		if("${mto_no}" == ""){
	        $("#mto_detail").datagrid("loadData",{total:0,rows:[]});
		}else{
			initHeadAndDetails();
		}
		if(!isEmpty("${msg}")&&"${msg}"=="import_success"){
			$.messager.show({title:"提示",msg:"导入成功！"});
		}
	});
	//初始化页面表头和表体，根据状态设置按钮显示情况
	function initHeadAndDetails(){
		$.post("${ctx}/mto/findByNo.do",{"mto_no":"${mto_no}"},function(result){
			data = result;
	        loadData(data);//加载表头
	        $("#mto_detail").datagrid("loadData",{total:data.details.length,rows:data.details});//加载明细
			if(data.state=="确认"){
				$("#delBtn").hide();
				$("#confirmBtn").hide();
				$("#concelConfirmBtn").show();
			}
	        if(data.state=="新建"){
	        	$("#concelConfirmBtn").hide();
				$("#delBtn").show();
				$("#confirmBtn").show();
	        }
		});
	}
	//删除料单
	function deleteMto(){
		$.messager.confirm("是否删除","准备执行数据删除操作，请确认或取消。",function(r){
		    if (r){
		    	$.post("${ctx}/mto/deleteMto.do",{"id":data.id},function(result){
		    		handleReturn(result,function(){
		    			jumpPage("订单查看","${ctx}/login/showList.do?url=business/mto/mtoHead");
 	 			});
		 	});
		   }
		});
	}
	//更改料单状态
	function mtoState(param){
 		$.messager.confirm('状态更改','正在进行'+(param=="confirm"?"确认":"取消确认")+'操作,是否确认！',function(r){
 			if (r){
 		 		$.post("${ctx}/mto/mtoState.do",{"id":data.id,"opr":param},function(result){
 		 			handleReturn(result,function(){
 		 				initHeadAndDetails();
 		 			});
 		 		});
 			}
 		});
	}
</script>
</body>
</html>
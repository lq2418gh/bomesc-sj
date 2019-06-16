<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>结构补料数据跟踪表</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div class="search_all_div">
				<div class="search_item" ignore>
					<label class="w100">项目名称</label>
					<span class="ml50">
						<input id="project_name_search" name="job_no" class="edit_combobox" data-options="width:200,url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
					</span>
				</div>
			</div>
			<div style="margin-top:5px;text-align:center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addStCondition()">新增条件</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeCondition()">删除条件</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="stSuppChooseColumnView()">显示列设置</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchstSupp()">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="resetSearch()">重置</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="importStDTS()">导入</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="toEdit()">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="makePicking()">生成领料单</a>
			</div>
		</div>
	</form>
	<div class="datagrid_div">
		<table id="st_data_track_supp_table" align="center" >
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true">ID</th>
					<th data-options="field:'project_name',width:100,halign:'center',sortable:true,hidden:true">项目名称</th>
					<th data-options="field:'job_no',width:100,halign:'center',sortable:true,hidden:true">项目工作号</th>
					<th data-options="field:'module_name',width:100,halign:'center',sortable:true,hidden:true">模块名称</th>
					<th data-options="field:'shop_draw_no',width:100,halign:'center',sortable:true,hidden:true">加设图纸编号</th>
					<th data-options="field:'shop_draw_rev',width:100,halign:'center',sortable:true,hidden:true">加设图纸版本</th>
					<th data-options="field:'contractor_draw_no',width:100,halign:'center',sortable:true,hidden:true">业主图纸编号</th>
					<th data-options="field:'contractor_draw_rev',width:100,halign:'center',sortable:true,hidden:true">业主图纸版本</th>
					<th data-options="field:'part_no',width:100,halign:'center',sortable:true,hidden:true">零件编号</th>
					<th data-options="field:'part_name',width:80,halign:'center',sortable:true,hidden:true">零件名称</th>
					<th data-options="field:'steel_type',width:100,halign:'center',sortable:true,hidden:true">零件类型</th>
					<th data-options="field:'structure_type',width:100,halign:'center',sortable:true,hidden:true">结构类型</th>
					<th data-options="field:'area_of_part',width:100,halign:'center',sortable:true,hidden:true">零件区域</th>
					<th data-options="field:'level_no',width:100,halign:'center',sortable:true,hidden:true">层数</th>
					<th data-options="field:'work_package_no',width:100,halign:'center',sortable:true,hidden:true">模块工作包号</th>
					<th data-options="field:'part_profile_name',width:100,halign:'center',sortable:true,hidden:true">零件规格名称</th>
					<th data-options="field:'part_profile',width:100,halign:'center',sortable:true,hidden:true">零件规格</th>
					<th data-options="field:'grade',width:100,halign:'center',sortable:true,hidden:true">材质</th>
					<th data-options="field:'qty',width:80,halign:'center',sortable:true,hidden:true">数量</th>
					<th data-options="field:'quantity_in_dm_mto',width:120,halign:'center',sortable:true,hidden:true">设计料单中的数量</th>
					<th data-options="field:'quantity_in_pd_mto',width:120,halign:'center',sortable:true,hidden:true">采办料单中的数量</th>
					<th data-options="field:'net_single_length',width:100,halign:'center',sortable:true,hidden:true">净长度</th>
					<th data-options="field:'net_single_width',width:100,halign:'center',sortable:true,hidden:true">净宽度</th>
					<th data-options="field:'unit_weight',width:100,halign:'center',sortable:true,hidden:true">单重</th>
					<th data-options="field:'net_single_weight',width:100,halign:'center',sortable:true,hidden:true">单净重</th>
					<th data-options="field:'net_total_weight',width:100,halign:'center',sortable:true,hidden:true">总净重</th>
					<th data-options="field:'gross_single_weight',width:100,halign:'center',sortable:true,hidden:true">单毛重</th>
					<th data-options="field:'gross_total_weight',width:80,halign:'center',sortable:true,hidden:true">总毛重</th>
					<th data-options="field:'net_area',width:100,halign:'center',sortable:true,hidden:true">净表面积</th>
					<th data-options="field:'surface_treatment',width:100,halign:'center',sortable:true,hidden:true">表面处理</th>
					<th data-options="field:'coating_system',width:100,halign:'center',sortable:true,hidden:true">油漆配套</th>
					<th data-options="field:'coating_area',width:100,halign:'center',sortable:true,hidden:true">油漆面积</th>
					<th data-options="field:'fireproof_type',width:100,halign:'center',sortable:true,hidden:true">防火类型</th>
					<th data-options="field:'fireproof_area',width:100,halign:'center',sortable:true,hidden:true">防火面积</th>
					<th data-options="field:'fireproof_thickness',width:100,halign:'center',sortable:true,hidden:true">防火厚度</th>
					<th data-options="field:'fireproof_length',width:100,halign:'center',sortable:true,hidden:true">防火长度</th>
					<th data-options="field:'wasted_by_drawing_update',width:160,halign:'center',sortable:true,formatter:formatValue,hidden:true">材料是否因升版作废</th>
					<th data-options="field:'added_by_drawing_update',width:160,halign:'center',sortable:true,formatter:formatValue,hidden:true">材料是否因升版增加</th>
					<th data-options="field:'nesting_draw_no',width:100,halign:'center',sortable:true,hidden:true">套料图纸编号</th>
					<th data-options="field:'nesting_draw_rev',width:100,halign:'center',sortable:true,hidden:true">套料图纸版本</th>
					<th data-options="field:'bulk_material_no',width:100,halign:'center',sortable:true,hidden:true">对应母材编号</th>
					<th data-options="field:'remnant_part_no',width:100,halign:'center',sortable:true,hidden:true">使用余料编号</th>
					<th data-options="field:'material_code',width:100,halign:'center',sortable:true,hidden:true">物料编码</th>
					<th data-options="field:'supplier',width:100,halign:'center',sortable:true,hidden:true">供货方</th>
					<th data-options="field:'mto_no',width:100,halign:'center',sortable:true,hidden:true">料单编号</th>
					<th data-options="field:'mto_row_no',width:100,halign:'center',sortable:true,hidden:true">料单行号</th>
					<th data-options="field:'ident_code',width:100,halign:'center',sortable:true,hidden:true">标签编号</th>
					<th data-options="field:'pay_item_no',width:100,halign:'center',sortable:true,hidden:true">业主付款编号</th>
					<th data-options="field:'bar_code_no',width:80,halign:'center',sortable:true,hidden:true">条形码编号</th>
					<th data-options="field:'vendor',width:100,halign:'center',sortable:true,hidden:true">厂家</th>
					<th data-options="field:'site',width:100,halign:'center',sortable:true,hidden:true">设备厂址</th>
					<th data-options="field:'website',width:100,halign:'center',sortable:true,hidden:true">设备网址</th>
					<th data-options="field:'tellphone_no',width:100,halign:'center',sortable:true,hidden:true">联系电话</th>
					<th data-options="field:'column1',width:100,halign:'center',sortable:true,hidden:true">补充列1</th>
					<th data-options="field:'column2',width:100,halign:'center',sortable:true,hidden:true">补充列2</th>
					<th data-options="field:'column3',width:100,halign:'center',sortable:true,hidden:true">补充列3</th>
					<th data-options="field:'column4',width:100,halign:'center',sortable:true,hidden:true">补充列4</th>
					<th data-options="field:'column5',width:100,halign:'center',sortable:true,hidden:true">补充列5</th>
					<th data-options="field:'column6',width:100,halign:'center',sortable:true,hidden:true">补充列6</th>
					<th data-options="field:'column7',width:100,halign:'center',sortable:true,hidden:true">补充列7</th>
					<th data-options="field:'column8',width:100,halign:'center',sortable:true,hidden:true">补充列8</th>
					<th data-options="field:'column9',width:100,halign:'center',sortable:true,hidden:true">补充列9</th>
					<th data-options="field:'column10',width:100,halign:'center',sortable:true,hidden:true">补充列10</th>
					<th data-options="field:'column11',width:100,halign:'center',sortable:true,hidden:true">补充列11</th>
					<th data-options="field:'column12',width:100,halign:'center',sortable:true,hidden:true">补充列12</th>
					<th data-options="field:'column13',width:100,halign:'center',sortable:true,hidden:true">补充列13</th>
					<th data-options="field:'column14',width:100,halign:'center',sortable:true,hidden:true">补充列14</th>
					<th data-options="field:'column15',width:100,halign:'center',sortable:true,hidden:true">补充列15</th>
					<th data-options="field:'column16',width:100,halign:'center',sortable:true,hidden:true">补充列16</th>
					<th data-options="field:'column17',width:100,halign:'center',sortable:true,hidden:true">补充列17</th>
					<th data-options="field:'column18',width:100,halign:'center',sortable:true,hidden:true">补充列18</th>
					<th data-options="field:'column19',width:100,halign:'center',sortable:true,hidden:true">补充列19</th>
					<th data-options="field:'column20',width:100,halign:'center',sortable:true,hidden:true">补充列20</th>
					<th data-options="field:'quantity_of_inwarehouse',width:100,halign:'center',sortable:true,hidden:true">入库数量</th>
					<th data-options="field:'recipients_of_warehouse',width:100,halign:'center',sortable:true,hidden:true">领用出库数量</th>
					<th data-options="field:'waste_materials_outbound',width:120,halign:'center',sortable:true,hidden:true">废旧物资出库数量</th>
					<th data-options="field:'in_allocating_outbound',width:100,halign:'center',sortable:true,hidden:true">调拨出库数量</th>
					<th data-options="field:'construction_of_withdrawing',width:100,halign:'center',sortable:true,hidden:true">建造退库数量</th>
					<th data-options="field:'stock',width:100,halign:'center',sortable:true,hidden:true">库存</th>
					<th data-options="field:'generate_request_number',width:150,halign:'center',sortable:true,hidden:true">生成领料单数量</th>
				</tr>
			</thead>
		</table>
	</div>
	
		<!-- 导入弹出框 -->
	<div id="file_add" class="easyui-dialog pop" style="width:400px;height:350px;padding:10px 20px" closed="true" buttons="#dlg-fielInfo-buttons" maximizable=true modal=true>
		<form id="file_form" method="post" class="edit_form">
			<div class="fitem">
				<label>文件<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="fileL" name="fileL" class="easyui-filebox" data-options="required:true,buttonText:'选择文件'"/>
			</div>
		</form>
    </div>
    <div id="dlg-fielInfo-buttons">
        <a href="javascript:saveFileInfo()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
        <a href="javascript:$('#file_add').dialog('close')" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
    </div>
	
	<script type="text/javascript">
	var currentRow,YN=[{"text":"是","value":"Y"},{"text":"否","value":"N"}],init=true,columns={};
 	$(function(){
 		$("#st_data_track_supp_table").datagrid({
			rownumbers:true,
			pagination:true,
			checkOnSelect:true,  
            onClickRow:function(rowIndex, rowData){
            	currentRow = rowData;
            },
            onLoadSuccess:function(data){
            	$(".search_button").linkbutton();
            	if(!isEmpty(data.rows[0])){
            		for(var key in data.rows[0]){
                		$("#st_data_track_supp_table").datagrid("showColumn",key);
               		}
            	}
            	$("#st_data_track_supp_table").datagrid("hideColumn","id");
            	currentRow=null;
            	if(init){
            		$("#st_data_track_supp_table").datagrid("reload");
            		init=false;
            	}
            	findColumns();
            }
		});
	   	//改变项目工作号 
	   	$("#project_name_search").combobox({
	   		onChange:function(oldValue,newValue){
	   			if(!isEmpty(oldValue)){
	   				findColumns();
	   				removeAll();
	   			}
	   		}
	   	});
 	});
 	
 	
 	
	//（是否因图纸升版增加、作废）
 	function formatValue(key){
 		for(var i=0;i<YN.length;i++){
    		if(YN[i].value==key){
    			return YN[i].text;
    		}
    	}
        return "";
 	}
	
	//根据项目和结构专业查询补充列模块的列名
	function findColumns(){
		var jobNo=$("#project_name_search").combobox("getValue");
 		if(isEmpty(jobNo)){
 			for(var i=1;i<=20;i++){
 	    		$("div[class$=column"+i+"] span:first").html("补充列"+i);
 	    	}
 			return;
 		}
 		project=jobNo;
 		//加载数据前查询对应专业的补充列名称
    	getColumnDisplaySetting("professional_st",jobNo);
 		if(null!=columns){
 			for(var i=1;i<=20;i++){
 	    		if(!isEmpty(columns["column"+i])){
 	    			$("div[class$=column"+i+"] span:first").html(columns["column"+i]);
 	    		}
 	    	}
 		}else{
 			for(var i=1;i<=20;i++){
 	    		$("div[class$=column"+i+"] span:first").html("补充列"+i);
 	    	}
 		}
	}
 	//查询对应专业的补充列名称
 	function getColumnDisplaySetting(code,jobNo){
 		$.ajax({
 			type:"post",
 			url:"${ctx}/columnDisplaySet/getColumnsByMajor.do",
 			async:false,
 			contentType : "application/json;charset=utf-8",
 			data:JSON.stringify({"code":code,"jobNo":jobNo}),
 			success:function(result){
 				columns=result;
			}
 		});
 	}
 	//增加动态查询条件
 	function addStCondition(){
 		var jobNo=$("#project_name_search").combobox("getValue");
 		if(isEmpty(jobNo)){
 			$.messager.show({
				title:"警告",
				msg:"请先选择项目!"
			});
 			return;
 		}
 		addCondition('StDataTrackSupp','professional_st',jobNo);
 	}
 	//删除动态查询条件
 	function removeAll(){
 		var condition = $(".search_all_div div.dynamic");
 		for(var i=0;i<condition.length;i++){
 			removeCondition();
 		}
 	}
 	//显示列设置
 	function stSuppChooseColumnView(){
 		var major="professional_st";
 		showSelfWindow({
 			closed:false,
 			title:"显示列设置",
 			href:"${ctx}/stDataTrackSupp/chooseColumnView.do?major="+major+"&beanName=StDataTrackSupp",
 			width:900,
 			height:500
 		});
 	}
	//查询按钮
	function searchstSupp(){
		condtion={"sort":"part_no","order":"desc"};
		$("#st_data_track_supp_table").datagrid({
			url:"${ctx}/stDataTrackSupp/query.do",
			queryParams:mosaicActivityParams(mosaicParams(condtion))
		});
	}
	//导入
	function importStDTS(){
		$("#file_add").dialog("open").dialog( "setTitle",'添加文件');
		$("#file_form").form("reset");//清空form
	}
	//导入保存
	function saveFileInfo(){
		if($("#file_form").form("validate")){
			$.ajaxFileUpload({
				url:"${ctx}/stDataTrackSupp/importStDTS.do",            //需要链接到服务器地址  
				secureuri:false,  
				fileElementId:$("#fileL").next().find("input[type='file']").attr("id"),                     //文件选择框的id属性  
				data : {},
				dataType: "json",                           //服务器返回的格式  
				success: function (result){
					handleReturn(result,function(){
	 	 				$("#file_add").dialog("close");
	 	 				jumpPage("结构补料数据跟踪表","${ctx}/login/showList.do?url=business/designDataManager/stDataTrackSupp");
	 	 			},function(){
	 	 				$("#file_form").form("reset");
	 	 			  }
					);
				},
				error:function (data, status, e){//服务器响应失败处理函数
					$("#fileL").filebox("clear");
                }
			});
			
		}else{
			$.messager.alert("操作提示","请添加要上传的文件！","warning");
		}
	}
	//编辑
	function toEdit(){
		var selectrow = $("#st_data_track_supp_table").datagrid("getChecked");//获取的是数组，多行数据
		if(1==selectrow.length){
// 			jumpPage("结构补料数据跟踪表","${ctx}/stDataTrackSupp/toEdit.do?id="+currentRow.id);
			showSelfWindow({
				closed:false,
	 			title:"显示列设置",
	 			href:"${ctx}/stDataTrackSupp/toEdit.do?id="+selectrow[0].id,
	 			width:1000,
	 			height:500
	 		});
		}else{
			$.messager.show({
				title:"警告",
				msg:"请选择一行数据!"
			});
		}
	}
	
	//生成领料单
	function makePicking(){
		var selectrow = $("#st_data_track_supp_table").datagrid("getChecked");//获取的是数组，多行数据
		if(isEmpty(selectrow)){
			$.messager.show({
				title:"警告",
				msg:"请选择一行数据!"
			});
		}else{
			var idArry =selectrow[0].id+",";
			var dataArry=[];
			var pk = selectrow[0].job_no+selectrow[0].module_no;
			for(var i=1;i<selectrow.length;i++){
				idArry+=selectrow[i].id+",";
				if(pk!=(selectrow[i].job_no+selectrow[i].module_no)){
					$.messager.show({
						title:"警告",
						msg:"请选择同一个项目同一个模块的数据!"
					});
					return;
				}
			}
			idArry = idArry.substring(0,idArry.length - 1);
			jumpPage("结构领料单","${ctx}/stDataTrackSupp/makePicking.do?idArry="+idArry);
		}
	}
	</script>
</body>
</html>
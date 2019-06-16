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
						<input id="list_no_search" type="text" class="easyui-textbox w250" dkd-search-element="list_no like text"/>
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
					<label class="w80">工作号</label>
					<span class="ml23">
						<input id="job_no_search" type="text" class="easyui-textbox w250" dkd-search-element="job_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">图纸类型</label>
					<span class="ml10">
						<input id="draw_type_search" type="text" class="edit_combobox w250" data-options="width:180,url:'${ctx}/dictionary/searchDictionary.do?code=drawType'" dkd-search-element="draw_type like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">录入人</label>
					<span class="ml23">
						<input id="entity_createuser_search" type="text" class="easyui-textbox w250" dkd-search-element="dh.entity_createuser like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">录入日期</label>
					<span class="ml10">
						<input id="entity_createdate_search" type="text" class="easyui-datebox w100" data-options="editable:false" dkd-search-element="dh.entity_createdate >= text"/>
					</span>
					<label class="w80">至</label>
					<span class="ml10">
						<input id="entity_createdate_search" type="text" class="easyui-datebox w100" data-options="editable:false" dkd-search-element="dh.entity_createdate <= text"/>
					</span>
				</div>
				<div align="center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrawDetail()">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="addDrawDetail('edit')">修改</a>
					<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeDrawDetail()">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDrawDetail()">查询</a>
				</div>
			</div>
		</div>
	</form>
	<div class="datagrid_div">
		<table id="drawDetail_data" align="center" style="width:100%">
			<thead>
				<tr>
					<th data-options="field:'list_no',width:100,halign:'center',sortable:true" rowspan="2">单据编码</th>
					<th data-options="field:'statistical_month',width:80,halign:'center',sortable:true" rowspan="2">统计月份</th>
					<th data-options="field:'major_name',width:100,halign:'center',sortable:true" rowspan="2">专业名称</th>
					<th data-options="field:'project_name',width:100,halign:'center',sortable:true" rowspan="2">项目名称</th>
					<th data-options="field:'job_no',width:100,halign:'center',sortable:true" rowspan="2">项目工作号</th>
					<th data-options="field:'entity_createuser',width:100,halign:'center',sortable:true" rowspan="2">录入人</th>
					<th data-options="field:'entity_createdate',width:100,halign:'center',sortable:true" rowspan="2">录入日期</th>
					<th data-options="field:'draw_type_name',width:100,halign:'center',sortable:true" rowspan="2">图纸类型</th>
					<th data-options="field:'total_draw_quantity',width:120,align:'left',halign:'center',sortable:true" rowspan="2">项目图纸总数量</th>
					<th data-options="width:900,align:'left',halign:'center'" colspan="3">上月完成图纸数量</th>
					<th data-options="width:900,halign:'center'" colspan="3">当月完成图纸数量</th>
					<th data-options="width:900,halign:'center'" colspan="3">当前完成图纸总数量</th>
				</tr>
				<tr>
					<th data-options="field:'pre_draw_forecast',width:140,halign:'center',sortable:true" >计划完成数量(张数)</th>
					<th data-options="field:'pre_draw_actual',width:140,halign:'center',sortable:true" >实际完成数量(张数)</th>
					<th data-options="field:'pre_discrepancy',width:100,halign:'center',sortable:true" >偏差值(%)</th>
					<th data-options="field:'this_draw_forecast',width:140,halign:'center',sortable:true" >计划完成数量(张数)</th>
					<th data-options="field:'this_draw_actual',width:140,halign:'center',sortable:true" >实际完成数量(张数)</th>
					<th data-options="field:'this_discrepancy',width:100,halign:'center',sortable:true" >偏差值(%)</th>
					<th data-options="field:'cumulative_draw_forecast',width:140,halign:'center',sortable:true" >计划完成数量(张数)</th>
					<th data-options="field:'cumulative_draw_actual',width:140,halign:'center',sortable:true" >实际完成数量(张数)</th>
					<th data-options="field:'cumulative_discrepancy',width:120,halign:'center',sortable:true" >偏差值(%)</th>
				</tr>
			</thead>
		</table>
	</div>
    <script type="text/javascript">
    var currentRow;
 	$(function(){
 		$("#drawDetail_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
            url:"${ctx}/drawDetail/query.do",
            queryParams: {          
                "sort":"dh.entity_createdate",
                "order":"desc"
            },
            onClickRow:function(rowIndex, rowData){
            	currentRow = rowData;
            },
            onDblClickRow:function(index,data){
            	findData(data.id);
            },
            onLoadSuccess:function(data){
            	currentRow=null;
            }
		});
 	});
 	function addDrawDetail(no){
 		if(null==currentRow&&!isEmpty(no)){
 			$.messager.show({
				title:"警告",
				msg:"请选择要修改的明细！"
			});
 			return;
 		}
 		if(isEmpty(no)){
 			jumpPage("图纸明细信息添加", "${ctx}/drawDetail/edit.do");
 		}else{
 			jumpPage("图纸明细信息编辑", "${ctx}/drawDetail/edit.do?id="+currentRow.id);
 		}
 	}
 	function searchDrawDetail(){
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
 		$("#drawDetail_data").datagrid("reload",mosaicParams({"sort":"dh.entity_createdate","order":"desc"}));
 	}
 	function findData(id){
 		jumpPage("图纸明细信息查看", "${ctx}/drawDetail/view.do?id="+id);
 	}
 	function removeDrawDetail(){
 		if(null==currentRow){
 			$.messager.show({
				title:"警告",
				msg:"请选择要删除的明细！"
			});
 			return;
 		}
 		$.messager.confirm("是否删除","准备执行数据删除操作，请确认或取消。",function(r){
			if (r){
				$.post("${ctx}/drawDetail/delete.do",{"id":currentRow.cid},function(result){
					handleReturn(result,function(){
						$("#drawDetail_data").datagrid("reload");
					});
				});
			}
		});
 	}
</script>
</body>
</html>
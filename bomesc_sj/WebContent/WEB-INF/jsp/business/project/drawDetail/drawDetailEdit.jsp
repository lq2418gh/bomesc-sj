<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>图纸明细管理</title>
</head>
<body>
	<!-- 编辑页面 -->
	<form id="drawDetail_form" method="post" action="${ctx}/drawDetail/save.do">
		<table width="100%" class="search_table">
			<tr>
				<th>单据编码:</th>
				<td><input id="list_no_edit" name="list_no" size="15" class="easyui-textbox" readonly="readonly"/></td>
				<th>项目名称:</th>
				<td><input id="project_edit" name="project" class="edit_combobox" data-options="width:120,url:'${ctx}/project/show.do'"/>
					<input type="hidden" id="project_name" name="project_name" value="" /></td>
				<th>项目工作号:</th>
				<td><input id="job_no" name="job_no" size="20" class="easyui-textbox" readonly="readonly"/></td>
				<th>专业名称:</th>
				<td><select id="major_edit" name="major" is-object class="easyui-combobox" data-options="width:120,panelHeight:'auto',required:true,editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'"></select></td>
			</tr>
			<tr>
				<th>统计月份:</th>
				<td><input id="statistical_month_edit" name="statistical_month" size="15" class="datebox_month" data-options="required:true,editable:false" /></td>
				<th>录入人:</th>
				<td><input id="entity_createuser_edit" name="entity_createuser" size="15" class="easyui-textbox" readonly="readonly"/></td>
				<th>录入日期:</th>
				<td><input id="entity_createdate_edit" name="entity_createdate" size="11" class="easyui-datetimebox" readonly="readonly"/></td>
			</tr>
			<tr>
				<td colspan="8" style="text-align: center">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveDrawDetail()">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="jumpPage('图纸管理','${ctx}/login/showList.do?url=business/project/drawDetail')">返回</a>
				</td>
			</tr>
			<tr>
				<td colspan="8">
					<center>
						<div id="toolbar_drawDetailEdit" class="dkd-toolbar-wrap">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addSoLine()">增行</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteSoLine()">删行</a>
					    </div>
					</center>
				</td>
			</tr>
		</table>
	</form>
	<div style="padding-left:10px">
		<div class="titleMessage">图纸数量信息-Quantity information of drawing</div>
		<input id="totalNum" type="hidden" name="totalNum" class="easyui-textbox"/>
	    <table id="draw_detail">
			<thead>
				<tr>
					<th data-options="field:'draw_type',width:500,editor:{type:'combobox',options:{valueField:'value',textField:'text',required:true,editable:false}},formatter:getValueByKey" rowspan="2">图纸类型</th>
					<th data-options="field:'total_draw_quantity',width:300,editor:{type:'numberbox',options: {required:true}}" rowspan="2">图纸总数量</th>
					<th data-options="width:800" colspan="3">上月完成图纸数量</th>
					<th data-options="width:800" colspan="3">当月完成图纸数量</th>
					<th data-options="width:800" colspan="3">当前完成图纸数量</th>
				</tr>
				<tr>
					<th data-options="field:'pre_draw_forecast',width:300,editor:{type:'textbox',options: {editable: false,required:true}}">计划完成数量</th>
					<th data-options="field:'pre_draw_actual',width:300,editor:{type:'textbox',options: {editable: false,required:true}}">实际完成数量</th>
					<th data-options="field:'pre_discrepancy',width:200,editor:{type:'textbox',options: {editable: false,required:true}}">偏差值(%)</th>
					<th data-options="field:'this_draw_forecast',width:300,editor:{type:'numberbox',options: {required:true,validType:'length[1,20]'}}">计划完成数量</th>
					<th data-options="field:'this_draw_actual',width:300,editor:{type:'numberbox',options: {required:true,validType:'length[1,20]'}}">实际完成数量</th>
					<th data-options="field:'this_discrepancy',width:200,editor:{type:'textbox',options: {editable: false,required:true}}">偏差值(%)</th>
					<th data-options="field:'cumulative_draw_forecast',width:300,editor:{type:'textbox',options: {editable: false,required:true}}">计划完成数量</th>
					<th data-options="field:'cumulative_draw_actual',width:300,editor:{type:'textbox',options: {editable: false,required:true}}">实际完成数量</th>
					<th data-options="field:'cumulative_discrepancy',width:200,editor:{type:'textbox',options: {editable: false,required:true}}">偏差值(%)</th>
				</tr>
			</thead>
		</table>
    </div>
    <script type="text/javascript">
    var currentRow,data={},drawTypes={},drawTypeChange=true,init=true;
    //初始化页面调用方法
 	$(function(){
 		$("#draw_detail").datagrid({
			rownumbers:true,
            fitColumns:true,
            singleSelect:true,
            toolbar:"#toolbar_drawDetailEdit",
            onClickRow:onClickRow,
            onLoadSuccess:function(data){
            	var rows = $("#draw_detail").datagrid("getRows");
            	for(var i=0;i<rows.length;i++){
            		rowData = $("#draw_detail").datagrid("getRows")[i];
            		rowData.draw_type = {"id":rowData.draw_type};
            		delete rowData.draw_type_name;
            	}
            }
		});
 		//项目下拉框change事件
 		$("#project_edit").combobox({
    		onChange: function (n,o) {
    			//自动填充项目工作号
    			fillProjectNo(n);
    			changeByDrawType();
    			checkValueValidate("project");
    			getTotalNum();
    		}
    	});
 		//专业下拉框change事件
 		$("#major_edit").combobox({
    		onChange: function (n,o) {
    			//自动填充项目工作号
    			changeByDrawType();
    			checkValueValidate("major");
    			getTotalNum();
    		}
    	});
 		//统计时间下拉框change事件
 		$("#statistical_month_edit").datebox({
    		onChange: function (n,o) {
    			//自动填充项目工作号
    			changeByDrawType();
    			checkValueValidate("month");
    		}
    	});
 		//获取字典值(图纸类型)
 		$.ajax({
 	        url: '${ctx}/dictionary/searchDictionary.do?code=drawType',
 	        dataType: 'json',
 	        type: 'POST',
 	        async: false,
 	        success: function (data) {
 	            for (var i = 0; i < data.length; i++) {
 	                var obj = data[i].draw_type;
 	                drawTypes[obj] = data[i].draw_type_name;
 	            }
 	            drawTypes = data;
 	        }
 	    });
		if(isEmpty("${id}")){
			//新增方法
			$("#draw_detail").datagrid("loadData",{total:0,rows:[]});
		}else{
			//编辑方法
			$.post("${ctx}/drawDetail/findByNo.do",{"id":"${id}"},function(result){
				data = result;
				data.project=data.job_no;
		        $("#drawDetail_form").form("load",data);
		        $("#draw_detail").datagrid("loadData",{total:data.drawDetailHeads.length,rows:data.drawDetailHeads});
	 	   });
		}
 	});
    //增加一行
 	function addSoLine(){
 		var project = $("#project_edit").combobox("getValue");
    	var major = $("#major_edit").combobox("getValue");
    	var month = $("#statistical_month_edit").datebox("getValue");
    	if(isEmpty(project)){
    		$.messager.show({
				title:"警告",
				msg:"请选择项目!"
			});
    		return;
    	}
    	if(isEmpty(major)){
    		$.messager.show({
				title:"警告",
				msg:"请选择专业!"
			});
    		return;
    	}
    	if(isEmpty(month)){
    		$.messager.show({
				title:"警告",
				msg:"请选择统计月份!"
			});
    		return;
    	}
		$("#draw_detail").datagrid("appendRow",{"draw_type":{"id":""}});
	}
 	var editIndex;
    //删除一行
 	function deleteSoLine(){
		var rows = $("#draw_detail").datagrid("getSelections");
		if(rows){
			for(var i = 0;i < rows.length;i++){
				 $("#draw_detail").datagrid("deleteRow",$("#draw_detail").datagrid("getRowIndex",rows[i]));
			}
		}
		editIndex = undefined;
		endEditing();
	}
 	//结束编辑
    function endEditing(){
        if (editIndex == undefined){return true;}
        if ($("#draw_detail").datagrid("validateRow", editIndex)){
            $("#draw_detail").datagrid("endEdit", editIndex);
            var rowData = $("#draw_detail").datagrid("getRows")[editIndex];
            rowData.draw_type = {"id":rowData.draw_type};
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    var editors;
    //点击行
 	function onClickRow(index,rowData){
 	    if (editIndex != index){
 	        if (endEditing()){
 	            $("#draw_detail").datagrid("beginEdit", index);
 	            editors= $('#draw_detail').datagrid('getEditors',index);
 	            editIndex = index;
 	            $(".datagrid-editable-input").css({"width":"100%","height":"26px"});
 	            $(".datagrid-editable-input:eq(0)").focus();
 	            init=true;
 	 	 	    bindAction(index,null);
 	        } 
 	    }
 	}
    //明细行绑定事件type用于判断是否为保存时触发的绑定事件
    function bindAction (index,type){
    	var draw_type = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'draw_type'});
 		var draw_type_id = $('#draw_detail').datagrid("getRows")[index].draw_type.id;
 		if(isEmpty(draw_type_id)){
 			draw_type_id="";
 		}
 		$(draw_type.target).combobox({
			onLoadSuccess: function(){
				$(draw_type.target).combobox('setValue',draw_type_id);
				init=false;
			},
			//图纸类型编辑框变化时取上个月的图纸总数量（项目+专业+月份+图纸类型）
			onChange : function(n,o){
	 		    if(!init){
	 		    	getPre(n,index);
	 		    }
	 		    drawTypeChange=false;
			}
		});
		$(draw_type.target).combobox('loadData', drawTypes);
 	 	//当前点击行计划完成数量输入框的change事件
 	 	//图纸当月完成数量
 	 	var thisFor = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'this_draw_forecast'});
 	 	var thisForNum = $('#draw_detail').datagrid("getRows")[index].this_draw_forecast;
 	 	$(thisFor.target).numberbox('setValue',thisForNum);
 	 	$(thisFor.target).numberbox({  
 	    	onChange : function(n,o){
 		    	if(!drawTypeChange||!isEmpty(type)){
 		    		fillPlan(n,index);
 		    	}
 		    }
 		});
 	 	//当月实际完成数量
 		var thisActual = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'this_draw_actual'});
 	 	var thisActualNum = $('#draw_detail').datagrid("getRows")[index].this_draw_actual;
 	 	$(thisActual.target).numberbox('setValue',thisActualNum);
 	 	//当月进度
 	 	var thisPro = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'this_discrepancy'});
 	 	var thisProNum = $('#draw_detail').datagrid("getRows")[index].this_discrepancy;
 	 	$(thisPro.target).textbox('setValue',thisProNum);
 	 	//当前点击行实际完成数量输入框的change事件
 	    $(thisActual.target).numberbox({  
 		    onChange : function(n,o){
 		    	if(!drawTypeChange||!isEmpty(type)){
 		    		fillActual(n,index);
 		    	}
 		    }
 		});
 	 	//图纸总数量输入框
 	 	var totalDraw = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'total_draw_quantity'});
 	 	$(totalDraw.target).numberbox({  
 		    onChange : function(newValue,oldValue){
 		    	if(!drawTypeChange||!isEmpty(type)){
 		    		//检查当前数量是否大于图纸总数量及清除当月数量和当前数量
 		    		checkTotalDrawNum(index,newValue);
 		    	}
 		    }
 		});
    }
    function checkTotalDrawNum(index,newValue){
    	var totalDrawNum = $("#totalNum").textbox("getValue");
    	if(parseFloat(newValue)>parseFloat(totalDrawNum)){
    		$.messager.show({
				title:"警告",
				msg:"当前图纸总数量大于对应项目专业下的图纸总量!"
			});
    		var drawTotal = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'total_draw_quantity'});
    		$(drawTotal.target).numberbox("setValue",null);
    	}
    	if(parseFloat(0)>=parseFloat(newValue)){
    		$.messager.show({
				title:"警告",
				msg:"通知总数量不可小于等于0!"
			});
    		var drawTotal = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'total_draw_quantity'});
    		$(drawTotal.target).numberbox("setValue",null);
    	}
    	//当月计划完成数量
 		var thisFor = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'this_draw_forecast'});
 		//当月实际完成数量
 		var thisActual = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'this_draw_actual'});
 		//当月完成进度
 		var thisPro = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'this_discrepancy'});
 		//当前计划完成数量
 		var cumFor = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'cumulative_draw_forecast'});
 		//当前实际完成数量
 		var cumActual = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'cumulative_draw_actual'});
 		//当前完成进度
 		var cumPro = $('#draw_detail').datagrid('getEditor', { 'index': index,field:'cumulative_discrepancy'});
 		$(thisFor.target).textbox("setValue",null);
 		$(thisActual.target).textbox("setValue",null);
 		$(thisPro.target).textbox("setValue",null);
 		$(cumFor.target).textbox("setValue",null);
 		$(cumActual.target).textbox("setValue",null);
 		$(cumPro.target).textbox("setValue",null);
    }
    //获取上一个月的图纸信息
 	function getPre(drawType,rowNo){
 		//当月计划完成数量
 		var thisFor = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_draw_forecast'});
 		//当月实际完成数量
 		var thisActual = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_draw_actual'});
 		//当月完成进度
 		var thisPro = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_discrepancy'});
 		//当前计划完成数量
 		var cumFor = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'cumulative_draw_forecast'});
 		//当前实际完成数量
 		var cumActual = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'cumulative_draw_actual'});
 		//当前完成进度
 		var cumPro = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'cumulative_discrepancy'});
 		//图纸类型
 		var drawType = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'draw_type'});
 		if(null==thisFor){
 			return;
 		}
 		$(thisFor.target).numberbox('setValue', null);
		$(thisActual.target).numberbox('setValue', null);
		$(thisPro.target).textbox('setValue', null);
		$(cumFor.target).textbox('setValue', null);
		$(cumActual.target).textbox('setValue', null);
		$(cumPro.target).textbox('setValue', null);
 		var project = $("#project_edit").combobox("getValue");
		var major = $("#major_edit").combobox("getValue");
		var month = $("#statistical_month_edit").datebox("getValue");
		var value = $(drawType.target).combobox('getValue');
		var pre;
		$.ajax({
			type:"post",
			async:false,
			contentType : "application/json;charset=utf-8",
 			url:"${ctx}/drawDetail/getPre.do",
 			data:JSON.stringify({"project":project,"major":major,"month":month,"drawType":value}),
 			success:function(result){
 				pre=result;
 			}
		})
		//上月图纸计划完成数量
		var preFor = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'pre_draw_forecast'});
		//上月实际完成数量
		var preDrawActual = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'pre_draw_actual'});
		//上月进度
		var prePro = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'pre_discrepancy'});
		//当月图纸计划完成量
		var thisFor = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_draw_forecast'});
		//当月图纸实际完成量
		var thisDrawActual = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_draw_actual'});
		//当月进度
		var thisPro = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_discrepancy'});
		if(isEmpty(pre)){
			$(preFor.target).textbox('setValue', "0");
			$(preDrawActual.target).textbox('setValue', "0");
			$(prePro.target).textbox('setValue', "0");
			return;
		}
		if(pre.id==""||pre.id==null){
			$(thisFor.target).numberbox('setValue', null);
			$(thisDrawActual.target).numberbox('setValue', null);
			$(thisPro.target).textbox('setValue', null);
		}
		$(preFor.target).textbox('setValue', pre.this_draw_forecast);
		$(preDrawActual.target).textbox('setValue', pre.this_draw_actual);
		$(prePro.target).textbox('setValue', pre.this_discrepancy);
 	}
    //检查是否填写图纸总数量
    function checkTotalNum(rowNo){
    	//图纸总数量
    	var totalDrawQty = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'total_draw_quantity'});
    	var totalDrawQtyNum = $(totalDrawQty.target).textbox("getValue");
    	if(isEmpty(totalDrawQtyNum)){
    		$.messager.show({
				title:"警告",
				msg:"请先填写图纸总数量!"
			});
    		//当月计划完成
     		var thisFor = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:"this_draw_forecast"});
    		$(thisFor.target).textbox("setValue",null);
     		//当月实际完成
     		var thisActual = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_draw_actual'});
     		$(thisActual.target).textbox("setValue",null);
    		return false;
    	}
    	return true;
    }
    //填写本月计划数据时触发的事件
 	function fillPlan(num,rowNo){
    	if(parseFloat(0)>parseFloat(num)){
    		$.messager.show({
				title:"警告",
				msg:"不可输入负数!"
			});
    		//当月计划完成
     		var thisFor = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_draw_forecast'});
    		$(thisFor.target).numberbox("setValue",null);
    		return;
    	}
 		//当月进度
 		var thisPro = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_discrepancy'});
 		//当前进度
 		var cumPro = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'cumulative_discrepancy'});
 		//当前计划完成数量
 		var cumFor = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'cumulative_draw_forecast'});
 		if(isEmpty(num)){
    		$(thisPro.target).textbox("setValue",null);
    		$(cumPro.target).textbox("setValue",null);
    		$(cumFor.target).textbox("setValue",null);
    		return;
    	}
    	if(!checkTotalNum(rowNo)){
    		return;
    	}
 		var project = $("#project_edit").combobox("getValue");
		var major = $("#major_edit").combobox("getValue");
		var month = $("#statistical_month_edit").datebox("getValue");
		//图纸类型
		var drawTypeInput = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'draw_type'});
		var drawType=$(drawTypeInput.target).textbox('getValue');
 		//当月实际完成
 		var thisActual = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_draw_actual'});
 		//当前实际完成数量
 		var cumActual = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'cumulative_draw_actual'});
 		var thisProNum=$(thisPro.target).textbox("getValue");
		var thisActualNum=$(thisActual.target).textbox("getValue");
		var pre;
		$.ajax({
			type:"post",
			async:false,
			contentType : "application/json;charset=utf-8",
 			url:"${ctx}/drawDetail/getTotalPre.do",
 			data:JSON.stringify({"project":project,"major":major,"month":month,"drawType":drawType}),
 			success:function(result){
 				pre=result;
 			}
		})
		//图纸总数量(手动填写的)
		var totalDrawQty= $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'total_draw_quantity'});
		var totalDrawQtyNum = $(totalDrawQty.target).textbox("getValue");
		if(null==thisActualNum||""==thisActualNum){
			$(thisPro.target).textbox('setValue','0');
		}else{
			$(thisPro.target).textbox('setValue',((parseFloat(thisActualNum)-parseFloat(num))/parseFloat(totalDrawQtyNum)*100).toFixed(2));
		}
		//判断当前完成数量是否大于图纸总数
		//var totalNum = parseFloat(num)+parseFloat(isEmpty(pre)?0:pre.cumulative_draw_forecast);
		if(parseFloat(num)>parseFloat(totalDrawQtyNum)){
			$.messager.show({
				title:"警告",
				msg:"当月计划完成数量或当前完成数量不可大于图纸总数量!"
			});
			var thisDrawFor = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_draw_forecast'});
			$(thisDrawFor.target).numberbox("setValue",null);
    		$(thisPro.target).textbox("setValue",null);
    		$(cumFor.target).textbox("setValue",null);
    		return;
		}
		$(cumFor.target).textbox('setValue', parseFloat(num)+parseFloat(isEmpty(pre)?0:pre.cumulative_draw_forecast));
		//当月实际完成数量
		var thisActualNum = $(thisActual.target).textbox("getValue");
		if(isEmpty(thisActualNum)){
			return;
		}
		if(0==(parseFloat(num)+parseFloat(isEmpty(pre)?0:pre.cumulative_draw_forecast))){
			$(cumPro.target).textbox('setValue', 0);
		}else{
			var cumDrawActual = parseFloat(isEmpty(pre)?0:pre.cumulative_draw_actual)+parseFloat(thisActualNum);
			var cumDrawFor = parseFloat(num)+parseFloat(isEmpty(pre)?0:pre.cumulative_draw_forecast);
			$(cumPro.target).textbox('setValue', ((parseFloat(cumDrawActual)-parseFloat(cumDrawFor))/parseFloat(totalDrawQtyNum)*100).toFixed(2));
		}
	}
    //填写本月实际完成图纸数量触发的事件
 	function fillActual(num,rowNo){
 		if(parseFloat(0)>parseFloat(num)){
    		$.messager.show({
				title:"警告",
				msg:"不可输入负数!"
			});
    		//当月实际完成
     		var thisActual = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_draw_actual'});
    		$(thisActual.target).numberbox("setValue",null);
    		return;
    	}
 		//当月进度
 		var thisPro = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_discrepancy'});
 		//当前实际完成数量
 		var cumActual = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'cumulative_draw_actual'});
 		//当前进度
 		var cumPro = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'cumulative_discrepancy'});
 		if(isEmpty(num)){
 			$(thisPro.target).textbox("setValue",null);
 			$(cumActual.target).textbox("setValue",null);
 			$(cumPro.target).textbox("setValue",null);
    		return;
    	}
 		if(!checkTotalNum(rowNo)){
    		return;
    	}
 		//当月计划完成
 		var thisFor = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_draw_forecast'});
 		//当前计划完成数量
 		var cumFor = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'cumulative_draw_forecast'});
 		var thisProNum=$(thisPro.target).textbox("getValue");
		var thisForNum=$(thisFor.target).textbox("getValue");
		var cumForNum=$(cumFor.target).textbox("getValue");
		var cumProNum=$(cumPro.target).textbox("getValue");
		var project = $("#project_edit").combobox("getValue");
		var major = $("#major_edit").combobox("getValue");
		var month = $("#statistical_month_edit").datebox("getValue");
		//当前完成进度
		var drawTypeInput = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'draw_type'});
		var drawType=$(drawTypeInput.target).textbox('getValue');
		var pre;
		$.ajax({
			type:"post",
			async:false,
			contentType : "application/json;charset=utf-8",
 			url:"${ctx}/drawDetail/getTotalPre.do",
 			data:JSON.stringify({"project":project,"major":major,"month":month,"drawType":drawType}),
 			success:function(result){
 				pre=result;
 			}
		})
		$(cumActual.target).textbox('setValue', parseFloat(num)+parseFloat(isEmpty(pre)?0:pre.cumulative_draw_actual));
		//图纸总数量(手动填写的)
		var totalDrawQty= $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'total_draw_quantity'});
		var totalDrawQtyNum = $(totalDrawQty.target).textbox("getValue");
		//判断当前完成数量是否大于图纸总数
		//var totalNum = parseFloat(num)+parseFloat(isEmpty(pre)?0:pre.cumulative_draw_actual);
		if(parseFloat(num)>parseFloat(totalDrawQtyNum)){
			$.messager.show({
				title:"警告",
				msg:"当月实际完成数量或当前实际完成数量不可大于图纸总数量!"
			});
			var thisDrawActual = $('#draw_detail').datagrid('getEditor', { 'index': rowNo,field:'this_draw_actual'});
			$(thisDrawActual.target).textbox("setValue",null);
    		$(thisPro.target).textbox("setValue",null);
    		$(cumActual.target).textbox("setValue",null);
    		return;
		}
		if(isEmpty(thisForNum)){
			return;
		}
		$(thisPro.target).textbox('setValue',((parseFloat(num)-parseFloat(thisForNum))/parseFloat(totalDrawQtyNum)*100).toFixed(2));
		if(0==parseFloat(num)+parseFloat(isEmpty(pre)?0:pre.cumulative_draw_actual)){
			$(cumPro.target).textbox('setValue',"0");
		}else{
			var cumDrawActual=parseFloat(num)+parseFloat(isEmpty(pre)?0:pre.cumulative_draw_actual);
			$(cumPro.target).textbox('setValue', ((parseFloat(cumDrawActual)-parseFloat(cumForNum))/(parseFloat(totalDrawQtyNum))*100).toFixed(2));
		}
	}
    //保存图纸明细
 	function saveDrawDetail(){
    	init=true;
 		endEditing();
 		if(!$("#drawDetail_form").form("validate")){
			return;
		}
 		var rows = $("#draw_detail").datagrid("getRows");
 		if(rows.length==0){
 			$.messager.show({
				title:"警告",
				msg:"请增加明细！"
			});
			return;
 		}
 		for(var i=0;i<rows.length;i++){
			if(!$("#draw_detail").datagrid("validateRow",i)){
				return;
			}
			var newData = $("#draw_detail").datagrid("getRows")[i];
			onClickRow(i,newData);
			//检验图纸类型是否填写
			var drawType = $('#draw_detail').datagrid('getEditor', { 'index': i,field:'draw_type'});
			var drawTypeNum = $(drawType.target).textbox("getValue");
			if(isEmpty(drawTypeNum)||{}==drawTypeNum){
				$.messager.show({
					title:"警告",
					msg:"图纸类型为空,请选择!"
				});
				bindAction(i,"save");
				return;
			}
			for(var j=i+1;j<rows.length;j++){
				var newDataRow = $("#draw_detail").datagrid("getRows")[j];
				onClickRow(j,newDataRow)
				//检验图纸类型是否填写
				var newDrawType = $('#draw_detail').datagrid('getEditor', { 'index': j,field:'draw_type'});
				var newDrawTypeNum = $(newDrawType.target).textbox("getValue");
				if(isEmpty(newDrawTypeNum)||{}==newDrawTypeNum||newDrawTypeNum==drawTypeNum){
					bindAction(j,"save");
					$.messager.show({
						title:"警告",
						msg:"图纸类型为空或图纸类型重复！请重新选择!"
					});
					return;
				}
				endEditing();
			}
 		}
		//存储个类型图纸数量，用于与图纸总数比较
 		var drawTypeTotal=0;
		//便利明细是否填值
		for(var i=0;i<rows.length;i++){
			if(!$("#draw_detail").datagrid("validateRow",i)){
				return;
			}
			var newData = $("#draw_detail").datagrid("getRows")[i];
			onClickRow(i,newData)
			editIndex=i;
			//检验图纸类型是否填写
			var drawType = $('#draw_detail').datagrid('getEditor', { 'index': i,field:'draw_type'});
			var drawTypeNum = $(drawType.target).textbox("getValue");
			if(isEmpty(drawTypeNum)||{}==drawTypeNum){
				bindAction(i,"save");
				$.messager.show({
					title:"警告",
					msg:"请选择图纸类型！"
				});
				return;
			}
			//图纸总数量
			var totalDraw= $('#draw_detail').datagrid('getEditor', { 'index': i,field:'total_draw_quantity'});
			var totalDrawNum = $(totalDraw.target).textbox("getValue");
			if(isEmpty(totalDrawNum)||0==parseFloat(totalDrawNum)){
				$.messager.show({
					title:"警告",
					msg:"请填写图纸总数量！"
				});
				return;
			}
			//当月计划完成适量
			var thisFor= $('#draw_detail').datagrid('getEditor', { 'index': i,field:'this_draw_forecast'});
			var thisForNum = $(thisFor.target).textbox("getValue");
			if(isEmpty(thisForNum)||0==parseFloat(thisForNum)){
				$.messager.show({
					title:"警告",
					msg:"请填写当月计划完成图纸数量！"
				});
				return;
			}
			//当月计划完成适量
			var thisActual= $('#draw_detail').datagrid('getEditor', { 'index': i,field:'this_draw_actual'});
			var thisActualNum = $(thisActual.target).textbox("getValue");
			if(isEmpty(thisActual)||0==parseFloat(thisActualNum)){
				$.messager.show({
					title:"警告",
					msg:"请填写当月实际完成图纸数量！"
				});
				return;
			}
			endEditing();
			drawTypeTotal=parseFloat(drawTypeTotal)+parseFloat(totalDrawNum);
		}
		var newData0 = $("#draw_detail").datagrid("getRows")[0];
		onClickRow(0,newData0);
		editIndex=0;
		//项目中的图纸数量
		var projectTotalQtyNum=$("#totalNum").textbox("getValue");
		if(parseFloat(projectTotalQtyNum)<drawTypeTotal){
			$.messager.show({
				title:"警告",
				msg:"图纸数量之和大于项目中的图纸总数量!请确认!"
			});
			endEditing();
			return;
		}
 		//获取所有选项，拼接
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
 		$.ajax({
 			type:"post",
 			url:"${ctx}/drawDetail/save.do",
 			contentType : "application/json;charset=utf-8",
 			data:getData("drawDetail_form",data,"drawDetailHeads",rows),
 			success:function(result){
 				handleReturn(result,function(){
 					jumpPage("图纸明细信息查看", "${ctx}/drawDetail/view.do?id="+result.id);
 				});
			}
 		});
 	}
    //自动填充项目工作号及项目名称
 	function fillProjectNo(n,o){
 		$("#project_name").val($("#project_edit").combobox("getText"));
 		$("#job_no").textbox("setValue",n);
 	}
    //格式化字典id为name
    function getValueByKey(key) {
    	for(var i=0;i<drawTypes.length;i++){
    		if(drawTypes[i].value==key){
    			return drawTypes[i].text;
    		}
    	}
        return "";
    }
    //更改表头项目、专业、时间时更改表体的数据
    function changeByDrawType(){
    	var rows = $("#draw_detail").datagrid("getRows");
    	var rowNum=rows.length;
		if(rows){
			for(var i = 0;i < rowNum;i++){
				 $("#draw_detail").datagrid("deleteRow",$("#draw_detail").datagrid("getRowIndex",rows[i]));
			}
		}
		editIndex=undefined;
    }
    //检查当前月是否已经录入
    function checkValueValidate(type){
    	var project = $("#project_edit").combobox("getValue");
    	var major = $("#major_edit").combobox("getValue");
    	var month = $("#statistical_month_edit").datebox("getValue");
    	if(isEmpty(project)){
    		return;
    	}
    	if(isEmpty(major)){
    		return;
    	}
    	if(isEmpty(month)){
    		return;
    	}
    	if(checkProjectValidate(project)){
			$.messager.show({
				title:"警告",
				msg:"该项目未在系统中录入，请确认！"
			});
			$("#project_edit").combobox("setValue","");
			$("#project_name").val(null);
			$("#job_no").textbox("setValue",null);
			return;
		}
    	var flag=true;
    	$.ajax({
    		type:"post",
    		contentType : "application/json;charset=utf-8",
    		async:false,
 			url:"${ctx}/drawDetail/checkValidate.do",
 			data:JSON.stringify({"project":project,"major":major,"month":month,"id":isEmpty(data.id)?"":data.id}),
 			success:function(result){
 				flag = result;
			}
    	})
    	if(flag){
    		$.messager.show({
				title:"警告",
				msg:"本月已存在相同项目、相同专业的单据，请检查"
			});
    		if("project"==type){
    			$("#project_edit").combobox("setValue","");
    		}else if("major"==type){
    			$("#major_edit").combobox("setValue","");
    		}else{
    			$("#statistical_month_edit").datebox("setValue",null);
    		}
 			return;
    	}
    }
    //选择转恶业和项目后查询项目中的图纸总数量
    function getTotalNum(){
    	var project= $("#project_edit").combobox("getValue");
    	var major = $("#major_edit").combobox("getValue");
    	if(isEmpty(project)){
    		return;
    	}
    	if(isEmpty(major)){
    		return;
    	}
    	var totalNum;
    	$.ajax({
    		type:"post",
    		contentType : "application/json;charset=utf-8",
    		async:false,
 			url:"${ctx}/drawDetail/getTotalNum.do",
 			data:JSON.stringify({"project":project,"major":major}),
 			success:function(result){
 				totalNum = result;
			}
    	})
    	$("#totalNum").textbox("setValue",totalNum);
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
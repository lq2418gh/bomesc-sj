<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>材料申购单管理</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div>
				<div class="search_item">
					<label class="w80">申购单号</label>
					<span class="ml10">
						<input id="mto_no_search" type="text" class="easyui-textbox w250" dkd-search-element="mto_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">项目名称</label>
					<span class="ml10">
						<input id="project_name_search" name="project_name" class="edit_combobox" data-options="width:250,url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">工作号</label>
					<span class="ml10">
						<input id="job_no_search" type="text" class="easyui-textbox w250" dkd-search-element="job_no like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">录入人</label>
					<span class="ml23">
						<input id="entity_createuser_search" type="text" class="easyui-textbox w250" dkd-search-element="bmh.entity_createuser like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">采购日期</label>
					<span class="ml10">
						<input id="purchase_date_start_search" type="text" class="easyui-datebox w100" dkd-search-element="purchase_date >= text"/>
					</span>
					<label class="w80 ml10">至</label>
					<span class="ml20">
						<input id="purchase_date_end_search" type="text" class="easyui-datebox w100" dkd-search-element="purchase_date <= text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">状态</label>
					<span class="ml23">
						<select id="state_search" class="easyui-combobox w250" dkd-search-element="state = text" data-options="editable:false,panelHeight:'auto'">
						    <option value="">请选择</option>
   							<option value="新建">新建</option>
   							<option value="确认">确认</option>
						</select>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">专业名称</label>
					<span class="ml10">
						<input id="major_search" name="major" size="13" class="easyui-combobox w250" data-options="width:245,panelHeight:'auto',editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'" dkd-search-element="major = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">&nbsp;采购类别</label>
					<span class="ml10">
						<select id="purchase_type_search" class="easyui-combobox w250" dkd-search-element="purchase_type = text" data-options="editable:false,panelHeight:'auto'">
						    <option value="">请选择</option>
   							<option value="正常采办">正常采办</option>
   							<option value="应急采办">应急采办</option>
						</select>
					</span >
				</div>
				<div class="search_item">
					<label class="w80">确认人</label>
					<span class="ml13">
						<input id="confirm_user_search" type="text" class="easyui-textbox w250" dkd-search-element="bmh.confirm_user like text"/>
					</span>
				</div>
				<div align="center">
					<a href="javascript:searchMtoHead()" class="easyui-linkbutton" iconCls="icon-search" >查询</a>
					<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-clear" >重置</a>
					<a href="javascript:importMto()" class="easyui-linkbutton" iconCls="icon-save" >导入</a>
					<a href="javascript:mtoState('confirm')" class="easyui-linkbutton" iconCls="icon-redo" >确认</a>
					<a href="javascript:mtoState('concelConfirm')" class="easyui-linkbutton" iconCls="icon-undo" >取消确认</a>
					<a href="javascript:deleteMto()" class="easyui-linkbutton" iconCls="icon-cancel" >删除</a>
				</div>
			</div>
		</div>
	</form>
	<div class="datagrid_div">
		<table id="mto_head_data" align="center" style="width:100%">
			<thead>
				<tr>
					<th data-options="field:'mto_no',width:170,align:'center'" >申购单编号</th>
					<th data-options="field:'state',width:100,align:'center'" >申购单状态</th>
					<th data-options="field:'purchase_company',width:100,align:'center'" >申购部门</th>
					<th data-options="field:'project_name',width:100,align:'center'" >项目名称</th>
					<th data-options="field:'job_no',width:100,align:'center'" >项目工作号</th>
					<th data-options="field:'major_name',width:100,align:'center'">专业名称</th>
					<th data-options="field:'purchase_type',width:100,align:'center'">采购类别</th>
					<th data-options="field:'emergency_reason',fitColumns: true,align:'center'">应急采购原因</th>
					<th data-options="field:'purchase_tech',fitColumns: true,align:'center'">采购技术要求</th>
					<th data-options="field:'if_use_stock',width:100,align:'center',formatter:formatIf">是否使用库存</th>
					<th data-options="field:'if_check_stock',width:100,align:'center',formatter:formatIf">是否检查库存</th>
					<th data-options="field:'stock_memo',fitColumns: true,align:'center'">库存使用说明</th>
					<th data-options="field:'purchase_date',width:100,align:'center'">采购日期</th>
					<th data-options="field:'entity_createuser',width:100,align:'center'">导入人</th>
					<th data-options="field:'entity_createdate',width:130,align:'center'">导入日期</th>
					<th data-options="field:'confirm_user',width:100,align:'center'">确认人</th>
					<th data-options="field:'confirm_date',width:130,align:'center'">确认日期</th>					
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
	var currentId;
 	var currentState;
 	$(function(){
 		$("#mto_head_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
            url:"${ctx}/mto/mtoHeadList.do",
            onClickRow:function(rowIndex, rowData){
            	currentId=rowData.id;
            	currentState=rowData.state;
            },
            onLoadSuccess:function(){
            	currentId="";
            },
            onDblClickRow:function(index,data){
            	jumpPage("订单查看","${ctx}/mto/view.do?mto_no=" + data.mto_no);
            }
		});
 	});
 	//查询方法
 	function searchMtoHead(){
 		var purchase_date_start = $("#purchase_date_start_search").datebox("getValue");
 		var purchase_date_end = $("#purchase_date_end_search").datebox("getValue");
 		var flag = timeCompare(purchase_date_start,purchase_date_end);
		if(flag == '1'){
			$("#purchase_date_start").datebox("setValue","");
			$("#purchase_date_end").datebox("setValue","");
			$.messager.alert("操作提示","采购日期开始时间不能大于采购日期结束时间，请检查！","warning");
			return false;
		}
 		$("#mto_head_data").datagrid("reload",mosaicParams());
 	}
 	//格式化方法
	function formatIf(val,row,index){
		if("Y"==val){
			return "是";
		}
		if("N"==val){
			return "否";
		}
	}
 	//时间比较
	function timeCompare(aTime, bTime) {
		var flag = "0";
	    var arr = aTime.split("-");
	    var starttime = new Date(arr[0], arr[1], arr[2]);
	    var starttimes = starttime.getTime();

	    var arrs = bTime.split("-");
	    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
	    var lktimes = lktime.getTime();

	    if (starttimes > lktimes) {
	        flag = "1";
	    }else{
	    	flag = "0";
	    }
		return flag;
	}
	//导入弹出框
	function importMto(){
		$("#file_add").dialog("open").dialog("setTitle","添加文件");//弹出框打开
		$("#file_form").form("reset");//清空form
	}
	//导入功能
	function saveFileInfo(){
 		if($("#file_form").form("validate")){
 			var success=false;
 			$.ajaxFileUpload({
				url:"${ctx}/mto/poi.do",            //需要链接到服务器地址  
				secureuri:false,  
				fileElementId:$("#fileL").next().find("input[type='file']").attr("id"),                     //文件选择框的id属性  
				data : {},
				dataType: "json",                           //服务器返回的格式  
				success: function (result){
					handleReturn(result,function(){
							$("#file_form").form("reset");
							$("#file_add").dialog("close");
	 	 					jumpPage("订单查看","${ctx}/mto/view.do?mto_no=" + result.id+"&msg=import_success");//这里result的id字段里放了mtoNo
	 	 				},function(){
							$("#file_form").form("reset");
	 	 				});
				},
				error:function (data, status, e){//服务器响应失败处理函数
					$("#file_add").dialog("close");
					$("#fileL").filebox("clear");
                }
			});
 		}else{
 			$.messager.alert("操作提示","请添加要上传的文件！","warning");
 		}
	}
	//删除功能
	function deleteMto(){
		if(!currentId){
			$.messager.show({
				title:"警告",
				msg:"请选择要操作的单据！"
			});
			return;
		}
		if(currentState=="确认"){
			$.messager.show({
				title:"警告",
				msg:"确认状态的材料申购单不可删除！"
			});
			return;
		}
		$.messager.confirm("是否删除","准备执行数据删除操作，请确认或取消。",function(r){
		    if (r){
		    	$.post("${ctx}/mto/deleteMto.do",{"id":currentId},function(result){
		    		handleReturn(result,function(){
		    			currentId = "";
		    			$("#mto_head_data").datagrid("reload");
 	 				});
		 		});
		   }
		});
	}
	//申购单状态改变
	function mtoState(param){
 		if(!currentId){
 			$.messager.show({
				title:"警告",
				msg:"请选择要操作的单据！"
			});
 			return;
 		}
		if(currentState=="确认"&&param=="confirm"){
			$.messager.show({
				title:"警告",
				msg:"该单据已确认，请检查！"
			});
			return;
		}
		if(currentState=="新建"&&param=="concelConfirm"){
			$.messager.show({
				title:"警告",
				msg:"该单据为新建单据，请检查！"
			});
			return;
		}
 		$.messager.confirm('状态更改','正在进行'+(param=="confirm"?"确认":"取消确认")+'操作,是否确认！',function(r){
 			if (r){
 		 		$.post("${ctx}/mto/mtoState.do",{"id":currentId,"opr":param},function(result){
 		 			handleReturn(result,function(){
 		 				$("#mto_head_data").datagrid("reload",mosaicParams());
 		 			});
 		 		});
 			}
 		});
	}
</script>
</body>
</html>
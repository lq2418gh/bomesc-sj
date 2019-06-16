<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>导入失败记录提醒</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div>
				<div class="search_item">
					<label class="w80">提示日期</label>
					<span class="ml10">
						<input id="prompt_date_start_search" type="text" class="easyui-datebox w100" dkd-search-element="prompt_date >= text"/>
					</span>
					<label class="w70 ml10">至</label>
					<span class="ml20">
						<input id="prompt_date_end_search" type="text" class="easyui-datebox w100" dkd-search-element="prompt_date <= text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">人员编号</label>
					<span class="ml10">
						<input id="job_number_search" type="text" class="easyui-textbox w250" dkd-search-element="job_number like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">邮箱</label>
					<span class="ml10">
						<input id="email_search" type="text" class="easyui-textbox w250" dkd-search-element="email like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">项目名称</label>
					<span class="ml10">
						<input id="project_name_search" name="project_name" class="edit_combobox" data-options="width:250,url:'${ctx}/project/show.do'" dkd-search-element="job_no = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">专业名称</label>
					<span class="ml10">
						<input id="major_search" name="major" size="13" class="easyui-combobox w250" data-options="width:250,panelHeight:'auto',editable:false,url:'${ctx}/dictionary/searchDictionary.do?code=professional'" dkd-search-element="major = text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">模块</label>
					<span class="ml10">
						<input id="module_search" type="text" class="easyui-textbox w250" dkd-search-element="module like text"/>
					</span>
				</div>
				<div class="search_item">
					<label class="w80">工作号&nbsp;&nbsp;&nbsp;</label>
					<span class="ml10">
						<input id="job_no_search" type="text" class="easyui-textbox w250" dkd-search-element="job_no like text"/>
					</span>
				</div>
				<div align="center">
					<a href="javascript:searchRecord()" class="easyui-linkbutton" iconCls="icon-search" >查询</a>
					<a href="javascript:showContent()" class="easyui-linkbutton" iconCls="icon-tip" >提示内容</a>
					<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-clear" >重置</a>
				</div>
			</div>
		</div>
	</form>
	<div class="datagrid_div">
		<table id="record_data" align="center" style="width:100%">
			<thead>
				<tr>
					<th data-options="field:'prompt_date',width:100,align:'center'" >提醒日期</th>
					<th data-options="field:'job_number',width:100,align:'center'" >人员编号</th>
					<th data-options="field:'manager_user_name',width:100,align:'center'" >提示人员</th>
					<th data-options="field:'major_name',width:200,align:'center'" >专业名称</th>
					<th data-options="field:'project_name',width:180,align:'center'" >项目名称</th>
					<th data-options="field:'job_no',width:200,align:'center'" >项目工作号</th>
					<th data-options="field:'email',width:200,align:'center'" >发送邮箱</th>
					<th data-options="field:'prompt_content',width:100,align:'center',hidden:'true'">提示内容</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="prompt_content_view" class="easyui-dialog pop" style="width:800;height:250;top:100px;padding:10px 20px;overflow:auto;" closed="true" buttons="#dlg-failInfo-buttons" maximizable=true modal=true>
		<form id="prompt_content_form_view" >
			<table class="view_table">
				<tr>
					<td column="prompt_content" ></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-failInfo-buttons" style="margin:0 auto">
    	<a class="easyui-linkbutton" iconCls="icon-back" onclick="$('#prompt_content_view').dialog('close')">返回</a>
    </div>
    <script type="text/javascript">
    var currentRow;
 	$(function(){
 		$("#record_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
            url:"${ctx}/lackMaterial/recordList.do",
            onLoadSuccess:function(){
            	currentRow="";
            },
            onClickRow:function(rowIndex, rowData){
            	currentRow=rowData;
            },
		});
 	});
 	//查询方法
 	function searchRecord(){
 		var prompt_date_start = $("#prompt_date_start_search").datebox("getValue");
 		var prompt_date_end = $("#prompt_date_end_search").datebox("getValue");
 		var flag = timeCompare(prompt_date_start,prompt_date_end);
		if(flag == '1'){
			$("#prompt_date_start_search").datebox("setValue","");
			$("#prompt_date_end_search").datebox("setValue","");
			$.messager.alert("操作提示","提醒日期开始时间不能大于提醒日期结束时间，请检查！","warning");
			return false;
		}
 		$("#record_data").datagrid("reload",mosaicParams());
 	}
 	//查看提示内容
 	function showContent(){
 		if(!currentRow){
			$.messager.show({
				title:"警告",
				msg:"请选择要查看的行！"
			});
 		}else{
 			$('#prompt_content_view').dialog('open').dialog("setTitle","提示信息");
 			loadData(currentRow);
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
</script>
</body>
</html>
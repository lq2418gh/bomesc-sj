<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>日志管理</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div class="search_all_div">
				<div class="search_item">
					<label class="w80">业务模块</label>
					<span class="ml10">
						<input id="entity_group" type="text" class="easyui-textbox w250" dkd-search-element="entity_group like text"/>
					</span>
				</div>
			</div>
			<div class="search_button" style="margin-top:5px;width:100%;text-align:center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchLog()">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="resetSearch()">重置</a>
			</div>
		</div>
	</form>
	<div class="datagrid_div">
		<table id="log_data" align="center">
			<thead>
				<tr>
					<th data-options="field:'entity_group',width:30,sortable:true">业务模块</th>
					<th data-options="field:'entity_type',width:30">功能模块</th>
					<th data-options="field:'operater_type',width:30,sortable:true">操作</th>
					<th data-options="field:'entity_code',width:40,sortable:true">单据编号</th>
					<th data-options="field:'description',width:90,sortable:true">操作详情</th>
					<th data-options="field:'ip_address',width:20,sortable:true">IP</th>
					<th data-options="field:'user_uame',width:20,sortable:true">操作人</th>
					<th data-options="field:'operate_time',width:30,sortable:true">操作时间</th>
				</tr>
			</thead>
		</table>
	</div>
    <script type="text/javascript">
    var data,id;
 	$(function(){
 		$("#log_data").datagrid({
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			fitColumns:true,
            url:"${ctx}/log/query.do"
		});
 	});
 	function searchLog(){
 		$("#log_data").datagrid("reload",mosaicParams());
 	}
</script>
</body>
</html>
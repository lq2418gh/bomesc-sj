<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>文件管理</title>
</head>
<body>
	<form class="search_form dkd-search">
		<div class="search_div">
			<div class="search_all_div">
				<div class="search_item">
					<label class="w80">文件名称</label>
					<span class="ml10">
						<input id="file_name_search" type="text" class="easyui-textbox w250" dkd-search-element="file_name like text"/>
					</span>
				</div>
			</div>
			<div class="search_button" style="margin-top:5px;width:100%;text-align:center">
				<a href="javascript:searchFileInfo()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
				<a href="javascript:resetSearch()" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
				<a href="javascript:newFileInfo()" class="easyui-linkbutton" iconCls="icon-add">上传文件</a>
			</div>
		</div>
	</form>
	<div style="padding-left:10px;padding-bottom:10px">
		<table id="fileInfo_data" align="center">
			<thead>
				<tr>
					<th data-options="field:'file_name',width:300">文件名称</th>
					<th data-options="field:'entity_createdate',width:100">上传日期</th>
					<th data-options="field:'entity_createuser',width:100">上传人</th>
					<th data-options="field:'_operate',width:180,formatter:formatFileInfoOper">操作</th>
				</tr>
			</thead>
		</table>
	</div>
    <!-- 上传文件弹出框 -->
	<div id="fileInfo_add" class="easyui-dialog pop" style="width:400px;height:350px;padding:10px 20px" closed="true" buttons="#dlg-fielInfo-buttons" maximizable=true modal=true>
		<form id="fileInfo_form" method="post" class="edit_form">
			<div class="fitem">
				<label>文件<font color="red" style="vertical-align: sub">&nbsp;*</font>：</label>
				<input id="fileL" name="fileL" class="easyui-filebox" data-options="required:true,buttonText:'选择文件'"/>
			</div>
		</form>
    </div>
    <div id="dlg-fielInfo-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveFileInfo()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#fileInfo_add').dialog('close')">取消</a>
    </div>
    <!-- 上传弹出框 -->
    <script type="text/javascript">
	    var data,id;
	    var initParams = {entity_code:"${entity_code}",entity_id:"${entity_id}"};
	 	$(function(){
	 		$("#fileInfo_data").datagrid({
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				fitColumns:true,
	            url:"${ctx}/fileInfo/query.do",
	            queryParams:initParams,
	            onLoadSuccess:function(data){
	            	$(".editButton").linkbutton();
				}
			});
	 	});
	 	function formatFileInfoOper(val,row,index){
	 		//"<a class=\"editButton m5\" href=\"javascript:void(0)\" iconCls=\"icon-save\" onclick=\"downloadFile('" + row.path + "','" + row.file_name + "')\">下载</a>" +
	 	    return "<a class=\"editButton m5\" href=\"javascript:void(0)\" iconCls=\"icon-save\" onclick=\"downloadFile('" + row.id + "')\">下载</a>" +
	 	    	"<a class=\"editButton m5\" href=\"javascript:void(0)\" iconCls=\"icon-cancel\" onclick=\"deleteFileInfo('" + row.id + "')\">删除</a>";
	 	}
	 	function deleteFileInfo(fileInfoId){
	 		$.messager.confirm("是否删除","是否确认删除？",function(r){
 			    if (r){
 			    	$.post("${ctx}/fileInfo/delete.do",{"id":fileInfoId},function(result){
 			    		handleReturn(result,function(){
 			    			$("#fileInfo_data").datagrid("reload");
 	 	 				});
 			 		});
 			    }
 			});
	 	}
	 	function searchFileInfo(){
	 		$("#fileInfo_data").datagrid("reload",mosaicParams(initParams));
	 	}
	 	function newFileInfo(){
	 		$("#fileInfo_add").dialog("open").dialog("setTitle","添加文件");
	 		$("#fileInfo_form").form("reset");
	 		data = initParams;
	 	}
	 	function saveFileInfo(){
	 		if($("#fileInfo_form").form("validate")){
	 			$.ajaxFileUpload({
					url:"${ctx}/fileInfo/save.do",            //需要链接到服务器地址  
					secureuri:false,  
					fileElementId:$("#fileL").next().find("input[type='file']").attr("id"),                     //文件选择框的id属性  
					data : data,
					dataType: "json",                           //服务器返回的格式  
					success: function (result){
						handleReturn(result,function(){
 	 	 					$("#fileInfo_add").dialog("close");
 	 	    				$("#fileInfo_data").datagrid("reload");
 	 	 				});
					},
					error:function (data, status, e)//服务器响应失败处理函数
                    {
						$("#fileL").filebox("clear");
                    }
				});
	 		}
		}
	 	function downloadFile(id){
	 		location.href = "${ctx}/fileInfo/download.do?id=" + id;
	 		/* path,name
	 		var url = uploadUrl + path;
	 		if(browser && browser != "IE"){
	 			var a = $("<a href='" + url + "' download='" + name + "'></a>");
	 			$("body").append(a);
	 			a[0].click();
	 			a.remove();
	 		}else{
	 			fileName = name;
		 		$("#fileFrame").attr("src",url);
	 		} 
	 		var fileName;
		 	$("#fileFrame").on("load",function(){
		 		console.log($("#fileFrame")[0].contentDocument.execCommand);
		 		console.log(fileName);
				$("#fileFrame")[0].contentDocument.execCommand("SaveAs",false,fileName);
	 		});
	 		*/
	 	}
	</script>
</body>
</html>
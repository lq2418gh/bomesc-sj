<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>结构数据跟踪表显示列配置</title>
</head>
<body>
	<form class="search_form">
		<table id="myTable" class="view_table" style="width:90%">
			
		</table>
	</form>
    <script type="text/javascript">
	    var columns;
	 	$(function(){
	    	$.ajax({
	    		type:"get",
	    		async:false,
	    		url:"${ctx}/login/searchColumnName.do?beanName="+"${beanName}"+"&professional="+"${major}",
	    		success:function(result){
	    			columns=result;
	    		}
	    	})
	    	var htmlText="";
	    	htmlText+="<tr><td><input name=\"radio\" type=\"radio\" onClick=\"checkAll()\"/>全选";
	    	htmlText+="<input name=\"radio\" type=\"radio\" onClick=\"inverse()\"/>反选";
	    	htmlText+="<input name=\"radio\" type=\"radio\" onClick=\"inverseAll()\"/>全不选</td></tr>";
	    	for(var i=0;i<columns.length;i=i+5){
	    		if(!isEmpty(columns[i])){
	    			htmlText+="<tr><td><input name=\"column\" type=\"checkbox\" "+checkChecked(columns[i].check)+" value=\""+columns[i].value+"\"/><label>"+columns[i].text+"</label></td>"
	    		}
				if(!isEmpty(columns[i+1])){
					htmlText+="<td><input name=\"column\" type=\"checkbox\" "+checkChecked(columns[i+1].check)+" value=\""+columns[i+1].value+"\"/><label>"+columns[i+1].text+"</label></td>"
	    		}
				if(!isEmpty(columns[i+2])){
					htmlText+="<td><input name=\"column\" type=\"checkbox\" "+checkChecked(columns[i+2].check)+" value=\""+columns[i+2].value+"\"/><label>"+columns[i+2].text+"</label></td>"
	    		}
				if(!isEmpty(columns[i+3])){
					htmlText+="<td><input name=\"column\" type=\"checkbox\" "+checkChecked(columns[i+3].check)+" value=\""+columns[i+3].value+"\"/><label>"+columns[i+3].text+"</label></td>"
	    		}
				if(!isEmpty(columns[i+4])){
					htmlText+="<td><input name=\"column\" type=\"checkbox\" "+checkChecked(columns[i+4].check)+" value=\""+columns[i+4].value+"\"/><label>"+columns[i+4].text+"</label></td></tr>"
	    		}
	    	}
	    	htmlText+="<tr><td colspan=\"5\" style=\"text-align: center\">";
	    	htmlText+="<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-save\" onclick=\"save()\">保存</a>";
	    	htmlText+="<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-back\" onclick=\"close_window()\">返回</a></td></tr>";
	    	$("#myTable").html(htmlText);
	 	});
	 	//判断是否拼接checked属性
	 	function checkChecked(check){
	 		var checked=""
	 		if(check=="true"){
				checked = "checked=true";
			}else{
				checked = "";
			}
	 		return checked;
	 	}
	 	function save(){
	 		var values="";
	 		$("#myTable input[name='column']:checkbox:checked").each(function(){
	 			values+=","+$(this).val();
 		  	});
	 		if(isEmpty(values)){
	 			$.messager.show({
					title:"警告",
					msg:"至少选择一列!"
				});
	 			return;
	 		}
	 		values=values.substring(1);
	 		$.ajax({
	 			type:"post",
	 			async:false,
	 			url:"${ctx}/stDesignDataManager/save.do",
	 			contentType:"application:json;charset:utf-8",
	 			data:JSON.stringify({"values":values,"major":"${major}"}),
	 			success:function(result){
	 				handleReturn(result,function(){
	 					closeSelfWindow();
	 					if("StDataTrack"=="${beanName}"){
	 						jumpPage("结构数据跟踪表","${ctx}/login/showList.do?url=business/designDataManager/stDataTrackTable");
	 			 		}else if("StDataTrackSupp"=="${beanName}"){
	 			 			jumpPage("结构补料数据跟踪表","${ctx}/login/showList.do?url=business/designDataManager/stDataTrackSupp");
	 			 		}else if("PiDataTrack"=="${beanName}"){
	 			 			jumpPage("管线数据跟踪表","${ctx}/login/showList.do?url=business/designDataManager/piDataTrackTable");
	 			 		}else if("PiDataTrackSupp"=="${beanName}"){
	 			 			jumpPage("管线补料数据跟踪表","${ctx}/login/showList.do?url=business/designDataManager/piDataTrackSupp");
	 			 		}
	 				});
	 			}
	 		})
	 	}
	 	//返回按钮
	 	function close_window(){
	 		closeSelfWindow();
	 		if("StDataTrack"=="${beanName}"){
				jumpPage("结构数据跟踪表","${ctx}/login/showList.do?url=business/designDataManager/stDataTrackTable");
	 		}else if("StDataTrackSupp"=="${beanName}"){
	 			jumpPage("结构补料数据跟踪表","${ctx}/login/showList.do?url=business/designDataManager/stDataTrackSupp");
	 		}else if("PiDataTrack"=="${beanName}"){
	 			jumpPage("管线数据跟踪表","${ctx}/login/showList.do?url=business/designDataManager/piDataTrackTable");
	 		}else if("PiDataTrackSupp"=="${beanName}"){
	 			jumpPage("管线补料数据跟踪表","${ctx}/login/showList.do?url=business/designDataManager/piDataTrackSupp");
	 		}
	 	}
	 	//全选
	 	function checkAll(){
	 		$("#myTable input[name='column']:checkbox").each(function(){
	 			$(this).prop("checked",true);
 		  	});
	 	}
	 	//反选
	 	function inverse(){
	 		$("#myTable input[name='column']:checkbox").each(function(){
	 			if(true==$(this).prop("checked")){
	 				$(this).prop("checked",false);
	 			}else{
	 				$(this).prop("checked",true);
	 			}
 		  	});
	 	}
	 	//全不选
	 	function inverseAll(){
	 		$("#myTable input[name='column']:checkbox").each(function(){
	 			$(this).prop("checked",false);
 		  	});
	 	}
	</script>
</body>
</html>
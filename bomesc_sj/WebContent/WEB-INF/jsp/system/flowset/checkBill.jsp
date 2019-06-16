<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>审核页面</title>
</head>
<body>
	<table style="width:100%;margin-top:5px">
		<tr>
			<th width="30%">审核意见</th>
			<td>
				<input id="check_opinion" name="check_opinion" class="easyui-textbox" data-options="multiline:true" style="width:300px;height:100px"/>
			</td>
		</tr>
		<tr style="height:40px">
			<td colspan="2" style="text-align: center">
				<a href="javascript:check('${work_no }',1)" class="easyui-linkbutton m5" iconCls="icon-ok">同意</a>
				<a href="javascript:check('${work_no }',0)" class="easyui-linkbutton m5" iconCls="icon-cancel">拒绝</a>
			</td>
		</tr>
	</table>
</body>
</html>
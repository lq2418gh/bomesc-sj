<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<meta charset="utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">  
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<!-- 各模块自身页面打开弹框 -->
<div id="MySelfWindow" modal="true" closed="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="shortcut icon" href="${ctx}/icon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.2/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.2/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/icon.css" />
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.2/easyloader.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.2/datagrid-autoadaptpage.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.2/datagrid-detailview.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	var rootPath = "${ctx}";
	var ssoServerLoginUrl = "${ssoServerLoginUrl}";
</script>
<c:if test="${not empty message}">
	<script type="text/javascript">
		alert("${message}");
	</script>
	<c:remove var="message"/>
</c:if>
<c:if test="${not empty current_user_sj}">
	<script type="text/javascript">
		store.setItem("current_user_sj", '${current_user_sj}');
		store.setItem("auths_sj", "${auths_sj}");
	</script>
</c:if>
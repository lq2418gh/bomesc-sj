<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户中心</title>
</head>
<body class="easyui-layout" id="mainBody">
	<!-- 上 -->
	<div region="north" split="false" style="height:70px;text-align: center;background: #369 url('${ctx}/img/loginbg.png') center top repeat-x" border="false">
		<div style="margin-top:20px">
			<img src="${ctx}/img/logoBorder.png" style="float:left;width:150px;height:25px;margin-left:100px;margin-top:5px" />
			<img class="img-rounded" src="${ctx}/img/name.png" style="float:left;width:300px;height:41px;" />
		</div>
		<span style="float:right;margin-right:10px;font-size:14px;margin-top:10px;color:white;width:35%">
			<span style="float:left">${sessionScope.currentUser.name},欢迎登陆博迈科海洋工程股份有限公司设计部系统</span>
			<a href="javascript:logout()" class="header_a">退出</a>
			<a href="${ssoServerUrl}" class="header_a">系统选择</a>
		</span>
	</div>
	<!-- 左-->
	<div region="west" class="menudiv" split="true" title="系统菜单" style="width:200px;overflow:hidden;" href="${ctx}/login/menu.do">
	</div>
	<!-- 中 
	<div region="center" class="maindiv" title="所在位置: 首页" style="overflow-x:hidden;padding: 0px;padding-right:10px" href="${ctx}/login/welcome.do" ></div>-->
	<!-- 正中间panel -->
   	<div data-options="region:'center'" >
    	<div  class="easyui-tabs"  id="centerTab" fit="true" border="false">
			<div title="首页" id="home-content" style="padding:20px;overflow-x:hidden;overflow-y:auto;" href="${ctx}/login/welcome.do"></div>
		</div>
    </div>
	<div id="MyPopWindow" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>  
	<div id="MyPopWindowSecond" modal="true" shadow="false" minimizable="false" cache="false" maximizable="false" collapsible="false" resizable="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>
</body>
</html>
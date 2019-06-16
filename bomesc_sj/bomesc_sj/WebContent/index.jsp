<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	   	<title>系统首页</title>
	</head>
  
	<body>
		<script type="text/javascript"> 
			$(function(){
				if("${param.session}" == "out"){
		 			alert("用户超时或多个相同账号登录造成会话失效,需要重新登录");
		 		}else if("${param.session}" == "noLogin"){
		 			alert("用户没有操作权限或未登录");
		 		}
		 		window.top.location="${ctx}/login/readyLogin.do";
			});
		</script>
	</body>
</html>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录</title>
<style type="text/css">
	.loginbg{position: absolute;top: 0;width:100%;height:100%;z-index: -1}
	.imgDiv{margin:100px auto 0px;width:930px;}
	.logoBorder{width:330px;height:56px;margin-bottom:60px;}
	.name{margin-bottom:50px;}
	.loginForm{margin:0px auto;width:30%;text-align:center}
	.loginTable{width:100%;padding:20px;background: url('${ctx}/img/namebg.png') repeat}
	.loginTable tr{height:60px}
	.loginTable td.left{width:30%;text-align: right;color:white}
	.captcha{width:60px;margin-left: 110px;}
	.captchaImg{width:80px;height:30px;margin-left:20px;vertical-align: bottom;border-radius:6px}
	a{font-size: 12px;color: #ffffff;text-decoration: none;}
</style>
</head>
<body style="padding:0"><!-- class="easyui-panel" title="登录" data-options="iconCls:'icon-man',collapsible:true,maximizable:true" background:#fafafa;-->
	<div style="width:100%;height:100%;">
		<img src="${ctx}/img/loginbg.png" class="loginbg" />
	    <div style="padding:10px">
	    	<div class="imgDiv">
		    	<img src="${ctx}/img/logoBorder.png" class="logoBorder"/>
				<img src="${ctx}/img/name.png" class="name" />
			</div>
	        <form id="loginForm" action="${ctx}/login/login.do" method="post" class="loginForm">
				<table class="loginTable" >
					<tr style="height:15px">
						<td colspan="2" align="right" style="vertical-align:top; border-top-width: 0px;">
							<a id="cn" href="?locale=zh_CN">中文</a>&nbsp;&nbsp;
							<a id="en" href="?locale=en_US">ENGLISH</a>
						</td>
					</tr>
					<tr>
						<td class="left">账号：</td>
	              		<td><input id="username" value="wzm" name="username" class="easyui-textbox" iconCls="icon-man" iconAlign="left" style="width:200px;height:32px"/></td>
	            	</tr>
					<tr>
						<td class="left">密码：</td>
	              		<td><input id="password" value="1" name="password" type="password" class="easyui-textbox" iconCls="icon-lock" iconAlign="left" style="width:200px;height:32px"/></td>
	            	</tr>
	            	<tr>
	            		<td class="left">验证码：</td>
		          		<td>
		          			<input type='text' id="j_captcha" name='j_captcha' class="easyui-textbox captcha" class="captcha" size="5" tabindex="3" />
		          			<img id="captchaImg" src="<c:url value="/jcaptcha.jpg"/>" class="captchaImg" onclick="refreshCaptcha();"/>
		          		</td>
		    		</tr>
	            	<tr>
	            		<td colspan="2" align="center">
	            			<button type="submit" onclick="return login()" class="easyui-linkbutton" style="width:20%;height:32px;margin-left:40px">登录</button>
	            			<button type="reset" class="easyui-linkbutton" style="width:20%;height:32px;margin-left:10px">重置</button>
	            		</td>
	            	</tr>
				</table>
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
					<span style="color:red">
						<c:choose> 
							<c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message eq '坏的凭证'}">密码错误</c:when> 
							<c:otherwise>${SPRING_SECURITY_LAST_EXCEPTION.message}</c:otherwise> 
						</c:choose> 
					</span>
					<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
				</c:if>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		function login(){
			var username = $("#username").textbox("getValue");
			var password = $("#password").textbox("getValue");
			if(isEmpty(username) || isEmpty(password)){
				$.messager.show({
 					title:'警告',
 					msg:"请将信息填写完整！"
 				});
				return false
			}else{
				return true;
			}
		}
		function refreshCaptcha() {  
		    $("#captchaImg").hide().attr("src","<c:url value='/jcaptcha.jpg'/>?" + Math.floor(Math.random() * 100)).fadeIn();  
		}
	</script>
</body>
</html>
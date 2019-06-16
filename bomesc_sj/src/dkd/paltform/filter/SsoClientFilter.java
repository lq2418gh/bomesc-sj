package dkd.paltform.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

import dkd.paltform.authority.domain.Authority;
import dkd.paltform.authority.domain.Role;
import dkd.paltform.authority.domain.User;
import dkd.paltform.util.HttpClientUtil;
import dkd.paltform.util.SpringUtil;
import dkd.paltform.util.SsoCache;
import dkd.paltform.util.StringUtils;

public class SsoClientFilter implements Filter{
	private Properties prop;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
		FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)servletRequest;
		HttpServletResponse res = (HttpServletResponse)servletResponse;
		HttpSession session = req.getSession();
		if ("/stDesignDataManager/getBomescCbMtoDetail.do".equals(req.getServletPath())) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		String token = req.getParameter("token");
		String gotoUrl = req.getRequestURL().toString();
		if(gotoUrl.endsWith("logout.do")){
			//子系统注销，重定向值用户中心进行注销
			res.sendRedirect(prop.getProperty("ssoServer") + prop.getProperty("ssoServerLogoutUrl"));
			return;
		}
		
		//注销，认证中心发起，注销各个子系统
		String logoutToken = req.getParameter("logoutToken");
		if(StringUtils.isNotEmpty(logoutToken)){
			HttpSession loginSession = SsoCache.get(logoutToken);
			if(loginSession != null){
				loginSession.invalidate();
				return;
			}
		}
		//国际化参数
		//Object local = session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		//+ "?local=" + (local == null ? "zh_CN" : local) 
		String url = prop.getProperty("ssoServer") + prop.getProperty("ssoServerLoginUrl") + "?gotoUrl=" + gotoUrl;
		
		Object currentUser = session.getAttribute("currentUser");
		if(currentUser !=  null){
			//已登录用户验证权限
			if(gotoUrl.contains("/index.do") && StringUtils.isNotEmpty(token)){
				PrintWriter out = res.getWriter();
				out.print("<script type='text/javascript'>");
				out.print("document.write(\"<form id='url' method='post' action='" + prop.getProperty("ssoClientUrl") + "'>\");");
				out.print("document.write(\"</form>\");");
				out.print("document.getElementById(\"url\").submit();");
				out.print("</script>");
			}else if(gotoUrl.contains("/index.do") || gotoUrl.contains("/menu.do") || gotoUrl.contains("/welcome.do")
					|| gotoUrl.contains("/showList.do")){
				//首页、菜单、欢迎页、跳转页面不验证权限
				filterChain.doFilter(servletRequest, servletResponse);
			}else{
				boolean flag = false;
				List<Authority> authoritys = (List<Authority>)session.getAttribute("authoritys");
				for(Authority auth : authoritys){
					//根据权限的url进行验证
					if(gotoUrl.contains(auth.getUrl())){
						flag = true;
						break;
					}
				}
				if(flag){
					filterChain.doFilter(servletRequest, servletResponse);					
				}else{
					//没有权限
					res.setStatus(403);					
				}
				//filterChain.doFilter(servletRequest, servletResponse);
			}
		}else{
			if(StringUtils.isNotEmpty(token)){
				String userDetails = validateToken(token);
				if(StringUtils.isNotEmpty(userDetails)){
					//userDetails = DESUtils.decrypt(userDetails, Constant.secretKey);
					
					JSONObject loginInfo = JSONObject.parseObject(userDetails);
					User loginUser = JSONObject.parseObject(loginInfo.getString("user"), User.class);
					List<Authority> authoritys = JSONObject.parseArray(loginInfo.getString("authoritys"), Authority.class);
					//List<Role> allRoles = JSONObject.parseArray(loginInfo.getString("allRoles"), Role.class);
					List<Role> userRoles = JSONObject.parseArray(loginInfo.getString("userRoles"), Role.class);
					//角色id字符串，查询待审任务用
					StringBuffer rolesSb = new StringBuffer();
					for(Role role : userRoles){
						rolesSb.append("'").append(role.getId()).append("',");
					}
					String rolesStr = "";
					if(StringUtils.isNotEmpty(rolesSb.toString())){
						rolesStr = rolesSb.substring(0, rolesSb.length() - 1);
					}
					//权限字符串，校验权限用
					StringBuffer auths = new StringBuffer(",");
					if(authoritys != null){
						for(Authority auth : authoritys){
							auths.append(auth.getCode() + ",");
						}
					}
					
					//登录用户
					session.setAttribute("currentUser", loginUser);
					//全部角色，因只有审批流程需要，一般不需要，所以改成需要时再从用户中心获取
					//session.setAttribute("allRoles", allRoles);
					//session.setAttribute("allRolesStr", loginInfo.getString("allRoles").replaceAll("\"", "\\\\\""));
					//用户所有角色，校验审核用
					session.setAttribute("userRoles", userRoles);
					session.setAttribute("userRolesStr", rolesStr);
					//用户所有权限，校验用
					session.setAttribute("authoritys", authoritys);
					
					session.setAttribute("ssoServerUrl", prop.getProperty("ssoServer") + prop.getProperty("ssoServerUrl"));
					session.setAttribute("uploadUrl", prop.getProperty("uploadUrl"));
					session.setAttribute("ssoServerLoginUrl", prop.getProperty("ssoServer") + prop.getProperty("ssoServerLoginUrl"));
					session.setAttribute("token", token);
					SsoCache.put(token, session);
					res.setContentType("text/html;charset=utf-8");
					PrintWriter out = res.getWriter();
					out.print("<script type='text/javascript'>");
					out.print("document.write(\"<form id='url' method='post' action='" + prop.getProperty("ssoClientUrl") + "'>\");");
					out.print("document.write(\"<input type='hidden' name='auths_sj' value='" + auths + "' />\");");
					out.print("document.write(\"<input type='hidden' name='current_user_sj' value='" + loginInfo.getString("user").replaceAll("\"", "\\\\\"") + "' />\");");
					out.print("document.write(\"</form>\");");
					out.print("document.getElementById(\"url\").submit();");
					out.print("</script>");
				}else{
					res.sendRedirect(url);					
				}
			}else{
				res.sendRedirect(url);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//获取系统配置文件
		prop = SpringUtil.getSystemProp();
	}
	private String validateToken(String token) throws IOException, ServletException {
		Map<String,String> params = new HashMap<String,String>();
		params.put("token", token);
		return HttpClientUtil.getUserCenterData("ssoServerValidateTokenUrl", params,false);
	}
}
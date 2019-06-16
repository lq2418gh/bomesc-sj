package dkd.paltform.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

import dkd.paltform.authority.domain.Role;
import dkd.paltform.authority.domain.User;

/**
 * spring的工具类，这个类很重要，要在普通类里调用spring注入的bean对象必须通过这个类
 * 通过注解是没用的，因为普通类不符合spring 或者 mvc机制
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class SpringUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;  
	private static SpringUtil util = null;
	public static Properties getSystemProp(){
	    return getProp("system");
	}
	public static Properties getBeanProp(){
		return getProp("bean");
	}
	public static Properties getRedisProp(){
		return getProp("redis");
	}
	public static Properties getBomescPurchaseProp(){
		return getProp("bomesc_purchase");
	}
	private static Properties getProp(String fileName){
		//获取系统配置文件
	    if(util == null){
	    	util = new SpringUtil();
	    }
	    InputStream in = util.getClass().getClassLoader().getResourceAsStream(fileName + ".properties");
	    Properties prop = new Properties();
	    try {
	    	prop.load(in);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    return prop;
	}
	@Override  
    public void setApplicationContext(ApplicationContext context)  
        throws BeansException {  
        SpringUtil.applicationContext = context;  
    }  
	/**
	 * 
	 * @date 2017-10-17
	 * @author wzm
	 * @param name
	 * @return
	 * 描述：根据名称获取ben对象
	 */
    public static Object getBean(String name){  
        return applicationContext.getBean(name);  
    }
    /**
     * 
     * @date 2017-10-17
     * @author wzm
     * @return
     * 描述：获取request对象
     */
    public static HttpServletRequest getRequest(){
    	return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    /**
     * 
     * @date 2017-10-17
     * @author wzm
     * @return
     * 描述：获取session对象
     */
    public static HttpSession getSession(){
    	if(RequestContextHolder.getRequestAttributes() == null){
    		return null;
    	}else{
    		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();    		
    	}
    }
    /**
     * 
     * @date 2017-10-17
     * @author wzm
     * @return
     * 描述：获取当前登录对象
     */
    public static User getCurrentUser(){
    	HttpSession session = getSession();
    	if(session != null){
    		return (User)getSession().getAttribute("currentUser");	
    	}else{
    		return null;
    	}
    }
    /**
     * 
     * @date 2017-10-17
     * @author wzm
     * @param params
     * @return
     * 描述：调用用户中心接口查询用户信息
     */
    public static String getUserByParams(Map<String,String> params){
    	return HttpClientUtil.getUserCenterData("ssoServerSelectUserUrl",params);
    }
    /**
     * 
     * @date 2017-10-17
     * @author wzm
     * @param params
     * @return
     * 描述：调用用户中心接口查询部门信息
     */
    public static String getDeptByParams(Map<String,String> params){
    	return HttpClientUtil.getUserCenterData("ssoServerSelectDeptUrl",params);
    }
    /**
     * 
     * @date 2017-10-17
     * @author wzm
     * 描述：初始化角色
     */
   private static void initRoles(){
		String roles = HttpClientUtil.getUserCenterData("ssoServerSelectRoleUrl");
		//JSONObject loginInfo = JSONObject.parseObject(roles);
		List<Role> roleDatas = JSONObject.parseArray(roles, Role.class);
		getSession().setAttribute("allRoles",roleDatas);
		getSession().setAttribute("allRolesStr", roles.replaceAll("\"", "\\\\\""));
    }
    /**
     * 
     * @date 2017-10-17
     * @author wzm
     * @return
     * 描述：获取所有角色信息
     */
	public static List<Role> getAllRoles(){
		Object allRoles = getSession().getAttribute("allRoles");
		if(allRoles == null){
			initRoles();
			allRoles = getSession().getAttribute("allRoles");
		}
		return (List<Role>)allRoles;
	}
	/**
	 * 
	 * @date 2017-10-17
	 * @author wzm
	 * @return
	 * 描述：获取所有角色信息（json字符串）
	 */
	public static String getAllRolesStr(){
		Object allRoles = getSession().getAttribute("allRolesStr");
		if(allRoles == null){
			initRoles();
			allRoles = getSession().getAttribute("allRolesStr");
		}
		return allRoles.toString();
   }
	/**
	 * 
	 * @date 2017-10-17
	 * @author wzm
	 * @return
	 * 描述：得到当前用户所拥有的角色，校验用
	 */
	public static List<Role> getUserRoles(){
        return (List<Role>)getSession().getAttribute("userRoles");
	}
	/**
	 * 
	 * @date 2017-10-24
	 * @author wzm
	 * @return
	 * 描述：得到当前用户的角色id，查询待审记录用
	 */
	public static String getUserRolesStr(){
		return getSession().getAttribute("userRolesStr").toString();
	}
}

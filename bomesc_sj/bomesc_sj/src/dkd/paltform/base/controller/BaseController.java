package dkd.paltform.base.controller;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import dkd.paltform.authority.domain.User;
import dkd.paltform.util.StringUtils;

@Controller
public class BaseController {
	
	public SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	private Map<String,String> params;
	/**
	 * 
	 * @date 2017-8-29
	 * @author wzm
	 * @param request
	 * @return 参数map
	 * 描述：根据request获取前台所传参数列表
	 */
	public Map<String,String> getParam(HttpServletRequest request){
		params = new HashMap<String,String>();
		Enumeration<?> enu=request.getParameterNames();
		String paramName,paramValue;
		while(enu.hasMoreElements()){
			paramName = (String)enu.nextElement();
			paramValue = request.getParameter(paramName);
			if((StringUtils.isNotEmpty(paramValue) || paramName.indexOf("isnull") > 0) && !paramName.equals("_")){
				params.put(paramName, paramValue);				
			}
		}
		return params;
	}
	
	public final String MD5(String s) {  
        char hexDigits[] = { '0', '1', '2', '3', '4',  
                             '5', '6', '7', '8', '9',  
                             'a', 'b', 'c', 'd', 'e', 'f' };  
        try {  
            byte[] btInput = s.getBytes();  
            //获得MD5摘要算法的 MessageDigest 对象  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
            //使用指定的字节更新摘要  
            mdInst.update(btInput);  
            //获得密文  
            byte[] md = mdInst.digest();  
            //把密文转换成十六进制的字符串形式  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {  
                byte byte0 = md[i];  
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                str[k++] = hexDigits[byte0 & 0xf];  
            }  
            return new String(str);  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }
	public User getCurrentUser(HttpSession session){
		return (User)session.getAttribute("currentUser");
	}
	/**
	 * 
	 * @date 2017-8-29
	 * @author wzm
	 * @param message
	 * @return
	 * 描述：各种成功、失败返回值
	 */
	public String toWriteSuccess(){
		return "{\"success\":true}";
	}
	public String toWriteSuccess(String message){
		return "{\"success\":true,\"msg\":\"" + StringUtils.replaceSpecial(message) + "\"}";
	}
	public String toWriteSuccess(String message,String id){
		return "{\"success\":true,\"msg\":\"" + StringUtils.replaceSpecial(message) + "\",\"id\":\"" + id + "\"}";
	}
	
	public String toWriteFail(){
		return "{\"success\":false}";
	}
	public String toWriteFail(String message){
		return "{\"success\":false,\"msg\":\"" + StringUtils.replaceSpecial(message) + "\"}";
	}
	public String toWriteFail(int index,String field,String message){
		return "{\"success\":false,\"msg\":\"" + StringUtils.replaceSpecial(message) + "\",\"index\":" + index + ",\"field\":\"" + field + "\"}";
	}
	public String toWriteFailAndLoginAgain(String message){
		return "{\"success\":false,\"msg\":\"" + StringUtils.replaceSpecial(message) + "\",\"loginAgain\":1}";
	}
}
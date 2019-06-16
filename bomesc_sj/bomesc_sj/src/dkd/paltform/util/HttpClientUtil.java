package dkd.paltform.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import dkd.paltform.authority.domain.User;

public class HttpClientUtil {
	public static String getUserCenterData(String url,Map<String,String> params){
		return getUserCenterData(url,params,true);
	}
	public static String getUserCenterData(String url){
		return getUserCenterData(url,null,true);
	}
	public static String getUserCenterData(String url,Map<String,String> params,boolean encryption){
		try {
			Properties prop = SpringUtil.getSystemProp();
			HttpClient httpClient = new HttpClient();
			PostMethod postMethod = new PostMethod(prop.getProperty("ssoServer") + prop.getProperty(url));
			NameValuePair[] params_convert;
			int size = 0;
			if(params == null){
				params = new HashMap<String,String>();
			}
			if(encryption){
				params_convert = new NameValuePair[params.keySet().size() + 4];
				//需要加密，同时加上用户名、密码做校验
				for (Map.Entry<String, String> entry : params.entrySet()) {
					params_convert[size++] = new NameValuePair(entry.getKey(), DESUtils.encrypt(entry.getValue(), Constant.secretKey));						
				}
				
				User user = SpringUtil.getCurrentUser();
				params_convert[size++] = new NameValuePair("user_id", DESUtils.encrypt(user.getId(), Constant.secretKey));
				params_convert[size++] = new NameValuePair("username", DESUtils.encrypt(user.getUsername(), Constant.secretKey));
				params_convert[size++] = new NameValuePair("password", DESUtils.encrypt(user.getPassword(), Constant.secretKey));
			}else{
				params_convert = new NameValuePair[params.keySet().size() + 1];
				//参数不加密
				for (Map.Entry<String, String> entry : params.entrySet()) {
					params_convert[size++] = new NameValuePair(entry.getKey(), entry.getValue());						
				}
			}
			//最后附上子系统
			params_convert[size++] = new NameValuePair("system_no", DESUtils.encrypt(prop.getProperty("system_no"), Constant.secretKey));
			postMethod.addParameters(params_convert);
			int flag = httpClient.executeMethod(postMethod);
			switch(flag) {
				case HttpStatus.SC_OK:
					return DESUtils.decrypt(postMethod.getResponseBodyAsString().toString(),Constant.secretKey);
				default:
					return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	//查询料单数据
	public static String validateMto(String url,Map<String,String> params){
		try {
			Properties prop = SpringUtil.getBomescPurchaseProp();
			HttpClient httpClient = new HttpClient();
            // 获得登陆后的 Cookie  
	        /**
			 * 登陆采办系统，后续开发将采办系统整合进来
			 */
			PostMethod postMethod = new PostMethod(prop.getProperty("ssoServer") + prop.getProperty(url));
			NameValuePair[] params_convert;
			int size = 0;
			if(params == null){
				params = new HashMap<String,String>();
			}
			params_convert = new NameValuePair[params.keySet().size()];
			//需要加密
			for (Map.Entry<String, String> entry : params.entrySet()) {
				params_convert[size++] = new NameValuePair(entry.getKey(), DESUtils.encrypt(entry.getValue(), Constant.secretKey));						
			}
			postMethod.addParameters(params_convert);
			int flag = httpClient.executeMethod(postMethod);
			switch(flag) {
				case HttpStatus.SC_OK:
					return postMethod.getResponseBodyAsString().toString();
				default:
					return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public static void main(String[] args) {
		//System.out.println(getUserCenterData("https://www.baidu.com/", null));
		/*try{
			 // 创建URL对象
	        URL myURL = new URL("https://www.sun.com");
	        // 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
	        HttpsURLConnection httpsConn = (HttpsURLConnection) myURL
	                .openConnection();
	        // 取得该连接的输入流，以读取响应内容
	        InputStreamReader insr = new InputStreamReader(httpsConn
	                .getInputStream());
	        // 读取服务器的响应内容并显示
	        int respInt = insr.read();
	        while (respInt != -1) {
	            System.out.print((char) respInt);
	            respInt = insr.read();
	        }
		}catch(Exception e){
			e.printStackTrace();
		}*/
		Map<String,String> map = new HashMap<String, String>();
		map.put("20180105001", "1,2,3");
		map.put("20180105002", "1,2,3");
		validateMto("ssoServerValidateMto",map);
		System.out.println(validateMto("ssoServerValidateMto",map));
	}
}

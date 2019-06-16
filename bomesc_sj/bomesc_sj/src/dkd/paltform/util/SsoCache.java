package dkd.paltform.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SsoCache {
	private static Map<String,HttpSession> cache = new HashMap<String,HttpSession>();
	public static void put(String key,HttpSession session){
		cache.put(key, session);
	}
	public static HttpSession get(String key){
		return cache.get(key);
	}
}
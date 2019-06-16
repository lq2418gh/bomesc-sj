package dkd.paltform.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;

public class ConvertMapToDomainUtil {
	
	/**
	 * @Title: assemble   
	 * @Description: List<Map<String, Object>> 转为   List<Object> 
	 * @param: @param objs
	 * @param: @param targetClass
	 * @param: @return
	 * @param: @throws IllegalAccessException
	 * @param: @throws InvocationTargetException
	 * @param: @throws InstantiationException      
	 * @return: List<Object>      
	 * @throws
	 * @author gaoxp
	 */
	public static List<Object> assemble(List<Map<String, String>> objs, Class<?> targetClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {  
		List<Object> list=new ArrayList<Object>();
		for (Map<String, String> map : objs) {
    	   Object obj=assemble(map, targetClass);
    	   list.add(obj);
       }
       return list;
    }  
	
	/**
	 * @Title: assemble   
	 * @Description: Map<String, Object> 转为   Object
	 * @param: @param objs
	 * @param: @param targetClass
	 * @param: @return
	 * @param: @throws IllegalAccessException
	 * @param: @throws InvocationTargetException
	 * @param: @throws InstantiationException      
	 * @return: Object      
	 * @throws
	 * @author gaoxp
	 */
	public static Object assemble(Map<String, String> objs, Class<?> targetClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {  
        if (null == objs) {  
            return null;  
        }  
        Object target = targetClass.newInstance();  
        if (target != null) {  
            for(Map.Entry<String, String> e : objs.entrySet()) {
            	if(null!=e.getValue()){
            		 BeanUtils.copyProperty(target, e.getKey(), e.getValue()); 
            	}
            }  
        }  
        return target;  
    }
	/**
	 * @date 2017年12月13日
	 * @author gxp
	 * @param objs
	 * @param targetClass
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * 描述：将相应map转换为制定类型的实体
	 */
	
	public static Object anotherAssemble(Map<String, Object> objs, Class<?> targetClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {  
        if (null == objs) {  
            return null;  
        }  
        Object target = targetClass.newInstance();  
        if (target != null) {  
            for(Map.Entry<String, Object> e : objs.entrySet()) { 
            	if(null!=e.getValue()){
            		BeanUtils.copyProperty(target, e.getKey(), e.getValue()); 
            	}
            }  
        }  
        return target;  
    }  
}

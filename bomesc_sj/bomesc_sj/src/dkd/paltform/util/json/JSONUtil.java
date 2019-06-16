package dkd.paltform.util.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import dkd.paltform.base.annotation.BeanSelect;
import dkd.paltform.base.domain.TreeEntity;
import dkd.paltform.util.json.serializer.Iserializer;

public class JSONUtil {
	/**
	 * @Title: getJSONByList
	 * @Description: 将LIST转化为JSON
	 * @param
	 * @author CST-TONGLZ
	 * @return String
	 * @throws
	 */
	private static String getJSONByList(Collection<?> coolCollection) {
		List<Object> list = new ArrayList<Object>(coolCollection);
		if (list.isEmpty())
			return "[]";
		StringBuffer bf = new StringBuffer("[");
		int size = list.size();
		Object obj;
		for (int i = 0; i < size; i++) {
			obj = list.get(i);
			bf.append(getJsonByEntity(obj));
			if (i == size - 1)
				continue;
			bf.append(",");
		}
		bf.append("]");
		return bf.toString();
	}

	/**
	 * @Title: getJsonByEntity
	 * @Description:Obj To Json
	 * @param
	 * @author CST-TONGLZ
	 * @return String
	 * @throws
	 */
	public static String getJsonByEntity(Object entity) {
		if (entity == null) {
			return "{}";
		}
		if(entity instanceof Collection<?>)
			return getJSONByList((Collection<?>)entity);
		Iserializer ser = SerializerFactory.getSerializer(entity.getClass());
		String result = ser.write(entity, entity.getClass(), true, null);
		return result;
	}
	public static String getJsonByTreeMap(List<Map<String,Object>> trees,boolean isRoot) {
		StringBuffer sb = new StringBuffer();
		String returnData;
		if(trees != null && !trees.isEmpty()){
			for(Map<String,Object> tree : trees){
				sb.append("{\"id\":\"" + tree.get("id"));
				sb.append("\",\"attributes\":{\"code\":\"" + tree.get("code") + "\"}");
				sb.append(",\"state\":\"closed\"");	
				sb.append(",\"text\":\""+tree.get("name"));
				sb.append("\"},");
			}
			returnData = sb.substring(0, sb.length() - 1);
		}else{
			returnData = "";
		}
		
		if(isRoot){
			return "[{\"id\":0,\"text\":\"根节点\",\"state\":\"open\",\"children\":[" + returnData + "]}]";
		}else{
			return "[" + returnData + "]";			
		}
	}
	public static String getJsonByTreeEntity(List<?> trees,boolean isRoot) {
		StringBuffer sb = new StringBuffer();
		String returnData;
		if(trees != null && !trees.isEmpty()){
			TreeEntity<?> tree;
			for(Object obj : trees){
				tree = (TreeEntity<?>)obj;
				sb.append("{\"id\":\"" + tree.getId());
				sb.append("\",\"attributes\":{\"code\":\"" + tree.getCode() + "\",\"levels\":" + tree.getLevels() + "}");
				if(tree.getLevels() == 3){
					sb.append(",\"state\":\"open\"");	
				}else{
					sb.append(",\"state\":\"closed\"");
				}
				sb.append(",\"text\":\""+tree.getName() + "\"");
				if(tree.getDetails() != null && !tree.getDetails().isEmpty()){
					sb.append(",\"children\":" + getJsonByTreeEntity(tree.getDetails(),false));
				}
				sb.append("},");
			}
			returnData = sb.substring(0, sb.length() - 1);
		}else{
			returnData = "";
		}
		
		if(isRoot){
			return "[{\"id\":0,\"text\":\"根节点\",\"state\":\"open\",\"children\":[" + returnData + "]}]";
		}else{
			return "[" + returnData + "]";			
		}
	}
	public static String getJsonByEntity(Object entity,Map<String,Object> map) {
		BeanSelect beanSelect;
		Field[] fields;
		for(Class<?> clazz = entity.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) { 
			fields = clazz.getDeclaredFields();
			for(Field field : fields){  
				beanSelect = field.getAnnotation(BeanSelect.class);
				if(null!=beanSelect&&beanSelect.gain()==false){
					continue;
				}
				field.setAccessible(true);  
			    String fieldName =  field.getName();
			    Object val;
				try {
					val = field.get(entity);
				}catch (IllegalAccessException e) {
					val="";
				}
			    map.put(fieldName, val);
			} 
		}
		return getJsonByEntity(map);
	}
	
}

package dkd.paltform.util.json.serializer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import dkd.paltform.dictionary.domain.Dictionary;
import dkd.paltform.util.json.FieldMethodRelation;
import dkd.paltform.util.json.SerializerFactory;
import dkd.paltform.util.json.annotation.AnnotatedAnalyzer;

public class EntitySerializer implements Iserializer {
	public static EntitySerializer instance = new EntitySerializer();

	private EntitySerializer() {

	}

	@Override
	public String write(Object object, Class<?> clz, boolean isSelf, Field field) {
		if (object == null) {
			return "null";
		}
		StringBuilder result = new StringBuilder();
		result.append("{");
		List<FieldMethodRelation> fields = AnnotatedAnalyzer.getFields(clz, isSelf, null);
		Field entityField;
		String fieldName;
		Method method;
		Object value = null;
		Iserializer ser;
		String str;
		Dictionary temp;
		for (FieldMethodRelation fieldMethodRelation : fields) {
			entityField = fieldMethodRelation.getField();
			fieldName = entityField.getName();
			method = fieldMethodRelation.getMethod();
			try {
				value = method.invoke(object);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			ser = SerializerFactory.getSerializer(entityField.getType());
			result.append("\"").append(fieldName).append("\":");
			Class<?> c=entityField.getType();
			if(value!=null&&c.getSimpleName().equals("Object")){
				c = value.getClass(); 
			}
			if(value instanceof Dictionary){
				temp = (Dictionary)value;
				str = "\"" + temp.getId() + "\",\"" + fieldName + "_name\":\"" + temp.getName() + "\"";
			}else{
				str = ser.write(value, c, false, entityField);
			}
			result.append(str);			
			result.append(",");
		}
		if(result.length()>1)
			result.deleteCharAt(result.length() - 1);
		result.append("}");
		return result.toString();
	}

}

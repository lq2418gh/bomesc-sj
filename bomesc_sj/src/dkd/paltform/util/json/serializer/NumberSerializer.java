package dkd.paltform.util.json.serializer;

import java.lang.reflect.Field;

public class NumberSerializer implements Iserializer{
	public static NumberSerializer instance = new NumberSerializer();
	private NumberSerializer(){
		
	}
	public String write(Object object,Class<?> clz, boolean isSelf,Field field) {
		StringBuilder result = new StringBuilder();
		if(object == null){
			result.append("0");
		}else{
			result.append(object.toString());
		}
	
		return result.toString();
		
	}

}

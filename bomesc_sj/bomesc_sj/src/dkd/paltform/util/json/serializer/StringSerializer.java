package dkd.paltform.util.json.serializer;

import java.lang.reflect.Field;

import dkd.paltform.util.StringUtils;

public class StringSerializer implements Iserializer{
	public static StringSerializer instance  = new StringSerializer();
	private StringSerializer(){
		
	}
	@Override
	public String write(Object object,Class<?> clz, boolean isSelf,Field field) {
		StringBuilder result = new StringBuilder();
		if(object == null){
			result.append("\"\"");
		}else{
			result.append("\"").append(StringUtils.replaceSpecial(object.toString())).append("\"");
		}
		return result.toString();
	}
}
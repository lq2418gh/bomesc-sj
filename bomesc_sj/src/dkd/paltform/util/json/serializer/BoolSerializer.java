package dkd.paltform.util.json.serializer;

import java.lang.reflect.Field;

public class BoolSerializer implements Iserializer{
	public static BoolSerializer instance = new BoolSerializer();
	private BoolSerializer(){		
	}
	@Override
	public String write(Object object,Class<?> clz, boolean isSelf,Field field) {
		return object.toString();			
	}
}

package dkd.paltform.util.json.serializer;

import java.lang.reflect.Field;

public class BooleanSerializer implements Iserializer{
    public static BooleanSerializer instance = new BooleanSerializer();
	private BooleanSerializer(){		
	}
	@Override
	public String write(Object object,Class<?> clz, boolean isSelf,Field field) {
		if(object == null){
			return "false";
		}else{
			return object.toString();
		}		
	}
}

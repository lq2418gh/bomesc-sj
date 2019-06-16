package dkd.paltform.util.json.serializer;

import java.lang.reflect.Field;

public class EnumSerializer implements Iserializer{
    public static EnumSerializer instance = new EnumSerializer();
    private EnumSerializer(){
    	
    }
	@Override
	public String write(Object object,Class<?> clz, boolean isSelf,Field field) {
		if(object == null) return "null";
		
		return "\""+object.toString()+"\"";
	}

}

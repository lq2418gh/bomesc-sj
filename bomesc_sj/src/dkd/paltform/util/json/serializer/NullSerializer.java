package dkd.paltform.util.json.serializer;

import java.lang.reflect.Field;

public class NullSerializer implements Iserializer{

	@Override
	public String write(Object object, Class<?> clz,boolean isSelf,Field field) {
		// TODO Auto-generated method stub
		return null;
	}

}

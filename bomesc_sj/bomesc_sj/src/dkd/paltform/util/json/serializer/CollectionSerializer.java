package dkd.paltform.util.json.serializer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import dkd.paltform.util.json.SerializerFactory;
import dkd.paltform.util.json.TechnologyException;

public class CollectionSerializer implements Iserializer {
	public static CollectionSerializer instance = new CollectionSerializer();

	private CollectionSerializer() {
	}

	@Override
	public String write(Object object, Class<?> clz, boolean isSelf, Field field) {
		StringBuilder result = new StringBuilder();
		result.append("[");
		Type fieldClass = field.getGenericType();
		if (fieldClass == null) {
			throw new TechnologyException("json序列化错误，没有指定列表对应的数据类型");
		}
		ParameterizedType pt = (ParameterizedType) fieldClass;
		Type genType = pt.getActualTypeArguments()[0];
		Collection<?> list = (Collection<?>) object;
		if (list == null || list.size() == 0) {
			return "[]";
		}
		for (Object entity : list) {
			Iserializer ser = SerializerFactory.getSerializer(genType.getClass());
			result.append(ser.write(entity, (Class<?>) genType, true, field));
			result.append(",");
		}
		result.deleteCharAt(result.length() - 1);
		result.append("]");

		return result.toString();
	}

}

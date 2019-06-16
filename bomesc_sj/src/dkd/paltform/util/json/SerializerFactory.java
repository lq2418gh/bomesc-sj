package dkd.paltform.util.json;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import dkd.paltform.util.json.serializer.BoolSerializer;
import dkd.paltform.util.json.serializer.BooleanSerializer;
import dkd.paltform.util.json.serializer.CollectionSerializer;
import dkd.paltform.util.json.serializer.DateSerializer;
import dkd.paltform.util.json.serializer.EntitySerializer;
import dkd.paltform.util.json.serializer.EnumSerializer;
import dkd.paltform.util.json.serializer.Iserializer;
import dkd.paltform.util.json.serializer.MapSerializer;
import dkd.paltform.util.json.serializer.NumberSerializer;
import dkd.paltform.util.json.serializer.StringSerializer;

public class SerializerFactory {
	public static Iserializer getSerializer(Class<?> clz) throws TechnologyException {
		// 字符串
		if (clz == String.class)
			return StringSerializer.instance;
		if (clz == Character.class)
			return StringSerializer.instance;
		// 布尔
		if (clz == Boolean.class)
			return BooleanSerializer.instance;
		// 布尔
		if (clz == boolean.class)
			return BoolSerializer.instance;
		// 数字
		if (clz == int.class || clz == long.class || clz == short.class || clz == double.class || clz == float.class||clz==Integer.class) {
			return NumberSerializer.instance;
		}
		if (Integer.class.isAssignableFrom(clz))
			return NumberSerializer.instance;

		if (Number.class.isAssignableFrom(clz))
			return NumberSerializer.instance;
		// 枚举
		if (clz.isEnum())
			return EnumSerializer.instance;

		if (Collection.class.isAssignableFrom(clz))
			return CollectionSerializer.instance;

		if (clz.isArray()) {
			throw new TechnologyException("系统不支持对数组进行JSON序列化");
		}
		if (Date.class.isAssignableFrom(clz))
			return DateSerializer.instance;

		if (Map.class.isAssignableFrom(clz)) {
			return MapSerializer.instance;
		}
		return EntitySerializer.instance;
	}

}

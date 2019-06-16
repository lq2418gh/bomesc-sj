package dkd.paltform.util.json.serializer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import dkd.paltform.util.json.JSONUtil;
import dkd.paltform.util.json.SerializerFactory;

public class MapSerializer implements Iserializer {
	public static MapSerializer instance = new MapSerializer();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String write(Object object, Class<?> clz, boolean isSelf, Field field) {
		if (object == null)
			return "null";
		Iserializer ser;
		Map<String, Object> map = (Map) object;
		Set<String> set = map.keySet();
		StringBuilder bf = new StringBuilder("{");
		for (String key : set) {
			bf.append("\"").append(key).append("\":");
			Object obj = map.get(key);
			if (obj instanceof Collection<?>) {
				bf.append(JSONUtil.getJsonByEntity((Collection<?>) obj));
			} else if (obj == null)
				bf.append("\"\"");
			else {
				ser = SerializerFactory.getSerializer(obj.getClass());
				bf.append(ser.write(obj, obj.getClass(), isSelf, field));
			}
			bf.append(",");
		}
		if (set.size() > 0)
			bf.deleteCharAt(bf.length() - 1);
		bf.append("}");
		return bf.toString();
	}
}

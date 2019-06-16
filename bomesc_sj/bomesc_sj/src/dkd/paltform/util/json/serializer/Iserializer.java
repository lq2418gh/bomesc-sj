package dkd.paltform.util.json.serializer;

import java.lang.reflect.Field;

public interface Iserializer {
    String write(Object object,Class<?> clz,boolean isSelf,Field field);
}

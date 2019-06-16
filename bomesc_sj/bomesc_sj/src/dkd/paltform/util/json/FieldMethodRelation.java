package dkd.paltform.util.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldMethodRelation {
	private Field field;
	private Method method;
	
	
	public FieldMethodRelation(Field field, Method method) {
		this.field = field;
		this.method = method;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	
	
}

package dkd.paltform.util.json.serializer;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import dkd.paltform.util.json.annotation.DateJsonFormat;

public class DateSerializer implements Iserializer{
    public static DateSerializer instance = new DateSerializer();
    
    private DateSerializer(){
    	
    }
	@Override
	public String write(Object object,Class<?> clz, boolean isSelf,Field field){
		if(object == null){
			return "null";
		}
		
		DateJsonFormat formater = null;
		JsonDeserialize serialize = null;
		if(field!=null){
			formater=field.getAnnotation(DateJsonFormat.class);
			serialize = field.getAnnotation(JsonDeserialize.class);
		}
		String style = "yyyy-MM-dd";
		if(serialize != null){
			style = "yyyy-MM-dd HH:mm:ss";
		}else{
			if(formater != null){
				style = formater.style();
			}			
		}
		Date date = (Date)object;
		SimpleDateFormat format = new SimpleDateFormat(style);
		return "\""+format.format(date)+"\"";
	}
}

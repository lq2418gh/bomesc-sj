package dkd.paltform.base;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import dkd.paltform.util.StringUtils;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

	@Override  
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		SimpleDateFormat formatYM = new SimpleDateFormat("yyyy-MM"); 
		SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy-MM-dd");
		String date = jp.getText();  
		try {  
			if(StringUtils.isEmpty(date)){
				return null;
			}else if(date.length() == 7){
				return formatYM.parse(date); 
			}else if(date.length() == 10){
				return formatYMD.parse(date); 
			}else{
				return format.parse(date); 
			}
		} catch (ParseException e) {  
			throw new RuntimeException(e);  
		}  
    }  
}
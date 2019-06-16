package dkd.paltform.base;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.mapping.Map;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.MapSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
@SuppressWarnings("unused")
public class JsonHttpMessageConverter extends FastJsonHttpMessageConverter{
	/*@Override  
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage)  
            throws IOException, HttpMessageNotWritableException {  
        // TODO Auto-generated method stub  
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";  
        JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);  
        super.writeInternal(obj, outputMessage);  
  
    }  */
	public static SerializeConfig mapping = new SerializeConfig();  
	  
	private String defaultDateFormat;
    private String defaultTimeStampFormat;
  
    @Override  
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
    	mapping.put(Map.class, new MapSerializer());
        OutputStream out = outputMessage.getBody();  
        String text = JSON.toJSONString(obj, mapping, super.getFeatures());  
        byte[] bytes = text.getBytes(getCharset());  
        out.write(bytes);  
    }  
  
    public void setDefaultDateFormat(String defaultDateFormat) { 
        mapping.put(Date.class, new SimpleDateFormatSerializer(defaultDateFormat)); 
    }

	public void setDefaultTimeStampFormat(String defaultTimeStampFormat) {
		mapping.put(Timestamp.class, new SimpleDateFormatSerializer(defaultTimeStampFormat)); 
	}
}

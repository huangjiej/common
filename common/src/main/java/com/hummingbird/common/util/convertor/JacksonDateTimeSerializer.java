/**
 * 
 * JacksonDateSerializer.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util.convertor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * @author john huang
 * 2015年7月9日 下午6:04:40
 * 本类主要做为 转换日期到yyyy-MM-dd HH:mm:ss
 */
public class JacksonDateTimeSerializer extends JsonSerializer<Date> {

	@Override 
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException { 
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
            String formattedDate = formatter.format(value); 
            jgen.writeString(formattedDate); 
    } 
}

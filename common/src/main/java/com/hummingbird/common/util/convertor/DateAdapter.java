/**
 * 
 * aaa.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util.convertor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.util.DateUtil;
import com.hummingbird.common.util.StrUtil;

/**
 * @author huangjiej_2
 * 2015年1月26日 下午3:19:00
 * 本类主要做为jaxb日期转换处理器
 */
public class DateAdapter extends XmlAdapter<String, Date> implements Converter {
	 
    @Override
    public Date unmarshal(String dateStr) throws Exception {
        if(StringUtils.isBlank(dateStr)){
        	return null;
        }
        return DateUtil.parse(dateStr).getTime();
    }
 
    @Override
    public String marshal(Date date) throws Exception {
        if(date==null){
        	return "";
        }
        return DateUtil.format2DateTime(date);
    }
    
    public Object convert(Class type, Object value){  
        if(value == null){  
            return null;  
        }else if(type == Timestamp.class){  
            return convertToDate(type, value, "yyyy-MM-dd HH:mm:ss");  
        }else if(type == Date.class){  
            return convertToDate(type, value, "yyyy-MM-dd");  
        }else if(type == String.class){  
            return convertToString(type, value);  
        }  
  
        throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());  
    }  
  
    protected Object convertToDate(Class type, Object value, String pattern) {  
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
        if(value instanceof String){  
            try{  
                if(StrUtil.isBlank(value.toString())){  
                    return null;  
                }  
                Date date = sdf.parse((String) value);  
                if(type.equals(Timestamp.class)){  
                    return new Timestamp(date.getTime());  
                }  
                return date;  
            }catch(Exception pe){  
                return null;  
            }  
        }else if(value instanceof Date){  
            return value;  
        }  
          
        throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());  
    }  
  
    protected Object convertToString(Class type, Object value) {  
        if(value instanceof Date){  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
              
            if (value instanceof Timestamp) {  
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            }   
              
            try{  
                return sdf.format(value);  
            }catch(Exception e){  
                throw new ConversionException("日期转换为字符串时出错！");  
            }  
        }else{  
            return value.toString();  
        }  
    }  
 
}

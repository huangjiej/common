/**
 * 
 * aaa.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util.convertor;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.util.DateUtil;

/**
 * @author huangjiej_2
 * 2015年1月26日 下午3:19:00
 * 本类主要做为jaxb日期转换处理器
 */
public class DateAdapter extends XmlAdapter<String, Date> {
	 
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
 
}

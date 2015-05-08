package com.hummingbird.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang.math.NumberUtils;

public class PropertiesUtil {
	Properties prop = new Properties();
	
	public PropertiesUtil(){
		InputStreamReader in=null;
		try {
			in = new InputStreamReader(PropertiesUtil.class
					.getResourceAsStream("/common.properties"),"utf8");
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(in!=null)
			{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getProperty(String key) {
		
		return prop.getProperty(key);
	}
	
	/**
	 * 获取boolean类型
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key){
		String keystr = getProperty(key);
		return "true".equalsIgnoreCase(keystr)||"1".equalsIgnoreCase(keystr)||"yes".equalsIgnoreCase(keystr);
	}
	/**
	 * 获取boolean类型
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key,boolean nulldefault){
		String keystr = getProperty(key);
		if(keystr==null)
		{
			return nulldefault;
		}
		return "true".equalsIgnoreCase(keystr)||"1".equalsIgnoreCase(keystr)||"yes".equalsIgnoreCase(keystr);
	}
	
	/**
	 * 获取int值
	 * @param key
	 * @param nulldefault
	 * @return
	 */
	public int getInt(String key,int nulldefault){
		String keystr = getProperty(key);
		return NumberUtils.toInt(keystr,nulldefault);
	}
}

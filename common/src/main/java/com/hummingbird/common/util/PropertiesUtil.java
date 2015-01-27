package com.hummingbird.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

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
}

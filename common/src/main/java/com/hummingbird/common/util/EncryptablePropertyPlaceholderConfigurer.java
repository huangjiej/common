/**
 * 
 * aa.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.hummingbird.common.constant.HummingbirdConst;

/**
 * @author john huang
 * 2015年6月17日 下午12:03:45
 * 本类主要做为 加密数据库连接
 */
public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {  
    private static final String key = HummingbirdConst.HUMMINGBIRDKEY;  
  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)  
        throws BeansException {  
            try {  
                String username = props.getProperty("jdbc.username");  
                if (username != null) {  
                    props.setProperty("jdbc.username", DESUtil.decodeDES(username,(key)));  
                }  
                  
                String password = props.getProperty("jdbc.password");  
                if (password != null) {  
                    props.setProperty("jdbc.password", DESUtil.decodeDES(password, (key)));  
                }  
                  
                String url = props.getProperty("jdbc.url");  
                if (url != null) {  
                    props.setProperty("jdbc.url", DESUtil.decodeDES(url, (key)));  
                }  
                  
                String driverClassName = props.getProperty("jdbc.driver");  
                if(driverClassName != null){  
                    props.setProperty("jdbc.driver", DESUtil.decodeDES(driverClassName, (key)));  
                }  
                  
                super.processProperties(beanFactory, props);  
            } catch (Exception e) {  
                e.printStackTrace();  
                throw new BeanInitializationException(e.getMessage());  
            }  
        }
    
    
    public static void main(String[] args) {
    	System.out.println("jdbc:mysql://115.29.7.155/maccount?characterEncoding=utf8"+"======>"+DESUtil.encryptDes("jdbc:mysql://115.29.7.155/maccount?characterEncoding=utf8", key));
    	System.out.println("mtuser"+"======>"+DESUtil.encryptDes("mtuser", key));
    	System.out.println("mtuser"+"======>"+DESUtil.encryptDes("mtuser", key));
    	System.out.println("com.mysql.jdbc.Driver"+"======>"+DESUtil.encryptDes("com.mysql.jdbc.Driver", key));
	}
    
    
}

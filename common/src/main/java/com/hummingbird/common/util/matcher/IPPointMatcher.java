/**
 * 
 * IPPointMatcher.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util.matcher;

import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;

/**
 * @author huangjiej_2
 * 2015年1月14日 下午12:44:44
 * 本类主要做为单个ip地址的匹配
 */
public class IPPointMatcher extends AbstractMatcher {

	
	
	public IPPointMatcher() {
		super();
		super.matchspliter= "\\.";
	}
	IntegerRangeMatcher part1 = new IntegerRangeMatcher();
	IntegerRangeMatcher part2 = new IntegerRangeMatcher();
	IntegerRangeMatcher part3 = new IntegerRangeMatcher();
	IntegerRangeMatcher part4 = new IntegerRangeMatcher();
	/* (non-Javadoc)
	 * @see com.hummingbird.common.util.matcher.AbstractMatcher#initPattern(java.lang.String)
	 */
	@Override
	public void initPattern(String pattern) throws DataInvalidException {
		if(StringUtils.isBlank(pattern)){
			throw ValidateException.ERROR_PARAM_NULL.cloneAndAppend(null,"格式定义");
		}
		
		String[] split = pattern.split(matchspliter,4);
		if(split.length<4)
		{
			throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(null,"非IP地址格式:"+pattern);
		}
		part1.initPattern(split[0]);
		part2.initPattern(split[1]);
		part3.initPattern(split[2]);
		part4.initPattern(split[3]);
		
		
	}
	/* (non-Javadoc)
	 * @see com.hummingbird.common.util.matcher.AbstractMatcher#match(java.lang.Object)
	 */
	@Override
	public boolean match(Object obj) throws DataInvalidException {
		if(obj==null) return false;
		if (obj instanceof InetAddress) {
			InetAddress address = (InetAddress) obj;
			String hostAddress = address.getHostAddress();
			return matchIpaddress(hostAddress);
		}
		else {
			String ip = ObjectUtils.toString(obj);
			if(isIPAddress(ip)){
				return matchIpaddress(ip);
			}
			else{
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(null,"格式定义出错，非地址格式:"+ip);
			}
		}
	}
	
	/**
	 * 匹配ip地址
	 * @param ip
	 * @return
	 * @throws DataInvalidException
	 */
	public boolean matchIpaddress(String ip) throws DataInvalidException {
		String[] split = ip.split(matchspliter,4);
		
		return part1.match(split[0])&&
		part2.match(split[1])&&
		part3.match(split[2])&&
		part4.match(split[3]);
		
		
	}
	
	/**
	 * 是否ip地址
	 * @param ip
	 * @return
	 */
	public boolean isIPAddress(String ip){
		if(StringUtils.isBlank(ip)) return false;
		Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		Matcher matcher = pattern.matcher(ip); //以验证127.400.600.2为例
		return matcher.matches();
	}
	
	
}

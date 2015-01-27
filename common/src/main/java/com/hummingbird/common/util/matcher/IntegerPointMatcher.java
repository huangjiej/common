/**
 * 
 * IntegerMatcher.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util.matcher;

import org.apache.commons.lang.ObjectUtils;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;

/**
 * @author huangjiej_2
 * 2015年1月14日 上午8:49:51
 * 本类主要做为 整数的单数值匹配
 */
public class IntegerPointMatcher extends AbstractMatcher {
	
	/**
	 * 初始化格式
	 * @param pattern
	 * @throws DataInvalidException
	 */
	public void initPattern(String pattern) throws DataInvalidException {
		try {
			pointInt = Integer.parseInt(pattern);
		} catch (NumberFormatException e) {
			throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"格式定义出错，非数值格式");
		}
	}

	Integer pointInt;
	
	/**
	 * 是否匹配
	 * @param obj
	 * @return
	 * @throws DataInvalidException
	 */
	public boolean match(Object obj) throws DataInvalidException{
		if(obj!=null){
			if (obj instanceof Number) {
				Number pattern = (Number) obj;
				return pattern.intValue()==pointInt;
			}
			String targetstr = ObjectUtils.toString(obj);
			try {
				int targetint = Integer.parseInt(targetstr);
				return targetint==pointInt;
			} catch (NumberFormatException e) {
				throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"格式匹配失败");
			}
			
		}
		return false;
	}
	
	
}

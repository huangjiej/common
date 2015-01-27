/**
 * 
 * IntegerMatcher.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util.matcher;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;

/**
 * @author huangjiej_2
 * 2015年1月14日 上午8:49:51
 * 本类主要做为 整数的范围数值匹配
 */
public class IntegerRangeMatcher extends IntegerPointMatcher{

	protected String rangeSpliter = "-";
	
	protected boolean isrange = true;
	
	/* (non-Javadoc)
	 * @see com.hummingbird.common.util.IntegerMatcher#initPattern(java.lang.String)
	 */
	public void initPattern(String pattern) throws DataInvalidException {
		if(StringUtils.isBlank(pattern)){
			throw ValidateException.ERROR_PARAM_NULL.cloneAndAppend(null,"格式定义");
		}
		pattern=pattern.trim();
			int rangeindex = pattern.indexOf("-");
			if(-1!=rangeindex){
				try {
					startInt = Integer.parseInt(pattern.substring(0,rangeindex));
					endInt = Integer.parseInt(pattern.substring(1+rangeindex));
					
				} catch (NumberFormatException e) {
					throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"格式定义出错，非数值格式:"+pattern);
				}
			}
			else{
				isrange=false;
				super.initPattern(pattern);
			}
	}
	
	/**
	 * 范围开始
	 */
	protected Integer startInt;
	/**
	 * 范围结束
	 */
	protected Integer endInt;
	
	/**
	 * 是否匹配
	 * @param obj
	 * @return
	 * @throws DataInvalidException
	 */
	public boolean match(Object obj) throws DataInvalidException{
		
		
		if(obj!=null){
			if(isrange){
				
				if (obj instanceof Number) {
					Number pattern = (Number) obj;
					int target = pattern.intValue();
					return target>=startInt && target<= endInt;
				}
				String targetstr = ObjectUtils.toString(obj);
				try {
					int targetint = Integer.parseInt(targetstr);
					return targetint>=startInt && targetint<= endInt;
				} catch (NumberFormatException e) {
					throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"范围格式匹配失败");
				}
			}
			else{
				return super.match(obj);
			}
			
		}
		return false;
	}
	
	
}

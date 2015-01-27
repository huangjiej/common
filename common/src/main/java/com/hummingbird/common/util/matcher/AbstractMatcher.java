/**
 * 
 * AbstractMatcher.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util.matcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hummingbird.common.exception.DataInvalidException;

/**
 * @author huangjiej_2
 * 2015年1月14日 下午12:41:54
 * 本类主要做为
 */
public abstract class AbstractMatcher {
	
	
	protected String matchspliter = ",";
	
	protected List<AbstractMatcher> matchers = new ArrayList<AbstractMatcher>();
	

	/**
	 * 初始化格式
	 * @param pattern
	 * @throws DataInvalidException
	 */
	public abstract void initPattern(String pattern) throws DataInvalidException;
	
	/**
	 * 是否匹配
	 * @param obj
	 * @return
	 * @throws DataInvalidException
	 */
	/**
	 * 是否匹配
	 * @param obj
	 * @return
	 * @throws DataInvalidException
	 */
	public boolean match(Object obj) throws DataInvalidException{
		if(obj==null) return false;
		for (Iterator iterator = matchers.iterator(); iterator.hasNext();) {
			IntegerRangeMatcher matcher = (IntegerRangeMatcher) iterator.next();
			if(matcher.match(obj)){
				return true;
			}
		}
		return false;
	}
	
	
	
}

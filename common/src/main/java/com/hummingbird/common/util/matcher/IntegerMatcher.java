/**
 * 
 * IntegerMatcher.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util.matcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.MatchExecutor.Matcher;

/**
 * @author huangjiej_2
 * 2015年1月14日 上午9:57:15
 * 本类主要做为 数值匹配器
 */
public class IntegerMatcher extends AbstractMatcher {
	
	
	/**
	 * 初始化格式
	 * @param pattern
	 * @throws DataInvalidException
	 */
	public void initPattern(String pattern) throws DataInvalidException {
		if(StringUtils.isBlank(pattern)){
			throw ValidateException.ERROR_PARAM_NULL.cloneAndAppend(null,"格式定义");
		}
		
		String[] matcherPrnArr = pattern.split(matchspliter);
		for (int i = 0; i < matcherPrnArr.length; i++) {
			String matchprnStr = matcherPrnArr[i];
			IntegerRangeMatcher integerRangeMatcher = new IntegerRangeMatcher();
			integerRangeMatcher.initPattern(matchprnStr);
			matchers.add(integerRangeMatcher);
		}
		
		
	}
	
	/**
	 * 获取得到的范围比较器
	 * @return
	 */
	public List<IntegerRangeMatcher> getMatchers(){
		List <IntegerRangeMatcher> list = new ArrayList<IntegerRangeMatcher>();
		for (Iterator iterator = matchers.iterator(); iterator.hasNext();) {
			AbstractMatcher abstractMatcher = (AbstractMatcher) iterator.next();
			if (abstractMatcher instanceof IntegerRangeMatcher) {
				IntegerRangeMatcher irm = (IntegerRangeMatcher) abstractMatcher;
				list.add(irm);
				
			}
		}
		return list;
	}
	
	
	

}

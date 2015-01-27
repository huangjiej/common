/**
 * common
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author huangjiej_2
 * 2014年12月15日 下午3:33:02
 * 本类主要做为
 */
public class MatchExecutor {

	String rawstr ;
	List<? extends Matcher> matchers = new ArrayList<Matcher>();
	
	public boolean matcher(Object obj){
		for (Iterator iterator = matchers.iterator(); iterator.hasNext();) {
			Matcher matcher = (Matcher) iterator.next();
			if(matcher.match(obj)){
				return true;
			}
		}
		return false;
	}
	
	public void addMatcher(Matcher matcher){
		//matchers.add(matcher);
	}
	
	
	
	public static interface Matcher{
		
		public boolean match(Object obj);
		
		
	}
	
}

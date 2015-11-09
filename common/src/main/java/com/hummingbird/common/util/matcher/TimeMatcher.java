/**
 * 
 * IntegerMatcher.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util.matcher;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.hummingbird.common.exception.DataInvalidException;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.DateUtil;

/**
 * @author huangjiej_2
 * 2015年1月14日 上午8:49:51
 * 本类主要做为 时间的范围数值匹配
 */
public class TimeMatcher extends AbstractMatcher {
	
	protected String minspliter = ":";

	protected String rangeSpliter = "-";
	
	protected boolean isrange = true;
	
	/**
	 * 精确到的字段
	 */
	protected int extractfield = Calendar.SECOND;
	
	/* (non-Javadoc)
	 * @see com.hummingbird.common.util.IntegerMatcher#initPattern(java.lang.String)
	 */
	public void initPattern(String pattern) throws DataInvalidException {
		if(StringUtils.isBlank(pattern)){
			throw ValidateException.ERROR_PARAM_NULL.cloneAndAppend(null,"格式定义");
		}
		
		String[] matcherPrnArr = pattern.split(matchspliter);
		for (int i = 0; i < matcherPrnArr.length; i++) {
			String matchprnStr = matcherPrnArr[i];
			TimeRangeMatcher timeRangeMatcher = new TimeRangeMatcher();
			timeRangeMatcher.initPattern(matchprnStr);
			matchers.add(timeRangeMatcher);
		}
	}
	
	/**
	 * 尝试获得时间
	 * @param substring
	 * @return
	 * @throws ParseException 
	 */
	private Date try2getTime(String hourmin) throws ParseException {
		Date time;
		try {
			time = DateUtils.parseDate(hourmin, new String[]{"HH:mm:ss","HHmmss","HH:mm","HHmm"});
			return time;
		} catch (Exception e) {
			//出错了,尝试下一个
		}
		
		try {
			time = DateUtils.parseDate(hourmin, new String[]{"HHmm","HH:mm"});
			extractfield = Calendar.MINUTE;
		} catch (Exception e) {
			//出错了,尝试下一个
		}
		time = DateUtils.parseDate(hourmin, new String[]{"HH"});
		extractfield = Calendar.HOUR_OF_DAY;
		return time;
	}

	/**
	 * 范围开始
	 */
	protected Date startTime;
	/**
	 * 范围结束
	 */
	protected Date endTime;
	
	/**
	 * 是否匹配
	 * @param obj
	 * @return
	 * @throws DataInvalidException
	 */
	public boolean match(Object obj) throws DataInvalidException{
		
		
		if(obj!=null){
				
				if (obj instanceof Number) {
					if(Calendar.HOUR_OF_DAY==extractfield){
						
						Number pattern = (Number) obj;
						int target = pattern.intValue();
						return target>=startTime.getHours() && target<= endTime.getHours();
					}
					throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(null,"无法识别的匹配内容:"+obj);
				}
				if (obj instanceof Date) {
					Date targetdate = (Date) obj;
					return targetdate.after(startTime)&&targetdate.before(endTime);
				}
				String targetstr = ObjectUtils.toString(obj);
				try {
					int targetint = Integer.parseInt(targetstr);
					return targetint>=startTime.getHours() && targetint<= endTime.getHours();
				} catch (NumberFormatException e) {
					throw ValidateException.ERROR_PARAM_FORMAT_ERROR.clone(e,"范围格式匹配失败");
				}
			
		}
		return false;
	}
	
	/**
	 * 取开始数值，应该使用 >=
	 * @return
	 */
	public Date getStartTime(){
		return startTime;
	} 
	
	/**
	 * 取结束数值，应该使用 <=
	 * @return
	 */
	public Date getEndTime(){
		return endTime;
	} 
	
	
	/**
	 * 获取得到的范围比较器
	 * @return
	 */
	public List<TimeRangeMatcher> getMatchers(){
		List <TimeRangeMatcher> list = new ArrayList<TimeRangeMatcher>();
		for (Iterator iterator = matchers.iterator(); iterator.hasNext();) {
			AbstractMatcher abstractMatcher = (AbstractMatcher) iterator.next();
			if (abstractMatcher instanceof TimeRangeMatcher) {
				TimeRangeMatcher irm = (TimeRangeMatcher) abstractMatcher;
				list.add(irm);
				
			}
		}
		return list;
	}
	
	public static void main(String[] args) throws DataInvalidException {
		TimeRangeMatcher tm = new TimeRangeMatcher();
		tm.initPattern("00:00:00-03:05");
		System.out.println(DateUtil.format(tm.getStartTime(),"HH:mm:ss").equals("00:00:00"));
		System.out.println(DateUtil.format(tm.getEndTime(),"HH:mm:ss").equals("03:05:00"));
		
		TimeMatcher tm1 = new TimeMatcher();
		tm1.initPattern("00:01:00-03:05,08:23-22:22,26:90:23");
		List<TimeRangeMatcher> matchers2 = tm1.getMatchers();
		for (Iterator iterator = matchers2.iterator(); iterator.hasNext();) {
			TimeRangeMatcher timeRangeMatcher = (TimeRangeMatcher) iterator.next();
			System.out.println(DateUtil.format(timeRangeMatcher.getStartTime(),"HH:mm:ss"));
			System.out.println(DateUtil.format(timeRangeMatcher.getEndTime(),"HH:mm:ss"));
		}
	}
}

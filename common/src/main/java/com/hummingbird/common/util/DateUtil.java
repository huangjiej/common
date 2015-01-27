package com.hummingbird.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	private static final Log log = LogFactory.getLog(DateUtil.class);
	
	private static SimpleDateFormat commondateformtter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//标准的中文日期格式
		private static SimpleDateFormat	fdate		= new SimpleDateFormat("yyyy-MM-dd");
		private static SimpleDateFormat	ftime		= new SimpleDateFormat("HH:mm:ss");
		private static SimpleDateFormat	fdatetime	= new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		
		private static String datespliter = "-";//日期分隔符
	    private static String timespliter = ":";//时间分隔符
	    private static String datetimespliter = " ";//日期和时间分隔符
	
	public static String getCurrentTimeStr() {
		Date currentTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(currentTime);
	}

	public static String getCurrentTimeStr(String ft) {
		Date currentTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat(ft);
		return format.format(currentTime);
	}
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String parseTimestampToString(Timestamp timestamp, String ft) {
		String result = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(ft);
			result = df.format(timestamp);
		} catch (Exception e) {
			log.debug(e.getMessage(),e);
		}
		return result;
	}


	public static Timestamp parseDateToTimestamp(Date date, String ft) {
		Timestamp timesamp = null;
		if (date != null) {
			try {
				SimpleDateFormat df = new SimpleDateFormat(ft);
				String result = df.format(date);
				timesamp = Timestamp.valueOf(result);
			} catch (Exception e) {
				log.debug(e.getMessage(),e);
			}
		}
		return timesamp;
	}
	
	public static String getTimeoutStr(int millisecond) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cd = Calendar.getInstance();
		cd.setTime(new Date());
		cd.add(Calendar.MILLISECOND, millisecond);
		return format.format(cd.getTime());
	}
	
	public static String getCurrentDateStr() {
		return getDateStr(new Date());
	}
	
	public static String getDateStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}
	public static String getDateStrFormat(Date date,String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(date);
	}
    public static Date dateAdd(Date date, int field, int amount) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(field, amount);
        return gc.getTime();
    }
    
    /**
     * 按yyyy-MM-dd HH:mm:ss进行格式化
     * @param date
     * @return
     */
    public static String formatCommonDate(Date date){
    	return commondateformtter.format(date);
    }
    
    /**
     * 按yyyy-MM-dd HH:mm:ss进行格式化，如为空则返回“”
     * @param date
     * @return
     */
    public static String formatCommonDateorNull(Date date){
    	return date==null?"":commondateformtter.format(date);
    }
    
    /**
     * 字符串转日期
     * @param datestr
     * @param formatter
     * @return
     */
    public static Date parseDate(String datestr,String formatter){
    	if(StringUtils.isBlank(datestr)){
    		return null;
    	}
    	SimpleDateFormat format ;
    	if(StringUtils.isBlank(formatter)){
    		format = commondateformtter;
    	}
    	else{
    		format = new SimpleDateFormat(formatter);
    	}
    	Date date;
		try {
			date = format.parse(datestr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    	return date;
    }

	/**
	 * 格式化今天
	 * @param string
	 * @return
	 */
	public static String formatToday(String parrent) {
		return getDateStrFormat(new Date(),parrent);
	}
	
	/**
	 * 输出标准日期
	 * @param millionSecond
	 * @return
	 */
	public static String getDateStr(long millionSecond)
	{
		return fdate.format(new java.util.Date(millionSecond));
	}
	/**
	 * 输出标准时间
	 * @param millionSecond
	 * @return
	 */
	public static String getTimeStr(long millionSecond)
	{
		return ftime.format(new java.util.Date(millionSecond));
	}
	/**
	 * 输出标准日期时间
	 * @param millionSecond
	 * @return
	 */
	public static String getDateTimeStr(long millionSecond)
	{
		return fdatetime.format(new java.util.Date(millionSecond));
	}
	
	/**
	 * 自定义时间格式输出 (e.g. yyyy-MM-dd HH:mm:ss.f)
	 * @param millionSecond
	 * @param pattern
	 * @return
	 */
	public static String date2Str(long millionSecond,String pattern)
	{
		return new java.text.SimpleDateFormat(pattern).format(new java.util.Date(millionSecond));
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date,String pattern)
	{
		if(fdate.toPattern().equals(pattern))
		{
			return fdate.format(date);
		}
		else if (fdatetime.toPattern().equals(pattern)) {
			return fdatetime.format(date);
		}
		else if(ftime.toPattern().equals(pattern))
		{
			return ftime.format(date);
		}
		else
		{
			return new SimpleDateFormat(pattern).format(date);
		}
	}
	/**
	 * 格式化成长日期格式
	 * @param date
	 * @return
	 */
	public static String format2DateTime(Date date) {
		return fdatetime.format(date);
	}
	
	/**
     * 日期时间通用转换，可能转换(19)99-12-1 或 12:23 或 99-12-1 12:23(:34) 的格式
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Calendar parse(String dateStr) throws ParseException
    {
    	String[] parts = dateStr.split(datetimespliter);
    	Calendar c = Calendar.getInstance();
    	if(parts.length>1)
    	{
    		//完整的日期时间字段
    		parseDate(c, parts[0]);
    		parseTime(c, parts[1]);
    	}
    	else 
    	{
    		if(parts[0].indexOf(datespliter)!=-1)
    		{
    			parseDate(c, parts[0]);
    			parseTime(c, "00:00:00");
    		}
    		else if(parts[0].indexOf(timespliter)!=-1)
    		{
    			parseTime(c, parts[0]);
    		}
    		else
    		{
    			throw new ParseException("转换错误，未知的日期格式"+dateStr,0);
    		}
    		
    	}
    	return c;
    }
    
    protected static void parseDate(Calendar c,String dateStr)
    {
    	String [] dateparts = dateStr.split(datespliter);
    	int year = Integer.parseInt(dateparts[0]);
    	if(year<100)
    	{
    		year+=1900;
    	}
		c.set(Calendar.YEAR, year);
		c.set(c.MONTH, Integer.parseInt(dateparts[1])-1);
		c.set(c.DAY_OF_MONTH,Integer.parseInt(dateparts[2]));
    }
    
    protected static void parseTime(Calendar c,String timeStr)
    {
    	String [] timeparts = timeStr.split("\\"+timespliter);
		c.set(c.HOUR_OF_DAY,Integer.parseInt(timeparts[0]));
		c.set(c.MINUTE,Integer.parseInt(timeparts[1]));
		if(timeparts.length>2)
		{
			c.set(c.SECOND,Integer.parseInt(timeparts[2]));
		}
		else
		{
			c.set(c.SECOND, 0);
		}
    }
    
    /**
     * 转换字符串至日期
     * @param sdate
     * @return
     * @throws ParseException 
     */
    public static Date parse2date(String sdate) throws ParseException{
    	Calendar calendar = parse(sdate);
    	if(calendar!=null){
    		return calendar.getTime();
    	}
    	return null;
    }
    
    /**
     * 根据指定的格式转换数据
     * @param sdate
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parse2date(String sdate,String pattern) throws ParseException{
    	return new SimpleDateFormat(pattern).parse(sdate);
    }
    
    /**
     * 格式化为一天的开始
     * @param date
     * @return 
     * @return
     */
    public static Date toDayStart(Date date){
    	Date ret;
    	ret=DateUtils.truncate(date, Calendar.DATE);
    	return ret;
    }
    
    /**
     * 格式化为一天的开始
     * @param date
     * @return
     * @throws ParseException 
     */
    public static Date toDayStart(String dateStr) throws ParseException{
    	Calendar cal = parse(dateStr);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	return cal.getTime();
    }
    /**
     * 格式化为一天的结束
     * @param date
     * @return
     * @throws ParseException 
     */
    public static Date toDayEnd(String dateStr) throws ParseException{
    	Calendar cal = parse(dateStr);
    	cal.set(Calendar.HOUR_OF_DAY, 23);
    	cal.set(Calendar.MINUTE, 59);
    	cal.set(Calendar.SECOND, 59);
    	cal.set(Calendar.MILLISECOND, 999);
    	
//    	DateUtils.trunate(cal, Calendar.DATE );
//    	DateUtils.truncate(parse, Calendar.MINUTE);
//    	DateUtils.truncate(parse, Calendar.SECOND);
//    	parse.add(Calendar.SECOND, 86359);
    	return cal.getTime();
    }
    
    /**
     * 格式化为一天的结束
     * @param date
     * @return 
     * @return
     */
    public static Date toDayEnd(Date date){
    	Date ret;
    	ret=DateUtils.truncate(date, Calendar.DATE);
//    	ret=DateUtils.truncate(ret, Calendar.MINUTE);
//    	ret=DateUtils.truncate(ret, Calendar.SECOND);
    	ret=DateUtils.add(ret, Calendar.DATE , 1);
    	ret=DateUtils.add(ret, Calendar.SECOND, -1);
    	return ret;
    }
    
    public static void main(String[] args) throws ParseException {
		System.out.println(toDayEnd("2014-01-01 13:23:34"));
		System.out.println(toDayStart("2014-01-01 13:23:34"));
		Date date = new Date();
		System.out.println(toDayEnd(date));
		System.out.println(toDayStart(date));
	}
}

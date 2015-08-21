package com.hummingbird.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 * @author huangjj
 * @create_time 2012-4-25 上午07:24:02
 */
public class RegexUtil
{
	

	/**
	 * 正则替换字符串
	 * 使用方法  ${aa}12,${bb}34,对 \\$\\{(.+?)\\} 替换为其它值
	 * @param str 原字符串
	 * @param patternStr 查找字符串,注意里面的配置字符串需要用到()进行分组
	 * @param func 替换方法
	 * @return
	 */
	public static String replace(String str,String patternStr,RegexUtilReplaceFunc func)
	{
		Pattern p = Pattern.compile(patternStr);
		Matcher matcher = p.matcher(str);
		while (matcher.find()) { 
            String s0 = matcher.group(); 
            for (int i = 0; i < matcher.groupCount(); i++) {
				String param = matcher.group(i+1);
				String replacestr = func.replace(param);
				str=str.replace(s0,replacestr);
			}
		}
		
		return str;
	}

	/**
	 * 提取匹配的内容至新字符串中,新字符串用{1},{2}来代表要替换的内容
	 * @param str
	 * @param patternStr
	 * @param newString4digit
	 * @return
	 */
	public static String replaceNoselect(String str,String patternStr,String newString4digit){
		Pattern p = Pattern.compile(patternStr);
		Matcher matcher = p.matcher(str);
		while (matcher.find()) { 
            List<String> matchs = new ArrayList<String>();
            for (int i = 0; i < matcher.groupCount(); i++) {
				String param = matcher.group(i+1);
				newString4digit =newString4digit.replaceAll("\\{"+(i+1)+"\\}", param);
			}
		}
		return newString4digit;
	
	}
	
	/**
	 * 从字符串中提取指定的内容，提取的内容以（）进行分组
	 * @param str
	 * @param patternStr
	 * @return
	 */
	public static String[] extractMatch(String str,String patternStr){
		Pattern p = Pattern.compile(patternStr);
		Matcher matcher = p.matcher(str);
		while (matcher.find()) { 
            List<String> matchs = new ArrayList<String>();
            for (int i = 0; i < matcher.groupCount(); i++) {
				String param = matcher.group(i+1);
				matchs.add(param);
			}
            return matchs.toArray(new String[]{});
		}
		
		return new String[]{};
	}
	
	public static void main(String[] args) {
		String[] extractMatch = RegexUtil.extractMatch("cryptCBCWithAppkey(accountid,mobilenum)", "cryptCBCWithAppkey\\((.+)*,?(.+)\\)");
		for (int i = 0; i < extractMatch.length; i++) {
			String string = extractMatch[i];
			System.out.println(string);
		}
	}
	
}



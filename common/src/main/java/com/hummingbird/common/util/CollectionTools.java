package com.hummingbird.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparatorChain;

import com.hummingbird.common.exception.ToolsException;

/**
 * 列表工具，
 * @author huangjj
 * 
 */
public abstract class CollectionTools {
	/**
	 * 指定字段名，把对象转变成某字段组成的字符串数组,主要是用于一组相同对象提取数据
	 * @param fieldname  字段名，如果为空则采用toString()方法
	 * @author 黄杰俊
	 */
	public static String[] convert2StrArray(Collection list, String fieldname)	throws Exception {
		String[] strs = new String[list.size()];
		if(fieldname==null)
		{
			//使用toString()
			int index = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object obj = (Object) iter.next();
				strs[index++] = obj.toString();
			}
		}
		else
		{
			// getXXX
			String methodname = "get" + fieldname.substring(0, 1).toUpperCase()
					+ fieldname.substring(1);
			int index = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object obj = (Object) iter.next();
				Method method = null;
				method = obj.getClass().getMethod(methodname, new Class[] {});// 执行getXXX方法，返回数组
				strs[index++] = (String) method.invoke(obj, new Object[] {});
			}
		}
		return strs;
	}
	/**
	 * 指定字段名，把对象转变成某字段组成的字符串数组,主要是用于一组相同对象提取数据
	 * @param fieldname  字段名，如果为空则采用toString()方法
	 * @author 黄杰俊
	 */
	public static String[] convert2StrArray(Object[] list, String fieldname)	throws Exception {
		String[] strs = new String[list.length];
		if(fieldname==null)
		{
			//使用toString()
			int index = 0;
			for (int i = 0; i < list.length; i++)
			{
				Object obj = list[i];
				strs[index++] = obj.toString();
			}

		}
		else
		{
			// getXXX
			String methodname = "get" + fieldname.substring(0, 1).toUpperCase()
					+ fieldname.substring(1);
			int index = 0;
			for (int i = 0; i < list.length; i++)
			{
				Object obj = list[i];
				strs[index++] = obj.toString();
				Method method = null;
				method = obj.getClass().getMethod(methodname, new Class[] {});// 执行getXXX方法，返回数组
				strs[index++] = (String) method.invoke(obj, new Object[] {});
			}

		}
		return strs;
	}

	/**
	 * 把对象的指定字段转换为 xxxx,xxx,xxx的形式 对于要放到在sql中使用，可以对此字符串再次转换 (,-> ',' ，再在前后加上')
	 * @param list
	 * @param fieldname
	 * @return
	 * @throws IEMSException
	 * @author 黄杰俊
	 */
	public static String convert2Str(Collection list, String fieldname)
			throws Exception {
		return convert2Str(list, fieldname, "", "", ",");
	}

	/**
	 * 把对象的指定字段转换为 [前缀]xxxx[后缀][分隔符][前缀]xxx[后缀][分隔符][前缀]xxx的形式
	 * @param list 操作对象
	 * @param fieldname 取的字段名
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @param spliter 分隔符
	 * @return
	 * @throws Exception
	 */
	public static String convert2Str(Collection list, String fieldname,
			String prefix, String suffix, String spliter) throws Exception {
		String[] array = CollectionTools.convert2StrArray(list, fieldname);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(prefix);
			sb.append(array[i]);
			sb.append(suffix);
			sb.append(spliter);
		}
		String result = sb.toString();
		if (sb.length() > 0) {
			result = sb.substring(0, sb.length() - 1);// 去掉最后一个分隔符
		}
		return result;
	}

	/**
	 * 在集合中取出对象
	 * @param c
	 * @param index
	 * @return
	 * @author 黄杰俊
	 */
	public static Object get(Collection c,int index)
	{
		if (c instanceof java.util.List)
		{
			java.util.List list = (java.util.List) c;
			return list.get(index);
		}
		else
		{
			int i=0;
			for (Iterator iterator = c.iterator(); iterator.hasNext();)
			{
				Object o = (Object) iterator.next();
				if(i==index)
				{
					return o;
				}
				i++;
			}
			throw new IndexOutOfBoundsException("集合的个数只有"+c.size()+"个，小于指定数");
		}
	}
	
	/**
	 * 是否是空或空列表
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(Collection list)
	{
		return list==null||list.isEmpty();
	}
	
	/**
	 * 是否是空或空Map
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(Map map)
	{
		return map==null||map.isEmpty();
	}
	/**
	 * 是否是空或空列表
	 * @param list
	 * @return
	 */
	public static boolean isnotEmpty(Collection list)
	{
		return !isEmpty(list);
	}
	
	/**
	 * 是否是空或空Map
	 * @param list
	 * @return
	 */
	public static boolean isnotEmpty(Map map)
	{
		return !isEmpty(map);
	}
	
	/**
	 * 数组是否是非空的
	 * @param arr
	 * @return
	 * @author 黄杰俊
	 */
	public static boolean isnotEmpty(Object[] arr) 
	{
		return !isEmpty(arr);
	}
	/**
	 * 判断数组是否为空
	 * @param arr
	 * @return
	 */
	public static boolean isEmpty(Object[] arr) {
		return arr==null||arr.length==0;
	}
	
	public static String joinArr(String[] arr,String warp,String spliter){
		if(isEmpty(arr)){
			return "";
		}
		String str="";
		try {
			str = new DefaultIteratorFlattenTemplate(warp,spliter).doFlatten(arr);
		} catch (ToolsException e) {
			//由于对字符串没有使用反射，所以不会有这种问题
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 把对象列表中的某个字段重新组装成列表
	 * @param fieldname  字段名
	 * @author 黄杰俊
	 */
	public static List collection2simple(Collection list, String fieldname)	throws Exception {
		if(fieldname==null)
			throw new Exception("字段为空,无法进行转换");
		if(list==null)
			throw new Exception("列表为空,无法进行转换");
		List results = new ArrayList(list.size());
			// getXXX
			String methodname = "get" + fieldname.substring(0, 1).toUpperCase()
					+ fieldname.substring(1);
			int index = 0;
			Method method = null;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object obj = (Object) iter.next();
				if(method==null)
				{
					method = obj.getClass().getMethod(methodname, new Class[] {});// 执行getXXX方法，返回数组
				}
				Object fieldvalue =  method.invoke(obj, new Object[] {});
				results.add(fieldvalue);
			}
		return results;
	}
	
	/**
     * 一个快速的按照列表中对象的属性排序的方法 ，根据方法 的第二至最后一个参数的描述进行顺序的排序。
     * @param list
     * @param fields 由一个或多个字符串组成，字符串由以下部分组成 字段属性|升序/降序标识|空值排序规则，其中后面的属性可以省略
     * 其中第一个值必须是属性，排序的关键字有 asc升序,desc降序,nullHigh空值排最前,nullLow空值排最后
     * 
     * sortByProperty(list,"name","sex","birthday")  按名称，性别，生日
     * sortByProperty(list,"name|asc","sex|asc","birthday|desc")  按名称升序，性别升序，生日降序
     * sortByProperty(list,"name|asc|nullLow","sex|asc|nullHigh","birthday|nullHigh") 按名称升序空值排最后，性别升序空值排最前，生日升序空值排最前
     * 
     */
    public static void sortByProperty(List list,String ... fields )
    {
    	 Comparator comparatorChain = getBeanComparator(fields);
    	 Collections.sort(list, comparatorChain);
    }
    
    /**
     * 一个快速的按照数组中对象的属性排序的方法 ，根据方法 的第二至最后一个参数的描述进行顺序的排序。
     * @param list 数据对象
     * @param fields 由一个或多个字符串组成，字符串由以下部分组成 字段属性|升序/降序标识|空值排序规则，其中后面的属性可以省略
     * 其中第一个值必须是属性，排序的关键字有 asc升序,desc降序,nullHigh空值排最前,nullLow空值排最后
     * 
     * sortByProperty(list,"name","sex","birthday")  按名称，性别，生日
     * sortByProperty(list,"name|asc","sex|asc","birthday|desc")  按名称升序，性别升序，生日降序
     * sortByProperty(list,"name|asc|nullLow","sex|asc|nullHigh","birthday|nullHigh") 按名称升序空值排最后，性别升序空值排最前，生日升序空值排最前
     * 
     */
    public static void sortByProperty(Object[] list,String ... fields )
    {
    	Comparator comparatorChain = getBeanComparator(fields);
    	Arrays.sort(list, comparatorChain);
    }
    
    /**
     * 一个快速生成按照列表中对象的属性排序的方法 ，根据方法 的第一至最后一个参数的描述进行顺序的排序。
     * @param fields 由一个或多个字符串组成，字符串由以下部分组成 字段属性|升序/降序标识|空值排序规则，其中后面的属性可以省略.
     * 其中第一个值必须是属性，排序的关键字有 asc升序,desc降序,nullHigh空值排最前,nullLow空值排最后
     * 
     * sortByProperty(list,"name","sex","birthday")  按名称，性别，生日
     * sortByProperty(list,"name|asc","sex|asc","birthday|desc")  按名称升序，性别升序，生日降序
     * sortByProperty(list,"name|asc|nullLow","sex|asc|nullHigh","birthday|nullHigh") 按名称升序空值排最后，性别升序空值排最前，生日升序空值排最前
     * 
     */
    public static  java.util.Comparator getBeanComparator(String ... fields)
    {
	    ComparatorChain comparatorChain = new ComparatorChain( ); 
		for (int i = 0; i < fields.length; i++)
		{
			String field = fields[i];
			String[] propertys = field.split("\\|");
			Comparator comp=null;
			for (int j = 0; j < propertys.length; j++)
			{
				String property = propertys[j];
				if(j==0)
				{
					comp = new BeanComparator(property);
				}
				else 
				{
					if("desc".equalsIgnoreCase(property))
					{
						comp=ComparatorUtils.reversedComparator(comp);
					}
					else if("nullLow".equalsIgnoreCase(property))
					{
						comp=ComparatorUtils.nullLowComparator(comp);
					}
					else if("nullHigh".equalsIgnoreCase(property))
					{
						comp=ComparatorUtils.nullHighComparator(comp);
					}
				}
			}
			comparatorChain.addComparator(comp);
		}
		return comparatorChain;
	}
	
    
    
    
    
}



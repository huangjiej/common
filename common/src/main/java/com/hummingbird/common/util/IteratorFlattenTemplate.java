/**
 * 
 */
package com.hummingbird.common.util;

import java.util.Collection;
import java.util.Iterator;

import com.hummingbird.common.exception.ToolsException;

/**
 * 迭代扁平化模板，主要是把collection中的对象转变成String输出
 * @author huangjj
 * @create_time 2009-8-24 下午02:18:36
 */
public abstract class IteratorFlattenTemplate
{
	
	protected String spliter = ",";

	/**
	 * @param spliter
	 */
	public IteratorFlattenTemplate(String spliter)
	{
		super();
		this.spliter = spliter;
	}

	/**
	 * 使用默认的分割器,  进行分割
	 */
	public IteratorFlattenTemplate()
	{
		super();
	}
	
	/**
	 * 执行扁平化
	 * @param coll
	 * @return
	 * @throws BaseException 
	 */
	public String doFlatten(Collection<?> coll) throws ToolsException
	{
		if(coll==null||coll.isEmpty())
		{
			return "";
		}
		StringBuilder sb = new StringBuilder(1000);
		String tempspliter="";
		for (Iterator iterator = coll.iterator(); iterator.hasNext();)
		{
			Object object = (Object) iterator.next();
			sb.append(tempspliter);
			sb.append(dowithIterator(object));
			tempspliter = spliter;
		}
		return sb.toString();
	}
	
	/**
	 * 执行扁平化
	 * @param coll
	 * @return
	 * @throws BaseException 
	 */
	public String doFlatten(Object[] coll) throws ToolsException
	{
		if(CollectionTools.isEmpty(coll))
		{
			return "";
		}
		StringBuilder sb = new StringBuilder(1000);
		String tempspliter="";
		for (int i = 0; i < coll.length; i++) {
			Object object = coll[i];
			sb.append(tempspliter);
			sb.append(dowithIterator(object));
			tempspliter = spliter;
			
		}
		return sb.toString();
	}
	
	/**
	 * 对collecion中的元素进行处理
	 * @param object
	 * @return  该元素转换成String 输出
	 * @throws BaseException 
	 */
	protected abstract String dowithIterator(Object object) throws ToolsException
	;
	
	
	
}

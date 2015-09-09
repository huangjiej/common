/**
 * 
 */
package com.hummingbird.common.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.exception.ToolsException;

/**
 * 迭代扁平化模板，主要是把collection中的对象转变成String输出
 * @author huangjj
 * @create_time 2009-8-24 下午02:18:36
 */
public class DefaultIteratorFlattenTemplate extends IteratorFlattenTemplate
{
	protected String warp;
	protected String fieldName;
	
	/**
	 * @param spliter
	 */
	public DefaultIteratorFlattenTemplate(String spliter)
	{
		super();
		this.spliter = spliter;
	}
	
	/**
	 * 
	 * @param warp 包围
	 * @param spliter
	 */
	public DefaultIteratorFlattenTemplate(String warp,String spliter)
	{
		super();
		this.spliter = spliter;
		this.warp=warp;
	}
	/**
	 * 
	 * @param warp 包围
	 * @param spliter
	 */
	public DefaultIteratorFlattenTemplate(String fieldName,String warp,String spliter)
	{
		super();
		this.spliter = spliter;
		this.warp=warp;
		this.fieldName=fieldName;
	}

	/**
	 * 使用默认的分割器,  进行分割
	 */
	public DefaultIteratorFlattenTemplate()
	{
		super();
	}
	
	
	
	/**
	 * 对collecion中的元素进行处理
	 * @param object
	 * @return  该元素转换成String 输出
	 * @throws BaseException 
	 */
	protected  String dowithIterator(Object object) throws ToolsException
	{
		if(object==null){
			return null;
		}
		String str = null;
		if (object instanceof String) {
			str = (String) object;
			
		}
		else{
			if(StringUtils.isNotBlank(fieldName)){
				try {
					str = BeanUtils.getProperty(object, fieldName);
				} catch (Exception e) {
					throw new ToolsException("根据属性取出对象属性值出错",e);
				}
			}
			else{
				str = object.toString();
			}
		}
		if(StringUtils.isNotBlank(warp)){
			str = StrUtil.buildStr(warp,str,warp);
		}
		return str;
	}
	
	
	
}

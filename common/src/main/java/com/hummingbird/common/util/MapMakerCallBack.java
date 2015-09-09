/**
 * 
 */
package com.hummingbird.common.util;

import com.hummingbird.common.exception.BusinessException;


/**
 * MAP创建器callback
 * @author huangjj
 * @create_time 2009-8-31 上午09:45:45
 */
public interface MapMakerCallBack
{
	/**
	 * 从列表中取key值  
	 * @param element
	 * @return
	 * @throws BaseException
	 */
	public Object getKey(Object element) throws BusinessException;
	
	/**
	 * 从列表中取value的值
	 * @param element
	 * @return
	 * @throws BaseException
	 */
	public Object getValue(Object element) throws BusinessException;
}



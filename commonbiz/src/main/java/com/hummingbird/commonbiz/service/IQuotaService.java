/**
 * 
 */
package com.hummingbird.commonbiz.service;

import com.hummingbird.commonbiz.exception.QuotaException;


/**
 * 限额service
 * @author huangjiej_2
 * 2014年9月27日 下午4:46:12
 */
public interface IQuotaService<T> {

	/**
	 * 检查限额
	 * @param createOrderVo
	 * @return
	 * @throws QuotaException
	 */
	boolean validateQuota(T createOrderVo) throws QuotaException;
	
	
	
}

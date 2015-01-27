/**
 * 
 * Validatable.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.vo;

/**
 * @author huangjiej_2
 * 2014年12月29日 下午11:18:45
 * 本类主要做为可被校验的对象
 */
public interface Validatable {

	/**
	 * 进行验证
	 * @return
	 */
	ValidateResult validate(Object param);
	
	
}

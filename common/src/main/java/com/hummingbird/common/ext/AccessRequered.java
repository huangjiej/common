/**
 * 
 */
package com.hummingbird.common.ext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huangjiej_2
 * 2014年10月25日 下午9:37:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessRequered {
	
	String methodName();
	
}

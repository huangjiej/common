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
	
	/**
	 * 功能名称
	 * @return
	 */
	String methodName();
	
	/**
	 * 功能编号
	 * @return
	 */
	int codebase() default 0; 
	
	/**
	 * 从json转成对象时用的类名
	 * @return
	 */
	String className() default "";
	/**
	 * 从json转成对象时,泛型用的类名,必须配合className 使用
	 * @return
	 */
	String genericClassName()  default "";
	
	/**
	 * 是否转为javabean
	 */
	boolean convert2javabean() default true;
	
	/**
	 * 是否为http payload中的 json字符串
	 * @return
	 */
	boolean isJson() default false;
	
	/**
	 * 校验app 和 app method
	 * @return
	 */
	boolean appMethodCheck() default false;
	/**
	 * 记录数据库日志
	 * @return
	 */
	boolean appLog() default true;
	
	
}

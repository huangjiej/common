/**
 * 
 * OrderUtil.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.commonbiz.util;

import java.util.Random;

import com.hummingbird.common.util.DateUtil;

/**
 * @author huangjiej_2
 * 2014年12月27日 下午10:49:19
 * 本类主要做为订单工具类
 */
public class NoGenerationUtil {

	/**
	 * 创建订单号
	 * @return
	 */
	public static String genOrderNo(){
		return genNO("UD00");
	}
	
	public static String genNO(String prefix){
		return prefix+DateUtil.getCurrentTimeStr() + String.format("%014d", new Random().nextInt(999999999));
	}
	public static String genNO(String prefix,int len){
		return prefix+DateUtil.getCurrentTimeStr() + String.format("%"+len+"d", new Random().nextInt(999999999));
	}
	
}

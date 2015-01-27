/**
 * 
 * ArrayBuilder.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author huangjiej_2
 * 2015年1月1日 下午1:46:11
 * 本类主要做为数组的构建器
 */
public class ArrayBuilder<T> {
	
	List<T> internallist = new ArrayList<T>(100);

	/**
	 * 构造函数
	 */
	public ArrayBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 添加一个元素
	 * @param obj
	 * @return
	 */
	public ArrayBuilder<T> add(T obj){
		internallist.add(obj);
		return this;
	}
	
	/**
	 * 在指定位置添加一个元素
	 * @param index
	 * @param obj
	 * @return
	 */
	public ArrayBuilder<T> add(int index,T obj)
	{
		internallist.add(index, obj);
		return this;
	}
	
	/**
	 * 添加所有的元素
	 * @param objs
	 * @return
	 */
	public ArrayBuilder<T> addAll(Collection<T> objs){
		internallist.addAll(internallist);
		return this;
	}
	/**
	 * 添加所有元素
	 * @param objs
	 * @return
	 */
	public ArrayBuilder<T> addAll(T[] objs){
		for (int i = 0; i < objs.length; i++) {
			T obj = objs[i];
			internallist.add(obj);
			
		}
		return this;
	}
	
	/**
	 * 生成数组
	 * @return
	 */
	public T[] getArray(){
		return (T[]) internallist.toArray();
	}

}

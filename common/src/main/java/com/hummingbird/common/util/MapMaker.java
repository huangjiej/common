package com.hummingbird.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;

import com.hummingbird.common.exception.BusinessException;

/**
 * map生成器，用于从collection中生成map
 * @author huangjj
 * @create_time 2009-8-31 上午09:23:20
 */
public class MapMaker
{
	
	/**
	 * 从collection中生成map,
	 * @param c
	 * @param keyproperty 从collection 中的item 中取一个属性作为key值
	 * @param valueproperty 从collection 中的item 中取一个属性作为value值,如果为空，则以整个item作为value值 
	 * @return
	 * @throws BusinessException
	 */
	public Map buildFromList(Collection c,String keyproperty,String valueproperty) throws BusinessException
	{
		return buildFromList(c, keyproperty, valueproperty,new java.util.HashMap());
	}
	
	/**
	 * 从collection中生成map,
	 * @param c
	 * @param keyproperty 从collection 中的item 中取一个属性作为key值
	 * @param valueproperty 从collection 中的item 中取一个属性作为value值,如果为空，则以整个item作为value值 
	 * @param map  待处理的map
	 * @return
	 * @throws BusinessException
	 */
	public Map buildFromList(Collection c,String keyproperty,String valueproperty,Map map) throws BusinessException
	{
		for (Iterator iterator = c.iterator(); iterator.hasNext();)
		{
			Object object = (Object) iterator.next();
			
			try
			{//这里可以改成callback的形式
				Object key;
				key = BeanUtils.getProperty(object, keyproperty);
				Object value=valueproperty==null?object:PropertyUtils.getProperty(object, valueproperty);
				map.put(key, value);
			}
			catch (Exception e)
			{
				throw new BusinessException("从Collectoin中构造map出错,无法找到对象的属性值",e);
			}
		}
		return map;
	}
	
	
	/**
	 * 从collection中生成map,通过回调接口
	 * @param c
	 * @param callback  回调接口
	 * @return
	 * @throws BusinessException
	 */
	public Map buildFromList(Collection c,MapMakerCallBack callback) throws BusinessException
	{
		return buildFromList(c,callback,new HashMap());
	}
	
	/**
	 * 从collection中生成map,通过回调接口
	 * @param c
	 * @param callback 回调接口
	 * @param map   待处理的map
	 * @return
	 * @throws BusinessException
	 */
	public Map buildFromList(Collection c,MapMakerCallBack callback,Map map) throws BusinessException
	{
		for (Iterator iterator = c.iterator(); iterator.hasNext();)
		{
			Object object = (Object) iterator.next();
			Object key = callback.getKey(object);
			Object value=callback.getValue(object);
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * 从collection中生成map,以collection中的item作为value值
	 * @param c
	 * @param keyproperty 从collection 中的item 中取一个属性作为key值
	 * @return
	 * @throws BusinessException
	 */
	public Map buildFromList(Collection c,String keyproperty) throws BusinessException
	{
		return buildFromList(c, keyproperty, null,new java.util.HashMap());
	}
	
	/**
	 * 建立map
	 * @param keyvaluecoll
	 * @return
	 */
	public static Map buildFromKeyValue(Collection<KeyValue> keyvaluecoll ){
		return buildFromKeyValue(keyvaluecoll.toArray(new KeyValue[]{}));
	}
	
	/**
	 * 通过keyvalue建立map
	 * @param keyValues
	 * @return
	 */
	public static Map buildFromKeyValue(KeyValue ...keyValues ){
		Map map = new HashMap();
		for (KeyValue keyValue : keyValues) {
			map.put(keyValue.getKey(), keyValue.getValue());
		}
		return map;
	}
	
	/**
	 * 分解为多个keyvalue
	 * @param map
	 * @return
	 */
	public List<KeyValue> split2list(Map map){
		List<KeyValue> list = new ArrayList<KeyValue>();
		if(map!=null)
		{
			for (Iterator iterator = map.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry en = (Map.Entry) iterator.next();
				list.add(new DefaultKeyValue(en));
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @author huangjiej_2
	 * 2014年8月29日 下午2:44:10
	 * @param <V>
	 */
	public static interface InitMapEntry<V> {
		public V initWhenAbsent();
	};
	
	/**
	 * 获取map中的某一个key值，如果不存在，则进行创建
	 * @param map
	 * @param key
	 * @param initme
	 * @return
	 */
	public static <K,V> V get(Map<K,V> map,K key, InitMapEntry<V> initme){
		if(map.containsKey(key)){
			return map.get(key);
		}
		else{
			V en= initme.initWhenAbsent();
			map.put(key, en);
			return en;
		}
	}
	

}

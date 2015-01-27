/**
 * 
 */
package com.hummingbird.commonbiz.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hummingbird.common.util.SpringBeanUtil;
import com.hummingbird.commonbiz.entity.LimitSetting;
import com.hummingbird.commonbiz.mapper.LimitSettingMapper;
import com.hummingbird.commonbiz.vo.Quotable;

/**
 * @author huangjiej_2
 * 2014年11月2日 下午4:50:25
 */
public class LimitSettingUtil {

	
	public static Map<String,LimitSetting> limitmapbyapp = new HashMap();
	public static Map<String,LimitSetting> limitmapbyseller = new HashMap();
	
	
	/**
	 * 获取限额
	 * @param appId
	 * @return
	 */
	public static LimitSetting getLimitSettingByAppId(String appId) {
		if(limitmapbyapp.containsKey(appId)){
			return limitmapbyapp.get(appId);
		}
		LimitSettingMapper limitSettingMapper = SpringBeanUtil.getInstance().getBean(LimitSettingMapper.class);
		LimitSetting limitSetting = limitSettingMapper.selectByApp(appId);
		if(limitSettingMapper!=null)
		{
			limitmapbyapp.put(appId, limitSetting);
		}
		return limitSetting;
	}
	
	/**
	 * 重置
	 */
	public static void reset(){
		limitmapbyapp.clear();
		limitmapbyseller.clear();
	}

	public static LimitSetting getLimitSetting(Quotable quotable) {
		if(StringUtils.isNotBlank(quotable.getAppId())){
			return getLimitSettingByAppId(quotable.getAppId());
		}
		else if(StringUtils.isNotBlank(quotable.getSellerId())){
			return getLimitSetting(quotable.getSellerId());
		}
		
		return null;
	}

	public static LimitSetting getLimitSetting(String sellerId) {
		if(limitmapbyseller.containsKey(sellerId)){
			return limitmapbyseller.get(sellerId);
		}
		LimitSettingMapper limitSettingMapper = SpringBeanUtil.getInstance().getBean(LimitSettingMapper.class);
		LimitSetting limitSetting = limitSettingMapper.selectByPrimaryKey(sellerId);
		limitmapbyseller.put(sellerId, limitSetting);
		return limitSetting;
		
	}

}

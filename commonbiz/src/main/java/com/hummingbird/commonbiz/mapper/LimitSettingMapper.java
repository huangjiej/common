package com.hummingbird.commonbiz.mapper;

import com.hummingbird.commonbiz.entity.LimitSetting;

public interface LimitSettingMapper {
    int deleteByPrimaryKey(String sellerid);

    int insert(LimitSetting record);

    int insertSelective(LimitSetting record);

    LimitSetting selectByPrimaryKey(String sellerid);

    int updateByPrimaryKeySelective(LimitSetting record);

    int updateByPrimaryKey(LimitSetting record);

	/**
	 * 根据APPID查询限额
	 * @param appId
	 */
    LimitSetting selectByApp(String appId);
    
    
}
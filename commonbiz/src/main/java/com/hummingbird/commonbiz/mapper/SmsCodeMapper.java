package com.hummingbird.commonbiz.mapper;

import com.hummingbird.commonbiz.entity.SmsCode;

public interface SmsCodeMapper {
    int insert(SmsCode record);

    int insertSelective(SmsCode record);

	/**
	 * 获取短信消息码
	 * @param appId
	 * @param mobileNum
	 * @return
	 */
	SmsCode getAuthCode(SmsCode record);
	
	/**
	 * 删除短消息验证码
	 * @param record
	 * @return
	 */
	int deleteAuthCode(SmsCode record);
}
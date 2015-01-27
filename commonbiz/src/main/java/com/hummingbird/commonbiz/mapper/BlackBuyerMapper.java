package com.hummingbird.commonbiz.mapper;

import com.hummingbird.commonbiz.entity.BlackBuyer;

/**
 * 黑名单
 * @author huangjiej_2
 * 2014年11月13日 上午12:42:13
 */
public interface BlackBuyerMapper {
	BlackBuyer getBlackBuyer(String buyerId);
}

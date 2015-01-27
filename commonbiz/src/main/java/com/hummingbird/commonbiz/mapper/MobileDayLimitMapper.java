package com.hummingbird.commonbiz.mapper;

import com.hummingbird.commonbiz.entity.MobileDayLimit;
import com.hummingbird.commonbiz.entity.MobileMonthLimit;

public interface MobileDayLimitMapper {
    int deleteByPrimaryKey(Integer idtMobileDaylimit);

    int insert(MobileDayLimit record);

    int insertSelective(MobileDayLimit record);

    MobileDayLimit selectByPrimaryKey(Integer idtMobileDaylimit);

    int updateByPrimaryKeySelective(MobileDayLimit record);

    int updateByPrimaryKey(MobileDayLimit record);
    
    /**
     * 添加限额，在判断通过后执行
     * @param record
     * @return
     */
    int addQuota(MobileDayLimit record);
    
    /**
     * 获取限额
     * @param record
     * @return
     */
    MobileDayLimit selectByQuota(MobileDayLimit record);

	/**
	 * 还原（减少）一个限额
	 * @param dayLimit 
	 * @return
	 */
	int minuslimit(MobileDayLimit dayLimit);
}
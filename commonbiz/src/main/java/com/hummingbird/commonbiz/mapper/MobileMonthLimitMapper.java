package com.hummingbird.commonbiz.mapper;

import com.hummingbird.commonbiz.entity.MobileMonthLimit;

public interface MobileMonthLimitMapper {
    int deleteByPrimaryKey(Integer idtMobileMonthlimit);

    int insert(MobileMonthLimit record);

    int insertSelective(MobileMonthLimit record);

    MobileMonthLimit selectByPrimaryKey(Integer idtMobileMonthlimit);

    int updateByPrimaryKeySelective(MobileMonthLimit record);

    int updateByPrimaryKey(MobileMonthLimit record);
    
    /**
     * 添加限额，在判断通过后执行
     * @param record
     * @return
     */
    int addQuota(MobileMonthLimit record);
    
    /**
     * 获取限额
     * @param record
     * @return
     */
    MobileMonthLimit selectByQuota(MobileMonthLimit record);
    
}
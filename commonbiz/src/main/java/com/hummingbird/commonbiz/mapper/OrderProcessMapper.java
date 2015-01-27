package com.hummingbird.commonbiz.mapper;

import com.hummingbird.commonbiz.entity.OrderProcess;

public interface OrderProcessMapper {
    int deleteByPrimaryKey(Integer idtOrderProcess);

    int insert(OrderProcess record);

    int insertSelective(OrderProcess record);

    OrderProcess selectByPrimaryKey(Integer idtOrderProcess);

    int updateByPrimaryKeySelective(OrderProcess record);

    int updateByPrimaryKey(OrderProcess record);
}
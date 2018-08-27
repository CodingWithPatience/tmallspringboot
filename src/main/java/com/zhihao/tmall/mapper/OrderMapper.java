package com.zhihao.tmall.mapper;

import java.util.List;

import com.zhihao.tmall.pojo.Order;
import com.zhihao.tmall.pojo.OrderExample;

public interface OrderMapper {
	long getTotal();
	
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}
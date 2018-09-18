package com.zhihao.tmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhihao.tmall.pojo.Order;
import com.zhihao.tmall.pojo.OrderExample;

public interface OrderMapper {
	long getTotal();

	long getTotalByUser(@Param(value="uid") int uid, @Param(value="status") String status);
	
	long getTotalByUserAll(@Param(value="uid") int uid, @Param(value="status") String status);
	
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}
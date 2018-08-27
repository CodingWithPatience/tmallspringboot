package com.zhihao.tmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhihao.tmall.pojo.Property;
import com.zhihao.tmall.pojo.PropertyExample;

public interface PropertyMapper {
    long getTotal(@Param("cid")int cid);

    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByExample(PropertyExample example);

    Property selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);
}
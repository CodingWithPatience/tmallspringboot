package com.zhihao.tmall.mapper;

import java.util.List;

import com.zhihao.tmall.pojo.User;
import com.zhihao.tmall.pojo.UserExample;

public interface UserMapper {
    long getTotal();
    
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
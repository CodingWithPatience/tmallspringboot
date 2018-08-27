package com.zhihao.tmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhihao.tmall.pojo.Category;
import com.zhihao.tmall.pojo.CategoryExample;

public interface CategoryMapper {
	long getTotal();
	
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

	List<String> search(@Param(value="keyword") String keyword);
}
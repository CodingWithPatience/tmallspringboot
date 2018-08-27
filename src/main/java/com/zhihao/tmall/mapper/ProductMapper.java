package com.zhihao.tmall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhihao.tmall.pojo.Product;
import com.zhihao.tmall.pojo.ProductExample;

public interface ProductMapper {
    long getTotal(@Param("cid") int cid);
    
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    
    List<Product> sortByReview(@Param(value="cid") int cid);

    List<Product> sortBySale(@Param(value="cid") int cid);
}
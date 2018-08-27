package com.zhihao.tmall.service;

import java.util.List;

import com.zhihao.tmall.pojo.Product;
import com.zhihao.tmall.pojo.PropertyValue;

public interface PropertyValueService {
    void init(Product p);
    void update(PropertyValue pv);
    PropertyValue get(int ptid, int pid);
    List<PropertyValue> list(int pid);
}

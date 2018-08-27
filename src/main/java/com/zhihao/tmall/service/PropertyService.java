package com.zhihao.tmall.service;

import java.util.List;

import com.zhihao.tmall.pojo.Property;
import com.zhihao.tmall.util.Page;

public interface PropertyService {
    long getTotal(int cid);
	void add(Property property);
	void delete(int id);
	void update(Property property);
	Property get(int id);
	List<Property> list(int cid);
	List<Property> list(int cid, Page page);
}

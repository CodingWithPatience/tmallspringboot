package com.zhihao.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhihao.tmall.mapper.PropertyMapper;
import com.zhihao.tmall.pojo.Property;
import com.zhihao.tmall.pojo.PropertyExample;
import com.zhihao.tmall.service.PropertyService;
import com.zhihao.tmall.util.Page;

@Service
public class PropertyServiceImpl implements PropertyService {
	
	@Autowired
	PropertyMapper propertyMapper;

	@Override
	public long getTotal(int cid) {
		return propertyMapper.getTotal(cid);
	}

	@Override
	public void add(Property property) {
		propertyMapper.insert(property);
	}

	@Override
	public void delete(int id) {
		propertyMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Property property) {
		propertyMapper.updateByPrimaryKey(property);
	}

	@Override
	public Property get(int id) {
		return propertyMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Property> list(int cid) {
		PropertyExample example = new PropertyExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		return propertyMapper.selectByExample(example);
	}

	@Override
	public List<Property> list(int cid, Page page) {
		PropertyExample example = new PropertyExample();
		example.createCriteria().andCidEqualTo(cid);
		example.setOrderByClause("id desc");
		example.setOffset(page.getStart());
		example.setLimit(page.getCount());
		return propertyMapper.selectByExample(example);
	}
}

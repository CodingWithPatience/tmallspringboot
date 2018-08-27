package com.zhihao.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhihao.tmall.mapper.CategoryMapper;
import com.zhihao.tmall.pojo.Category;
import com.zhihao.tmall.pojo.CategoryExample;
import com.zhihao.tmall.service.CategoryService;
import com.zhihao.tmall.util.Page;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryMapper categoryMapper;
	
	// 获取所有分类的总数
	@Override
	public long getTotal() {
		return categoryMapper.getTotal();
	}
	
	// 查询所有分类
    @Override
	public List<Category> list() {
    	CategoryExample example = new CategoryExample();
		example.setOrderByClause("id desc");
		return categoryMapper.selectByExample(example);
	}
    
    // 以分页的方式查询分类
    @Override
	public List<Category> list(Page page) {
    	CategoryExample example = new CategoryExample();
		example.setOrderByClause("id desc");
		example.setOffset(page.getStart());
		example.setLimit(page.getCount());
		return categoryMapper.selectByExample(example);
	}

    // 添加分类
	@Override
	public void add(Category category) {
		categoryMapper.insert(category);
	}

	// 删除分类
	@Override
	public void delete(int id) {
		categoryMapper.deleteByPrimaryKey(id);
	}

	// 通过id获取分类
	@Override
	public Category get(int id) {
	  return categoryMapper.selectByPrimaryKey(id);
	}

	// 更新分类
	@Override
	public void update(Category category) {
	  categoryMapper.updateByPrimaryKeySelective(category);
	}

	// 查找分类的名称
	@Override
	public List<String> search(String keyword) {
		return categoryMapper.search(keyword);
	}
}

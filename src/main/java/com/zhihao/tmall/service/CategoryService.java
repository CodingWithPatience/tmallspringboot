package com.zhihao.tmall.service;

import java.util.List;

import com.zhihao.tmall.pojo.Category;
import com.zhihao.tmall.util.Page;

public interface CategoryService {
	
	long getTotal();

	List<String> search(String keyword);
	
	public List<Category> list();
	
	public List<Category> list(Page page);

	public void add(Category category);
	
	public void delete(int id); 
	
	public Category get(int id); 
	
	public void update(Category category); 
}
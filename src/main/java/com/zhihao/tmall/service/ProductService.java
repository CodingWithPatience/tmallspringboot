package com.zhihao.tmall.service;

import java.util.List;
import java.util.Set;

import com.zhihao.tmall.pojo.Category;
import com.zhihao.tmall.pojo.Product;
import com.zhihao.tmall.util.Page;

public interface ProductService {
	
	// 产品排序方式
	String ALL = "id desc";
	String DATE = "createDate desc"; 
	String PRICE = "promotePrice";
	String REVIEW = "review";
	String SALE = "saleCount";
	
    long getTotal(int cid);
    void add(Product p);
    void delete(int id);
    void update(Product p);
    Product get(Integer id);
    List<Product> list(int cid);
    List<Product> list(int cid, String sortCondition);
	List<Product> list(int cid, Page page);
	List<Product> sortedlist(int cid, String sortCondition);
    void setFirstProductImage(Product p);
    void setFirstProductImage(List<Product> products);
    void fill(List<Category> cs);
    void fill(Category c);
    void fillByRow(List<Category> cs);
    void setSaleAndReviewNumber(Product p);
    void setSaleAndReviewNumber(List<Product> ps);	
    List<Product> search(String keyword);
    Set<String> getCategoryName(List<Product> products);
	void fill(Category c, String sort);
}

package com.zhihao.tmall.comparator;

import java.util.Comparator;

import com.zhihao.tmall.pojo.Product;

public class ProductAllComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		return p2.getReviewCount()*p2.getSaleCount()-p1.getReviewCount()*p1.getSaleCount();
	}

}

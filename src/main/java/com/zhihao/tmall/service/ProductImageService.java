package com.zhihao.tmall.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.zhihao.tmall.pojo.ProductImage;
import com.zhihao.tmall.util.UploadedImageFile;

public interface ProductImageService {

	String type_single = "type_single";
	String type_detail = "type_detail";

	void add(ProductImage pi, HttpSession session, UploadedImageFile uploadedImageFile);
	void delete(int id, HttpSession session);
	void update(ProductImage pi);
	ProductImage get(int id);
	List<ProductImage> list(int pid, String type);
}

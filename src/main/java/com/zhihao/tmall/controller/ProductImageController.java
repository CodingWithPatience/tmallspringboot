package com.zhihao.tmall.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhihao.tmall.pojo.Product;
import com.zhihao.tmall.pojo.ProductImage;
import com.zhihao.tmall.service.ProductImageService;
import com.zhihao.tmall.service.ProductService;
import com.zhihao.tmall.util.UploadedImageFile;

/**
 * 后台管理产品图片的控制器
 * @author zzh
 * 2018年8月26日
 */
@Controller
@RequestMapping("admin/productImage")
public class ProductImageController {

	@Autowired
	ProductService productService;

	@Autowired
	ProductImageService productImageService;

	// 添加产品图片
	@PostMapping("add")
	public String add(ProductImage  pi, HttpSession session, UploadedImageFile uploadedImageFile) {
		productImageService.add(pi, session, uploadedImageFile);
		
		return "redirect:/admin/productImage/list/"+pi.getPid();
	}

	// 删除图片
	@GetMapping("delete/{id}")
	public String delete(@PathVariable int id, int pid, HttpSession session) {
		productImageService.delete(id, session);
		
		return "redirect:/admin/productImage/list/"+pid;
	}

	// 显示图片
	@GetMapping("list/{pid}")
	public String list(@PathVariable int pid, Model model) {
		Product p = productService.get(pid);
		List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
		List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);

		model.addAttribute("p", p);
		model.addAttribute("pisSingle", pisSingle);
		model.addAttribute("pisDetail", pisDetail);

		return "admin/listProductImage";
	}
}

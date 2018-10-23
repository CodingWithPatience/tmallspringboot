package com.zhihao.tmall.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhihao.tmall.constant.PageParam;
import com.zhihao.tmall.pojo.Category;
import com.zhihao.tmall.pojo.Product;
import com.zhihao.tmall.service.CategoryService;
import com.zhihao.tmall.service.ProductService;
import com.zhihao.tmall.util.Page;

/**
 * 后台管理产品的控制器
 * @author zzh
 * 2018年8月26日
 */
@Controller
@RequestMapping("admin/category")
public class ProductController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	// 添加产品
	@PostMapping("/{cid}/product")
	public String add(Model model, Product p) {
		p.setCreateDate(new Date());
		productService.add(p);
		return "redirect:/admin/category/"+p.getCid()+"/product/";
	}

	// 通过id删除产品
	@DeleteMapping("/{cid}/product/{id}")
	public String delete(@PathVariable int id) {
		Product p = productService.get(id);
		productService.delete(id);
		return "redirect:/admin/category/"+p.getCid()+"/product/";
	}

	// 编辑产品
	@GetMapping("/{cid}/product/{id}")
	public String edit(Model model, @PathVariable(value="id") int id) {
		Product p = productService.get(id);
		Category c = categoryService.get(p.getCid());
		p.setCategory(c);
		model.addAttribute("p", p);
		return "admin/editProduct";
	}

	// 更新产品
	@PutMapping("/{cid}/product/{id}")
	public String update(Product p) {
		productService.update(p);
		return "redirect:/admin/category/"+p.getCid()+"/product/";
	}

	// 通过分页的方式查询产品，
	@GetMapping(value= {"/{cid}/product", "/{cid}/product"})
	public String list(@PathVariable Integer cid,
			@RequestParam(required=false) Integer page, Model model, Page productPage) {
		productPage.setCurrentPage(page);
		productPage.setParam(PageParam.PRODUCT);
		productPage.setId(String.valueOf(cid));
		Category c = categoryService.get(cid);

		List<Product> ps = productService.list(cid, productPage);
		long total = productService.getTotal(cid);
		productPage.setTotal(total);

		model.addAttribute("ps", ps);
		model.addAttribute("c", c);
		model.addAttribute("page", productPage);
		return "admin/listProduct";
	}
}

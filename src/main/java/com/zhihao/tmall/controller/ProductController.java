package com.zhihao.tmall.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("admin/product")
public class ProductController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	// 添加产品
	@PostMapping("add")
	public String add(Model model, Product p) {
		p.setCreateDate(new Date());
		productService.add(p);
		return "redirect:/admin/product/list/"+p.getCid();
	}

	// 通过id删除产品
	@GetMapping("delete/{id}")
	public String delete(@PathVariable int id) {
		Product p = productService.get(id);
		productService.delete(id);
		return "redirect:/admin/product/list/"+p.getCid();
	}

	// 编辑产品
	@GetMapping("edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		Product p = productService.get(id);
		Category c = categoryService.get(p.getCid());
		p.setCategory(c);
		model.addAttribute("p", p);
		return "admin/editProduct";
	}

	// 更新产品
	@PostMapping("update")
	public String update(Product p) {
		productService.update(p);
		return "redirect:/admin/product/list/"+p.getCid();
	}

	// 通过分页的方式查询产品，
	@GetMapping(value= {"list/{cid}", "list/{cid}/{pageNum}"})
	public String list(@PathVariable Integer cid,
			@PathVariable(required=false) String pageNum, Model model, Page page) {
		page.setCurrentPage(pageNum);
		page.setParam("product");
		page.setId(String.valueOf(cid));
		Category c = categoryService.get(cid);

		List<Product> ps = productService.list(cid, page);
		long total = productService.getTotal(cid);
		page.setTotal(total);

		model.addAttribute("ps", ps);
		model.addAttribute("c", c);
		model.addAttribute("page", page);
		return "admin/listProduct";
	}
}

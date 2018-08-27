package com.zhihao.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhihao.tmall.pojo.Category;
import com.zhihao.tmall.pojo.Property;
import com.zhihao.tmall.service.CategoryService;
import com.zhihao.tmall.service.PropertyService;
import com.zhihao.tmall.util.Page;

/**
 * 后台管理分类属性的控制器
 * @author zzh
 * 2018年8月26日
 */
@Controller
@RequestMapping("admin/property")
public class PropertyController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	PropertyService propertyService;

	// 添加分类属性
	@RequestMapping("add")
	public String add(Model model, Property p) {
		propertyService.add(p);
		return "redirect:/admin/property/list/"+p.getCid();
	}

	// 删除分类属性
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable int id) {
		Property p = propertyService.get(id);
		propertyService.delete(id);
		return "redirect:/admin/property/list/"+p.getCid();
	}

	// 编辑分类属性
	@RequestMapping("edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		Property p = propertyService.get(id);
		Category c = categoryService.get(p.getCid());
		p.setCategory(c);
		model.addAttribute("p", p);
		return "admin/editProperty";
	}

	// 更新分类属性
	@RequestMapping("update")
	public String update(Property p) {
		propertyService.update(p);
		return "redirect:/admin/property/list/"+p.getCid();
	}

	// 显示分类属性
	@RequestMapping(value= {"list/{cid}", "list/{cid}/{pageNum}"})
	public String list(@PathVariable(value="cid") int cid, Model model, Page page,
			@PathVariable(required=false, value="pageNum") String pageNum) {
		page.setCurrentPage(pageNum);								
		page.setParam("property");
		page.setId(String.valueOf(cid));
		
		Category c = categoryService.get(cid);
		List<Property> ps = propertyService.list(cid, page);
		long total = propertyService.getTotal(cid);
		page.setTotal(total);

		model.addAttribute("ps", ps);
		model.addAttribute("c", c);
		model.addAttribute("page", page);
		return "admin/listProperty";
	}
}


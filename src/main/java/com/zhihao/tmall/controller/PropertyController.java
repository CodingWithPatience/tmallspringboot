package com.zhihao.tmall.controller;

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
@RequestMapping("admin/category")
public class PropertyController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	PropertyService propertyService;

	// 添加分类属性
	@PostMapping("/{cid}/property")
	public String add(Model model, Property p, @PathVariable int cid)  {
		propertyService.add(p);
		return "redirect:/admin/category/"+cid+"/property";
	}

	// 删除分类属性
	@DeleteMapping("/{cid}/property/{id}")
	public String delete(@PathVariable(value="id") int id, @PathVariable(value="cid") int cid) {
		propertyService.delete(id);
		return "redirect:/admin/category/"+cid+"/property";
	}

	// 编辑分类属性
	@GetMapping("/{cid}/property/{id}")
	public String edit(Model model, @PathVariable int id, @PathVariable(value="cid") int cid) {
		Property p = propertyService.get(id);
		Category c = categoryService.get(cid);
		p.setCategory(c);
		model.addAttribute("p", p);
		return "admin/editProperty";
	}

	// 更新分类属性
	@PutMapping("/{cid}/property/{id}")
	public String update(Property p, @PathVariable(value="cid") int cid) {
		propertyService.update(p);
		return "redirect:/admin/category/"+cid+"/property";
	}

	// 显示分类属性
	@GetMapping(value= {"/{cid}/property"})
	public String list(@PathVariable(value="cid") int cid, Model model, Page propertyPage,
			@RequestParam(required=false) Integer page) {
		propertyPage.setCurrentPage(page);								
		propertyPage.setParam(PageParam.PROPERTY);
		
		Category c = categoryService.get(cid);
		List<Property> ps = propertyService.list(cid, propertyPage);
		long total = propertyService.getTotal(cid);
		propertyPage.setTotal(total);

		model.addAttribute("ps", ps);
		model.addAttribute("c", c);
		model.addAttribute("page", propertyPage);
		return "admin/listProperty";
	}
}


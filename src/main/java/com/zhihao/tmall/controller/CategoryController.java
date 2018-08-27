package com.zhihao.tmall.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.zhihao.tmall.pojo.Category;
import com.zhihao.tmall.service.CategoryService;
import com.zhihao.tmall.util.ImageUtil;
import com.zhihao.tmall.util.Page;
import com.zhihao.tmall.util.UploadedImageFile;

@Controller
@RequestMapping("admin/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	// 显示分类，根据pageNum进行分页
	@GetMapping(value= {"list", "list/{pageNum}"})
	public String list(Model model, 
			@PathVariable(required=false) String pageNum, Page page) {
		// 设置pageNum为page当前页
		page.setCurrentPage(pageNum);
		// 设置当前page为category类型，用于显示正确的href属性，当点击选择某一页时
		// 能正确地将请求发送到CategoryController
		page.setParam("category");
		
		List<Category> cs = categoryService.list(page);
		long total = categoryService.getTotal();
		page.setTotal(total);
		model.addAttribute("cs", cs);
		model.addAttribute("page", page);
		return "admin/listCategory";
	}
	
	// 添加分类
	@PostMapping("add")
	public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile)
				throws IOException {
	
		categoryService.add(c);
		File imageFolder= new File(session.getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder,c.getId()+".jpg");
		if(!file.getParentFile().exists())
		file.getParentFile().mkdirs();
		
		// 处理上传图片
		uploadedImageFile.getImage().transferTo(file);
		BufferedImage img = ImageUtil.change2jpg(file);
		ImageIO.write(img, "jpg", file);
		return "redirect:/admin/category/list";
	}
	
	// 根据id删除分类
	@GetMapping("delete/{id}")
	public String delete(@PathVariable int id, HttpSession session) throws IOException {
		categoryService.delete(id);

		// 删除分类图片
		File imageFolder= new File(session.getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder,id+".jpg");
		file.delete();
		return "redirect:/admin/category/list";
	}
	
	// 编辑分类
	@GetMapping("edit/{id}")
	public String edit(@PathVariable int id, Model model) throws IOException {
		Category c= categoryService.get(id);
		model.addAttribute("c", c);
		return "admin/editCategory";
	}

	// 更新分类
	@PostMapping("update")
	public String update(Category c, HttpSession session,
			UploadedImageFile uploadedImageFile) throws IOException {
		categoryService.update(c);
		MultipartFile image = uploadedImageFile.getImage();
		// 更新图片
		if(null!=image && !image.isEmpty()){
			File imageFolder= new File(session.getServletContext().getRealPath("img/category"));
			File file = new File(imageFolder,c.getId()+".jpg");
			image.transferTo(file);
			BufferedImage img = ImageUtil.change2jpg(file);
			ImageIO.write(img, "jpg", file);
		}
		return "redirect:/admin/category/list";
	}
}

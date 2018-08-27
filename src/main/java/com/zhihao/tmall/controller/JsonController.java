/**
 * 
 */
package com.zhihao.tmall.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.zhihao.tmall.pojo.Product;
import com.zhihao.tmall.service.CategoryService;
import com.zhihao.tmall.service.ProductService;

/**
 * 用于返回josn数据而非视图名称的控制器
 * 实现动态加载数据
 * @author zzh
 * 2018年8月18日
 */
@Controller
@RequestMapping("jsondata")
public class JsonController {

	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	
	/**
	 * 在home页中通过下拉滚动条，动态加载数据
	 * 根据参数cid每次返回一类产品
	 * @param cid 产品所属分类id
	 * @return products 该分类下的产品集合
	 * 2018年8月26日
	 */
	@GetMapping("product/{cid}")
	@ResponseBody
	public List<Product> list(@PathVariable int cid) {
		List<Product> products = productService.list(cid);
		return products;
	}
	
	/**
     * 智能搜索提示
     * Ajax请求的方式实现实时显示后台返回的数据
     * 返回的是产品所属分类的名称非产品名称
     * 因为产品名称较长且为一连串描述性语句，而非名词性短语
     * @param keyword 搜索关键字
     * @return cNames 产品分类名字集合
     * 2018年8月21日
     */
    @GetMapping("searchtip")
    @ResponseBody
    public Set<String> searchTip(@RequestParam String keyword) {
        keyword = HtmlUtils.htmlEscape(keyword);
        // 通过关键字搜索产品，通过产品获得所属分类名称，返回分类名称集合
    	List<Product> ps = productService.search(keyword);
    	Set<String> cNames = productService.getCategoryName(ps);
    	// 根据关键字直接搜索产品所属分类，返回产品分类名称集合
//        List<String> cNames = new ArrayList<>();
//        cNames = categoryService.search(keyword);
    	return cNames;
    }
}

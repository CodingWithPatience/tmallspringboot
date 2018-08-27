package com.zhihao.tmall.controller;

import com.zhihao.tmall.pojo.Product;
import com.zhihao.tmall.pojo.PropertyValue;
import com.zhihao.tmall.service.ProductService;
import com.zhihao.tmall.service.PropertyValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 后台管理产品属性值的控制器
 * @author zzh
 * 2018年8月26日
 */
@Controller
@RequestMapping("admin/propertyValue")
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;

    // 编辑产品属性值
    @RequestMapping("edit/{pid}")
    public String edit(Model model, @PathVariable int pid) {
        Product p = productService.get(pid);
        propertyValueService.init(p);
        List<PropertyValue> pvs = propertyValueService.list(p.getId());

        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "admin/editPropertyValue";
    }
    
    // 更新产品属性值
    @RequestMapping("update")
    @ResponseBody
    public String update(PropertyValue pv) {
        propertyValueService.update(pv);
        return "success";
    }
}

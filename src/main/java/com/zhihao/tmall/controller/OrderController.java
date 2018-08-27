package com.zhihao.tmall.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhihao.tmall.pojo.Order;
import com.zhihao.tmall.service.OrderItemService;
import com.zhihao.tmall.service.OrderService;
import com.zhihao.tmall.util.Page;

/**
 * 后台管理订单的控制器
 * @author zzh
 * 2018年8月26日
 */
@Controller
@RequestMapping("admin/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    // 后台显示订单，根据pageNum分页
    @GetMapping(value= {"list", "list/{pageNum}"})
    public String list(Model model, @PathVariable(required=false) String pageNum, Page page){
    	page.setCurrentPage(pageNum);  // 当pageNum为null时，该方法不做任何操作
    	page.setParam("order");

        List<Order> os = orderService.list(page);
        long total = orderService.getTotal();
        page.setTotal(total);

        model.addAttribute("os", os);
        model.addAttribute("page", page);
        return "admin/listOrder";
    }

    // 设置订单为已发货状态
    @GetMapping("delivery/{id}")
    public String delivery(@PathVariable int id, Order o) throws IOException {
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderService.update(o);
        return "redirect:/admin/order/list";
    }
}

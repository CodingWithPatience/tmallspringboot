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
import org.springframework.web.bind.annotation.RequestParam;

import com.zhihao.tmall.constant.PageParam;
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
    @GetMapping(value= {""})
    public String list(Model model, @RequestParam(required=false) Integer page, Page OrderPage){
    	OrderPage.setCurrentPage(page);  // 当pageNum为null时，该方法不做任何操作
    	OrderPage.setParam(PageParam.ORDER);

        List<Order> os = orderService.list(OrderPage);
        long total = orderService.getTotal();
        OrderPage.setTotal(total);

        model.addAttribute("os", os);
        model.addAttribute("page", OrderPage);
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

package com.zhihao.tmall.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhihao.tmall.mapper.OrderMapper;
import com.zhihao.tmall.pojo.Order;
import com.zhihao.tmall.pojo.OrderExample;
import com.zhihao.tmall.pojo.OrderExample.Criteria;
import com.zhihao.tmall.pojo.OrderItem;
import com.zhihao.tmall.pojo.Product;
import com.zhihao.tmall.pojo.User;
import com.zhihao.tmall.service.OrderItemService;
import com.zhihao.tmall.service.OrderService;
import com.zhihao.tmall.service.ProductService;
import com.zhihao.tmall.service.UserService;
import com.zhihao.tmall.util.Page;

@Service
public class OrderServiceImpl implements OrderService {
	
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
	ProductService productService;

    @Override
	public long getTotal() {
		return orderMapper.getTotal();
	}
    
    @Override
	public long getTotalByUser(int uid, String status) {
    	if(OrderService.delete.equals(status))
    		return orderMapper.getTotalByUserAll(uid, status);
		return orderMapper.getTotalByUser(uid, status);
	}
    
    @Override
    public void add(Order o) {
        orderMapper.insert(o);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order c) {
        orderMapper.updateByPrimaryKeySelective(c);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> result = orderMapper.selectByExample(example);
        orderItemService.fill(result);
        setUser(result);
        return result;
    }

    @Override
	public List<Order> list(Page page) {
    	OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        example.setOffset(page.getStart());
        example.setLimit(page.getCount());
        List<Order> result = orderMapper.selectByExample(example);
        orderItemService.fill(result);
        setUser(result);
        return result;
	}
    
    public void setUser(List<Order> os){
        for (Order o : os)
            setUser(o);
    }
    
    public void setUser(Order o){
        int uid = o.getUid();
        User u = userService.get(uid);
        o.setUser(u);
    }
    
	@Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float add(Order o, List<OrderItem> ois) {
        float total = 0;
        add(o);
 
        for(OrderItem oi: ois) {
            oi.setOid(o.getId());
            orderItemService.update(oi);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
    }

    @Override
    public List<Order> list(int uid, int count, int limit, String status) {
        OrderExample example =new OrderExample();
        Criteria criteria = example.createCriteria().andUidEqualTo(uid);
        if(OrderService.delete.equals(status))
        	criteria.andStatusNotEqualTo(status);
        else
        	criteria.andStatusEqualTo(status);
        
        example.setOrderByClause("id desc");
        example.setOffset(count);
        example.setLimit(limit);
        return orderMapper.selectByExample(example);
    }
    
    @Override
    public Order create(Order order, User user) {
    	String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderService.waitPay);
    	return order;
    }

	@Override
	public Order finish(Order order, List<OrderItem> orderItems) {
		order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        update(order);
        
        for(OrderItem orderItem : orderItems) {
        	Product product = productService.get(orderItem.getPid());
        	product.setStock(product.getStock()-1);
        	productService.update(product);
        }
		return order;
	}
}

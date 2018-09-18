package com.zhihao.tmall.controller;

import com.zhihao.tmall.pojo.Category;
import com.zhihao.tmall.pojo.Order;
import com.zhihao.tmall.pojo.OrderItem;
import com.zhihao.tmall.pojo.Product;
import com.zhihao.tmall.pojo.PropertyValue;
import com.zhihao.tmall.pojo.Review;
import com.zhihao.tmall.pojo.User;
import com.zhihao.tmall.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * 处理前端用户请求的控制器
 * 有@ResponseBody注解的方法返回的是json数据，一般用于异步加载数据
 * 其他方法返回的是String类型的视图名称
 * @author zzh
 * 2018年8月21日
 */
@Controller
@RequestMapping("")
public class ForeController {
	
	// 自动注入service层的实例对象，用于处理各种与数据库操作的请求
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;
    
    // 登陆请求
    @GetMapping("login")
    public String login(HttpSession session, Model model) {
    	if(session.getAttribute("user") != null)
    		return "redirect:/home";
    	return "fore/login";
    }
    
    // 处理登陆提交的数据
    @PostMapping("login")
    public String login(@RequestParam("name") String name,
    		@RequestParam("password") String password, Model model, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);            // 将html标签转义
        User user = userService.get(name,password);
 
        // 若用户不存在，返回登录页并提示错误信息
        if(null==user){
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        session.setAttribute("user", user);       // 将user存储在session中
        
        if(session.getAttribute("servletPath")!=null) {
        	String servletPath = (String) session.getAttribute("servletPath");
        	session.removeAttribute("servletPath");
        	if(servletPath.equals("/"))
        		return "redirect:/home";
			return "redirect:/"+servletPath;                  
        }
        return "redirect:/home";                  // 登陆成功，重定向到home页
    }

    @GetMapping("home")
    public String home(Model model) {
        List<Category> cs = categoryService.list();  // 查找所有产品分类
        model.addAttribute("cs", cs);
        return "fore/home";
    }

    // 处理注册提交的数据
    @PostMapping("register")
    public String register(Model model, User user) {
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);    // 查询用户名是否已经存在
        
        if(exist){
            String errMsg = "用户名已经被使用";
            model.addAttribute("errMsg", errMsg);
            model.addAttribute("user", null);
            return "fore/register";                   // 用户名已存在，返回注册页
        }
        userService.add(user);                        // 注册成功，向数据库中写入用户
        return "redirect:/registerSuccess";
    }
    
    // 注销用户请求
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");              // 清除session中user属性
        return "redirect:/home";
    }
    
    /**
     * 查看产品详情请求
     * @param pid 产品id
     * @param model 存放页面所需的数据
     * @return "fore/product" 视图名称信息，fore文件夹下名为product的视图
     * 2018年8月21日
     */
    @GetMapping("product/{pid}")
    public String product(@PathVariable(value="pid") int pid, Model model) { 
    	Product p = productService.get(pid);        // 通过产品id查找产品
        List<PropertyValue> pvs = propertyValueService.list(p.getId());   // 产品属性值
        List<Review> reviews = reviewService.list(p.getId());   // 产品评论
        model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "fore/product";
    }
    
    // 检查是否已经登陆，返回json数据，非视图名称
    @GetMapping("checkLogin")
    @ResponseBody
    public String checkLogin( HttpSession session) {
        User user = (User)session.getAttribute("user");
        if(null!=user)
            return "success";
        return "fail";
    }
    
    /**
     * 当添加购物车或点击购买链接，若未登陆，弹出模态登陆窗口
     * 对模态登陆请求的处理，返回josn数据，fail或success
     * @param name 用户名
     * @param password 密码
     * @return success 登陆成功； fail 登陆失败
     * 2018年8月21日
     */
    @GetMapping("loginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name,
    		@RequestParam("password") String password, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        password = HtmlUtils.htmlEscape(password);
        User user = userService.get(name,password);

        if(null==user){
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    /**
     * 查看某一分类下的所有产品
     * @param cid 分类id
     * @param sort 排序方式
     * @param model 
     * @return 视图名称
     * 2018年8月21日
     */
    @GetMapping(value= {"category/{cid}", "category/{cid}/{sort}"})
    public String category(@PathVariable(value="cid")int cid,
    		@PathVariable(required=false, value="sort")String sort, Model model) {
        Category c = categoryService.get(cid);
        
        // sort若存在，以sort作为查询条件，返回的产品结果集以sort的方式进行排序
        if(null!=sort){
            switch(sort){
                case "date" :
                	sort = ProductService.DATE;
                    break;
 
                case "price":
                	sort = ProductService.PRICE;
                    break;
 
                case "all":
                	sort = ProductService.ALL;
                    break;
            }
            productService.fill(c, sort);
        }
        else {
			productService.fill(c);
		}
        
        productService.setSaleAndReviewNumber(c.getProducts());
        model.addAttribute("c", c);
        return "fore/category";
    }

    // 搜索请求，keyword搜索关键字
    @RequestMapping("search")
    public String search(String keyword, RedirectAttributes model){
        List<Product> ps = new ArrayList<>();
        ps = productService.search(keyword);           // 基于产品名称的模糊查询
        productService.setSaleAndReviewNumber(ps);     // 设置产品的售卖记录及评论数量
        model.addFlashAttribute("ps", ps);
        model.addFlashAttribute("keyword", keyword);
        return "redirect:/searchResult";
    }
    
    /**
     * 点击购买链接的请求
     * 检查用户是否已经购买过该产品，若已购买过，更改相应的订单项的购买数量记录
     * 使其加上num，若未购买过，则创建该产品新的订单项。
     * @param pid 产品id 
     * @param num 产品购买数量
     * @return 重定向到购买结算请求
     * 2018年8月21日
     */
    @GetMapping("buyone/{pid}/{num}")
    public String buyone(@PathVariable(value="pid") int pid, 
    		@PathVariable(value="num") int num, HttpSession session) {
        Product p = productService.get(pid);
        int oiid = 0;
 
        User user =(User)session.getAttribute("user");
        boolean found = false;
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        // 检查与用户相关联的订单项中是否有该产品，若有，更改购买数量
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId().intValue()==p.getId().intValue()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }
 
        // 若用户没有购买过该产品，创建该产品的订单项并与当前用户关联
        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
            oiid = oi.getId();      
        }
        return "redirect:/buy/"+oiid;
    }

    /**
     * 填写用户信息的页面及计算结算金额的请求
     * 若从购物车点击结算链接，可能包含多种产品，即多个订单项
     * 则oiids为订单项id数组
     * 若从点击购买链接进入到结算请求，则只有一个订单项，oiids为单数
     * @param oiids 订单项id数组
     * @return 视图名称
     * 2018年8月21日
     */
    @GetMapping("buy/{oiids}")
    public String buy(Model model, @PathVariable(value="oiids") String[] oiids, HttpSession session){
        List<OrderItem> ois = new ArrayList<>();
        float total = 0;
 
        for (String strid : oiids) {
            int id = Integer.parseInt(strid);
            OrderItem oi = orderItemService.get(id);
            // 计算结算金额
            total += oi.getProduct().getPromotePrice()*oi.getNumber();
            ois.add(oi);
        }
        session.setAttribute("ois", ois);
        model.addAttribute("total", total);
        return "fore/buy";
    }
    
    /**
     * 加入购物车请求
     * @param pid 产品id
     * @param num 加入购物车的产品数量
     * @return success json格式数据，表示添加到购物车成功
     * 2018年8月21日
     */
    @GetMapping("addCart")
    @ResponseBody
    public String addCart(int pid, int num, Model model, HttpSession session) {
        Product p = productService.get(pid);
        User user = (User)session.getAttribute("user");
        boolean found = false;
 
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        // 检查与用户相关联的订单项中是否有该产品，若有，更改购买数量
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId().intValue()==p.getId().intValue()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }
 
        // 若用户没有购买过该产品，创建该产品的订单项并与当前用户关联
        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
        }
        return "success";
    }
    
    // 查看购物车请求
    @GetMapping("cart")
    public String cart( Model model,HttpSession session) {
        User user = (User)session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", ois);
        return "fore/cart";
    }
    
    /**
     * 在购物车页面中改变产品购买数量的但不刷新页面的Ajax请求
     * 在前端页面中使用jQuery的$.post()方法提交请求相关的数据
     * @param pid 产品id
     * @param number 更改后的产品购买数量
     * @return success 返回表示更改成功的信息
     * 2018年8月21日
     */
    @PostMapping("changeOrderItem")
    @ResponseBody
    public String changeOrderItem( Model model,HttpSession session, int pid, int number) {
    	User user = (User) session.getAttribute("user");
 
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId().intValue()==pid){
                oi.setNumber(number);
                orderItemService.update(oi);
                break;
            }
        }
        return "success";
    }
    
    // 在购物车中删除订单项的请求，通过订单id删除
    @GetMapping("deleteOrderItem/{oiid}")
    @ResponseBody
    public String deleteOrderItem( Model model, 
    		HttpSession session, @PathVariable(value="oiid") int oiid){
        orderItemService.delete(oiid);
        return "success";
    }
    
    /**
     * 提交订单的请求的处理
     * 生成订单，重定向到付款页面
     * @param order spring MVC根据用户填写的数据生成的Order对象
     * @return 重定向到付款页
     * 2018年8月21日
     */
    @PostMapping("createOrder")
    public String createOrder(RedirectAttributes model, Order order, HttpSession session){
        User user = (User)session.getAttribute("user");
		
		@SuppressWarnings("unchecked")
		List<OrderItem> ois = (List<OrderItem>)session.getAttribute("ois");
		orderService.create(order, user);
		float total = orderService.add(order,ois);      // 计算付款金额，total包含在路径参数中
		
		model.addAttribute("oid", order.getId());
		model.addAttribute("total", total);
		return "redirect:alipay/{oid}/{total}";
    }
    
	// 返回支付页面，oid订单id，total付款金额
    @GetMapping(value= {"alipay/{oid}/{total}"})
    public String alipay(@PathVariable(value="oid")int oid,
    		@PathVariable(value="total")float total, Model model) {
    	model.addAttribute("oid", oid);
    	model.addAttribute("total", total);
        return "fore/alipay";
    }
    
    // 付款成功后，返回付款成功的页面
    @RequestMapping("payed/{oid}/{total}")
    public String payed(@PathVariable(value="oid")int oid, 
    		@PathVariable(value="total")float total, Model model, HttpSession session) {
        Order order = orderService.get(oid);
		List<OrderItem> orderItems = orderItemService.listByOrder(oid);
        orderService.finish(order, orderItems);
        model.addAttribute("o", order);
        return "fore/payed";
    }
   
    // 我的订单页面
    @GetMapping("bought")
    public String bought(Model model, HttpSession session) {
//        User user = (User)  session.getAttribute("user");
//        long total = orderService.getTotalByUser(user.getId(), OrderService.delete);
        // 过滤处于删除状态的订单
//        List<Order> os = orderService.list(user.getId(), 24, 1, OrderService.delete);   
//        orderItemService.fill(os);
//        model.addAttribute("os", os);
//        model.addAttribute("total", total);
        return "fore/bought";
    }
    
    // 点击确收货请求
    @GetMapping("confirmPay/{oid}")
    public String confirmPay( Model model, @PathVariable int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        model.addAttribute("o", o);
        return "fore/confirmPay";
    }
    
    // 已经确认收货后的最终确认付款的请求
    @GetMapping("orderConfirmed/{oid}")
    public String orderConfirmed( Model model,@PathVariable int oid) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.waitReview);   // 设置订单为等待评价状态
        o.setConfirmDate(new Date());
        orderService.update(o);
        return "fore/orderConfirmed";
    }
    
    // 删除订单请求
    @PostMapping("deleteOrder")
    @ResponseBody
    public String deleteOrder( Model model, int oid){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.delete);   // 设置订单未删除状态
        orderService.update(o);
        return "success";
    }
    
    // 点击评价的请求，shownonly表示仅显示评价内容
    @GetMapping(value={"review/{oid}", "review/{oid}/{showonly}"})
    public String review(Model model, @PathVariable(value="oid") int oid, 
    		@PathVariable(required=false, value="showonly") String showonly) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        Product p = o.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(p.getId());
        productService.setSaleAndReviewNumber(p);
        model.addAttribute("showonly",showonly);
        model.addAttribute("p", p);
        model.addAttribute("o", o);
        model.addAttribute("reviews", reviews);
        return "fore/review";
    }
    
    /**
     * 提交评价内容的请求
     * 将评价内容写入数据库，并与相应的产品、用户相关联，设置提交评价的时间
     * 更新订单，将订单设置未完成状态
     * @param pid 产品id
     * @param content 提交的评价内容
     * @return 重定向到仅显示评价内容的页面
     * 2018年8月21日
     */
    @PostMapping("doreview")
    public String doreview(Model model, HttpSession session,
    		@RequestParam("oid") int oid, @RequestParam("pid") int pid, String content) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.finish);
        orderService.update(o);
     
        content = HtmlUtils.htmlEscape(content);
     
        User user = (User) session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setCreateDate(new Date());
        review.setUid(user.getId());
        reviewService.add(review);
     
        return "redirect:review/"+oid+"/showonly";
    }
}


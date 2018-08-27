package com.zhihao.tmall.service;
 
import java.util.List;

import com.zhihao.tmall.pojo.User;
import com.zhihao.tmall.util.Page;

public interface UserService {
    long getTotal();
    void add(User c);
    void delete(int id);
    void update(User c);
    User get(int id);
    List<User> list();
    List<User> list(Page pgae);
    boolean isExist(String name);
    User get(String name, String password);
}

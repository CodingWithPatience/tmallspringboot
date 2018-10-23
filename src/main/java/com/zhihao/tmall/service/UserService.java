package com.zhihao.tmall.service;
 
import java.util.List;

import com.zhihao.tmall.pojo.User;
import com.zhihao.tmall.util.Page;

public interface UserService {
	int INACTIVE = 0;
	int ACTIVE = 1;
	
    long getTotal();
    void add(User c);
    void delete(int id);
    void update(User c);
    User get(int id);
    List<User> list();
    List<User> list(Page pgae);
    boolean isUsernameExist(String name);
    boolean isMailExist(String account);
    User get(String name, String password);
	User getByCode(String code);
}

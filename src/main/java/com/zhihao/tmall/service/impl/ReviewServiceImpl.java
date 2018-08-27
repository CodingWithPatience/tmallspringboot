package com.zhihao.tmall.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhihao.tmall.mapper.ReviewMapper;
import com.zhihao.tmall.pojo.Review;
import com.zhihao.tmall.pojo.ReviewExample;
import com.zhihao.tmall.pojo.User;
import com.zhihao.tmall.service.ReviewService;
import com.zhihao.tmall.service.UserService;
 
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    UserService userService;
 
    @Override
    public void add(Review c) {
        reviewMapper.insert(c);
    }
 
    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }
 
    @Override
    public void update(Review c) {
        reviewMapper.updateByPrimaryKeySelective(c);
    }
 
    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }
 
    public List<Review> list(int pid){
        ReviewExample example =new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
 
        List<Review> result =reviewMapper.selectByExample(example);
        setUser(result);
        return result;
    }
 
    public void setUser(List<Review> reviews){
        for (Review review : reviews) {
            setUser(review);
        }
    }
 
    private void setUser(Review review) {
        int uid = review.getUid();
        User user = userService.get(uid);
        review.setUser(user);
    }
 
    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }
}

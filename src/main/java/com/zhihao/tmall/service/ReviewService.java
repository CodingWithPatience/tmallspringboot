package com.zhihao.tmall.service;

import java.util.List;

import com.zhihao.tmall.pojo.Review;

public interface ReviewService {
     
    void add(Review c);

    void delete(int id);
    void update(Review c);
    Review get(int id);
    List<Review> list(int pid);

    int getCount(int pid);
}


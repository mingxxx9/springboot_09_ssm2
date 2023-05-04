package com.itheima.service.impl;

import com.itheima.dao.BlogDao;
import com.itheima.domain.Blog;
import com.itheima.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;
    @Override
    public List<Blog> getAll() {
        return blogDao.getAll();
    }

    @Override
    public List<Blog> getById(Integer id) {
        return blogDao.getById(id);
    }
}

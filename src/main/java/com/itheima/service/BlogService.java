package com.itheima.service;

import com.itheima.domain.Blog;

import com.itheima.tools.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BlogService {
    public List<Blog> getAll();
    public List<Blog> getById(Integer id);

    public Result likeblog(Integer id);
}

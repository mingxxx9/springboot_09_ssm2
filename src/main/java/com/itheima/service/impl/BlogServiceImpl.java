package com.itheima.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.itheima.dao.BlogDao;
import com.itheima.domain.Blog;
import com.itheima.service.BlogService;
import com.itheima.tools.Result;
import com.itheima.tools.UserOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.itheima.tools.Code.UPDATE_OK;
import static com.itheima.tools.Prefix.LIKED_BLOG;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public List<Blog> getAll() {
        return blogDao.getAll();
    }

    @Override
    public Blog getById(Integer id) {
        return blogDao.getById(id);
    }
@Override
    public Result likeblog(Integer id){

    //1.获取登录用户
    Integer userId= UserOps.getUser().getId();

    String key =LIKED_BLOG+id;
    Boolean isMember =stringRedisTemplate.opsForSet().isMember(key,userId.toString());
    if(BooleanUtil.isFalse(isMember)){
        boolean isSuccess = blogDao.likeblog(id);
        if (isSuccess) stringRedisTemplate.opsForSet().add(key,userId.toString());
    }else {
        boolean isSuccess=  blogDao.dislikeblog(id);
        if (isSuccess) stringRedisTemplate.opsForSet().remove(key,userId.toString());
    }
    return new Result(UPDATE_OK,blogDao.getById(id));
    }
}

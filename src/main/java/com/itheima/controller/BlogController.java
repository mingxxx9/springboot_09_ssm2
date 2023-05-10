package com.itheima.controller;

import com.itheima.domain.Blog;
import com.itheima.domain.UserDTO;
import com.itheima.service.BlogService;
import com.itheima.service.UserService;
import com.itheima.tools.Code;
import com.itheima.tools.Result;
import com.itheima.tools.UserOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itheima.tools.Prefix.LIKED_BLOG;

//已经弃用
@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
@Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping
    public Result getAll() {
         Integer localuserid= UserOps.getUser().getId();
        List<Blog> blogList = blogService.getAll();
        for (Blog blog:blogList
             ) {
            int userid=blog.getUserid();
            String key=LIKED_BLOG+blog.getId();
            UserDTO user = userService.getById(userid);
            Boolean isliked = stringRedisTemplate.opsForSet().isMember(key,localuserid.toString());
            blog.setIsliked(isliked);
            blog.setUsername(user.getName());
        }
        Integer code = blogList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = blogList != null ? "" : "数据查询失败，请重试！";
        System.out.println(1);
        return new Result(code,blogList,msg);
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Integer localuserid= UserOps.getUser().getId();
        Blog blog = blogService.getById(id);
        Integer code = blog != null ? Code.GET_OK : Code.GET_ERR;
        String msg = blog != null ? "" : "数据查询失败，请重试！";
        UserDTO user = userService.getById(id);
        String name=user.getName();
        String key=LIKED_BLOG+blog.getId();
        Boolean isliked = stringRedisTemplate.opsForSet().isMember(key,localuserid.toString());
        blog.setIsliked(isliked);
        blog.setUsername(name);

        return new Result(code,blog,msg);
    }
    @PutMapping("/like/{id}")
    public Result likeBlog(@PathVariable Integer id){
       return blogService.likeblog(id);
    }
}

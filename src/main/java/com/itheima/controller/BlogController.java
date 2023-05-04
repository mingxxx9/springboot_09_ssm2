package com.itheima.controller;

import com.itheima.domain.Blog;
import com.itheima.domain.UserDTO;
import com.itheima.service.BlogService;
import com.itheima.service.UserService;
import com.itheima.tools.Code;
import com.itheima.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;
    @GetMapping
    public Result getAll() {
        List<Blog> blogList = blogService.getAll();
        for (Blog blog:blogList
             ) {
            int userid=blog.getUserid();
            UserDTO user = userService.getById(userid);
            blog.setUsername(user.getName());
        }
        Integer code = blogList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = blogList != null ? "" : "数据查询失败，请重试！";
        System.out.println(1);
        return new Result(code,blogList,msg);
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        List<Blog> blogList = blogService.getById(id);
        Integer code = blogList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = blogList != null ? "" : "数据查询失败，请重试！";
        UserDTO user = userService.getById(id);
        String name=user.getName();
        for (Blog blog:blogList
        ) blog.setUsername(name);
        return new Result(code,blogList,msg);
    }
}

package com.itheima.service;

import cn.hutool.json.JSONUtil;
import com.itheima.domain.Book;
import com.itheima.domain.User;
import com.itheima.domain.UserDTO;
import com.itheima.tools.UserOps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private BlogService blogService;
    @Autowired
  private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;
    @Test
    public void test(){
        System.out.println(JSONUtil.toJsonStr(new Book()));
        System.out.println(stringRedisTemplate.opsForValue().get("name"));
        stringRedisTemplate.opsForValue().set("age","12");
    }
    @Test


    public  void before(){
        userService.getAllFriend(3);
    }
@Test
    public void testOps(){
    System.out.println(UserOps.getUser());
}



    @Test
    public void testBlog(){
        System.out.println(blogService.getAll());
    }

}

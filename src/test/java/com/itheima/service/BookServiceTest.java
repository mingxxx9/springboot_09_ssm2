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
    public void testGetById(){
        Book book = bookService.getById(2);
        System.out.println(book);
    }


    public  void before(){
        userService.getAllFriend(3);
    }
@Test
    public void testOps(){
    System.out.println(UserOps.getUser());
}
    @Test
    public void testGetAll(){
        List<Book> all = bookService.getAll();
        System.out.println(all);
    }
    @Test
    public void setStringRedisTemplate(){
        UserDTO user = userService.getById(3);
        String jsonStr = JSONUtil.toJsonStr(user);
        stringRedisTemplate.opsForValue().set("user",jsonStr);
        String userJson=stringRedisTemplate.opsForValue().get("user");
        System.out.println(userJson);
        User newUser=JSONUtil.toBean(userJson,User.class);
        System.out.println(newUser);


    }

}

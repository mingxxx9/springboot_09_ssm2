package com.itheima.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import com.itheima.domain.User;
import com.itheima.domain.UserDTO;
import com.itheima.service.UserService;
import com.itheima.tools.Code;
import com.itheima.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.itheima.tools.Code.LOGIN_ERR;
import static com.itheima.tools.Code.LOGIN_OK;
import static com.itheima.tools.Prefix.LOGIN_USER;
@RestController
@RequestMapping("/login")
public class LoginContronller {
    @Autowired
    private UserService userService;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @PostMapping()
    public Result UserLogin(@RequestBody User user){
        System.out.println("aa");
        //查询用户
        UserDTO loginUser=userService.login(user);
        if (loginUser==null) return new Result(LOGIN_ERR,null,"密码或用户名错误");
        //获取token
        String token = UUID.randomUUID(true).toString();
        String key=LOGIN_USER+token;
        //redis中存入token
        //  UserDTO loginUser1 = BeanUtil.copyProperties(loginUser, UserDTO.class);
        Map<String, Object> beanToMap = BeanUtil.beanToMap(loginUser,new HashMap<>(), CopyOptions.create().ignoreNullValue().setFieldValueEditor((fieldName, fieldValue)-> fieldValue.toString()));
        stringRedisTemplate.opsForHash().putAll(key,beanToMap);
        System.out.println(stringRedisTemplate.expire(key, 30, TimeUnit.MINUTES));

        //返回token
        return new Result(LOGIN_OK,token,"登录成功");

    }
    @PostMapping("/add")
    //添加用户
    public Result save(@RequestBody User user) {
        boolean flag = userService.save(user);
        if (flag) return UserLogin(user);
       else return new Result(Code.SAVE_ERR,flag,"创建失败");
    }
}

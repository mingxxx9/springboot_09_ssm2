package com.itheima.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import com.itheima.domain.GongDe;
import com.itheima.domain.User;
import com.itheima.domain.UserDTO;
import com.itheima.tools.UserOps;
import com.itheima.service.UserService;
import com.itheima.tools.Code;

import com.itheima.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.itheima.tools.Code.*;
import static com.itheima.tools.Prefix.LOGIN_USER;


@RestController
@RequestMapping("/users")
public class UserContronller {
    @Autowired
    private UserService userService;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @PostMapping
    public Result save(@RequestBody User user) {
        boolean flag = userService.save(user);
        return new Result(flag ? Code.SAVE_OK:Code.SAVE_ERR,flag);
    }

    @PutMapping
    public Result update(@RequestBody UserDTO user) {
        boolean flag = userService.update(user);
        return new Result(flag ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    @PutMapping("/add/{id}")
    public Result addGongDe(@PathVariable Integer id) {
        Integer gongDe= userService.add(id);
        if (gongDe==-1) return new Result(Code.UPDATE_ERR,null,"一天不能超过十次");
        System.out.println(gongDe);
        return new Result( Code.UPDATE_OK,gongDe);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean flag = userService.delete(id);

        return new Result(flag ? Code.DELETE_OK:Code.DELETE_ERR,flag);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        UserDTO user = UserOps.getUser();
        Integer code = user != null ? Code.GET_OK : Code.GET_ERR;
        String msg = user != null ? "" : "数据查询失败，请重试！";
        return new Result(code,user,msg);
    }

    @GetMapping
    public Result getAllFriend() {
        UserDTO host=UserOps.getUser();
        List<UserDTO> userList = userService.getAllFriend(host.getId());
        Integer code = userList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = userList != null ? "" : "数据查询失败，请重试！";
        System.out.println(1);
        return new Result(code,userList,msg);
    }
    @PutMapping("/stole/{id}")
    public Result StoleFriend(@PathVariable Integer id){
     //获得随机数
        int random= (int)(Math.random()*10);
        int value=5;
        //获得被偷的对象
        UserDTO friend=userService.getById(id);
        //获得当前用户
//        User host=UserOps.getUser();
        System.out.println(UserOps.getUser());
        UserDTO host=userService.getById(2);
        int friend_gongde=friend.getGongDe();
        int host_gongde=host.getGongDe();
        if (friend_gongde<5) return new Result(STOLE_ERR,null,"好友已经没有功德了");
        host.setGongDe(host_gongde+value-random);
        friend.setGongDe(friend_gongde-value);
        userService.update(host);
        userService.update(friend);
        return new Result(STOLE_OK,new GongDe(host.getGongDe(),friend.getGongDe()),"偷取好友5功德，偷东西扣"+random+"功德，共获得"+(value-random));

    }
@PostMapping("/login")
    public Result UserLogin(@RequestBody User user){
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

}

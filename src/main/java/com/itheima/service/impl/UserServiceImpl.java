package com.itheima.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.itheima.dao.UserDao;
import com.itheima.domain.GongDe;
import com.itheima.domain.User;

import com.itheima.domain.UserDTO;
import com.itheima.tools.Code;
import com.itheima.tools.Result;
import com.itheima.tools.UserOps;

import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.itheima.tools.Code.STOLE_ERR;
import static com.itheima.tools.Code.STOLE_OK;
import static com.itheima.tools.Prefix.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean save(User user) {
        return userDao.save(user) > 0;
    }

    public boolean update(UserDTO user) {
        return userDao.update(user) > 0;
    }

    public boolean delete(Integer id) {
        return userDao.delete(id) > 0;
    }

    public UserDTO getById(Integer id) {
//        if(id == 1){
//            throw new BusinessException(Code.BUSINESS_ERR,"请不要使用你的技术挑战我的耐性!");
//        }
        return userDao.getById(id);
    }

    @Override
    public  UserDTO login(User user) {
       return userDao.loginByPassword(user);
    }

    @Override
    //redis实现，需要每天删除redis中的stoled:id字段
//    public Result stoleFriend(Integer id) {
//        //获得随机数
//        int random= (int)(Math.random()*10);
//        int value=5;
//        //获得被偷的对象
//        UserDTO friend=getById(id);
//        //获得当前用户
//        UserDTO host=UserOps.getUser();
//        //判断今天是否偷取过
//        String key =STOLED_ID+id;
//        Boolean isMember =stringRedisTemplate.opsForSet().isMember(key,friend.getId().toString());
//        //偷过了直接发返回结果
//        if(BooleanUtil.isTrue(isMember)){
//            return new Result(STOLE_OK,"今天已经偷过了");
//        }
//        //加入到redis中
//        stringRedisTemplate.opsForSet().add(key,friend.getId().toString());
//        //提取数据，封装成bean
//        int friend_gongde=friend.getGongDe();
//        int host_gongde=host.getGongDe();
//        if (friend_gongde<5) return new Result(STOLE_ERR,null,"好友已经没有功德了");
//        host.setGongDe(host_gongde+value-random);
//        friend.setGongDe(friend_gongde-value);
//        update(host);
//        update(friend);
//        return new Result(STOLE_OK,new GongDe(host.getGongDe(),friend.getGongDe()),"偷取好友5功德，偷东西扣"+random+"功德，共获得"+(value-random));
//
//    }
            //直接操作数据库实现
    public Result stoleFriend(Integer id) {
        //获得随机数
        int random= (int)(Math.random()*10);
        int value=5;
        //获得被偷的对象
        UserDTO friend=getById(id);
        //获得当前用户
        UserDTO host=UserOps.getUser();
        //判断今天是否偷取过
        String key =STOLED_ID+id;
        int  isStoled= userDao.queryStoled(host.getId(),friend.getId());
        //偷过了直接发返回结果
        if(isStoled==1){
            return new Result(STOLE_OK,"今天已经偷过了");
        }
        //修改mysql中的字段
       int  isSuccess=userDao.updateStoled(host.getId(),friend.getId());
        if (isSuccess==0) return new Result(Code.SYSTEM_UNKNOW_ERR,null,"系统繁忙，请稍后再试！");
        //提取数据，封装成bean
        int friend_gongde=friend.getGongDe();
        int host_gongde=host.getGongDe();
        if (friend_gongde<5) return new Result(STOLE_ERR,null,"好友已经没有功德了");
        host.setGongDe(host_gongde+value-random);
        friend.setGongDe(friend_gongde-value);
        update(host);
        update(friend);
        return new Result(STOLE_OK,new GongDe(host.getGongDe(),friend.getGongDe()),"偷取好友5功德，偷东西扣"+random+"功德，共获得"+(value-random));

    }

    public List<UserDTO> getAll() {
        return userDao.getAll();
    }

    @Override
    public List<UserDTO> getAllFriend(Integer id) {
        System.out.println(UserOps.getUser());
        return userDao.getAllFriend(id);
    }

    @Override
    public Integer add(Integer id) {
        UserDTO user= UserOps.getUser();
       if (user.getIs_ten()==10) return -1;

        userDao.update(user);
       return user.getGongDe();
    }

}

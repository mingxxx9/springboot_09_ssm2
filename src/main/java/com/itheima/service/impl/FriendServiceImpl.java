package com.itheima.service.impl;

import com.itheima.dao.FriendDao;
import com.itheima.dao.UserDao;
import com.itheima.domain.GongDe;
import com.itheima.domain.UserDTO;
import com.itheima.service.FriendService;
import com.itheima.tools.Code;
import com.itheima.tools.Result;
import com.itheima.tools.UserOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.text.normalizer.CharTrie;

import java.util.List;

import static com.itheima.tools.Code.STOLE_ERR;
import static com.itheima.tools.Code.STOLE_OK;
import static com.itheima.tools.Prefix.STOLED_ID;
@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private UserDao userDao;
    public List<UserDTO> getAllFriend(Integer id) {
        System.out.println(UserOps.getUser());
        return friendDao.getAllFriend(id);
    }


    public Result stoleFriend(Integer id) {
        //获得随机数
        int random= (int)(Math.random()*10);
        int value=5;
        //获得被偷的对象
        UserDTO friend=userDao.getById(id);
        //获得当前用户
        UserDTO host=UserOps.getUser();
        //判断今天是否偷取过
        String key =STOLED_ID+id;
        int  isStoled= friendDao.queryStoled(host.getId(),friend.getId());
        //偷过了直接发返回结果
        if(isStoled==1){
            return new Result(STOLE_OK,"今天已经偷过了");
        }
        //修改mysql中的字段
        int  isSuccess=friendDao.updateStoled(host.getId(),friend.getId());
        if (isSuccess==0) return new Result(Code.SYSTEM_UNKNOW_ERR,null,"系统繁忙，请稍后再试！");
        //提取数据，封装成bean
        int friend_gongde=friend.getGongDe();
        int host_gongde=host.getGongDe();
        if (friend_gongde<5) return new Result(STOLE_ERR,null,"好友已经没有功德了");
        host.setGongDe(host_gongde+value-random);
        friend.setGongDe(friend_gongde-value);
        userDao.update(host);
        userDao.update(friend);
        return new Result(STOLE_OK,new GongDe(host.getGongDe(),friend.getGongDe()),"偷取好友5功德，偷东西扣"+random+"功德，共获得"+(value-random));

    }
}

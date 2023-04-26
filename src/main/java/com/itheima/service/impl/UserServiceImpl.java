package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;

import com.itheima.domain.UserDTO;
import com.itheima.tools.UserOps;

import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

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

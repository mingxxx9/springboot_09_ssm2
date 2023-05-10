package com.itheima.service;


import com.itheima.domain.User;
import com.itheima.domain.UserDTO;
import com.itheima.tools.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserService {

    /**
     * 保存
     * @param user
     * @return
     */
    public boolean save(User user);

    /**
     * 修改
     * @param user
     * @return
     */
    public boolean update(UserDTO user);

    /**
     * 按id删除
     * @param id
     * @return
     */
    public boolean delete(Integer id);

    /**
     * 按id查询
     * @param id
     * @return
     */
    public UserDTO getById(Integer id);

    /**
     * 查询全部
     * @return
     */
    public List<UserDTO> getAll();

    public List<UserDTO> getAllFriend(Integer id);

    public  Integer add(Integer id);

    public UserDTO login(User user);

    public Result stoleFriend(Integer id);

    Result followUser(Integer id);
}

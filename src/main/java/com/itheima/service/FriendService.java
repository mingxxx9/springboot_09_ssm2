package com.itheima.service;

import com.itheima.domain.UserDTO;
import com.itheima.tools.Result;

import java.util.List;

public interface FriendService {
    public List<UserDTO> getAllFriend(Integer id);
    public Result stoleFriend(Integer id);
}

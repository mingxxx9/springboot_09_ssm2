package com.itheima.dao;

import com.itheima.domain.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FriendDao {
    @Select("select * from login_user where id in (select friend from is_friend where host=#{id} )")
    public List<UserDTO> getAllFriend(Integer id);
    @Select("select is_stoled from is_friend where host=#{host} and friend=#{friend}")
    public int  queryStoled(@Param("host") Integer host, @Param("friend")Integer friend);
    @Update("update is_friend set is_stoled=1  where host=#{host} and friend=#{friend}")
    public int updateStoled(@Param("host") Integer host,@Param("friend")Integer friend);

    @Insert("INSERT INTO is_friend values (#{host},#{friend},0)" )
    public int makeFriend(@Param("host") Integer host,@Param("friend")Integer friend);
}

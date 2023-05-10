package com.itheima.dao;


import com.itheima.domain.User;
import com.itheima.domain.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

// TODO 添加@Mapper
@Mapper
public interface UserDao {
    @Insert("insert into login_User (name,gender,password) values(#{name},#{gender},#{password})")
    public int save(User user);

    @Update("update login_user set name = #{name}, gender = #{gender}, gongDe = #{gongDe},is_ten= #{is_ten} where id = #{id}")
    public int update(UserDTO user);

    @Delete("delete from login_user where id = #{id}")
    public int delete(Integer id);

    @Select("select * from login_user where id = #{id}")
    public UserDTO getById(Integer id);

    @Select("select * from login_user")
    public List<UserDTO> getAll();

    @Select("select * from login_user where id in (select friend from is_friend where host=#{id} )")
    public List<UserDTO> getAllFriend(Integer id);
    @Select("select * from login_user where name= #{name} and password=#{password} ")
    public UserDTO loginByPassword(User user);
    @Select("select is_stoled from is_friend where host=#{host} and friend=#{friend}")
    public int  queryStoled(@Param("host") Integer host,@Param("friend")Integer friend);
    @Update("update is_friend set is_stoled=1  where host=#{host} and friend=#{friend}")
    public int updateStoled(@Param("host") Integer host,@Param("friend")Integer friend);
}

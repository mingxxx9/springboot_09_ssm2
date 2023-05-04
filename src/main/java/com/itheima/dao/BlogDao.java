package com.itheima.dao;

import com.itheima.domain.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface BlogDao {
@Select("select * from conment")
    public List<Blog> getAll();
@Select("select * from conment where id = #{id}")
         List<Blog> getById(Integer id);
}


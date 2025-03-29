package com.example.demo.Mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper
{
  @Insert("insert into users (account_id, name, token, gmt_create, gmt_modified) values (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified})")
  void insert(User user);
  
  @Select("select * from users where token=#{value}")
  User findByToken(String value);
}

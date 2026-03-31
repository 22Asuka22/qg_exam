package com.zjy.mapper;

import com.zjy.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectAll();
    List<User> selectByNumPassword(@Param("number")String number, @Param("password")String password);
    int insertUser(@Param("number")String number, @Param("password")String password);
    List<User> selectByNum(String number);
    int updateDormByNum(@Param("dorm")String dorm, @Param("number")String number);
    int updatePasswordByNum(@Param("password")String password, @Param("number")String number);
    String selectPasswordByNum(String number);
}

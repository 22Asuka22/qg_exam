package com.zjy.service;


import com.zjy.constant.UserConstant;
import com.zjy.mapper.UserMapper;
import com.zjy.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User login(String number, String password) {
        List<User> users= userMapper.selectByNumPassword(number, password);
        return (users == null || users.isEmpty())?null:users.get(0);
    }

    public boolean checkStudentNumberRegex(String number){
        if(number.matches(UserConstant.STUDENT_ID_REGEX)){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkAdminNumberRegex(String number){
        if(number.matches(UserConstant.ADMIN_ID_REGEX)){
            return true;
        }else{
            return false;
        }
    }

    public int register(String number, String password) {
        return userMapper.insertUser(number, password);
    }

    public int updateDorm(String dorm,String num){
        return userMapper.updateDormByNum(dorm,num);
    }
}

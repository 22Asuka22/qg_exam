package com.zjy.service;

import com.zjy.controller.UserConstant;
import com.zjy.mapper.MybatisUtils;
import com.zjy.mapper.UserMapper;
import com.zjy.pojo.User;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserService {
    public boolean login(String number, String password) {
        SqlSession sqlSession = null;
        try{
            sqlSession = MybatisUtils.getSqlSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> student = userMapper.selectByNumPassword(number, password);
            if(student.isEmpty()){
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            MybatisUtils.close(sqlSession);
        }
        return false;
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

    public boolean checkNumberLive(String number){
        SqlSession sqlSession = null;
        try{
            sqlSession = MybatisUtils.getSqlSession(true);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> user = userMapper.selectByNum(number);
            if(user!=null&&!user.isEmpty()){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(UserConstant.DB_WRONG_MSG);
        }finally{
            MybatisUtils.close(sqlSession);
        }
        return false;
    }



    public int register(String number, String password) {
        SqlSession sqlSession = null;
        try{
            sqlSession = MybatisUtils.getSqlSession(true);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.insertUser(number, password);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            MybatisUtils.close(sqlSession);
        }
        return 0;
    }

    public List<User> selectUser(String number,String password) {
        SqlSession sqlSession = null;
        try{
            sqlSession = MybatisUtils.getSqlSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.selectByNumPassword(number, password);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            MybatisUtils.close(sqlSession);
        }
        return null;
    }

    public int updatePassword(User user) {
        SqlSession sqlSession = null;
        try{
            sqlSession = MybatisUtils.getSqlSession(true);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.updatePasswordByNum(user.getPassword(), user.getNumber());
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            MybatisUtils.close(sqlSession);
        }
        return 0;
    }
}

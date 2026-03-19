package com.zjy.service;

import com.zjy.controller.UserConstant;
import com.zjy.mapper.DormAffairMapper;
import com.zjy.mapper.MybatisUtils;
import com.zjy.mapper.UserMapper;
import com.zjy.pojo.DormAffair;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AffairService {
    public int updateDorm(String number,String dorm){
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.getSqlSession(true);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.updateDormByNum(dorm,number);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            MybatisUtils.close(sqlSession);
        }
        return 0;
    }

    public boolean checkPhoneRegex(String phone){
        if(phone.matches(UserConstant.PHONE_REGEX)){
            return true;
        }else{
            return false;
        }
    }

    public int insertDormAffair(DormAffair dormAffair){
        SqlSession sqlSession = null;
        try{
            sqlSession = MybatisUtils.getSqlSession(true);
            DormAffairMapper mapper = sqlSession.getMapper(DormAffairMapper.class);
            return mapper.insertDormAffair(dormAffair);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(UserConstant.DB_WRONG_MSG);
        }finally{
            MybatisUtils.close(sqlSession);
        }
        return 0;
    }

    public List<DormAffair> selectDormAffair(String number){
        SqlSession sqlSession = null;
        try{
            sqlSession = MybatisUtils.getSqlSession(true);
            DormAffairMapper mapper = sqlSession.getMapper(DormAffairMapper.class);
            return mapper.selectDormAffairByNum(number);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(UserConstant.DB_WRONG_MSG);
        }finally{
            MybatisUtils.close(sqlSession);
        }
        return null;
    }

    public int deleteDormAffair(int id){
        SqlSession sqlSession = null;
        try{
            sqlSession = MybatisUtils.getSqlSession(true);
            DormAffairMapper mapper = sqlSession.getMapper(DormAffairMapper.class);
            return mapper.deleteDormAffairById(id);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            MybatisUtils.close(sqlSession);
        }
        return 0;
    }
}

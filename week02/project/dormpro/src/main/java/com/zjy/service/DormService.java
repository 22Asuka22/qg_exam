package com.zjy.service;

import com.zjy.Utils.JwtUtils;
import com.zjy.mapper.DormAffairMapper;
import com.zjy.mapper.UserMapper;
import com.zjy.pojo.DormAffair;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormService {
    @Autowired
    private DormAffairMapper dormAffairMapper;
    @Autowired
    private UserMapper userMapper;

    public int createAffair(DormAffair dormAffair) {
        return dormAffairMapper.insertDormAffair(dormAffair);
    }

    public List<DormAffair> getDormAffair(String number) {
        return dormAffairMapper.selectDormAffairByNum(number);
    }

    public int deleteAffair(int id) {
        return dormAffairMapper.deleteDormAffairById(id);
    }

    public String selectPasswordByNum(String number) {
        return userMapper.selectPasswordByNum(number);
    }

    public int updatePasswordByNum(String password,String number) {
        return userMapper.updatePasswordByNum(password,number);
    }

    public List<DormAffair> selectAllDormAffair() {
        return dormAffairMapper.selectAllDormAffair();
    }

    public int updateState(int state,int id) {
        return dormAffairMapper.updateState(state,id);
    }

    public List<DormAffair> selectDormAffairByState(int state) {
        return dormAffairMapper.selectDormAffairByState(state);
    }
}

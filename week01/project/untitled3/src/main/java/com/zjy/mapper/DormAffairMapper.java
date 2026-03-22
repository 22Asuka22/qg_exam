package com.zjy.mapper;

import com.zjy.pojo.DormAffair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DormAffairMapper {
    int insertDormAffair(DormAffair dormAffair);
    List<DormAffair> selectDormAffairByNum(String number);
    int deleteDormAffairById(int id);
    List<DormAffair> selectDormAffairByState(int state);
    List<DormAffair> selectAllDormAffair();
    int updateState(@Param("state")int state,@Param("id")int id);
}
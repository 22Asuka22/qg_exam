package com.zjy.mapper;

import com.zjy.pojo.DormAffair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DormAffairMapper {
    int insertDormAffair(DormAffair dormAffair);
    List<DormAffair> selectDormAffairByNum(String number);
    int deleteDormAffairById(int id);
}
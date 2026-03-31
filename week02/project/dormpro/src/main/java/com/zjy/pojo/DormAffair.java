package com.zjy.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DormAffair {
    private int id;
    private String number;
    private String problem;
    private Timestamp time;
    private String phone;
    private String dorm;
    private int state;
    private String imgUrl;
}

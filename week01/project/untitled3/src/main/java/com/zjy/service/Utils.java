package com.zjy.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Utils {
    public static int choiceInput(){
        String number;
        Scanner sc = new Scanner(System.in);
        number = sc.nextLine();
        String numberRegex = "\\d+";
        int numberInt;
        if(number.matches(numberRegex)){
            numberInt = Integer.parseInt(number);
        }else{
            return -1;
        }
        return numberInt;
    }

    public static Timestamp getCurrentSqlTimestamp() {
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        return Timestamp.valueOf(nowLocalDateTime);
    }

}

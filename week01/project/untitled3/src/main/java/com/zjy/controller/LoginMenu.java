package com.zjy.controller;

import com.zjy.pojo.User;
import com.zjy.service.UserService;
import com.zjy.service.Utils;

import java.util.List;
import java.util.Scanner;

public class LoginMenu {
    private UserService userService = new UserService();
    private Scanner sc = new Scanner(System.in);
    private StudentMenu studentMenu = new StudentMenu();
    public void showMenu(){
        while(true){
            System.out.println("=====================================");
            System.out.println("🏠 宿舍报修管理系统");
            System.out.println("=====================================");
            System.out.println("1. 登录");
            System.out.println("2. 注册");
            System.out.println("3. 退出");
            System.out.print("请选择操作（输入 1-3）：\n>");

            int choice = Utils.choiceInput();

            switch(choice){
                case -1:
                    System.out.println(UserConstant.WRONG_INPUT_MSG);
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("再见");
                    System.exit(0);
                default:
                    System.out.println(UserConstant.OVER_INPUT_MSG);
            }
        }
    }

    public void login(){
        System.out.println("===== 用户登录 =====");
        String number;
        String password;
        boolean bool;
        System.out.print("请输入账号：\n>");
        number = sc.nextLine();
        System.out.print("请输入密码：\n>");
        password = sc.nextLine();
        bool = userService.login(number,password);
        if(bool&&number.matches(UserConstant.STUDENT_ID_REGEX)){
            System.out.print(UserConstant.LOGIN_SUCCESS_MSG);
            System.out.println("角色：学生");
            List<User> students = userService.selectUser(number,password);
            if (students == null || students.isEmpty()) {
                System.out.println("用户信息查询失败！");
                return;
            }
            User student = students.get(0);
            studentMenu.studentMainMenu(student);
        }else if(bool&&number.matches(UserConstant.ADMIN_ID_REGEX)){
            System.out.print(UserConstant.LOGIN_SUCCESS_MSG);
            System.out.println("角色：维修人员");
        } else{
            System.out.println(UserConstant.LOGIN_FAIL_MSG);
        }

    }

    public void register(){
        System.out.println("===== 用户注册 =====");
        System.out.print("请选择角色（1-学生，2-维修人员）：\n>");
        String number;
        String password;
        String repeatPassword;
        int choice = Utils.choiceInput();
        switch(choice){
            case -1:
                System.out.println(UserConstant.WRONG_INPUT_MSG);
                break;
            case 1:
                System.out.print("请输入学号（前缀3125或3225）：\n>");
                number = sc.nextLine();
                if(!userService.checkStudentNumberRegex(number)){
                    System.out.println("账号格式错误");
                    break;
                }
                if(userService.checkNumberLive(number)){
                    System.out.println("账号已存在!");
                    break;
                }
                System.out.print("请输入密码：\n>");
                password = sc.nextLine();
                System.out.print("请确认密码：\n>");
                repeatPassword = sc.nextLine();
                if(password.equals(repeatPassword)){
                    if(userService.register(number,password)==1){
                        System.out.println("注册成功");
                        break;
                    }else{
                        System.out.println(UserConstant.DB_WRONG_MSG);
                        break;
                    }
                }else{
                    System.out.println(UserConstant.REPEAT_WRONG_MSG);
                    break;
                }
            case 2:
                System.out.print("请输入学号（前缀0025）：\n>");
                number = sc.nextLine();
                if(!userService.checkAdminNumberRegex(number)){
                    System.out.println("账号格式错误");
                    break;
                }
                if(userService.checkNumberLive(number)){
                    System.out.println("账号已存在！");
                    break;
                }
                System.out.print("请输入密码：\n>");
                password = sc.nextLine();
                System.out.print("请确认密码：\n>");
                repeatPassword = sc.nextLine();
                if(password.equals(repeatPassword)){
                    if(userService.register(number,password)==1){
                        System.out.println("注册成功");
                        break;
                    }else{
                        System.out.println(UserConstant.DB_WRONG_MSG);
                        break;
                    }
                }else{
                    System.out.println(UserConstant.REPEAT_WRONG_MSG);
                    break;
                }
            default:
                System.out.println(UserConstant.OVER_INPUT_MSG);
        }
    }
}

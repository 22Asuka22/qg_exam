package com.zjy.controller;

import com.zjy.pojo.DormAffair;
import com.zjy.service.AffairService;
import com.zjy.service.Utils;

import java.util.List;

public class AdminMenu {
    private AffairService affairService = new AffairService();
    public void AdminMainMenu() {
        System.out.println("===== 管理员菜单 =====");
        System.out.println("1. 查看所有报修单");
        System.out.println("2. 查看报修单详情");
        System.out.println("3. 更新报修单状态");
        System.out.println("4. 删除报修单");
        System.out.println("5. 修改密码");
        System.out.println("6. 退出");
        System.out.print("请选择操作（输入 1-6）：\n>");

        int choice = Utils.choiceInput();
        switch (choice) {
            case -1:
                System.out.println(UserConstant.WRONG_INPUT_MSG);
                break;
            case 1:

        }
    }

    public void showAllDormAffair(){
        //创建list
        //让用户输入想要显示的装态分为0,1，all
        //调用service的方法，根据用户想要的状态传入不同参数，将信息存储到list中
        //此处主要显示id，宿舍号，粗略报修信息，时间，状态
    }

    public void showDormAffair(){
        //让用户输入id
        //调用service的方法，根据id将信息存放到list中
        //显示所有消息
    }

    public void updateDormAffair(){
        //让用户输入id
        //调用service的方法，根据id将状态更新到list中
    }

    public void deleteAffair(){
        showAllDormAffair();
        //输入要删除的affair序号
        System.out.print("请输入要删除的报修信息的序号：\n>");
        int id = Utils.choiceInput();
        if(affairService.deleteDormAffair(id)==1){
            System.out.println("删除成功！");
        }else{
            System.out.println(UserConstant.DB_WRONG_MSG+"或检查保修单id是否正确");
        }
        //调service里的删除方法
    }
}

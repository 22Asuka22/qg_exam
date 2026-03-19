package com.zjy.controller;

import com.zjy.pojo.DormAffair;
import com.zjy.pojo.User;
import com.zjy.service.AffairService;
import com.zjy.service.UserService;
import com.zjy.service.Utils;

import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private AffairService affairService = new AffairService();
    private UserService userService = new UserService();
    private User user = null;
    private Scanner sc = new Scanner(System.in);
    public void studentMainMenu(User usertemp){
        user = usertemp;
        while(true) {
            if (user.getDorm() == null) {
                System.out.print("请输入宿舍号：\n>");
                user.setDorm(sc.nextLine());
                if (affairService.updateDorm(user.getNumber(), user.getDorm()) == 1) {
                    System.out.println("宿舍绑定成功！");
                } else {
                    System.out.println(UserConstant.DB_WRONG_MSG);
                    break;
                }
            }
            System.out.println("===== 学生菜单 =====");
            System.out.println("1. 绑定/修改宿舍");
            System.out.println("2. 创建报修单");
            System.out.println("3. 查看我的报修记录");
            System.out.println("4. 取消报修单");
            System.out.println("5. 修改密码");
            System.out.println("6. 退出");
            System.out.print("请选择操作（输入 1-6）：\n>");

            int choice = Utils.choiceInput();

            switch (choice) {
                case -1:
                    System.out.println(UserConstant.WRONG_INPUT_MSG);
                    break;
                case 1:
                    changeDorm();
                    break;
                case 2:
                    setAffair();
                    break;
                case 3:
                    showAffair();
                    break;
                case 4:
                    deleteAffair();
                    break;
                case 5:
                    updatePassword();
                    break;
                case 6:
                    System.out.println("再见");
                    System.exit(0);
                default:
                    System.out.println(UserConstant.OVER_INPUT_MSG);
            }
        }
    }

    public void changeDorm(){
        System.out.print("请输入宿舍号：\n>");
        user.setDorm(sc.nextLine());
        if(affairService.updateDorm(user.getNumber(),user.getDorm())==1){
            System.out.println("宿舍修改绑定成功！");
        }else {
            System.out.println(UserConstant.DB_WRONG_MSG);
        }
    }

    public void setAffair(){
        DormAffair dormAffair = new DormAffair();
        dormAffair.setNumber(user.getNumber());
        dormAffair.setDorm(user.getDorm());
        System.out.println("用户："+user.getNumber() +"\t宿舍号："+user.getDorm());
        System.out.print("请输入报修详情：\n>");
        dormAffair.setProblem(sc.nextLine());
        System.out.print("请输入联系电话：\n>");
        String phone = sc.nextLine();
        if(affairService.checkPhoneRegex(phone)){
            dormAffair.setPhone(phone);
        }else{
            System.out.println("电话号码格式错误！");
            return;
        }
        dormAffair.setTime(Utils.getCurrentSqlTimestamp());
        dormAffair.setState(0);
        if(affairService.insertDormAffair(dormAffair)!=0){
            System.out.println("报修添加成功!");
        }else{
            System.out.println(UserConstant.DB_WRONG_MSG+",报修添加失败!");
        }
    }

    public void showAffair(){
        //创建list存DormAffair
        List<DormAffair> dormAffairs = affairService.selectDormAffair(user.getNumber());
        if(dormAffairs==null||dormAffairs.isEmpty()){
            System.out.println("未检查到报修记录");
            return;
        }
        //调用service里的方法写入list值
        //输出所有affair
        int pos=1;
        for(DormAffair dormAffair:dormAffairs){
            System.out.println(pos+" 报修信息："+dormAffair.getProblem()
                    +"\t电话号："+dormAffair.getPhone()
                    +"\t创建时间："+dormAffair.getTime()
                    +"\t状态："+(dormAffair.getState() == 1? "已维修":"未维修"));
            ++pos;
        }
    }

    public void deleteAffair(){
        //创建list存DormAffair
        //调用service里的方法写入list值
        //输出所有affair
        List<DormAffair> dormAffairs = affairService.selectDormAffair(user.getNumber());
        if(dormAffairs==null||dormAffairs.isEmpty()){
            System.out.println("未检查到报修记录");
            return;
        }
        int pos=1;
        for(DormAffair dormAffair:dormAffairs){
            System.out.println(pos+"\t报修信息："+dormAffair.getProblem()
                    +"\t电话号："+dormAffair.getPhone()
                    +"\t创建时间："+dormAffair.getTime()
                    +"\t状态："+(dormAffair.getState() == 1? "已维修":"未维修"));
            ++pos;
        }
        //输入要删除的affair序号
        System.out.print("请输入要删除的报修信息的序号：\n>");
        int choice = Utils.choiceInput();
        if(choice>pos||choice<1){
            System.out.println("无法识别序号");
        }else{
            if(affairService.deleteDormAffair(dormAffairs.get(choice-1).getId())==1){
                System.out.println("删除成功！");
            }else{
                System.out.println(UserConstant.DB_WRONG_MSG);
            }
        }
        //调用service里的删除方法
    }

    public void updatePassword(){
        //输入原密码
        System.out.print("请输入原密码\n>");
        String password = sc.nextLine();
        System.out.println(user.getPassword());
        if(password.equals(user.getPassword())){
            System.out.print("请输入新密码\n>");
            String newPassword = sc.nextLine();
            System.out.print("请再次确认密码\n>");
            String confirmPassword = sc.nextLine();
            if(confirmPassword.equals(newPassword)) {
                user.setPassword(newPassword);
            }else{
                System.out.println(UserConstant.REPEAT_WRONG_MSG);
                return;
            }
            if(userService.updatePassword(user)==1){
                System.out.println("密码修改成功！");
            }else{
                System.out.println(UserConstant.DB_WRONG_MSG);
            }
        }else{
            System.out.println("原密码错误!");
        }
        //输入新密码
        //确认新密码
    }

}

package com.zjy.controller;

import com.zjy.pojo.DormAffair;
import com.zjy.pojo.User;
import com.zjy.service.AffairService;
import com.zjy.service.UserService;
import com.zjy.service.Utils;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final AffairService affairService = new AffairService();
    private final UserService userService = new UserService();
    private User user = null;
    private final Scanner sc = new Scanner(System.in);
    public void AdminMainMenu(User usertemp) {
        user = usertemp;
        while (true) {
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
                    showAllDormAffair();
                    break;
                case 2:
                    showDormAffair();
                    break;
                case 3:
                    updateDormAffair();
                    break;
                case 4:
                    deleteDormAffair();
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

    public void showAllDormAffair(){
        //让用户输入想要显示的装态分为0,1，all
        System.out.println("=====报修单状态=====");
        System.out.println("1.未完成");
        System.out.println("2.已完成");
        System.out.println("3.全部");
        System.out.print("请选择想要查看的报修单状态：\n>");

        int choice = Utils.choiceInput();
        switch (choice) {
            case -1:
                System.out.println(UserConstant.WRONG_INPUT_MSG);
                break;
            case 1:
                showOrFinish(0);
                break;
            case 2:
                showOrFinish(1);
                break;
            case 3:
                showAll();
                break;
            default:
                System.out.println(UserConstant.OVER_INPUT_MSG);
        }
        //调用service的方法，根据用户想要的状态传入不同参数，将信息存储到list中
        //此处主要显示宿舍号，粗略报修信息，时间，状态
    }

    public void showOrFinish(int i){
        List<DormAffair> dormAffairs = affairService.selectDormAffairByState(i);
        if(dormAffairs==null||dormAffairs.isEmpty()){
            System.out.println(UserConstant.UNFIND_MSG);
            return;
        }
        int pos = 1;
        for (DormAffair dormAffair : dormAffairs) {
            String shortProblem = dormAffair.getProblem().length()>=5 ? dormAffair.getProblem().substring(0, 5) : dormAffair.getProblem();
            System.out.println(pos
                    +"\t时间："+dormAffair.getTime()
                    +"\t宿舍号："+dormAffair.getDorm()
                    +"\t问题："+shortProblem
                    +"\t状态："+(dormAffair.getState() == 1? "已维修":"未维修"));
        }
    }

    public List<DormAffair> showAll(){
        List<DormAffair> dormAffairs = affairService.selectAllDormAffair();
        if(dormAffairs==null||dormAffairs.isEmpty()){
            System.out.println(UserConstant.UNFIND_MSG);
            return null;
        }
        int pos = 1;
        for (DormAffair dormAffair : dormAffairs) {
            String shortProblem = dormAffair.getProblem().length()>=5 ? dormAffair.getProblem().substring(0, 5) : dormAffair.getProblem();
            System.out.println(pos
                    +"\t时间："+dormAffair.getTime()
                    +"\t宿舍号："+dormAffair.getDorm()
                    +"\t问题："+shortProblem
                    +"\t状态："+(dormAffair.getState() == 1? "已维修":"未维修"));
            pos++;
        }
        return dormAffairs;
    }

    public void showDormAffair(){
        List<DormAffair> dormAffairs = showAll();
        if(dormAffairs ==null||dormAffairs.isEmpty()){
            return;
        }
        int sum = dormAffairs.size();
        System.out.print("请输入想要详细查看内容的报修信息的序号:\n>");

        int choice = Utils.choiceInput();

        if(choice<1||choice>sum){
            System.out.println(UserConstant.WRONG_INPUT_MSG);
            return;
        }

        System.out.println("时间："+dormAffairs.get(choice-1).getTime()
                +"\n宿舍号："+dormAffairs.get(choice-1).getDorm()
                +"\n报修信息："+dormAffairs.get(choice-1).getProblem()
                +"\n电话号："+dormAffairs.get(choice-1).getPhone()
                +"\n状态："+(dormAffairs.get(choice-1).getState()== 1? "已维修":"未维修"));
        //让用户输入id
        //调用service的方法，根据id将信息存放到list中
        //显示所有消息
    }

    public void updateDormAffair(){
        //让用户输入id
        List<DormAffair> dormAffairs = showAll();
        if(dormAffairs ==null||dormAffairs.isEmpty()){
            return;
        }
        int sum = dormAffairs.size();
        System.out.print("请输入想要修改状态的报修信息的序号:\n>");

        int choice = Utils.choiceInput();

        if(choice<1||choice>sum){
            System.out.println(UserConstant.WRONG_INPUT_MSG);
            return;
        }

        System.out.println("=====报修单状态=====");
        System.out.println("1.未完成");
        System.out.println("2.已完成");
        System.out.print("请输入新的报修单状态：\n>");

        int newState = Utils.choiceInput();

        switch (newState){
            case -1:
                System.out.println(UserConstant.WRONG_INPUT_MSG);
                break;
            case 1:
                updateState(0,dormAffairs.get(choice-1).getId());
                break;
            case 2:
                updateState(1,dormAffairs.get(choice-1).getId());
                break;
            default:
                System.out.println(UserConstant.OVER_INPUT_MSG);
        }
        //调用service的方法，根据id将状态更新到list中
    }

    public void updateState(int state,int id){
        if(affairService.updateState(state,id)==1){
            System.out.println("更新成功！");
        }else{
            System.out.println(UserConstant.DB_WRONG_MSG);
        }
    }

    public void deleteDormAffair(){
        List<DormAffair> dormAffairs = showAll();
        if(dormAffairs ==null||dormAffairs.isEmpty()){
            return;
        }
        int sum = dormAffairs.size();
        System.out.print("请输入想要删除的报修信息的序号:\n>");

        int choice = Utils.choiceInput();

        if(choice<1||choice>sum){
            System.out.println("无法识别序号");
            return;
        }

        if(affairService.deleteDormAffair(dormAffairs.get(choice-1).getId())==1){
            System.out.println("删除成功！");
        }else{
            System.out.println(UserConstant.DB_WRONG_MSG);
        }

    }

    public void updatePassword(){
        //输入原密码
        System.out.print("请输入原密码\n>");
        String password = sc.nextLine();
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

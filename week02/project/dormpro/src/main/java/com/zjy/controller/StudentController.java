package com.zjy.controller;

import com.zjy.Utils.JwtUtils;
import com.zjy.mapper.DormAffairMapper;
import com.zjy.pojo.DormAffair;
import com.zjy.pojo.User;
import com.zjy.service.DormService;
import com.zjy.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final UserService userService;
    private final DormService dormService;

    public StudentController(UserService userService, DormService dormService) {
        this.userService = userService;
        this.dormService = dormService;
    }

    @PutMapping("/dorm")
    public Map<String,Object> setDorm(@RequestBody Map<String,String> map, HttpServletRequest request){
        //获取新宿舍名
        String dorm = map.get("dorm");

        //获取token，通过token获取旧user信息
        String authHeader = request.getHeader("Authorization");
        String token = JwtUtils.getTokenByHeader(authHeader);
        Map<String,Object> userInfo = JwtUtils.getUserMap(token);
        String num = (String)userInfo.get("number");

        User user = new User();
        user.setDorm(dorm);
        user.setNumber(num);
        if(userService.updateDorm(dorm,num)==1){
            Map<String,Object> newMap = new HashMap<>();
            String newToken = JwtUtils.createToken(user);
            map.put("token",newToken);
            return newMap;
        }else{
            throw new RuntimeException("数据库错误");
        }
    }

    @PostMapping("/affair")
    public void setAffair(@RequestBody Map<String,String> map, HttpServletRequest request) {
        //获取报修信息，电话号码
        String problem = map.get("problem");
        String phone = map.get("phone");
        String imgUrl = map.get("imgUrl");
        Timestamp time = new Timestamp(System.currentTimeMillis());


        //获取token，通过token获取学号
        String authHeader = request.getHeader("Authorization");
        String token = JwtUtils.getTokenByHeader(authHeader);
        Map<String, Object> userInfo = JwtUtils.getUserMap(token);
        String num = (String) userInfo.get("number");
        String dorm = (String)userInfo.get("dorm");

        DormAffair dormAffair = new DormAffair();
        dormAffair.setProblem(problem);
        dormAffair.setDorm(dorm);
        dormAffair.setNumber(num);
        dormAffair.setPhone(phone);
        dormAffair.setTime(time);
        dormAffair.setState(0);
        dormAffair.setImgUrl(imgUrl);

        //通过dormService的储存方法储存
        if (dormService.createAffair(dormAffair) != 1) {
            throw new RuntimeException("数据库存储错误");
        }
    }

    @GetMapping("/affairs")
    public List<DormAffair> getAffairs(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = JwtUtils.getTokenByHeader(authHeader);
        Map<String, Object> userInfo = JwtUtils.getUserMap(token);
        String num = (String) userInfo.get("number");

        return dormService.getDormAffair(num);
    }

    @DeleteMapping("/affair/{id}")
    public void deleteAffair(@PathVariable("id") int id) {
        if(dormService.deleteAffair(id)!=1){
            throw new RuntimeException("数据库存储错误");
        }
    }

    @PutMapping("/password")
    public void updatePassword(@RequestBody Map<String,String> map, HttpServletRequest request) {
        String password = map.get("oldPwd");
        String newPassword = map.get("newPwd");

        String authHeader = request.getHeader("Authorization");
        String token = JwtUtils.getTokenByHeader(authHeader);
        Map<String, Object> userInfo = JwtUtils.getUserMap(token);
        String number = (String)userInfo.get("number");
        String oldPassword = dormService.selectPasswordByNum(number);

        if(password.equals(oldPassword)){
            if(dormService.updatePasswordByNum(newPassword,number)!=1){
                throw new RuntimeException("数据库存储错误");
            }
        }else{
            throw new RuntimeException("旧密码错误");
        }
    }
}

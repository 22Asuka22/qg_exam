package com.zjy.controller;

import com.zjy.Utils.JwtUtils;
import com.zjy.pojo.User;
import com.zjy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody User loginuser){
        //if(loginuser.getRole()==0){
        //    if(!userService.checkStudentNumberRegex(loginuser.getNumber())){
        //        throw new RuntimeException("账号格式错误");
        //    }
        //}else if(loginuser.getRole()==1){
        //    if(!userService.checkAdminNumberRegex(loginuser.getNumber())){
        //        throw new RuntimeException("账号格式错误");
        //    }
        //}

        User user = userService.login(loginuser.getNumber(), loginuser.getPassword());
        if(user == null){
            throw new RuntimeException("账号或密码错误");
        }

        user.setPassword(null);
        Map<String,Object> map = new HashMap<>();
        String token = JwtUtils.createToken(user);
        map.put("token",token);
        return map;
    }

    @PostMapping("register")
    public void register(@RequestBody User user){
        if(userService.register(user.getNumber(), user.getPassword())!=1){
            throw new RuntimeException("数据库错误");
        }
    }
}

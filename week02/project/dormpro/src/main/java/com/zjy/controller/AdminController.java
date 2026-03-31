package com.zjy.controller;

import com.zjy.Utils.JwtUtils;
import com.zjy.pojo.DormAffair;
import com.zjy.service.DormService;
import com.zjy.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final DormService dormService;

    public AdminController(UserService userService, DormService dormService) {
        this.userService = userService;
        this.dormService = dormService;
    }

    @GetMapping("/affairs")
    public List<DormAffair> getAffairs(@RequestParam String state) {
        if(state.equals("all")){
            return dormService.selectAllDormAffair();
        }else{
            int stateInt = Integer.parseInt(state);
            return dormService.selectDormAffairByState(stateInt);
        }

    }

    @PutMapping("/affair/{id}/state")
    public void updateAffairState(@PathVariable("id") int id) {
        if(dormService.updateState(1,id)!=1){
            throw new RuntimeException("数据库存储错误");
        }
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

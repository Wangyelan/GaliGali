package com.gali.galigali.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gali.galigali.dao.User;
import com.gali.galigali.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    UserMapper userMapper;
    @RequestMapping("/login")
    public String login(String username,String password){
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getUsername,username);
        User user=userMapper.selectOne(userQueryWrapper);
        if(user==null){
            return "登陆失败";
        }
        else{
            if(user.getPassword().equals(password)){
                return "登陆成功";
            }
            else{
                return "登录失败";
            }
        }
    }
    @PostMapping("/enroll")
    public String enroll(@RequestBody User user){
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getUsername,user.getUsername());
        User user1=userMapper.selectOne(userQueryWrapper);
        if(user1!=null){
            return "注册失败";
        }
        else{
            userMapper.insert(user);
            return "注册成功";
        }
    }
}

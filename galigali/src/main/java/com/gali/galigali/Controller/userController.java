package com.gali.galigali.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gali.galigali.dao.Log;
import com.gali.galigali.dao.User;
import com.gali.galigali.mapper.LogMapper;
import com.gali.galigali.mapper.UserMapper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class userController{

    @Autowired UserMapper userMapper;
    @Autowired LogMapper logMapper;


    @RequestMapping("/login")
    public String login(String username, String password){//核验用户登录
        Log log=new Log();
        log.setUsername(username);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        log.setCreateTime(simpleDateFormat.format(new Date()));
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getUsername,username);
        User user=userMapper.selectOne(userQueryWrapper);
        if(user==null){
            log.setEvent("login failed");
            logMapper.insert(log);
            return "登陆失败";
        }
        else{
            if(user.getPassword().equals(password)){
                if(user.getState().equals("normal")) {
                    log.setEvent("login success");
                    logMapper.insert(log);
                    return "登陆成功";
                }
                else {
                    log.setEvent("Your account is frozen");
                    logMapper.insert(log);
                    return "您的帐号处于封禁状态";
                }
            }
            else{
                log.setEvent("login failed");
                logMapper.insert(log);
                return "登录失败";
            }
        }
    }
    @RequestMapping("/attend")
    public String attend(String username,String password){//核验一级管理员登录
        Log log=new Log();
        log.setUsername(username);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        log.setCreateTime(simpleDateFormat.format(new Date()));
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getUsername,username);
        User user=userMapper.selectOne(userQueryWrapper);
        if(user==null){
            log.setEvent("login failed");
            logMapper.insert(log);
            return "登陆失败";
        }
        else{
            if(user.getPassword().equals(password)){
                if(user.getRole().equals("user")){
                    log.setEvent("You donnt be passed");
                    logMapper.insert(log);
                    return "您没有此权限";
                }
                if(user.getState().equals("normal")) {
                    log.setEvent("login success");
                    logMapper.insert(log);
                    return "登陆成功";
                }
                else {
                    log.setEvent("Your account is frozen");
                    logMapper.insert(log);
                    return "您的帐号处于封禁状态";
                }
            }
            else{
                log.setEvent("login failed");
                logMapper.insert(log);
                return "登录失败";
            }
        }
    }
    @RequestMapping("/supervise")
    public String supervise(String username,String password){//核验系统管理员登录
        Log log=new Log();
        log.setUsername(username);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        log.setCreateTime(simpleDateFormat.format(new Date()));
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getUsername,username);
        User user=userMapper.selectOne(userQueryWrapper);
        if(user==null){
            log.setEvent("login failed");
            logMapper.insert(log);
            return "登陆失败";
        }
        else{
            if(user.getPassword().equals(password)){
                if(!user.getRole().equals("president")){
                    log.setEvent("You donnt be passed");
                    logMapper.insert(log);
                    return "您没有此权限";
                }
                log.setEvent("login success");
                logMapper.insert(log);
                return "登陆成功";
            }
            else{
                log.setEvent("login failed");
                logMapper.insert(log);
                return "登录失败";
            }
        }
    }
    @RequestMapping("/enroll")
    public String enroll(String username,String password){
        Log log=new Log();
        log.setUsername(username);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        log.setCreateTime(simpleDateFormat.format(new Date()));
        User user = new User(username,password);
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getUsername,user.getUsername());
        User user1=userMapper.selectOne(userQueryWrapper);
        if(user1!=null){
            log.setEvent("enroll failed");
            logMapper.insert(log);
            return "注册失败";
        }
        else{
            userMapper.insert(user);
            log.setEvent("enroll+success");
            logMapper.insert(log);
            return "注册成功";
        }
    }
    @RequestMapping("/block")
    public String block(String username){
        Log log=new Log();
        log.setUsername(username);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        log.setCreateTime(simpleDateFormat.format(new Date()));
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getUsername,username);
        User user1=userMapper.selectOne(userQueryWrapper);
        if(user1!=null){
            if(user1.getRole().equals("user")) {
                log.setEvent("freeze success");
                logMapper.insert(log);
                return "封禁成功!";
            }
            else{
                log.setEvent("you donnt be passed");
                logMapper.insert(log);
                return "您不具备此权限";
            }


        }
        else{
            log.setEvent("do error");
            logMapper.insert(log);
            return "操作异常!";
        }
    }
}

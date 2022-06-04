package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController{
    @Resource
    UserMapper userMapper;

    @PostMapping
    public Result<?> save(@RequestBody User user){
        userMapper.insert(user);
        return Result.success();
    }

    @GetMapping("/login")
    @ResponseBody
    public String login(){
        return "这是登陆界面";
    }

}

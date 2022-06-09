package com.example.controller;

import cn.hutool.db.Session;
import com.example.common.Result;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.utils.SecurityUtils;
import com.example.utils.TokenUtils;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    // 通过一个Resource注解，省去了SqlSessionFactoryBuilder,SqlSessionFactory,SqlSession
    @Resource
    UserMapper userMapper;

    @PostMapping
    public Result<?> save(@RequestBody User user) {
        userMapper.insert(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        User res = userMapper.findByName(user.getUserUsername());
        String msg = "";
        if (res == null) {
            msg = "不存在该用户名";
            return Result.error("-1", msg);
        }

        if (!SecurityUtils.matchesPassword(user.getUserPassword(), res.getUserPassword())) {
            msg = "输入密码错误";
            return Result.error("-1", msg);
        }

        // 生成token
        String token = TokenUtils.genToken(res);
        res.setToken(token);
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        String psd = user.getUserPassword();
        user.setUserPassword(SecurityUtils.encodePassword(psd));
        userMapper.insert(user);
        return Result.success();
    }

    //@PostMapping("/logout")

//    @GetMapping("/show")
//    @ResponseBody
//    public String show() {
//        User user = userMapper.selectById(1);
//        return user + "";
//    }
}

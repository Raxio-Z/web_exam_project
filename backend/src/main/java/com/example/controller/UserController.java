package com.example.controller;

import cn.hutool.db.Session;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.utils.SecurityUtils;
import com.example.utils.TokenUtils;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    // 通过一个Resource注解，省去了SqlSessionFactoryBuilder,SqlSessionFactory,SqlSession
    @Resource
    UserMapper userMapper;


    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_username",user.getUserUsername());
        User res = userMapper.selectOne(wrapper);
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
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_username",user.getUserUsername());
        User res = userMapper.selectOne(wrapper);

        String msg= "";
        if(res!=null)
        {
            msg = "用户名已经存在";
            return Result.error("-1",msg);
        }
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

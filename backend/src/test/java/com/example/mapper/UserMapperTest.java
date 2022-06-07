package com.example.mapper;

import com.example.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Autowired
    UserMapper userMapper;


    @Test
    void test1(){
        User user = new User();
        user.setUserEmail("2923784829@qq.com");
        user.setUserPassword("123");
        userMapper.insert(user);
    }

}
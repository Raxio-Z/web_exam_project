package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    // 使用注解式开发，省去了配置mapper.xml文件，关联类的过程，在mybatis中一般叫做repository
}

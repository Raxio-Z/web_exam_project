package com.example.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

// 使用component注入spring中
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("examCreateDate", new Date(), metaObject);
        this.setFieldValByName("examUpdateDate", new Date(), metaObject);
        this.setFieldValByName("questionCreateDate", new Date(), metaObject);
        this.setFieldValByName("questionUpdateDate", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("examUpdateDate", new Date(), metaObject);
        this.setFieldValByName("questionUpdateDate", new Date(), metaObject);
    }
}

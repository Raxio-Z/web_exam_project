package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    // 自增
    @TableId(type= IdType.AUTO)
    private Integer userId;

    private String userUsername;
    private String userNickname;
    private String userPassword;
    private Integer userRoleId;
    private String userAvatar;
    private String userDescription;
    private String userEmail;
    private String userPhone;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userUsername='" + userUsername + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRoleId=" + userRoleId +
                ", userAvatar='" + userAvatar + '\'' +
                ", userDescription='" + userDescription + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}

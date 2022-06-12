package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "question_level")
public class QuestionLevel {
    @TableId(value="id",type = IdType.AUTO)
    private Integer questionLevelId;

    @TableField(value="name")
    private String questionLevelName;

    @TableField(value="description")
    private String questionLevelDescription;
}

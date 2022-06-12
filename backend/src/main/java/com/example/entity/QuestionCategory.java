package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="question_category")
public class QuestionCategory {
    @TableId(value="id",type = IdType.AUTO)
    private Integer questionCategoryId;

    @TableField(value="name")
    private String questionCategoryName;

    @TableField(value="description")
    private String questionCategoryDescription;
}

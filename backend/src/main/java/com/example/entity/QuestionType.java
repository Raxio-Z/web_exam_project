package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="question_type")
public class QuestionType {
    @TableId(value="id",type= IdType.AUTO)
    private Integer questionTypeId;

    @TableField(value="type")
    private String questionTypeDescription;
}

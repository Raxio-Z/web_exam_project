package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "question_option")
public class QuestionOption {
    @TableId(value="id",type= IdType.AUTO)
    private Integer questionOptionId;

    @TableField(value="content")
    private String questionOptionContent;

    @TableField(value="description")
    private String questionOptionDescription;
}

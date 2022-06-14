package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class QuestionType {
    @TableId(value="id",type= IdType.AUTO)
    private Integer questionTypeId;

    @TableField(value="type")
    private Integer questionTypeDescription;
}

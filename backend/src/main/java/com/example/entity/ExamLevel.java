package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="exam_level")
public class ExamLevel {
    @TableId(value="id",type= IdType.AUTO)
    private Integer examLevelId;

    @TableField(value="name")
    private String examLevelName;

    @TableField(value="description")
    private String examLevelDescription;
}

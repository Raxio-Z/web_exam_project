package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="exam_category")
public class ExamCategory {
    @TableId(value="id",type =IdType.AUTO)
    private Integer examCategoryId;

    @TableField(value="name")
    private String examCategoryName;

    @TableField(value="description")
    private String examCategoryDescription;
}

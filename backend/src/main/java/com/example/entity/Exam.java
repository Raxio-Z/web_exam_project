package com.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@TableName(value = "exam")
@Data
public class Exam {
    @TableId(value="id",type= IdType.AUTO)
    private Integer examId;

    @TableField(value= "name")
    private String examName;

    @TableField(value = "category_id")
    private String examCategoryId;

    @TableField(value = "level_id")
    private String examLevelId;

    @TableField(value = "score")
    private Integer examScore;

    @TableField(value="time_limit")
    private Integer examTimeLimit;

    @TableField(value = "description")
    private String examDescription;

    // 存储该考试包含的所有问题的id，使用'_'分隔
    @TableField(value="question_ids")
    private String examQuestionIds;

    @TableField(value="create_date",fill= FieldFill.INSERT)
    private Date examCreateDate;

    @TableField(value="update_date",fill=FieldFill.INSERT_UPDATE)
    private Date examUpdateDate;


    // 选题分值
}

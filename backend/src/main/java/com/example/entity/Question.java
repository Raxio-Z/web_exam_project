package com.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;


@Data
@TableName(value="question")
public class Question {
    @TableId(value="id",type= IdType.AUTO)
    private Integer questionId;

    @TableField(value="name")
    private String questionName;

//    @TableField(value="score")
//    private Integer questionScore;

    @TableField(value="level_id")
    private Integer questionLevelId;

    @TableField(value="category_id")
    private Integer questionCategoryId;

    @TableField(value="creator_id")
    private Integer questionCreatorId;

    @TableField(value="option_ids")
    private String questionOptionIds;

    @TableField(value="type_id")
    private Integer questionTypeId;

    @TableField(value="description")
    private String questionDescription;

    @TableField(value="create_date",fill= FieldFill.INSERT)
    private Date questionCreateDate;

    @TableField(value="update_date",fill = FieldFill.INSERT_UPDATE)
    private Date questionUpdateDate;
    // 种类

}

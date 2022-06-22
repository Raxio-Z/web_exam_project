package com.example.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "exam_record")
public class ExamRecord {
    @TableId(type = IdType.AUTO,value="id")
    private Integer examRecordId;

    @TableField(value= "joiner_id")
    private Integer examRecordJoinerId;

    @TableField(value = "exam_id")
    private Integer examRecordExamId;

    @TableField(value = "score")
    private Integer examRecordScore;
}

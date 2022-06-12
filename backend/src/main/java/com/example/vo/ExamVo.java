package com.example.vo;

import lombok.Data;

@Data
public class ExamVo {
    private Integer serial;
    private String name;
    private String subject;
    private String difficulty;
    private Integer score;
    private Integer duration;
}

package com.example.vo;


import lombok.Data;

import java.util.List;

@Data
public class ExamCreateVo {
    private Integer radioScore;

    private Integer checkScore;

    private Integer judgeScore;

    private List<QuestionSelectVo> radios;

    private List<QuestionSelectVo> checks;

    private List<QuestionSelectVo> judges;

    private String name;

    private Integer subject;

    // 考试时间
    private Integer elapse;

    // 难度
    private Integer levels;


    public Integer getLevels() {
        if (levels == null)
            return 2;
        return levels;
    }


}

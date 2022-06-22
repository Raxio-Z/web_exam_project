package com.example.vo;

import lombok.Data;

import java.util.List;

@Data
public class ExamDetailVo {
    private ExamVo exam;
    private List<Integer> radioIds;
    private List<Integer> checkIds;
    private List<Integer> judgeIds;

}

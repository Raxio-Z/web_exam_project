package com.example.vo;


import com.example.entity.QuestionOption;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QuestionVo {
    Integer serial;
    String name;
    String subject;
    String difficulty;
    String category;

    List<QuestionOption> options;

    List<QuestionOption> answers;
}

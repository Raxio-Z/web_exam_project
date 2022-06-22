package com.example.vo;

import com.example.entity.QuestionOption;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDetailVo {
    private String type;

    private String name;

    private List<QuestionOption> options;
}

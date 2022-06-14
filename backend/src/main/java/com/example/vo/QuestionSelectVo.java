package com.example.vo;

import lombok.Data;

@Data
public class QuestionSelectVo {
    private Integer id;

    private String name;

    private String type;

    // 标记该选题是否被选中，默认为false
    private Boolean checked = false;
}

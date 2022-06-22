package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Question;
import org.apache.ibatis.annotations.Select;

public interface QuestionMapper extends BaseMapper<Question> {

    @Select("select type from question_type where id = #{id}")
    String findTypeById(Integer id);
}

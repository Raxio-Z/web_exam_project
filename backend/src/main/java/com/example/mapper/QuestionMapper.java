package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Question;
import com.example.vo.ExamVo;
import com.example.vo.QuestionVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface QuestionMapper extends BaseMapper<Question> {

    @Select("select type from question_type where id = #{id}")
    String findTypeById(Integer id);


    @Select("select q.id as `serial`,q.`name` as `name`,ql.`name` as difficulty,qc.`name` as `subject`,qt.`type` as `category` from question q,question_level ql,question_type qt,question_category qc where q.level_id = ql.id and q.category_id = qc.id and q.level_id = ql.id and q.type_id = qt.id")
    List<QuestionVo> findAllQuestionVo();

    @Select("select MAX(id) from question_option")
    Integer findMaxId();

    @Select("select q.id as `serial`,q.`name` as `name`,ql.`name` as difficulty,qc.`name` as `subject`,qt.`type` as `category` from question q,question_level ql,question_type qt,question_category qc where q.level_id = ql.id and q.category_id = qc.id and q.level_id = ql.id and q.type_id = qt.id and q.`name` like CONCAT('%',#{key},'%')")
    List<QuestionVo> findQuestionVosByKey(String key);
}

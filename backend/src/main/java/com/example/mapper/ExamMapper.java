package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Exam;
import com.example.vo.ExamVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ExamMapper extends BaseMapper<Exam> {
    @Select("select e.id as serial,e.`name` as `name`,ec.`name` as `subject`,el.`name` as difficulty,e.score as score,e.time_limit as duration from exam e,exam_category ec,exam_level el where e.category_id = ec.id and e.level_id = el.id;")
    List<ExamVo> findAllExamVos();
}

package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.ExamRecord;
import org.apache.ibatis.annotations.Update;

public interface ExamRecordMapper extends BaseMapper<ExamRecord> {

    @Update("update exam_record set score = #{score} where joiner_id = #{joinerId} and exam_id = #{examId}")
    void updateScore(Integer score,Integer joinerId,Integer examId);
}

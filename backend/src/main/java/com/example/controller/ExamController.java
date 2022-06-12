package com.example.controller;



import com.example.common.Result;
import com.example.entity.Exam;
import com.example.mapper.ExamMapper;
import com.example.vo.ExamVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    ExamMapper examMapper;

    @GetMapping("/all")
    Result<?> getExamAll() {
        List<ExamVo> examVos;
        Result<?> res;
        try {
            examVos = examMapper.findAllExamVos();
            res = Result.success(examVos);
        } catch (Exception e){
            res = Result.error("-1","获取考试列表失败");
            e.printStackTrace();
        }
        return res;
    }

}











package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.entity.Exam;
import com.example.entity.ExamCategory;
import com.example.entity.QuestionLevel;
import com.example.mapper.ExamMapper;
import com.example.vo.ExamCreateVo;
import com.example.vo.ExamVo;
import com.example.vo.QuestionSelectVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        } catch (Exception e) {
            res = Result.error("-1", "获取考试列表失败");
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping("/question/select")
    Result<?> getQuestionSelect() {
        List<ExamCategory> categories = null;
        List<QuestionSelectVo> radios = null;
        List<QuestionSelectVo> checks = null;
        List<QuestionSelectVo> judges = null;
        List<QuestionLevel> levels = null;
        Map<String, Object> data = new HashMap<>();
        Result<?> res;
        try {
            radios = examMapper.findQuestionsByTypeId(1);
            checks = examMapper.findQuestionsByTypeId(2);
            judges = examMapper.findQuestionsByTypeId(3);
            categories = examMapper.findAllExamCategories();
            levels = examMapper.findAllQuestionLevels();
            data.put("radios", radios);
            data.put("checks", checks);
            data.put("judges", judges);
            data.put("categories", categories);
            data.put("levels", levels);
            res = Result.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            res = Result.error("-1", "问题获取失败");
        }
        return res;
    }

    @PostMapping("/create")
    Result<?> ExamCreate(@RequestBody ExamCreateVo createVo) {
        Exam exam = new Exam();
        exam.setExamCategoryId(createVo.getSubject());
        exam.setExamCheckPoints(createVo.getCheckScore());
        exam.setExamJudgePoints(createVo.getJudgeScore());
        exam.setExamRadioPoints(createVo.getRadioScore());
        exam.setExamTimeLimit(createVo.getElapse());
        exam.setExamName(createVo.getName());
        exam.setExamLevelId(createVo.getLevel());

        List<QuestionSelectVo> questions = new ArrayList<>();
        questions.addAll(createVo.getChecks());
        questions.addAll(createVo.getJudges());
        questions.addAll(createVo.getRadios());


        StringBuilder questionIds = new StringBuilder();
        boolean flag = true;
        int score = 0;
        Integer radioPoints = exam.getExamRadioPoints();
        Integer checkPoints = exam.getExamCheckPoints();
        Integer judgePoints = exam.getExamJudgePoints();
        for (QuestionSelectVo t : questions) {
            if (t.getChecked()) {
                switch (t.getType()) {
                    case "radio":
                        score += radioPoints;
                        break;
                    case "check":
                        score += checkPoints;
                        break;
                    case "judge":
                        score += judgePoints;
                        break;
                }
                if (flag) {
                    questionIds.append(t.getId());
                    flag = false;
                } else
                    questionIds.append("-").append(t.getId());
            }
        }
        exam.setExamQuestionIds(questionIds.toString());
        exam.setExamScore(score);
        try {
            examMapper.insert(exam);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "创建考试失败");
        }

    }

}











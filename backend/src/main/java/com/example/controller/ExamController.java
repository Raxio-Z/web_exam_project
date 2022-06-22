package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.entity.Exam;
import com.example.entity.ExamCategory;
import com.example.entity.Question;
import com.example.entity.QuestionLevel;
import com.example.mapper.ExamMapper;
import com.example.mapper.QuestionMapper;
import com.example.utils.TokenUtils;
import com.example.vo.ExamCreateVo;
import com.example.vo.ExamDetailVo;
import com.example.vo.ExamVo;
import com.example.vo.QuestionSelectVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    ExamMapper examMapper;
    @Resource
    QuestionMapper questionMapper;

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


        StringBuilder radioIds = new StringBuilder();
        StringBuilder checkIds = new StringBuilder();
        StringBuilder judgeIds = new StringBuilder();

        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        int score = 0;
        Integer radioPoints = exam.getExamRadioPoints();
        Integer checkPoints = exam.getExamCheckPoints();
        Integer judgePoints = exam.getExamJudgePoints();
        for (QuestionSelectVo t : questions) {
            if (t.getChecked()) {
                switch (t.getType()) {
                    case "radio":
                        score += radioPoints;
                        if (flag1) {
                            radioIds.append(t.getId());
                            flag1 = false;
                        } else
                            radioIds.append("-").append(t.getId());
                        break;
                    case "check":
                        score += checkPoints;
                        if (flag2) {
                            checkIds.append(t.getId());
                            flag2 = false;
                        } else
                            checkIds.append("-").append(t.getId());
                        break;
                    case "judge":
                        score += judgePoints;
                        if (flag3) {
                            judgeIds.append(t.getId());
                            flag3 = false;
                        } else
                            judgeIds.append("-").append(t.getId());
                        break;
                }
            }
        }
        exam.setExamRadioIds(radioIds.toString());
        exam.setExamCheckIds(checkIds.toString());
        exam.setExamJudgeIds(judgeIds.toString());

        exam.setExamScore(score);
        try {
            examMapper.insert(exam);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "创建考试失败");
        }

    }

    @GetMapping("/my")
    Result<?> getMyExam()
    {
        Integer Id = TokenUtils.getUserId();

        return Result.success();
    }

    @PostMapping ("/selection")
    Result<?> getSelectionDetail(@RequestBody Integer questionId)
    {
        Question question = questionMapper.selectById(questionId);
        return Result.success();
    }

    @PostMapping("/detail")
    Result<?> getExamDetail(@RequestBody Integer examId)
    {
        ExamDetailVo examDetailVo = new ExamDetailVo();
        ExamVo examVo = new ExamVo();

        try {
            Exam exam = examMapper.selectById(examId);
            examVo.setSerial(examId);
            examVo.setName(exam.getExamName());
            examVo.setDifficulty(examMapper.findLevelById(exam.getExamLevelId()));
            examVo.setDuration(exam.getExamTimeLimit());
            examVo.setCheckScore(exam.getExamCheckPoints());
            examVo.setRadioScore(exam.getExamRadioPoints());
            examVo.setJudgeScore(exam.getExamJudgePoints());

            examDetailVo.setRadioIds(Stream.of(exam.getExamRadioIds().split("-")).map(Integer::parseInt).collect(Collectors.toList()));
            examDetailVo.setCheckIds(Stream.of(exam.getExamCheckIds().split("-")).map(Integer::parseInt).collect(Collectors.toList()));
            examDetailVo.setJudgeIds(Stream.of(exam.getExamJudgeIds().split("-")).map(Integer::parseInt).collect(Collectors.toList()));
            examDetailVo.setExam(examVo);

            return Result.success(examDetailVo);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("-1","获取考试详情失败");
        }







    }


}











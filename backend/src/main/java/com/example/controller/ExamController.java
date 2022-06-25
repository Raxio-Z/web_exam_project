package com.example.controller;


import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.entity.*;
import com.example.mapper.ExamMapper;
import com.example.mapper.ExamRecordMapper;
import com.example.mapper.QuestionMapper;
import com.example.mapper.QuestionOptionMapper;
import com.example.utils.DecoderUtils;
import com.example.utils.TokenUtils;
import com.example.vo.*;
import org.apache.commons.collections.ListUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.print.attribute.ResolutionSyntax;
import java.util.*;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    ExamMapper examMapper;
    @Resource
    QuestionMapper questionMapper;

    @Resource
    QuestionOptionMapper questionOptionMapper;

    @Resource
    ExamRecordMapper examRecordMapper;

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
                    case "单选题":
                        score += radioPoints;
                        if (flag1) {
                            radioIds.append(t.getId());
                            flag1 = false;
                        } else
                            radioIds.append("-").append(t.getId());
                        break;
                    case "多选题":
                        score += checkPoints;
                        if (flag2) {
                            checkIds.append(t.getId());
                            flag2 = false;
                        } else
                            checkIds.append("-").append(t.getId());
                        break;
                    case "判断题":
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
    Result<?> getMyExam() {
        // 1. 获取userId
        // 2. 根据userId关联查询exam表中的数据
        try {
            Integer id = TokenUtils.getUserId();
            List<ExamVo> examVos = examMapper.findMyExamVosById(id);
            return Result.success(examVos);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "获取我的考试失败");
        }
    }

    @PostMapping("/selection")
    Result<?> getSelectionDetail(@RequestBody Integer questionId) {
        try {
            Question question = questionMapper.selectById(questionId);
            QuestionDetailVo questionDetailVo = new QuestionDetailVo();
            questionDetailVo.setName(question.getQuestionName());
            questionDetailVo.setType(questionMapper.findTypeById(question.getQuestionTypeId()));
            List<Integer> optionIds = DecoderUtils.decodeIds(question.getQuestionOptionIds());
            List<QuestionOption> options = questionOptionMapper.selectBatchIds(optionIds);
            questionDetailVo.setOptions(options);
            questionDetailVo.setQuestionId(questionId);
            return Result.success(questionDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "获取题目详情失败");
        }


    }

    @PostMapping("/detail")
    Result<?> getExamDetail(@RequestBody Integer examId) {
        ExamDetailVo examDetailVo = new ExamDetailVo();
        ExamVo examVo = new ExamVo();

        try {
            Exam exam = examMapper.selectById(examId);
            examVo.setSerial(examId);
            examVo.setName(exam.getExamName());
            examVo.setDifficulty(examMapper.findLevelById(exam.getExamLevelId()));
            String subject = examMapper.findCategoryById(exam.getExamCategoryId());
            examVo.setSubject(subject);
            examVo.setScore(exam.getExamScore());
            examVo.setDuration(exam.getExamTimeLimit());
            examVo.setCheckScore(exam.getExamCheckPoints());
            examVo.setRadioScore(exam.getExamRadioPoints());
            examVo.setJudgeScore(exam.getExamJudgePoints());
            if (!exam.getExamRadioIds().isEmpty())
                examDetailVo.setRadioIds(DecoderUtils.decodeIds(exam.getExamRadioIds()));
            if (!exam.getExamCheckIds().isEmpty())
                examDetailVo.setCheckIds(DecoderUtils.decodeIds(exam.getExamCheckIds()));
            if (!exam.getExamJudgeIds().isEmpty())
                examDetailVo.setJudgeIds(DecoderUtils.decodeIds(exam.getExamJudgeIds()));
            examDetailVo.setExam(examVo);

            return Result.success(examDetailVo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "获取考试详情失败");
        }
    }


    @PostMapping("/submit/{examId}")
    Result<?> ExamSubmit(@PathVariable(name = "examId") Integer examId, @RequestBody Map<Integer, List<?>> answer) {
        // 1. 根据examId拿到题目分值
        // 2. 根据answer中的questionId找到该问题对应的answerId
        // 3. 根据answerId与选择的值进行比较计算分数
        try {
            Exam exam = examMapper.selectById(examId);
            Integer radioPoints = exam.getExamRadioPoints();
            Integer checkPoints = exam.getExamCheckPoints();
            Integer judgePoints = exam.getExamJudgePoints();

            Map<Integer, Integer> points = new HashMap<>();
            points.put(1, radioPoints);
            points.put(2, checkPoints);
            points.put(3, judgePoints);
            Integer scores = 0;
            for (Map.Entry<Integer, List<?>> entry : answer.entrySet()) {
                Question question = questionMapper.selectById(entry.getKey());
                String answerIds = question.getQuestionOptionAnswerIds();
                List<Integer> ansIds = DecoderUtils.decodeIds(answerIds);
                if (ListUtils.isEqualList(ansIds, entry.getValue()))
                    scores += points.get(question.getQuestionTypeId());
            }
            ExamRecord record = new ExamRecord();
            record.setExamRecordExamId(examId);
            Integer userId = TokenUtils.getUserId();
            record.setExamRecordJoinerId(userId);
            record.setExamRecordScore(scores);

            Map<String, Object> req = new HashMap<>();
            req.put("joiner_id", userId);
            req.put("exam_id", examId);
            if (examRecordMapper.selectByMap(req).isEmpty())
                examRecordMapper.insert(record);
            else
                examRecordMapper.updateScore(record.getExamRecordScore(), record.getExamRecordJoinerId(), record.getExamRecordExamId());

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "交卷失败");
        }


    }

    @PostMapping("/search")
    Result<?> ExamFilter(@RequestBody String key) {
        List<ExamVo> examVos;
        Result<?> res;
        try {
            examVos = examMapper.findExamVosByKey(key);
            res = Result.success(examVos);
        } catch (Exception e) {
            res = Result.error("-1", "获取考试列表失败");
            e.printStackTrace();
        }
        return res;
    }
}











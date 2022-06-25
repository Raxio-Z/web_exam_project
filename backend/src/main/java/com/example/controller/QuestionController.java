package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.entity.Exam;
import com.example.entity.Question;
import com.example.entity.QuestionOption;
import com.example.mapper.QuestionMapper;
import com.example.mapper.QuestionOptionMapper;
import com.example.utils.DecoderUtils;
import com.example.vo.ExamVo;
import com.example.vo.QuestionVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    QuestionMapper questionMapper;

    @Resource
    QuestionOptionMapper questionOptionMapper;

    @GetMapping("/all")
    Result<?> findAllQuestion() {
        try {
            List<QuestionVo> questionVos = questionMapper.findAllQuestionVo();
            List<Question> questions = questionMapper.selectList(new QueryWrapper<Question>());
            for (int i = 0; i < questions.size(); i++) {
                //Map<String,Object> t1 = new HashMap<>();

                List<Integer> optionIds = DecoderUtils.decodeIds(questions.get(i).getQuestionOptionIds());
                List<Integer> answerIds = DecoderUtils.decodeIds(questions.get(i).getQuestionOptionAnswerIds());

                List<QuestionOption> options = questionOptionMapper.selectBatchIds(optionIds);
                List<QuestionOption> answers = questionOptionMapper.selectBatchIds(answerIds);

                QuestionVo t = questionVos.get(i);
                t.setOptions(options);
                t.setAnswers(answers);
            }
            return Result.success(questionVos);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("-1", "问题信息获取失败");
        }
    }

    @PostMapping("/create")
    Result<?> questionCreate(@RequestBody Map<String, Object> question) {
        Question res = new Question();
        res.setQuestionName((String) question.get("name"));
        res.setQuestionTypeId((Integer) question.get("type"));
        res.setQuestionLevelId((Integer) question.get("level"));
        res.setQuestionCategoryId((Integer) question.get("subject"));

        List<Map<String, Object>> getOptions = (List<Map<String, Object>>) question.get("options");
        Integer maxId = questionMapper.findMaxId();
        StringBuilder optionIds = new StringBuilder();
        StringBuilder answerIds = new StringBuilder();

        for (int i = 0; i < getOptions.size(); i++) {
            QuestionOption temp = new QuestionOption();
            temp.setQuestionOptionContent((String) getOptions.get(i).get("content"));
            questionOptionMapper.insert(temp);
            if (i == 0) {
                optionIds.append(maxId + i + 1);
            } else {
                optionIds.append("-").append(maxId + i + 1);
            }

            if ((Boolean) getOptions.get(i).get("answer")) {
                if (answerIds.toString().isEmpty())
                    answerIds.append(maxId + i + 1);
                else
                    answerIds.append("-").append(maxId + i + 1);
            }
        }

        res.setQuestionOptionIds(optionIds.toString());
        res.setQuestionOptionAnswerIds(answerIds.toString());

        questionMapper.insert(res);
        return Result.success();
    }

    @PostMapping("/search")
    Result<?> questionFilter(@RequestBody String key) {
        List<QuestionVo> questionVos;
        Result<?> res;
        try {
            questionVos = questionMapper.findQuestionVosByKey(key);
            res = Result.success(questionVos);
        } catch (Exception e) {
            res = Result.error("-1", "获取考试列表失败");
            e.printStackTrace();
        }
        return res;
    }

}

package com.ningf.ningfoj.judge.strategy;

import com.ningf.ningfoj.model.dto.question.JudgeCase;
import com.ningf.ningfoj.model.dto.questionsubmit.JudgeInfo;
import com.ningf.ningfoj.model.entity.Question;
import com.ningf.ningfoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @description: 用于定义所需传递的参数
 * @author: Lenovo
 * @time: 2024/11/12 上午9:34
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private Question question;
    private List<JudgeCase> judgeCases;
    private QuestionSubmit questionSubmit;
}

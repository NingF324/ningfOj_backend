package com.ningf.ningfoj.judge;

import cn.hutool.json.JSONUtil;
import com.ningf.ningfoj.common.ErrorCode;
import com.ningf.ningfoj.exception.BusinessException;
import com.ningf.ningfoj.judge.codeSandBox.CodeSandBox;
import com.ningf.ningfoj.judge.codeSandBox.CodeSandBoxFactory;
import com.ningf.ningfoj.judge.codeSandBox.CodeSandBoxProxy;
import com.ningf.ningfoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ningf.ningfoj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.ningf.ningfoj.judge.strategy.JudgeContext;
import com.ningf.ningfoj.model.dto.question.JudgeCase;
import com.ningf.ningfoj.judge.codeSandBox.model.JudgeInfo;
import com.ningf.ningfoj.model.entity.Question;
import com.ningf.ningfoj.model.entity.QuestionSubmit;
import com.ningf.ningfoj.model.enums.QuestionSubmitStatusEnum;
import com.ningf.ningfoj.service.QuestionService;
import com.ningf.ningfoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/12 上午8:40
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Value("${codeSandBox.type:example}")
    private String type;

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        if(!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目正在判题中");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        Boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新错误");
        }

        //调用代码沙箱，获取执行结果
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        List<JudgeCase> judgeCases = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCases.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);

        List<String> outputList = executeCodeResponse.getOutputList();
        //根据沙箱执行结果判断是否正确
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCases(judgeCases);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        //修改数据库中判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}

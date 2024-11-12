package com.ningf.ningfoj.judge.codeSandBox.impl;

import com.ningf.ningfoj.judge.codeSandBox.CodeSandBox;
import com.ningf.ningfoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ningf.ningfoj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.ningf.ningfoj.model.dto.questionsubmit.JudgeInfo;
import com.ningf.ningfoj.model.enums.JudgeInfoMessageEnum;
import com.ningf.ningfoj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * @description:业务流程示例
 * @author: Lenovo
 * @time: 2024/11/11 下午9:38
 */
public class ExampleCodeSandBox implements CodeSandBox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTimeLimit(100L);
        judgeInfo.setMemoryLimit(100L);
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());

        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}

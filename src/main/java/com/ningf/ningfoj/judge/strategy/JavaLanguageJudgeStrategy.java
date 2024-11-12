package com.ningf.ningfoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.ningf.ningfoj.model.dto.question.JudgeCase;
import com.ningf.ningfoj.model.dto.question.JudgeConfig;
import com.ningf.ningfoj.model.dto.questionsubmit.JudgeInfo;
import com.ningf.ningfoj.model.entity.Question;
import com.ningf.ningfoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/12 下午7:10
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        long timeLimit = judgeInfo.getTimeLimit();
        long memoryLimit = judgeInfo.getMemoryLimit();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCases = judgeContext.getJudgeCases();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTimeLimit(timeLimit);
        judgeInfoResponse.setMemoryLimit(memoryLimit);
        if(outputList.size()!= inputList.size()){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        for (int i = 0; i < judgeCases.size(); i++) {
            JudgeCase judgeCase = judgeCases.get(i);
            if(!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum =JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        //判断题目限制是否满足
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr,JudgeConfig.class);
        //java需额外执行10秒
        final long JAVA_TIME_COST = 10000L;
        long needMemoryLimit = judgeConfig.getMemoryLimit();
        long needTimeLimit = judgeConfig.getTimeLimit();
        if(memoryLimit>needMemoryLimit) {
            judgeInfoMessageEnum =JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if((timeLimit-JAVA_TIME_COST)>needTimeLimit) {
            judgeInfoMessageEnum =JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}

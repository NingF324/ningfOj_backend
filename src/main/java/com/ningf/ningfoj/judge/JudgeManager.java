package com.ningf.ningfoj.judge;

import com.ningf.ningfoj.judge.strategy.DefaultJudgeStrategy;
import com.ningf.ningfoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.ningf.ningfoj.judge.strategy.JudgeContext;
import com.ningf.ningfoj.judge.strategy.JudgeStrategy;
import com.ningf.ningfoj.judge.codeSandBox.model.JudgeInfo;
import com.ningf.ningfoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @description: 判题管理
 * @author: Lenovo
 * @time: 2024/11/12 下午7:38
 */

@Service
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(language)) {
            judgeStrategy= new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}

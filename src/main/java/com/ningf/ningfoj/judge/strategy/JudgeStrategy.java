package com.ningf.ningfoj.judge.strategy;

import com.ningf.ningfoj.judge.codeSandBox.model.JudgeInfo;

public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}

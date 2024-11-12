package com.ningf.ningfoj.judge;

import com.ningf.ningfoj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.ningf.ningfoj.model.entity.QuestionSubmit;
import com.ningf.ningfoj.model.vo.QuestionSubmitVO;

public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}

package com.ningf.ningfoj.judge.codeSandBox;

import com.ningf.ningfoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ningf.ningfoj.judge.codeSandBox.model.ExecuteCodeResponse;

public interface CodeSandBox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}

package com.ningf.ningfoj.judge.codeSandBox.impl;

import com.ningf.ningfoj.judge.codeSandBox.CodeSandBox;
import com.ningf.ningfoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.ningf.ningfoj.judge.codeSandBox.model.ExecuteCodeResponse;

/**
 * @description:实际调用接口的沙箱
 * @author: Lenovo
 * @time: 2024/11/11 下午9:38
 */
public class RemoteCodeSandBox implements CodeSandBox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("remote");
        return null;
    }
}

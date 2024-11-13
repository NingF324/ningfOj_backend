package com.ningf.ningfoj.judge.codeSandBox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/11 下午5:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    private List<String> outputList;
    private String message;
    private Integer status;
    private JudgeInfo judgeInfo;
}

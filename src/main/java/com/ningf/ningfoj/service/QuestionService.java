package com.ningf.ningfoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ningf.ningfoj.model.dto.question.QuestionQueryRequest;
import com.ningf.ningfoj.model.entity.Question;
import com.ningf.ningfoj.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ningf.ningfoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Lenovo
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-10-14 09:36:28
*/
public interface QuestionService extends IService<Question> {
    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取问题封装
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取问题封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

}
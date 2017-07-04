package cn.ucai.jkbd.biz;

import cn.ucai.jkbd.bean.Question;

/**
 * Created by gg on 2017/7/2.
 */

public interface IExambiz {
    void beginExam();
    Question getQuestion();
    Question nextQuestion();
    Question preQuestion();
    String getQuestionindex();
    int commitExam();
}

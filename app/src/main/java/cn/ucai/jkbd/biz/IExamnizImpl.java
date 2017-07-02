package cn.ucai.jkbd.biz;

import cn.ucai.jkbd.dao.IExamdao;
import cn.ucai.jkbd.dao.IExamdaoImpl;

/**
 * Created by gg on 2017/7/2.
 */

public class IExamnizImpl implements  IExambiz {
    IExamdao dao;
    public IExamnizImpl()
    {
        this.dao= new IExamdaoImpl();
    }
    @Override
    public void beginExam() {
           dao.loadIExamdaoInfo();
        dao.loadQuestionList();
    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}

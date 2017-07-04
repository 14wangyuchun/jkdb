package cn.ucai.jkbd.biz;

import java.util.List;

import cn.ucai.jkbd.ExamApplication;
import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.dao.IExamdao;
import cn.ucai.jkbd.dao.IExamdaoImpl;

/**
 * Created by gg on 2017/7/2.
 */

public class IExamnizImpl implements  IExambiz {
    IExamdao dao;
    int QuestionIndex =0;
     List<Question>Examlist =null;
    public IExamnizImpl()
    {
        this.dao= new IExamdaoImpl();
    }
    @Override
    public void beginExam() {

        QuestionIndex=0;
           dao.loadIExamdaoInfo();
        dao.loadQuestionList();

    }

    @Override
    public Question getQuestion() {
        Examlist=ExamApplication.getInstance().getmExamList();
        if(Examlist!=null)
        {
            return Examlist.get(QuestionIndex);
        }
        else
        {
            return null;
        }

    }

    @Override
    public Question nextQuestion() {
        if(Examlist!=null && QuestionIndex<Examlist.size()-1)
        {QuestionIndex++;
            return Examlist.get(QuestionIndex);
        }
        else
        {
            return null;
        }
    }

    @Override
    public Question preQuestion() {
        if(Examlist!=null && QuestionIndex>0)
        {QuestionIndex--;
            return Examlist.get(QuestionIndex);
        }
        else
        {
            return null;
        }
    }

    @Override
    public String getQuestionindex() {
        return (QuestionIndex+1+".");
    }

    @Override
    public int commitExam() {
        int s = 0;
        for (Question exam :Examlist)
        {
            String useransewer = exam.getUseranswer();
            if(useransewer!=null && ! useransewer.equals(""))
            {
                if(exam.getAnswer().equals(useransewer))
                {
                    s++;
                }
            }
        }
      return s;
    }
}

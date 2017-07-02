package cn.ucai.jkbd;
import android.app.Application;
import java.util.List;
import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.bean.item;
import cn.ucai.jkbd.biz.IExambiz;



/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    item mExamInfo;
   public static  String LOAD_INFO="load_info";
    public static  String LOAD_IQUESTION="load_question";
    public static  String LOAD_SUCCESS="load_success";
    public item getmExamInfo() {
        return mExamInfo;
    }

    public void setmExamInfo(item mExamInfo) {
        this.mExamInfo = mExamInfo;
    }

    public List<Question> getmExamList() {
        return mExamList;
    }

    public void setmExamList(List<Question> mExamList) {
        this.mExamList = mExamList;
    }

    List<Question> mExamList;
    private static ExamApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance=this;
    }

    public static ExamApplication getInstance(){
        return instance;
    }


}

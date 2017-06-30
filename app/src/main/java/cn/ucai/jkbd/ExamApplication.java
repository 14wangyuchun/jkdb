package cn.ucai.jkbd;

import android.app.Application;
import android.database.CursorJoiner;
import android.util.Log;
import java.util.List;
import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.bean.item;
import cn.ucai.jkbd.bean.result;
import cn.ucai.jkbd.utils.OkHttpUtils;
import cn.ucai.jkbd.utils.ResultUtils;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    item mExamInfo;

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

        initData();
    }

    public static ExamApplication getInstance(){
        return instance;
    }


    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils<item> utils=new OkHttpUtils<>(instance);
                String uri="http://101.251.196.90:8080/JztkServer/examInfo";
                utils.url(uri)
                        .targetClass(item.class)
                        .execute(new OkHttpUtils.OnCompleteListener<item>(){
                            @Override
                            public  void onSuccess(item result){
                                Log.e("main","result="+result);
                                mExamInfo=result;
                            }
                            @Override
                            public void onError(String error){
                                Log.e("main","error="+error);
                            }
                        });
                OkHttpUtils<String> utils1=new OkHttpUtils<String>(instance);
                String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url2)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
                            @Override
                            public void onSuccess(String result) {
                                result listResultFromJson = ResultUtils.getListResultFromJson(result);
                                if (listResultFromJson!=null && listResultFromJson.getError_code()==0){
                                    List<Question> list=listResultFromJson.getResult();
                                    if(list!=null && list.size()>0){

                                        mExamList=list;
                                    }
                                }

                            }

                            @Override
                            public void onError(String error) {
                                 Log.e("main","error="+error);
                            }
                        });


            }
        }).start();




    }
}

package cn.ucai.jkbd;

import android.app.Application;
import android.util.Log;

import java.util.List;

import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.bean.item;
import cn.ucai.jkbd.utils.OkHttpUtils;

/**
 * Created by gg on 2017/6/30.
 */

public class andomapption extends Application {
    item it;
    List<Question> questionList;
    private  static  andomapption  apption;

    public void setIt(item it) {
        this.it = it;
    }
public  static andomapption getapption(){
    return apption;
}
    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apption=this;
        
        initDay();
    }

    private void initDay() {
        OkHttpUtils<item> utils=new OkHttpUtils<>(apption);
        String uri="http://101.251.196.90:8080/JztkServer/examInfo ";
        utils.url(uri)
                .targetClass(item.class)
                .execute(new OkHttpUtils.OnCompleteListener<item>(){
                    @Override
                    public  void onSuccess(item result){

                        Log.e("main555","result="+result);
                        it=result;
                    }
                    @Override
                    public void onError(String error){
                        Log.e("main","error="+error);
                    }
                });

    }

    public item getIt() {
        return it;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }
}

package cn.ucai.jkbd.dao;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import cn.ucai.jkbd.ExamApplication;
import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.bean.item;
import cn.ucai.jkbd.bean.result;
import cn.ucai.jkbd.utils.OkHttpUtils;
import cn.ucai.jkbd.utils.ResultUtils;

/**
 * Created by gg on 2017/7/2.
 */

public class IExamdaoImpl implements IExamdao {
    @Override
    public void loadIExamdaoInfo() {

        OkHttpUtils<item> utils=new OkHttpUtils<>(ExamApplication.getInstance());
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uri)
                .targetClass(item.class)
                .execute(new OkHttpUtils.OnCompleteListener<item>(){
                    @Override
                    public  void onSuccess(item result){
                        Log.e("main","result="+result);
                        ExamApplication.getInstance().setmExamInfo(result);
                        ExamApplication.getInstance().sendBroadcast( new Intent(ExamApplication.LOAD_INFO)
                                .putExtra(ExamApplication.LOAD_SUCCESS,true));
                    }
                    @Override
                    public void onError(String error){
                        Log.e("main","error="+error);
                        ExamApplication.getInstance().sendBroadcast( new Intent(ExamApplication.LOAD_INFO)
                                .putExtra(ExamApplication.LOAD_SUCCESS,false));
                    }
                });

    }

    @Override
    public void loadQuestionList() {

        OkHttpUtils<String> utils1 = new OkHttpUtils<String>(ExamApplication.getInstance());
        String url2 = "http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils1.url(url2)
                .targetClass(String.class)
                .execute(new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        boolean success= false;
                        cn.ucai.jkbd.bean.result listResultFromJson = ResultUtils.getListResultFromJson(result);
                        if (listResultFromJson != null && listResultFromJson.getError_code() == 0) {
                            List<Question> list = listResultFromJson.getResult();
                            if (list != null && list.size() > 0) {
                                ExamApplication.getInstance().setmExamList(list);
                                success =true ;

                            }
                        }
                        ExamApplication.getInstance().sendBroadcast( new Intent(ExamApplication.LOAD_IQUESTION)
                                .putExtra(ExamApplication.LOAD_SUCCESS,success));
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main", "error=" + error);
                        ExamApplication.getInstance().sendBroadcast( new Intent(ExamApplication.LOAD_IQUESTION)
                                .putExtra(ExamApplication.LOAD_SUCCESS,false));
                    }
                });

    }
}

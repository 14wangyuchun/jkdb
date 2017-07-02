package cn.ucai.jkbd.activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import cn.ucai.jkbd.ExamApplication;
import cn.ucai.jkbd.R;
import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.bean.item;
import cn.ucai.jkbd.biz.IExambiz;
import cn.ucai.jkbd.biz.IExamnizImpl;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
     IExambiz biz ;
    LoadExamBroadcast loadexambroadcast;
    LoadQuestiomExamBroadcast loadQuestiomExamBroadcast;
    TextView tvExamInfo,tvExamTitle,tvop1,tvop2,tvop3,tvop4;
        ImageView mImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_test);
        loadexambroadcast = new LoadExamBroadcast();
        loadQuestiomExamBroadcast = new LoadQuestiomExamBroadcast();
        setListener();
        initView();
        loaddata();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loadexambroadcast!=null)
        {
            unregisterReceiver(loadexambroadcast);
        }
        if(loadQuestiomExamBroadcast!=null)
        {
            unregisterReceiver(loadQuestiomExamBroadcast);
        }
    }

    private void setListener() {
        registerReceiver( loadexambroadcast,new IntentFilter(ExamApplication.LOAD_INFO));
        registerReceiver( loadQuestiomExamBroadcast,new IntentFilter(ExamApplication.LOAD_IQUESTION));
    }

    private void loaddata() {
        biz=new IExamnizImpl();
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();

            }
        }).start();

    }

    private void initView()
    {
        tvExamInfo= (TextView) findViewById(R.id.tv_andinfo);

        tvExamTitle= (TextView) findViewById(R.id.tvExamTitle);
       tvop1= (TextView) findViewById(R.id.tv_op1);
        tvop2= (TextView) findViewById(R.id.tv_op2);
        tvop3= (TextView) findViewById(R.id.tv_op3);
        tvop4= (TextView) findViewById(R.id.tv_op4);
        mImageView= (ImageView) findViewById(R.id.im_exam_image);

    }

    private void initData() {
        if (isLoadExaminfo && isLoadQuestion)
        {
            item result = ExamApplication.getInstance().getmExamInfo();
        if (result != null) {
            showData(result);

        }
        List<Question> examlist = ExamApplication.getInstance().getmExamList();
        if (examlist != null) {
            showExam(examlist);

        }
    }
    }

    private void showExam(List<Question> examlist) {
        Question exam=examlist.get(0);
            if (exam!=null){
                tvExamTitle.setText(exam.getQuestion());
                tvop1.setText(exam.getItem1());
                tvop2.setText(exam.getItem2());
                tvop3.setText(exam.getItem3());
                tvop4.setText(exam.getItem4());
                Picasso.with(ExamActivity.this).load(exam.getUrl()).into(mImageView);

            }
    }

    private void showData(item result) {
        tvExamInfo.setText(result.toString());

    }
    boolean isLoadExaminfo = false;
    boolean isLoadQuestion =false;
 class  LoadExamBroadcast extends BroadcastReceiver{
     @Override
     public void onReceive(Context context, Intent intent) {
         boolean issuccess = intent.getBooleanExtra(ExamApplication.LOAD_SUCCESS,false);
         if (issuccess)
         {
             isLoadExaminfo=true;
         }
    initData();
     }
 }

    class  LoadQuestiomExamBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean issuccess = intent.getBooleanExtra(ExamApplication.LOAD_SUCCESS,false);
            if (issuccess)
            {
           isLoadQuestion = true;
            }
           initData();
        }
    }
}

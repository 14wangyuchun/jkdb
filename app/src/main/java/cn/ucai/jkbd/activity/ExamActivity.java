package cn.ucai.jkbd.activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    ProgressBar dialog;
    LoadExamBroadcast loadexambroadcast;
    LinearLayout linearLoading,layout3,layout4;
    LoadQuestiomExamBroadcast loadQuestiomExamBroadcast;
    TextView tvload,tvExamInfo,tvExamTitle,tvop1,tvop2,tvop3,tvop4,tvno;
    CheckBox cb1,cb2,cb3,cb4;
        ImageView mImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_test);
        loadexambroadcast = new LoadExamBroadcast();
        loadQuestiomExamBroadcast = new LoadQuestiomExamBroadcast();
        setListener();
        initView();
        biz=new IExamnizImpl();
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
        linearLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tvload.setText("下载中...");
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
        linearLoading= (LinearLayout) findViewById(R.id.layout_loading);
        tvExamTitle= (TextView) findViewById(R.id.tvExamTitle);
       tvop1= (TextView) findViewById(R.id.tv_op1);
        tvop2= (TextView) findViewById(R.id.tv_op2);
        tvop3= (TextView) findViewById(R.id.tv_op3);
        tvop4= (TextView) findViewById(R.id.tv_op4);
        cb1= (CheckBox) findViewById(R.id.cb_01);
        cb2= (CheckBox) findViewById(R.id.cb_02);
        cb3= (CheckBox) findViewById(R.id.cb_03);
        cb4= (CheckBox) findViewById(R.id.cb_04);
        layout3= (LinearLayout) findViewById(R.id.layout_03);
        layout4= (LinearLayout) findViewById(R.id.layout_04);
        tvload= (TextView) findViewById(R.id.tv_load);
        dialog= (ProgressBar) findViewById(R.id.load_dialog);
        mImageView= (ImageView) findViewById(R.id.im_exam_image);
        tvno= (TextView) findViewById(R.id.tv_No);
        linearLoading.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
           loaddata();
            }
        });

    }

    private void initData() {
        if(isLoadExaminfoREceiver && isLoadQuestionReceiver) {

            if (isLoadExaminfo && isLoadQuestion) {
                linearLoading.setVisibility(View.GONE);
                item result = ExamApplication.getInstance().getmExamInfo();
                if (result != null) {
                    showData(result);
                }
                    showExam(biz.getQuestion());
            }else {
                linearLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tvload.setText("下载失败，点击重新下载");

            }
        }
    }

    private void showExam(Question exam ) {
            if (exam!=null){
                tvno.setText(biz.getQuestionindex());
                tvExamTitle.setText(exam.getQuestion());
                tvop1.setText(exam.getItem1());
                tvop2.setText(exam.getItem2());
                tvop3.setText(exam.getItem3());
                tvop4.setText(exam.getItem4());
                if(exam.getUrl()!=null && !exam.getUrl().equals(""))
                { mImageView.setVisibility(View.VISIBLE);
                    Picasso.with(ExamActivity.this).load(exam.getUrl()).into(mImageView);
                }
             else {
                    mImageView.setVisibility(View.GONE);
                }
                cb4.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);
                cb3.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);
                layout3.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);
                layout4.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);

            }
    }

    private void showData(item result) {
        tvExamInfo.setText(result.toString());

    }
    boolean isLoadExaminfo = false;
    boolean isLoadQuestion =false;
    boolean isLoadExaminfoREceiver = false;
    boolean isLoadQuestionReceiver =false;

    public void preQuestion(View view) {
        showExam(biz.preQuestion());
    }

    public void nextQuestion(View view) {
        showExam(biz.nextQuestion());
    }

    class  LoadExamBroadcast extends BroadcastReceiver{
     @Override
     public void onReceive(Context context, Intent intent) {
         boolean issuccess = intent.getBooleanExtra(ExamApplication.LOAD_SUCCESS,false);
         if (issuccess)
         {
             isLoadExaminfo=true;
         }
         isLoadExaminfoREceiver=true;
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
            isLoadQuestionReceiver=true;
           initData();
        }
    }
}

package cn.ucai.jkbd.activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    CheckBox[] cbs = new CheckBox[4];
    LoadExamBroadcast loadexambroadcast;
    LinearLayout linearLoading,layout3,layout4;
    LoadQuestiomExamBroadcast loadQuestiomExamBroadcast;
    TextView tvtime,tvload,tvExamInfo,tvExamTitle,tvop1,tvop2,tvop3,tvop4,tvno;
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
        tvtime = (TextView) findViewById(R.id.tv_time);
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
            cbs[0]=cb1;
           cbs[1]=cb2;
           cbs[2]=cb3;
           cbs[3]=cb4;
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
       cb1.setOnCheckedChangeListener(listener);
        cb2.setOnCheckedChangeListener(listener);
        cb3.setOnCheckedChangeListener(listener);
        cb4.setOnCheckedChangeListener(listener);
    }

    CompoundButton.OnCheckedChangeListener listener =new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int userAnswer = 0;
            if (isChecked) {
                switch (buttonView.getId()) {
                    case R.id.cb_01:
                        userAnswer = 1;
                        break;
                    case R.id.cb_02:
                        userAnswer = 2;
                        break;
                    case R.id.cb_03:
                        userAnswer = 3;
                        break;
                    case R.id.cb_04:
                        userAnswer = 4;
                        break;
                }
                if (userAnswer > 0) {
                    for (CheckBox cb : cbs)
                    {
                        cb.setChecked(false);
                    }
                    cbs[userAnswer - 1].setChecked(true);
                }
            }
        }
    };
    private void initData() {
        if(isLoadExaminfoREceiver && isLoadQuestionReceiver) {

            if (isLoadExaminfo && isLoadQuestion) {
                linearLoading.setVisibility(View.GONE);
                item result = ExamApplication.getInstance().getmExamInfo();
                if (result != null) {
                    showData(result);
                    inittime(result);
                }
                    showExam(biz.getQuestion());
            }else {
                linearLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tvload.setText("下载失败，点击重新下载");

            }
        }
    }

    private void inittime(item result) {
  int sumTime = result.getLimitTime()*60*1000;
        final Timer timer = new Timer();
        final long overtime = sumTime+ System.currentTimeMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
         long l= overtime - System.currentTimeMillis();
                final long min = l/1000/60;
                final long sec =  l/1000%60;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvtime.setText("剩余时间:"+min+"分"+sec+"秒");
                    }
                });
            }
        },0,1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
            }
        },sumTime);

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
                     resetOptions();
                String useranswer = exam.getUseranswer();
                if (useranswer!=null && !useranswer.equals(""))
                {
                 int usercb = Integer.parseInt(useranswer)-1;
                    cbs[usercb].setChecked(true);
                }
            }
    }

    private void resetOptions() {
        for (CheckBox cb :cbs)
        {
            cb.setChecked(false);
        }
    }
    public void saveAnuserswer()
    {

        for (int i=0; i<cbs.length;i++)
        {
            if (cbs[i].isChecked())
            {
                biz.getQuestion().setUseranswer(String.valueOf(i+1));
                return;
            }
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
        saveAnuserswer();
        showExam(biz.preQuestion());
    }

    public void nextQuestion(View view) {
        saveAnuserswer();
        showExam(biz.nextQuestion());
    }

    public void commit(View view) {
        saveAnuserswer();
        int s=biz.commitExam();
        Log.e("112","s"+s);
        View inflate = View.inflate(this,R.layout.layou_result,null);
        TextView tvresult = (TextView) inflate.findViewById(R.id.tv_result);
        tvresult.setText("你的分数为\n"+s+"分");
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.exam_commit32x32)
                .setTitle("交卷")
                .setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create().show();
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

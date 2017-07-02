package cn.ucai.jkbd.activity;
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


/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tvExamTitle,tvop1,tvop2,tvop3,tvop4;
        ImageView mImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_test);
        initView();
        initData();
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

    private void initData(){
        item result = ExamApplication.getInstance().getmExamInfo();
        if (result!=null){
            showData(result);

        }
        List<Question> examlist=ExamApplication.getInstance().getmExamList();
        if (examlist!=null){
                   showExam(examlist);

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


}

package cn.ucai.jkbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.ucai.jkbd.R;
import cn.ucai.jkbd.bean.item;
import cn.ucai.jkbd.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public  void text(View view){
        startActivity(new Intent(MainActivity.this,ExamActivity.class));
    }
    public  void exit(View view){
       finish();
    }
}

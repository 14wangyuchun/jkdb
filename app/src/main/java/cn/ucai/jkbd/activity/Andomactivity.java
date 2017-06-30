package cn.ucai.jkbd.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.ucai.jkbd.R;
import cn.ucai.jkbd.andomapption;
import cn.ucai.jkbd.bean.item;

/**
<<<<<<< HEAD
 * Created by Administrator on 2017/6/29.6666
=======
 * Created by Administrator on 2017/6/29.
>>>>>>> origin/master
 */

public class Andomactivity extends AppCompatActivity {
   TextView tvandinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_test);
        initData();
        initView();
    }

    private void initView() {
        tvandinfo=(TextView)findViewById(R.id.tv_andinfo);
    }

    private void initData() {
        item itm= andomapption.getapption().getIt();
        if(itm!=null)
        {
            showdata(itm);
        }
    }

    private void showdata(item itm) {
           tvandinfo.setText(itm.toString());
    }
}

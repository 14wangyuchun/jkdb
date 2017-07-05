package cn.ucai.jkbd.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.ucai.jkbd.ExamApplication;
import cn.ucai.jkbd.R;
import cn.ucai.jkbd.activity.ExamActivity;
import cn.ucai.jkbd.bean.Question;

/**
 * Created by Administrator on 2017/7/4.
 */

public class AdapterQuestion extends BaseAdapter{
    Context mcomtext;
    List<Question> questions;

    public AdapterQuestion(Context context) {
        mcomtext = context;
        questions = ExamApplication.getInstance().getmExamList();
    }

    @Override
    public int getCount() {
        return questions==null?0:questions.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
   View view=View.inflate(mcomtext, R.layout.item_question,null);
        TextView tvno1= (TextView) view.findViewById(R.id.tv_no1);
        ImageView ivquestion = (ImageView) view.findViewById(R.id.iv_question);
        String usa = questions.get(position).getUseranswer();
        String rusa = questions.get(position).getAnswer();
        if(usa!=null && !usa.equals(""))
        {
            ivquestion.setImageResource(position%2==0?R.mipmap.dagou1:R.mipmap.dagou2);
            if (!usa.equals(rusa))
            {
                ivquestion.setImageResource(R.mipmap.erro);
            }
        }
        else
        {
            ivquestion.setImageResource(position%2==0?R.mipmap.wenhao3:R.mipmap.wenhao2);
        }

         tvno1.setText("第"+(position+1)+"题");
        return view;
    }
}

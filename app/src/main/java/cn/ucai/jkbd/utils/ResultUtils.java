package cn.ucai.jkbd.utils;
import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import cn.ucai.jkbd.bean.Question;
import cn.ucai.jkbd.bean.result;


public class ResultUtils {
    static String UTF_8 = "utf-8";
    public static result getListResultFromJson(String jsonStr){
       result rest=new result();

        Log.e("Utils","jsonStr="+jsonStr);
        try {
            if(jsonStr==null || jsonStr.isEmpty() || jsonStr.length()<3)return null;
            JSONObject jsonObject = new JSONObject(jsonStr);
            if(!jsonObject.isNull("error_code")) {
                rest.setError_code(jsonObject.getInt("error_code"));
            }else if(!jsonObject.isNull("msg")){
                rest.setError_code(jsonObject.getInt("msg"));
            }
            if(!jsonObject.isNull("reason")) {
                rest.setReason(jsonObject.getString("reason"));
            }else if(!jsonObject.isNull("result")){
                rest.setReason(jsonObject.getString("result"));
            }
            if(!jsonObject.isNull("result")) {
                JSONArray array = jsonObject.getJSONArray("result");
                if (array != null) {
                    List<Question> list = new ArrayList<Question>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonGroupAvatar = array.getJSONObject(i);
                        Question ga = new Gson().fromJson(jsonGroupAvatar.toString(), Question.class);
                        list.add(ga);
                    }

                   rest.setResult(list);
                    return rest;
                }
            }else{
                JSONArray array = new JSONArray(jsonStr);
                if (array != null) {
                    List<Question> list = new ArrayList<Question>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonGroupAvatar = array.getJSONObject(i);
                        Question ga = new Gson().fromJson(jsonGroupAvatar.toString(), Question.class);
                        list.add(ga);
                    }
                    rest.setResult(list);
                    return rest;
                }
            }
            return rest;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

}

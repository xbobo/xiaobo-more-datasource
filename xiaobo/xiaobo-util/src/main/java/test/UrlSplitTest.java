package test;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

/**
 * @author xiaobo
 * @date 2019/8/21
 * @description:
 */
public class UrlSplitTest {

    public static void main(String[] args) {
        String str="https://b45548200.at.baijiayun.com/web/playback/index?classid=19080151919413&token=d0XjzokJ9x-Z8SfWvkqQcQG_NyXuW4IzHRirsBi_Ja-ISq9shy0XN4717sUDxHYmxc0LYeWG7jkKp0fXMnVKLQ&session_id=201908181";
        JSONObject paramters = urlParamtersTOJSONObject(str);
        System.out.println(paramters);
    }

    private static JSONObject urlParamtersTOJSONObject(String str) {
        int index = str.indexOf("?");
        String splitStr=str.substring(index+1);
        String[] array = splitStr.split("&");
        JSONObject paramters=new JSONObject();
        for(String single:array){
            String[] split = single.split("=");
            if(split!=null&&split.length>0){
                if(split.length==1){
                    paramters.put(split[0],"");
                }else{
                    paramters.put(split[0],split[1]);
                }
            }
        }
        return paramters;
    }
}

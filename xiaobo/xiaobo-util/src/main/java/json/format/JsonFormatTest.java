package json.format;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaobo
 * @date 2019/8/6
 * @description:
 */
public class JsonFormatTest {
    public static void main(String[] args) {
        // 生产环境中，config要做singleton处理，要不然会存在性能问题
//        UserTest user = new UserTest();
//        user.setUserId("aaaBBBcccDDDD");
//        user.setUserInfo("eeeFFFgggHHIi");
//        String json = JSON.toJSONString(user);
//        String cu = hump2UnderLineJsonBean(json);
//        System.out.println(cu);
//        String s = underLine2HumpJsonBean(cu);
//        System.out.println(s);
//
//        String cuall = hump2UnderLineAll(json);
//        System.out.println(cuall);
//        String sall = underLine2HumpAll(cu);
//        System.out.println(sall);

        String testt="{\"Operation_Name\": \"Camp_product_click_Detail\"}";
        System.out.println(underLine2HumpJsonBean(testt));
        System.out.println(underLine2HumpAll(testt));


    }

    /**
     * 下划线 转驼峰
     * @param content
     * @return
     */
    public static  String underLine2HumpJsonBean(String content){
        String regex="_(\\w)(\\w{0,}\":)";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase()+matcher.group(2));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    public static  String underLine2HumpAll(String content){
        String regex="_(\\w)";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 驼峰转下划线
     * @param content
     * @return
     */
    public static  String hump2UnderLineJsonBean(String content){
        String regex="([A-Z]\\w{0,}\":)";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static  String hump2UnderLineAll(String content){
        String regex="([A-Z])";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}

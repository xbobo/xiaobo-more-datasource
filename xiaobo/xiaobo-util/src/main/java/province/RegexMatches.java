package province;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaobo
 * @date 2019/8/5
 * @description:
 */
public class RegexMatches {
    private static String REGEX = "a";
    private static String INPUT = "aa1bfooaa1bfooa1bfoobkkk";
    private static String REPLACE = "-";
    public static void main(String[] args) {
//        Pattern p = Pattern.compile(REGEX);
//        // 获取 matcher 对象
//        Matcher m = p.matcher(INPUT);
//        StringBuffer sb = new StringBuffer();
//        while(m.find()){
//            m.appendReplacement(sb,REPLACE);
//        }
//        m.appendTail(sb);
//        System.out.println(sb.toString());

        //
//        System.out.println(validateEmail("q@qqq12q..com"));
//        System.out.println(validatePhone("12345678901"));
        System.out.println(validatePassword("z12a111"));


    }
    /**
     * email 验证
     */
    public static  Boolean  validateEmail(String content){
        String emailRegex="^[a-zA-Z0-9_-]+@{1}[a-zA-Z0-9_-]+\\.{1}[a-zA-Z0-9_-]+$";
        Pattern p=Pattern.compile(emailRegex);
        Matcher matcher = p.matcher(content);
        if(matcher.find()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 手机号验证
     * @param content
     * @return
     */
    public static  Boolean  validatePhone(String content){
        String phoneRegex="^\\d{11}$";
        Pattern p=Pattern.compile(phoneRegex);
        Matcher matcher = p.matcher(content);
        if(matcher.find()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 密码验证
     * @param content
     * @return
     */
    public static  Boolean  validatePassword(String content){
        // 密码 以字母为首
        String phoneRegex="^[a-zA-z][a-zA-z0-9]{7,15}$";
        Pattern p=Pattern.compile(phoneRegex);
        Matcher matcher = p.matcher(content);
        if(matcher.find()){
            return true;
        }else {
            return false;
        }
    }
}

package test;

import java.text.Collator;
import java.util.*;

/**
 * @author xiaobo
 * @date 2019/7/23
 * @description:
 */
public class ListSortTest {

    public static void main(String[] args) {

        List<String> showLabels=new ArrayList<String>();

        showLabels.add("三");
        showLabels.add("一");
        showLabels.add("四");
        showLabels.add("二");
        showLabels.add("五");
        showLabels.add("六");
        showLabels.add("七");
        showLabels.add("八");
        showLabels.add("九");
        showLabels.add("十");
        showLabels.add("啊");
        Collator collator = Collator.getInstance(Locale.CHINA);
        Collections.sort(showLabels, collator);

        showLabels.sort(new Comparator<String>() {

            @Override
            public int compare(String a, String b) {

                return Collator.getInstance(Locale.CHINA).compare(a,b);
            }});

        System.out.println(showLabels.toString());

    }

    public static String stringToAscii(String value) {

        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int)chars[i]).append(",");
            } else {
                sbu.append((int)chars[i]);
            }
        }
        return sbu.toString();
    }
    public static String replaceUpcase(String value) {
        value=value.replace("一","1");
        return "";
    }


}

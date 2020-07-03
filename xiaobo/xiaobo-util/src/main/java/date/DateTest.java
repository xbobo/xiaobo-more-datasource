package date;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaobo
 * @date 2019/8/1
 * @description:
 *
 *
 *
 */
public class DateTest {

    public static void main(String[] args) {
//        List<Long> list=new ArrayList<>();
//        list.add(1L);list.add(2L);list.add(3L);
//
//        List<Long> list1=new ArrayList<>(list);
//
//        list1.removeAll(list);
//
//        System.out.println(list.size());

//        String str="blob:http://www.pxwx.com/7bb6e72d-a1d3-4380-ae86-082c2208ed2f";
//        String reg=".+//.+/(.+)";
//        Pattern pattern = Pattern.compile(reg);
//        Matcher matcher = pattern.matcher(str);
//        if(matcher.find()){
//            System.out.println(matcher.group(0));
//            System.out.println(matcher.group(1));
//        }


          StringBuffer a = new StringBuffer("A");
          StringBuffer b = new StringBuffer("B");
          operator(a, b);
          System.out.println(a +"," + b);

        String s1="疯狂Java讲义";
        Collection books = new HashSet();
        books.add("轻量级JavaEE企业应用实践");
        books.add(s1);

        for (Object obj: books) {
            String book = (String) obj;
            System.out.println(book);
            if (book.equals(s1)) {
                books.remove(book);
            }
        }
        System.out.println(books);

    }
    public static void operator(StringBuffer x, StringBuffer y) {
      x.append(y); y = x;
    }
}

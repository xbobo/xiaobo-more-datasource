package questions;

import projecttset.A;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xiaobo
 * @date 2019/11/8
 * @description:
 */
public class FloatPrimitiveTest1 {

//    A: true
//    B: false
//    C: 由硬件指令决定

    public static void main(String[] args) {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        if (a == b) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        System.out.println(BigDecimal.valueOf(1D).subtract(BigDecimal.valueOf(0.9D)));
        System.out.println(Float.valueOf(Float.valueOf(1.0f)-Float.valueOf(0.9f)));

        Set<String> set = new HashSet<>();
        set.add("A");
        set.add("B");
        set.add("C");
        set.add("A");
        set = Collections.unmodifiableSet(set);
        System.out.println(set);

        List<String> list = new ArrayList<>();

        list.add("A");
        list.add("B");
        list.add("C");

        list = Collections.unmodifiableList(list);//unmodifiableList() 方法用于返回指定列表的不可修改视图。

        System.out.println(list);


    }
}

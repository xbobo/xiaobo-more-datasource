package questions;

import projecttset.A;

/**
 * @author xiaobo
 * @date 2019/11/8
 * @description:
 */
public class FloatPrimitiveTest2 {



    public static void main(String[] args) {

        /////////22222222222222222222//////////////////////////////////////
        //    A: true
        //    B: false
        //    C: 编译出错

        Float a = Float.valueOf(1.0f - 0.9f);
        Float b = Float.valueOf(0.9f - 0.8f);
        System.out.println(a);
        System.out.println(b);
        if (a.equals(b)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
        /////////33333333333333////////////////////
//        A: null
//        B: 抛出异常
//        C: default

        String param = null;
        switch (param) {
            case "null":
                System.out.println("null");
                break;
            default:
                System.out.println("default");
        }
        ///////////444444444444444//////////////////////////////
    }
}

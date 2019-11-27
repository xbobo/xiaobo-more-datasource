package date;

import weixin.nati.DateUtil;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaobo
 * @date 2019/8/1
 * @description:
 */
public class DateTest {
    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
//        System.out.println(DateUtil.getDayStr(1564668000L, 1564675200L));
//        System.out.println(DateUtil.getDayStr(1564754400L, 1564675200L));
//        System.out.println(DateUtil.getDayStr(1564675200L, 1564754400L));
        System.out.println(new String("\u548c\u5e73\u533a"));



    }
}

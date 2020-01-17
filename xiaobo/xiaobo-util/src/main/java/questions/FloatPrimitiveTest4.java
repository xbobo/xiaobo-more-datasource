package questions;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaobo
 * @date 2019/11/8
 * @description:
 */
public class FloatPrimitiveTest4 {
    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

//        A: 两种赋值的方式是一样的
//        B: 推荐a的赋值方式
//        C: 推荐b的赋值方式
        BigDecimal a =BigDecimal.valueOf(0.1D);
        System.out.println(a);
        BigDecimal b = new BigDecimal("0.1");
        System.out.println(b);

//        A: lock是非公平锁
//        B: finally代码块不会抛出异常
//        C: tryLock获取锁失败则直接往下执行
        try {
            lock.tryLock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }
}

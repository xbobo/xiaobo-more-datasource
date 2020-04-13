package juc;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaobo
 * @date 2020/4/10
 * @description:
 */
public class JUCTest {
    //https://www.cnblogs.com/xiaoxi/p/8303574.html

    /**
     JUC  java.util.concurrent
     CAS
     LOCKS
     Colleactions
     automic
     */
    public static void main1(String[] args) {
        Lock lock=new ReentrantLock();
        lock.lock();

        lock.unlock();
        //使用Callable+FutureTask获取执行结果
        //第一种方式
        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
        executor.submit(futureTask);
        executor.shutdown();

        //第二种方式，注意这种方式和第一种方式效果是类似的，只不过一个使用的是ExecutorService，一个使用的是Thread
//        Task task = new Task();
//        FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
//        Thread thread = new Thread(futureTask);
//        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println("主线程在执行任务");

        try {
            if(futureTask.get()!=null){
                System.out.println("task运行结果"+futureTask.get());
            }else{
                System.out.println("future.get()未获取到结果");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }


/// 使用Callable+Future获取执行结果
    public static void main2(String[] args) {

        //创建线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        //创建Callable对象任务
        Task task = new Task();
        //提交任务并获取执行结果
        Future<Integer> result = executor.submit(task);
        //关闭线程池
        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println("主线程在执行任务");

        try {
            if(result.get()!=null){
                System.out.println("task运行结果"+result.get());
            }else{
                System.out.println("未获取到结果");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }

}
class Task implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        Thread.sleep(3000);
        int sum = 0;
        for(int i=0;i<100;i++){
            sum += i;
        }
        return sum;
    }

}
/**
 实现Runnable接口和实现Callable接口的区别：

 1、Runnable是自从java1.1就有了，而Callable是1.5之后才加上去的。

 2、Callable规定的方法是call(),Runnable规定的方法是run()。

 3、Callable的任务执行后可返回值，而Runnable的任务是不能返回值(是void)。

 4、call方法可以抛出异常，run方法不可以。

 5、运行Callable任务可以拿到一个Future对象，表示异步计算的结果。它提供了检查计算是否完成的方法，以等待计算的完成，并检索计算的结果。通过Future对象可以了解任务执行情况，可取消任务的执行，还可获取执行结果。

 6、加入线程池运行，Runnable使用ExecutorService的execute方法，Callable使用submit方法。
 */

package quick.executeservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangxc on 2017/3/27.
 */
public class CacheThreadPool {
    public static void main(String[] args) {
        useThread();
        notUseThread();
    }

    private static void notUseThread() {
        for (int i = 0; i < 15; i++) {
            System.out.println("获取" + i + "的信息并保存");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void useThread() {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++)
            exec.execute(new MyThread("张" + i));
        exec.shutdown();//并不是终止线程的运行，而是禁止在这个Executor中添加新的任务
    }
}



package quick.executeservice;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by wangxc on 2017/3/29.
 */
public class TaskSleep implements Callable<Integer> {

    private int num;

    public TaskSleep(int num){
        this.num = num;
    }

    public Integer call() throws Exception {
//        System.out.println(num + "--->" +i);
//        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + "执行中--->");
        int temp = new Random().nextInt(100);
        return  temp;
    }
}

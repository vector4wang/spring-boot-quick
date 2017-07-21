package quick.executeservice;

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
        System.out.println(Thread.currentThread().getName()+"--->执行中");
        Thread.sleep(10);
        return  num;
    }
}

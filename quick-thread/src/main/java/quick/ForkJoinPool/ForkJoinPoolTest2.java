package quick.ForkJoinPool;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/7/13
 * Time: 17:25
 * Description:
 */
public class ForkJoinPoolTest2 {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int arr[] = new int[10];
        Random random = new Random();
        int total = 0;
        // 初始化100个数字元素
        long s1 = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
//            int temp = random.nextInt(100);
            // 对数组元素赋值,并将数组元素的值添加到total总和中
            Thread.sleep(1000);
            total += (arr[i] = i);
        }
        long e1 = System.currentTimeMillis();
        System.out.println("初始化时的总和=" + total + " 耗时:" + (double) (e1 - s1) / 1000);
        // 创建包含Runtime.getRuntime().availableProcessors()返回值作为个数的并行线程的ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 提交可分解的PrintTask任务

        long s2 = System.currentTimeMillis();

//        Integer invoke = forkJoinPool.invoke(new SumTask(arr, 0,
//                arr.length));
        Future<Integer> future = forkJoinPool.submit(new SumTask(arr, 0,
                arr.length));
        long e2 = System.currentTimeMillis();


        System.out.println("计算出来的总和=" + future.get() + " 耗时:" + (double) (e1 - s1) / 1000);
        // 关闭线程池
        forkJoinPool.shutdown();
    }

}

class SumTask extends RecursiveTask<Integer> {
    // 每个"小任务"最多只打印50个数
    private static final int MAX = 20;
    private int arr[];
    private int start;
    private int end;

    SumTask(int arr[], int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        // 当end-start的值小于MAX时候，开始打印
        if ((end - start) < MAX) {
            for (int i = start; i < end; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sum += arr[i];
            }
            return sum;
        } else {
            System.err.println("=====任务分解======");
            // 将大任务分解成两个小任务
            int middle = (start + end) / 2;
            SumTask left = new SumTask(arr, start, middle);
            SumTask right = new SumTask(arr, middle, end);
            // 并行执行两个小任务
            left.fork();
            right.fork();
//             把两个小任务累加的结果合并起来
            return left.join() + right.join();

        }
    }

}


package quick.ForkJoinPool;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/7/13
 * Time: 17:13
 * Description:
 * http://www.infoq.com/cn/articles/fork-join-introduction/
 * https://www.ibm.com/developerworks/cn/java/j-lo-forkjoin/
 */
public class ForkJoinPoolTest {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 创建包含Runtime.getRuntime().availableProcessors()返回值作为个数的并行线程的ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 提交可分解的PrintTask任务
        PrintTask printTask = new PrintTask(0, 25);
        forkJoinPool.invoke(printTask);
//        submit.join();

//        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);//阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
        // 关闭线程池
        forkJoinPool.shutdown();
        if (printTask.isCompletedNormally()) {
            System.out.println("Main: The process has completed normally.");
        }
    }

}

// 无返回值
class PrintTask extends RecursiveAction {
    // 每个"小任务"最多只打印50个数
    private static final int THRESHOLD = 10;

    private int start;
    private int end;

    PrintTask(int start, int end) {
        this.start = start;
        this.end = end;
    }


    @Override
    protected void compute() {
        // 当end-start的值小于MAX时候，开始打印
        if ((end - start) < THRESHOLD) {
            for (int i = start; i < end; i++) {
                Random random = new Random();
                try {
                    Thread.sleep(random.nextInt(1000));
                    System.out.println(Thread.currentThread().getName() + "的i值:" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } else {
            // 将大任务分解成两个小任务
            int middle = (start + end) / 2;
            PrintTask left = new PrintTask(start, middle);
            PrintTask right = new PrintTask(middle, end);
            // 并行执行两个小任务
//            left.fork();
//            right.fork();
//            left.join();
//            right.join();
            invokeAll(left,right);
        }
    }
}


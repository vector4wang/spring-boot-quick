//package quick.ForkJoinPool;
//
//import java.util.concurrent.ForkJoinPool;
//
///**
// * Created with IDEA
// * User: vector
// * Data: 2017/7/13
// * Time: 17:10
// * Description:
// */
//public class TestMain {
//    public static void main(String[] args) {
//        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
//        forkJoinPool.submit(()->{
//            tasks.parallelStream().forEach(t->{
//                try {
//                    String gdsstatus=transactionService.GetTransInfo(url, t.getTask_id());
//                    checkStatus(t.getTask_id(),t.getTask_status(),gdsstatus);
//                } catch (Exception e) {
//                    System.out.println("EXCEPTION OCCOR IN TASK:"+t.getTask_id());
//                    e.printStackTrace();
//                }
//
//                System.out.println("NO:"+count.getAndIncrement()+" is done");
//
//            });
//        });
//    }
//}

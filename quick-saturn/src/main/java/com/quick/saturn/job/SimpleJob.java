package com.quick.saturn.job;

import com.google.gson.Gson;
import com.vip.saturn.job.AbstractSaturnJavaJob;
import com.vip.saturn.job.SaturnJobExecutionContext;
import com.vip.saturn.job.SaturnJobReturn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleJob extends AbstractSaturnJavaJob {

    private static Gson gson = new Gson();

    /**
     * @param jobName         作业名
     * @param shardItem       分片项
     * @param shardParam      分片参数
     * @param shardingContext 其它参数信息
     * @return 返回执行结果
     * @throws InterruptedException 注意处理中断异常
     */
    @Override
    public SaturnJobReturn handleJavaJob(String jobName, Integer shardItem, String shardParam,
                                         SaturnJobExecutionContext shardingContext) throws InterruptedException {
        log.info("SimpleJob jobName:{}, shardItem:{}, shardParam:{}, shardingContext:{}", jobName, shardItem, shardParam, gson.toJson(shardingContext));
        return new SaturnJobReturn(shardItem + "end");
    }
}

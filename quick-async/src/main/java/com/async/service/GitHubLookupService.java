package com.async.service;

import com.async.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class GitHubLookupService {

    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * 不指定name的话，spring默认使用“SimpleAsyncTaskExecutor”
     * 如果只自定义了一个Executor，则优先使用自定义的那个
     * 如果自定义了多个Executor 一定要在Async注解加上name，four则spring不知道你用的是哪一个，最终还是会使用SimpleAsyncTaskExecutor
     *
     * @param user
     * @return
     * @throws InterruptedException
     */
    @Async("asyncExecutor2")
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        logger.info(Thread.currentThread().getName() + " Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }

    @Async("asyncExecutor1")
    public Future<User> findUser2(String userName) {
        logger.info(Thread.currentThread().getName() + " Looking up " + userName);
        try {
            Thread.sleep(new Random().nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(new User("wangxc","xxxx"));
    }

}
package com.async.init;

import com.async.entity.User;
import com.async.service.GitHubLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final GitHubLookupService gitHubLookupService;

    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<User> page1 = gitHubLookupService.findUser("vector4wang");
        CompletableFuture<User> page2 = gitHubLookupService.findUser("Zhuinden");
        CompletableFuture<User> page3 = gitHubLookupService.findUser("xialeistudio");

        Future<User> futurePage1 = gitHubLookupService.findUser2("vector4wang");
        Future<User> futurePage2 = gitHubLookupService.findUser2("Zhuinden");
        Future<User> futurePage3 = gitHubLookupService.findUser2("xialeistudio");

        logger.info("--> " + futurePage1.get());
        logger.info("--> " + futurePage2.get());
        logger.info("--> " + futurePage3.get());

        // Wait until they are all done
        CompletableFuture.allOf(page1,page2,page3).join();

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());



    }

}
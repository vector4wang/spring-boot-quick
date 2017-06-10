package com.quick.es;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Administrator on 2017/6/5 0005.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
    }

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class,args);
    }

}

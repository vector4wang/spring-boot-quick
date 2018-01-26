package com.github.oauth;

import com.github.oauth.user.User;
import com.github.oauth.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/10/30
 * Time: 16:40
 * Description:
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    UserRepo userRepo;

    @Autowired
    public void init(){
        try {
            User user=new User();
            user.setUsername("vector");
            user.setPassword("123456");
            user.setRole("ROLE_USER");
            userRepo.save(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.quick.auth.server;


import com.quick.auth.server.entity.Account;
import com.quick.auth.server.mapper.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/10/26
 * Time: 14:02
 * Description: http://www.leftso.com/blog/139.html
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



    //-----------------下面代码处理初始化一个用户-------------
    //用户名:leftso 用户密码:111aaa 用户角色:ROLE_USER
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    public void init(){
        try {
            Account account=new Account();
            account.setName("vector");
            account.setPassword("23333");
            account.setRoles(new String []{"ROLE_USER"});
            accountRepository.deleteAll();
            accountRepository.save(account);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

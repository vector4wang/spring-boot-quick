package com.quick.abs;

import com.quick.abs.service.CreatureLifeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AbstractTemplateApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AbstractTemplateApplication.class);

        CreatureLifeService creatureLifeService = applicationContext.getBean("creatureLifeService",CreatureLifeService.class);
        creatureLifeService.birth2die("Duck");
    }
}
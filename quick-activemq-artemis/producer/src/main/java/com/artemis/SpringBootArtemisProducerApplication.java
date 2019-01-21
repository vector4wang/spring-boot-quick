package com.artemis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class SpringBootArtemisProducerApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringBootArtemisProducerApplication.class, args);
	}


}
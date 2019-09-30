package com.quick.mongodb;

import com.quick.mongodb.entity.Customer;
import com.quick.mongodb.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author vector
 * @date: 2019/9/29 0029 15:13
 */
@SpringBootApplication
public class MongoApplication implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        customerRepository.deleteAll();

        customerRepository.save(new Customer("vector", "wang"));
        customerRepository.save(new Customer("bmhjqs", "wang"));

        System.out.println("customer fetch with findAll()");
        System.out.println("-----------------------------");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        System.out.println("Customer found with findByFirstName('vector'):");
        System.out.println("--------------------------------");
        System.out.println(customerRepository.findByFirstName("vector"));


        System.out.println("Customers found with findByLastName('wang'):");
        System.out.println("--------------------------------");
        for (Customer customer : customerRepository.findByLastName("wang")) {
            System.out.println(customer);
        }


    }
}

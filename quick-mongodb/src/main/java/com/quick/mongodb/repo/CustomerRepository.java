package com.quick.mongodb.repo;

import com.quick.mongodb.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author vector
 * @date: 2019/9/29 0029 15:08
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);

    public List<Customer> findByLastName(String lastName);
}

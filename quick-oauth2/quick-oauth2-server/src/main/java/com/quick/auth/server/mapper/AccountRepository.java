package com.quick.auth.server.mapper;

import com.quick.auth.server.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/10/26
 * Time: 19:38
 * Description:
 */
@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByName(String name);
}

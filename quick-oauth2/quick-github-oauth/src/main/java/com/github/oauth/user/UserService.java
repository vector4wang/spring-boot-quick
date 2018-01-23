package com.github.oauth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User save(User entity) throws Exception {
        return userRepo.save(entity);
    }

    @Override
    public void delete(Long id) throws Exception {
        userRepo.delete(id);
    }

    @Override
    public void delete(User entity) throws Exception {
        userRepo.delete(entity);
    }

    @Override
    public User findById(Long id) {
        return userRepo.findOne(id);
    }

    @Override
    public User findBySample(User sample) {
        return userRepo.findOne(whereSpec(sample));
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public List<User> findAll(User sample) {
        return userRepo.findAll(whereSpec(sample));
    }

    @Override
    public Page<User> findAll(PageRequest pageRequest) {
        return userRepo.findAll(pageRequest);
    }

    @Override
    public Page<User> findAll(User sample, PageRequest pageRequest) {
        return userRepo.findAll(whereSpec(sample), pageRequest);
    }

    private Specification<User> whereSpec(final User sample){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (sample.getId()!=null){
                predicates.add(cb.equal(root.<Long>get("id"), sample.getId()));
            }

            if (StringUtils.hasLength(sample.getUsername())){
                predicates.add(cb.equal(root.<String>get("username"),sample.getUsername()));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User sample = new User();
        sample.setUsername(username);
        User user = findBySample(sample);

        if( user == null ){
            throw new UsernameNotFoundException(String.format("User with username=%s was not found", username));
        }

        return user;
    }
}
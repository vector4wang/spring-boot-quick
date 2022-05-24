package com.quick.crypt.test.service.impl;

import com.quick.crypt.test.base.BaseServiceImpl;
import com.quick.crypt.test.dao.UserDao;
import com.quick.crypt.test.entity.User;
import com.quick.crypt.test.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-05-18 16:52:03
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }



    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    @Override
    public int batchInsert(List<User> userList) {
        return this.userDao.insertBatch(userList);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public int insert(String name, String phone) {
        return userDao.insertStr(name, phone);
    }

    @Override
    public int batchSetInsert(Set<User> sets) {
        return userDao.insertBatch(sets);
    }
}

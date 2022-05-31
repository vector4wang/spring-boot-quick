package com.quick.crypt.test.service;

import com.quick.crypt.test.base.BaseService;
import com.quick.crypt.test.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-05-18 16:52:01
 */
public interface UserService extends BaseService<User> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);



    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    int batchInsert(List<User> userList);


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<User> findAll();

    int insert(String vector, String s);

    int batchSetInsert(Set<User> sets);
}

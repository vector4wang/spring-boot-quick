package com.quick.druid.sys.service.impl;

import com.quick.druid.sys.entity.User;
import com.quick.druid.sys.mapper.UserMapper;
import com.quick.druid.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vector4wang
 * @since 2019-12-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}

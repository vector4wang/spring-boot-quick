package com.quick.shardingjdbc.service.impl;

import com.quick.shardingjdbc.entity.Address;
import com.quick.shardingjdbc.mapper.AddressMapper;
import com.quick.shardingjdbc.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vector
 * @since 2022-08-16
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}

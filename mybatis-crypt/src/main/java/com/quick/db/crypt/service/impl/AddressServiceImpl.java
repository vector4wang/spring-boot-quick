package com.quick.db.crypt.service.impl;

import com.quick.db.crypt.dao.AddressDao;
import com.quick.db.crypt.entity.Address;
import com.quick.db.crypt.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Address)表服务实现类
 *
 * @author makejava
 * @since 2022-05-12 16:47:34
 */
@Service("addressService")
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressDao addressDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Address queryById(Integer id) {
        return this.addressDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param address 实例对象
     * @return 实例对象
     */
    @Override
    public Address insert(Address address) {
        this.addressDao.insert(address);
        return address;
    }

    /**
     * 修改数据
     *
     * @param address 实例对象
     * @return 实例对象
     */
    @Override
    public Address update(Address address) {
        this.addressDao.update(address);
        return this.queryById(address.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.addressDao.deleteById(id) > 0;
    }
}

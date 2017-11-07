package com.quick.druid.mapper;

import com.quick.druid.entity.Customers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: wangxc
 * @GitHub: https://github.com/vector4wang
 * @CSDN: http://blog.csdn.net/qqhjqs?viewmode=contents
 * @BLOG: http://vector4wang.tk
 * @wxid: BMHJQS
 */
@Mapper
public interface CustormersMapper {


    @Select("SELECT * FROM customers")
    List<Customers> selectList();
}

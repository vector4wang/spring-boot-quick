package com.quick.mapper;

import com.quick.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by bd2 on 2017/3/14.
 */
@Mapper
public interface AddressMapper {

    @Results(id="allLists",value={
            @Result(property = "addressId",column = "address_id",id=true),
            @Result(property = "address",column = "address"),
            @Result(property = "address2",column = "address2"),
            @Result(property = "district",column = "district"),
            @Result(property = "cityId",column = "city_id"),
            @Result(property = "postalCode",column = "postal_code"),
            @Result(property = "phone",column = "phone"),
            @Result(property = "location",column = "location"),
            @Result(property = "lastUpdate",column = "last_update")

    })
    @Select("SELECT * FROM address")
    List<Address> findList();
}

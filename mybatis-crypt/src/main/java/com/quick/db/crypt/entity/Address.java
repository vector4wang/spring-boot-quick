package com.quick.db.crypt.entity;

import java.io.Serializable;

/**
 * (Address)实体类
 *
 * @author makejava
 * @since 2022-05-12 16:47:30
 */
public class Address implements Serializable {
    private static final long serialVersionUID = -38039070201134402L;
    
    private Integer id;
    
    private Integer userId;
    
    private String address;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}


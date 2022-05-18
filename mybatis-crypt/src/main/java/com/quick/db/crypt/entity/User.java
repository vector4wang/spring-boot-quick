package com.quick.db.crypt.entity;

import com.quick.db.crypt.annotation.CryptEntity;
import com.quick.db.crypt.annotation.CryptField;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2022-05-18 16:51:55
 */
@Data
@Builder
@CryptEntity
public class User implements Serializable {
    private static final long serialVersionUID = -33370517147127073L;
    
    private Integer id;
    
    private String name;

    @CryptField
    private String phone;



}


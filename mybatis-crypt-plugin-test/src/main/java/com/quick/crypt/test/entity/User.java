package com.quick.crypt.test.entity;

import com.quick.db.crypt.annotation.CryptEntity;
import com.quick.db.crypt.annotation.CryptField;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2022-05-18 16:51:55
 */
@Data
@CryptEntity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -33370517147127073L;

    @Id
    private Integer id;

    private String name;

    @CryptField
    private String phone;

}


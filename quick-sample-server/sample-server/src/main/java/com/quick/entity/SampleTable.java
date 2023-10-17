package com.quick.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 *
 * </p>
 *
 * @author vector4wang
 * @version ${cfg.version}
 * @since 2023-10-17
 */
@Data
@Accessors(chain = true)
@TableName("sample_table")
public class SampleTable implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId("id")
    @TableField("id")
    private Long id;
    private String name;
    private String userCode;
    private String address;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}

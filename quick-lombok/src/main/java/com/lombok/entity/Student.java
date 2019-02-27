package com.lombok.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author vector
 * @date: 2019/2/27 0027 10:30
 */
@Data
@ToString(callSuper = true)
public class Student extends Person {
    private String stuNo;
    private String classNo;
}

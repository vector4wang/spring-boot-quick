package com.lombok.entity;

import lombok.Builder;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

/**
 * @author vector
 * @date: 2019/2/27 0027 10:50
 */
@Builder
@ToString
public class Adult {
    private String name;
    private int age;

    @Singular
    private List<String> cds;
}

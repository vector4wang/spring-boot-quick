package com.quick.redis.entity;

import java.io.Serializable;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/9/26
 * Time: 21:24
 * Description:
 */
public class Company implements Serializable {

    private String companyName;

    private Integer label;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", label=" + label +
                '}';
    }
}

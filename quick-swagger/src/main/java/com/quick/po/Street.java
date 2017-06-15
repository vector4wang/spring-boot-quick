package com.quick.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/6/15
 * Time: 20:55
 * Description:
 */
@ApiModel
public class Street {

    @ApiModelProperty(allowableValues = "街道")
    private String street;

    @ApiModelProperty(allowableValues = "门牌号")
    private int number;

    @Override
    public String toString() {
        return "Street{" +
                "street='" + street + '\'' +
                ", number=" + number +
                '}';
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

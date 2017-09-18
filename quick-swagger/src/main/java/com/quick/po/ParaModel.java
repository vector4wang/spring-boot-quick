package com.quick.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/6/15
 * Time: 20:54
 * Description:
 */
@ApiModel
public class ParaModel {

    @ApiModelProperty(allowableValues = "省")
    private String provice;

    @ApiModelProperty(allowableValues = "地区")
    private String area;


    private List<Street> streets;

    @Override
    public String toString() {
        return "ParaModel{" +
                "provice='" + provice + '\'' +
                ", area='" + area + '\'' +
                ", streets=" + streets +
                '}';
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }
}

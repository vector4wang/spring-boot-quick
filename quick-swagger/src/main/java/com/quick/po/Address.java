package com.quick.po;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/12
 * Time: 8:43
 * Description:
 */
public class Address implements Serializable {

	@ApiModelProperty(name = "province",example = "广东省")
    private String province;

	@ApiModelProperty(name = "area",example = "地域")
    private String area;

	@ApiModelProperty(name = "street",example = "街道")
    private String street;

	@ApiModelProperty(name = "num",example = "号码")
    private String num;

    public Address(){}

	public Address(String province, String area, String street, String num) {
		this.province = province;
		this.area = area;
		this.street = street;
		this.num = num;
	}

	@Override
    public String toString() {
        return "Address{" +
                "province='" + province + '\'' +
                ", area='" + area + '\'' +
                ", street='" + street + '\'' +
                ", num='" + num + '\'' +
                '}';
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}

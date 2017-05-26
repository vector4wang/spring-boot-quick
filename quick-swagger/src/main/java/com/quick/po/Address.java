package com.quick.po;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/12
 * Time: 8:43
 * Description:
 */
public class Address {
    private String province;
    private String area;
    private String street;
    private String num;

    public Address(){}


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

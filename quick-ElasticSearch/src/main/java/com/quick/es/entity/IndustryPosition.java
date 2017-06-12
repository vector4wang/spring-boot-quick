package com.quick.es.entity;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/6/8
 * Time: 16:16
 * Description:
 */
public class IndustryPosition {
    private String industry; // 行业
    private String position; // 职位

    public IndustryPosition(String industry, String position) {
        this.industry = industry;
        this.position = position;
    }

    @Override
    public String toString() {
        return "IndustryPosition{" +
                "industry='" + industry + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

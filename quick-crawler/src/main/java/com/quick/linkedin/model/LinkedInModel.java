package com.quick.linkedin.model;

/**
 * Created by bd2 on 2017/3/9.
 */
public class LinkedInModel {

    private int id;
    private String firstName;
    private String lastName;
    private String industryName; // 服务行业
    private String headline; // 当前的头衔
    private String address;
    private String educations;
    private String experiences;
    private String skills;
    private String uniqueUrl; // 个人链接，独一无二
    private String insertTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducations() {
        return educations;
    }

    public void setEducations(String educations) {
        this.educations = educations;
    }

    public String getExperiences() {
        return experiences;
    }

    public void setExperiences(String experiences) {
        this.experiences = experiences;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getUniqueUrl() {
        return uniqueUrl;
    }

    public void setUniqueUrl(String uniqueUrl) {
        this.uniqueUrl = uniqueUrl;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public String toString() {
        return "LinkedInModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", industryName='" + industryName + '\'' +
                ", headline='" + headline + '\'' +
                ", address='" + address + '\'' +
                ", educations='" + educations + '\'' +
                ", experiences='" + experiences + '\'' +
                ", skills='" + skills + '\'' +
                ", uniqueUrl='" + uniqueUrl + '\'' +
                ", insertTime='" + insertTime + '\'' +
                '}';
    }
}

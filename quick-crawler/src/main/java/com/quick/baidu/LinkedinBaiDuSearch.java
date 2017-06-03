package com.quick.baidu;

import java.util.Date;

public class LinkedinBaiDuSearch {

    public final static String YES = "YES";
    public final static String NO = "NO";
    public final static String OTHER = "OTHER";

    private Integer id;

    private String search;

    private String name;

    private String summary;

    private String url;

    private String crawled;

    private Date insertTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search == null ? null : search.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getCrawled() {
        return crawled;
    }

    public void setCrawled(String crawled) {
        this.crawled = crawled == null ? null : crawled.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public String toString() {
        return "LinkedinBaiDuSearch{" +
                "id=" + id +
                ", search='" + search + '\'' +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", url='" + url + '\'' +
                ", crawled='" + crawled + '\'' +
                ", insertTime=" + insertTime +
                '}';
    }
}
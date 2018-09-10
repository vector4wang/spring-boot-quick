package com.crawler.vw.entity;

import com.github.vector4wang.annotation.CssSelector;
import com.github.vector4wang.util.SelectType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Blog implements Serializable {

	@CssSelector(selector = "#mainBox > main > div.blog-content-box > div.article-header-box > div > div.article-info-box > div > span.time", dateFormat = "yyyy年MM月dd日 HH:mm:ss")
	private Date publishDate;

	@CssSelector(selector = "main > div.blog-content-box > div.article-header-box > div.article-header>div.article-title-box > h1", resultType = SelectType.TEXT)
	private String title;

	@CssSelector(selector = "main > div.blog-content-box > div.article-header-box > div.article-header>div.article-info-box > div > div > span.read-count", resultType = SelectType.TEXT)
	private String readCountStr;

	private int readCount;

	@CssSelector(selector = "#article_content",resultType = SelectType.TEXT)
	private String content;

	@CssSelector(selector = "body > div.tool-box > ul > li:nth-child(1) > button > p",resultType = SelectType.TEXT)
	private int likeCount;

	/**
	 * 暂时不支持自动解析列表的功能，所以加个中间变量，需要二次解析下
	 */
	@CssSelector(selector = "#mainBox > main > div.comment-box > div.comment-list-container > div.comment-list-box",resultType = SelectType.HTML)
	private String comentTmp;

	private String url;

	private String urlMd5;

	private List<String> comment;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public String getReadCountStr() {
		return readCountStr;
	}

	public void setReadCountStr(String readCountStr) {
		this.readCountStr = readCountStr;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getComentTmp() {
		return comentTmp;
	}

	public void setComentTmp(String comentTmp) {
		this.comentTmp = comentTmp;
	}

	public List<String> getComment() {
		return comment;
	}

	public void setComment(List<String> comment) {
		this.comment = comment;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlMd5() {
		return urlMd5;
	}

	public void setUrlMd5(String urlMd5) {
		this.urlMd5 = urlMd5;
	}

	@Override
	public String toString() {
		return "Blog{" + "title='" + title + '\'' + ", publishDate=" + publishDate + ", readCountStr='" + readCountStr
				+ '\'' + ", readCount=" + readCount + ", content='" + content + '\'' + ", likeCount=" + likeCount
				+ ", comentTmp='" + comentTmp + '\'' + ", url='" + url + '\'' + ", comment=" + comment + '}';
	}
}

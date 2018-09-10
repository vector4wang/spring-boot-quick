package com.quick.feign.entity;

/**
 * @author vector
 * @Data 2018/9/10 0010
 * @Description
 */
public class DomainDetail {

	/**
	 * id : 11000002000016
	 * sitename : 新浪网
	 * sitedomain : sina.com.cn
	 * sitetype : 交互式
	 * cdate : 2016-01-21
	 * comtype : 企业单位
	 * comname : 北京新浪互联信息服务有限公司
	 * comaddress : 北京市网安总队
	 * updateTime : 2017-09-09
	 */

	private String id;
	private String sitename;
	private String sitedomain;
	private String sitetype;
	private String cdate;
	private String comtype;
	private String comname;
	private String comaddress;
	private String updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getSitedomain() {
		return sitedomain;
	}

	public void setSitedomain(String sitedomain) {
		this.sitedomain = sitedomain;
	}

	public String getSitetype() {
		return sitetype;
	}

	public void setSitetype(String sitetype) {
		this.sitetype = sitetype;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getComtype() {
		return comtype;
	}

	public void setComtype(String comtype) {
		this.comtype = comtype;
	}

	public String getComname() {
		return comname;
	}

	public void setComname(String comname) {
		this.comname = comname;
	}

	public String getComaddress() {
		return comaddress;
	}

	public void setComaddress(String comaddress) {
		this.comaddress = comaddress;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "DomainDetail{" + "id='" + id + '\'' + ", sitename='" + sitename + '\'' + ", sitedomain='" + sitedomain
				+ '\'' + ", sitetype='" + sitetype + '\'' + ", cdate='" + cdate + '\'' + ", comtype='" + comtype + '\''
				+ ", comname='" + comname + '\'' + ", comaddress='" + comaddress + '\'' + ", updateTime='" + updateTime
				+ '\'' + '}';
	}
}

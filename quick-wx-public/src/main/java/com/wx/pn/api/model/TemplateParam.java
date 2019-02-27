package com.wx.pn.api.model;

public class TemplateParam extends BaseModel {

    /**
     * 值
     */
    private String value;
    /**
     * 颜色
     */
    private String color;



    public TemplateParam() {
		super();
	}

	public TemplateParam(String value) {
		super();
		this.value = value;
	}

	public TemplateParam(String value, String color) {
		super();
		this.value = value;
		this.color = color;
	}

	public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

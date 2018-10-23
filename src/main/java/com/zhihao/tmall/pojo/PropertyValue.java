package com.zhihao.tmall.pojo;

import java.io.Serializable;

public class PropertyValue implements Serializable {
	private static final long serialVersionUID = 6595840217244601581L;

	private Integer id;

    private Integer pid;

    private Integer ptid;

    private String value;
    
    /*非数据库字段*/
    private Property property;
 
    public Property getProperty() {
        return property;
    }
 
    public void setProperty(Property property) {
        this.property = property;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPtid() {
        return ptid;
    }

    public void setPtid(Integer ptid) {
        this.ptid = ptid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
}
package com.zhihao.tmall.pojo;

import java.io.Serializable;

public class Property implements Serializable {
	private static final long serialVersionUID = -5474596876211520903L;

	private Integer id;

    private Integer cid;

    private String name;

    private Category category;   
		
    // 非数据库字段
    public Category getCategory() {
    	return category;
		}
    
    public void setCategory(Category category) {
    	this.category = category;
		}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}
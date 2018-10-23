package com.zhihao.tmall.pojo;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1108705528719818579L;
	private Integer id;
    private Integer status;
    private String name;
    private String password;
    private String mailAccount;
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    
    public String getMailAccount() {
		return mailAccount;
	}

	public void setMailAccount(String mailAccount) {
		this.mailAccount = mailAccount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAnonymousName(){
        if(null==name)
            return null;
 
        if(name.length()<=1)
            return "*";
 
        if(name.length()==2)
            return name.substring(0,1) +"*";
 
        char[] cs =name.toCharArray();
        for (int i = 1; i < cs.length-1; i++) {
            cs[i]='*';
        }
        return new String(cs);
 
    }
}
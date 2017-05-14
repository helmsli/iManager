package com.xinwei.process.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.xinwei.security.entity.User;

public class PublishOptimize implements Serializable{
     //优化时间
	 private Date createTime;
     //优化人
     private Long userid;
     //优化人名字
     private String username;
     //优化人单位
     private String company;
     //优化附件
     private String attatchment;
     //优化扩展信息1
     private String data1;
     //优化扩展信息2
     private String data2;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAttatchment() {
		return attatchment;
	}
	public void setAttatchment(String attatchment) {
		this.attatchment = attatchment;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
     
     public void setUser(User user)
     {
    	 this.setUserid(user.getId());
    	 this.setUsername(user.getUsername());
    	 this.setCompany(user.getCompany_name());
     }
     
     public void setNow()
     {
    	 Date now = Calendar.getInstance().getTime();
    	 this.setCreateTime(now);
     }
}

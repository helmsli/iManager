package com.xinwei.process.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
/**
 * 部门经理发布
 *
 */
public class DepartleaderPublish {
	
	public static final int ALLOW = 0;//允许申报
	public static final int FORBIDEN = 1;//不允许申报
	public static final int IN_PROCESS =2;//已经申报，正在进行中
	
	public static final String DISPATCH_PROJECTMANAGER = "0";//指定项目经理申报；
	public static final String DISPATCH_THREELEADER = "1";//指定三级部门经理申报
	
    private Long publishId;//发布ID

    private String title;//发布名称

    private Long categoryId;//种类ID：
    
    private String serviceType;//类型

    private String description;//描述

    private Date createTime; //创建时间

    private String createPerson;//创建者

    private String data1;//扩展数据区1

    private String data2;//扩展数据区2

    private String data3;//扩展数据区3

    private String data4;//扩展数据区4

    private String data5;//扩展数据区5

    private String data6;//扩展数据区6
 
    private String data7;//扩展数据区7

    private String data8;//扩展数据区8

    private String data9;//扩展数据区9

    private String data10;//扩展数据区10
 
    public Long getPublishId() {
		return publishId;
	}


	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Long getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}


	public String getServiceType() {
		return serviceType;
	}


	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getCreatePerson() {
		return createPerson;
	}


	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
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


	public String getData3() {
		return data3;
	}


	public void setData3(String data3) {
		this.data3 = data3;
	}


	public String getData4() {
		return data4;
	}


	public void setData4(String data4) {
		this.data4 = data4;
	}


	public String getData5() {
		return data5;
	}


	public void setData5(String data5) {
		this.data5 = data5;
	}


	public String getData6() {
		return data6;
	}


	public void setData6(String data6) {
		this.data6 = data6;
	}


	public String getData7() {
		return data7;
	}


	public void setData7(String data7) {
		this.data7 = data7;
	}


	public String getData8() {
		return data8;
	}


	public void setData8(String data8) {
		this.data8 = data8;
	}


	public String getData9() {
		return data9;
	}


	public void setData9(String data9) {
		this.data9 = data9;
	}


	public String getData10() {
		return data10;
	}


	public void setData10(String data10) {
		this.data10 = data10;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
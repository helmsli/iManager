package com.xinwei.process.entity;

import java.util.Date;
/**
 * 项目相关材料类
 * @author xuejinku
 *
 */
public class ProjectMaterial {
	//数据ID
	private String dataId;
	//名称
	private String name;
	//类型
	private String type;
	//项目ID
    private String projectId;
    // 创建者
 	private String createPerson;
 	// 创建时间
 	private Date createTime;
 	// 下载URL
 	private String downLoadURL;
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDownLoadURL() {
		return downLoadURL;
	}
	public void setDownLoadURL(String downLoadURL) {
		this.downLoadURL = downLoadURL;
	}
	
	@Override
	public String toString() {
		return "ProjectMaterial [dataId=" + dataId + ", name=" + name
				+ ", type=" + type + ", projectId=" + projectId
				+ ", createPerson=" + createPerson + ", createTime="
				+ createTime + ", downLoadURL=" + downLoadURL + "]";
	}
 	
}

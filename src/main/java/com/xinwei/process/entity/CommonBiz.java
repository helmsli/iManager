package com.xinwei.process.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

//尽职调查，更新
public class CommonBiz{
	// 数据标识
	private String dataId;
	// 类型标识
	private String projectCategory;
	// 项目标识
	private Long projectId;
	// 项目名称
	private String projectName;
	// 业务类型
	private String serviceType;
	// 创建者ID
	private String createPerson;
	// 创建者名称
	private String createPersonName;
	// 创建时间
	private Date createTime;
	// 更新者
	private String updatePerson;

	// 更新时间
	private Date updateTime;
	// 结论
	private String result;
	// 业务归属
	private String serviceOwner;
	// 流程ID
	private String processInstanceId;
	// 任务ID
	private String taskId;
	// 扩展流程信息
	private String extActivitiInfo;
	// 状态
	private String status;
	// 扩展状态
	private String extStatus;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String data5;
	private String data6;
	private String data7;
	private String data8;
	private String data9;
	private String data10;
	public String getCreatePerson() {
		return createPerson;
	}
	public String getCreatePersonName() {
		return createPersonName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public String getData1() {
		return data1;
	}

	public String getData10() {
		return data10;
	}

	public String getData2() {
		return data2;
	}

	public String getData3() {
		return data3;
	}

	public String getData4() {
		return data4;
	}

	public String getData5() {
		return data5;
	}

	public String getData6() {
		return data6;
	}

	public String getData7() {
		return data7;
	}

	public String getData8() {
		return data8;
	}

	public String getData9() {
		return data9;
	}

	public String getDataId() {
		return dataId;
	}

	public String getExtActivitiInfo() {
		return extActivitiInfo;
	}


	public String getExtStatus() {
		return extStatus;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getProjectCategory() {
		return projectCategory;
	}

	public Long getProjectId() {
		return projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	
	public String getResult() {
		return result;
	}

	public String getServiceOwner() {
		return serviceOwner;
	}

	public String getServiceType() {
		return serviceType;
	}

	public String getStatus() {
		return status;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	

	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	public void setData10(String data10) {
		this.data10 = data10;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

	public void setData6(String data6) {
		this.data6 = data6;
	}

	public void setData7(String data7) {
		this.data7 = data7;
	}

	public void setData8(String data8) {
		this.data8 = data8;
	}

	public void setData9(String data9) {
		this.data9 = data9;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public void setExtActivitiInfo(String extActivitiInfo) {
		this.extActivitiInfo = extActivitiInfo;
	}

	public void setExtStatus(String extStatus) {
		this.extStatus = extStatus;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setServiceOwner(String serviceOwner) {
		this.serviceOwner = serviceOwner;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
package com.xinwei.process.entity;

import com.xinwei.util.JsonUtil;

/**
 * 状态信息
 *
 */
public class StateInfo {

	private String state;//当前状态
	private String stateName;//当前状态名
	private String taskId;//任务ID
	private String assignName;//任务办理人姓名、或组名
	
	public StateInfo() {
		super();
	}
	
	public StateInfo(String state, String stateName, String taskId) {
		this.state = state;
		this.stateName = stateName;
		this.taskId = taskId;
	}
	
	public StateInfo(String state, String stateName, String taskId,String assignName) {
		this.state = state;
		this.stateName = stateName;
		this.taskId = taskId;
		this.assignName = assignName;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}	
	public String getAssignName() {
		return assignName;
	}
	public void setAssignName(String assignName) {
		this.assignName = assignName;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}

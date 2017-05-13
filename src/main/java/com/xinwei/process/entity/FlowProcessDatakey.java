package com.xinwei.process.entity;

public class FlowProcessDatakey {
	public static final int PUBLISH = 1;
	public static final int PROJECT = 2;
	private int type;
	private Long projectId;

	public FlowProcessDatakey(int type,long projectId)
	{
		this.type = type;
		this.projectId = projectId;
	}
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

}

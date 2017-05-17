package com.xinwei.process.entity;

import java.util.List;

public class ProjectExtInfo {
	   private String taskDescribe;
	   private String taskbackground;
	   private String taskgoal;
	   private String taskDanger;
	   private String taskcreate;
	   private String taskplan;
	   private List<ProjectAnnex> newApplyAttachment;
	   private String pWordInfo;
	public String getTaskDescribe() {
		return taskDescribe;
	}
	public void setTaskDescribe(String taskDescribe) {
		this.taskDescribe = taskDescribe;
	}
	public String getTaskbackground() {
		return taskbackground;
	}
	public void setTaskbackground(String taskbackground) {
		this.taskbackground = taskbackground;
	}
	public String getTaskgoal() {
		return taskgoal;
	}
	public void setTaskgoal(String taskgoal) {
		this.taskgoal = taskgoal;
	}
	public String getTaskDanger() {
		return taskDanger;
	}
	public void setTaskDanger(String taskDanger) {
		this.taskDanger = taskDanger;
	}
	public String getTaskcreate() {
		return taskcreate;
	}
	public void setTaskcreate(String taskcreate) {
		this.taskcreate = taskcreate;
	}
	public String getTaskplan() {
		return taskplan;
	}
	public void setTaskplan(String taskplan) {
		this.taskplan = taskplan;
	}
	
	public List<ProjectAnnex> getNewApplyAttachment() {
		return newApplyAttachment;
	}
	public void setNewApplyAttachment(List<ProjectAnnex> newApplyAttachment) {
		this.newApplyAttachment = newApplyAttachment;
	}
	public String getpWordInfo() {
		return pWordInfo;
	}
	public void setpWordInfo(String pWordInfo) {
		this.pWordInfo = pWordInfo;
	}
	   
	   
}

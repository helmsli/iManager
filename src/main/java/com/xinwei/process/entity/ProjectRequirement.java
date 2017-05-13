package com.xinwei.process.entity;

import java.util.Date;

public class ProjectRequirement {
	//需求ID
    private Long requirementId;
    //项目类型ID
    private Long categoryId;
    //发布时间
    private Date publishTime;
   //需求状态
    private String state;
    //需求描述
    private String requirementDesc;
    //发布者
    private Long userId;
  //预期完成时间
    private Date completeTime;
   //奖金
    private Long reward;

    public Long getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRequirementDesc() {
        return requirementDesc;
    }

    public void setRequirementDesc(String requirementDesc) {
        this.requirementDesc = requirementDesc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Long getReward() {
        return reward;
    }

    public void setReward(Long reward) {
        this.reward = reward;
    }

	@Override
	public String toString() {
		return "ProjectRequirement [requirementId=" + requirementId
				+ ", categoryId=" + categoryId + ", publishTime=" + publishTime
				+ ", state=" + state + ", requirementDesc=" + requirementDesc
				+ ", userId=" + userId + ", completeTime=" + completeTime
				+ ", reward=" + reward + "]";
	}
    
    
}
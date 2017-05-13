package com.xinwei.process.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xinwei.util.JsonUtil;

/**
 * 项目对象
 */
public class Project {
	
	public static final String APPLY = "apply";//项目经理申报；
	public static final String CREATE = "create";//项目经理自己创建
	//项目ID 
    private Long projectId;
    //项目名称
    private String projectName;
    //项目经理ID(项目负责人)
    private Long projectManagerId;
    //项目负责人姓名
    private String projectManagerName;
	//项目类型ID(项目类别)
    private Long categoryId;
   //项目子类型(项目子类别)
    private String subcategory;
	//项目周期(按月还是周计)
    private String cycleType;
    //项目主流程当前状态信息
    private String mainCurrentState;
    //项目主流程上一状态信息
    private String mainPreviousState;
    //项目更改流程当前状态信息
    private String changeCurrentState;
    //项目更改流程上一状态信息
    private String changePreviousState;
    //项目更改数据ID
    private String changeDataId;
    //周期性报告当前状态
    private String reportCurrentState;
    //项目更改流程实例ID
    private String changeProcessInstanceId;
	//项目流程实例ID
    private String projectProcessInstanceId;
    //项目扩展信息
    private String projectExtInfo;
    //项目成员分工信息
    private String projectTaskDetail;
    //项目经理自评
    private String selfAppraise;
    //部门经理评价
    private String departLeaderAppraise;
    //项目预算
    private String projectCosts;
    //项目开始时间
    private Date startTime;
    //项目完成时间
    private Date completeTime;
    //项目 申请时间
    private Date projectApplyTime;
    //项目 申请人
    private String projectApplyPerson;
	//联系电话
    private String telno;
	//电子邮箱
    private String email;
	//项目里程碑
    private String projectMilestone;
    //部门经理发布Id
    private Long publishId;
    //部门经理发布主题
    private String publishTitle;
    //项目状态（正在进行、结项）
    private String state;
    public Long getCategoryId() {
        return categoryId;
    }
    public String getChangeCurrentState() {
        return changeCurrentState;
    }
    
    public String getChangeCurrentStateFromStatusInfo() {
    	String state = "";
        try {
			StateInfo changeCurrentStateInfo = JsonUtil.fromJson(getChangeCurrentState(),StateInfo.class);
			if (changeCurrentStateInfo!=null) {
				 state = changeCurrentStateInfo.getState();
			}
			return state;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }

    public String getChangeDataId() {
        return changeDataId;
    }

    public String getChangePreviousState() {
        return changePreviousState;
    }

    public String getChangeProcessInstanceId() {
        return changeProcessInstanceId;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public String getCycleType() {
        return cycleType;
    }

    public String getDepartLeaderAppraise() {
        return departLeaderAppraise;
    }

    public String getEmail() {
        return email;
    }

    public String getMainCurrentState() {
        return mainCurrentState;
    }

    public String getMainPreviousState() {
        return mainPreviousState;
    }

    public String getProjectApplyPerson() {
		return projectApplyPerson;
	}

    public Date getProjectApplyTime() {
        return projectApplyTime;
    }

    public String getProjectCosts() {
        return projectCosts;
    }

    public String getProjectExtInfo() {
        return projectExtInfo;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getProjectManagerId() {
        return projectManagerId;
    }

    public String getProjectManagerName() {
        return projectManagerName;
    }

    public String getProjectMilestone() {
        return projectMilestone;
    }

    public String getProjectName() {
        return projectName;
    }
    
   
    
    public String getProjectProcessInstanceId() {
        return projectProcessInstanceId;
    }

    public String getProjectTaskDetail() {
        return projectTaskDetail;
    }

    public String getReportCurrentState() {
        return reportCurrentState;
    }

    public String getSelfAppraise() {
        return selfAppraise;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getState() {
		return state;
	}

    public String getSubcategory() {
        return subcategory;
    }

    public String getTelno() {
        return telno;
    }

    /**
     * 
     * @param state -- getTaskDefinitionKey
     * @param taskId  -- taskId
     * @param taskName -- TaskName
     * @return
     */
    public String makeChangeCurrentStateFromStatusInfo(String state,String taskId,String taskName) {
        
        try {
        	StateInfo changeCurrentStateInfo = new StateInfo();
    		changeCurrentStateInfo.setState(state);
    		changeCurrentStateInfo.setStateName(taskName);
    		changeCurrentStateInfo.setTaskId(taskId);
    		//更改流程当前任务状态信息
    		String changeCurrentStateInfoJson = JsonUtil.toJson(changeCurrentStateInfo);
    		return changeCurrentStateInfoJson;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setChangeCurrentState(String changeCurrentState) {
        this.changeCurrentState = changeCurrentState;
    }

    public void setChangeDataId(String changeDataId) {
        this.changeDataId = changeDataId;
    }

    public void setChangePreviousState(String changePreviousState) {
        this.changePreviousState = changePreviousState;
    }

    public void setChangeProcessInstanceId(String changeProcessInstanceId) {
        this.changeProcessInstanceId = changeProcessInstanceId;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType;
    }

    public void setDepartLeaderAppraise(String departLeaderAppraise) {
        this.departLeaderAppraise = departLeaderAppraise;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMainCurrentState(String mainCurrentState) {
        this.mainCurrentState = mainCurrentState;
    }

    public void setMainPreviousState(String mainPreviousState) {
        this.mainPreviousState = mainPreviousState;
    }

    public void setProjectApplyPerson(String projectApplyPerson) {
		this.projectApplyPerson = projectApplyPerson;
	}

    public void setProjectApplyTime(Date projectApplyTime) {
        this.projectApplyTime = projectApplyTime;
    }

    public void setProjectCosts(String projectCosts) {
        this.projectCosts = projectCosts;
    }

    public void setProjectExtInfo(String projectExtInfo) {
        this.projectExtInfo = projectExtInfo;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setProjectManagerId(Long projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public void setProjectMilestone(String projectMilestone) {
        this.projectMilestone = projectMilestone;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectProcessInstanceId(String projectProcessInstanceId) {
        this.projectProcessInstanceId = projectProcessInstanceId;
    }

    public void setProjectTaskDetail(String projectTaskDetail) {
        this.projectTaskDetail = projectTaskDetail;
    }

    public void setReportCurrentState(String reportCurrentState) {
        this.reportCurrentState = reportCurrentState;
    }

    public void setSelfAppraise(String selfAppraise) {
        this.selfAppraise = selfAppraise;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setState(String state) {
		this.state = state;
	}

    
	public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

	public void setTelno(String telno) {
        this.telno = telno;
    }
	public Long getPublishId() {
		return publishId;
	}
	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}
	
	public String getPublishTitle() {
		return publishTitle;
	}
	public void setPublishTitle(String publishTitle) {
		this.publishTitle = publishTitle;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

package com.xinwei.process.entity;

import java.util.Date;
import java.util.List;

public class ExpertReview {//tb_ expert_review专家评审记录表
    private Long recordId;

    private Long projectId;
    //评审阶段(初期，中期，终期)
    private String stage;

    private Date time;
    //评审专家列表
    private String expertList;

    private String room;

    private String expertScore;
    
    //评审材料
    private List<ProjectAnnex> viewMaterials;

	//评审报告
    private List<ProjectAnnex> viewReports;

	public String getExpertList() {
        return expertList;
    }

	public String getExpertScore() {
        return expertScore;
    }

	public Long getProjectId() {
        return projectId;
    }
    public Long getRecordId() {
        return recordId;
    }

    public String getRoom() {
        return room;
    }

    public String getStage() {
        return stage;
    }

    public Date getTime() {
        return time;
    }

    public List<ProjectAnnex> getViewMaterials() {
		return viewMaterials;
	}

	public void setViewMaterials(List<ProjectAnnex> viewMaterials) {
		this.viewMaterials = viewMaterials;
	}

	public List<ProjectAnnex> getViewReports() {
		return viewReports;
	}

	public void setViewReports(List<ProjectAnnex> viewReports) {
		this.viewReports = viewReports;
	}
    public void setExpertList(String expertList) {
        this.expertList = expertList;
    }

    public void setExpertScore(String expertScore) {
        this.expertScore = expertScore;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setTime(Date time) {
        this.time = time;
    }

	@Override
	public String toString() {
		return "ExpertReview [recordId=" + recordId + ", projectId="
				+ projectId + ", stage=" + stage + ", time=" + time
				+ ", expertList=" + expertList + ", room=" + room
				+ ", expertScore=" + expertScore + ", viewMaterials="
				+ viewMaterials + ", viewReports=" + viewReports + "]";
	}

}
package com.xinwei.process.entity;
import java.util.List;
import java.util.Map;
//项目详情
public class ProjectDetailInfo {
	private Project project;
	//key:stage
	//value:初期{专家评审、决策委员会评审、部门经理评审}  中期{评审材料、专家评分、评审报告、决策委员会评审、部门经理评审}  终期{评审材料、专家评分、评审报告、决策委员会评审、部门经理评审}
	Map<String,Object> beginStage;  
	Map<String,Object> middleStage;  
	Map<String,Object> lastStage;  
	
	//周期性报告URL
    private List<ProjectAnnex> projectAnnexLists;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Map<String, Object> getBeginStage() {
		return beginStage;
	}

	public void setBeginStage(Map<String, Object> beginStage) {
		this.beginStage = beginStage;
	}

	public Map<String, Object> getMiddleStage() {
		return middleStage;
	}

	public void setMiddleStage(Map<String, Object> middleStage) {
		this.middleStage = middleStage;
	}

	public Map<String, Object> getLastStage() {
		return lastStage;
	}

	public void setLastStage(Map<String, Object> lastStage) {
		this.lastStage = lastStage;
	}

	public List<ProjectAnnex> getProjectAnnexLists() {
		return projectAnnexLists;
	}

	public void setProjectAnnexLists(List<ProjectAnnex> projectAnnexLists) {
		this.projectAnnexLists = projectAnnexLists;
	}

	@Override
	public String toString() {
		return "ProjectDetailInfo [project=" + project + ", beginStage="
				+ beginStage + ", middleStage=" + middleStage + ", lastStage="
				+ lastStage + ", projectAnnexLists=" + projectAnnexLists + "]";
	}
}

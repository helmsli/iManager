package com.xinwei.process.entity;

/**
 * 周期报告
 *
 */
public class MonthlyReport {

	private String specialExplain;//特殊情况说明
	private String innovation;//创新点和亮点
	private String changeConditions;//项目变更情况
	private String riskDescription;//风险说明
	private String summary;//经验总结
	private String improvementPlan;//改进计划
	
	public String getSpecialExplain() {
		return specialExplain;
	}
	public void setSpecialExplain(String specialExplain) {
		this.specialExplain = specialExplain;
	}
	public String getInnovation() {
		return innovation;
	}
	public void setInnovation(String innovation) {
		this.innovation = innovation;
	}
	public String getChangeConditions() {
		return changeConditions;
	}
	public void setChangeConditions(String changeConditions) {
		this.changeConditions = changeConditions;
	}
	public String getRiskDescription() {
		return riskDescription;
	}
	public void setRiskDescription(String riskDescription) {
		this.riskDescription = riskDescription;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getImprovementPlan() {
		return improvementPlan;
	}
	public void setImprovementPlan(String improvementPlan) {
		this.improvementPlan = improvementPlan;
	}
	
}

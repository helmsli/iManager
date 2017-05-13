package com.xinwei.process.entity;

public class MonthlyCheck {
	private String score;//评分
	private String reviewNum;//评审会议次数
	private String fileNum;//输出文档个数
	private String codeNum;//代码走读次数
	private String whetherAdjust;//有无调整
	private String adjustBefore;//调整前情况
	private String adjustAfter;//调整后情况
	private String adjustWhy;//调整理由
	private String opinion;//意见
	private String examine;//审批情况
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getReviewNum() {
		return reviewNum;
	}
	public void setReviewNum(String reviewNum) {
		this.reviewNum = reviewNum;
	}
	public String getFileNum() {
		return fileNum;
	}
	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}
	public String getCodeNum() {
		return codeNum;
	}
	public void setCodeNum(String codeNum) {
		this.codeNum = codeNum;
	}
	public String getWhetherAdjust() {
		return whetherAdjust;
	}
	public void setWhetherAdjust(String whetherAdjust) {
		this.whetherAdjust = whetherAdjust;
	}
	public String getAdjustBefore() {
		return adjustBefore;
	}
	public void setAdjustBefore(String adjustBefore) {
		this.adjustBefore = adjustBefore;
	}
	public String getAdjustAfter() {
		return adjustAfter;
	}
	public void setAdjustAfter(String adjustAfter) {
		this.adjustAfter = adjustAfter;
	}
	public String getAdjustWhy() {
		return adjustWhy;
	}
	public void setAdjustWhy(String adjustWhy) {
		this.adjustWhy = adjustWhy;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getExamine() {
		return examine;
	}
	public void setExamine(String examine) {
		this.examine = examine;
	}
	
	
}

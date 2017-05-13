package com.xinwei.process.entity;

/**
 * 项目进行中的活动
 *
 */
public class ReportActivity {

	private String id;//活动序号
	private String time;//活动时间
	private String title;//活动名称
	private String content;//活动内容
	private String produce;//产出
	private String reviewTimes;//评审次数
	private String codeTimes;//代码走读次数
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getProduce() {
		return produce;
	}
	public void setProduce(String produce) {
		this.produce = produce;
	}
	public String getReviewTimes() {
		return reviewTimes;
	}
	public void setReviewTimes(String reviewTimes) {
		this.reviewTimes = reviewTimes;
	}
	public String getCodeTimes() {
		return codeTimes;
	}
	public void setCodeTimes(String codeTimes) {
		this.codeTimes = codeTimes;
	}
					
}

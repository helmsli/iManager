package com.xinwei.process.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 部门经理发布与指定的申报人
 * @author xuejinku
 *
 */
public class PublishApplyPerson {
	
    private Long publishId;//发布Id

    private Long applyPersonId;//指定的申报人

    private Long projectId;//申报的项目Id

    private String status;//申报项目状态

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
    }

    public Long getApplyPersonId() {
        return applyPersonId;
    }

    public void setApplyPersonId(Long applyPersonId) {
        this.applyPersonId = applyPersonId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
    
}
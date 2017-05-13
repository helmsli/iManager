package com.xinwei.process.entity;

import java.util.Date;

/**
 * 项目附件对象
 */
public class ProjectAnnex {
	
	//附件ID 
    private Long annexId;
    //附件名称
    private String annexName;
    //上传附件的原始名称
    private String originalFilename;
    //附件类型ID（附件、中期评审材料、中期评审报告、终期评审材料、终期评审报告、项目周期报告）
    private Long typeId;
    //项目ID
    private Long projectId;
    //附件服务器存储路径
    private String path;
    //文件上传者
    private String userId;
    // 文件上传时间
    private Date uploadTime;
    
    public Long getAnnexId() {
        return annexId;
    }

    public void setAnnexId(Long annexId) {
        this.annexId = annexId;
    }

    public String getAnnexName() {
        return annexName;
    }

    public void setAnnexName(String annexName) {
        this.annexName = annexName;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }
    
    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Override
	public String toString() {
		return "ProjectAnnex [annexId=" + annexId + ", annexName=" + annexName
				+ ", originalFilename=" + originalFilename + ", typeId="
				+ typeId + ", projectId=" + projectId + ", path=" + path
				+ ", userId=" + userId + ", uploadTime=" + uploadTime + "]";
	}

}
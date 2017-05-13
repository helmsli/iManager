package com.xinwei.process.entity;

import java.util.Date;

import com.xinwei.process.constant.ApprovedConstants;
/*单位表*/
public class Company {
	//单位ID 
    private Long companyId;
    //单位名称
    private String companyName;
    //单位性质
    private String companyProperty;
    //证件类型
    private String certificateType;
    //证件ID
    private String certificateId;
    //注册地址
    private String registerAddress;
    //公司地址
    private String businessAddress;
    //联系人
    private String linkMan;
    //联系电话
    private String linkTel;
    //email
    private String email;
    //创建人
    private String createPerson;
    //创建时间
    private Date createTime;
    //审核人
    private String approvePerson;
	//审核时间
    private Date approveTime;
	//当前状态（0：待审核，1：审核通过，2：审核不通过）
    private String currentStatus = ApprovedConstants.CODE_NOT_APPROVED.toString();//初始为待审核
    
    public String getApprovePerson() {
        return approvePerson;
    }
    public Date getApproveTime() {
        return approveTime;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyProperty() {
        return companyProperty;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public Date getCreateTime() {
		return createTime;
	}

    public String getCurrentStatus() {
        return currentStatus;
    }

    public String getEmail() {
        return email;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setApprovePerson(String approvePerson) {
        this.approvePerson = approvePerson;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyProperty(String companyProperty) {
        this.companyProperty = companyProperty;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

   

    public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }
	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", companyName="
				+ companyName + ", companyProperty=" + companyProperty
				+ ", certificateType=" + certificateType + ", certificateId="
				+ certificateId + ", registerAddress=" + registerAddress
				+ ", businessAddress=" + businessAddress + ", linkMan="
				+ linkMan + ", linkTel=" + linkTel + ", email=" + email
				+ ", createPerson=" + createPerson + ", createTime="
				+ createTime + ", approvePerson=" + approvePerson
				+ ", approveTime=" + approveTime + ", currentStatus="
				+ currentStatus + "]";
	}
}
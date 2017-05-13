
package com.xinwei.security.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;
import com.xinwei.process.constant.ApprovedConstants;

public class User extends IdEntity {

	/** 描述 */
	private static final long serialVersionUID = -4277639149589431277L;

	private String username = "";

	private String firstname = "";

	private String lastname = "";
	// 存储原始密码
	private String plainPassword;
	
	private transient String password = "";
	
	//辅助字段（用于修改密码）
	private transient String oldPassword;
	//辅助字段（用于修改密码）
	private transient String newPassword;

	private transient String salt = "";

	private String phone = "";

	private String email = "";

	private Date birthday;

	private String address = "";

	private Integer sex = 0;

	private String imgurl = "";

	private Long department_id;

	private String cardno = "";
	private Integer cardtype = 0;

	private String remark;
	
	//fangping:add一个字段 （单位名称）2017-02-14
	private String company_name;

	/**
	 * 帐号创建时间
	 */
	private Date createTime;

	/**
	 * 使用状态0 有效，1无效
	 */
	private Integer status = 0;
	
	
	/**
	 * 是否禁用（0:未禁用   1：禁用）
	 */
	private Integer isDisabled = 0;
	
	/**
	 * 审核状态（0：未审核，1：审核通过，2：审核不通过）
	 */
	private Integer approvalStatus = ApprovedConstants.ApproveResult.CODE_Agree;//初始为审核通过
    
	/**
	 * reserve: not set value
	 */
	private List<Long> roleIds = new ArrayList<Long>();
	
	private List<Role> roles = new ArrayList<Role>();

	public Integer getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}

	public String getPlainPassword() {
		return plainPassword;
	}
	
	

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public String getOldPassword() {
		return oldPassword;
	}



	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}



	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public Long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}

	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * 返回 password 的值
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置 password 的值
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public Integer getCardtype() {
		return cardtype;
	}

	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 返回 salt 的值
	 * 
	 * @return salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * 设置 salt 的值
	 * 
	 * @param salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 返回 email 的值
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置 email 的值
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * reserve: not set value
	 */
	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * 返回 phone 的值
	 * 
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置 phone 的值
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

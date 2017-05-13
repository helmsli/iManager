package com.xinwei.process.entity;

public class RoleServiceType {
    private Long id;
    //角色ID
    private Integer roleId;
    //业务类型
    private String serviceType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

	@Override
	public String toString() {
		return "RoleServiceType [id=" + id + ", roleId=" + roleId
				+ ", serviceType=" + serviceType + "]";
	}
}
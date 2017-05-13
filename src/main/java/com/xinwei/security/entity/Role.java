
package com.xinwei.security.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class Role extends IdEntity {

	/** 描述 */
	private static final long serialVersionUID = -5537665695891354775L;

	private String name = "";

	private String description = "";

	private transient List<RolePermission> rolePermissions = Lists.newArrayList();
	
	private transient List<String> permissionStrs =  Lists.newArrayList();

	public List<String> getPermissionStrs() 
	{
		//*****这里必须要加初始化这一句代码，因为系统中用了mybatis的二级缓存。角色也进行缓存。
		//如果不加这一句代码，缓存中的Role每次执行getPermissionStrs() ，都会对List<String> permissionStrs 进行追加，导致严重错误
		permissionStrs = new ArrayList<String>();
		
		for (RolePermission rolePermission : rolePermissions) 
		{
			permissionStrs.add(rolePermission.getResource_sn());
		}
		return permissionStrs;
	}

	public void setPermissionStrs(List<String> permissionStrs) {
		this.permissionStrs = permissionStrs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	

	public List<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	/**
	 * 返回 name 的值
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 name 的值
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Role) {
			Role role = (Role) obj;
			return (role.getName().equals(this.getName()) && role.getId().equals(this.getId()));
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}

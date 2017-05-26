package com.xinwei.security.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.RolePermission;

public interface RolePermissionService {
	
	/**
	 * 获取角色的所有权限
	 * @param role_id
	 * @return
	 */
	List<RolePermission> findByRoleId(@Param("role_id")Long role_id);

	/**
	 * 保存角色对应的 所有权限
	 * @param role
	 */
	void update(Role role);

	/**
	 * 删除角色对应的所有权限
	 * @param role
	 */
	void delete(@Param("role_id")Long role_id);
	
	
	/**
	 * 保存角色的权限信息
	 * @param roleId
	 * @param rolePermissions
	 */
	void save(Long roleId,List<RolePermission> rolePermissions);

}

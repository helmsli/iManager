 
package com.xinwei.security.service;

import java.util.List;
import java.util.Map;

import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.User;
import com.xinwei.security.entity.UserRole;
import com.xinwei.util.page.Page;

public interface UserRoleService  {
	
	/**
	 * 根据userId，找到已分配的角色。
	 * 描述
	 * @param userId
	 * @return
	 */
	//List<UserRole> find(Long userId);
	
	/**
	 * 根据用户id，查询用户的角色，并将角色的权限信息 一并查出
	 * @param userId
	 * @return
	 */
	public List<Role> findRoleDetailsByUserId(Long userId);
	
	/**
	 * 获取用户id的所有角色
	 * @param userId
	 * @return
	 */
	List<Role> findRolesByUserId(Long userId);
	
	
	/**
	 * 根据用户id，删除角色
	 * @param userId
	 */
	void deleteByUserId(Long userId);
	
	
	/**
	 * 删除用户角色关系
	 * @param userRoles
	 */
	void deleteUserRole(List<UserRole> userRoles);
	
	
	/**
	 * 批量更新userRole，更新前删除userId对应的所有角色
	 * @param userRoles
	 */
	void updateUserRole(List<UserRole> userRoles,Long userId);
	
	
	/**
	 * 保存用户角色
	 * @param userRole
	 */
	void save(UserRole userRole);
	
	/**
	 * 批量保存用户角色
	 * @param userRoles
	 */
	void save(List<UserRole> userRoles);
	
	/**
	 * （containRoleId=true  查询角色下的用户  、 containRoleId=false 查询非角色下的用户）
	 * @param map
	 * @return
	 */
	Page<User> findUsersByRoleId(Map<String,Object> map);
	
	/**
	 * 根据用户名或单位名模糊查询指定角色下的用户（带分页）
	 * @param map
	 * @return
	 */
	Page<User> findUsersByRoleIdAndNameOrCompanyName(Map<String,Object> map);
	
	/**
	 * 查询某个角色下的所有用户
	 * @param roleId 角色Id
	 * @return
	 */
	List<User> findAllUsersByRoleId(Long roleId);
}

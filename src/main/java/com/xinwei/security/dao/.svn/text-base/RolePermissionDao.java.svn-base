 
package com.xinwei.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinwei.security.entity.Menu;
import com.xinwei.security.entity.RolePermission;


public interface RolePermissionDao  {
	
	/**
	 * 获取角色的菜单
	 * @param role_id
	 * @return
	 */
	List<Menu> findByRoleId(@Param("role_id")Long role_id);
	
	
	/**
	 * 根据角色id，查询角色下的权限信息
	 * @param role_id
	 * @return
	 */
	List<RolePermission> findSimpleByRoleId(@Param("role_id")Long role_id);
	
	/**
	 * 保存角色对应的 所有权限
	 * @param role
	 */
	void save(RolePermission rolePermission);

	
	/**
	 * 删除角色对应的所有权限
	 * @param role
	 */
	void delete(@Param("role_id")Long role_id);

	
	
	/**
	 * 获取用户拥有权限的菜单
	 * @param user_id
	 * @return
	 */
	List<Menu> findByUserId(@Param("user_id")Long user_id);
	
	
	
	


	/**
	 * 查询所有角色权限信息
	 * @return
	 */
	List<RolePermission> findAll();
	
	
	/**
	 * 查询角色对应的菜单信息
	 * @return
	 */
	List<RolePermission> findMenus();
	
	
	/**
	 * 查询角色对应的按钮信息
	 * @return
	 */
	List<RolePermission> findFunctions();
	
	
	

	
}

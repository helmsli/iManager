 
package com.xinwei.security.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.User;
import com.xinwei.security.entity.UserRole;


public interface UserRoleDao  {
	UserRole get(@Param("id")Long id);
	
	
	List<Role> findRolesByUserId(@Param("user_id")Long userId);
	
	Long findCountByRoleId(@Param("role_id")Long roleId);
	
	/**
	 * 查询用户id，角色id 是否存在
	 * @param userRole
	 * @return
	 */
	Long findByRoleIdUserIdCount(UserRole userRole);
	
	void deleteByUserId(@Param("user_id")Long userId);

	void deleteUserRole(UserRole userRole);
	
	void save(UserRole userRole);

	
	/**
	 * 获取分页条数（containRoleId=true  查询角色下的用户  、 containRoleId=false 查询非角色下的用户）
	 * @param map 查询参数
	 * @return
	 */
	Long findUsersByRoleIdCount(Map<String,Object> map);
	
	/**
	 * 获取分页后的数据（containRoleId=true  查询角色下的用户  、 containRoleId=false 查询非角色下的用户）
	 * @param map 查询参数（包含起始记录数startRow,当页记录数pageSize）
	 * @return
	 */
	List<User> findUsersByRoleId(Map<String,Object> map);
	
	/**
	 * 获取分页条数（根据用户名或单位名模糊查询指定角色下的用户 ）
	 * @param map 查询参数
	 * @return
	 */
	Long countUsersByRoleIdAndNameOrCompanyName(Map<String,Object> map);
	
	/**
	 * 获取分页后的数据（根据用户名或单位名模糊查询指定角色下的用户）
	 * @param map 查询参数（包含起始记录数startRow,当页记录数pageSize）
	 * @return
	 */
	List<User> findUsersByRoleIdAndNameOrCompanyName(Map<String,Object> map);
	
	/**
	 * 查询某个角色下的所有用户列表
	 * @param roleId
	 * @return
	 */
	List<User> findAllUsersByRoleId(@Param("role_id")Long roleId);
}

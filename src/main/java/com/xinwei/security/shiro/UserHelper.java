package com.xinwei.security.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.google.common.collect.Sets;
import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.RolePermission;
import com.xinwei.security.entity.User;

public class UserHelper {
	
	
	/**
	 * 获取用户信息
	 * @return
	 */
	public static User getUser() {
		User user = null;
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser)subject.getPrincipal();
		if(null != shiroUser){
			user = shiroUser.getUser();
		}
		return user;
	}
	
	
	
	/**
	 * 获取用户id
	 * @return
	 */
	public static Long getUserId() {
		return getUser().getId();
	}
	
	/**
	 * 获取当前登录用户 的权限信息
	 * @return
	 */
	public static Set<String> getUserPermission(){
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser)subject.getPrincipal();
		// 得到所有权限
		Set<String> result = Sets.newHashSet();
		//List<Role> roles = userRoleService.findRoleDetailsByUserId(shiroUser.getId());
		//shiroUser.getUser().setRoles(roles);
		for (Role role : shiroUser.getUser().getRoles()) {
			Set<String> tmp = Sets.newHashSet(role.getPermissionStrs());
			result.addAll(tmp);
		}
		return result;
	}
	
	
	
	/**
	 * 根据用户id，查询所有的权限字符串 集合
	 * @param userId
	 * @return
	 */
	public static Set<String> getMenusByUserId(Long userId){
		/*Set<String> result = new HashSet<>();
		
		//获取用户的所有角色权限信息
		List<Role> roles = userRoleDao.findRolesByUserId(userId);
		
		if (!roles.isEmpty()) {
			for (Role role : roles) {
				//获取角色的所有权限
				List<RolePermission> rolePermissions = rolePermissionDao.findSimpleByRoleId(role.getId());
				for (RolePermission rolePermission : rolePermissions) {
					result.add(rolePermission.getResource_key());
				}
			}
		}
		return result;*/
		return null;
	}
	
	
	
}

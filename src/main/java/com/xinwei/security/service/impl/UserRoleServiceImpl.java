 
package com.xinwei.security.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.process.service.UserAndGroupService;
import com.xinwei.security.MessageCode;
import com.xinwei.security.dao.RolePermissionDao;
import com.xinwei.security.dao.UserRoleDao;
import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.RolePermission;
import com.xinwei.security.entity.User;
import com.xinwei.security.entity.UserRole;
import com.xinwei.security.exception.ServiceException;
import com.xinwei.security.service.UserRoleService;
import com.xinwei.util.page.Page;

@Service
public class UserRoleServiceImpl  implements UserRoleService {
	private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RolePermissionDao rolePermissionDao;
	@Autowired
	private UserAndGroupService userAndGroupServiceImpl; //activiti中的用户与组服务

	/**
	 * 根据用户id，查询用户的角色，并将角色的权限信息 一并查出
	 * @param userId
	 * @return
	 */
	public List<Role> findRoleDetailsByUserId(Long userId) {
		List<Role> roles = userRoleDao.findRolesByUserId(userId);
		
		if (!roles.isEmpty()) {
			for (Role role : roles) {
				//获取角色的所有权限
				List<RolePermission> rolePermissions = rolePermissionDao.findSimpleByRoleId(role.getId());
				role.setRolePermissions(rolePermissions);
			}
			
		}
		return roles;
	}
	
	@Override
	public List<Role> findRolesByUserId(Long userId) {
		return userRoleDao.findRolesByUserId(userId);
	}


	@Override
	public void deleteByUserId(Long userId) {
		userRoleDao.deleteByUserId(userId);
		//删除Activiti中用户、组关系
		userAndGroupServiceImpl.deleteMembershipByUId(userId.toString());
	}

	@Override
	public void updateUserRole(List<UserRole> userRoles, Long userId) {
		this.deleteByUserId(userId);
		//删除Activiti中的用户组关系
		userAndGroupServiceImpl.deleteMembershipByUId(userId.toString());
		for (UserRole userRole : userRoles) {
			userRoleDao.save(userRole);
			//保存新的用户组关系到Activiti中
			userAndGroupServiceImpl.addMembership(userId.toString(), userRole.getRole_id().toString());
		}
		
	}

	@Override
	public void save(UserRole userRole) {
		userRoleDao.save(userRole);
		//保存新的用户组关系到Activiti中
		userAndGroupServiceImpl.addMembership(
				userRole.getUser_id().toString(),
				userRole.getRole_id().toString());
	}

	@Override
	public void save(List<UserRole> userRoles) {
		for (UserRole userRole : userRoles) {
			if(userRoleDao.findByRoleIdUserIdCount(userRole) == 0)
				userRoleDao.save(userRole);
				//保存新的用户组关系到Activiti中
				userAndGroupServiceImpl.addMembership(
						userRole.getUser_id().toString(),
						userRole.getRole_id().toString());
		}
	}

	@Override
	public void deleteUserRole(List<UserRole> userRoles) {
		for (UserRole userRole : userRoles) {
			if (userRole.getRole_id() ==1 && userRole.getUser_id() == 1) {
				logger.warn("操作员{},尝试删除超级管理员", SecurityUtils.getSubject().getPrincipal());
				throw new ServiceException(MessageCode.USER_CAN_NOT_UPDATE_ADMIN);
			}
			userRoleDao.deleteUserRole(userRole);
			//删除Activiti中用户、组关系
			userAndGroupServiceImpl.deleteMembership(
					userRole.getUser_id().toString(), 
					userRole.getRole_id().toString());
		}
		
	}

	
	@Override
	public Page<User> findUsersByRoleId(Map<String, Object> map) {
		Page<User> page = new Page<User>(userRoleDao.findUsersByRoleIdCount(map));
		map.put("startRow", page.getStartRow());
		map.put("pageSize", page.getPageSize());
		page.setList(userRoleDao.findUsersByRoleId(map));
		return page;
	}

	@Override
	public List<User> findAllUsersByRoleId(Long roleId) {
		return userRoleDao.findAllUsersByRoleId(roleId);
	}

	@Override
	public Page<User> findUsersByRoleIdAndNameOrCompanyName(
			Map<String, Object> map) {
		Page<User> page = new Page<User>(userRoleDao.countUsersByRoleIdAndNameOrCompanyName(map));
		map.put("startRow", page.getStartRow());
		map.put("pageSize", page.getPageSize());
		page.setList(userRoleDao.findUsersByRoleIdAndNameOrCompanyName(map));
		return page;
	}

}

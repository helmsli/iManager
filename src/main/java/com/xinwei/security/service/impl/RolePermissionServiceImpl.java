package com.xinwei.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.security.MessageCode;
import com.xinwei.security.dao.RolePermissionDao;
import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.RolePermission;
import com.xinwei.security.exception.ServiceException;
import com.xinwei.security.service.RolePermissionService;
import com.xinwei.security.shiro.ShiroDbRealm;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
	
	@Autowired
	private RolePermissionDao rolePermissionDao;
	
	@Autowired(required = false)
	private ShiroDbRealm shiroRealm;

	@Override
	public List<RolePermission> findByRoleId(Long role_id) {
		return rolePermissionDao.findSimpleByRoleId(role_id);
	}

	@Override
	public void update(Role role) {
		delete(role.getId());
		List<RolePermission> permissionList = role.getRolePermissions();
		for (RolePermission permission : permissionList) {
			permission.setId(role.getId());
			rolePermissionDao.save(permission);
		}
		shiroRealm.clearAllCachedAuthorizationInfo();
		
	}

	@Override
	public void delete(Long role_id) {
		rolePermissionDao.delete(role_id);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	@Override
	public void save(Long roleId, List<RolePermission> rolePermissions) {
		if(null == roleId  || 0 == roleId){
			throw new ServiceException(MessageCode.ROLE_ID_NULL);
		}
		rolePermissionDao.delete(roleId);
		for (RolePermission rolePermission : rolePermissions) {
			rolePermissionDao.save(rolePermission);
		}
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

}

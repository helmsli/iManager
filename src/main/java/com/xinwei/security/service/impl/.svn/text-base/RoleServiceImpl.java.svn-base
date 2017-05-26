 
package com.xinwei.security.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.process.service.UserAndGroupService;
import com.xinwei.security.MessageCode;
import com.xinwei.security.dao.RoleDao;
import com.xinwei.security.dao.RolePermissionDao;
import com.xinwei.security.dao.UserRoleDao;
import com.xinwei.security.entity.Role;
import com.xinwei.security.exception.ServiceException;
import com.xinwei.security.service.RoleService;
import com.xinwei.security.shiro.ShiroDbRealm;
import com.xinwei.util.page.Page;

@Service
public class RoleServiceImpl  implements RoleService {
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired 
	private RolePermissionDao rolePermissionDao;
	
	@Autowired
	private UserAndGroupService userAndGroupServiceImpl; //activiti中的用户与组服务
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired(required = false)
	private ShiroDbRealm shiroRealm;
	

	/**
	 * 修改角色，但是只是修改角色的权限，即 security_role_permission
	 */
	public void update(Role role) {
		if(null == role.getId()  || 0 == role.getId()){
			throw new ServiceException(MessageCode.ROLE_ID_NULL);
		}
		checkRole(role);
		roleDao.update(role);
		//在Activiti中修改相应的组信息
		userAndGroupServiceImpl.addGroup(role.getId().toString(), 
				role.getName(), "assignment");	
	}

	//验证角色
	private void checkRole(Role role) {
		if(StringUtils.isEmpty(role.getName())){
			throw new ServiceException(MessageCode.ROLE_NAME_NULL);
		}
		
		//验证角色名 是否存在
		Long count = roleDao.findByRolename(role);
		if(count > 0){
			throw new ServiceException(MessageCode.ROLE_NAME_NULL);
		}
	}
	
	/**
	 * 判断是否超级管理员角色.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	public void delete(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}，尝试删除超级管理员角色", SecurityUtils.getSubject().getPrincipal() + "。");
			throw new ServiceException(MessageCode.ROLE_CAN_NOT_DELETE_ADMIN);
		}
		
		if(userRoleDao.findCountByRoleId(id) >0){
			throw new ServiceException(MessageCode.ROLE_CAN_NOT_DELETE_RELEATE);
		}
		
		//删除角色权限关系
		rolePermissionDao.delete(id);
		//删除用户角色关系
		//userRoleDao.deleteByRoleId(id);
		//删除角色
		roleDao.delete(id);
		shiroRealm.clearAllCachedAuthorizationInfo();
		
		//删除Activiti中该组相关的用户组关系
		userAndGroupServiceImpl.deleteMembershipByGroupId(id.toString());
		//删除对应Activiti中的用户组
		userAndGroupServiceImpl.deleteGroup(id.toString());
	}


	@Override
	public void save(Role entity) {
		checkRole(entity);
		roleDao.save(entity);
		//在Activiti中创建相应的组
		userAndGroupServiceImpl.addGroup(entity.getId().toString(), 
		entity.getName(), "assignment");
	}

	@Override
	public Role get(Long id) {
		return roleDao.get(id);
	}

	
	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}


	@Override
	public Page<Role> findByPage(Map<String,Object> map) {
		Page<Role> page = new Page<Role>(roleDao.findByPageCount(map ));
		map.put("startRow", page.getStartRow());
		map.put("pageSize", page.getPageSize());
		page.setList(roleDao.findByPage(map));
		return page;
	}

	

}

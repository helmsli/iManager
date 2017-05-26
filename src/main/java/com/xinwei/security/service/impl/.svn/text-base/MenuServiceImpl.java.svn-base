 
package com.xinwei.security.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xinwei.security.cache.MenuCache;
import com.xinwei.security.dao.FunctionDao;
import com.xinwei.security.dao.MenuDao;
import com.xinwei.security.dao.RolePermissionDao;
import com.xinwei.security.entity.Function;
import com.xinwei.security.entity.Menu;
import com.xinwei.security.entity.RolePermission;
import com.xinwei.security.service.MenuService;
import com.xinwei.security.shiro.UserHelper;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private FunctionDao functionDao;
	
	@Autowired
	private RolePermissionDao rolePermissionDao;
	
	
	/**
	 * 获取所有的菜单
	 * @return  
	 */
	public List<Menu> getAllMenu() {
		List<Menu> menus = menuDao.findAll();
		List<Menu> temp = new ArrayList<Menu>(menus) ;
		List<Menu> result = makeTree(temp);
		return result;
	}
	
	
	/**
	 * 获取拥有权限的菜单
	 * @return
	 */
	public List<Menu> getMenu() {
		List<Menu> menus = rolePermissionDao.findByUserId(UserHelper.getUser().getId());
		List<Menu> temp = new ArrayList<Menu>(menus) ;
		List<Menu> result = makeTree(temp);
		return result;
		/*
		List<Menu> result = Lists.newArrayList();
		
		//获取所有菜单
		List<Menu> allMenus = getAllMenu();
		
		//只遍历了两次，目前只有两层
		for (Menu menu : allMenus) 
		{
			if (SecurityUtils.getSubject().isPermitted(menu.getSn())) 
			{
				List<Menu> list = Lists.newArrayList();
				for (Menu m : menu.getChildren()) {
					if (SecurityUtils.getSubject().isPermitted(m.getSn())) {
						list.add(m);
					}
				}
				menu.setChildren(list);
				result.add(menu);
			}
		}
		return result;
		*/
	}
	
	
	/**
	 * 获取所有的菜单（包括菜单下的按钮）
	 * @return
	 */
	public List<Menu> getAllMenuFunctions(){
		//查询所有菜单
		List<Menu> menus = getAllMenu();
		
		//遍历所有菜单，查询缓存中的   菜单id是否有function功能列表，有则设置
		for (Menu menu : menus) {
			
			List<Menu> children = menu.getChildren();
			for (Menu child : children) {
				//List<Function> functions = MenuCache.getFunctions(child.getId());
				if(null != child && null != child.getId())
				{
					List<Function> functions = functionDao.findByMenuId(child.getId());
					if(!functions.isEmpty()){
						child.setFunctions(functions);
					}	
				}
				
			}
			
			
		}
		return menus;
	}
	
	
	/**
	 * 获取有权限的菜单（包括菜单下的按钮）
	 */
	@Override
	public List<Menu> getMenuFunctions() {
		List<Menu> result = Lists.newArrayList();
		
		//获取所有的菜单及按钮
		List<Menu> menus = getAllMenuFunctions();
		
		System.out.println("====获取所有的菜单=====");
		
		//只遍历了两次，目前只有两层
		for (Menu menu : menus) 
		{
			// 只加入拥有view权限的Module
			if(SecurityUtils.getSubject().isPermitted(menu.getSn()))
			//if (permissions.contains(menu.getSn())) 
			{
				List<Menu> list = Lists.newArrayList();
				for (Menu m : menu.getChildren()) 
				{
					if (SecurityUtils.getSubject().isPermitted(m.getSn())) {
						
						//菜单下所有的功能列表
						List<Function> havfunctions = Lists.newArrayList();
						List<Function> functions = m.getFunctions();
						for (Function function : functions) 
						{
							if(SecurityUtils.getSubject().isPermitted(function.getSn())){
								havfunctions.add(function);
							}
						}
						m.setFunctions(havfunctions);
						list.add(m);
					}
				}
				menu.setChildren(list);
				result.add(menu);
			}
		}
		return result;
	}
	
	
	
	
	
	/**
	 * 根据角色id集合 获取有权限的菜单（包括菜单下的按钮）
	 */
	@Override
	public List<Menu> getMenuFunctions(List<Long> roleIds) {
		if(null == roleIds || roleIds.size() ==0)
			return null;
		List<Menu> result = Lists.newArrayList();
		
		// 得到所拥有的权限
		Set<String> permissions = new HashSet<>();
		for (Long roleId : roleIds) {
			//获取角色的所有权限
			List<RolePermission> rolePermissions = rolePermissionDao.findSimpleByRoleId(roleId);
			for (RolePermission rolePermission : rolePermissions) {
				permissions.add(rolePermission.getResource_sn());
			}
		}
		
		
		//获取所有的菜单及按钮
		List<Menu> menus = getAllMenuFunctions();
		
		//只遍历了两次，目前只有两层
		for (Menu menu : menus) 
		{
			// 只加入拥有view权限的Module
			if (permissions.contains(menu.getSn())) 
			{
				List<Menu> list = Lists.newArrayList();
				for (Menu m : menu.getChildren()) 
				{
					if (permissions.contains(m.getSn())) {
						
						//菜单下所有的功能列表
						List<Function> havfunctions = Lists.newArrayList();
						List<Function> functions = m.getFunctions();
						for (Function function : functions) 
						{
							if(permissions.contains(function.getSn())){
								havfunctions.add(function);
							}
						}
						m.setFunctions(havfunctions);
						list.add(m);
					}
				}
				menu.setChildren(list);
				result.add(menu);
			}
		}
		return result;
	}
	
	
	
	
	/**
	 * 根据角色id 获取所有的菜单（包括菜单下的按钮）
	 * 对有权限的菜单、按钮 进行checked选中
	 */
	@Override
	public List<Menu> getMenuFunctionsChecked(Long roleId) {
		
		// 得到所拥有的权限
		Set<String> permissions = new HashSet<>();
		//获取角色的所有权限
		List<RolePermission> rolePermissions = rolePermissionDao.findSimpleByRoleId(roleId);
		for (RolePermission rolePermission : rolePermissions) {
			permissions.add(rolePermission.getResource_sn());
		}
		
		
		//获取所有的菜单及按钮
		List<Menu> menus = getAllMenuFunctions();
		
		//只遍历了两次，目前只有两层
		for (Menu menu : menus) 
		{
			//因为用了mybatis的二级缓存，checked又是辅助字段，这里在内存中被更新后，会被保存状态，所以要重置
			if(permissions.contains(menu.getSn()))
				menu.setChecked(true);
			else
				menu.setChecked(false);
			
			
			for (Menu childMenu : menu.getChildren()) 
			{
				
				if (permissions.contains(childMenu.getSn())) 
					childMenu.setChecked(true);
				else
					childMenu.setChecked(false);
				
				//菜单下所有的功能列表
				List<Function> functions = childMenu.getFunctions();
				for (Function function : functions) 
				{
					function.setChecked(false);
					if(permissions.contains(function.getSn())){
						function.setChecked(true);
					}
				}
				
			}
		}
		return menus;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private List<Menu> makeTree(List<Menu> list) {
		List<Menu> parent = new ArrayList<Menu>();
		//获取第一级菜单
		for (Menu e : list) {
			if (e.getParent_id() == null) {
				e.setChildren(new ArrayList<Menu>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		boolean isRemove = list.removeAll(parent);
		
		//往一级菜单中 添加子菜单
		makeChildren(parent, list);
		
		return parent;
	}
	
	private void makeChildren(List<Menu> parent, List<Menu> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		//保存parent中的下一级节点，用于后面删除
		List<Menu> tempMenus = new ArrayList<Menu>();
		for (Menu parentMenu : parent) {
			for (Menu menu : children) {
				menu.setChildren(new ArrayList<Menu>(0));
				if (parentMenu.getId().equals(menu.getParent_id())) {
					parentMenu.getChildren().add(menu);
					tempMenus.add(menu);
				}
			}
		}
		
		children.removeAll(tempMenus);
		makeChildren(tempMenus, children);
	}


	@Override
	public List<Menu> findAll() {
		return menuDao.findAll();
	}


	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	*//**
	 * 只获取有权限的二级菜单
	 * @param userRoles
	 * @return
	 *//*
	public List<String> getMenuModule(List<UserRole> userRoles) {
		List<String> result = Lists.newArrayList();
		// 得到所有权限
		Set<String> permissionSet = Sets.newHashSet();
		for (UserRole userRole : userRoles) {
			Set<String> tmp = Sets.newHashSet(userRole.getRole().getPermissionList());
			permissionSet.addAll(tmp);
		}
		
		// 组装菜单,只获取二级菜单
		//Module rootModule = moduleService.get(1L);
		Menu rootModule = getTree();
		for (Menu m1 : rootModule.getChildren()) {
			// 只加入拥有view权限的Module
			if (permissionSet.contains(m1.getSn() + ":" + Constants.OPERATION_VIEW)) {
				result.add(m1.getName());
			}
		}
		return result;
	}
	*/
	

	
	
	
	
	
}

 
package com.xinwei.security.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.xinwei.security.entity.Menu;
import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.RolePermission;
import com.xinwei.security.entity.UserRole;
import com.xinwei.security.service.MenuService;
import com.xinwei.security.service.RolePermissionService;
import com.xinwei.security.service.RoleService;
import com.xinwei.security.service.UserRoleService;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.page.Page;

@Controller
@RequestMapping("/management/role")
public class RoleControllerNew {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	
	@Autowired
	private MenuService menuService;
	
	
	
	
	/**
	 * 查询角色列表
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value="/getAll", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getAll() {
		List<Role> roles = roleService.findAll();
		ResultVO<Role> resultVO = new ResultVO<Role>();	
		resultVO.setOthers("roles", roles);
		return resultVO.toString();
	}
	
	
	/**
	 * 查询角色列表
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String list(String keywords) {
		Page<Role> page = roleService.findByPage(new HashMap<String, Object>());
		
		ResultVO<Role> resultVO = new ResultVO<Role>();
		resultVO.setPage(page);
		resultVO.setLists(page.getList());
		//resultVO.setKeywords(keywords);
		return resultVO.toString();
	}
	
	
	
	
	
	/**
	 * 获取多个角色下的菜单按钮信息（包括按钮）
	 * @return
	 */
	@RequestMapping(value="/getMenuFunctions", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getMenuFunctions(@RequestParam(value="roleIds[]",required=false)List<Long> roleIds) {
		ResultVO<Object> result = new  ResultVO<>();
		List<Menu> menuFunctions = menuService.getMenuFunctions(roleIds);
		result.setOthers("menuFunctions", menuFunctions);
		return result.toString();
	}
	
	
	/**
	 * 获取单个角色下的所有的菜单按钮信息（包括按钮）
	 * 对有权限的菜单、按钮 进行checked标注
	 * @return
	 */
	@RequestMapping(value="/getMenuFunctionsChecked", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getMenuFunctionsChecked(Long roleId) {
		ResultVO<Object> result = new  ResultVO<>();
		List<Menu> menuFunctions = menuService.getMenuFunctionsChecked(roleId);
		result.setOthers("menuFunctions", menuFunctions);
		return result.toString();
	}
	

	/**
	 * 获取有权限的模块信息
	 * @return
	 */
	@RequestMapping(value="/getModules", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getRoles() {
		
		List<Menu> menuFunctions = menuService.getMenuFunctions();
		
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("module", menuFunctions);
		return result.toString();
	}
	
	
	
	
	/**
	 * 获取所有的菜单按钮信息
	 * @return
	 */
	@RequestMapping(value="/getAllModules", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getAllModules() {
		
		List<Menu> menuFunctions = menuService.getAllMenuFunctions();
		
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("module", menuFunctions);
		return result.toString();
	}
	
	
	/**
	 * 根据角色id，获取该角色权限分配的  菜单、按钮id（仅是id，不是树）
	 * @return
	 */
	@RequestMapping(value="/getMenuFunctionIds", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getMenuFunctionIds(Long roleId) {
		
		List<RolePermission> permissions = rolePermissionService.findByRoleId(roleId);
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("permissions", permissions);
		return result.toString();
	}
	
	
	
	/**
	 * 修改角色权限信息
	 * @return
	 */
	@RequestMapping(value="/updateInfo", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String updateInfo(Long id,@RequestParam(value="menus[]",required=false)List<Long> menus,
			@RequestParam(value="functions[]",required=false)List<Long> functions) {
		
		List<RolePermission> rolePermissions = new ArrayList<>();
		RolePermission rolePermission ;
		if(null != menus)
		{
			for (Long menuId : menus) {
				rolePermission = new RolePermission(id,menuId,0);
				rolePermissions.add(rolePermission);
			}
		}
		
		
		if(null != functions)
		{
			for (Long functionId : functions) {
				rolePermission = new RolePermission(id,functionId,1);
				rolePermissions.add(rolePermission);
			}
		}
		
		rolePermissionService.save(id, rolePermissions);
		return new  ResultVO<>().toString();
	}
	
	
	
	
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/create", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String create(Role role) {
		roleService.save(role);
		return new ResultVO<>().toString();
	}
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/update", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String update(Role role) {
		roleService.update(role);
		return  new ResultVO<>().toString();
	}
	
	
	
	/**
	 * 角色里面 添加用户
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/addUser", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String addUser(Long roleId,@RequestParam(value="userIds[]",required=false)List<Long> userIds) {
		
		List<UserRole> userRoles = new ArrayList<>();
		UserRole userRole ;
		for (Long userId : userIds) {
			userRole = new UserRole(userId, roleId);
			userRoles.add(userRole);
		}
		userRoleService.save(userRoles);
		return  new ResultVO<>().toString();
	}
	
	
	/**
	 * 角色里面 删除用户
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/removeUser", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String removeUser(Long roleId,@RequestParam(value="userIds[]",required=false)List<Long> userIds) {
		
		List<UserRole> userRoles = new ArrayList<UserRole>();
		UserRole userRole ;
		for (Long userId : userIds) {
			userRole = new UserRole(userId, roleId);
			userRoles.add(userRole);
		}
		userRoleService.deleteUserRole(userRoles);
		return  new ResultVO<>().toString();
	}
	
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String delete(Long id) {
		roleService.delete(id);
		return new ResultVO<>().toString();
	}
	
	
	
	
	
	
	
	
	
	
	

	
	
}

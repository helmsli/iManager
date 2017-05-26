package com.xinwei.security.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xinwei.security.dao.RoleDao;
import com.xinwei.security.dao.RolePermissionDao;
import com.xinwei.security.dao.UserRoleDao;
import com.xinwei.security.entity.Menu;
import com.xinwei.security.entity.RolePermission;
import com.xinwei.util.spring.SpringFactory;

/**
 * 缓存 角色-权限关联 信息(暂时未用)
 * @author shaoyong
 *
 */
public class RolePermissionCache {
	private RolePermissionCache(){}
	
	//角色id <----> 层级的菜单列表
	private static Map<Long,List<Menu>> roleMenus = new HashMap<>();
		
	//角色id <----> 角色权限信息
	private static Map<Long,List<RolePermission>> role2permission = new HashMap<>();
	
	//角色id <----> 角色权限信息字符串
	private static Map<Long,List<String>> role2permissionStr = new HashMap<>();
	
	private static RoleDao roleDao = (RoleDao) SpringFactory.getBean("roleDao");
	
	private static RolePermissionDao rolePermissionDao = (RolePermissionDao) SpringFactory.getBean("rolePermissionDao");
	
	private static UserRoleDao userRoleDao = (UserRoleDao) SpringFactory.getBean("userRoleDao");
	
	
	
	static{
		
		
		/*Map<Long,List<Function>> roleFunctions = new HashMap<>();
		//角色--菜单信息
		List<RolePermission> rolePermissions = rolePermissionDao.findFunctions();
		//将所有的角色菜单信息 --》  存储到  roleMenus的map中（roleId <--> 菜单列表）
		for (RolePermission rolePermission : rolePermissions) {
			List<Function> functions = roleFunctions.get(rolePermission.getRole_id());
			if(functions.isEmpty()){
				functions = new ArrayList<>();
				functions.add(rolePermission.getFunction());
				roleFunctions.put(rolePermission.getRole_id(), functions);
			}else{
				functions.add(rolePermission.getFunction());
			}
		}
		
		//遍历map（roleId <--> 菜单列表） ==>  roleId <--> 层级的菜单列表
		for (Entry<Long, List<Menu>> entry : roleFunctions.entrySet()) 
		{
			Long roleId = entry.getKey();
			List<Menu> menus = makeTree(entry.getValue());
			roleMenus.put(roleId, menus);
		}*/
		
	}




	
	
	
	
	
	
	/**
	 * 根据角色id，查询菜单
	 * @param roleId
	 * @return
	 */
	public static List<Menu> getMenusByRoleId(Long roleId){
		List<Menu> menus = rolePermissionDao.findByRoleId(roleId);
		List<Menu> result = makeTree(menus);
		return result;
	}
	
	
	
	/**
	 * 将菜单列表 --》转换成有层级关系的菜单列表 
	 * @param list
	 * @return
	 */
	private static List<Menu> makeTree(List<Menu> list) {
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
	
	private static void makeChildren(List<Menu> parent, List<Menu> children) {
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
	
	
	
	
	/**
	 * 获得角色--层级菜单列表
	 */
	private static void getRoleMenus() {
		Map<Long,List<Menu>> result = new HashMap<>();
		
		//角色--菜单信息
		List<RolePermission> rolePermissions = rolePermissionDao.findMenus();
		
		//将所有的角色菜单信息 --》  存储到  roleMenus的map中（roleId <--> 菜单列表）
		for (RolePermission rolePermission : rolePermissions) {
			List<Menu> menus = result.get(rolePermission.getRole_id());
			if(menus.isEmpty()){
				menus = new ArrayList<>();
				menus.add(rolePermission.getMenu());
				result.put(rolePermission.getRole_id(), menus);
			}else{
				menus.add(rolePermission.getMenu());
			}
		}
		
		//遍历map（roleId <--> 菜单列表） ==>  roleId <--> 层级的菜单列表
		for (Entry<Long, List<Menu>> entry : result.entrySet()) 
		{
			Long roleId = entry.getKey();
			List<Menu> menus = makeTree(entry.getValue());
			result.put(roleId, menus);
		}
	}
	
	
	
	
	
	
	
}

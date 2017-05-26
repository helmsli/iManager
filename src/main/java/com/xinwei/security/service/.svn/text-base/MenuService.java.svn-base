package com.xinwei.security.service;

import java.util.List;

import com.xinwei.security.entity.Menu;

public interface MenuService {
	/**
	 * 获取所有的菜单模块（树形）
	 * @return
	 */
	public List<Menu> getAllMenu() ;
	
	
	/**
	 * 获取所有的菜单
	 * @return
	 */
	public List<Menu> findAll();
	
	/**
	 * 获取拥有权限的菜单
	 * @return
	 */
	public List<Menu> getMenu();
	
	
	/**
	 * 获取所有的菜单（包括菜单下的按钮）
	 * @return
	 */
	public List<Menu> getAllMenuFunctions();
	
	
	
	/**
	 * 获取有权限的的菜单（包括菜单下的按钮）
	 * @return
	 */
	public List<Menu> getMenuFunctions();


	/**
	 * 根据角色id集合 获取有权限的菜单（包括菜单下的按钮）
	 * @param roleIds
	 * @return
	 */
	List<Menu> getMenuFunctions(List<Long> roleIds);
	
	
	
	/**
	 * 根据角色id 获取所有的菜单（包括菜单下的按钮）
	 * 对有权限的菜单、按钮 进行checked选中
	 */
	List<Menu> getMenuFunctionsChecked(Long roleId);
}

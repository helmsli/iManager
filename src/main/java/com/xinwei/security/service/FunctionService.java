package com.xinwei.security.service;

import java.util.List;

import com.xinwei.security.entity.Function;

public interface FunctionService {
	/**
	 * 获取某个菜单下 所有的功能列表
	 * @param menuId
	 * @return
	 */
	public List<Function> getAllFunction(Long menuId);
	
	
	/**
	 * 获取某个菜单下 有权限的功能列表
	 * @param menuId
	 * @return
	 */
	public List<Function> getFunction(Long menuId);
	
	/**
	 * 获取所有的功能列表
	 * @return
	 */
	public List<Function> findAll();
}

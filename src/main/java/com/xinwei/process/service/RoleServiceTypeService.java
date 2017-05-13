package com.xinwei.process.service;

import java.util.List;

import com.xinwei.process.entity.RoleServiceType;

public interface RoleServiceTypeService{
	/**
	 * 获取所有
	 */
	List<RoleServiceType> selectAll();
	/**
	 * 保存
	 */
	Long save(RoleServiceType roleServiceType);

	/**
	 * 删除
	 */
	void delete(Long id);
	
	/**
	 * 修改
	 */
	void update(RoleServiceType roleServiceType);
	
	/**
	 * 获取ById
	 */
	RoleServiceType selectByPrimaryKey(Long id) ;
	/**
	 * 通过角色ID查询业务类型列表
	 */
	List<String> selectServiceTypeListByRole(Integer roleId) ;
	
	List<String> selectServiceTypeByCache(Integer roleId) ;
	
	void reset(Object para);
	
	
}

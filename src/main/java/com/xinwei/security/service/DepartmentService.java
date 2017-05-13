package com.xinwei.security.service;

import java.util.List;

import com.xinwei.security.entity.Department;

public interface DepartmentService extends BaseService<Department, Long>{
	/**
	 * 获取所有的部门（树形）
	 * @return
	 */
	public List<Department> getAllDepartmentTree() ;
	
	
	
	

}

 
package com.xinwei.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinwei.security.entity.Department;

public interface DepartmentDao  {
	
	
	List<Department> findAll();
			
	void save(Department department);
	
	void update(Department department);
	
	void delete(@Param("id")Long id);
	
	Department get(@Param("id")Long id);
	
	// 根据部门名查找部门
	Department findByName(Department department);
	
	/**
	 * 根据部门id，查询其下 是否有子级部门
	 * @param id
	 * @return
	 */
	Long findCountByParentId(@Param("id")Long id);
	
}

 
package com.xinwei.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinwei.security.entity.Function;

public interface FunctionDao  {
	
	
	List<Function> findAll();
	
	List<Function> findByMenuId(@Param("menu_id") Long menu_id);
			
	
	void save(Function function);

	void delete(@Param("id") Long id);

	void update(Function function);	
}

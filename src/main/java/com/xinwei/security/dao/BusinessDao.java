package com.xinwei.security.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xinwei.security.entity.Business;

public interface BusinessDao {
	void save(Business business);
	
	void update(Business business);
	
	void delete(@Param("id")Long id);
	
	Business get(@Param("id")Long id);
	
	
	/**
	 * 获取分页条数
	 * @param map 查询参数
	 * @return
	 */
	Long findByPageCount(Map<String,Object> map);
	
	/**
	 * 获取分页后的数据
	 * @param map 查询参数（包含起始记录数startRow,当页记录数pageSize）
	 * @return
	 */
	List<Business> findByPage(Map<String,Object> map);
	
	
	
	
	
	
	
		
	
	
}
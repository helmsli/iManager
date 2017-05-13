
package com.xinwei.security.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xinwei.security.entity.Role;

public interface RoleDao {

	void save(Role role);

	void delete(@Param("id") Long id);

	void update(Role role);

	/**
	 * 获取分页条数
	 * 
	 * @param map
	 *            查询参数
	 * @return
	 */
	Long findByPageCount(Map<String, Object> map);

	
	
	/**
	 * 根据角色名 查询角色是否存在
	 * @param name
	 * @return
	 */
	Long findByRolename(Role role);
	
	/**
	 * 获取分页后的数据
	 * 
	 * @param map
	 *            查询参数（包含起始记录数startRow,当页记录数pageNum）
	 * @return
	 */
	List<Role> findByPage(Map<String, Object> map);

	Role get(@Param("id") Long id);

	List<Role> findAll();

}

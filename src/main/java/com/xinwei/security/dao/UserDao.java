package com.xinwei.security.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xinwei.security.entity.User;

public interface UserDao {
	void save(User user);
	
	void update(User user);
	
	void delete(@Param("id")Long id);
	
	User get(@Param("id")Long id);
	
	
	// 根据登录名查找用户
	User findByUsername(User user);
	

	/**
	 * 移动部门
	 * @param map
	 */
	void moveDepartment(@Param("userIds")List<Long> userIds,@Param("department_id")Long department_id);

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
	List<User> findByPage(Map<String,Object> map);

	/**
	根据用户ID查询单位名称
	 */
	String getCompanyNameByUserId(Long userId);
	
	
	
	
	
	
	
		
	
	
}
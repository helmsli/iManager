 
package com.xinwei.security.service;

import java.util.List;
import java.util.Map;

import com.xinwei.security.entity.User;
import com.xinwei.util.page.Page;


public interface UserService extends BaseService<User, Long>{
	
	User get(String username);
	
	/**
	 * 重置密码
	 * @param user
	 */
	public void resetPassword(User user);
	
	
	
	
	/**
	 * 修改密码
	 * @param user
	 */
	public void updatePassword(String oldPassword,String newPassword);
	
	
	/**
	 * 移动部门
	 * @param userIds
	 * @param department_id
	 */
	public void moveDepartment(List<Long> userIds,Long department_id);
	
	
	
	/**
	 * 修改用户的基本信息
	 * @param user
	 */
	public void updateSelf(User user);
	
	
	
	
}

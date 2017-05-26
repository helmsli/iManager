package com.xinwei.security.entity;

import com.xinwei.security.entity.IdEntity;

/**
 * 
 * @author Administrator
 * @hibernate.class table="T_UsersRoles"
 */
public class UserRole extends IdEntity {
	
	/** 描述  */
	private static final long serialVersionUID = -8888778227379780116L;
	
	public UserRole(){}
	
	public UserRole(Long user_id,Long role_id){
		this.user_id = user_id;
		this.role_id = role_id;
	}
	
	
	private Long role_id;
	
	private Long user_id; 
	
	private Role role;
	
	private User user;

	/**  
	 * 返回 role 的值   
	 * @return role  
	 */
	public Role getRole() {
		return role;
	}

	/**  
	 * 设置 role 的值  
	 * @param role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**  
	 * 返回 user 的值   
	 * @return user  
	 */
	public User getUser() {
		return user;
	}

	/**  
	 * 设置 user 的值  
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}


	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	
	
	
}

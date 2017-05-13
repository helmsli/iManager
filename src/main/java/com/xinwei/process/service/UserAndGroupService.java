package com.xinwei.process.service;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

/**
 * Activiti用户与组的相关服务
 * @author xuejinku
 */
public interface UserAndGroupService {

	/**
	 * 添加用户
	 * @param userId
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param email
	 */
	public abstract void addUser(Long userId, String firstName, String lastName,
			String password, String email);
	/**
	 * 添加组
	 * @param groupId
	 * @param groupName
	 * @param groupType
	 */
	public abstract void addGroup(String groupId, String groupName,
			String groupType);
	/**
	 * 给用户设置所在组
	 * @param userId
	 * @param groupId
	 */
	public abstract void addMembership(String userId, String groupId);

	/**
	 * 查询所有用户
	 * @return
	 */
	public abstract List<User> queryAllUser();

	/**
	 * 获取用户所在的组列表
	 * @param userId
	 * @return
	 */
	public abstract List<Group> queryGroupsContaintUser(String userId);

	/**
	 * 获取某个组的所有用户
	 * @param groupId
	 * @return
	 */
	public abstract List<User> queryUsersInGroup(String groupId);
	
	/**
	 * 修改某个用户
	 * @param user
	 */
	public abstract void updateUser(User user);
	
	/**
	 * 修改某个组
	 * @param group
	 */
	public abstract void updateGroup(Group group);
	
	/**
	 * 删除某个用户
	 * @param userId
	 * @return 
	 */
	public abstract int deleteUser(String userId);
	
	/**
	 * 删除某个组
	 * @param groupId
	 * @return
	 */
	public abstract int deleteGroup(String groupId);
	
	/**
	 * 删除用户组间关系
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public abstract int deleteMembership(String userId, String groupId);
	
	/**
	 * 根据用户Id删除该用户的所有组关系
	 * @param userId 
	 * @return
	 */
	public abstract int deleteMembershipByUId(String userId);
	
	/**
	 * 根据组ID删除用户组间关系
	 * @param groupId 
	 * @return 
	 */
	public abstract int deleteMembershipByGroupId(String groupId);
}

package com.xinwei.process.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.stereotype.Service;

import com.xinwei.process.service.UserAndGroupService;

@Service
public class UserAndGroupServiceImpl implements UserAndGroupService {

	
	@Resource
	private IdentityService identityService ;
	
	@Override
	public void addUser(Long userId, String firstName, String lastName,
			String password, String email) {
		
		//检查该用户是否已经存在
		User existUser = identityService.createUserQuery()
			.userId(userId.toString()).singleResult();
		if(null!= existUser){
			
			existUser.setFirstName(firstName);
			existUser.setLastName(lastName);
			existUser.setPassword(password);
			existUser.setEmail(email);
			identityService.saveUser(existUser);
		} else {
			// 根据传入的userID生成一个Activiti 中的User对象
			User user = identityService.newUser(userId.toString());
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPassword(password);
			user.setEmail(email);
			// 保存用户到acticiti 数据库
			identityService.saveUser(user);
		}
	}

	@Override
	public void addGroup(String groupId, String groupName, String groupType){
		//检查该组是否已经存在
		Group existGroup = identityService.createGroupQuery()
				.groupId(groupId).singleResult();
		if(null!= existGroup){
			
			existGroup.setName(groupName);
			existGroup.setType(groupType);
			identityService.saveGroup(existGroup);
	       
		} else {
			// 创建新的组对象
			Group group = identityService.newGroup(groupId);
			group.setName(groupName);
			group.setType(groupType);
			// 保存组对象
			identityService.saveGroup(group);
		}
	}
	
	@Override
	public void addMembership(String userId, String groupId){
		 //将用户加入相应的组中
		identityService.createMembership(userId, groupId);
	}
	
	@Override
	public List<User> queryAllUser(){
		//查询所有用户
		List<User> userList = identityService.createUserQuery().list();
		return userList;
	}
	
	@Override
	public List<Group> queryGroupsContaintUser(String userId){
		//查询用户所属的组
	    List<Group> groupList = identityService
					.createGroupQuery()
					.groupMember(userId)
					.list();
	    return groupList;
	}
	
	@Override
	public List<User> queryUsersInGroup(String groupId){
		//查询某个组中的用户
		List<User> userListInGroup = identityService
				   .createUserQuery()
				   .memberOfGroup(groupId)
				   .list();
		return userListInGroup;
	}


	@Override
	public void updateUser(User user) {
		if(null != user){
			identityService.saveUser(user);
		}
		
	}


	@Override
	public void updateGroup(Group group) {
		if(null != group){
			identityService.saveGroup(group);
		}
	}


	@Override
	public int deleteUser(String userId) {
		if(null != userId){
			identityService.deleteUser(userId);
			return 1;
		}
		return 0;
	}

	@Override
	public int deleteGroup(String groupId) {
		if(null != groupId){
			identityService.deleteGroup(groupId);
			return 1;
		}
		return 0;
	}

	@Override
	public int deleteMembership(String userId, String groupId) {
		if(null != userId &&null != groupId){
			identityService.deleteMembership(userId, groupId);
			return 1;
		}
		return 0;
	}

	@Override
	public int deleteMembershipByUId(String userId) {
		if (null != userId) {
			List<Group> groups = this.queryGroupsContaintUser(userId);
			if (null != groups) {
				for (Group group : groups) {
					this.deleteMembership(userId, group.getId());
				}
				return groups.size();
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	@Override
	public int deleteMembershipByGroupId(String groupId) {
		if (null != groupId) {
			List<User> users = this.queryUsersInGroup(groupId);
			if (null != users) {
				for (User user : users) {
					this.deleteMembership(user.getId(),groupId);
				}
				return users.size();
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

}

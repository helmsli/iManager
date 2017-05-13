package com.xinwei.process.controller;

import com.xinwei.security.entity.User;
import com.xinwei.security.shiro.UserHelper;

public class BaseController {
	
	public User getCurrentUser()
	{
		User user = UserHelper.getUser();
		return user;
	}
	public boolean isCanDoData(Long roleId)
	{
		User user = getCurrentUser();
		//是否可以处理所有数据
		if(canDoAllData())
		{
			return true;
		}
		else if(user.getRoleIds().contains(roleId))//是否可以处理当前数据
		{
			return true;
		}else{	
			return false;
		}
	}
	
	
	public boolean canDoAllData()
	{
		//获取当前用户信息
		User user = getCurrentUser();
		//如果当前用户为管理员可进行所有数据操作
		if(user.getRoleIds().contains(1L)){
			return true;
		}else{
			return false;
		}
	}
}


package com.xinwei.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.security.cache.MenuCache;
import com.xinwei.security.dao.FunctionDao;
import com.xinwei.security.entity.Function;
import com.xinwei.security.entity.Menu;
import com.xinwei.security.service.FunctionService;
import com.xinwei.security.shiro.UserHelper;

@Service
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private FunctionDao functionDao;

	public List<Function> getFunction(Long menuId) {
		List<Function> result = new ArrayList();
		// 该菜单下的所有的function功能列表
		//List<Function> functions = MenuCache.getFunctions(menuId);
		List<Function> functions = functionDao.findByMenuId(menuId);
		// 查找用户的所有权限
		//Set<String> permissions = UserHelper.getUserPermission();
		// 遍历，检查拥有哪些权限
		for (Function function : functions) {
			if (SecurityUtils.getSubject().isPermitted(function.getSn())) {
				result.add(function);
			}
		}
		return result;
	}

	@Override
	public List<Function> getAllFunction(Long menuId) {
		//return MenuCache.getFunctions(menuId);
		return functionDao.findByMenuId(menuId);
	}

	@Override
	public List<Function> findAll() {
		return functionDao.findAll();
	}

	

}

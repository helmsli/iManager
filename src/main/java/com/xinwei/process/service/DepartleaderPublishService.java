package com.xinwei.process.service;

import java.util.Map;

import com.xinwei.process.entity.DepartleaderPublish;
import com.xinwei.security.entity.User;
import com.xinwei.util.page.Page;

public interface DepartleaderPublishService {

	/**
	 * 保存
	 */
	Long save(DepartleaderPublish departleaderPublish);
	
	/**
	 * 根据Id获取发布对象
	 * @param departleaderPublishId 
	 * @return
	 */
	DepartleaderPublish getById(Long publishId);
	
	/**
	 * 分页获取所有发布列表
	 * @return
	 */
	Page<DepartleaderPublish> getList();
	
	/**
	 * 根据种类查询条件分页获取发布列表
	 * @param user 当前用户
	 * @param queryMap
	 * @return
	 */
	Page<DepartleaderPublish> getListByConditions(User user, Map<String, Object> queryMap);

	/**
	 * 根据种类Id分页获取指定给三级部门经理的发布列表
	 */
	Page<DepartleaderPublish> getToThreeLeaderByCategoryId(
			Map<String, Object> queryMap);

	/**
	 * 根据种类ID分页获取某用户可以申报的发布列表
	 * @param currentUser
	 * @param queryMap
	 * @return
	 */
	Page<DepartleaderPublish> getApplyListByCategoryId(User currentUser,
			Map<String, Object> queryMap);

}

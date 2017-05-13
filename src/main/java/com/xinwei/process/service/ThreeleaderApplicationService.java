package com.xinwei.process.service;

import java.util.Map;

import com.xinwei.process.entity.ThreeleaderApplication;
import com.xinwei.security.entity.User;
import com.xinwei.util.page.Page;


public interface ThreeleaderApplicationService {

	/**
	 * 保存
	 */
	Long save(ThreeleaderApplication threeleaderApplication);
	
	/**
	 * 根据Id获取申报对象
	 * @param applicationId 申报ID
	 * @return
	 */
	ThreeleaderApplication getById(Long applicationId);
	
	/**
	 * 根据用户Id和发布Id获取申报对象
	 * @param applyPerson 申报人
	 * @param departleaderPublishId 部门经理发布Id
	 * @return
	 */
	ThreeleaderApplication getByApplyPersonAndPublishId(String applyPerson,Long departleaderPublishId);
	
	/**
	 * 根据Id修改申报对象当前任务状态
	 * @param currentState 当前状态信息
	 * @param applicationId 申报ID
	 * @return
	 */
	void updateCurrentStateByApplicationId(String currentState, Long applicationId);
	
	/**
	 * 根据Id修改申报对象流程实例Id
	 * @param processInstanceId 流程实例ID
	 * @param applicationId 申报ID
	 * @return
	 */
	void updateProcessInstanceIdByApplicationId(String processInstanceId, Long applicationId);
	
	/**
	 * 根据Id修改申报状态
	 * @param state 状态：开始、结束
	 * @param applicationId 申报ID
	 * @return
	 */
	void updateStateByApplicationId(String state, Long applicationId);
	
	/**
	 * 根据当前用户信息以及种类ID、分页数据 ，分页获取申报列表
	 * @param currentUser
	 * @param queryMap
	 * @return
	 */
	Page<ThreeleaderApplication> getByUserAndCategoryId(User currentUser,
			Map<String, Object> queryMap);
	
	void updateByPrimaryKey(ThreeleaderApplication threeleaderApplication);
}

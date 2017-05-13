package com.xinwei.process.service;

import java.util.List;
import java.util.Map;

import com.xinwei.process.entity.CommonBiz;
import com.xinwei.security.entity.User;
import com.xinwei.util.page.Page;

public interface CommonBizService {

	/**
	 * 获取所有
	 */
	List<CommonBiz> selectAll();

	/**
	 * 获取ById
	 */
	CommonBiz selectByPrimaryKey(String dataId);

	/**
	 * 保存
	 */
	String save(CommonBiz commonBiz);

	/**
	 * 删除
	 */
	void delete(String dataId);

	/**
	 * 修改
	 */
	void update(CommonBiz commonBiz);

	/**
	 *  根据项目标识和更新状态进行查询
	 * @param projectId
	 * @param status
	 * @return
	 */
	List<CommonBiz> selectByProjectIdAndStatus(Long projectId, String status);

	/**
	 * 根据项目ID和业务类型查询
	 * @param projectId
	 * @param serviceType
	 * @return
	 */
	List<CommonBiz> selectByProjectIdAndServiceType(Long projectId,
			String serviceType);

	/**
	 *  更新审批结果、变更状态，更新人和更新时间
	 * @param commonBiz
	 */
	void updateApprovalChangeByDataId(CommonBiz commonBiz);

	/**
	 * 更新变更状态status
	 * @param status
	 * @param dataId
	 */
	void updateChangeStatusByDataId(String status, String dataId);

	/**
	 *  根据projectId查询CommonBiz对象
	 * @param projectId
	 * @return
	 */
	List<CommonBiz> getCommonBizByProjectId(Long projectId);

	/**
	 *  更新变更状态status
	 * @param processInstanceId
	 * @param taskId
	 * @param dataId
	 */
	void updateProcessInstanceAndTaskIdByDataId(String processInstanceId,
			String taskId, String dataId);

	/** 
	 * 根据项目类别和业务类型,当前用户，项目名(参数可选)进行分页查询
	 * @param user 当前用户信息
	 * @param map
	 * @return
	 */
	Page<CommonBiz> selectByCategoryServiceTypeAndProjectName(User user,
			Map<String, Object> map);

}

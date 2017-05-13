package com.xinwei.process.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xinwei.process.entity.Project;

public interface ProjectMapper {
	int deleteByPrimaryKey(Long projectId);

	int insert(Project record);

	Project selectByPrimaryKey(Long projectId);

	List<Project> selectAll();

	int updateByPrimaryKey(Project record);

	/**
	 * 根据用户ID、项目类别获取该用户申请的正在进行的项目记录数
	 * 
	 * @param map 用户ID、项目类型ID
	 *            查询参数
	 * @return
	 */
	Long getProjectListByUserIdCount(Map<String, Object> map);

	/**
	 * 根据用户ID、项目类别分页查询正在进行的项目列表
	 * 
	 * @param map 
	 *            查询参数（包含起始记录数startRow,当页记录数pageSize，用户ID、项目类型ID）
	 * @return
	 */
	List<Project> getProjectListByUserId(Map<String, Object> map);

	/**
	 * 获取已结项目列表分页条数
	 * 
	 * @param map 用户ID、项目类型ID
	 *            查询参数
	 * @return
	 */
	Long getFinishedProjectListByUserIdCount(Map<String, Object> map);

	/**
	 * 获取已结项目列表分页后的数据
	 * 
	 * @param map 
	 *            查询参数（包含起始记录数startRow,当页记录数pageSize，用户ID、项目类型ID）
	 * @return
	 */
	List<Project> getFinishedProjectListByUserId(Map<String, Object> map);

	/**
	 * 根据项目类别进行统计
	 * 
	 * @param categoryId
	 *        项目种类ID
	 * @return 记录数
	 */
	Long countProjectsByCategoryId(Long categoryId);

	/**
	 * 根据项目种类进行分页查询
	 * 
	 * @param map
	 *            查询参数（包含起始记录数startRow,当页记录数pageSize，项目类别）
	 * @return
	 */
	List<Project> getProjectsByCategoryId(Map<String, Object> map);
	
	/**
	 * 根据项目类别和状态进行统计
	 * 
	 * @param map(项目种类ID,项目状态)
	 *                
	 * @return 记录数
	 */
	Long countByCategoryIdAndState(Map<String, Object> map);

	/**
	 * 根据项目种类、状态进行分页查询
	 * 
	 * @param map
	 *            查询参数（包含起始记录数startRow,当页记录数pageSize，项目类别）
	 * @return
	 */
	List<Project> getByCategoryIdAndState(Map<String, Object> map);
	
	/**
	 * 根据项目经理、项目类别和状态进行统计
	 * 
	 * @param map(项目经理ID，种类ID,项目状态)
	 *                
	 * @return 记录数
	 */
	Long countByProjectManagerIdCategoryIdAndState(Map<String, Object> map);

	/**
	 * 根据项目经理、项目种类、状态进行分页查询
	 * 
	 * @param map
	 *            查询参数（包含项目经理、项目种类、状态，起始记录数startRow,当页记录数pageSize）
	 * @return
	 */
	List<Project> getByProjectManagerIdCategoryIdAndState(Map<String, Object> map);
	
	/**
	 * 根据项目经理Id、项目类别进行统计
	 * 
	 * @param categoryId
	 *        项目经理ID，项目种类ID
	 * @return 记录数
	 */
	Long countProjectsByProjectManagerIdCategoryId(Map<String, Object> map);

	/**
	 * 根据项目经理Id、项目类别进行统计
	 * 
	 * @param map
	 *            查询参数（包含起始记录数startRow,当页记录数pageSize，项目经理ID，项目类别）
	 * @return
	 */
	List<Project> getProjectsByProjectManagerIdCategoryId(Map<String, Object> map);

	/**
	 * 修改项目表项目经理自评
	 * 
	 * @param selfAppraise
	 * @param projectId 项目ID
	 */
	int updateSelfAppraiseByProjectId(@Param("selfAppraise") String selfAppraise,@Param("projectId") Long projectId);
	/**
	 * 修改项目表部门经理评价
	 * @param departLeaderAppraise 
	 * @param projectId 项目ID
	 */
	int updateDepartLeaderAppraiseByProjectId(@Param("departLeaderAppraise") String departLeaderAppraise,@Param("projectId") Long projectId);

	/**
	 * 修改流程实例ID的方法
	 * @param projectProcessInstanceId 流程实例ID 
	 * @param projectId 项目ID
	 */
	void updateProcessInstanceByProjectID(@Param("projectProcessInstanceId") String projectProcessInstanceId,@Param("projectId") Long projectId);
	/**
	 * 修改更改流程实例ID的方法
	 * @param projectProcessInstanceId 流程实例ID 
	 * @param projectId 项目ID
	 */
	void updateChangeProcessInstanceId(@Param("changeProcessInstanceId") String changeProcessInstanceId,@Param("projectId") Long projectId);
	/**
	 * 修改主流程项目状态信息的方法
	 */
	void updateMainCurrentState(@Param("mainCurrentState") String mainCurrentState,@Param("projectId") Long projectId);
	/**
	 * 修改主流程上一状态信息的方法
	 */
	void updateMainPreviousState(@Param("mainPreviousState") String mainPreviousState,@Param("projectId") Long projectId);
	/**
	 * 修改更改流程当前状态信息的方法
	 */
	void updateChangeCurrentState(@Param("changeCurrentState") String changeCurrentState,@Param("projectId") Long projectId);
	/**
	 * 修改更改流程上一状态信息的方法
	 */
	void updateChangePreviousState(@Param("changePreviousState") String changePreviousState,@Param("projectId") Long projectId);
	/**
	 * 修改周期报告状态信息的方法
	 */
	void updateReportCurrentState(@Param("reportCurrentState") String reportCurrentState,@Param("projectId") Long projectId);
	
	/**
	 * 修改项目更改数据ID
	 */
	void updateChangeDataId(@Param("changeDataId") String changeDataId,@Param("projectId") Long projectId);
	
	/**
	 * 修改项目状态
	 */
	void updateState(@Param("state") String state,@Param("projectId") Long projectId);

	/**
	 * 修改项目changeDataId&changeProcessInstanceId
	 */
	void updateProjectChangeByProjectId(@Param("changeDataId") String changeDataId,@Param("changeProcessInstanceId") String changeProcessInstanceId,@Param("projectId") Long projectId);

	/**
	 * 根据ProjectId查询项目变更信息
	 */
	Project selectChangeInfoByProjectId(Long projectId);
	/**
	 * 根据项目种类和状态获查询
	 */
	List<Project> selectAllByCategoryIdAndState(@Param("categoryId") Long categoryId,@Param("state") String state);
	/**
	 * 根据项目project_manager_id（user） 、种类、状态查询
	 */
	List<Project> selectAllByProjectManagerIdCategoryIdAndState(@Param("projectManagerId") Long projectManagerId,@Param("categoryId") Long categoryId,@Param("state") String state);

	/**
	 * 根据项目申请人和发布Id进行查询
	 * @param projectApplyPerson
	 * @param publishId
	 * @return
	 */
	Project selectByApplyPersonAndPublishId(@Param("projectApplyPerson")String projectApplyPerson,
			@Param("publishId")Long publishId);

	/**
	 * 根据项目申请人和项目类别统计申报的项目记录数
	 * @param map
	 * @return
	 */
	Long countApplyProjectListByUserIdAndCategoryId(Map<String, Object> map);

	/**
	 * 根据项目申请人和项目类别分页查询申报的项目列表
	 * @param map
	 * @return
	 */
	List<Project> getApplyProjectListByUserIdAndCategoryId(
			Map<String, Object> map);

	/**
	 * 根据projectID列表进行查询
	 * @param projectIdList
	 * @return
	 */
	List<Project> selectByIdList(@Param("idList")List<Long> idList);
	
}
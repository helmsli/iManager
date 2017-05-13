package com.xinwei.process.service;

import java.util.List;
import java.util.Map;

import com.xinwei.process.entity.Project;
import com.xinwei.process.entity.ProjectDetailInfo;
import com.xinwei.security.entity.User;
import com.xinwei.util.page.Page;

public interface ProjectService{
	
	//项目对象属性
    String MAINCURRENTSTATE = "mainCurrentState";  //项目主流程当前状态信息
    String MAINPREVIOUSSTATE = "mainPreviousState";  //项目主流程上一状态信息
    String CHANGECURRENTSTATE = "changeCurrentState";  //项目更改流程当前状态信息  
    String CHANGEPREVIOUSSTATE = "changePreviousState";  //项目更改流程上一状态信息
    String REPORTCURRENTSTATE = "reportCurrentState";  //周期性报告当前状态
    String CHANGEDATAID = "changeDataId";  //项目更改数据ID
    String CHANGEPROCESSINSTANCEID = "changeProcessInstanceId";  //项目更改流程实例ID
    String SELFAPPRAISE = "selfAppraise";  //项目经理自评 
    String DEPARTLEADERAPPRAISE = "departLeaderAppraise";  //部门经理评价  
    String PROJECTPROCESSINSTANCEID = "projectProcessInstanceId";  //项目流程实例ID
    String STATTE = "state";//项目状态（正在进行、结项）
	/**
	 * 获取所有
	 */
	List<Project> selectAll();
	
	
	/**
	 * 获取ById
	 */
	Project selectByPrimaryKey(Long project_id);
	
	/**
	 * 保存
	 */
	Long save(Project project);

	/**
	 * 删除
	 */
	void delete(Long project_id);
	
	/**
	 * 修改
	 */
	void update(Project project);

	
	/**
	  通过用户Id、项目种类ID分页查询正在进行的项目列表
	 * @param map
	 * @return
	 */
	Page<Project> getProjectListByUserId(Map<String,Object> map);
	
	
	/**
	  根据用户Id和项目类别分页查询已结束项目列表
	 * @param map
	 * @return
	 */
	Page<Project> getFinishedProjectListByUserId(Map<String,Object> map);
	
	/**
	  通过用户角色、项目种类Id分页查询所有项目列表
	 * @param map
	 * @return
	 */
	Page<Project> getProjectListByUserAndCategoryId(User user,Map<String,Object> map);
	
	/**
	  通过用户角色、项目种类Id、状态分页查询项目列表
	 * @param map
	 * @return
	 */
	Page<Project> getProjectListByCategoryIdAndState(User user,Map<String,Object> map);
	
	/***
	 * 通过项目Id查询项目详情
	 * @param projectId
	 * @param stage
	 * @return
	 */
	ProjectDetailInfo getProjectDetailInfo(Long projectId);
	
	/**
	 * 根据用户角色获取相应的正在进行的项目列表
	 * @param currentUser 当前用户
	 * @return
	 */
	Map<String, Object> getAllRunnigProjects(User currentUser);
	
	/**
	 * 高亮跟踪流程实例
	 */
	byte[] traceProcessImage(String processInstanceId);

	/**
	 * 修改项目某个属性值
	 * @param propertyName 属性名称 
	 * @param propertyNewValue 属性新值
	 * @param projectId 项目ID
	 */
    void updateProjectPropertyByProjectId(String propertyName,String propertyNewValue,Long projectId);
    
    /**
	 * 修改项目多个属性值
	 * @param propertyName 属性名称 
	 * @param propertyNewValue 属性新值
	 * @param projectId 项目ID
	 */
    void updateProjectPropertysByProjectId(String propertyName[],String propertyNewValue[],Long projectId);
		

    void updateProjectChangeByProjectId(String changeDataId,
			String changeProcessInstanceId,Long projectId);

    
	/**
	 * 根据projectId获取ChangeInfo
	 */
	Project selectChangeInfoByProjectId(Long projectId);
	/**
	 * 根据项目种类和状态获取项目列表
	 */
	List<Project> selectAllByCategoryIdAndState(Long categoryId,String state);
	/**
	 * 根据项目project_manager_id（user） 、种类、状态获取项目列表
	 */
	List<Project> selectAllByProjectManagerIdCategoryIdAndState(Long projectManagerId,Long categoryId,String state);

	/**
	 * 根据项目申请人和发布Id进行查询
	 * @param projectApplyPerson 项目申报人
	 * @param publishId 发布Id
	 * @return
	 */
	Project getByApplyPersonAndPublishId(String projectApplyPerson, Long publishId);

	/**
	 * 根据项目申请人和种类Id分页查询申报的项目列表
	 * @param map
	 * @return
	 */
	Page<Project> getApplyProjectListByUserIdAndCategoryId(
			Map<String, Object> map);
	
	com.doc.project.vo.Project createDownloadEntity(Project project);

}

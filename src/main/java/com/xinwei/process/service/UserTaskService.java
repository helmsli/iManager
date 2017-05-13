package com.xinwei.process.service;

import java.util.List;
import java.util.Map;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;


/**
 * 用户任务相关服务
 * @author xuejinku
 *
 */
public interface UserTaskService {

	/**
	 * 获取用户待办任务列表
	 * @param userId 用户ID
	 * @return
	 */
	public List<Task> getTaskList(String userId);
	
	/**
	 * 根据用户ID和项目类别ID获取用户待办任务数
	 * @param userId 用户ID
	 * @param categoryId 类别ID
	 * @return
	 */
	public Long countTasksByUidAndCategoryId(String userId,String categoryId);
	
	/**
	 * 根据用户ID和项目类别ID,分页获取用户待办任务列表
	 * @param userId 用户ID
	 * @param categoryId 类别ID
	 * @param firstResult 起始索引
	 * @param maxResults 获取记录数
	 * @return
	 */
	public List<Task> getTaskListByUidAndCategoryId(String userId,String categoryId,int firstResult,int maxResults);
	
	/**
	 * 根据用户ID和项目类别ID获取用户已完成任务数
	 * @param userId 用户ID
	 * @param categoryId 种类ID
	 * @return
	 */
	public Long countHistoryTasksByUidAndCategoryId(String userId,String categoryId);
	
	/**
	 * 根据用户ID和项目类别ID,分页获取用户已完成任务列表
	 * @param userId 用户ID
	 * @param categoryId 类别ID
	 * @param firstResult 起始索引
	 * @param maxResults 获取记录数
	 * @return
	 */
	public List<HistoricTaskInstance> getHistoryTasksByUidAndCategoryId(String userId,String categoryId,int firstResult,int maxResults);
	
	/**
	 * 获取某个用户任务
	 * @param taskId 任务ID
	 * @return
	 */
	public Task getTask(String taskId);
	
	/**
	 * 签收某个任务
	 * @param taskId 任务ID
	 * @param userId 用户ID
	 */
	public void claimTask(String taskId, String userId);
	
	/**
	 * 完成任务
	 * @param taskId 任务Id
	 * @param variables 流程变量
	 */
	public void completeTask(String taskId, Map<String, Object> variables);
	
	/**
	 * 根据任务Id获取历史任务对象
	 * @param historyTaskId
	 * @return
	 */
	public HistoricTaskInstance getHistoryTask(String historyTaskId);
	
	/**
	 * 获取用户已办理的任务列表
	 * @param userId 用户ID
	 * @return 用户已完成任务列表
	 */
	public List<HistoricTaskInstance> getHistoryTasksByUid(String userId);
	
	/**
	 * 根据业务key获取已完成任务列表
	 * @param businessKey 业务key
	 * @return 已完成任务列表
	 */
	public List<HistoricTaskInstance> getFinishedTasksByBusinessKey(String businessKey);

	/**
	 * 根据流程实例ID查询某个流程当前待处理的所有用户任务
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	public abstract List<Task> findTaskByProcessInstanceId(
			String processInstanceId);
	
	/**
	 * 根据流程实例ID查询某个流程当前待处理的所有用户任务
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	public abstract List<Task> findActiveTaskByProcessInstanceId(
			String processInstanceId);
	
	/**
	 * 根据流程实例ID查询某个流程当前待处理的所有用户任务
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	public abstract List<HistoricProcessInstance> findFinishedTaskByProcessKey(
			String processKey);
	
}

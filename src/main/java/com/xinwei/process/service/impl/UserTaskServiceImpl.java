package com.xinwei.process.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import com.xinwei.process.service.UserTaskService;

@Service
public class UserTaskServiceImpl implements UserTaskService {

	@Resource
	private TaskService taskService; //Activiti中用户任务服务
	@Resource
	private HistoryService historyService;// 与历史数据（历史表）相关的Service
	
	@Override
	public List<Task> getTaskList(String userId) {
		// 使用综合查询接口
		List<Task> allTasks = taskService.createTaskQuery()
				.taskCandidateOrAssigned(userId)
				.orderByTaskCreateTime().asc()
				.list();
		return allTasks;
	}
	@Override
	public Long countTasksByUidAndCategoryId(String userId,String categoryId) {
		
		 Long count = taskService.createTaskQuery()
				.taskCandidateOrAssigned(userId)
				.taskCategory(categoryId)
				.count();
		return count;
	}
	
	@Override
	public List<Task> getTaskListByUidAndCategoryId(String userId,String categoryId,int firstResult,int maxResults) {
		// 使用综合查询接口
		List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateOrAssigned(userId)
				.taskCategory(categoryId)
				.orderByTaskCreateTime().asc()
				.listPage(firstResult, maxResults);//分页获取
		return tasks;
	}
	
	@Override
	public Task getTask(String taskId) {
		Task task = taskService
				.createTaskQuery()
				.taskId(taskId)
				.singleResult();
		return task;
	}

	@Override
	public void claimTask(String taskId, String userId) {
		 //任务签收
	     taskService.claim(taskId, userId);
	}

	@Override
	public void completeTask(String taskId, Map<String, Object> variables) {
		//完成任务
		taskService.complete(taskId, variables);
	}

	@Override
	public HistoricTaskInstance getHistoryTask(String historyTaskId) {
		// TODO Auto-generated method stub
		return  historyService
	            .createHistoricTaskInstanceQuery()
	            .taskId(historyTaskId)
	            .singleResult();
	}
	
	@Override
	public List<HistoricTaskInstance> getHistoryTasksByUid(String userId) {
		List<HistoricTaskInstance> list = historyService
	            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
	            .taskAssignee(userId)
	            .finished()
	            .orderByHistoricTaskInstanceStartTime().asc()
	            .list();
		 return list;
	}

	@Override
	public Long countHistoryTasksByUidAndCategoryId(String userId,String categoryId) {
		Long count = historyService
	            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
	            .taskAssignee(userId)
	            .taskCategory(categoryId)
	            .finished()
	            .count();
		 return count;
	}
	
	@Override
	public List<HistoricTaskInstance> getHistoryTasksByUidAndCategoryId(String userId,String categoryId,int firstResult,int maxResults) {
		List<HistoricTaskInstance> finishedTasklist = historyService
	            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
	            .taskAssignee(userId)
	            .taskCategory(categoryId)
	            .finished()
	            .orderByHistoricTaskInstanceStartTime().desc()
	            .listPage(firstResult, maxResults);
		 return finishedTasklist;
	}
	
	@Override
	public List<HistoricTaskInstance> getFinishedTasksByBusinessKey(String businessKey) {
		List<HistoricTaskInstance> finishedTasklist = historyService
	            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
	            .processInstanceBusinessKey(businessKey)
	            .finished()
	            .orderByHistoricTaskInstanceStartTime().desc()
	            .list();
		 return finishedTasklist;
	}
	
	@Override
	public List<Task> findTaskByProcessInstanceId(String processInstanceId) {
		List<Task> tasks = taskService.createTaskQuery()
				.processInstanceId(processInstanceId)
				.orderByTaskCreateTime().asc()
				.list();
		return tasks;
	}
	
	@Override
	public List<Task> findActiveTaskByProcessInstanceId(String processInstanceId) {
		List<Task> tasks = taskService.createTaskQuery()
				.processInstanceId(processInstanceId).active()
				.orderByTaskCreateTime().desc()
				.list();
		return tasks;
	}

	@Override
	public  List<HistoricProcessInstance> findFinishedTaskByProcessKey(
			String processKey) {
		List<HistoricProcessInstance> tasks = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey(processKey).finished().orderByProcessInstanceEndTime()
				.desc().list();
		return tasks;
	}

}

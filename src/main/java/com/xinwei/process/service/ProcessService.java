package com.xinwei.process.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.TaskInfo;
/**
 * Activiti流程相关服务
 *
 */
public interface ProcessService {
	/**
	 * 启动一个流程实例
	 * @param className 类名称
	 * @param dataId 数据Id
	 * @param userId  启动流程的用户ID
	 * @param variables 设置流程中需要用的流程变量
	 * @return
	 */
	public abstract ProcessInstance startProcess(String processDefinitionKey,String className,String dataId,
			String userId, Map<String, Object> variables);
	
	/**
	 * 获取某个正在执行的流程实例
	 * @param processInstanceId 流程实例Id
	 * @return
	 */
	public abstract ProcessInstance getProcessInstance(String processInstanceId);

	/**
	 * 查询历史流程实例
	 * @param processInstanceId 流程实例ID
	 * @return 历史流程实例对象
	 */
	public abstract HistoricProcessInstance findHistoryProcessInstance(
			String processInstanceId);
	
	/**
	 * 查询某个流程实例已经历的所有活动
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	public abstract List<HistoricActivityInstance> findHistoryActiviti(
			String processInstanceId);
	/**
	 * 查询某个流程实例已经历的所有用户任务
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	public abstract List<HistoricTaskInstance> findHistoryTask(
			String processInstanceId);
	
	/**
	 * 查询某个流程实例的执行中设置的所有流程变量
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	public abstract List<HistoricVariableInstance> findHistoryProcessVariables(
			String processInstanceId);
	
	/**
	 * 根据业务key,得到业务数据Id
	 */
	public Long getDataIdByBusinessKey(String businessKey);
	
	/**
	 * 根据业务key,得到业务对象类名
	 */
	public String getCLassNameByBusinessKey(String businessKey);
	
	/**
	 * 根据任务对象获取对应的状态信息
	 * @param taskInfo
	 * @return StateInfo对象json串
	 */
	public String getStateInfoByTask(TaskInfo taskInfo);
	
	/**
	 * 根据任务对象获取对应的状态信息
	 * @param delegateTask 
	 * @return StateInfo对象json串
	 */
	public String getStateInfoByDelegateTask(DelegateTask delegateTask);
	
}
package com.xinwei.process.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.TaskInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.util.StringUtil;

import com.xinwei.process.entity.StateInfo;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.TaskDefKeyNameService;
import com.xinwei.security.service.UserService;

/**
 * 流程相关服务
 */
@Service
public class ProcessServiceImpl implements ProcessService {
	
	@Resource
	private RuntimeService runtimeService;//activiti运行时服务
	@Resource
	private IdentityService identityService; //activiti用户、组服务
	@Resource
	private HistoryService historyService;// 与历史数据（历史表）相关的Service
	@Resource
	private TaskDefKeyNameService taskDefKeyNameServiceImpl;// 根据TaskDefinitionKey获取任务状态名服务
	
	private Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
	
	@Override
	public ProcessInstance startProcess(String processDefinitionKey,String className,String dataId,String userId, Map<String,Object> variables){
		
		//构造businessKey，构造规则为：类名加上冒号再加上数据ID，如(Project:1000001)
		String businessKey = className + ":" + dataId;
		//设置启动流程的人员Id
		identityService.setAuthenticatedUserId(userId);
		
		//启动一个流程实例
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(processDefinitionKey,businessKey,variables);
		return processInstance;
	}

	@Override
	public ProcessInstance getProcessInstance(String processInstanceId) {
		ProcessInstance processInstance = runtimeService
	    		.createProcessInstanceQuery()
	    		.processInstanceId(processInstanceId)
	    		.singleResult();
		return processInstance;
	}

	@Override
	public HistoricProcessInstance findHistoryProcessInstance(
			String processInstanceId) {
		   HistoricProcessInstance hpi = historyService
	              .createHistoricProcessInstanceQuery()// 创建历史流程实例查询
	              .processInstanceId(processInstanceId)// 使用流程实例ID查询
	              .orderByProcessInstanceStartTime().asc()
	              .singleResult();
		 
	       logger.debug(hpi.getId() + "    " + hpi.getProcessDefinitionId() + "    " + hpi.getStartTime() + "    "
	                + hpi.getEndTime() + "     " + hpi.getDurationInMillis());
	       return hpi;
	}

	@Override
	public List<HistoricActivityInstance> findHistoryActiviti(
			String processInstanceId) {
		List<HistoricActivityInstance> list = historyService
                .createHistoricActivityInstanceQuery()// 创建历史活动实例的查询
                .processInstanceId(processInstanceId)//
                .orderByHistoricActivityInstanceStartTime().asc()
                .list();
  
	    return list;
	}

	@Override
	public List<HistoricTaskInstance> findHistoryTask(String processInstanceId) {
		List<HistoricTaskInstance> list = historyService
	            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
	            .processInstanceId(processInstanceId)
	            .taskDeleteReason("completed")
	            .orderByHistoricTaskInstanceStartTime().asc()
	            .list();
		 return list;
	}

	@Override
	public List<HistoricVariableInstance> findHistoryProcessVariables(
			String processInstanceId) {
		List<HistoricVariableInstance> list = historyService
                .createHistoricVariableInstanceQuery()// 创建一个历史的流程变量查询对象
                .processInstanceId(processInstanceId)//
                .list();
        
        return list;
	}
	
	@Override
	public Long getDataIdByBusinessKey(String businessKey){
		String [] parts = this.splitBusinessKey(businessKey);
		Long dataId = Long.parseLong(parts[1]);
		return dataId;
	}
	
	@Override
	public String getCLassNameByBusinessKey(String businessKey) {
		String [] parts = this.splitBusinessKey(businessKey);
		String className =  parts[0];
		return className;
	}
	
	@Override
	public String getStateInfoByTask(TaskInfo taskInfo) {
		// 获取任务定义key
		String taskDefinitionKey = taskInfo.getTaskDefinitionKey();
		// 根据任务定义key获取状态名
		String stateName = taskDefKeyNameServiceImpl.selectStateNameByPrimaryKey(taskDefinitionKey);
		if(StringUtils.isBlank(stateName)){
			stateName = taskInfo.getName();
		}
		// 构造状态信息
		StateInfo stateInfo = new StateInfo(taskDefinitionKey,stateName,taskInfo.getId());
		return stateInfo.toString();
	}
	
	@Override
	public String getStateInfoByDelegateTask(DelegateTask delegateTask) {
		// 获取任务定义key
		String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
		// 根据任务定义key获取状态名
		String stateName = taskDefKeyNameServiceImpl.selectStateNameByPrimaryKey(taskDefinitionKey);
		if(StringUtils.isBlank(stateName)){
			stateName = delegateTask.getName();
		}
		// 获取任务办理人姓名
		String assignName = getAssignName(delegateTask);
		// 构造状态信息
		StateInfo stateInfo = new StateInfo(taskDefinitionKey,stateName,delegateTask.getId(),assignName);
		return stateInfo.toString();
	}

	// 获取任务办理人姓名
	private String getAssignName(DelegateTask delegateTask) {
		String assignName = "";
		try {
			String userId  = delegateTask.getAssignee();
			// 如果确定了办理人
			if(StringUtil.isNotEmpty(userId)){
				assignName = identityService
						.createUserQuery()
						.userId(userId)
						.singleResult()
						.getFirstName();
			}else{
				// 获取候选组或候选人列表
				Set<IdentityLink> candidates = delegateTask.getCandidates();
				if(null != candidates && !candidates.isEmpty()){
					// 遍历候选人或候选组
					for(IdentityLink identity : candidates){
						if(StringUtil.isNotEmpty(identity.getUserId())){
							assignName = assignName + 
									identityService
									.createUserQuery()
									.userId(identity.getUserId())
									.singleResult()
									.getFirstName() + " ";
						}else if(StringUtil.isNotEmpty(identity.getGroupId())){
							assignName = assignName +
									identityService
									.createGroupQuery()
									.groupId(identity.getGroupId())
									.singleResult()
									.getName() + " ";
						}else{
							
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			assignName = "";
		}
		return assignName.trim();
	}

	/*
	 * 分割业务key
	 * @param businessKey 业务key
	 * @return
	 */
	private String [] splitBusinessKey(String businessKey){
		String [] parts = null;
		if(StringUtils.isNotBlank(businessKey)){
			if(businessKey.contains(":")){
				//以":"为分隔符，分割业务key
				parts = businessKey.split(":");
			}
		}
		return parts;
	}

	
}
